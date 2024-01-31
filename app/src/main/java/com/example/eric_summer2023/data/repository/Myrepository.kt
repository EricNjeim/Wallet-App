package com.example.eric_summer2023.data.repository

interface Myrepository {
    suspend fun dofirestorecall():Pair<Double, String>
}