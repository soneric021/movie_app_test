package com.ericsonmontero.moviewtechnicaltest.di

import com.ericsonmontero.moviewtechnicaltest.BuildConfig
import com.ericsonmontero.moviewtechnicaltest.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}