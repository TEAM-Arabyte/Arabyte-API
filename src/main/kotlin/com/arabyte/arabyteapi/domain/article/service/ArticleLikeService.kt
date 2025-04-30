package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.ArticleLikeResponse
import com.arabyte.arabyteapi.domain.article.entity.Article
import com.arabyte.arabyteapi.domain.article.entity.ArticleLike
import com.arabyte.arabyteapi.domain.article.repository.ArticleLikeRepository
import com.arabyte.arabyteapi.domain.user.entity.User
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ArticleLikeService(
    private val articleLikeRepository: ArticleLikeRepository,
) {
    @Transactional
    fun toggleLike(user: User, article: Article): ArticleLikeResponse {
        val existing = articleLikeRepository.findByArticleIdAndUserId(article.id, user.id)

        return if (existing != null) {
            articleLikeRepository.delete(existing)
            article.likeCount -= 1
            ArticleLikeResponse(false, article.likeCount)
        } else {
            articleLikeRepository.save(ArticleLike(article, user))
            article.likeCount += 1
            ArticleLikeResponse(true, article.likeCount)
        }
    }

    fun findLikedArticleIds(userId: Long, articleIds: List<Long>): Set<Long> {
        return articleLikeRepository
            .findByUserIdAndArticleIdIn(userId, articleIds)
            .map { it.article.id }
            .toSet()
    }

    fun isArticleLikedByUser(userId: Long, articleId: Long): Boolean {
        return articleLikeRepository.findByArticleIdAndUserId(articleId, userId) != null
    }

    fun getLikedArticles(user: User, page: Int, size: Int): Page<Article> {
        return articleLikeRepository
            .findByUserOrderByCreatedAtDesc(user, PageRequest.of(page, size))
            .map { it.article }
    }
}
