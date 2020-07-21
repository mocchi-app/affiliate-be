package org.mocchi.affiliate.model.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("user_profile")
data class UserProfile(
    @Column("up_id")
    val id: Long,
    @Column("email")
    val email: String,
    @Column("about")
    val about: String? = null,
    @Column("location")
    val location: String? = null,
    @Column("image")
    val image: ByteArray?,
    @Column("stripe_account_id")
    val stripeAccountId: String?
)

@Table("user_profile")
data class UserProfileInsert(
    @Column("email")
    val email: String,
    @Column("about")
    val about: String? = null,
    @Column("location")
    val location: String? = null,
    @Column("image")
    val image: ByteArray? = null,
    @Column("stripe_account_id")
    val stripeAccountId: String? = null
)
