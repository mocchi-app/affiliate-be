package org.mocchi.affiliate.controller.exception

import java.lang.RuntimeException

class ResourceNotFoundException(message: String): RuntimeException(message)