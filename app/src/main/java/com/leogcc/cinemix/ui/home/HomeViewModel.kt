package com.leogcc.cinemix.ui.home

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leogcc.cinemix.data.model.Item
import com.leogcc.cinemix.data.repository.ItemRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repo = ItemRepository()
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    fun cargar(tipo: String?) {
        viewModelScope.launch {
            _items.value = if (tipo == null) repo.getAll() else repo.getByTipo(tipo)
        }
    }

    fun incrementarVisto(item: Item) {
        viewModelScope.launch {
            val actualizado = item.copy(vecesVisto = item.vecesVisto + 1)
            repo.guardar(actualizado)
            _mensaje.value = "Visto  veces"
            cargar(null)
        }
    }
}