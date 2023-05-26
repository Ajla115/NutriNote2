package com.example.nutrinote2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.nutrinote2.ui.theme.NutriNote2Theme
import com.example.nutrinote2.ui.theme.Shapes


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriNote2Theme() {
                Surface(color = MaterialTheme.colors.background) {
                    LoginScreen(this@LoginActivity)
            }

                }
            }
        }
    }


@Composable
fun LoginScreen(mContext: Context) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            HeaderText()
            Spacer(modifier = Modifier.height(128.dp))
            EmailTextField()
            Spacer(modifier = Modifier.height(4.dp))
            PasswordTextField()
            Spacer(modifier = Modifier.height(64.dp))
            ButtonLogin()
            /*Spacer(modifier = Modifier.height(16.dp))
            ButtonFBLogin()*/
            Spacer(modifier = Modifier.height(128.dp))
            /*ButtonToRegister(onClick = {
                mContext.startActivity(Intent(mContext, RegisterActivity::class.java))
            })*/
        }
    }
}


@Composable
private fun HeaderText() {
    Text(text = "Welcome,", fontWeight = FontWeight.Bold, fontSize = 32.sp, color = Color(0xFF1B7F79))
    Spacer(modifier = Modifier.height(2.dp))
    Text(text = "Sign in to continue,", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color.DarkGray)
}

@Composable
private fun EmailTextField() {
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
}

@Composable
private fun PasswordTextField() {
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current //dodano

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
}

@Composable
private fun ButtonLogin() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF99EDC3)
        )) {
        Text("Login", fontSize = 15.sp)
    }
}

/*@Composable
private fun ButtonFBLogin() {
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
private fun ButtonToRegister(onClick: () -> Unit) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text("Don't have an account ? ", fontSize = 15.sp)
        Text("Sign Up ", fontSize = 15.sp,
            color = Color(0xFF1B7F79),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current // Retrieve the context using LocalContext
    LoginScreen(context)
}*/
