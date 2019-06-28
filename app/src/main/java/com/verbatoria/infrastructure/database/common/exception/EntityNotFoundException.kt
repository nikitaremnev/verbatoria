package com.verbatoria.infrastructure.database.common.exception

/**
 * @author n.remnev
 */

class EntityNotFoundException(id: String, target: Class<*>) : RuntimeException("${target.simpleName} with id=$id not found")