package com.verbatoria.domain.dashboard.info

import com.verbatoria.infrastructure.database.entity.age_group.AgeGroupDao
import com.verbatoria.infrastructure.database.entity.age_group.AgeGroupEntity

/**
 * @author n.remnev
 */

interface AgeGroupRepository {

    fun saveAll(entities: Collection<AgeGroupEntity>)

    fun deleteAll()

    fun isAgeAvailableForArchimedes(age: Int): Boolean

}

class AgeGroupRepositoryImpl(
    private val dao: AgeGroupDao
) : AgeGroupRepository {

    override fun saveAll(entities: Collection<AgeGroupEntity>) {
        dao.save(entities)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }

    override fun isAgeAvailableForArchimedes(age: Int): Boolean =
        dao.isAgeAvailableForArchimedes(age)

}
