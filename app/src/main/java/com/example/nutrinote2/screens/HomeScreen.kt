package com.example.nutrinote2.screens


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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.example.nutrinote2.databasedata.DBHandler
import kotlin.math.absoluteValue


@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
                )
            {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            CalorieCounterScreen()
        }
    }
}


var consumedCarbs = 245.0f
var consumedProtein = 35.0f
var consumedFat = 55.0f

@Composable
fun CalorieCounterScreen() {

    val dbHelper = DBHandler(context = LocalContext.current)
    //dbHelper.insertFoods()

    val context = LocalContext.current
    val db = DBHandler(context)
    val lastLoginEmail = remember { db.getLastLoginEmail() }
    var userId = remember { db.getUserIdByEmail(lastLoginEmail) }

    var foodAddedTrigger by remember { mutableStateOf(0) }


    /*
        //inserting values in userFoods table
        val users = listOf(1, 2, 3, 4)
        val dates = listOf("6.6.2023.", "7.6.2023.", "8.6.2023.")
        val foodIds = 1..12

        for (user in users) {
            for (date in dates) {
                for (foodId in foodIds) {

                    dbHelper.insertUserFood(userId = user, foodId = foodId, date = date)
                }
            }
        }
    */

    var carbsPercentage = remember { mutableStateOf(0.0f) }
    var proteinPercentage = remember { mutableStateOf(0.0f) }
    var fatPercentage = remember { mutableStateOf(0.0f) }
    var consumedCalories = remember { mutableStateOf(0.0f) }

    LaunchedEffect(foodAddedTrigger) {
        consumedCarbs = dbHelper.getDailyConsumptionCarbs(userId)
        consumedProtein = dbHelper.getDailyConsumptionProtein(userId)
        consumedFat = dbHelper.getDailyConsumptionFat(userId)

        carbsPercentage.value = 100 * consumedCarbs / 300
        proteinPercentage.value = 100 * consumedProtein / 50
        fatPercentage.value = 100 * consumedFat / 70
        consumedCalories.value = 4 * consumedCarbs + 4 * consumedProtein + 9 * consumedFat
    }


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
        Text(
            text = "Add Meal",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(Modifier.fillMaxWidth()) {
            MealButton(
                text = "Breakfast",
                imageResId = R.drawable.breakfast,
                foodAddedTrigger = foodAddedTrigger,
                onFoodAdded = {
                    foodAddedTrigger++
                })
            MealButton(
                text = "Lunch",
                imageResId = R.drawable.lunch,
                foodAddedTrigger = foodAddedTrigger,
                onFoodAdded = {
                    foodAddedTrigger++
                })
            MealButton(
                text = "Dinner",
                imageResId = R.drawable.dinner,
                foodAddedTrigger = foodAddedTrigger,
                onFoodAdded = {
                    foodAddedTrigger++
                })
            MealButton(
                text = "Snack",
                imageResId = R.drawable.snack,
                foodAddedTrigger = foodAddedTrigger,
                onFoodAdded = {
                    foodAddedTrigger++
                })
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CalorieBar(consumedCalories: MutableState<Float>, totalCalories: Int, modifier: Modifier = Modifier) {
    val progress = (consumedCalories.value / totalCalories.toFloat()).coerceIn(0f, 1f)
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
    Text(
        text = "Calories: ${consumedCalories.value}",
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun NutritionBars(
    carbsPercentage: MutableState<Float>,
    proteinPercentage: MutableState<Float>,
    fatPercentage: MutableState<Float>,
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
fun NutritionBar(name: String, percentage: MutableState<Float>, color: Color, modifier: Modifier = Modifier) {
    val strokeSize = 20.dp
    val startAngle = -90f
    val sweepAngle = 360f * (percentage.value / 100f)
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
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${percentage.value}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@Composable
fun MealButton(
        text: String,
        imageResId: Int,
        foodAddedTrigger: Int,
        onFoodAdded: () -> Unit,
        modifier: Modifier = Modifier
    ) {

    val dbHelper = DBHandler(context = LocalContext.current)
    val dbHandler = DBHandler(context = LocalContext.current)

    var category = text
    var expanded by remember { mutableStateOf(false) }
    val foods = dbHandler.getFoodsByCategory(category)
    val selectedFood = remember { mutableStateOf("") }
    val buttonColor = colorResource(id = R.color.colorPrimary)
    val mealButtonColor = Color(0xFFD2EDC8)
    val context = LocalContext.current
    val db = DBHandler(context)
    val lastLoginEmail = remember { db.getLastLoginEmail() }
    var userId = remember { db.getUserIdByEmail(lastLoginEmail) }

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

                        var foodId = dbHandler.getFoodIdByName(selectedFood.value)
                        consumedProtein += dbHelper.getFoodProteinById(foodId)
                        consumedCarbs += dbHelper.getFoodCarbsById(foodId)
                        consumedFat += dbHelper.getFoodFatById(foodId)
                        dbHelper.updateDailyConsumption(userId = userId, consumedProtein, consumedCarbs, consumedFat)
                        onFoodAdded()
                        dbHelper.insertUserFood(userId = userId, foodId = foodId, date = "9.6.2023.")
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
                            Text(
                                text = food.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.padding(start = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Protein: ${food.protein}")
                            Text(text = "Carbs: ${food.carbs}")
                            Text(text = "Fat: ${food.fat}")
                        }
                    }
                }

            }
        }

        if (selectedFood.value.isNotEmpty()) {
            Text(
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
