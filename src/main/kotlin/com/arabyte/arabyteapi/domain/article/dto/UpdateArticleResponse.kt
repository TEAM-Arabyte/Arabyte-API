package com.arabyte.arabyteapi.domain.article.dto

import com.arabyte.arabyteapi.domain.article.entity.Article

data class UpdateArticleResponse(
    val articleId: Long
) {
    companion object {
        fun of(article: Article): UpdateArticleResponse {
            return UpdateArticleResponse(
                articleId = article.id
            )
        }
    }
}