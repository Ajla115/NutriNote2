package com.example.nutrinote2.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import com.example.nutrinote2.ui.theme.Shapes
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

import com.example.nutrinote2.databasedata.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun RegisterScreen(onRegisterButtonClicked : () -> Unit) {
    val context = LocalContext.current
    val db = DBHandler(context)
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) } // Error message state

    val focusManager = LocalFocusManager.current



    /*val dbHelper = DBHandler(context = LocalContext.current)
    dbHelper.insertWaterIntakeEntry("2023-05-23", 500) // Day 1: 500ml
    dbHelper.insertWaterIntakeEntry("2023-05-24", 1000) // Day 2: 1000ml
    dbHelper.insertWaterIntakeEntry("2023-05-25", 1250) // Day 3: 1250ml
    dbHelper.insertWaterIntakeEntry("2023-05-26", 1750) // Day 4: 1750ml
    dbHelper.insertWaterIntakeEntry("2023-05-27", 1000) // Day 5: 1000ml
    dbHelper.insertWaterIntakeEntry("2023-05-28", 2000) // Day 6: 2000ml
    dbHelper.insertWaterIntakeEntry("2023-05-29", 1750) // Day 7: 1750ml*/

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Welcome,",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color(0xFF1B7F79)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Track your nutrient intake effortlessly with our app.",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true, //dodano
                keyboardOptions = KeyboardOptions.Default.copy( //dodano
                    keyboardType = KeyboardType.Text, //dodano
                    imeAction = ImeAction.Next //dodano
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }) //dodano
                //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true, //dodano
                keyboardOptions = KeyboardOptions.Default.copy( //dodano
                    keyboardType = KeyboardType.Email, //dodano
                    imeAction = ImeAction.Next //da prebaci na sljedecu liniju //dodano
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }) //dodano
                //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy( //dodano
                    keyboardType = KeyboardType.Password, //dodano
                    imeAction = ImeAction.Done  //dodano
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }), //dodano
                visualTransformation = PasswordVisualTransformation() //dodano
                //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onClick@{
                    // Check if all fields are filled
                    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                            .show()
                        return@onClick
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        // Check if the user already exists in the database
                        val existingUser = db.findByUsername(username)
                        if (existingUser != null) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "User already exists in the database",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            db.addUser(username, password, email)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "User Added to Database",
                                    Toast.LENGTH_SHORT
                                ).show()

                                db.insertLoginLog(log_email = email)
                                db.insertDailyConsumption(userId = db.getUserIdByEmail(email), 0.0f, 0.0f, 0.0f)

                                // Now call the original onClick function
                                onRegisterButtonClicked()
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
                Text("Register", fontSize = 15.sp, color= Color.White)
            }
            Spacer(modifier = Modifier.height(64.dp))
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

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
RegisterScreen(onRegisterButtonClicked : { } )
}*/


/*Button(
    onClick = onClick@ {
        // Check if all fields are filled
        if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return@onClick
        }

        CoroutineScope(Dispatchers.IO).launch {
            // Check if the user already exists in the database
            val existingUser = db.findByUsername(username)
            if (existingUser != null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "User already exists in the database", Toast.LENGTH_SHORT).show()
                }
            } else {
                db.addUser(username, password, email)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "User Added to Database", Toast.LENGTH_SHORT).show()
                }
                // Now call the original onClick function
                onRegisterButtonClicked()
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
    Text("Register", fontSize = 15.sp)
}
Spacer(modifier = Modifier.height(64.dp))

}
}
}*/




/*
@Composable
fun RegisterScreen(onRegisterButtonClicked : () -> Unit) {

/*val myScope = CoroutineScope(Dispatchers.Default)*/
val context = LocalContext.current
val db = AppDatabase.getDatabase(context)
val userDao = db.userDao()

var username by remember { mutableStateOf("") }
var email by remember { mutableStateOf("") }
var password by remember { mutableStateOf("") }
var errorMessage by remember { mutableStateOf<String?>(null) } // Error message state

val focusManager = LocalFocusManager.current

//val viewModel: UserViewModel = viewModel()
//val viewModel: UserViewModel by viewModel(factory = UserViewModelFactory(userDao))


Box(
modifier = Modifier.fillMaxSize(),
contentAlignment = Alignment.Center
) {
Column(modifier = Modifier.padding(16.dp)) {
Spacer(modifier = Modifier.height(4.dp))

Text(text = "Welcome,", fontWeight = FontWeight.Bold, fontSize = 32.sp, color = Color(0xFF1B7F79))
Spacer(modifier = Modifier.height(2.dp))
Text(text = "Sign up to create an account,", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color.DarkGray)

Spacer(modifier = Modifier.height(128.dp))

OutlinedTextField(
    value = username,
    onValueChange = { username = it },
    label = { Text(text = "Username") },
    modifier = Modifier.fillMaxWidth(),
    singleLine = true, //dodano
    keyboardOptions = KeyboardOptions.Default.copy( //dodano
        keyboardType = KeyboardType.Text, //dodano
        imeAction = ImeAction.Next //dodano
    ),
    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }) //dodano
    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
)

Spacer(modifier = Modifier.height(4.dp))

OutlinedTextField(
    value = email,
    onValueChange = { email = it },
    label = { Text(text = "Email") },
    modifier = Modifier.fillMaxWidth(),
    singleLine = true, //dodano
    keyboardOptions = KeyboardOptions.Default.copy( //dodano
        keyboardType = KeyboardType.Email, //dodano
        imeAction = ImeAction.Next //da prebaci na sljedecu liniju //dodano
    ),
    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }) //dodano
    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
)

Spacer(modifier = Modifier.height(4.dp))

OutlinedTextField(
    value = password,
    onValueChange = { password = it },
    label = { Text(text = "Password") },
    modifier = Modifier.fillMaxWidth(),
    singleLine = true,
    keyboardOptions = KeyboardOptions.Default.copy( //dodano
        keyboardType = KeyboardType.Password, //dodano
        imeAction = ImeAction.Done  //dodano
    ),
    keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }), //dodano
    visualTransformation = PasswordVisualTransformation() //dodano
    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
)

Spacer(modifier = Modifier.height(14.dp))
Button(
    onClick = onRegisterButtonClicked,

modifier = Modifier.fillMaxWidth(),
    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
    shape = Shapes.large,
    colors = ButtonDefaults.buttonColors(
        backgroundColor = Color(0xFF99EDC3)
    )
) {
    Text("Register", fontSize = 15.sp)
}
Spacer(modifier = Modifier.height(64.dp))


}
}
}
*/

/*@Composable
fun RegisterScreen(onRegisterButtonClicked : () -> Unit) {

val viewModel: UserViewModel = viewModel()
/*val myScope = CoroutineScope(Dispatchers.Default)*/
val context = LocalContext.current
val db = NutriNoteDatabase.getDatabase(context)
val userDao = db.userDao()

var username by remember { mutableStateOf("") }
var email by remember { mutableStateOf("") }
var password by remember { mutableStateOf("") }

val focusManager = LocalFocusManager.current


Box(
modifier = Modifier.fillMaxSize(),
contentAlignment = Alignment.Center
) {
Column(modifier = Modifier.padding(16.dp)) {
Spacer(modifier = Modifier.height(4.dp))

Text(text = "Welcome,", fontWeight = FontWeight.Bold, fontSize = 32.sp, color = Color(0xFF1B7F79))
Spacer(modifier = Modifier.height(2.dp))
Text(text = "Sign up to create an account,", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color.DarkGray)

Spacer(modifier = Modifier.height(128.dp))

OutlinedTextField(
    value = username,
    onValueChange = { username = it },
    label = { Text(text = "Username") },
    modifier = Modifier.fillMaxWidth(),
    singleLine = true, //dodano
    keyboardOptions = KeyboardOptions.Default.copy( //dodano
        keyboardType = KeyboardType.Text, //dodano
        imeAction = ImeAction.Next //dodano
    ),
    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }) //dodano
    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
)

Spacer(modifier = Modifier.height(4.dp))

OutlinedTextField(
    value = email,
    onValueChange = { email = it },
    label = { Text(text = "Email") },
    modifier = Modifier.fillMaxWidth(),
    singleLine = true, //dodano
    keyboardOptions = KeyboardOptions.Default.copy( //dodano
        keyboardType = KeyboardType.Email, //dodano
        imeAction = ImeAction.Next //da prebaci na sljedecu liniju //dodano
    ),
    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }) //dodano
    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
)

Spacer(modifier = Modifier.height(4.dp))

OutlinedTextField(
    value = password,
    onValueChange = { password = it },
    label = { Text(text = "Password") },
    modifier = Modifier.fillMaxWidth(),
    singleLine = true,
    keyboardOptions = KeyboardOptions.Default.copy( //dodano
        keyboardType = KeyboardType.Password, //dodano
        imeAction = ImeAction.Done  //dodano
    ),
    keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }), //dodano
    visualTransformation = PasswordVisualTransformation() //dodano
    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
)

Spacer(modifier = Modifier.height(14.dp))


Button(
    onClick = {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            // Show a message that all fields need to be filled
        } else {
            viewModel.viewModelScope.launch {
                val existingUser = viewModel.getUserByEmail(email)
                if (existingUser != null) {
                    // Show a message that a user with this email already exists
                } else {
                    val user = User(username = username, email = email, password = password)
                    viewModel.insertUser(user)
                    onRegisterButtonClicked()
                }
            }
        }
    },
    //...
) {
    Text("Register", fontSize = 15.sp)
}

Button(
    onClick = onRegisterButtonClicked,
    modifier = Modifier.fillMaxWidth(),
    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
    shape = Shapes.large,
    colors = ButtonDefaults.buttonColors(
        backgroundColor = Color(0xFF99EDC3)
    )

) {
    Text("Register", fontSize = 15.sp)
}
Spacer(modifier = Modifier.height(64.dp))

}
}
}*/



