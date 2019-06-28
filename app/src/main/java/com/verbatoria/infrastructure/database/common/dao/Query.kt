package com.verbatoria.infrastructure.database.common.dao

import java.util.*

/**
 * @author n.remnev
 */

interface Query<T : Entity> {

    fun equalTo(field: String, value: String): Query<T>

    fun equalTo(field: String, value: Boolean): Query<T>

    fun equalTo(field: String, value: Long): Query<T>

    fun equalTo(field: String, value: Int): Query<T>

    fun equalTo(field: String, value: Date): Query<T>

    fun between(field: String, from: Date, to: Date): Query<T>

    fun before(field: String, date: Date): Query<T>

    fun after(field: String, date: Date): Query<T>

    fun lessThan(field: String, value: Long): Query<T>

    fun greaterThan(field: String, value: Long): Query<T>

    fun greaterThan(field: String, value: Int): Query<T>

    fun or(): Query<T>

    fun isEmpty(field: String): Query<T>

    fun isNotEmpty(field: String): Query<T>

    fun isNull(field: String): Query<T>

    fun isNotNull(field: String): Query<T>

    fun contains(field: String, values: Array<String>): Query<T>

    fun contains(field: String, values: Array<Int>): Query<T>

    fun not(): Query<T>

    fun inGroup(block: Query<T>.() -> Unit): Query<T>

    fun find(): List<T>

    fun remove(): Boolean

    fun findFirst(): T?

    fun limit(offset: Int, count: Int): Query<T>

    fun offset(offset: Int): Query<T>

    fun count(count: Int): Query<T>

    fun findSortedDescending(field: String): List<T>

    fun findSortedAscending(field: String): List<T>

    fun like(field: String, value: String, sensitive: Boolean = false): Query<T>

    fun size(): Long

}