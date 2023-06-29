package com.example.radius.di

import com.example.radius.network.RadiusApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkhttp():OkHttpClient = OkHttpClient
        .Builder()
//        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    @Named("base_url")
    fun providesBaseURL():String = "https://my-json-server.typicode.com/"

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,@Named("base_url") baseURL: String):Retrofit = Retrofit
        .Builder()
        .baseUrl(baseURL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRadiusApi(retrofit: Retrofit):RadiusApi = retrofit.create(RadiusApi::class.java)
}