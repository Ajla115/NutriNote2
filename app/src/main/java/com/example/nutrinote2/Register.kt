package com.example.nutrinote2

//radi samo je potrebno estetski dotjerati
//potrebno je vidjeti sta se desava sa bojama i napraviti da se ovi placeholderi vide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {

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

        Column {
            Text(
                text = "First Name:",
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(5.dp)
            )
            TextField(
                value = firstName,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }),
                singleLine = true,
                onValueChange = { firstName = it },
                modifier = modifier
                    .clip(RoundedCornerShape(32.dp))
                    .height(40.dp)
                    .width(250.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray,
                    placeholderColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = { Text("First Name")},
            )
        }





        Spacer(modifier = Modifier.height(5.dp))
        Column{
            Text(
                text = "Last Name:",
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(5.dp)
            )
            TextField(
                value = lastName,
                placeholder = { Text("Last Name")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }),
                singleLine = true,
                onValueChange = { firstName = it },
                modifier = modifier
                    .clip(RoundedCornerShape(32.dp))
                    .height(40.dp)
                    .width(250.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray,
                    placeholderColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Column{
            Text(
                text = "Email:",
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(5.dp)
            )
            TextField(
                value = email,
                placeholder = { Text("firstname.lastname@gmail.com")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus((FocusDirection.Down)) }),
                singleLine = true,
                onValueChange = { email = it },
                modifier = modifier
                    .clip(RoundedCornerShape(32.dp))
                    .height(40.dp)
                    .width(250.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray,
                    placeholderColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Column {
            Text(
                text = "Password:",
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(5.dp)
            )
            TextField(
                value = password,
                placeholder = { Text("Password") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
                singleLine = true,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = modifier
                    .clip(RoundedCornerShape(32.dp))
                    .height(40.dp)
                    .width(250.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray,
                    placeholderColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))


        // Login button
        Button(
            onClick = { /* TODO: Handle login button click */ },
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .height(40.dp)
                .width(250.dp)
                .background(color = Color(0xFF1B7F79))
        ) {
            Text(
                text = stringResource(id = R.string.register),
                textAlign = TextAlign.Center,
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
}