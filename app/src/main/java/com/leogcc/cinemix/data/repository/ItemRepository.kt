package com.leogcc.cinemix.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.leogcc.cinemix.data.model.Item
import kotlinx.coroutines.tasks.await

class ItemRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val col = db.collection("items")

    private val userId get() = auth.currentUser?.uid ?: ""

    suspend fun getAll(): List<Item> {
        return col.whereEqualTo("userId", userId)
            .orderBy("titulo", Query.Direction.ASCENDING)
            .get().await()
            .toObjects(Item::class.java)
    }

    suspend fun getByTipo(tipo: String): List<Item> {
        return col.whereEqualTo("userId", userId)
            .whereEqualTo("tipo", tipo)
            .get().await()
            .toObjects(Item::class.java)
    }

    suspend fun getFavoritos(): List<Item> {
        return col.whereEqualTo("userId", userId)
            .whereEqualTo("esFavorito", true)
            .get().await()
            .toObjects(Item::class.java)
    }

    suspend fun guardar(item: Item): String {
        val doc = if (item.id.isEmpty()) col.document() else col.document(item.id)
        val conId = item.copy(id = doc.id, userId = userId)
        doc.set(conId).await()
        return doc.id
    }

    suspend fun eliminar(id: String) {
        col.document(id).delete().await()
    }

    suspend fun buscar(query: String): List<Item> {
        return col.whereEqualTo("userId", userId)
            .orderBy("titulo")
            .startAt(query)
            .endAt(query + "\uf8ff")
            .get().await()
            .toObjects(Item::class.java)
    }
}
