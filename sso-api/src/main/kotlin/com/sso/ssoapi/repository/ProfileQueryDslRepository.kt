package com.sso.ssoapi.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sso.ssoapi.controller.dto.profile.ProfileUpdateRequest
import com.sso.ssoapi.dto.QProfileDetail
import com.sso.ssoapi.dto.ProfileDetail
import com.sso.ssoapi.dto.QPostDetail
import com.sso.ssoapi.dto.PostDetail
import com.sso.ssoapi.entity.QProfile.profile
import com.sso.ssoapi.entity.QPost.post
import com.sso.ssoapi.entity.QUser.user
import org.springframework.stereotype.Repository

@Repository
class ProfileQueryDslRepository(
    val jpaQueryFactory: JPAQueryFactory
) {
    fun findProfileDetail(UserId: Long): ProfileDetail? {
        return jpaQueryFactory.selectFrom(user)
            .leftJoin(profile).on(profile.userId.eq(user.id))
            .select(
                QProfileDetail(
                    profile.id,
                    user.id,
                    user.nickname,
                    profile.url,
                )
            )
            .where(user.id.eq(UserId))
            .fetchFirst()
    }

    fun findPostListByUserId(UserId: Long): List<PostDetail> {
        return jpaQueryFactory.selectFrom(post)
            .select(
                QPostDetail(
                    post.id,
                    post.userId,
                    post.content,
                    post.imageUrl,
                )
            )
            .where(post.userId.eq(UserId))
            .fetch()
    }

    fun updateProfile(profileUpdateRequest: ProfileUpdateRequest, UserId: Long) {
        jpaQueryFactory.update(profile)
                .set(profile.career, profileUpdateRequest.career)
                .set(profile.elementrySchool, profileUpdateRequest.elementrySchool)
                .set(profile.middleSchool, profileUpdateRequest.middleSchool)
                .set(profile.highSchool, profileUpdateRequest.highSchool)
                .set(profile.university, profileUpdateRequest.university)
                .set(profile.originPlace, profileUpdateRequest.originPlace)
                .set(profile.residence, profileUpdateRequest.residence)
                .where(profile.userId.eq(UserId))
                .execute()
    }
}