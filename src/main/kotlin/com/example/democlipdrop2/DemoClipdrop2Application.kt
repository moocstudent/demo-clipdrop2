package com.example.democlipdrop2

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import org.apache.commons.io.FileUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.IOException

@SpringBootApplication
class DemoClipdrop2Application

fun main(args: Array<String>) {
    runApplication<DemoClipdrop2Application>(*args)
//    inpainting()
}



fun inpainting(): Response {
    println("inpainting")
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
            .header("x-api-key","95358a121e9458ae796e169fe5d3de0590cf7bb06f34112700815f4467318f1a8345d4589e0be0023e8192136926747e")
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