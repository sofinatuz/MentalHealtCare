package com.example.euphoria

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText


class AddRecord : Activity(), View.OnClickListener {
    private var addTodoBtn: Button? = null
    private var subjectEditText: EditText? = null
    private var descEditText: EditText? = null
    private var dbManager: DBManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Add Record"
        setContentView(R.layout.fragment_notes_add)
        subjectEditText = findViewById<View>(R.id.subject_edittext) as EditText
        descEditText = findViewById<View>(R.id.description_edittext) as EditText
        addTodoBtn = findViewById<View>(R.id.add_record) as Button
        dbManager = DBManager(this)
        dbManager!!.open()
        addTodoBtn!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_record -> {
                val name = subjectEditText!!.text.toString()
                val desc = descEditText!!.text.toString()
                dbManager?.insert(name, desc)
                val main = Intent(this@AddRecord, NoteFragment::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(main)
            }
        }
    }
}
