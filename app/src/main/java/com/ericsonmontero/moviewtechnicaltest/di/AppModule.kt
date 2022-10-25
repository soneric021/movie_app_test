package com.ericsonmontero.moviewtechnicaltest.di

import android.content.Context
import androidx.room.Room
import com.ericsonmontero.moviewtechnicaltest.BuildConfig
import com.ericsonmontero.moviewtechnicaltest.data.local.MovieDao
import com.ericsonmontero.moviewtechnicaltest.data.local.MovieDatabase
import com.ericsonmontero.moviewtechnicaltest.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpLoginInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            if(BuildConfig.DEBUG){
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideMoshi(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit (moshi: MoshiConverterFactory, client: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshi)
            .client(client)
            .baseUrl(BuildConfig.BASE_URL).build()

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context):MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movie-database").build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase):MovieDao {
        return movieDatabase.movieDao()
    }
}