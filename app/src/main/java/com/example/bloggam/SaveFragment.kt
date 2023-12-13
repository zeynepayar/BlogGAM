package com.example.bloggam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bloggam.databinding.FragmentSaveBinding
import com.google.firebase.database.*
import android.widget.ArrayAdapter


class SaveFragment : Fragment() {


    private lateinit var binding: FragmentSaveBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSaveBinding.inflate(inflater, container, false)
        val view = binding.root

        // Firebase veritabanına bağlan
        database = FirebaseDatabase.getInstance()
        val blogRef = database.getReference("bloglar")
        // Blogları dinleyerek güncelle
        blogRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Firebase veritabanındaki blogları çek
                val blogList = mutableListOf<Bloglar>()
                for (blogSnapshot in snapshot.children) {
                    val blog = blogSnapshot.getValue(Bloglar::class.java)
                    blog?.let {
                        blogList.add(it)
                    }
                }
                // Adapter oluştur
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    blogList.map { "${it.constAdi} - ${it.constAciklama}" }
                )

                // ListView'a adapter'ı set et
                binding.listView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        return view
    }
}



