package com.arabyte.arabyteapi.domain.article.dto.article

import com.arabyte.arabyteapi.domain.article.dto.comment.CommentResponse

data class ArticleResponse(
    val nickname: String,
    val createdAt: String,
    val title: String,
    val text: String,
    val likeCount: Int,
    val commentCount: Int,
    val comments: List<CommentResponse>,
    val imageUrls: List<String>
)