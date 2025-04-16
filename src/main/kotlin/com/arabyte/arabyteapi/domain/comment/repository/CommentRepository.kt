package com.arabyte.arabyteapi.domain.comment.repository

import com.arabyte.arabyteapi.domain.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByArticleId(articleId: Long): List<Comment>
}