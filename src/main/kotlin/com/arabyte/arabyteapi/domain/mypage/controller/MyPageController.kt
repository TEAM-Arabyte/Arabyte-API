package com.arabyte.arabyteapi.domain.mypage.controller

import com.arabyte.arabyteapi.domain.article.dto.ArticlePreviewResponse
import com.arabyte.arabyteapi.domain.mypage.dto.GetUserInfoResponse
import com.arabyte.arabyteapi.domain.mypage.dto.MyPageResponse
import com.arabyte.arabyteapi.domain.mypage.dto.OcrVerificationResponse
import com.arabyte.arabyteapi.domain.mypage.dto.UpdateBasicInfoRequest
import com.arabyte.arabyteapi.domain.mypage.dto.UpdateSubInfoRequest
import com.arabyte.arabyteapi.domain.mypage.enums.MyPageArticleType
import com.arabyte.arabyteapi.domain.mypage.service.ContractService
import com.arabyte.arabyteapi.domain.mypage.service.MyPageService
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.annotation.RequestUser
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/mypage")
class MyPageController(
    private val myPageService: MyPageService,
    private val contractService: ContractService
) {
    @Operation(
        summary = "마이페이지 게시물 조회",
        description = "MY로 요청하면 내가 쓴 게시물, LIKE로 요청하면 내가 좋아요를 누른 게시물을 반환합니다."
    )
    @GetMapping("/articles")
    fun getMyArticles(
        @RequestUser user: User,
        @RequestParam type: MyPageArticleType,
        @RequestParam page: Int,
        @RequestParam size: Int
    ): Page<ArticlePreviewResponse> {
        return myPageService.getMyPageArticles(type, user, page, size)
    }

    @Operation(
        summary = "마이페이지 사용자 정보",
        description = "마이페이지에서 프로필 수정 전에 필요한 사용자 정보를 반환합니다."
    )
    @GetMapping("/info")
    fun getUserInfo(
        @RequestUser user: User
    ): GetUserInfoResponse {
        return myPageService.getUserInfo(user)
    }

    @Operation(summary = "닉네임 수정", description = "마이페이지에서 닉네임을 수정합니다.")
    @PatchMapping("/nickname")
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
    fun updateBasicInfo(
        @RequestUser user: User,
        @RequestBody request: UpdateBasicInfoRequest
    ): MyPageResponse {
        return myPageService.updateBasicInfo(user, request)
    }

    @Operation(
        summary = "마이페이지 부가 정보 수정",
        description = "사용자의 부가 정보(알바 경력, 관심 직종)을 수정합니다."
    )
    @PatchMapping("/sub-info")
    fun updateSubInfo(
        @RequestUser user: User,
        @RequestBody request: UpdateSubInfoRequest
    ): MyPageResponse {
        return myPageService.updateSubInfo(user, request)
    }

    @Operation(
        summary = "근로계약서 검증",
        description = "사용자 정보와 근무지를 입력받아 근로계약서와 비교하여 검증결과를 반환합니다."
    )
    @PostMapping("/contract")
    fun verifyContract(
        @RequestUser user: User,
        @RequestParam companyName: String,
        @RequestPart file: MultipartFile,
    ): OcrVerificationResponse {
        return contractService.verifyContract(user, companyName, file)
    }
}