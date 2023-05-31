package com.example.nutrinote2.screens


//radi samo je potrebno estetski dotjerati
//potrebno je vidjeti sta se desava sa bojama i napraviti da se ovi placeholderi vide
//ne svida mi se sto su edges kod dugmadi ovoliko rounded, tako da treba i to prepraviti

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrinote2.ui.theme.Shapes


@Composable
fun SplashScreen( onLoginButtonClicked: () -> Unit,
                  onRegisterButtonClicked: () -> Unit,
                 modifier: Modifier = Modifier){
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF99EDC3)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally) {
            //Spacer(modifier = Modifier.height(4.dp))

            Text(text = "NutriNote", fontWeight = FontWeight.Bold, fontSize = 64.sp, color = Color.DarkGray, style = MaterialTheme.typography.h1)

            Spacer(modifier = Modifier.height(75.dp))

            Button(
                onClick = onRegisterButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
                shape = Shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF1B7F79))


            ) {
                Text("Register", color = Color.White, fontSize = 15.sp)
            }
            //Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = onLoginButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 1.dp),
                shape = Shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF1B7F79)
                )) {
                Text("Login", color = Color.White, fontSize = 15.sp)
            }
        }
    }
}




/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SplashScreen( onLoginButtonClicked = {},
                  onRegisterButtonClicked = {},
                  modifier = Modifier)
}*/




