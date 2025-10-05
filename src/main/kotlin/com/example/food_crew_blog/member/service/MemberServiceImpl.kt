package com.example.food_crew_blog.member.service

import com.example.food_crew_blog.member.dto.MemberRegisterRequest
import com.example.food_crew_blog.member.dto.MemberRegisterResponse
import com.example.food_crew_blog.member.domain.Member
import com.example.food_crew_blog.member.dto.MemberDeleteRequest
import com.example.food_crew_blog.member.dto.MemberDeleteResponse
import com.example.food_crew_blog.member.repository.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository
) : MemberService {
    
    private val logger = LoggerFactory.getLogger(MemberServiceImpl::class.java)
    private val passwordEncoder = BCryptPasswordEncoder()
    
    @Transactional
    override fun registerMember(memberRegisterRequest: MemberRegisterRequest): MemberRegisterResponse {
        logger.info("회원가입 시도 - 이메일: ${memberRegisterRequest.email}, 닉네임: ${memberRegisterRequest.nickname}")
        
        if (isEmailExists(memberRegisterRequest.email)) {
            logger.warn("회원가입 실패 - 이메일 중복: ${memberRegisterRequest.email}")
            throw IllegalArgumentException("이미 존재하는 이메일입니다: ${memberRegisterRequest.email}")
        }
        
        if (isNicknameExists(memberRegisterRequest.nickname)) {
            logger.warn("회원가입 실패 - 닉네임 중복: ${memberRegisterRequest.nickname}")
            throw IllegalArgumentException("이미 존재하는 닉네임입니다: ${memberRegisterRequest.nickname}")
        }
        
        val encodedPassword = passwordEncoder.encode(memberRegisterRequest.password)
        logger.debug("비밀번호 암호화 완료")
        
        val member = Member(
            email = memberRegisterRequest.email,
            password = encodedPassword,
            nickname = memberRegisterRequest.nickname,
            name = memberRegisterRequest.name
        )
        
        val savedMember = memberRepository.save(member)
        logger.info("회원가입 성공 - 회원 ID: ${savedMember.id}, 이메일: ${savedMember.email}")
        
        return MemberRegisterResponse.from(savedMember)
    }
    
    @Transactional(readOnly = true)
    override fun isEmailExists(email: String): Boolean {
        logger.debug("이메일 중복 확인: $email")
        return memberRepository.existsByEmail(email)
    }
    
    @Transactional(readOnly = true)
    override fun isNicknameExists(nickname: String): Boolean {
        logger.debug("닉네임 중복 확인: $nickname")
        return memberRepository.existsByNickname(nickname)
    }

    @Transactional
    override fun deleteMember(memberDeleteRequest: MemberDeleteRequest): MemberDeleteResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        val currentUserEmail = authentication.name

        logger.info("회원 탈퇴 요청 - 사용자: $currentUserEmail")

        val member = memberRepository.findByEmail(currentUserEmail)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다.") }

        if (!passwordEncoder.matches(memberDeleteRequest.password, member.getPassword())) {
            logger.warn("회원 탈퇴 실패 - 비밀번호 불일치: $currentUserEmail")
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }
        memberRepository.delete(member)

        logger.info("회원 탈퇴 완료 - 사용자: $currentUserEmail")
        return MemberDeleteResponse()
    }
}
