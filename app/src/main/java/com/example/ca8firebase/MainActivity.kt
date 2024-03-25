package com.example.ca8firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.bSave).setOnClickListener {
            val firstName = findViewById<EditText>(R.id.firstNameLog).text.toString()
            val middleName = findViewById<EditText>(R.id.middleNameLog).text.toString()
            val lastName = findViewById<EditText>(R.id.lastNameLog).text.toString()
            val db = FirebaseFirestore.getInstance()
            val user: MutableMap<String,Any> = HashMap()
            user ["firstName"] = firstName
            user["middleName"] = middleName
            user ["lastName"] = lastName
            db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    Log.d("dbfirebase","save: ${user}")
                }
                .addOnFailureListener {
                    Log.d("dbfirebase Failed","${user}")
                }
            db.collection("users")
                .get()
                .addOnCompleteListener {
                    val result: StringBuffer = StringBuffer()
                    if (it.isSuccessful) {
                        for (document in it.result!!) {
                            Log.d(
                                "dbfirebase", "retrive" +
                                        "${document.data.getValue("firstName")}" +
                                        "${document.data.getValue("middleName")}" +
                                        "${document.data.getValue("lastName")}"
                            )
                        }

                    }
                }
        }
    }
}