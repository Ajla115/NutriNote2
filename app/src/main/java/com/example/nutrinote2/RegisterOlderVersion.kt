package com.example.nutrinote2

//primjer kako samo prvi put odradila register
//radi samo je potrebno estetski dotjerati

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/*
@Composable
fun RegistrationScreen(modifier : Modifier = Modifier) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF99EDC3)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NutriNote",
            style = MaterialTheme.typography.h1,
            color = Color(0xFF1B7F79),
            fontSize = 48.sp,
            modifier = Modifier.padding(32.dp),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Registration",
            style = MaterialTheme.typography.h6,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(75.dp))

        // User prompts
        Column {
            Text(
                text = "First Name:",
                fontSize = 10.sp,
                textAlign = TextAlign.Justify,
                modifier = modifier.padding(5.dp)
            )
            TextField(
                value = firstName,
                onValueChange = { newValue -> firstName = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
                    .height(48.dp)
                    .background(color = Color(0xFF99EDC3), shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp)),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.DarkGray),
                placeholder = { Text(text = "First Name", color = Color.DarkGray) },
            )
        }


        TextField(
            value = lastName,
            onValueChange = { newValue -> lastName = newValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .height(48.dp)
                .background(color = Color(0xFF99EDC3), shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp)),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.DarkGray),
            placeholder = { Text(text = "Last Name", color = Color.DarkGray) }
        )


        TextField(
            value = email,
            onValueChange = { newValue -> email = newValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .height(48.dp)
                .background(color = Color(0xFF99EDC3), shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp)),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.DarkGray),
            placeholder = { Text(text = "Email", color = Color.DarkGray) }
        )


        TextField(
            value = password,
            onValueChange = { newValue -> password = newValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .height(48.dp)
                .background(color = Color(0xFF99EDC3), shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp)),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.DarkGray),
            placeholder = { Text(text = "Password", color = Color.DarkGray) },
            visualTransformation = PasswordVisualTransformation()
        )
        // Register button
        Button(
            onClick = { /* TODO: Handle register button click */ },
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
                .width(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color(0xFF99EDC3), shape = RoundedCornerShape(12.dp)),
            contentPadding = PaddingValues(8.dp)
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen()
}*/