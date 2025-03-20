package com.arabyte.arabyteapi.domain.article

import com.arabyte.arabyteapi.global.entity.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity

@Entity
class Comment(
    val articleId: Long,
    val commentId: Long,
    var text: String,
    var likeCount: Int,
    @JsonIgnore
    val userId: Long,
) : BaseEntity()