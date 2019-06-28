package com.verbatoria.infrastructure.database.common.dao

/**
 * @author n.remnev
 */

interface Entity {

    companion object Fields {
        const val FIELD_ID = "id"
    }

    var id: String?

}
