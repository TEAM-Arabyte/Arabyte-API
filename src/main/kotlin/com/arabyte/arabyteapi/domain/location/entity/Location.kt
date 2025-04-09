package com.arabyte.arabyteapi.domain.location.entity

import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class Location(
    val locationCode: String,
    var sido: String,
    val sidoCode: String,
    var gu: String,
    val guCode: String,
    var dong: String,
    val dongCode: String,
    val depth: Int,
) : BaseEntity()