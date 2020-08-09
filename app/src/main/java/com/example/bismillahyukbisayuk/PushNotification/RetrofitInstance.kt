package com.example.bismillahyukbisayuk.PushNotification

import com.example.bismillahyukbisayuk.PushNotification.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private val retrofit by kotlin.lazy {
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        val api by kotlin.lazy {
            retrofit.create(NotificationAPI::class.java)
        }
    }
}