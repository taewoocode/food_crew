package com.example.food_crew_blog.member.dto

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class MemberDeleteRequest(
    @field:NotBlank(message = "비밀번호 입력은 필수입니다.")
    val password: String
)

data class MemberDeleteResponse(
    val message: String = "회원 탈퇴가 완료되었습니다.",
    val deletedAt: LocalDateTime = LocalDateTime.now()
)