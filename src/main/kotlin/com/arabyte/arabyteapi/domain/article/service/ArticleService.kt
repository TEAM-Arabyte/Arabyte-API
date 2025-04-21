package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.*
import com.arabyte.arabyteapi.domain.article.entity.Article
import com.arabyte.arabyteapi.domain.article.enums.ArticleKind
import com.arabyte.arabyteapi.domain.article.repository.ArticleRepository
import com.arabyte.arabyteapi.domain.comment.dto.CommentResponse
import com.arabyte.arabyteapi.domain.comment.repository.CommentRepository
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val articleLikeService: ArticleLikeService,
    private val commentRepository: CommentRepository
) {
    @Transactional
    fun createArticle(user: User, request: CreateArticleRequest): CreateArticleResponse {
        val article = articleRepository.save(
            Article(
                title = request.title,
                text = request.text,
                likeCount = 0,
                isAnonymous = request.isAnonymous,
                user = user,
                articleKindId = request.articleKind,
            )
        )

        return CreateArticleResponse(article.id, article.title)
    }

    fun getArticlePreviews(
        user: User,
        articleKind: ArticleKind?,
        pageable: Pageable
    ): Page<ArticlePreviewResponse> {
        val articles = if (articleKind != null) {
            articleRepository.findAllByArticleKindId(articleKind, pageable)
        } else {
            articleRepository.findAll(pageable)
        }

        val articleIds = articles.map { it.id }.toList()
        val likedArticleIds = articleLikeService.findLikedArticleIds(user.id, articleIds)

        return articles.map { article ->
            val commentCount = article.comments.size
            val uploadAt = getPreviewUploadTime(article.createdAt)

            ArticlePreviewResponse(
                title = article.title,
                text = article.text,
                likeCount = article.likeCount,
                commentCount = commentCount,
                uploadAt = uploadAt,
                // TODO
                thumbnailImage = "이미지",
                articleKind = article.articleKindId,
                isLiked = likedArticleIds.contains(article.id)
            )
        }
    }

    private fun getPreviewUploadTime(createAt: LocalDateTime?): String {
        val now = LocalDateTime.now()
        val duration = Duration.between(createAt, now)

        return when {
            duration.toMinutes() < 1 -> "방금 전"
            duration.toHours() < 1 -> "${duration.toMinutes()}분 전"
            duration.toHours() < 24 -> "${duration.toHours()}시간 전"
            else -> "${duration.toDays()}일 전"
        }
    }

    fun getArticleDetail(user: User, articleId: Long): ArticleResponse {
        val article = getArticle(articleId)

        val articleUser = article.user
        val isLiked = articleLikeService.isArticleLikedByUser(user.id, article.id)

        val commentList = commentRepository.findAllByArticleId(articleId)
        val commentResponses = commentList.map { comment ->
            CommentResponse(
                commentId = comment.id,
                text = comment.text,
                // 이러면 모든 익명 유저의 닉네임이 "익명"이 되버림
                nickname = if (comment.isAnonymous) "익명" else comment.user.nickname,
                createdAt = comment.createdAt?.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
                    ?: "날짜없음",
                isAnonymous = comment.isAnonymous,
                parentId = comment.parent?.id,
                userId = comment.user.id,
            )
        }

        return ArticleResponse(
            articleId = article.id,
            nickname = if (article.isAnonymous) "익명" else articleUser.nickname,
            createdAt = article.createdAt?.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
                ?: "날짜없음",
            title = article.title,
            text = article.text,
            likeCount = article.likeCount,
            commentCount = commentList.size,
            comments = commentResponses,
            imageUrls = article.images.map { it.url },
            isLiked = isLiked,
        )
    }

    fun updateArticle(
        user: User,
        articleId: Long,
        request: UpdateArticleRequest
    ): UpdateArticleResponse {
        val article = getArticle(articleId)

        if (article.user.id != user.id) {
            throw CustomException(CustomError.ARTICLE_FORBIDDEN)
        }

        article.title = request.title
        article.text = request.text
        article.isAnonymous = request.isAnonymous
        article.articleKindId = request.articleKind

        articleRepository.save(article)

        return UpdateArticleResponse(
            articleId = article.id,
            message = "${article.id}번 게시물이 수정되었습니다"
        )
    }

    fun deleteArticle(user: User, articleId: Long): DeleteArticleResponse {
        val article = getArticle(articleId)

        if (article.user.id != user.id) {
            throw CustomException(CustomError.ARTICLE_FORBIDDEN)
        }

        articleRepository.delete(article)

        return DeleteArticleResponse(
            articleId = article.id,
            message = "${article.id}번 게시물이 삭제되었습니다."
        )
    }

    fun getArticle(articleId: Long): Article {
        return articleRepository.findById(articleId)
            .orElseThrow { CustomException(CustomError.ARTICLE_NOT_FOUND) }
    }

    fun toggleLike(user: User, request: ArticleLikeRequest): ArticleLikeResponse {
        val article = getArticle(request.articleId)
        return articleLikeService.toggleLike(user, article)
    }
}