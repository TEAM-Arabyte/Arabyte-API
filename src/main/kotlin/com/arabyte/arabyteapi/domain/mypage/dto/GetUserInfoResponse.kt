package com.arabyte.arabyteapi.domain.mypage.dto

import com.arabyte.arabyteapi.domain.user.entity.User

data class GetUserInfoResponse(
    val userName: String,
    val location: String,
    val gender: String,
    val experienceYears: Int? = null,
    val experienceMonths: Int? = null,
    val jobInterests: List<String> = emptyList()
) {
    companion object {
        fun of(
            user: User
        ): GetUserInfoResponse {
            val interests = listOfNotNull(
                user.jobInterests?.category1?.name,
                user.jobInterests?.category2?.name,
                user.jobInterests?.category3?.name
            )

            return GetUserInfoResponse(
                userName = user.nickname,
                location = user.location.toString(),
                gender = user.gender,
                experienceYears = user.experienceYears,
                experienceMonths = user.experienceMonths,
                jobInterests = interests
            )
        }
    }
}
