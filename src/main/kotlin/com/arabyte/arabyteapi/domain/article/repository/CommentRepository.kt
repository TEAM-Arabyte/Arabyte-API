package com.arabyte.arabyteapi.domain.article.repository

import com.arabyte.arabyteapi.domain.article.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByArticleId(articleId: Long): List<Comment>
}