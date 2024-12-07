package com.example.groupcascreennavigation


import androidx.activity.compose.setContent
import androidx.compose.material3.*

import androidx.compose.ui.res.stringResource

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.groupcascreennavigation.ui.NewsArticleContentScreen
import com.example.groupcascreennavigation.ui.NewsArticleScreen
import com.example.groupcascreennavigation.ui.data.getArticles


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(
    currentScreenTitle: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = currentScreenTitle) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val articles = remember { getArticles() }

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            NewsArticleScreen(navController = navController, articles = articles)
        }
        composable("details/{articleId}") { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId")
            NewsArticleContentScreen(articleId = articleId, articles = articles) {
                navController.navigateUp()
            }
        }
    }
}