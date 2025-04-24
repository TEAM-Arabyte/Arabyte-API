package com.arabyte.arabyteapi.domain.mypage.controller

import com.arabyte.arabyteapi.domain.article.dto.ArticlePreviewResponse
import com.arabyte.arabyteapi.domain.mypage.dto.MyPageResponse
import com.arabyte.arabyteapi.domain.mypage.dto.UpdateBasicInfoResponse
import com.arabyte.arabyteapi.domain.mypage.dto.UpdateSubInfoResponse
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

    @Operation(summary = "닉네임 수정", description = "마이페이지에서 닉네임을 수정합니다.")
    @PatchMapping("/nickname/update")
    fun updateNickName(
        @RequestUser user: User,
        @RequestParam newNickname: String
    ): MyPageResponse {
        return myPageService.updateNickName(user, newNickname)
    }

    @Operation(
        summary = "마이페이지 기본 정보 수정",
        description = "사용자의 기본 정보(거주지, 나이, 성별)을 수정합니다."
    )
    @PatchMapping("/basic-info")
    fun upDateBasicInfo(
        @RequestUser user: User,
        @RequestBody request: UpdateBasicInfoResponse
    ): MyPageResponse {
        return myPageService.updateBasicInfo(user, request)
    }

    @Operation(
        summary = "마이페이지 부가 정보 수정",
        description = "사용자의 부가 정보(알바 경력, 관심 직종)을 수정합니다."
    )
    @PatchMapping("/sub-info")
    fun upDateSubInfo(
        @RequestUser user: User,
        @RequestBody request: UpdateSubInfoResponse
    ): MyPageResponse {
        return myPageService.updateSubInfo(user, request)
    }
}