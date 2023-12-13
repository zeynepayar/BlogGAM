package com.example.bloggam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bloggam.databinding.ActivityGuncelleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GuncelleActivity : AppCompatActivity() {
    lateinit var binding: ActivityGuncelleBinding
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityGuncelleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference?.child("users")

        val currentUser = auth.currentUser//kullanıcıyı currentuser iine aktarmış olduk
        binding.guncelleemil.setText(currentUser?.email)
        //realtime databasede bulunan kullanıcının id sine erişip adını soyadını alalım
        var userReference=databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
            binding.guncelleadsoyad.setText(snapshot.child("adisoyadi").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        //bilgilerimi güncelle button
        binding.guncellebilgilerimibutton.setOnClickListener {
            var guncelleemil= binding.guncelleemil.text.toString().trim()
            currentUser!!.updateEmail(guncelleemil)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"E-mail güncellendi ",
                            Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext,"E-mail güncellemesi başarısız ",
                            Toast.LENGTH_LONG).show()
                    }
                }
            //parola güncelleme
            var guncelleparola =binding.guncelleparola.text.toString().trim()
            currentUser!!.updatePassword(guncelleparola)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Parola güncellendi ",
                            Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext,"Parola güncellemesi başarısız ",
                            Toast.LENGTH_LONG).show()
                    }
                }
               //ad soyad güncelemesi
            var currentUserDb =currentUser?.let { it1-> databaseReference?.child(it1.uid)}
            currentUserDb?.removeValue()
            currentUserDb?.child("adisoyadi")?.setValue(binding.guncelleadsoyad.text.toString())
            Toast.makeText(applicationContext,"AdiSoyadi Güncellendi  ",
                Toast.LENGTH_LONG).show()

        }
        //giriş sayfasına gitmek için
        binding.guncellegirisyapbutton.setOnClickListener {
            intent = Intent(applicationContext,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}