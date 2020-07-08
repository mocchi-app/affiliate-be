package org.mocchi.affiliate.model.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("user_profile")
data class UserProfile(
    @Column("up_id")
    val id: Long,
    @Column("email")
    val email: String,
    @Column("image")
    val image: ByteArray?
)

@Table("user_profile")
data class UserProfileInsert(
    @Column("email")
    val email: String,
    @Column("image")
    val image: ByteArray? = null
)
