package com.arabyte.arabyteapi.domain.article.controller

import com.arabyte.arabyteapi.domain.article.dto.ArticlePreviewResponse
import com.arabyte.arabyteapi.domain.article.dto.CreateArticleRequest
import com.arabyte.arabyteapi.domain.article.dto.CreateArticleResponse
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

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성하는 API 입니다.")
    @PostMapping
    fun createArticle(
        @RequestBody request: CreateArticleRequest
    ): ResponseEntity<CreateArticleResponse> {
        val response = articleService.createArticle(request)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "게시물 조회", description = "전체게시판, 자유게시판, 정보게시판의 게시물들을 조회하는 API입니다.")
    @GetMapping
    fun getArticles(
        @RequestParam(required = false) articleKind: ArticleKind?,
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<ArticlePreviewResponse>> {
        val articlePreviewList = articleService.getArticlePreviews(articleKind, pageable)
        return ResponseEntity.ok(articlePreviewList)
    }
}