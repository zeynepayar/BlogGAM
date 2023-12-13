package com.example.bloggam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bloggam.databinding.ActivityProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.content.Intent
import android.widget.Toast

class ProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilBinding
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        // Firebase Database başlat
        database = FirebaseDatabase.getInstance()
        // Varsayılan olarak "users" düğümünün olduğunu varsayalım
        databaseReference = database?.reference?.child("users")
        var currentUser = auth.currentUser
        binding.profilemail.text = "E-mail:" + currentUser?.email//aktardık

        //realtime - database deki id'ye ulaşıp altındaki childların veriyi sayfaya aktaracağız
        var userReference = databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object : ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot){
              binding.profiladsoyad.text = "Tam adınız: " + snapshot.child("adisoyadi").value.toString()

           }
            override fun onCancelled(error: DatabaseError){

            }
        })
        //çıkış yap butonu
        binding.profilcikisyapbutton.setOnClickListener {
            auth.signOut() //oturmu sonlandırdık
            startActivity(Intent(this@ProfilActivity,GirisActivity::class.java))
            finish()
        }
        //hesabımı sil  butonu
        binding.prfofilhesabMSilbutton.setOnClickListener {
            currentUser?.delete()?.addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(applicationContext,"Hesabınız silindi",
                        Toast.LENGTH_LONG).show()
                    auth.signOut()
                    startActivity(Intent(this@ProfilActivity,GirisActivity::class.java))
                    finish()
                    var currentUserDb =currentUser?.let { it1-> databaseReference?.child(it1.uid)}
                    currentUserDb?.removeValue()
                    Toast.makeText(applicationContext,"Adi Soyadi Silindi",
                        Toast.LENGTH_LONG).show()
                }
            }

        }
        //güncelle butonu
        binding.profilbilgilerimigNcellebutton.setOnClickListener {
            startActivity(Intent(this@ProfilActivity, GuncelleActivity::class.java))
            finish()
        }
        //uygulamaya git butonu
        binding.profiluygulamayagitbutton.setOnClickListener(){
            intent = Intent(applicationContext, anasayfa_Activity::class.java)
            startActivity(intent)
            finish()
        }
        //admin paneline git butonu
        binding.profiladminpanalibutton.setOnClickListener(){
            intent = Intent(applicationContext, AdminActivity::class.java)
            startActivity(intent)
            finish()
        }

        }



}