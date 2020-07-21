package org.mocchi.affiliate.model.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("user_state")
data class UserState(
    @Column("us_id")
    val id: Long,
    @Column("us_up_id")
    val userId: Long
)

@Table("user_state")
data class InsertUserState(
    @Column("us_up_id")
    val userId: Long
)
