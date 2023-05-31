package com.example.nutrinote2.databasedata

import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getUserByCredentials(email: String, password: String): Flow<User>

    fun get_all(): Flow<List<User>>

   fun insert(user: User)

   fun delete(user: User)

     fun update(user: User)
}

interface ItemsRepository{



}
