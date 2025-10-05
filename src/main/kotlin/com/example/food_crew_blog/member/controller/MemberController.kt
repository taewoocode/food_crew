package com.example.food_crew_blog.member.controller

import com.example.food_crew_blog.member.dto.MemberRegisterRequest
import com.example.food_crew_blog.member.dto.MemberRegisterResponse
import com.example.food_crew_blog.member.dto.MemberDeleteRequest
import com.example.food_crew_blog.member.dto.MemberDeleteResponse
import com.example.food_crew_blog.member.domain.Member
import com.example.food_crew_blog.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/members")
@Tag(name = "Member", description = "회원 관리 API")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/register")
    @Operation(
        summary = "회원가입",
        description = "새로운 회원을 등록합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "회원가입 성공",
                content = [Content(schema = Schema(implementation = MemberRegisterResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 데이터"
            ),
            ApiResponse(
                responseCode = "409",
                description = "이메일 또는 닉네임 중복"
            )
        ]
    )
    fun registerMember(
        @Parameter(description = "회원가입 정보", required = true)
        @Valid @RequestBody memberRegisterRequest: MemberRegisterRequest
    ): ResponseEntity<MemberRegisterResponse> {
        val response = memberService.registerMember(memberRegisterRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/check-email")
    @Operation(
        summary = "이메일 중복 확인",
        description = "이메일 중복 여부를 확인합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "중복 확인 완료"
            )
        ]
    )
    fun checkEmailExists(
        @Parameter(description = "확인할 이메일", required = true)
        @RequestParam email: String
    ): ResponseEntity<Map<String, Any>> {
        val exists = memberService.isEmailExists(email)
        return ResponseEntity.ok(mapOf(
            "email" to email,
            "exists" to exists
        ))
    }

    @GetMapping("/check-nickname")
    @Operation(
        summary = "닉네임 중복 확인",
        description = "닉네임 중복 여부를 확인합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "중복 확인 완료"
            )
        ]
    )
    fun checkNicknameExists(
        @Parameter(description = "확인할 닉네임", required = true)
        @RequestParam nickname: String
    ): ResponseEntity<Map<String, Any>> {
        val exists = memberService.isNicknameExists(nickname)
        return ResponseEntity.ok(mapOf(
            "nickname" to nickname,
            "exists" to exists
        ))
    }


    @DeleteMapping("/delete")
    @Operation(
        summary = "회원 탈퇴",
        description = "현재 로그인한 회원을 탈퇴합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "회원 탈퇴 성공",
                content = [Content(schema = Schema(implementation = MemberDeleteResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 데이터"
            ),
            ApiResponse(
                responseCode = "401",
                description = "인증되지 않은 사용자"
            ),
            ApiResponse(
                responseCode = "403",
                description = "비밀번호 불일치"
            )
        ]
    )
    fun deleteMember(
        @Parameter(description = "탈퇴 요청 정보", required = true)
        @Valid @RequestBody memberDeleteRequest: MemberDeleteRequest
    ): ResponseEntity<MemberDeleteResponse> {
        val response = memberService.deleteMember(memberDeleteRequest)
        return ResponseEntity.ok(response)
    }
}
