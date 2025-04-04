package com.example.retrofitpractice.roomdb

class UserRepository(private val userDao: UserDao) {
    suspend fun upsertUser(user: User) = userDao.upsertUser(user = user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user = user)
    suspend fun deleteAllUsers() = userDao.deleteAllUsers()
}