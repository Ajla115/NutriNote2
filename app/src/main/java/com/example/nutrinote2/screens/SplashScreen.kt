package com.example.nutrinote2.screens


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrinote2.R
import com.example.nutrinote2.ui.theme.Shapes


@Composable
fun SplashScreen(
    onLoginButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            colorResource(id = R.color.colorPrimaryDark)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())


        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "NutriNote",
                fontWeight = FontWeight.Bold,
                fontSize = 64.sp,
                color = colorResource(id = R.color.colorAccentDark),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 70.dp)


            )

            Spacer(modifier = Modifier.height(75.dp))

            Button(
                onClick = onRegisterButtonClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 1.dp)
                    .background(color = colorResource(id = R.color.colorPrimary))
                    .clip(RoundedCornerShape(50.dp)),
                contentPadding = PaddingValues(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF1B7F79),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Register",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }


            Button(
                onClick = onLoginButtonClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 1.dp)
                    .clip(RoundedCornerShape(50.dp)),
                contentPadding = PaddingValues(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.colorAccentDark),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.BottomCenter)
                .background(color = Color.Transparent)

        ) {

            Image(
                painter = painterResource(id = R.drawable.pngartboard_4_1),
                contentDescription = "Your Drawable",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}




/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SplashScreen( onLoginButtonClicked = {},
                  onRegisterButtonClicked = {},
                  modifier = Modifier)
}
*/



