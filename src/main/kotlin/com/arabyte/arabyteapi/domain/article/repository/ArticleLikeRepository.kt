package com.arabyte.arabyteapi.domain.article.repository

import com.arabyte.arabyteapi.domain.article.entity.ArticleLike
import com.arabyte.arabyteapi.domain.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleLikeRepository : JpaRepository<ArticleLike, Long> {
    fun findByArticleIdAndUserId(articleId: Long, userId: Long): ArticleLike?
    fun findByUserIdAndArticleIdIn(userId: Long, articleIds: List<Long>): List<ArticleLike>
    fun findByUserOrderByCreatedAtDesc(user: User, pageable: Pageable): Page<ArticleLike>
}