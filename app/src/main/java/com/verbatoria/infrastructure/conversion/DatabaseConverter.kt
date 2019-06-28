package com.verbatoria.infrastructure.conversion

import java.util.*

/**
 * @author n.remnev
 */

abstract class DatabaseConverter<Entity, Domain> {

    abstract fun toEntity(domain: Domain): Entity

    abstract fun toDomain(entity: Entity): Domain

    protected fun generateId() = UUID.randomUUID().toString()

    protected fun generateId(value: Long) =
        UUID.nameUUIDFromBytes(byteArrayOf(value.toByte())).toString()

    protected fun generateId(value: String) = UUID.nameUUIDFromBytes(value.toByteArray()).toString()

}