package com.verbatoria.infrastructure.conversion

import java.util.*

/**
 * @author n.remnev
 */

abstract class DtoConverter {

    protected fun generateId() = UUID.randomUUID().toString()

}