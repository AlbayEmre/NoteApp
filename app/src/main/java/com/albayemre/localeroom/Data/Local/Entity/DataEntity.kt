package com.albayemre.localeroom.Data.Local.Entity

import android.icu.util.Calendar
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

@Entity(tableName = "notes_table")
data class DataEntity(

    @PrimaryKey
    @ColumnInfo("note_ID")
    val noteID: String = UUID.randomUUID().toString(),

    @ColumnInfo("note_title")
    val noteTitle: String = "",

    @ColumnInfo("note_subtitle")
    val noteSubtitle: String = "",

    @ColumnInfo("note_imagePath")
    val noteDescription: String = "",

    @ColumnInfo("noteImage_Path")
    val noteImagePath: String = "",

    @ColumnInfo("note_Date")
    val notedata: String = currentEntryNoteDate()
)






//Bu fonksiyon, cihazın bulunduğu yerel ayara (Locale) göre "yyyy-MM-dd" formatında bugünün tarihini döndürür.
fun currentEntryNoteDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(calendar.time)
}



/*
noteID

noteTitle

noteSubtitle

noteDescription

noteImagePath

noteEntryDate*/
