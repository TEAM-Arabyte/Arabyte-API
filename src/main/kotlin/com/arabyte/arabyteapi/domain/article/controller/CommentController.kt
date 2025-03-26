package com.arabyte.arabyteapi.domain.article.controller

import com.arabyte.arabyteapi.domain.article.dto.comment.CreateCommentRequest
import com.arabyte.arabyteapi.domain.article.dto.comment.CreateCommentResponse
import com.arabyte.arabyteapi.domain.article.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/comments")
class CommentController(
    private val commentService: CommentService
) {

    @Operation(summary = "댓글 생성", description = "새로운 댓글을 생성하는 API 입니다.")
    @PostMapping
    fun createComment(
        @RequestBody request: CreateCommentRequest
    ): ResponseEntity<CreateCommentResponse> {
        val response = commentService.createComment(request)
        return ResponseEntity.ok(response)
    }
}