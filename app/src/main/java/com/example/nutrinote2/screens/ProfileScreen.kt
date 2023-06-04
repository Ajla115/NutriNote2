package com.example.nutrinote2.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutrinote2.NutriNoteScreen
import com.example.nutrinote2.databasedata.DBHandler
import com.example.nutrinote2.databasedata.User

@Composable
fun ProfileScreen() {
    val dbHelper = DBHandler(context = LocalContext.current)
    val context = LocalContext.current
    val db = DBHandler(context)
    val lastLoginEmail = remember { db.getLastLoginEmail() }
    var userId = remember { db.getUserIdByEmail(lastLoginEmail) }
    val isIt = remember { db.isUserFoodsTableEmpty(userId) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,


    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
    
                Text(
                    text = "Hello $lastLoginEmail!",
                    style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(16.dp)
                )
                
                Text(
                    text = "History of food intake",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,

                    ),
                    modifier = Modifier.padding(16.dp)
                )

                // History display
                if (isIt) {
                    Text(text = "No data available.")
                }

                val foodAndDates = dbHelper.getFoodAndDatesByUserId(userId)

                LazyColumn {
                    items(foodAndDates) { (foodId, date) ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .background(Color(0xFFB7E4C7), RoundedCornerShape(8.dp))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Food: $foodId",
                                    modifier = Modifier.weight(1f),
                                    style = TextStyle(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "Date: $date",
                                    style = TextStyle(fontStyle = FontStyle.Normal)
                                )
                            }
                        }
                    }
                }



            }
        }
    }
}




