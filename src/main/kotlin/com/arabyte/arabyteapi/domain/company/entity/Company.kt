package com.arabyte.arabyteapi.domain.company.entity

import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class Company(
    val mapId: String,
    var name: String = "",
) : BaseEntity()