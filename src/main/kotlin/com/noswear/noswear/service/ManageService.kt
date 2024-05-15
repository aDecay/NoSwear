package com.noswear.noswear.service

import com.noswear.noswear.domain.Program
import com.noswear.noswear.dto.ProgramDto
import com.noswear.noswear.repository.ProgramRepository
import com.noswear.noswear.repository.TeachesRepository
import com.noswear.noswear.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ManageService(
    private val programRepository: ProgramRepository,
    private val userRepository: UserRepository,
    private val teachesRepository: TeachesRepository
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
}