package org.mocchi.affiliate.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFondException(ex: ResourceNotFoundException): ResponseEntity<String> =
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.message)
}