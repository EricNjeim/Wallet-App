package com.example.eric_summer2023.domain.repository
import com.example.eric_summer2023.data.repository.Myrepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Myrepositoryimplementatoin @Inject constructor() : Myrepository {
    override suspend fun dofirestorecall(): Pair<Double, String> {
        val db = FirebaseFirestore.getInstance()
        val documentSnapshot = db.collection("MYWALLET").document("Details").get().await()

        if (documentSnapshot.exists()) {
            val balance = documentSnapshot.getDouble("Balance") ?: 0.0
            val fullName = documentSnapshot.getString("Full Name") ?: ""
            return Pair(balance, fullName)
        } else {
            return Pair(-1.0, "")
        }
    }
}
