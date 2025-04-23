package com.arabyte.arabyteapi.domain.comment.service

import com.arabyte.arabyteapi.domain.article.service.ArticleService
import com.arabyte.arabyteapi.domain.comment.dto.*
import com.arabyte.arabyteapi.domain.comment.entity.Comment
import com.arabyte.arabyteapi.domain.comment.repository.CommentRepository
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.service.UserService
import com.arabyte.arabyteapi.global.enums.CustomError
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
    fun createComment(user: User, request: CreateCommentRequest): CreateCommentResponse {
        val article = articleService.getArticles(request.articleId)

        val parent = request.parentId?.let {
            commentRepository.findById(it)
                .orElseThrow { CustomException(CustomError.PARENT_COMMENT_NOT_FOUND) }
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

    fun updateComment(
        user: User,
        commentId: Long,
        request: UpdateCommentRequest
    ): UpdateCommentResponse {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { CustomException(CustomError.COMMENT_NOT_FOUND) }

        if (comment.user.id != user.id) {
            throw CustomException(CustomError.COMMENT_FORBIDDEN)
        }

        comment.text = request.text
        comment.isAnonymous = request.isAnonymous

        commentRepository.save(comment)

        return UpdateCommentResponse(
            commentId = commentId,
            message = "${commentId}번 댓글이 수정되었습니다."
        )
    }

    fun deleteComment(user: User, commentId: Long): DeleteCommentResponse {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { CustomException(CustomError.COMMENT_NOT_FOUND) }

        if (comment.user.id != user.id) {
            throw CustomException(CustomError.COMMENT_FORBIDDEN)
        }

        commentRepository.delete(comment)

        return DeleteCommentResponse(
            commentId = comment.id,
            message = "${commentId}번 댓글이 삭제되었습니다."
        )
    }
}