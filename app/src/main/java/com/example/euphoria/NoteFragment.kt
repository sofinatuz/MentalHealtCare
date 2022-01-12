package com.example.euphoria

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener


class NoteFragment : Activity() {
    private var dbManager: DBManager? = null
    private var listView: ListView? = null
    private var adapter: SimpleCursorAdapter? = null
    val from = arrayOf<String>(
        DatabaseHelper._ID,
        DatabaseHelper.SUBJECT, DatabaseHelper.DESC
    )
    val to = intArrayOf(R.id.id, R.id.title, R.id.desc)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notes)
        dbManager = DBManager(this)
        dbManager!!.open()
        val cursor = dbManager!!.fetch()
        listView = findViewById<View>(R.id.list_view) as ListView
        listView!!.emptyView = findViewById(R.id.empty)
        adapter = SimpleCursorAdapter(this, R.layout.fragment_notes_view, cursor, from, to, 0)
        adapter!!.notifyDataSetChanged()
        listView!!.adapter = adapter

        // OnCLickListiner For List Items
        listView!!.onItemClickListener =
            OnItemClickListener { parent, view, position, viewId ->
                val idTextView = view.findViewById<View>(R.id.id) as TextView
                val titleTextView = view.findViewById<View>(R.id.title) as TextView
                val descTextView = view.findViewById<View>(R.id.desc) as TextView
                val id = idTextView.text.toString()
                val title = titleTextView.text.toString()
                val desc = descTextView.text.toString()
                val modify_intent = Intent(applicationContext, ModifyRecord::class.java)
                modify_intent.putExtra("title", title)
                modify_intent.putExtra("desc", desc)
                modify_intent.putExtra("id", id)
                startActivity(modify_intent)
            }
        val btnTambah = findViewById<View>(R.id.tambahCatatan) as Button
        btnTambah.setOnClickListener {
            val add_mem = Intent(this@NoteFragment, AddRecord::class.java)
            startActivity(add_mem)
        }
    }
}