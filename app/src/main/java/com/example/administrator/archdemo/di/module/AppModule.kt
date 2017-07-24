package com.example.administrator.archdemo.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.example.administrator.archdemo.api.ArchService
import com.example.administrator.archdemo.db.AppDatabase
import com.example.administrator.archdemo.global.DbObject
import com.example.administrator.archdemo.global.UrlObject
import com.example.administrator.archdemo.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @desc
 * @author Tiany
 * @date 2017/7/4 0004
 */
@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(app: Application) : Context{
        return  app.applicationContext
    }

    @Singleton
    @Provides
    fun provideArchService(): ArchService {
        return Retrofit.Builder()
                .baseUrl(UrlObject.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(ArchService::class.java!!)
    }

    @Singleton
    @Provides
    fun providerDb(app: Context): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java!!, DbObject.NAME_DATABASE).build()
    }

}