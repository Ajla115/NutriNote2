package com.example.nutrinote2

import com.example.nutrinote2.ui.theme.NutriNote2Theme
import com.example.nutrinote2.ui.theme.Shapes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriNote2Theme() {
                Surface(color = MaterialTheme.colors.background) {
                    RegisterScreen()
                }

            }
        }
    }
}


@Composable
fun RegisterScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(4.dp))
            HeaderText()
            Spacer(modifier = Modifier.height(128.dp))
            UsernameTextField()
            Spacer(modifier = Modifier.height(4.dp))
            EmailTextField()
            Spacer(modifier = Modifier.height(4.dp))
            PasswordTextField()
            Spacer(modifier = Modifier.height(14.dp))
            ButtonRegister()
            //Spacer(modifier = Modifier.height(16.dp))
            /*ButtonFBRegister()*/
            Spacer(modifier = Modifier.height(64.dp))
            ButtonToLogin()
        }
    }
}

/*RegisterScreen composable: It represents the registration screen UI.
 It is wrapped in a Box composable to center its content vertically and
 horizontally. It contains a Column composable that stacks various UI
 elements vertically.
 */



@Composable
private fun HeaderText() {
    Text(text = "Welcome,", fontWeight = FontWeight.Bold, fontSize = 32.sp, color = Color(0xFF1B7F79))
    Spacer(modifier = Modifier.height(2.dp))
    Text(text = "Sign up to create an account,", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color.DarkGray)
}

@Composable
private fun UsernameTextField() {
    var username by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

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
}

@Composable
private fun EmailTextField() {
    var email by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current //dodano

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
}

@Composable
private fun PasswordTextField() {
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current //dodano

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
}

@Composable
private fun ButtonRegister() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF99EDC3)
        )

    ) {
        Text("Register", fontSize = 15.sp)
    }
}

/*@Composable
private fun ButtonFBRegister() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
        shape = Shapes.large,
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            /*Image(painter = painterResource(id = R.drawable.ic_facebook), contentDescription = "")
            Text("  Connect to Facebook", color = Color.Blue)*/
        }
    }
}*/

@Composable
private fun ButtonToLogin() {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text("Have an account ? ", fontSize = 15.sp)
        Text("Sign In ", fontSize = 15.sp, color = Color(0xFF1B7F79), fontWeight = FontWeight.SemiBold)
    }
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RegisterScreen()
}*/
