package com.example.bloggam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bloggam.databinding.ActivityAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseFireStore: FirebaseFirestore

    // Firebase Firestore veritabanı ile etkileşim için kullanılacak FirebaseFirestore nesnesinin bildirildiği
    var database: FirebaseDatabase? = null
    // Firebase Firestore veritabanı ile etkileşim için kullanılacak FirebaseFirestore nesnesinin bildirildiği
    // private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    // private lateinit var permissionLauncher: ActivityResultLauncher<String>
    // private var imageuri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_admin_paneli)

        val binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instance
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        firebaseFireStore = FirebaseFirestore.getInstance()
        //storage = Firebase.storage
        //registerLauncher()
        //inItVars()
        //registerClickEvents()

        //Çıkış yap butonuna basıldığında profil sayfasına geri dönmek için
        binding.admincikisyapButton.setOnClickListener() {
            intent = Intent(applicationContext, ProfilActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.adminblogkaydetbutton.setOnClickListener() {
            //hashMapOf<String, Any>() ile boş bir Map oluşturulur. Bu Map, Firestore'a eklenecek olan verileri içerir.
            val Map = hashMapOf<String, Any>()

            Map.put("BlogAdi", binding.adminblogGir.text.toString())
            Map.put("BlogAciklama", binding.adminblogAciklama.text.toString())

            firebaseFireStore.collection("bloglar").add(Map)
                .addOnCompleteListener() { task ->
                    if (task.isComplete && task.isSuccessful) {
                        Toast.makeText(this, "Başarılı Yükleme", Toast.LENGTH_LONG)
                            .show()

                    }

                }.addOnFailureListener {
                    Toast.makeText(
                        this@AdminActivity,
                        "Başarısız Yükleme",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

    }
}
