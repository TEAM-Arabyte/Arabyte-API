package com.arabyte.arabyteapi.domain.article.controller

import com.arabyte.arabyteapi.domain.article.dto.article.*
import com.arabyte.arabyteapi.domain.article.enums.ArticleKind
import com.arabyte.arabyteapi.domain.article.service.ArticleService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/articles")
class ArticleController(
    private val articleService: ArticleService
) {

    @Operation(summary = "게시물 생성", description = "새로운 게시글을 생성하는 API 입니다.")
    @PostMapping
    fun createArticle(
        @RequestBody request: CreateArticleRequest
    ): ResponseEntity<CreateArticleResponse> {
        val response = articleService.createArticle(request)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "게시물 목록 조회", description = "전체게시판, 자유게시판, 정보게시판의 게시물 개요를 조회하는 API입니다.")
    @GetMapping
    fun getArticles(
        @RequestParam(required = false) articleKind: ArticleKind?,
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<ArticlePreviewResponse>> {
        val articlePreviewList = articleService.getArticlePreviews(articleKind, pageable)
        return ResponseEntity.ok(articlePreviewList)
    }

    @GetMapping("/{articleId}")
    @Operation(summary = "게시글 상세 조회", description = "게시물과 해당 댓글들을 함께 반환합니다.")
    fun getArticleDetail(
        @PathVariable articleId: Long
    ): ResponseEntity<ArticleResponse> {
        val response = articleService.getArticleDetail(articleId)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "게시물 수정", description = "게시물을 수정하는 API입니다.")
    @PutMapping("/{articleId}")
    fun updateArticle(
        @PathVariable articleId: Long,
        @RequestBody request: UpdateArticleRequest
    ): ResponseEntity<Map<String, String>> {
        articleService.updateArticle(articleId, request)
        return ResponseEntity.ok(mapOf("message" to "${articleId}번 게시물이 수정되었습니다."))
    }

    @Operation(summary = "게시물 삭제", description = "게시물을 삭제하는 API입니다.")
    @DeleteMapping("/{articleId}")
    fun deleteArticle(
        @PathVariable articleId: Long,
    ): ResponseEntity<Map<String, String>> {
        articleService.deleteArticle(articleId)
        return ResponseEntity.ok(mapOf("message" to "${articleId}번 게시물이 삭제되었습니다."))
    }
}