package com.arabyte.arabyteapi.domain.mypage.service

import com.arabyte.arabyteapi.domain.article.dto.ArticlePreviewResponse
import com.arabyte.arabyteapi.domain.article.service.ArticleLikeService
import com.arabyte.arabyteapi.domain.article.service.ArticleService
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class MyPageService(
    private val articleLikeService: ArticleLikeService,
    private val articleService: ArticleService
) {
    fun getMyPageArticles(type: String, user: User, page: Int, size: Int): Page<ArticlePreviewResponse> {
        val articles = when (type) {
            "my" -> articleService.getMyArticles(user, page, size)
            "like" -> articleLikeService.getLikedArticles(user, page, size)
            else -> throw CustomException(CustomError.INVALID_URL)
        }

        val articleIds = articles.map { it.id }.toList()
        val likedArticleIds = articleLikeService.findLikedArticleIds(user.id, articleIds)

        return articles.map { article ->
            val commentCount = article.comments.size
            val uploadAt = articleService.getPreviewUploadTime(article.createdAt)

            ArticlePreviewResponse(
                articleId = article.id,
                title = article.title,
                text = article.text,
                likeCount = article.likeCount,
                commentCount = commentCount,
                uploadAt = uploadAt,
                thumbnailImage = article.images.firstOrNull()?.url,
                articleKind = article.articleKindId,
                isLiked = likedArticleIds.contains(article.id),
            )
        }

    }
}