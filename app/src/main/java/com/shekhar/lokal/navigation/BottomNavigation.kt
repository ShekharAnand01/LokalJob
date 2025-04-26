package com.shekhar.lokal.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shekhar.lokal.R
import com.shekhar.lokal.screens.BookMarkPage
import com.shekhar.lokal.screens.HomePage
import com.shekhar.lokal.viewmodel.JobViewModel

@Composable
fun HomeNavigation(navController: NavController, viewModal: JobViewModel) {
    val bottomNavController = rememberNavController()
    val currentEntry = bottomNavController.currentBackStackEntryAsState().value
    val currentRoute = currentEntry?.destination?.route

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModal.errorChannel.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                when (currentRoute) {
                    HomeScreen::class.qualifiedName -> "Jobs"
                    else -> "Bookmarks"
                }
            )
        },
        bottomBar = { ChatroomBottomNavigation(bottomNavController) },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->

        NavHost(
            navController = bottomNavController,
            startDestination = HomeScreen
        ) {
            composable<HomeScreen> {
                HomePage(navController, viewModal, modifier = Modifier.padding(paddingValues))
            }

            composable<BookmarkScreen> {
                BookMarkPage(navController, viewModal, modifier = Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun ChatroomBottomNavigation(navController: NavController) {

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavItemState(
            title = "Jobs",
            selectedIcon = R.drawable.home,
            route = HomeScreen
        ),
        BottomNavItemState(
            title = "Bookmarks",
            selectedIcon = R.drawable.bookmark,
            route = BookmarkScreen
        )
    )

    LaunchedEffect(selectedItem) {
        navController.navigate(items[selectedItem].route) {
            popUpTo(0) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.selectedIcon),
                        contentDescription = item.title,
                        tint = if (index == selectedItem) Color.Green else Color.Unspecified
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (index == selectedItem) Color.Green else Color.Gray
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                },
                alwaysShowLabel = false
            )


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(title: String) {

    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Medium, fontFamily = FontFamily.SansSerif) },
        navigationIcon = {},
        modifier = Modifier
    )
}