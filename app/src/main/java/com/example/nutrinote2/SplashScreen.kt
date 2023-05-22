package com.example.nutrinote2

import androidx.compose.foundation.layout.R


//radi samo je potrebno estetski dotjerati
//potrebno je vidjeti sta se desava sa bojama i napraviti da se ovi placeholderi vide
//ne svida mi se sto su edges kod dugmadi ovoliko rounded, tako da treba i to prepraviti
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrinote2.ui.theme.NutriNote2Theme
import com.example.nutrinote2.ui.theme.Shapes
import kotlinx.coroutines.delay

class SplashScreenActivity : ComponentActivity() {
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
fun SplashScreen(modifier: Modifier){
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF99EDC3)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally) {
            //Spacer(modifier = Modifier.height(4.dp))
            HeaderText()
            Spacer(modifier = Modifier.height(75.dp))
            ButtonRegisterSplash()
            //Spacer(modifier = Modifier.height(16.dp))
            /*ButtonFBRegister()*/
            Spacer(modifier = Modifier.height(14.dp))
            ButtonLoginSplash()
        }
    }
}

@Composable
private fun HeaderText() {
    Text(text = "NutriNote", fontWeight = FontWeight.Bold, fontSize = 64.sp, color = Color.DarkGray, style = MaterialTheme.typography.h1)
}

@Composable
private fun ButtonRegisterSplash() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF1B7F79))


    ) {
        Text("Register", color = Color.White, fontSize = 15.sp)
    }
}

@Composable
private fun ButtonLoginSplash() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF1B7F79)
        )) {
        Text("Login", color = Color.White, fontSize = 15.sp)
    }
}

//kako sam prethodno uradila i radi, samo nije nesto lijepo
/*@Composable
fun NutriNoteApp(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF99EDC3)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NutriNote",

            color = Color(0xFF1B7F79),
            fontSize = 64.sp,
            modifier = Modifier.padding(32.dp),
            fontWeight = FontWeight.SemiBold
        )
        // Rounded corners button for Sign Up
        Button(
            onClick = { /* TODO: Handle login button click */ },
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .height(40.dp)
                .width(250.dp)
                .background(color = Color(0xFF1B7F79))
        ) {
            Text(
                text = "Login"/*stringResource(id = R.string.register)*/,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Rounded corners button for Register
        Button(
            onClick = { /* TODO: Handle login button click */ },
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .height(40.dp)
                .width(250.dp)
                .background(color = Green)
        ) {
            Text(
                text = "Register"/*stringResource(id = R.string.register)*/,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}
*/

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SplashScreen(modifier = Modifier)
}
*/



