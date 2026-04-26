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

    fun cargar(tipo: String?) {
        viewModelScope.launch {
            _items.value = if (tipo == null) repo.getAll() else repo.getByTipo(tipo)
        }
    }
}
