package com.verbatoria.infrastructure.database.common.converter

import android.arch.persistence.room.TypeConverter
import com.verbatoria.infrastructure.extensions.formatWithMillisecondsAndZeroOffset
import com.verbatoria.infrastructure.extensions.parseWithMillisecondsAndZeroOffset
import java.util.*

/**
 * @author n.remnev
 */

class DateRoomConverter {

    @TypeConverter
    fun stringToDate(string: String?): Date? =
        string?.parseWithMillisecondsAndZeroOffset()

    @TypeConverter
    fun dateToString(date: Date?): String? =
        date?.formatWithMillisecondsAndZeroOffset()

}