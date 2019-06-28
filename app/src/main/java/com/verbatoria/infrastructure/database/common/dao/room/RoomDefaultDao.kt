package com.verbatoria.infrastructure.database.common.dao.room

import android.arch.persistence.db.SupportSQLiteQuery
import android.arch.persistence.room.*

/**
 * @author n.remnev
 */

interface RoomDefaultDao<Entity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: Iterable<Entity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(entity: Entity) : Int

    @Delete
    fun delete(entity: Entity)

    @Delete
    fun delete(entity: Iterable<Entity>)

    @RawQuery
    fun findAll(query: SupportSQLiteQuery): List<Entity>

    @RawQuery
    fun dropTable(query: SupportSQLiteQuery): Int

    @RawQuery
    fun findById(query: SupportSQLiteQuery): List<Entity>

}