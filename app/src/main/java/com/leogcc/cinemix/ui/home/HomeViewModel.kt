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

    private var todosLosItems: List<Item> = emptyList()

    fun cargar(tipo: String?) {
        viewModelScope.launch {
            try {
                val todos = repo.getAll()
                todosLosItems = todos
                _items.value = if (tipo == null) todos else todos.filter { it.tipo == tipo }
            } catch (e: Exception) {
                _mensaje.value = "Error cargar: "
            }
        }
    }

    fun buscar(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _items.value = todosLosItems
            } else {
                _items.value = todosLosItems.filter {
                    it.titulo.contains(query, ignoreCase = true)
                }
            }
        }
    }

    fun cargarFavoritos() {
        viewModelScope.launch {
            try {
                _items.value = repo.getFavoritos()
            } catch (e: Exception) {
                _mensaje.value = "Error favoritos: "
            }
        }
    }

    fun cargarEstadisticas() {
        viewModelScope.launch {
            try {
                val todos = repo.getAllSimple()
                val map = mutableMapOf<String, Int>()
                map["pelicula"] = todos.count { it.tipo == "pelicula" }
                map["serie"]    = todos.count { it.tipo == "serie" }
                map["libro"]    = todos.count { it.tipo == "libro" }
                _estadisticas.value = map
                _mensaje.value = "Stats:  items"
            } catch (e: Exception) {
                _mensaje.value = "Error stats: "
            }
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