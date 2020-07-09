package org.mocchi.affiliate.service

import org.mocchi.affiliate.model.dto.ProfileInfo
import org.mocchi.affiliate.model.entity.UserProfile
import org.mocchi.affiliate.model.entity.UserProfileInsert
import org.mocchi.affiliate.repository.UserProfileRepository
import org.springframework.stereotype.Service

@Service
class UserProfileService(
    private val userProfileRepository: UserProfileRepository
) {

    suspend fun getUser(email: String): UserProfile? =
        userProfileRepository.findUserProfileByEmail(email)

    suspend fun insertOrGetUser(email: String): UserProfile =
        userProfileRepository.findUserProfileByEmail(email)
            ?: userProfileRepository.insertNewEmail(
                UserProfileInsert(
                    email = email
                )
            )

    suspend fun updateUserProfile(email: String, profileInfo: ProfileInfo) =
        insertOrGetUser(email)
            .let {
                userProfileRepository.updateProfile(
                    it.id,
                    UserProfileInsert(
                        email = email,
                        about = profileInfo.about,
                        location = profileInfo.location
                    )
                )
            }

    suspend fun updateImage(userId: Long, image: ByteArray) =
        userProfileRepository.updateImage(userId, image)
}
