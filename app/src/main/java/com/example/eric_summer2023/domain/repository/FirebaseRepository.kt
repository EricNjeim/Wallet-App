package com.example.eric_summer2023.domain.repository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore)  {
    suspend fun GetData(): Pair<Double, String> {
        val documentSnapshot = firestore.collection("MYWALLET").document("Details").get().await()

        if (documentSnapshot.exists()) {
            val balance = documentSnapshot.getDouble("Balance") ?: 0.0
            val fullName = documentSnapshot.getString("Full Name") ?: ""
            return Pair(balance, fullName)
        } else {
            return Pair(-1.0, "")
        }
    }
    fun updateDocument(documentPath: String, data: Map<String, Any>): Task<Void> {
        val documentReference = firestore.document(documentPath)
        return documentReference.set(data, SetOptions.merge())
    }
}
