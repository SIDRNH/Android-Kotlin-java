package com.example.retrofitpractice.roomdb

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    suspend fun upsertUser(user: User) = userDao.upsertUser(user = user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user = user)
    suspend fun deleteAllUsers() = userDao.deleteAllUsers()
    fun fetchUser(): Flow<User?> = userDao.fetchUser()
}