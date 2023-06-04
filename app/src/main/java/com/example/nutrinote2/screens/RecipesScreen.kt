package com.example.nutrinote2.screens

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrinote2.R
import com.example.nutrinote2.data.Recipe
import com.example.nutrinote2.data.recipes


@Composable
fun RecipesScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set the background color to white
    ) {
        items(recipes) {
            RecipeItem(
                recipe = it,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}



@Composable
fun RecipeItem(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.White, // Set the background color of the Surface to white
        shape = MaterialTheme.shapes.medium, // Apply rounded corners to the Surface
        elevation = 4.dp // Apply elevation to the Surface
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            RecipeIcon(recipe.imageResourceId)
            RecipeInformation(recipe.name, recipe.info_link)
        }
    }
}

@Composable
fun RecipeIcon(
    @DrawableRes recipeIcon: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.image_size))
            .clip(CircleShape)
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(recipeIcon),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }

    // Content Description is not needed here - image is decorative, and setting a null content
    // description allows accessibility services to skip this element during navigation.


}

@Composable
fun RecipeInformation(
    recipeName: Int,
    infoLink: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        androidx.compose.material3.Text(
            text = stringResource(id = recipeName),
            fontWeight = FontWeight.Bold,
            fontSize= 20.sp,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
        )
        androidx.compose.material3.Text(
            text = stringResource(R.string.info_link),
            fontSize=16.sp,
            //style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(infoLink))
                context.startActivity(intent)
            }
        )
    }
}

//Deafult verzija
/*@Composable
fun RecipesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Movies View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    RecipesScreen()
}*/