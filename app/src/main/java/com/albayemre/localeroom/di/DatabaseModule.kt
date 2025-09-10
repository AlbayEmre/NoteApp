package com.albayemre.localeroom.di

import android.content.Context
import androidx.room.Room
import com.albayemre.localeroom.Data.Local.Dao.NoteDatabaseDao
import com.albayemre.localeroom.Data.Local.DataBase.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton




@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {




    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): NoteDataBase {
        return Room.databaseBuilder(
            context,
            NoteDataBase::class.java,
            "notes_db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(noteDataBase: NoteDataBase): NoteDatabaseDao {
        return noteDataBase.noteDao()
    }





}