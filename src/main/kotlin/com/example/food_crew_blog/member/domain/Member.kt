package com.example.food_crew_blog.member.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "members")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(unique = true, nullable = false, length = 100)
    val email: String,
    
    @Column(nullable = false, length = 100)
    val password: String,
    
    @Column(unique = true, nullable = false, length = 20)
    val nickname: String,
    
    @Column(nullable = false, length = 10)
    val name: String,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: MemberRole = MemberRole.USER,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: MemberStatus = MemberStatus.ACTIVE,
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null
) {
    // JPA를 위한 기본 생성자
    constructor() : this(
        id = null,
        email = "",
        password = "",
        nickname = "",
        name = "",
        role = MemberRole.USER,
        status = MemberStatus.ACTIVE,
        createdAt = LocalDateTime.now(),
        updatedAt = null
    )
    
    // equals, hashCode, toString을 위한 메서드들
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as Member
        
        if (id != other.id) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (nickname != other.nickname) return false
        if (name != other.name) return false
        if (role != other.role) return false
        if (status != other.status) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        return result
    }
    
    override fun toString(): String {
        return "Member(id=$id, email='$email', nickname='$nickname', name='$name', role=$role, status=$status, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}

enum class MemberRole {
    USER, ADMIN
}

enum class MemberStatus {
    ACTIVE, INACTIVE, SUSPENDED
}
