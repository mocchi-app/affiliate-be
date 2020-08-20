package org.mocchi.affiliate.service

import org.mocchi.affiliate.client.StripeClient
import org.mocchi.affiliate.configuration.StripeProperties
import org.mocchi.affiliate.controller.exception.ResourceNotFoundException
import org.mocchi.affiliate.model.client.StripeTokenResponse
import org.mocchi.affiliate.repository.UserProfileRepository
import org.mocchi.affiliate.repository.UserStatesRepository
import org.springframework.stereotype.Service

@Service
class StripeService(
    private val stripeProperties: StripeProperties,
    private val userStatesRepository: UserStatesRepository,
    private val userProfileRepository: UserProfileRepository,
    private val stripeClient: StripeClient
) {

    suspend fun initConnect(email: String): String =
        userProfileRepository.findUserProfileByEmail(email)
            ?.let { userStatesRepository.saveNewCode(userId = it.id) }
            ?.let {
                "https://connect.stripe.com/express/oauth/authorize" +
                    "?client_id=${stripeProperties.clientId}" +
                    "&state=${it.id}" +
                    "&stripe_user[email]=$email" +
                    "&redirect_uri=http://64.225.118.43:7071/stripe/complete"
            }
            ?: throw ResourceNotFoundException("user doesn't exist in database")

    suspend fun completeConnect(email: String, code: String, state: String): StripeTokenResponse =
        userProfileRepository.findUserProfileByEmail(email)
            ?.takeIf {
                userStatesRepository.getById(state.toLong())?.userId == it.id
            }
            ?.let { userProfile ->
                stripeClient.getToken(code)
                    .takeIf {
                        userProfileRepository.updateStripeAccountId(userProfile.id, it.stripeUserId) == 1
                    }
            }
            ?: throw ResourceNotFoundException("user or code doesn't exist in database")
}
