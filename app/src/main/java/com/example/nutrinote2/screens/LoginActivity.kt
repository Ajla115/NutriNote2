package com.example.nutrinote2.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrinote2.R
import com.example.nutrinote2.databasedata.DBHandler
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
                text = "NutriNote is here to help you make every bite count!",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(60.dp))

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
                                db.insertLoginLog(log_email = email)

                                onLoginButtonClicked()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp)),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
                shape = Shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.colorAccentDark)
                )
            ) {
                Text("Login", fontSize = 15.sp, color= Color.White)
            }
            Spacer(modifier = Modifier.height(128.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.groceries),
                contentDescription = "Your Drawable",
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}
