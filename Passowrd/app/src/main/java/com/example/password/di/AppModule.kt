package com.example.password.di

import android.app.Application
import androidx.room.Room
import com.example.password.database.Dao
import com.example.password.database.Repository
import com.example.password.database.AppRoomDatabase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppRoomDatabase {
        return Room.databaseBuilder(
            app,
            AppRoomDatabase::class.java,
            "database"
        ).fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    fun provideDao(db: AppRoomDatabase): Dao = db.dao;

    @Provides
    @Singleton
    fun provideRepository(dao: Dao): Repository = Repository(dao);

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance();

}