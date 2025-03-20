package com.arabyte.arabyteapi.domain.review

import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.Entity

// todo 필드들 확정되고 나서 구현하기

@Entity
class Review(
    val text: String,
    // 근무지
    // 근무지역
    // 카테고리
    var rating: Int,
    // 근무강도
    // 근무분위기
    // 급여 보상 체계
    // 급여 지급 일시
    // 야근
    // 업무 난이도
    // 작성자
) : BaseEntity()