package com.aj.currencycalculator.di

import android.content.Context
import com.aj.currencycalculator.util.SharedPrefHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providersSharedPrefHelper(@ApplicationContext context: Context) =
        SharedPrefHelper(context)
}
