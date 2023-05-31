package com.example.nutrinote2.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrinote2.databasedata.DBHandler
import com.example.nutrinote2.databasedata.User
import com.example.nutrinote2.databasedata.UserViewModel
import com.example.nutrinote2.ui.theme.Shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun LoginScreen(onLoginButtonClicked: () -> Unit) {
    val context = LocalContext.current
    val db = DBHandler(context)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Welcome,",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color(0xFF1B7F79)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Sign in to continue,",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(128.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email:") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) })
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password:") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = onClick@{
                    // Check if all fields are filled
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        return@onClick
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        // Check if the user already exists in the database
                        val existingUser = db.findByEmail(email)

                        if (existingUser == null || existingUser.password != password) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "The user does not exist in the database", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // If the user exists and the password is correct, proceed with the original onClick function
                            withContext(Dispatchers.Main) {
                                onLoginButtonClicked()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
                shape = Shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF99EDC3)
                )
            ) {
                Text("Login", fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}


/*
@Composable
fun LoginScreen(onLoginButtonClicked : () -> Unit) {
    val viewModel: UserViewModel = viewModel()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = "Welcome,", fontWeight = FontWeight.Bold, fontSize = 32.sp, color = Color(0xFF1B7F79))
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Sign in to continue,", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color.DarkGray)

            Spacer(modifier = Modifier.height(128.dp))

            var email by remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current //dodano

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email:") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy( //dodano
                    keyboardType = KeyboardType.Email, //dodano
                    imeAction = ImeAction.Next //da prebaci na sljedecu liniju //dodano
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }) //dodano
                //dodano
                //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(4.dp))

            var password by remember { mutableStateOf("") }


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password:") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy( //dodano
                    keyboardType = KeyboardType.Password, //dodano
                    imeAction = ImeAction.Done  //dodano
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }), //dodano
                visualTransformation = PasswordVisualTransformation()//dodano
                //ja sam sklanjala zareze sa zadnjeg elementa u redoslijedu, iako nije bio problem ako je on bio tu, ali sta znam, za svaki slucaj
                //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = /*{
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        val user = User(username = email, email = email, password = password)
                        viewModel.onUserInputChanged(user)*/
                        onLoginButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
                shape = Shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF99EDC3)
                )) {
                Text("Login", fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(128.dp))

        }
    }
}
*/



/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    LoginScreen(onLoginButtonClicked = {})
}*/
