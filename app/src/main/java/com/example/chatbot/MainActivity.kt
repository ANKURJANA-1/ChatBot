package com.example.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var text: AppCompatEditText
    private lateinit var send: AppCompatImageView
    private lateinit var recycleList: RecyclerView

    private lateinit var messageList: ArrayList<MessageModel>
    private val USER: String = "user"
    private val BOT: String = "bot"

    private lateinit var adapter: MessageRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageList = ArrayList()

        send = findViewById(R.id.send)
        text = findViewById(R.id.message)
        recycleList = findViewById(R.id.recyclerView)

        send.setOnClickListener(View.OnClickListener {
            sendMessage(text.text.toString())
        })


//        var list=ArrayList<MessageModel>()
//
//        list.add(MessageModel("hi",USER))
//        list.add(MessageModel("hello",BOT))
//        list.add(MessageModel("hi",USER))
//        list.add(MessageModel("hello",BOT))

        adapter = MessageRVAdapter(messageList, this)
        recycleList.adapter = adapter
        recycleList.layoutManager = LinearLayoutManager(this)


    }


    private fun sendMessage(userMessage: String) {
        messageList.add(MessageModel(userMessage, USER))

        val retrofit = RetrofitService
            .getInstance()
            .getBotMessage(
               userMessage
           )
            .enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
//                var message: Message? = response.body()
//
//                if (message != null) {
//                    messageList.add(MessageModel(message.cnt, BOT))
//                }

                Log.d("data",response.body().toString())
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.d("data",t.toString())
            }
        })
    }

}