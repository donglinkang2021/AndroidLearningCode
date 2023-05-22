package com.linkdom.drawapp.ui.home

import com.linkdom.drawapp.base.use
import com.linkdom.drawapp.data.model.Screen
import com.linkdom.drawapp.ui.Destinations
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkdom.drawapp.ui.DigitalInkScreen
import com.linkdom.drawapp.ui.DigitalInkViewModelImpl
import com.linkdom.drawapp.ui.LocalDigitalInkViewModel
import com.linkdom.drawapp.ui.theme.DrawAppTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDestination: (Destinations) -> Unit
) {

    val (state, _) = use(LocalHomeViewModel.current, HomeViewModel.State())

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(state.screens) { index, screen ->

                ScreenCard(
                    screen = screen,
                    onClick = { navigateToDestination.invoke(it) },
                    modifier = Modifier.padding(
                        top = if (index == 0) 16.dp else 0.dp,
                        bottom = 16.dp
                    )
                )
            }
        }
    }
}

@Composable
fun ScreenCard(
    screen: Screen,
    modifier: Modifier = Modifier,
    onClick: (Destinations) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .clickable { onClick.invoke(screen.destinations) }
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .weight(2f)
                .padding(16.dp)
        ) {
            Text(
                text = screen.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = screen.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ScreenCardPreview() {
    val navController = rememberNavController()

    HomeScreen(
        navigateToDestination = { navController.navigate(route = it.value) },
        modifier = Modifier.fillMaxSize()
    )
}