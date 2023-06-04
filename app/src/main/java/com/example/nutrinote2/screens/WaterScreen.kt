package com.example.nutrinote2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrinote2.R
import androidx.compose.ui.res.colorResource
import com.example.nutrinote2.databasedata.DBHandler
import com.example.nutrinote2.databasedata.WaterIntakeEntry
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WaterScreen() {
    val dbHelper = DBHandler(context = LocalContext.current)
    val showDialog = remember { mutableStateOf(false) }
    val waterIntakeEntries = rememberWaterIntakeEntries(dbHelper)
    val totalWaterIntake = calculateTotalWaterIntake(waterIntakeEntries)

    val incrementWaterIntake: () -> Unit = {
        val currentWaterIntake = totalWaterIntake.value
        val updatedWaterIntake = currentWaterIntake + 250 // Increment by 250ml
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        dbHelper.insertWaterIntakeEntry(currentDate, updatedWaterIntake)
        totalWaterIntake.value = updatedWaterIntake
    }

    val decrementWaterIntake: () -> Unit = {
        val currentWaterIntake = totalWaterIntake.value
        if (currentWaterIntake >= 250) {
            val updatedWaterIntake = currentWaterIntake - 250 // Decrement by 250ml
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            dbHelper.insertWaterIntakeEntry(currentDate, updatedWaterIntake)
            totalWaterIntake.value = updatedWaterIntake
        }
    }

    Scaffold {
        WaterScreenScreen(
            waterIntake = totalWaterIntake.value,
            onIncreaseClicked = incrementWaterIntake,
            onDecreaseClicked = decrementWaterIntake,
            modifier = Modifier.padding(it)
        )
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "No Need to Drink More Water") },
            text = {
                Text(
                    text = "You have reached your daily water intake goal. Stay hydrated!"
                )
            },
            confirmButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}

@Composable
private fun rememberWaterIntakeEntries(dbHelper: DBHandler): List<WaterIntakeEntry> {
    val waterIntakeEntriesState = remember { mutableStateOf(emptyList<WaterIntakeEntry>()) }

    DisposableEffect(dbHelper) {
        val entries = dbHelper.getAllWaterIntakeEntries()
        waterIntakeEntriesState.value = entries
        onDispose { /* Clean-up logic */ }
    }

    return waterIntakeEntriesState.value
}

@Composable
private fun calculateTotalWaterIntake(waterIntakeEntries: List<WaterIntakeEntry>): MutableState<Int> {
    val totalWaterIntake = remember { mutableStateOf(0) }
    waterIntakeEntries.forEach { entry ->
        totalWaterIntake.value += entry.amount
    }
    return totalWaterIntake
}

@Composable
fun WaterScreenScreen(
    waterIntake: Int,
    onIncreaseClicked: () -> Unit,
    onDecreaseClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(R.drawable.waterglass),
                contentDescription = null,
                modifier = Modifier
                    .width(280.dp)
                    .height(280.dp)
                    .align(Alignment.Center)
                    .offset(x = (5).dp, y = (50).dp),
                contentScale = ContentScale.Fit,
            )
        }
        WaterIntakeInfo(
            waterIntake = waterIntake,
            onIncreaseClicked = onIncreaseClicked,
            onDecreaseClicked = onDecreaseClicked,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Composable
private fun WaterIntakeInfo(
    waterIntake: Int,
    onIncreaseClicked: () -> Unit,
    onDecreaseClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(colorResource(id = R.color.colorPrimary))
                    .clickable { onDecreaseClicked() },
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "Remove",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(colorResource(id = R.color.colorPrimaryDark))
                    .clickable {
                        if (waterIntake < 3000) {
                            onIncreaseClicked()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }

        Text(
            text = "One water cup is 250 ml",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.colorAccentDark)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )

        Text(
            text = stringResource(R.string.total_water),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.colorAccentDark)
            ),
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "$waterIntake ml",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color=  colorResource(id = R.color.colorAccentDark)

            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .offset(x = 280.dp, y = (-34).dp)
        )
    }
}

@Composable
fun NutriNoteApp() {
    WaterScreen()
}
