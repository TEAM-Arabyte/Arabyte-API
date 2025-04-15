package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.article.ArticleLikeRequest
import com.arabyte.arabyteapi.domain.article.dto.article.ArticleLikeResponse
import com.arabyte.arabyteapi.domain.article.entity.ArticleLike
import com.arabyte.arabyteapi.domain.article.repository.ArticleLikeRepository
import com.arabyte.arabyteapi.domain.user.service.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ArticleLikeService(
    private val articleService: ArticleService,
    private val articleLikeRepository: ArticleLikeRepository,
    private val userService: UserService
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
            val user = userService.getUserByUserId(request.userId)
            articleLikeRepository.save(ArticleLike(article, user))
            article.likeCount += 1
            ArticleLikeResponse(true, article.likeCount)
        }
    }
}
