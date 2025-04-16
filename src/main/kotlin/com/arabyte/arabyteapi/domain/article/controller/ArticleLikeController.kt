package com.arabyte.arabyteapi.domain.article.controller

import com.arabyte.arabyteapi.domain.article.dto.ArticleLikeRequest
import com.arabyte.arabyteapi.domain.article.dto.ArticleLikeResponse
import com.arabyte.arabyteapi.domain.article.service.ArticleLikeService
import com.arabyte.arabyteapi.global.annotation.SwaggerCustomException
import com.arabyte.arabyteapi.global.enums.CustomExceptionGroup
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles/like")
class ArticleLikeController(
    private val articleLikeService: ArticleLikeService
) {
    @Operation(summary = "게시물 좋아요", description = "게시물 좋아요 토글 API")
    @PostMapping
    @SwaggerCustomException(CustomExceptionGroup.ARTICLE_LIKE)
    fun toggleLike(
        @RequestBody request: ArticleLikeRequest
    ): ArticleLikeResponse {
        return articleLikeService.toggleLike(request)
    }
}