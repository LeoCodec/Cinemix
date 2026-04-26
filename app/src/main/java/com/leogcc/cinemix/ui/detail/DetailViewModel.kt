package com.leogcc.cinemix.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leogcc.cinemix.data.model.Item
import com.leogcc.cinemix.data.repository.ItemRepository
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val repo = ItemRepository()
    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item
    private var itemActual: Item? = null

    fun cargar(id: String) {
        viewModelScope.launch {
            val lista = repo.getAll()
            itemActual = lista.find { it.id == id }
            itemActual?.let { _item.value = it }
        }
    }

    fun toggleFavorito(esFavorito: Boolean) {
        viewModelScope.launch {
            itemActual?.let {
                val actualizado = it.copy(esFavorito = esFavorito)
                repo.guardar(actualizado)
                itemActual = actualizado
            }
        }
    }
}
