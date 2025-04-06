package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.article.ArticleLikeRequest
import com.arabyte.arabyteapi.domain.article.dto.article.ArticleLikeResponse
import com.arabyte.arabyteapi.domain.article.entity.ArticleLike
import com.arabyte.arabyteapi.domain.article.repository.ArticleLikeRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ArticleLikeService(
    private val articleService: ArticleService,
    private val articleLikeRepository: ArticleLikeRepository
) {
    @Transactional
    fun toggleLike(request: ArticleLikeRequest): ArticleLikeResponse {
        val article = articleService.getArticle(request.articleId)
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
