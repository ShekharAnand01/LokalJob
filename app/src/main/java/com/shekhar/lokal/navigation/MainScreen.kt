package com.shekhar.lokal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shekhar.lokal.screens.JobDetailPage
import com.shekhar.lokal.viewmodel.JobViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val viewModal = koinViewModel<JobViewModel>()

    NavHost(
        navController, startDestination = MainScreen
    ) {

        composable<MainScreen> {
            HomeNavigation(navController, viewModal)
        }

        composable<DetailScreen> { backStackEntry ->
            val job = backStackEntry.toRoute<DetailScreen>()
            JobDetailPage(navController, viewModal, job.id)
        }

    }
}

@Serializable
object MainScreen

@Serializable
object HomeScreen

@Serializable
object BookmarkScreen

@Serializable
data class DetailScreen(val id: Int)