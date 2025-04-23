package com.arabyte.arabyteapi.domain.mypage.controller

import com.arabyte.arabyteapi.domain.article.dto.ArticlePreviewResponse
import com.arabyte.arabyteapi.domain.mypage.service.MyPageService
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.annotation.RequestUser
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mypage")
class MyPageController(
    private val myPageService: MyPageService,
) {
    @Operation(
        summary = "마이페이지 게시물 조회",
        description = "/articles/my로 요청하면 내가 쓴 게시물, /article/like로 요청하면 내가 좋아요를 누른 게시물을 반환합니다."
    )
    @GetMapping("/articles/{type}")
    fun getMyArticles(
        @PathVariable type: String,
        @RequestUser user: User,
        @RequestParam page: Int,
        @RequestParam size: Int
    ): Page<ArticlePreviewResponse> {
        return myPageService.getMyPageArticles(type, user, page, size)
    }
}