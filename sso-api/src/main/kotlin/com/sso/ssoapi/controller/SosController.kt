package com.sso.ssoapi.controller

import com.sso.ssoapi.controller.dto.sos.SosDetailResponse
import com.sso.ssoapi.controller.dto.sos.SosListResponse
import com.sso.ssoapi.dto.ApiResponse
import com.sso.ssoapi.service.SosService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sos")
class SosController(
    private val sosService: SosService
) {
    @GetMapping("/list")
    fun findSosList(): ApiResponse {
        val sosList = sosService.findSosList()
        return ApiResponse(SosListResponse(sosList))
    }

    @GetMapping("/{id}")
    fun findSosDetail(@PathVariable id: Long): ApiResponse {
        val sos = sosService.findSosDetail(id)
        return ApiResponse(SosDetailResponse(sos))
    }
}