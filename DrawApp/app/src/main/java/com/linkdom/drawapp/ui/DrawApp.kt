package com.linkdom.drawapp.ui


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.linkdom.drawapp.ui.theme.DrawAppTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkdom.drawapp.ui.home.HomeScreen
import com.linkdom.drawapp.ui.home.HomeViewModelImpl
import com.linkdom.drawapp.ui.home.LocalHomeViewModel
import dagger.hilt.android.HiltAndroidApp

@Preview(showBackground = true)
@Composable
fun DrawApp2() {

    val navController = rememberNavController()

    DrawAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            NavHost(navController = navController, startDestination = "home") {
                composable(route = Destinations.Home.value) {
                    CompositionLocalProvider(
                        LocalHomeViewModel provides hiltViewModel<HomeViewModelImpl>()
                    ) {
                        HomeScreen(
                            navigateToDestination = { navController.navigate(route = it.value) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                composable(route = "digital_ink") {
                    CompositionLocalProvider(
                        LocalDigitalInkViewModel provides  hiltViewModel<DigitalInkViewModelImpl>(),
                    ) {
                        DigitalInkScreen(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DrawApp(){
//    val viewModel = hiltViewModel<DigitalInkViewModelImpl>()
    val navController = rememberNavController()
    DrawAppTheme(){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            NavHost(navController = navController, startDestination = "home") {
                composable(route = Destinations.Home.value) {
                    CompositionLocalProvider(
                        LocalHomeViewModel provides hiltViewModel<HomeViewModelImpl>()
                    ) {
                        HomeScreen(
                            navigateToDestination = { navController.navigate(route = it.value) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}