package com.arabyte.arabyteapi.domain.article.controller

import com.arabyte.arabyteapi.domain.article.dto.*
import com.arabyte.arabyteapi.domain.article.enums.ArticleKind
import com.arabyte.arabyteapi.domain.article.service.ArticleService
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.annotation.RequestUser
import com.arabyte.arabyteapi.global.annotation.SwaggerCustomException
import com.arabyte.arabyteapi.global.enums.CustomExceptionGroup
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/articles")
class ArticleController(
    private val articleService: ArticleService
) {

    @Operation(summary = "게시물 생성", description = "새로운 게시글을 생성하는 API 입니다.")
    @PostMapping
    @SwaggerCustomException(CustomExceptionGroup.ARTICLE_CREATE)
    fun createArticle(
        @RequestUser user: User,
        @RequestBody request: CreateArticleRequest
    ): CreateArticleResponse {
        return articleService.createArticle(user, request)
    }

    @Operation(summary = "게시물 목록 조회", description = "전체게시판, 자유게시판, 정보게시판의 게시물 개요를 조회하는 API입니다.")
    @GetMapping
    @SwaggerCustomException(CustomExceptionGroup.ARTICLE_LIST)
    fun getArticles(
        @RequestUser user: User,
        @RequestParam(required = false) articleKind: ArticleKind?,
        @PageableDefault(
            size = 10,
            sort = ["createdAt"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable
    ): Page<ArticlePreviewResponse> {
        return articleService.getArticlePreviews(user, articleKind, pageable)
    }

    @Operation(summary = "게시글 상세 조회", description = "게시물과 해당 댓글들을 함께 반환합니다.")
    @GetMapping("/{articleId}")
    @SwaggerCustomException(CustomExceptionGroup.ARTICLE_DETAIL)
    fun getArticleDetail(
        @RequestUser user: User,
        @PathVariable articleId: Long
    ): ArticleResponse {
        return articleService.getArticleDetail(user, articleId)
    }

    @Operation(summary = "게시물 수정", description = "게시물을 수정하는 API입니다.")
    @PutMapping("/{articleId}")
    @SwaggerCustomException(CustomExceptionGroup.ARTICLE_UPDATE)
    fun updateArticle(
        @RequestUser user: User,
        @PathVariable articleId: Long,
        @RequestBody request: UpdateArticleRequest
    ): UpdateArticleResponse {
        return articleService.updateArticle(user, articleId, request)
    }

    @Operation(summary = "게시물 삭제", description = "게시물을 삭제하는 API입니다.")
    @DeleteMapping("/{articleId}")
    @SwaggerCustomException(CustomExceptionGroup.ARTICLE_DELETE)
    fun deleteArticle(
        @RequestUser user: User,
        @PathVariable articleId: Long,
    ): DeleteArticleResponse {
        return articleService.deleteArticle(user, articleId)
    }

    @Operation(summary = "게시물 좋아요", description = "게시물 좋아요 토글 API")
    @PostMapping("/like")
    @SwaggerCustomException(CustomExceptionGroup.ARTICLE_LIKE)
    fun toggleLike(
        @RequestUser user: User,
        @RequestBody request: ArticleLikeRequest
    ): ArticleLikeResponse {
        return articleService.toggleLike(user, request)
    }
}