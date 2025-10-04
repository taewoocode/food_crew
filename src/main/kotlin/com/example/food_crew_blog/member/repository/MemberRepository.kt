package com.example.food_crew_blog.member.repository

import com.example.food_crew_blog.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    
    fun findByEmail(email: String): Optional<Member>
    
    fun findByNickname(nickname: String): Optional<Member>
    
    fun existsByEmail(email: String): Boolean
    
    fun existsByNickname(nickname: String): Boolean
}
