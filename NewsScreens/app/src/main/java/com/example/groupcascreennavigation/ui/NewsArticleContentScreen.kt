package com.example.groupcascreennavigation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.groupcascreennavigation.NewsAppBar
import com.example.groupcascreennavigation.ui.data.Article

@Composable
fun NewsArticleContentScreen(articleId: String?, articles: List<Article>, navigateUp: () -> Unit) {
    val article = articles.find { it.id == articleId }
    Scaffold(
        topBar = {
            if (article != null) {
                NewsAppBar(
                    currentScreenTitle = article.title,
                    canNavigateBack = true,
                    navigateUp = navigateUp
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (article != null) {
                Image(
                    painter = rememberAsyncImagePainter(article.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
            if (article != null) {
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}