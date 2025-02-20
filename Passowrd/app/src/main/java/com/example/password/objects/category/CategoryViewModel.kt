package com.example.password.objects.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.password.database.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _state = MutableStateFlow(CategoryState());
    val state: StateFlow<CategoryState> = _state;

    init {
        viewModelScope.launch {
            repository.getCategories().collect { categories ->
                _state.update {
                    it.copy(categories = categories)
                }
            }
        }
    }

    fun onEvent(event: CategoryEvent) {
        when(event) {
            is CategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    repository.deleteCategory(event.category);
                }
            };
            is CategoryEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingCategory = false
                    )
                }
            };
            is CategoryEvent.SaveCategory -> {
                val name: String = state.value.name;
                if (name.isBlank()) return;
                val editingCategory = state.value.editingCategory;
                viewModelScope.launch {
                    if (editingCategory != null) {
                        val updatedCategory = Category(editingCategory.id, name);
                        repository.updateCategory(updatedCategory);
                    } else {
                        val category = Category(name);
                        repository.insertCategory(category);
                    }
                }
                _state.update {
                    it.copy(
                        name = "",
                        isAddingCategory = false,
                        editingCategory = null
                    )
                }
            };
            is CategoryEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            };
            is CategoryEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingCategory = true,
                        name = "",
                        editingCategory = null
                    )
                }
            };
            is CategoryEvent.EditCategory -> {
                _state.update {
                    it.copy(
                        name = event.category.name,
                        isAddingCategory = true,
                        editingCategory = event.category
                    )
                }
            }
        }
    }
}