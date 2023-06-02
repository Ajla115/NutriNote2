package com.example.nutrinote2.screens


import android.content.ContentValues
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nutrinote2.R

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.nutrinote2.databasedata.DBHandler



@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CalorieCounterScreen()
    }
}



@Composable
fun CalorieCounterScreen() {

    val dbHelper = DBHandler(context = LocalContext.current)
    //dbHelper.insertFoods()

    val context = LocalContext.current
    val db = DBHandler(context)
    val lastLoginEmail = remember { db.getLastLoginEmail() }
    var userId = remember { db.getUserIdByEmail(lastLoginEmail) }


/*
    //inserting values in userFoods table
    val users = listOf(1, 2, 3, 4)
    val dates = listOf("1.6.2023.", "2.6.2023.", "3.6.2023.", "4.6.2023.", "5.6.2023.", "6.6.2023.", "7.6.2023.", "8.6.2023.", "9.6.2023.")
    val foodIds = 1..12

    for (user in users) {
        for (date in dates) {
            for (foodId in foodIds) {

                dbHelper.insertUserFood(userId = user, foodId = foodId, date = date)
            }
        }
    }
*/

    var consumedCarbs = 245
    var consumedProtein = 35
    var consumedFat = 55
    var carbsPercentage = 100 * consumedCarbs / 300
    var proteinPercentage = 100 * consumedProtein / 50
    var fatPercentage = 100 * consumedFat / 70
    var consumedCalories = 4 * consumedCarbs + 4 * consumedProtein + 9 * consumedFat


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Calorie bar
        CalorieBar(
            consumedCalories = consumedCalories,
            totalCalories = 2030,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Nutrition bars
        NutritionBars(
            carbsPercentage = carbsPercentage,
            proteinPercentage = proteinPercentage,
            fatPercentage = fatPercentage,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Add meal buttons
        androidx.compose.material3.Text(
            text = "Add Meal",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(Modifier.fillMaxWidth()) {
            MealButton(text = "Breakfast", imageResId = R.drawable.breakfast)
            MealButton(text = "Lunch", imageResId = R.drawable.lunch)
            MealButton(text = "Dinner", imageResId = R.drawable.dinner)
            MealButton(text = "Snack", imageResId = R.drawable.snack)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CalorieBar(consumedCalories: Int, totalCalories: Int, modifier: Modifier = Modifier) {
    val progress = (consumedCalories.toFloat() / totalCalories.toFloat()).coerceIn(0f, 1f)
    val backgroundColor = Color(0xFFD2EDC8)
    val primaryColor = Color(0xFF85A07B)

    Box(
        modifier = modifier
            .height(20.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(20.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(primaryColor)
        )
    }
    androidx.compose.material3.Text(
        text = "Calories: $consumedCalories",
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun NutritionBars(
    carbsPercentage: Int,
    proteinPercentage: Int,
    fatPercentage: Int,
    modifier: Modifier = Modifier
) {
    val carbsColor = Color(0xFFCBEAC0)
    val proteinColor = Color(0xFFB2D1A6)
    val fatColor = Color(0xFF98B78D)

    Row(modifier = modifier) {
        NutritionBar("Carbs", carbsPercentage, carbsColor)
        Spacer(modifier = Modifier.padding(25.dp))
        NutritionBar("Protein", proteinPercentage, proteinColor)
        Spacer(modifier = Modifier.padding(25.dp))
        NutritionBar("Fat", fatPercentage, fatColor)
    }
}

@Composable
fun NutritionBar(name: String, percentage: Int, color: Color, modifier: Modifier = Modifier) {
    val strokeSize = 20.dp
    val startAngle = -90f
    val sweepAngle = 360f * (percentage / 100f)
    val diameter = 85.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Canvas(
            modifier = Modifier
                .size(diameter)
                .clip(CircleShape)
                .background(Color(0xFFE5F5DF))
        ) {
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeSize.toPx())
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            androidx.compose.material3.Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )
            androidx.compose.material3.Text(
                text = "$percentage%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@Composable
fun MealButton(text: String, imageResId: Int, modifier: Modifier = Modifier) {

    val dbHelper = DBHandler(context = LocalContext.current)

    var category = text
    var expanded by remember { mutableStateOf(false) }

    val dbHandler = DBHandler(context = LocalContext.current)

    val foods = dbHandler.getFoodsByCategory(category)
    val selectedFood = remember { mutableStateOf("") }
    val buttonColor = Color(0xFFABC7A2)
    val mealButtonColor = Color(0xFFD2EDC8)

    Column(modifier = modifier) {
        Button(
            onClick = { expanded = true },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = Color(0xFF131712)),
            modifier = modifier
                .padding(8.dp)
                .width(170.dp)
                .height(70.dp)
        ) {
            Row(modifier = modifier) {
                Icon(
                    painter = painterResource(imageResId),
                    contentDescription = text,
                    tint = LocalContentColor.current,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                androidx.compose.material3.Text(
                    text = text,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(color = Color(0xFFABC7A2))
        ) {
            foods.forEach { food ->
                Button(
                    modifier = modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(80.dp),
                    onClick = {
                        selectedFood.value = food.name
                        expanded = false
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = mealButtonColor, contentColor = Color(0xFF131712)),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            androidx.compose.material3.Text(
                                text = food.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.padding(start = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            androidx.compose.material3.Text(text = "Protein: ${food.protein}")
                            androidx.compose.material3.Text(text = "Carbs: ${food.carbs}")
                            androidx.compose.material3.Text(text = "Fat: ${food.fat}")
                        }
                    }
                }

            }
        }

        if (selectedFood.value.isNotEmpty()) {
            androidx.compose.material3.Text(
                text = "Selected Food: ${selectedFood.value}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
