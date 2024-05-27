package com.noswear.noswear.service

import com.noswear.noswear.domain.Joins
import com.noswear.noswear.domain.JoinsId
import com.noswear.noswear.domain.Program
import com.noswear.noswear.domain.User
import com.noswear.noswear.dto.JoinDto
import com.noswear.noswear.dto.ProgramDto
import com.noswear.noswear.dto.VoiceDto
import com.noswear.noswear.repository.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.io.File
import java.lang.Exception
import java.util.*

@Service
class ManageService(
    private val programRepository: ProgramRepository,
    private val userRepository: UserRepository,
    private val teachesRepository: TeachesRepository,
    private val worksRepository: WorksRepository,
    private val joinsRepository: JoinsRepository,
    private val belongsRepository: BelongsRepository,
    private val classRepository: ClassRepository
) {
    fun createProgram(email: String, programDto: ProgramDto): Program {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        val classId = teachesRepository.findById(user.id!!)
            .orElseThrow()
            .cId

        val program = Program(
            classId = classId,
            programName = programDto.programName,
            startDate = programDto.startDate,
            endDate = programDto.endDate
        )

        return programRepository.save(program)
    }

    fun getALlPrograms(email: String): List<Program> {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        val classId = if (user.role == "STUDENT") {
            belongsRepository.findById(user.id!!)
                .orElseThrow()
                .classId
        } else {
            teachesRepository.findById(user.id!!)
                .orElseThrow()
                .cId
        }

        return programRepository.findByClassId(classId)
    }

    fun getMyPrograms(email: String): List<Program> {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        return joinsRepository.findByJoinsIdId(user.id!!).map { joins ->
            joins.joinsId.program
        }
    }

    fun getCurrentProgram(email: String): Program? {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        val program = if (user.role == "STUDENT") {
            programRepository.findCurrentProgramByUserId(user.id!!)
        } else {
            val cId = teachesRepository.findById(user.id!!)
                .orElseThrow()
                .cId

            programRepository.findCurrentProgramByClassId(cId)
        }

        return program
    }

    fun joinProgram(email: String, joinDto: JoinDto): Program {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        val belongs = belongsRepository.findById(user.id!!)
            .orElseThrow()

        val program = programRepository.findByClassIdAndProgramName(belongs.classId, joinDto.programName)
            ?: throw Exception()

        joinsRepository.save(Joins(
            JoinsId(
                id = user.id!!,
                program = program
            )
        ))

        return program
    }

    fun getClassStudents(email: String): List<User> {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        val cId = teachesRepository.findById(user.id!!)
            .orElseThrow()
            .cId

        return belongsRepository.findByClassId(cId).map {
            it.user
        }
    }

    fun getSchoolCode(email: String): String {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        val works = worksRepository.findByIdAndIsManagerTrue(user.id!!)
            ?: throw Exception("No school that ${user.name} manages exists")
        return works.sId
    }

    fun getClassCode(email: String): String {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        return teachesRepository.findById(user.id!!)
            .orElseThrow()
            .cId
    }

    fun getClassInfo(email: String): String {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        val cId = if (user.role == "STUDENT") {
            belongsRepository.findById(user.id!!)
                .orElseThrow()
                .classId
        } else {
            teachesRepository.findById(user.id!!)
                .orElseThrow()
                .cId
        }

        return classRepository.findById(cId)
            .orElseThrow()
            .className
    }

    fun getUserInfo(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")
    }

    fun saveVoice(email: String, voiceDto: VoiceDto) {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        val path = "./voice_files/${user.id!!}.m4a"
        val byteArray = Base64.getDecoder().decode(voiceDto.data)
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        file.writeBytes(byteArray)
    }
}