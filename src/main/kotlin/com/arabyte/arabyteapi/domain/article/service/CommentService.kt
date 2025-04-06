package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.comment.*
import com.arabyte.arabyteapi.domain.article.entity.Comment
import com.arabyte.arabyteapi.domain.article.repository.CommentRepository
import com.arabyte.arabyteapi.domain.user.service.UserService
import com.arabyte.arabyteapi.global.exception.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val articleService: ArticleService,
    private val userService: UserService
) {
    @Transactional
    fun createComment(request: CreateCommentRequest): CreateCommentResponse {
        val article = articleService.getArticle(request.articleId)
        val user = userService.getUser(request.userId)

        val parent = request.parentId?.let {
            commentRepository.findById(it).orElseThrow { CustomException(CustomError.PARENT_COMMENT_NOT_FOUND) }
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

    fun updateComment(commentId: Long, request: UpdateCommentRequest): UpdateCommentResponse {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { CustomException(CustomError.COMMENT_NOT_FOUND) }

        comment.text = request.text
        comment.isAnonymous = request.isAnonymous

        commentRepository.save(comment)

        return UpdateCommentResponse(
            commentId = commentId,
            message = "${commentId}번 댓글이 수정되었습니다."
        )
    }

    fun deleteComment(commentId: Long): DeleteCommentResponse {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { CustomException(CustomError.COMMENT_NOT_FOUND) }

        commentRepository.delete(comment)

        return DeleteCommentResponse(
            commentId = comment.id,
            message = "${commentId}번 댓글이 삭제되었습니다."
        )
    }
}