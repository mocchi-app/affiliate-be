package org.mocchi.affiliate.controller

import com.stripe.model.oauth.TokenResponse
import org.mocchi.affiliate.controller.exception.ResourceNotFoundException
import org.mocchi.affiliate.model.client.StripeTokenResponse
import org.mocchi.affiliate.model.dto.CompleteStripeConnect
import org.mocchi.affiliate.model.dto.RedirectResponse
import org.mocchi.affiliate.service.StripeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stripe")
class StripeController(
    private val stripeService: StripeService
) {

    @GetMapping("init")
    suspend fun initConnect(): RedirectResponse =
        getEmailFromContext()
            .let { stripeService.initConnect(it) }
            .let { RedirectResponse(it) }

    @PostMapping("complete")
    suspend fun completeConnect(@RequestBody completeStripeConnect: CompleteStripeConnect): StripeTokenResponse =
        getEmailFromContext()
            .let {
                stripeService.completeConnect(
                    email = it,
                    code = completeStripeConnect.code,
                    state = completeStripeConnect.state
                )
            }

}
