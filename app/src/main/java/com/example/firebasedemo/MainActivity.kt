package com.example.firebasedemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Student")

        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener() {
            val stdID: String = findViewById<TextView>(R.id.tfID).text.toString()
            val stdName = findViewById<TextView>(R.id.tfName).text.toString()
            val stdProgramme = findViewById<TextView>(R.id.tfProgramme).text.toString()

            myRef.child(stdID).child("Name").setValue(stdName)
            myRef.child(stdID).child("Programme").setValue(stdProgramme)
        }

        val getData = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                var sb = StringBuilder()
                for(std in p0.children){
                    var name = std.child("Name").getValue()
                    sb.append("${name} \n")
                }
                val tvResult: TextView = findViewById(R.id.tvResult)
                tvResult.setText(sb)
            }
        }

        val btnGet: Button = findViewById(R.id.btnGet)
        btnGet.setOnClickListener() {
             myRef.addValueEventListener(getData)
//             any changes will invoke
             myRef.addListenerForSingleValueEvent(getData)

            // filter
//            val qry:Query = myRef.orderByChild("Programme").equalTo("RIT")
//            qry.addValueEventListener(getData)
//            qry.addListenerForSingleValueEvent(getData)
        }


    }
}