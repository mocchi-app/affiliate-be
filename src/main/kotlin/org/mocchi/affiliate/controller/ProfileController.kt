package org.mocchi.affiliate.controller

import kotlinx.coroutines.reactive.awaitFirst
import org.mocchi.affiliate.controller.exception.ResourceNotFoundException
import org.mocchi.affiliate.model.controller.ImageResponse
import org.mocchi.affiliate.model.dto.ProfileInfo
import org.mocchi.affiliate.model.dto.UserProfileResponse
import org.mocchi.affiliate.service.ImageService
import org.mocchi.affiliate.service.UserProfileService
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/influencer")
class ProfileController(
    private val imageService: ImageService,
    private val profileService: UserProfileService
) {

    @PutMapping(
        "image",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.IMAGE_JPEG_VALUE]
    )
    suspend fun submitProfile(@RequestPart("file") file: Mono<FilePart>): ImageResponse =
        ImageResponse(imageService.storeImage(getEmailFromContext(), file.awaitFirst()))

    @GetMapping("image", produces = [MediaType.IMAGE_JPEG_VALUE])
    suspend fun getProfileImage(): ImageResponse =
        getEmailFromContext()
            .let { imageService.getImage(it) }
            .let { ImageResponse(it) }

    @PutMapping(
        "profile",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun putProfileInfo(@RequestBody profileInfo: ProfileInfo): Int =
        getEmailFromContext()
            .let { profileService.updateUserProfile(it, profileInfo) }


    @GetMapping(
        "profile",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun getProfile(): UserProfileResponse =
        getEmailFromContext()
            .let { profileService.insertOrGetUser(it) }
            .let {
                UserProfileResponse(
                    email = it.email,
                    about = it.about,
                    location = it.location
                )
            }
}
