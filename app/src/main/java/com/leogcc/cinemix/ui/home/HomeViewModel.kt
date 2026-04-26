package com.leogcc.cinemix.ui.home

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

    private val _estadisticas = MutableLiveData<Map<String, Int>>()
    val estadisticas: LiveData<Map<String, Int>> = _estadisticas

    fun cargar(tipo: String?) {
        viewModelScope.launch {
            _items.value = if (tipo == null) repo.getAll() else repo.getByTipo(tipo)
        }
    }

    fun buscar(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _items.value = repo.getAll()
            } else {
                val todos = repo.getAll()
                _items.value = todos.filter {
                    it.titulo.contains(query, ignoreCase = true)
                }
            }
        }
    }

    fun cargarFavoritos() {
        viewModelScope.launch {
            _items.value = repo.getFavoritos()
        }
    }

    fun cargarEstadisticas() {
        viewModelScope.launch {
            val todos = repo.getAll()
            val map = mutableMapOf<String, Int>()
            map["pelicula"] = todos.count { it.tipo == "pelicula" }
            map["serie"]    = todos.count { it.tipo == "serie" }
            map["libro"]    = todos.count { it.tipo == "libro" }
            _estadisticas.value = map
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