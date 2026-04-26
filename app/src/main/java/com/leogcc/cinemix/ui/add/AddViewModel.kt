package com.leogcc.cinemix.ui.add

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leogcc.cinemix.data.model.Item
import com.leogcc.cinemix.data.remote.StorageService
import com.leogcc.cinemix.data.repository.ItemRepository
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {

    private val repo    = ItemRepository()
    private val storage = StorageService()

    private val _guardado = MutableLiveData<Boolean>()
    val guardado: LiveData<Boolean> = _guardado

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun guardar(item: Item, portadaUri: Uri?) {
        viewModelScope.launch {
            try {
                val url = portadaUri?.let { storage.subirPortada(it) } ?: ""
                repo.guardar(item.copy(portadaUrl = url))
                _guardado.value = true
            } catch (e: Exception) {
                _error.value = "Error al guardar: ${e.message}"
            }
        }
    }
}
