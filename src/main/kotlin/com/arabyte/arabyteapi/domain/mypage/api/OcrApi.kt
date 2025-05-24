package com.arabyte.arabyteapi.domain.mypage.api

import com.arabyte.arabyteapi.domain.mypage.dto.OcrVerificationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OcrApi {
    @Multipart
    @POST("/ocr/contract")
    fun verifyContract(
        @Part file: MultipartBody.Part,
        @Part("user_name") userName: RequestBody,
        @Part("company_name") companyName: RequestBody
    ): Call<OcrVerificationResponse>
}