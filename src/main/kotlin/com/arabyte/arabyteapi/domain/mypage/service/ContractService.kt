package com.arabyte.arabyteapi.domain.mypage.service

import com.arabyte.arabyteapi.domain.mypage.api.OcrApi
import com.arabyte.arabyteapi.domain.mypage.dto.OcrVerificationResponse
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service
import java.nio.file.Files

@Service
class ContractService(
    private val ocrApi: OcrApi
) {
    fun verifyContract(user: User, companyName: String, imageUrl: String): OcrVerificationResponse {
        val userName = user.username

        // 1. 이미지 다운로드
        val url = java.net.URL(imageUrl)
        val connection = url.openConnection()
        val contentType = connection.contentType ?: "application/octet-stream"
        val extension = when {
            contentType.contains("jpeg") -> "jpg"
            contentType.contains("png") -> "png"
            contentType.contains("bmp") -> "bmp"
            else -> "bin"
        }

        val tempFile = Files.createTempFile("contract-", ".$extension").toFile()
        url.openStream().use { input ->
            tempFile.outputStream().use { output -> input.copyTo(output) }
        }

        // 2. 미디어 타입 지정
        val mediaType = contentType.toMediaTypeOrNull()

        val requestFile = MultipartBody.Part.createFormData(
            "file",
            tempFile.name,
            tempFile.asRequestBody(mediaType)
        )

        val userNamePart = userName.toRequestBody("text/plain".toMediaTypeOrNull())
        val companyNamePart = companyName.toRequestBody("text/plain".toMediaTypeOrNull())

        // 3. OCR API 호출
        val response = ocrApi.verifyContract(requestFile, userNamePart, companyNamePart).execute()

        if (!response.isSuccessful) throw CustomException(CustomError.OCR_REQUEST_FAILED)

        val verifyResult = response.body()?.verifyResult
            ?: throw CustomException(CustomError.OCR_RESPONSE_NULL)

        return OcrVerificationResponse(verifyResult)
    }

}