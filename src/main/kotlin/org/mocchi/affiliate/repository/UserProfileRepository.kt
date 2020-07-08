package org.mocchi.affiliate.repository

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.mocchi.affiliate.converter.UserProfileConverter
import org.mocchi.affiliate.model.entity.UserProfile
import org.mocchi.affiliate.model.entity.UserProfileInsert
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class UserProfileRepository(
    private val databaseClient: DatabaseClient,
    private val userProfileConverter: UserProfileConverter
) {

    suspend fun findUserProfileByEmail(email: String): UserProfile? =
        databaseClient.select()
            .from(UserProfile::class.java)
            .matching(Criteria.where("email").`is`(email))
            .fetch()
            .first()
            .awaitFirstOrNull()

    suspend fun insertNewEmail(userProfile: UserProfileInsert): UserProfile =
        databaseClient.insert()
            .into(UserProfileInsert::class.java)
            .using(userProfile)
            .fetch()
            .first()
            .map { userProfileConverter.convert(it) }
            .awaitFirst()

    suspend fun updateImage(userId: Long, image: ByteArray): Int =
        databaseClient.update()
            .table("user_profile")
            .using(Update.update("image", image))
            .matching(Criteria.where("up_id").`is`(userId))
            .fetch()
            .rowsUpdated()
            .awaitFirst()

}
