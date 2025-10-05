package com.example.food_crew_blog.member.service

import com.example.food_crew_blog.member.dto.MemberRegisterRequest
import com.example.food_crew_blog.member.dto.MemberRegisterResponse
import com.example.food_crew_blog.member.dto.MemberDeleteRequest
import com.example.food_crew_blog.member.dto.MemberDeleteResponse

interface MemberService {
    
    /**
     * 회원가입
     * @param memberRegisterRequest 회원가입 정보
     * @return 생성된 회원 정보 (비밀번호 제외)
     */
    fun registerMember(memberRegisterRequest: MemberRegisterRequest): MemberRegisterResponse
    
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
    
    /**
     * 회원 탈퇴
     * @param memberDeleteRequest 탈퇴 요청 정보
     * @return 탈퇴 응답 정보
     */
    fun deleteMember(memberDeleteRequest: MemberDeleteRequest): MemberDeleteResponse
    

    /**
     * 회원탈퇴
     *  @param memberRegisterRequest 회원탈퇴
     *  @return 생성된 회원 정보 (비밀번호 제외)
     */
    fun deleteMember(memberDeleteRequest: MemberDeleteRequest): MemberDeleteResponse
}
