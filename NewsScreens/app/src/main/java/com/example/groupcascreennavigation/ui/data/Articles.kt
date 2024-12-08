package com.example.groupcascreennavigation.ui.data

data class Article(val id: String, val title: String, val content: String, val imageUrl: String)

fun getArticles(): List<Article> {
    return listOf(
        Article(
            id = "1",
            title = "Election News",
            content = "There have been some interesting events regarding elections.",
            imageUrl = "https://ichef.bbci.co.uk/news/1024/cpsprodpb/13BF6/production/_132668808_gettyimages-1368404093.jpg.webp"
        ),
        Article(
            id = "2",
            title = "Economy News",
            content = "There have been some interesting events regarding the economy.",
            imageUrl = "https://plus.unsplash.com/premium_photo-1681487769650-a0c3fbaed85a?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8ZWNvbm9teXxlbnwwfHwwfHx8MA%3D%3D"
        ),
        Article(
            id = "3",
            title = "Sports News",
            content = "There have been some interesting events regarding sports.",
            imageUrl = "https://images.pexels.com/photos/46798/the-ball-stadion-football-the-pitch-46798.jpeg?auto=compress&cs=tinysrgb&w=600"
        ),
        Article(
            id = "4",
            title = "Crime News",
            content = "There have been some interesting events regarding crime.",
            imageUrl = "https://images.pexels.com/photos/532001/pexels-photo-532001.jpeg?auto=compress&cs=tinysrgb&w=600"
        )
    )
}
