package com.arabyte.arabyteapi.domain.article.dto

import com.arabyte.arabyteapi.domain.article.enums.ArticleKind

data class UpdateArticleRequest(
    val title: String,
    val text: String,
    val isAnonymous: Boolean,
    val articleKind: ArticleKind
)