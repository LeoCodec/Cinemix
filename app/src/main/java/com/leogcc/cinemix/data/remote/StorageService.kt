package com.leogcc.cinemix.data.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class StorageService {

    private val storage = FirebaseStorage.getInstance()
    private val ref = storage.reference.child("portadas")

    suspend fun subirPortada(uri: Uri): String {
        val nombre = UUID.randomUUID().toString() + ".jpg"
        val imgRef = ref.child(nombre)
        imgRef.putFile(uri).await()
        return imgRef.downloadUrl.await().toString()
    }

    suspend fun eliminarPortada(url: String) {
        storage.getReferenceFromUrl(url).delete().await()
    }
}
