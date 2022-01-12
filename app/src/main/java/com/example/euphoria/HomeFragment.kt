package com.example.euphoria

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class HomeFragment : Fragment() {
    var btnInfo: Button? = null
    var btnCall: Button? = null

    // Method onCreateView dipanggil saat Fragment harus menampilkan layoutnya         // dengan membuat layout tersebut secara manual lewat objek View atau dengan      // membaca file XML
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout tampilan untuk fragment ini
        val view = inflater.inflate(
            R.layout.fragment_home,
            parent, false
        )
        val btnInfo = view.findViewById<View>(R.id.info) as Button
        val btnCall = view.findViewById<View>(R.id.Call) as Button
        val btnSesi1 = view.findViewById<View>(R.id.btnSesi1) as Button
        val btnSesi2 = view.findViewById<View>(R.id.btnSesi2) as Button
        val btnNotes = view.findViewById<View>(R.id.notes) as Button
        val btnMapLain = view.findViewById<View>(R.id.btnMapLain) as Button

        btnNotes.setOnClickListener {
            val intentNotes = Intent(activity, NoteFragment::class.java)
            startActivity(intentNotes)
        }
        btnCall.setOnClickListener {
            val nextFrag = CallFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        btnInfo.setOnClickListener {
            val nextFrag = InformationFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        btnMapLain.setOnClickListener {
            val nextFrag = KonsultasiLainnya()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        btnSesi2.setOnClickListener {
            val intentSesi2 = Intent(activity, ShowMap2::class.java)
            startActivity(intentSesi2)
        }
        btnSesi1.setOnClickListener {
            val intentSesi1 = Intent(activity, ShowMap1::class.java)
            startActivity(intentSesi1)
        }

//        btnSesi1.setOnClickListener {
//            val url = "https://api.whatsapp.com/send?phone=" + "6281313016422"
//            val i = Intent(Intent.ACTION_VIEW)
//            i.data = Uri.parse(url)
//            startActivity(i)
//        }
//        btnSesi2.setOnClickListener {
//            val url = "https://api.whatsapp.com/send?phone=" + "6289520132211"
//            val i = Intent(Intent.ACTION_VIEW)
//            i.data = Uri.parse(url)
//            startActivity(i)
//        }
        return view
    }

    // Method ini dipanggil sesaat setelah onCreateView().
    // Semua pembacaan view dan penambahan listener dilakukan disini (atau
    // bisa juga didalam onCreateView)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
}