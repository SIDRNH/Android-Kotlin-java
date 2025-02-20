package com.example.password.objects.item

sealed interface ItemEvent {
    object SaveItem: ItemEvent;
    data class SetName(val name: String): ItemEvent;
    data class SetUserName(val userName: String): ItemEvent;
    data class SetPassword(val password: String): ItemEvent;
    data class SetInfo(val info: String): ItemEvent;
    data class SetWebLink(val webLink: String): ItemEvent;
    object ShowDialog: ItemEvent;
    object HideDialog: ItemEvent;
    data class DeleteItem(val item: Item): ItemEvent;
    data class EditItem(val item: Item): ItemEvent;
}