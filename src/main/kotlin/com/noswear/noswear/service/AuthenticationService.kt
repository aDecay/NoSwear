package com.noswear.noswear.service

import com.noswear.noswear.domain.*
import com.noswear.noswear.dto.StudentDto
import com.noswear.noswear.dto.TeacherDto
import com.noswear.noswear.repository.*
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    val teacherRepository: TeacherRepository,
    val studentRepository: StudentRepository,
    val schoolRepository: SchoolRepository,
    val worksRepository: WorksRepository,
    val classRepository: ClassRepository,
    val withinRepository: WithinRepository,
    val teachesRepository: TeachesRepository
) {
    fun teacherRegister(teacherDto: TeacherDto): Teacher {
        val teacherEntity = Teacher(
            email = teacherDto.email,
            password = teacherDto.password,
            name = teacherDto.name
        )

        val teacherResult = teacherRepository.save(teacherEntity)

        lateinit var schoolId: String
        if (teacherDto.create) {
            val schoolEntity = School(
                schoolName = teacherDto.schoolName!!,
                startDate = teacherDto.startDate!!
            )

            val schoolResult = schoolRepository.save(schoolEntity)

            schoolId = schoolResult.schoolId!!
        } else {
            schoolId = teacherDto.schoolId!!
        }

        val worksEntity = Works(
            id = teacherResult.id!!,
            sId = schoolId,
            isManager = teacherDto.create
        )

        worksRepository.save(worksEntity)

        val classEntity = Class(
            className = teacherDto.className
        )

        val classResult = classRepository.save(classEntity)

        val withinEntity = Within(
            cId = classResult.classId!!,
            sId = schoolId
        )

        withinRepository.save(withinEntity)

        val teachesEntity = Teaches(
            id = teacherResult.id!!,
            cId = classResult.classId!!
        )

        teachesRepository.save(teachesEntity)

        return teacherResult
    }

    fun studentRegister(studentDto: StudentDto): Student {
        val student = Student(
            email = studentDto.email,
            password = studentDto.password,
            name = studentDto.name
        )

        return studentRepository.save(student)
    }
}