package com.example.firebaseexample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var input: EditText
    private lateinit var btnWrite: Button
    private lateinit var btnRead: Button
    private lateinit var textView: TextView

    private lateinit var rootDatabaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)
        btnWrite = findViewById(R.id.btn)
        btnRead = findViewById(R.id.btnRead)
        textView = findViewById(R.id.textView)

        // Initialize Firebase Database reference
        rootDatabaseRef = FirebaseDatabase.getInstance().reference

        btnWrite.setOnClickListener {
            val data = input.text.toString()
            // Write data to a specific child node (e.g., "user1")
            rootDatabaseRef.child("user1").setValue(data)
        }

        btnRead.setOnClickListener {
            // Read data from the same child node "user1"
            rootDatabaseRef.child("user1").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val data = dataSnapshot.getValue(String::class.java)
                    textView.text = data ?: "No data available"
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors
                    textView.text = "Error: ${databaseError.message}"
                }
            })
        }
    }
}
