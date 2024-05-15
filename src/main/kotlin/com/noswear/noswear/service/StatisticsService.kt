package com.noswear.noswear.service

import com.noswear.noswear.domain.TotalCount
import com.noswear.noswear.domain.TotalCountId
import com.noswear.noswear.domain.WordCount
import com.noswear.noswear.domain.WordCountId
import com.noswear.noswear.dto.SendDto
import com.noswear.noswear.repository.TotalCountRepository
import com.noswear.noswear.repository.UserRepository
import com.noswear.noswear.repository.WordCountRepository
import com.noswear.noswear.utils.ProfanityUtil
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*

@Service
class StatisticsService(
    private val wordCountRepository: WordCountRepository,
    private val totalCountRepository: TotalCountRepository,
    private val userRepository: UserRepository
) {
    fun analyzeProfanity(email: String, sendDto: SendDto): Map<String, Int> {
        val result = HashMap<String, Int>()

        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")
        val id = user.id!!

        val date = sendDto.dateTime.toLocalDate()
        val hour = sendDto.dateTime.hour

        val uuid = UUID.randomUUID()
        val path = "./temp_files/$uuid.m4a"
        val byteArray = Base64.getDecoder().decode(sendDto.data)
        val file = File(path)
        file.writeBytes(byteArray)

        val badWords = ProfanityUtil.getBadWords()

        var count = 0

        val processBuilder = ProcessBuilder("whisper", path, "--language", "Korean", "--output_format", "txt")
        val process = processBuilder.start()
        BufferedReader(InputStreamReader(process.inputStream))
            .forEachLine { line ->
                println(line)
                line.split(" ").map { split ->
                    badWords.forEach { word ->
                        if (split.contains(word)) {
                            val wordCountId = WordCountId(
                                id = id,
                                word = word,
                                date = date
                            )

                            wordCountRepository.findById(wordCountId)
                                .map { wordCount ->
                                    wordCount.count += 1
                                    wordCountRepository.save(wordCount)
                                }.orElseGet {
                                    wordCountRepository.save(
                                        WordCount(
                                            wordCountId = wordCountId,
                                            count = 1
                                        )
                                    )
                                }

                            if (result.containsKey(word)) {
                                result[word] = result[word]!! + 1
                            } else {
                                result[word] = 1
                            }

                            count++
                        }
                    }
                }
            }

        val totalCountId = TotalCountId(
            id = id,
            date = date,
            time = hour
        )

        totalCountRepository.findById(totalCountId)
            .map { totalCount ->
                totalCount.count += count
                totalCountRepository.save(totalCount)
            }.orElseGet {
                totalCountRepository.save(
                    TotalCount(
                        totalCountId = totalCountId,
                        count = count
                    )
                )
            }

        File("./$uuid.txt").delete()
        file.delete()

        return result
    }
}