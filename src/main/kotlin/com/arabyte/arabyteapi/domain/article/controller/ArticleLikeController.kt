package com.arabyte.arabyteapi.domain.article.controller

import com.arabyte.arabyteapi.domain.article.dto.article.ArticleLikeRequest
import com.arabyte.arabyteapi.domain.article.dto.article.ArticleLikeResponse
import com.arabyte.arabyteapi.domain.article.service.ArticleLikeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/articles/like")
class ArticleLikeController(
    private val articleLikeService: ArticleLikeService
) {
    @Operation(summary = "게시물 좋아요", description = "게시물 좋아요 토글 API")
    @PostMapping
    fun toggleLike(
        @RequestBody request: ArticleLikeRequest
    ): ResponseEntity<ArticleLikeResponse> {
        val response = articleLikeService.toggleLike(request)
        return ResponseEntity.ok(response)
    }
}