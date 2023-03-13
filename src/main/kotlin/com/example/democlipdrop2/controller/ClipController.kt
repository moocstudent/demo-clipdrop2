package com.example.democlipdrop2.controller

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import org.apache.commons.io.FileUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.IOException

@RequestMapping("/clip")
@RestController
class ClipController {

    companion object {
        val API_KEY = "95358a121e9458ae796e169fe5d3de0590cf7bb06f34112700815f4467318f1a8345d4589e0be0023e8192136926747e"
    }

    /**
     * mask遮罩
     */
    @GetMapping("/mask")
    fun inpainting(): Response {
        print("inpainting")
        val client = OkHttpClient()
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image_file",
                "ori.jpg",
                File("docs/images/ori.jpg")
                    .asRequestBody("image/jpeg".toMediaType())
            )
            .addFormDataPart(
                "mask_file",
                "mask.png",
                File("docs/images/mask.png")
                    .asRequestBody("image/png".toMediaType())
            )
            .build()

        val request =
            Request.Builder()
                .header("x-api-key", API_KEY)
                .url("https://clipdrop-api.co/cleanup/v1")
                .post(requestBody)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val file = File("docs/results/inpainting.jpg")
            FileUtils.writeByteArrayToFile(file, response.body?.bytes())
            println("success")
            return response // here is a byte array of the returned image
        }
    }

    /**
     * 移除背景
     */
    @GetMapping("/removeBg")
    fun removeBg(): Response {
        print("removeBg")
        val client = OkHttpClient()
        val requestBody =
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "image_file",
                    "street.jpg",
                    File("docs/images/street.jpg").asRequestBody("image/jpeg".toMediaType())
                )
                .build()

        val request =
            Request.Builder()
                .header("x-api-key", API_KEY)
                .url("https://clipdrop-api.co/remove-background/v1")
                .post(requestBody)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val file = File("docs/results/removeBg.png")
            FileUtils.writeByteArrayToFile(file, response.body?.bytes())
            println("success")
            return response // here is a byte array of the returned image
        }
    }

    /**
     * 锐化 清晰化
     */
    @GetMapping("/superResolution")
    fun superResolution(): Response {
        print("superResolution")
        // this example uses the OkHttp library
        val client = OkHttpClient()
        val requestBody =
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "image_file",
                    "car.png",
                    File("docs/images/car.png").asRequestBody("image/jpeg".toMediaType())
                )
                .addFormDataPart("upscale", 2.toString())
                .build()

        val request =
            Request.Builder()
                .header("x-api-key", API_KEY)
                .url("https://clipdrop-api.co/super-resolution/v1")
                .post(requestBody)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val file = File("docs/results/superResolution.png")
            FileUtils.writeByteArrayToFile(file, response.body?.bytes())
            println("success")
            return response
        }
    }

    /**
     * 移除圖片中文本
     */
    @GetMapping("/removeText")
    fun removeText(): Response {
        print("removeText")
        // this example uses the OkHttp library
        val client = OkHttpClient()
        val requestBody =
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "image_file",
                    "image.jpg",
                    File("docs/images/image.jpg").asRequestBody("image/jpeg".toMediaType())
                )
                .build()

        val request =
            Request.Builder()
                .header("x-api-key", API_KEY)
                .url("https://clipdrop-api.co/remove-text/v1")
                .post(requestBody)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val file = File("docs/results/removeText.png")
            FileUtils.writeByteArrayToFile(file, response.body?.bytes())
            println("success")
            return response
        }
    }

    @GetMapping("/textToImage")
    fun textToImage():Response {
        print("textToImage")
        // this example uses the OkHttp library
        val client = OkHttpClient()

        val requestBody =
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("prompt", "photograph of a cat surfing")
                .build()

        val request =
            Request.Builder()
                .header("x-api-key", API_KEY)
                .url("https://clipdrop-api.co/text-to-image/v1")
                .post(requestBody)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val file = File("docs/results/textToImage.png")
            FileUtils.writeByteArrayToFile(file, response.body?.bytes())
            println("success")
            return response
        }
    }

    @GetMapping("/replaceBg")
    fun replaceBg():Response{
        // this example uses the OkHttp library
        val client = OkHttpClient()

        val requestBody =
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "image_file",
                    "car.jpg",
                    File("docs/images/car.jpg").asRequestBody("image/jpeg".toMediaType())
                )
                .addFormDataPart("prompt", "a cozy marble kitchen with wine glasses")
                .build()

        val request =
            Request.Builder()
                .header("x-api-key", API_KEY)
                .url("https://clipdrop-api.co/replace-background/v1")
                .post(requestBody)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val file = File("docs/results/replaceBg.png")
            FileUtils.writeByteArrayToFile(file, response.body?.bytes())
            println("success")
            return response
        }
    }

    @GetMapping("/pde")
    fun pde():Response {
        // this example uses the OkHttp library
        val client = OkHttpClient()

        val requestBody =
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "image_file",
                    "portrait.jpg",
                    File("docs/images/portrait.jpg").asRequestBody("image/jpeg".toMediaType())
                )
                .build()

        val request =
            Request.Builder()
                .header("x-api-key", "YOUR_API_KEY")
                .url("https://clipdrop-api.co/portrait-depth-estimation/v1")
                .post(requestBody)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val file = File("docs/results/pde.png")
            FileUtils.writeByteArrayToFile(file, response.body?.bytes())
            println("success")
            return response
        }
    }

    @GetMapping("/psn")
    fun psn():Response {
        // this example uses the OkHttp library
        val client = OkHttpClient()

        val requestBody =
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "image_file",
                    "portrait.jpg",
                    File("docs/images/portrait.jpg").asRequestBody("image/jpeg".toMediaType())
                )
                .build()

        val request =
            Request.Builder()
                .header("x-api-key", API_KEY)
                .url("https://clipdrop-api.co/portrait-surface-normals/v1")
                .post(requestBody)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val file = File("docs/results/psn.png")
            FileUtils.writeByteArrayToFile(file, response.body?.bytes())
            println("success")
            return response
        }
    }
}

