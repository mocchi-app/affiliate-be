package org.mocchi.affiliate.controller

import kotlinx.coroutines.reactive.awaitFirst
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
    suspend fun submitProfile(@RequestPart("file") file: Mono<FilePart>) =
        getEmailFromContext()
            ?.let {
                imageService.storeImage(it, file.awaitFirst())
            }

    @GetMapping("image", produces = [MediaType.IMAGE_JPEG_VALUE])
    suspend fun getProfileImage() =
        getEmailFromContext()
            ?.let { imageService.getImage(it) }

    @PutMapping(
        "profile",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun putProfileInfo(@RequestBody profileInfo: ProfileInfo) =
        getEmailFromContext()
            ?.let { profileService.updateUserProfile(it, profileInfo) }

    @GetMapping(
        "profile",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun getProfile() =
        getEmailFromContext()
            ?.let { profileService.insertOrGetUser(it) }
            ?.let {
                UserProfileResponse(
                    email = it.email,
                    about = it.about,
                    location = it.location
                )
            }

    private suspend fun getEmailFromContext() =
        ReactiveSecurityContextHolder.getContext()
            .awaitFirst()
            .authentication
            .principal
            .takeIf { it is Jwt }
            ?.let { it as Jwt }
            ?.let { it.claims["name"] as String }
}
