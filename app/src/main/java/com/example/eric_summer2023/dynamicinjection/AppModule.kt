package com.example.eric_summer2023.dynamicinjection

import com.example.eric_summer2023.data.repository.Myrepository
import com.example.eric_summer2023.domain.repository.Myrepositoryimplementatoin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMyRepository(): Myrepository {
        return Myrepositoryimplementatoin()
    }
}