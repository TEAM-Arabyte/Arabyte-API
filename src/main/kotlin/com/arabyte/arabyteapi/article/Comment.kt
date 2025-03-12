package com.arabyte.arabyteapi.article

import com.arabyte.arabyteapi.common.entity.BaseEntity
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