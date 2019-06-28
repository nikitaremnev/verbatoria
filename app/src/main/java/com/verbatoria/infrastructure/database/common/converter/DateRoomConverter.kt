package com.verbatoria.infrastructure.database.common.converter

import android.arch.persistence.room.TypeConverter
import ru.tcsbank.agentref.infrastructure.date.formatWithMillisecondsAndZeroOffset
import ru.tcsbank.agentref.infrastructure.date.parseWithMillisecondsAndZeroOffset
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