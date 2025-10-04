package com.example.food_crew_blog.member.service

import com.example.food_crew_blog.member.dto.MemberRegisterInfo
import com.example.food_crew_blog.member.domain.Member
import com.example.food_crew_blog.member.repository.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository
) : MemberService {
    
    private val logger = LoggerFactory.getLogger(MemberServiceImpl::class.java)
    private val passwordEncoder = BCryptPasswordEncoder()
    
    @Transactional
    override fun registerMember(memberRegisterInfo: MemberRegisterInfo): Member {
        logger.info("회원가입 시도 - 이메일: ${memberRegisterInfo.email}, 닉네임: ${memberRegisterInfo.nickname}")
        
        // 이메일 중복 확인
        if (isEmailExists(memberRegisterInfo.email)) {
            logger.warn("회원가입 실패 - 이메일 중복: ${memberRegisterInfo.email}")
            throw IllegalArgumentException("이미 존재하는 이메일입니다: ${memberRegisterInfo.email}")
        }
        
        // 닉네임 중복 확인
        if (isNicknameExists(memberRegisterInfo.nickname)) {
            logger.warn("회원가입 실패 - 닉네임 중복: ${memberRegisterInfo.nickname}")
            throw IllegalArgumentException("이미 존재하는 닉네임입니다: ${memberRegisterInfo.nickname}")
        }
        
        // 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(memberRegisterInfo.password)
        logger.debug("비밀번호 암호화 완료")
        
        // 회원 엔티티 생성
        val member = Member(
            email = memberRegisterInfo.email,
            password = encodedPassword,
            nickname = memberRegisterInfo.nickname,
            name = memberRegisterInfo.name
        )
        
        // 회원 저장
        val savedMember = memberRepository.save(member)
        logger.info("회원가입 성공 - 회원 ID: ${savedMember.id}, 이메일: ${savedMember.email}")
        
        return savedMember
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
}
