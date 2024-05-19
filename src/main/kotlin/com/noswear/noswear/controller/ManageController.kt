package com.noswear.noswear.controller

import com.noswear.noswear.dto.JoinDto
import com.noswear.noswear.dto.ProgramDto
import com.noswear.noswear.service.ManageService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/manage")
@Controller
class ManageController(
    private val manageService: ManageService
) {
    @PostMapping("/program/create")
    @PreAuthorize("hasAnyRole('MANAGER', 'TEACHER')")
    fun createProgram(@RequestBody programDto: ProgramDto): ResponseEntity<ProgramDto> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val program = manageService.createProgram(name, programDto)
        return ResponseEntity.ok(ProgramDto.of(program))
    }

    @GetMapping("/program/get/all")
    @PreAuthorize("isAuthenticated()")
    fun getAllPrograms(): ResponseEntity<List<ProgramDto>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val programs = mutableListOf<ProgramDto>()
        manageService.getALlPrograms(name).map { program ->
            programs.add(ProgramDto.of(program))
        }
        return ResponseEntity.ok(programs)
    }

    @GetMapping("/program/get/me")
    @PreAuthorize("hasRole('STUDENT')")
    fun getMyProgram(): ResponseEntity<List<ProgramDto>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val programs = mutableListOf<ProgramDto>()
        manageService.getMyPrograms(name).map { program ->
            programs.add(ProgramDto.of(program))
        }
        return ResponseEntity.ok(programs)
    }

    @PostMapping("/program/join")
    @PreAuthorize("hasRole('STUDENT')")
    fun joinProgram(@RequestBody joinDto: JoinDto): ResponseEntity<ProgramDto> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val program = manageService.joinProgram(name, joinDto)
        return ResponseEntity.ok(ProgramDto.of(program))
    }

    @GetMapping("/code/school")
    @PreAuthorize("hasAnyRole('MANAGER', 'TEACHER')")
    fun getSchoolCode(): ResponseEntity<String> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val schoolId = manageService.getSchoolCode(name)
        return ResponseEntity.ok(schoolId)
    }

    @GetMapping("/code/class")
    @PreAuthorize("hasAnyRole('MANAGER', 'TEACHER')")
    fun getClassCode(): ResponseEntity<String> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val classId = manageService.getClassCode(name)
        return ResponseEntity.ok(classId)
    }
}