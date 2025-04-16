package com.arabyte.arabyteapi.domain.article.dto

import com.arabyte.arabyteapi.domain.comment.dto.CommentResponse

data class ArticleResponse(
    val articleId: Long,
    val nickname: String,
    val createdAt: String,
    val title: String,
    val text: String,
    val likeCount: Int,
    val commentCount: Int,
    val comments: List<CommentResponse>,
    val imageUrls: List<String>
)