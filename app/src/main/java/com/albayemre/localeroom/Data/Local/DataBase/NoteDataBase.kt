package com.albayemre.localeroom.Data.Local.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.albayemre.localeroom.Data.Local.Dao.NoteDatabaseDao
import com.albayemre.localeroom.Data.Local.Entity.DataEntity

//entities = [NoteEntity::class, UserEntity::class]  Bu veritabanında kullanılacak tablo sınıflarını (Entity) belirtir.
@Database(
    version = 1,
    entities = [DataEntity::class],
    exportSchema = false
)
abstract class NoteDataBase : RoomDatabase() {

    //Dao muzu cevien metodu tanımladık
    abstract fun noteDao(): NoteDatabaseDao
}
