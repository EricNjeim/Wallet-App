package com.example.eric_summer2023.dynamicinjection

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Firebasemodule {
    @Provides
    @Singleton
    fun provideFirebase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}