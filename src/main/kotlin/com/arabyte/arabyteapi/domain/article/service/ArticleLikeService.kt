package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.article.ArticleLikeRequest
import com.arabyte.arabyteapi.domain.article.dto.article.ArticleLikeResponse
import com.arabyte.arabyteapi.domain.article.entity.ArticleLike
import com.arabyte.arabyteapi.domain.article.repository.ArticleLikeRepository
import com.arabyte.arabyteapi.domain.article.repository.ArticleRepository
import com.arabyte.arabyteapi.global.exception.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ArticleLikeService(
    private val articleRepository: ArticleRepository,
    private val articleLikeRepository: ArticleLikeRepository
) {
    @Transactional
    fun toggleLike(request: ArticleLikeRequest): ArticleLikeResponse {
        val article = articleRepository.findById(request.articleId)
            .orElseThrow { CustomException(CustomError.ARTICLE_NOT_FOUND) }

        val existing = articleLikeRepository.findByArticleIdAndUserId(article.id, request.userId)

        return if (existing != null) {
            articleLikeRepository.delete(existing)
            article.likeCount -= 1
            ArticleLikeResponse(false, article.likeCount)
        } else {
            articleLikeRepository.save(ArticleLike(article, request.userId))
            article.likeCount += 1
            ArticleLikeResponse(true, article.likeCount)
        }
    }
}
