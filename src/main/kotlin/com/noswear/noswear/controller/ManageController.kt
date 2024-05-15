package com.noswear.noswear.controller

import com.noswear.noswear.domain.Program
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
    @PostMapping("/make-program")
    @PreAuthorize("hasAnyRole('MANAGER', 'TEACHER')")
    fun createProgram(@RequestBody programDto: ProgramDto): ResponseEntity<Program> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val teacher = manageService.createProgram(name, programDto)
        return ResponseEntity.ok(teacher)
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