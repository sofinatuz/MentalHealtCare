package com.example.euphoria

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.euphoria.MessagingAdapter
import com.codepalace.chatbot.utils.BotResponse
import com.codepalace.chatbot.utils.Constants
import com.codepalace.chatbot.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_therapy.*
import kotlinx.coroutines.*

class TherapyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout tampilan untuk fragment ini
        val view = inflater.inflate(
            R.layout.chatawal,
            parent, false
        )
        val btnChat = view.findViewById<View>(R.id.btnChat) as Button

        btnChat.setOnClickListener {
            val intentNotes = Intent(activity, TherapyFragment2::class.java)
            startActivity(intentNotes)
        }
        return view
    }

    // Method ini dipanggil sesaat setelah onCreateView().
    // Semua pembacaan view dan penambahan listener dilakukan disini (atau
    // bisa juga didalam onCreateView)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
}