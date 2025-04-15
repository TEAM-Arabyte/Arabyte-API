package com.arabyte.arabyteapi.domain.user.dto

import com.arabyte.arabyteapi.domain.review.enums.Category
import com.fasterxml.jackson.annotation.JsonProperty

data class OnboardingRequest(
    @JsonProperty("user_id")
    val userId: Long,

    @JsonProperty("experience_years")
    val experienceYears: Int,

    @JsonProperty("experience_months")
    val experienceMonths: Int,

    @JsonProperty("job_interests_1")
    val jobInterests1: Category,

    @JsonProperty("job_interests_2")
    val jobInterests2: Category,

    @JsonProperty("job_interests_3")
    val jobInterests3: Category

)