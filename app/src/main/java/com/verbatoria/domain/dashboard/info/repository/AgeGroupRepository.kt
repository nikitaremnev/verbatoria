package com.verbatoria.domain.dashboard.info.repository

import com.verbatoria.domain.dashboard.info.model.AgeGroupEntity

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
