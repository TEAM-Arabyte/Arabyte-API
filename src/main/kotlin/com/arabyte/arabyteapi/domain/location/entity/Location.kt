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
) : BaseEntity() {
    override fun toString(): String {
        when (depth) {
            1 -> {
                return sido
            }

            2 -> {
                return "$sido $gu"
            }

            3 -> {
                return "$sido $gu $dong"
            }

            else -> {
                return "$sido $gu $dong"
            }
        }
    }
}