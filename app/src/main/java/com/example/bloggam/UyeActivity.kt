package com.example.bloggam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.bloggam.databinding.ActivityUyeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference // Import ekledik
import com.google.firebase.database.FirebaseDatabase // Import ekledik

class UyeActivity : AppCompatActivity() {
    lateinit var binding: ActivityUyeBinding
    lateinit var auth:FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityUyeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        // Firebase Database başlat
        database = FirebaseDatabase.getInstance()
        // Varsayılan olarak "users" düğümünün olduğunu varsayalım
        databaseReference = database?.reference?.child("users")

        //kaydet butonu ile kaydetme adımları
        binding.uyekaydetbutton.setOnClickListener {
            var uyeadsoyad =binding.uyeadsoyad.text.toString()
            var uyeemail =binding.uyeemail.text.toString()
            var uyeparola =binding.uyeparola.text.toString()
            //doldurmadan geçiş yapılsın istmemiyoruz
            if(TextUtils.isEmpty(uyeadsoyad)){
                binding.uyeadsoyad.error="Lütfen adınızı ve soyadınızı giriniz."
                return@setOnClickListener//tekrar dönüp konrtol etmesini sağlıyoruz
            }else if(TextUtils.isEmpty(uyeemail)) {
                binding.uyeemail.error = "Lütfen e-mailinizi giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeparola)) {
                binding.uyeparola.error = "Lütfen parolanızı giriniz."
                return@setOnClickListener

            }
            //email , parola ve kullanıcı bilgilerini veri tabnına ekleme
            auth.createUserWithEmailAndPassword(binding.uyeemail.text.toString(), binding.uyeparola.text.toString())
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful) {
                        //şuanki kullanıcı bilgilerini alalım
                        var currentUser = auth.currentUser
                        //kullanıcı id sini alıp o id adı altında adımızı ve soyadımızı kaydediyoruz
                        var currentUserDb =currentUser?.let { it1-> databaseReference?.child(it1.uid)}
                        currentUserDb?.child("adisoyadi")?.setValue(binding.uyeadsoyad.text.toString())
                        Toast.makeText(this@UyeActivity,"Kayıt Başarılı",Toast.LENGTH_LONG).show()

                    } else{
                        Toast.makeText(this@UyeActivity,"Kayıt Hatalı",Toast.LENGTH_LONG).show()
                    }
                }




        }
        //giriş sayfasına gitmek için
        binding.uyegirisyapbutton.setOnClickListener {
            intent = Intent(applicationContext,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}