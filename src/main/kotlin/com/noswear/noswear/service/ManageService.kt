package com.noswear.noswear.service

import com.noswear.noswear.domain.Program
import com.noswear.noswear.dto.ProgramDto
import com.noswear.noswear.repository.ProgramRepository
import com.noswear.noswear.repository.TeachesRepository
import com.noswear.noswear.repository.UserRepository
import com.noswear.noswear.repository.WorksRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class ManageService(
    private val programRepository: ProgramRepository,
    private val userRepository: UserRepository,
    private val teachesRepository: TeachesRepository,
    private val worksRepository: WorksRepository
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
}