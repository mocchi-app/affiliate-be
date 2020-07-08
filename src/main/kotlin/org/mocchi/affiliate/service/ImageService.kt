package org.mocchi.affiliate.service

import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service

@Service
class ImageService(
    private val userProfileService: UserProfileService
) {

    suspend fun storeImage(email: String, file: FilePart): ByteArray =
        DataBufferUtils.join(file.content())
            .map { it.asByteBuffer().array() }
            .awaitFirst()
            .let { image ->
                userProfileService.insertOrGetUser(email)
                    .let {
                        userProfileService.updateImage(it.id, image)
                    }
                image
            }

    suspend fun getImage(email: String): ByteArray? =
        userProfileService.getUser(email)?.image
}
