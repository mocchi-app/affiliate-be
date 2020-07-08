package org.mocchi.affiliate.repository

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mocchi.affiliate.AbstractIntegrationTest
import org.mocchi.affiliate.model.entity.UserProfile
import org.mocchi.affiliate.model.entity.UserProfileInsert
import org.springframework.beans.factory.annotation.Autowired

internal class UserProfileRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var userProfileRepository: UserProfileRepository

    @Test
    fun `should insert new user profile`() {
        runBlocking {
            val email = "email"
            val actual = userProfileRepository.insertNewEmail(
                UserProfileInsert(
                    email = email
                )
            )
            val expected = UserProfile(1, email, null)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
        }
    }

    @Test
    fun `should find user profile by email`() {
        runBlocking {
            val email = "email"
            userProfileRepository.insertNewEmail(
                UserProfileInsert(
                    email = email
                )
            )
            val expected = UserProfile(1, email, null)
            val actual = userProfileRepository.findUserProfileByEmail(email)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
        }
    }

    @Test
    fun `should not find user profile by email as it doesn't exist`() {
        runBlocking {
            val email = "email"
            val actual = userProfileRepository.findUserProfileByEmail(email)
            assertThat(actual).isNull()
        }
    }

    @Test
    fun `should update image for user profile`() {
        runBlocking {
            val email = "email"
            val inserted = userProfileRepository.insertNewEmail(
                UserProfileInsert(email = email)
            )
            val image = ByteArray(1000)
            val expected = UserProfile(inserted.id, email, image)

            val actual = userProfileRepository.updateImage(inserted.id, image)
            assertThat(actual).isEqualTo(1)

            val actualAfterUpdate = userProfileRepository.findUserProfileByEmail(email)
            assertThat(actualAfterUpdate)
                .isEqualToIgnoringGivenFields(expected, "image")
                .hasNoNullFieldsOrProperties()
        }
    }

    @Test
    fun `should not update image for user profile as it doesn't exist`() {
        runBlocking {
            val image = ByteArray(1000)

            val actual = userProfileRepository.updateImage(-1, image)
            assertThat(actual).isEqualTo(0)
        }
    }
}
