package com.example.yeschat.chat

import androidx.lifecycle.ViewModel
import com.example.yeschat.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class ChatScreenViewModel(private val firebaseDatabase: FirebaseDatabase): ViewModel() {
    private val _state: MutableStateFlow<ChatScreenState> = MutableStateFlow(ChatScreenState());
    val state: StateFlow<ChatScreenState> = _state.asStateFlow();

    fun onEvent(event: ChatScreenEvent) {
        when(event) {
            is ChatScreenEvent.ListenMessages -> listenMessages(event.chanelId);
            is ChatScreenEvent.TextMessage -> _state.update { it.copy(message = event.textMessage) };
            is ChatScreenEvent.SendMessage -> {
                sendMessage(channelId = event.channelId);
            }
        }
    }

    private fun sendMessage(channelId: String) {

        val user: FirebaseUser? = Firebase.auth.currentUser;
        if (user == null) {
            _state.update { it.copy(error = "Please Login to Send Message") };
            return;
        }
        val message: Message = Message(
            id = firebaseDatabase.reference.push().key ?: UUID.randomUUID().toString(),
            senderId = user.uid,
            message = _state.value.message,
            createdAt = System.currentTimeMillis(),
            senderName = Firebase.auth.currentUser?.displayName ?: "Unknown",
            senderImage = null,
            imageUrl = null
        );
        _state.update { it.copy(loading = true, error = null) };
        firebaseDatabase.reference.child("messages")
            .child(channelId)
            .push()
            .setValue(message)
            .addOnSuccessListener {
                _state.update { it.copy(loading = false, message = "") };
            }.addOnFailureListener { e ->
                _state.update { it.copy(loading = true, error = e.localizedMessage ?: "Failed to Send Message") };
            }
    }

    private fun listenMessages(channelId: String) {
        _state.update { it.copy(loading = true, error = null) };
        firebaseDatabase.getReference("messages")
            .child(channelId)
            .orderByChild("createdAt")
            .addValueEventListener(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = snapshot.children.mapNotNull { it.getValue(Message::class.java) };
                    _state.update { it.copy(messages = messages, loading = false, error = null) };
                }

                override fun onCancelled(error: DatabaseError) {
                     //Handle Error
                    _state.update { it.copy(loading = false, error = error.message) };
                }
            }
        )
    }
}