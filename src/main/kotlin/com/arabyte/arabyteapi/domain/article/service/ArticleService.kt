package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.article.*
import com.arabyte.arabyteapi.domain.article.dto.comment.CommentResponse
import com.arabyte.arabyteapi.domain.article.entity.Article
import com.arabyte.arabyteapi.domain.article.enums.ArticleKind
import com.arabyte.arabyteapi.domain.article.repository.ArticleRepository
import com.arabyte.arabyteapi.domain.article.repository.CommentRepository
import com.arabyte.arabyteapi.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository
) {
    @Transactional
    fun createArticle(request: CreateArticleRequest): CreateArticleResponse {
        val article = Article(
            title = request.title,
            text = request.text,
            likeCount = 0,
            isAnonymous = request.isAnonymous,
            userId = request.userId,
            articleKindId = request.articleKind,
        )

        val saved = articleRepository.save(article)
        return CreateArticleResponse(saved.id, saved.title)
    }

    fun getArticlePreviews(articleKind: ArticleKind?, pageable: Pageable): Page<ArticlePreviewResponse> {
        val articles: Page<Article> = if (articleKind != null) {
            articleRepository.findAllByArticleKindId(articleKind, pageable)
        } else {
            articleRepository.findAll(pageable)
        }

        return articles.map { article ->
            val commentCount = article.comments.size
            val uploadAt = getPreviewUploadTime(article.createdAt)

            ArticlePreviewResponse(
                title = article.title,
                text = article.text,
                likeCount = article.likeCount,
                commentCount = commentCount,
                uploadAt = uploadAt,
                thumbnailImage = "이미지",
                articleKind = article.articleKindId
            )
        }
    }

    fun getPreviewUploadTime(createAt: LocalDateTime?): String {
        val now = LocalDateTime.now();
        val duration = Duration.between(createAt, now)

        return when {
            duration.toMinutes() < 1 -> "방금 전"
            duration.toHours() < 1 -> "${duration.toMinutes()}분 전"
            duration.toHours() < 24 -> "${duration.toHours()}시간 전"
            else -> "${duration.toDays()}일 전"
        }
    }

    fun getArticleDetail(articleId: Long): ArticleResponse {
        val article = articleRepository.findByIdOrNull(articleId)
            ?: throw IllegalArgumentException("해당 게시글이 존재하지 않습니다.")

        val user = userRepository.findByIdOrNull(article.userId)
            ?: throw IllegalArgumentException("작성자 정보가 없습니다.")

        val commentList = commentRepository.findAllByArticleId(articleId)
        val commentResponses = commentList.map { comment ->
            CommentResponse(
                commentId = comment.id,
                text = comment.text,
                // 이러면 모든 익명 유저의 닉네임이 "익명"이 되버림
                nickname = if (comment.isAnonymous) "익명" else comment.user.nickname,
                createdAt = comment.createdAt?.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) ?: "날짜없음",
                isAnonymous = comment.isAnonymous,
                parentId = comment.parent?.id,
                userId = comment.user.id,
            )
        }

        return ArticleResponse(
            nickname = if (article.isAnonymous) "익명" else user.nickname,
            createdAt = article.createdAt?.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) ?: "날짜없음",
            title = article.title,
            text = article.text,
            likeCount = article.likeCount,
            commentCount = commentList.size,
            comments = commentResponses,
            imageUrls = article.images.map { it.url }
        )
    }

    fun updateArticle(articleId: Long, request: UpdateArticleRequest) {
        val article = articleRepository.findById(articleId)
            .orElseThrow { IllegalArgumentException("해당 게시글이 존재하지 않습니다.") }

        article.title = request.title
        article.text = request.text
        article.isAnonymous = request.isAnonymous

        articleRepository.save(article)
    }

    fun deleteArticle(articleId: Long) {
        val article = articleRepository.findById(articleId)
            .orElseThrow { IllegalArgumentException("해당 게시글이 존재하지 않습니다.") }

        articleRepository.delete(article)
    }
}