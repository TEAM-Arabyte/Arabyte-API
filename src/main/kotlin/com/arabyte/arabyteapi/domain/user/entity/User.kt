package com.arabyte.arabyteapi.domain.user.entity

import com.arabyte.arabyteapi.domain.article.entity.Article
import com.arabyte.arabyteapi.domain.article.entity.ArticleLike
import com.arabyte.arabyteapi.domain.article.entity.Comment
import com.arabyte.arabyteapi.domain.location.entity.Location
import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "`user`")
class User(
    var kakaoId: Long,
    var username: String,
    var nickname: String,
    var profileImageUrl: String,

    var ageRange: String,
    var gender: String,
    var email: String,
    var phoneNumber: String?,
    var experienceYears: Int = 0,
    var experienceMonths: Int = 0,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val articles: MutableList<Article> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<ArticleLike> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location? = null,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var jobInterests: UserJobInterest? = null
) : BaseEntity()