package com.arabyte.arabyteapi.domain.article.entity

import com.arabyte.arabyteapi.domain.article.enums.ArticleKind
import com.arabyte.arabyteapi.global.entity.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Article(
    var title: String,
    var text: String,
    var likeCount: Int,
    var isAnonymous: Boolean,
    @JsonIgnore
    val userId: Long,
    @Enumerated
    val articleKindId: ArticleKind
) : BaseEntity() {
    @OneToMany(mappedBy = "article", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf()

    @OneToMany(mappedBy = "article", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val images: MutableList<ArticleImage> = mutableListOf()
}