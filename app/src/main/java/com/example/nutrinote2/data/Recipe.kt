package com.example.nutrinote2.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.nutrinote2.R

data class Recipe(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    val info_link: String,
)


val recipes = listOf(
    Recipe(R.drawable.recipe1, R.string.recipe1, "https://www.delish.com/cooking/recipe-ideas/a24489879/beef-and-broccoli-recipe/"),
    Recipe(R.drawable.recipe2, R.string.recipe2, "https://www.delish.com/cooking/recipe-ideas/a25861120/honey-walnut-shrimp-recipe/"),
    Recipe(R.drawable.recipe3, R.string.recipe3, "https://www.delish.com/cooking/recipe-ideas/recipes/a5658/black-bean-burgers-recipe/"),
    Recipe(R.drawable.recipe4, R.string.recipe4, "https://www.delish.com/cooking/recipe-ideas/recipes/a6868/chicken-tetrazzini-recipe/" ),
    Recipe(R.drawable.recipe5, R.string.recipe5, "https://www.delish.com/cooking/recipe-ideas/recipes/a51337/classic-lasagna-recipe/"),
    Recipe(R.drawable.recipe6, R.string.recipe6, "https://www.delish.com/cooking/recipe-ideas/a29786303/risotto-rice-recipe/"),
    Recipe(R.drawable.recipe7, R.string.recipe7, "https://www.delish.com/cooking/a22159055/garlic-butter-meatballs-recipe/"),
    Recipe(R.drawable.recipe8, R.string.recipe8, "https://www.delish.com/cooking/recipe-ideas/recipes/a55377/perfect-baked-sweet-potato-recipe/"),
    Recipe(R.drawable.recipe9, R.string.recipe9, "https://www.delish.com/cooking/recipe-ideas/recipes/a45774/chicken-bacon-and-spinach-spaghetti-recipe/"),
    Recipe(R.drawable.recipe10, R.string.recipe10, "https://www.delish.com/cooking/recipe-ideas/a43876922/crab-stuffed-avocado-recipe/")
)

