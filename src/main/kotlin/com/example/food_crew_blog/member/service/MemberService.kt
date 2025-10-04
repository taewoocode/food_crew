package com.example.food_crew_blog.member.service

import com.example.food_crew_blog.member.dto.MemberRegisterInfo
import com.example.food_crew_blog.member.domain.Member

interface MemberService {
    
    /**
     * 회원가입
     * @param memberRegisterInfo 회원가입 정보
     * @return 생성된 회원 정보
     */
    fun registerMember(memberRegisterInfo: MemberRegisterInfo): Member
    
    /**
     * 이메일 중복 확인
     * @param email 확인할 이메일
     * @return 중복 여부
     */
    fun isEmailExists(email: String): Boolean
    
    /**
     * 닉네임 중복 확인
     * @param nickname 확인할 닉네임
     * @return 중복 여부
     */
    fun isNicknameExists(nickname: String): Boolean
}
