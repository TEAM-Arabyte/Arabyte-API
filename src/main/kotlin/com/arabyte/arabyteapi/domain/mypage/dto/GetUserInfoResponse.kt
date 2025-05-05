package com.arabyte.arabyteapi.domain.mypage.dto

import com.arabyte.arabyteapi.domain.user.entity.UserJobInterest

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
            userName: String,
            location: String,
            gender: String,
            experienceYears: Int?,
            experienceMonths: Int?,
            jobInterests: UserJobInterest?
        ): GetUserInfoResponse {
            val interests = listOfNotNull(
                jobInterests?.category1?.name,
                jobInterests?.category2?.name,
                jobInterests?.category3?.name
            )

            return GetUserInfoResponse(
                userName = userName,
                location = location,
                gender = gender,
                experienceYears = experienceYears,
                experienceMonths = experienceMonths,
                jobInterests = interests
            )
        }
    }
}
