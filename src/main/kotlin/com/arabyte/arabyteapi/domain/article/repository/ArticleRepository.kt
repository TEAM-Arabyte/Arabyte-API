package com.arabyte.arabyteapi.domain.article.repository

import com.arabyte.arabyteapi.domain.article.entity.Article
import com.arabyte.arabyteapi.domain.article.enums.ArticleKind
import com.arabyte.arabyteapi.domain.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByArticleKindId(articleKind: ArticleKind, pageable: Pageable): Page<Article>
    fun findAllByUserOrderByCreatedAtDesc(user: User, pageable: Pageable): Page<Article>
}