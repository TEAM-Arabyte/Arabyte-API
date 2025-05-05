package com.arabyte.arabyteapi.domain.mypage.service

import com.arabyte.arabyteapi.domain.article.dto.ArticlePreviewResponse
import com.arabyte.arabyteapi.domain.article.service.ArticleLikeService
import com.arabyte.arabyteapi.domain.article.service.ArticleService
import com.arabyte.arabyteapi.domain.location.service.LocationService
import com.arabyte.arabyteapi.domain.mypage.dto.GetUserInfoResponse
import com.arabyte.arabyteapi.domain.mypage.dto.MyPageResponse
import com.arabyte.arabyteapi.domain.mypage.dto.UpdateBasicInfoRequest
import com.arabyte.arabyteapi.domain.mypage.dto.UpdateSubInfoRequest
import com.arabyte.arabyteapi.domain.mypage.enums.MyPageArticleType
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.entity.UserJobInterest
import com.arabyte.arabyteapi.domain.user.service.UserService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class MyPageService(
    private val articleLikeService: ArticleLikeService,
    private val articleService: ArticleService,
    private val locationService: LocationService,
    private val userService: UserService,
) {
    fun getMyPageArticles(type: MyPageArticleType, user: User, page: Int, size: Int): Page<ArticlePreviewResponse> {
        val articles = when (type) {
            MyPageArticleType.MY -> articleService.getMyArticles(user, page, size)
            MyPageArticleType.LIKE -> articleLikeService.getLikedArticles(user, page, size)
        }

        val articleIds = articles.map { it.id }.toList()
        val likedArticleIds = articleLikeService.findLikedArticleIds(user.id, articleIds)

        return articles.map { article ->
            val isLiked = likedArticleIds.contains(article.id)

            ArticlePreviewResponse.of(article, isLiked)
        }
    }

    fun getUserInfo(user: User): GetUserInfoResponse {
        val location = "${user.location?.sido ?: ""} ${user.location?.gu ?: ""} ${user.location?.dong ?: ""}".trim()

        return GetUserInfoResponse.of(
            user.nickname,
            location,
            user.gender,
            user.experienceYears,
            user.experienceMonths,
            user.jobInterests
        )
    }

    fun updateNickName(user: User, newNickname: String): MyPageResponse {
        user.nickname = newNickname

        val savedUser = userService.saveUser(user)
        return MyPageResponse.of(savedUser)
    }

    fun updateBasicInfo(user: User, request: UpdateBasicInfoRequest): MyPageResponse {
        val location = locationService.findById(request.locationId)

        user.location = location
        user.ageRange = request.ageRange
        user.gender = request.gender

        val savedUser = userService.saveUser(user)
        return MyPageResponse.of(savedUser)
    }

    fun updateSubInfo(user: User, request: UpdateSubInfoRequest): MyPageResponse {
        user.experienceYears = request.experienceYears
        user.experienceMonths = request.experienceMonths

        if (request.jobInterests.isEmpty()) {
            val savedUser = userService.saveUser(user)
            return MyPageResponse.of(savedUser)
        }

        val interests = request.jobInterests
        val userJobInterests = user.jobInterests

        if (userJobInterests != null) {
            userJobInterests.category1 = interests.getOrNull(0)
            userJobInterests.category2 = interests.getOrNull(1)
            userJobInterests.category3 = interests.getOrNull(2)
        } else {
            val interestEntity = UserJobInterest(
                user = user,
                category1 = interests.getOrNull(0),
                category2 = interests.getOrNull(1),
                category3 = interests.getOrNull(2),
            )
            user.jobInterests = interestEntity
        }

        val savedUser = userService.saveUser(user)
        return MyPageResponse.of(savedUser)
    }
}