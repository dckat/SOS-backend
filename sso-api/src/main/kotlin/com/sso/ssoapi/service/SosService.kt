package com.sso.ssoapi.service

import com.sso.ssoapi.dto.ApplyDetail
import com.sso.ssoapi.dto.MySosDetail
import com.sso.ssoapi.dto.SosDetail
import com.sso.ssoapi.dto.SosUserApplyDetail
import com.sso.ssoapi.entity.SosUserApply
import com.sso.ssoapi.entity.SosUserApplyStatus
import com.sso.ssoapi.repository.SosQueryDslRepository
import com.sso.ssoapi.repository.SosUserApplyQueryDslRepository
import com.sso.ssoapi.repository.SosUserApplyRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class SosService(
    private val sosUserApplyQueryDslRepository: SosUserApplyQueryDslRepository,
    private val sosQueryDslRepository: SosQueryDslRepository,
    private val sosUserApplyRepository: SosUserApplyRepository
) {
    @Transactional(readOnly = true)
    fun findSosList(): List<SosDetail> {
        return sosQueryDslRepository.findSosList()
    }

    @Transactional(readOnly = true)
    fun findSosDetail(sosId: Long): SosDetail? {
        return sosQueryDslRepository.findSosDetail(sosId)
    }

    @Transactional(readOnly = true)
    fun findApplyListByUserId(id: Long): List<SosUserApplyDetail> {
        return sosQueryDslRepository.findApplyListByUserId(id)
    }

    @Transactional(readOnly = true)
    fun findSosListByUserId(id: Long): List<MySosDetail> {
        return sosQueryDslRepository.findSosListByUserId(id)
    }

    @Transactional(readOnly = true)
    fun findUserApplyListBySosId(id: Long): List<ApplyDetail> {
        return sosUserApplyQueryDslRepository.findUserApplyListBySosId(id)
    }

    @Transactional
    fun acceptSos(sosId: Long, userId: Long) {
        sosQueryDslRepository.acceptSos(sosId, userId)
    }

    @Transactional
    fun refuseSos(sosId: Long, userId: Long) {
        sosQueryDslRepository.refuseSos(sosId, userId)
    }

    @Transactional
    fun cancelSosUserApply(sosId: Long, userId: Long) {
        sosQueryDslRepository.cancelSosUserApply(sosId, userId)
    }

    @Transactional
    fun insertUserSosApply(userId: Long, sosId: Long, status: SosUserApplyStatus = SosUserApplyStatus.WAIT) {
        val existApply =
            sosUserApplyRepository.findByUserIdAndSosIdAndStatusNot(userId, sosId, SosUserApplyStatus.CANCEL)
        if (existApply != null) throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "?????? ??????????????? ??????????????????."
        )
        sosUserApplyRepository.save(
            SosUserApply(
                userId,
                sosId,
                status
            )
        )
    }
}