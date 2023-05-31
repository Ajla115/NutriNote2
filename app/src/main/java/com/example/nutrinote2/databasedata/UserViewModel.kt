package com.example.nutrinote2.databasedata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    private val _userInput = MutableStateFlow(User(0, "", "", ""))

    val userInput: StateFlow<User> = _userInput

    fun onUserInputChanged(user: User) {
        _userInput.value = user
    }

    init {
        viewModelScope.launch {
            userInput.collect { user ->
                insertUser(user)
            }
        }
    }

    private suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}
