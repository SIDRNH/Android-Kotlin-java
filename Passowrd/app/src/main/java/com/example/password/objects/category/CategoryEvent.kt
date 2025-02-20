package com.example.password.objects.category

sealed interface CategoryEvent {
    object SaveCategory: CategoryEvent;
    data class SetName(val name: String): CategoryEvent;
    object ShowDialog: CategoryEvent;
    object HideDialog: CategoryEvent;
    data class DeleteCategory(val category: Category): CategoryEvent;
    data class EditCategory(val category: Category): CategoryEvent;
}