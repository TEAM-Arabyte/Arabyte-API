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
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files

@Service
class ContractService(
    private val ocrApi: OcrApi
) {
    fun verifyContract(user: User, companyName: String, file: MultipartFile): OcrVerificationResponse {
        val userName = user.username
        
        val originalFilename = file.originalFilename ?: "contract.png"
        val extension = StringUtils.getFilenameExtension(originalFilename)

        val tempFile = Files.createTempFile("contract-", ".$extension").toFile()
        file.transferTo(tempFile)

        val mediaType = when (extension?.lowercase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "bmp" -> "image/bmp"
            else -> "application/octet-stream"
        }.toMediaTypeOrNull()

        val requestFile = MultipartBody.Part.createFormData(
            "file",
            tempFile.name,
            tempFile.asRequestBody(mediaType)
        )

        val userNamePart = userName.toRequestBody("text/plain".toMediaTypeOrNull())
        val companyNamePart = companyName.toRequestBody("text/plain".toMediaTypeOrNull())

        val response = ocrApi.verifyContract(requestFile, userNamePart, companyNamePart).execute()

        if (!response.isSuccessful) throw CustomException(CustomError.OCR_REQUEST_FAILED)

        val verifyResult = response.body()?.verifyResult
            ?: throw CustomException(CustomError.OCR_RESPONSE_NULL)

        return OcrVerificationResponse(verifyResult)
    }
}