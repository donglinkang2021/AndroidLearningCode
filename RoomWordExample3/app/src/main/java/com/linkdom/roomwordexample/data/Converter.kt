package com.linkdom.roomwordexample.data

import androidx.room.TypeConverter
import com.linkdom.roomwordexample.data.models.Priority

class Converter  {
    @TypeConverter
    fun fromPriorty(priority: Priority):String{
        return priority.name
    }
    @TypeConverter
    fun toPriorty(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}