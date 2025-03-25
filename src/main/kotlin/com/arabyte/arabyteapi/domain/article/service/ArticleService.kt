package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.article.dto.ArticlePreviewResponse
import com.arabyte.arabyteapi.domain.article.dto.CreateArticleRequest
import com.arabyte.arabyteapi.domain.article.dto.CreateArticleResponse
import com.arabyte.arabyteapi.domain.article.entity.Article
import com.arabyte.arabyteapi.domain.article.enums.ArticleKind
import com.arabyte.arabyteapi.domain.article.repository.ArticleRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
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
            val uploadAt = getUploadTime(article.createdAt)

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

    fun getUploadTime(createAt: LocalDateTime?): String {
        val now = LocalDateTime.now();
        val duration = Duration.between(createAt, now)

        return when {
            duration.toMinutes() < 1 -> "방금 전"
            duration.toHours() < 1 -> "${duration.toMinutes()}분 전"
            duration.toHours() < 24 -> "${duration.toHours()}시간 전"
            else -> "${duration.toDays()}일 전"
        }
    }
}