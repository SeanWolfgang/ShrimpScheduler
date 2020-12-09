package com.example.shrimpscheduler;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeTypeCoverters {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static OffsetDateTime fromTimestamp(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return OffsetDateTime.parse(dateString);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static String dateToTimestamp(OffsetDateTime date) {
        if (date == null) {
            return null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            return formatter.format(date);
        }
    }

}
