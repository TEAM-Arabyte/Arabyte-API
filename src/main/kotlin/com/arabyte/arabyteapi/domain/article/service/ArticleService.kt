package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.*
import com.arabyte.arabyteapi.domain.article.entity.Article
import com.arabyte.arabyteapi.domain.article.entity.ArticleImage
import com.arabyte.arabyteapi.domain.article.enums.ArticleKind
import com.arabyte.arabyteapi.domain.article.repository.ArticleRepository
import com.arabyte.arabyteapi.domain.comment.dto.CommentResponse
import com.arabyte.arabyteapi.domain.comment.repository.CommentRepository
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
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

        request.articleImages?.forEach { imageUrl ->
            article.images.add(ArticleImage(url = imageUrl, article = article))
        }

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
            val isLiked = likedArticleIds.contains(article.id)
            ArticlePreviewResponse.of(article, isLiked)
        }
    }

    fun getArticleDetail(user: User, articleId: Long): ArticleResponse {
        val article = getArticles(articleId)

        val articleUser = article.user
        val isLiked = articleLikeService.isArticleLikedByUser(user.id, article.id)

        val commentList = commentRepository.findAllByArticleId(articleId)
        val commentResponses = commentList.map { comment ->
            CommentResponse(
                commentId = comment.id,
                text = comment.text,
                // TODO - 이러면 모든 익명 유저의 닉네임이 "익명"이 되버림
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
        val article = getArticles(articleId)

        if (article.user.id != user.id) {
            throw CustomException(CustomError.ARTICLE_FORBIDDEN)
        }

        article.title = request.title
        article.text = request.text
        article.isAnonymous = request.isAnonymous
        article.articleKindId = request.articleKind

        article.images.clear()
        request.articleImages?.forEach { imageUrl ->
            article.images.add(ArticleImage(url = imageUrl, article = article))
        }

        val savedArticle = articleRepository.save(article)
        return UpdateArticleResponse.of(savedArticle)
    }

    fun deleteArticle(user: User, articleId: Long): DeleteArticleResponse {
        val article = getArticles(articleId)

        if (article.user.id != user.id) {
            throw CustomException(CustomError.ARTICLE_FORBIDDEN)
        }

        articleRepository.delete(article)

        return DeleteArticleResponse.of(article)
    }

    fun getArticles(articleId: Long): Article {
        return articleRepository.findById(articleId)
            .orElseThrow { CustomException(CustomError.ARTICLE_NOT_FOUND) }
    }

    fun getMyArticles(user: User, page: Int, size: Int): Page<Article> {
        return articleRepository.findAllByUserOrderByCreatedAtDesc(user, PageRequest.of(page, size))
    }

    fun toggleLike(user: User, request: ArticleLikeRequest): ArticleLikeResponse {
        val article = getArticles(request.articleId)
        return articleLikeService.toggleLike(user, article)
    }
}