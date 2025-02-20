package com.example.password.objects.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.password.aes.AESUtils
import com.example.password.database.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: Repository): ViewModel(){
    private val _state = MutableStateFlow(ItemState());
    val state: StateFlow<ItemState> = _state;
    private var categoryId: Int = -1;

    init {
        getItems(categoryId);
    }

    fun onEvent(event: ItemEvent) {
        when (event) {
            is ItemEvent.DeleteItem -> {
                viewModelScope.launch {
                    repository.deleteItem(event.item);
                }
            }
            is ItemEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingItem = false
                    );
                }
            }
            is ItemEvent.SaveItem -> {
                val name = state.value.name;
                val userName = state.value.userName;
                val password = AESUtils.encrypt(state.value.password);
                val info = state.value.info;
                val webLink = state.value.webLink;
                if (name.isBlank() || password.isBlank()) return;
                val editingItem = state.value.editingItem;
                viewModelScope.launch {
                    if (editingItem != null) {
                        val updatedItem = Item(editingItem.id, categoryId, name, userName, password, info, webLink);
                        repository.updateItem(updatedItem);
                    } else {
                        val item = Item(categoryId, name, userName, password, info, webLink)
                        repository.insertItem(item);
                    }
                }
                getItems(categoryId);
                _state.update {
                    it.copy(
                        name = "",
                        userName = "",
                        password = "",
                        info = "",
                        webLink = "",
                        isAddingItem = false,
                        editingItem = null
                    );
                }

            }
            is ItemEvent.SetInfo -> {
               _state.update {
                   it.copy(
                       info = event.info
                   );
               }
            }
            is ItemEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    );
                }
            }
            is ItemEvent.SetUserName -> {
                _state.update {
                    it.copy(
                        userName = event.userName
                    );
                }
            }
            is ItemEvent.SetPassword -> {
                _state.update {
                    it.copy(
                        password = event.password
                    );
                }
            }
            is ItemEvent.SetWebLink -> {
                _state.update {
                    it.copy(
                        webLink = event.webLink
                    );
                }
            }
            is ItemEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        name = "",
                        userName = "",
                        password = "",
                        info = "",
                        webLink = "",
                        isAddingItem = true,
                        editingItem = null
                    );
                }
            }
            is ItemEvent.EditItem -> {
                _state.update {
                    it.copy(
                        name = event.item.name,
                        userName = event.item.userName,
                        password = AESUtils.decrypt(event.item.password),
                        info = event.item.info,
                        webLink = event.item.webLink,
                        isAddingItem = true,
                        editingItem = event.item
                    )
                }
            }
        }
    }
    fun setCategoryId(id: Int) {
        categoryId = id;
        getItems(categoryId);
    }
    fun getItems(id: Int) {
        viewModelScope.launch {
            repository.getItem(id = id).collect { items ->
                _state.update {
                    it.copy(items = items);
                }
            }
        }
    }
}