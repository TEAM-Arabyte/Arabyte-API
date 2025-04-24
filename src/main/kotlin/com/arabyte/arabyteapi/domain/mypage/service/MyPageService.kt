package com.arabyte.arabyteapi.domain.mypage.service

import com.arabyte.arabyteapi.domain.article.dto.ArticlePreviewResponse
import com.arabyte.arabyteapi.domain.article.service.ArticleLikeService
import com.arabyte.arabyteapi.domain.article.service.ArticleService
import com.arabyte.arabyteapi.domain.location.service.LocationService
import com.arabyte.arabyteapi.domain.mypage.dto.MyPageResponse
import com.arabyte.arabyteapi.domain.mypage.dto.UpdateBasicInfoResponse
import com.arabyte.arabyteapi.domain.mypage.dto.UpdateSubInfoResponse
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.entity.UserJobInterest
import com.arabyte.arabyteapi.domain.user.repository.UserRepository
import com.arabyte.arabyteapi.domain.user.service.UserService
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class MyPageService(
    private val articleLikeService: ArticleLikeService,
    private val articleService: ArticleService,
    private val userRepository: UserRepository,
    private val locationService: LocationService,
    private val userService: UserService
) {
    fun getMyPageArticles(type: String, user: User, page: Int, size: Int): Page<ArticlePreviewResponse> {
        val articles = when (type) {
            "my" -> articleService.getMyArticles(user, page, size)
            "like" -> articleLikeService.getLikedArticles(user, page, size)
            else -> throw CustomException(CustomError.INVALID_URL)
        }

        val articleIds = articles.map { it.id }.toList()
        val likedArticleIds = articleLikeService.findLikedArticleIds(user.id, articleIds)

        return articles.map { article ->
            val commentCount = article.comments.size
            val uploadAt = articleService.getPreviewUploadTime(article.createdAt)

            ArticlePreviewResponse(
                articleId = article.id,
                title = article.title,
                text = article.text,
                likeCount = article.likeCount,
                commentCount = commentCount,
                uploadAt = uploadAt,
                thumbnailImage = article.images.firstOrNull()?.url,
                articleKind = article.articleKindId,
                isLiked = likedArticleIds.contains(article.id),
            )
        }
    }

    fun updateNickName(user: User, newNickname: String): MyPageResponse {
        user.nickname = newNickname

        return MyPageResponse(
            userId = userService.saveUser(user).id
        )
    }

    fun updateBasicInfo(user: User, request: UpdateBasicInfoResponse): MyPageResponse {
        val location = locationService.findById(request.locationId)

        user.location = location
        user.ageRange = request.ageRange
        user.gender = request.gender

        return MyPageResponse(
            userId = userService.saveUser(user).id
        )
    }

    fun updateSubInfo(user: User, request: UpdateSubInfoResponse): MyPageResponse {
        user.experienceYears = request.experienceYears
        user.experienceMonths = request.experienceMonths

        if (request.jobInterests.isNotEmpty()) {
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
        }

        return MyPageResponse(
            userId = userService.saveUser(user).id
        )
    }
}