package com.baga.coroutinebehaviours

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import com.baga.coroutinebehaviours.cancellation.AboutUI
import com.baga.coroutinebehaviours.ui.theme.CoroutineBehavioursTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutineBehavioursTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Greeting(name: String) {
    val navController = rememberAnimatedNavController()
    val offsetSignedValue = 1000
    AnimatedNavHost(navController = navController, startDestination = "Home", route = "test") {
        composable("Home",
            popEnterTransition = { initial: NavBackStackEntry, target: NavBackStackEntry ->
                println("AnimatedNavHost Home ${initial.destination.route}")
                slideInHorizontally(
                    initialOffsetX = {
                        -offsetSignedValue
                    },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },exitTransition = { _,_ ->
                slideOutHorizontally(
                    targetOffsetX = {
                        -offsetSignedValue
                    },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }) {
            CoroutineBehaviourUI(navController)
        }
        composable(
            route = "CancellableAbout",
            enterTransition = { initial: NavBackStackEntry, target: NavBackStackEntry ->
                println("AnimatedNavHost CancellableAbout enterTransition ${initial.destination.route}")
                slideInHorizontally(
                    initialOffsetX = { offsetSignedValue }
                )+ fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {_,_ ->
                slideOutHorizontally(
                    targetOffsetX = {
                        offsetSignedValue
                    },
                    animationSpec = tween(300)
                )+ fadeOut(animationSpec = tween(300))
            }
        ) {
            AboutUI()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoroutineBehavioursTheme {
        Greeting("Android")
    }
}