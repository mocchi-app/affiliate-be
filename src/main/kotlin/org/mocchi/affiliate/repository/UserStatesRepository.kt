package org.mocchi.affiliate.repository

import org.mocchi.affiliate.converter.UserStateConverter
import org.mocchi.affiliate.model.entity.InsertUserState
import org.mocchi.affiliate.model.entity.UserState
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class UserStatesRepository(
    private val databaseClient: DatabaseClient,
    private val userStateConverter: UserStateConverter
) {

    suspend fun saveNewCode(userId: Long): UserState =
        databaseClient.insert()
            .into(InsertUserState::class.java)
            .using(InsertUserState(userId))
            .fetch()
            .awaitFirst()
            .let { userStateConverter.convert(it) }

    suspend fun getById(id: Long): UserState? =
        databaseClient.select()
            .from(UserState::class.java)
            .matching(
                Criteria.where("us_id").`is`(id)
            )
            .fetch()
            .awaitFirstOrNull()
}
