package org.mocchi.affiliate.controller

import kotlinx.coroutines.reactive.awaitFirst
import org.mocchi.affiliate.service.ImageService
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/influencer")
class ProfileController(
    private val imageService: ImageService
) {

    @PutMapping(
        "image",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.IMAGE_JPEG_VALUE]
    )
    suspend fun submitProfile(@RequestPart("file") file: Mono<FilePart>) =
        ReactiveSecurityContextHolder.getContext()
            .awaitFirst()
            .authentication
            .principal
            .takeIf { it is Jwt }
            ?.let { it as Jwt }
            ?.let { it.claims["key"] as String }
            ?.let {
                imageService.storeImage(it, file.awaitFirst())
            }

    @GetMapping("image", produces = [MediaType.IMAGE_JPEG_VALUE])
    suspend fun getProfileImage() =
        ReactiveSecurityContextHolder.getContext()
            .awaitFirst()
            .authentication
            .principal
            .takeIf { it is Jwt }
            ?.let { it as Jwt }
            ?.let { it.claims["key"] as String }
            ?.let { imageService.getImage(it) }
}
