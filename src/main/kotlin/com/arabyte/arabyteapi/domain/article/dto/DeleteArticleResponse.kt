package com.arabyte.arabyteapi.domain.article.dto

import com.arabyte.arabyteapi.domain.article.entity.Article

data class DeleteArticleResponse(
    val articleId: Long
) {
    companion object {
        fun of(article: Article): DeleteArticleResponse {
            return DeleteArticleResponse(
                articleId = article.id
            )
        }
    }
}