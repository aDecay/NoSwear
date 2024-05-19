package com.noswear.noswear.service

import com.noswear.noswear.domain.Joins
import com.noswear.noswear.domain.JoinsId
import com.noswear.noswear.domain.Program
import com.noswear.noswear.dto.JoinDto
import com.noswear.noswear.dto.ProgramDto
import com.noswear.noswear.repository.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class ManageService(
    private val programRepository: ProgramRepository,
    private val userRepository: UserRepository,
    private val teachesRepository: TeachesRepository,
    private val worksRepository: WorksRepository,
    private val joinsRepository: JoinsRepository,
    private val belongsRepository: BelongsRepository
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
                .cId
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

        val programs = mutableListOf<Program>()
        joinsRepository.findByJoinsIdId(user.id!!).map { joins ->
            programs.add(programRepository.findById(joins.joinsId.pId)
                .orElseThrow())
        }


        return programs
    }

    fun joinProgram(email: String, joinDto: JoinDto): Program {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        val belongs = belongsRepository.findById(user.id!!)
            .orElseThrow()

        val program = programRepository.findByClassIdAndProgramName(belongs.cId, joinDto.programName)
            ?: throw Exception()

        joinsRepository.save(Joins(
            JoinsId(
                id = user.id!!,
                pId = program.programId!!
            )
        ))

        return program
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