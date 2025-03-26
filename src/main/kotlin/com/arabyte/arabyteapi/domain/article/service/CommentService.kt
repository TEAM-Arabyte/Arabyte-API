package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.comment.CreateCommentRequest
import com.arabyte.arabyteapi.domain.article.dto.comment.CreateCommentResponse
import com.arabyte.arabyteapi.domain.article.entity.Comment
import com.arabyte.arabyteapi.domain.article.repository.ArticleRepository
import com.arabyte.arabyteapi.domain.article.repository.CommentRepository
import com.arabyte.arabyteapi.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createComment(request: CreateCommentRequest): CreateCommentResponse {
        val article = articleRepository.findById(request.articleId)
            .orElseThrow { IllegalArgumentException("게시글이 존재하지 않습니다.") }

        val user = userRepository.findById(request.userId)
            .orElseThrow { IllegalArgumentException("사용자가 존재하지 않습니다.") }

        val parent = request.parentId?.let {
            commentRepository.findById(it).orElseThrow { IllegalArgumentException("부모 댓글이 존재하지 않습니다.") }
        }

        val comment = Comment(
            article = article,
            user = user,
            parent = parent,
            text = request.text,
            isAnonymous = request.isAnonymous
        )

        val saved = commentRepository.save(comment)
        return CreateCommentResponse(
            commentId = saved.id,
            message = "댓글이 성공적으로 등록되었습니다."
        )
    }
}