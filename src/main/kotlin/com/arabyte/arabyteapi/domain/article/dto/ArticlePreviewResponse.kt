package com.arabyte.arabyteapi.domain.article.dto

import com.arabyte.arabyteapi.domain.article.entity.Article
import com.arabyte.arabyteapi.domain.article.enums.ArticleKind

data class ArticlePreviewResponse(
    val articleId: Long,
    val title: String,
    val text: String,
    val likeCount: Int,
    val commentCount: Int,
    val uploadAt: String,
    val thumbnailImage: String?,
    val articleKind: ArticleKind,
    val isLiked: Boolean
) {
    companion object {
        fun of(article: Article, uploadAt: String, isLiked: Boolean): ArticlePreviewResponse {
            return ArticlePreviewResponse(
                articleId = article.id,
                title = article.title,
                text = article.text,
                likeCount = article.likeCount,
                commentCount = article.comments.size,
                uploadAt = uploadAt,
                thumbnailImage = article.images.firstOrNull()?.url,
                articleKind = article.articleKindId,
                isLiked = isLiked
            )
        }
    }
}
