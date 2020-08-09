package com.example.bismillahyukbisayuk.PushNotification

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.example.bismillahyukbisayuk.R
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic"

class MainActivityPush : AppCompatActivity() {

    val TAG = "MainActivity"
    private lateinit var etTitle: EditText
    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_push)

        toolbar = findViewById(R.id.toolbar)
        etMessage = findViewById(R.id.etMessage)
        etTitle = findViewById(R.id.etTitle)
        btnSend = findViewById(R.id.btnSend)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
//            etToken.setText(it.token)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        btnSend.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            if (title.isNotEmpty() && message.isNotEmpty()) {
                PushNotification(
                        NotificationData(title, message),
                        TOPIC
                ).also {
                    sendNotification(it)
                }
            }
        }
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if (response.isSuccess) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.toString())
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

}