package com.arabyte.arabyteapi.domain.article.controller

import com.arabyte.arabyteapi.domain.article.dto.comment.*
import com.arabyte.arabyteapi.domain.article.service.CommentService
import com.arabyte.arabyteapi.global.annotation.SwaggerCustomException
import com.arabyte.arabyteapi.global.enum.CustomExceptionGroup
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(
    private val commentService: CommentService
) {

    @Operation(summary = "댓글 생성", description = "새로운 댓글을 생성하는 API 입니다.")
    @PostMapping("/{articleId}")
    @SwaggerCustomException(CustomExceptionGroup.COMMENT_CREATE)
    fun createComment(
        @RequestBody request: CreateCommentRequest
    ): CreateCommentResponse {
        return commentService.createComment(request)
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정하는 API입니다.")
    @PutMapping("/{commentId}")
    @SwaggerCustomException(CustomExceptionGroup.COMMENT_UPDATE)
    fun updateArticle(
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest
    ): UpdateCommentResponse {
        return commentService.updateComment(commentId, request);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제하는 API입니다.")
    @DeleteMapping("/{commentId}")
    @SwaggerCustomException(CustomExceptionGroup.COMMENT_DELETE)
    fun deleteArticle(
        @PathVariable commentId: Long,
    ): DeleteCommentResponse {
        return commentService.deleteComment(commentId)
    }
}