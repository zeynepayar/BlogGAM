package com.example.bloggam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.bloggam.databinding.ActivityGirisBinding
import com.example.bloggam.databinding.ActivityUyeBinding
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class GirisActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityGirisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance() //Konum izni aldık
        //kullanıcının oturum açıp açmadığını kontrol edelim
        var currentUser =auth.currentUser
        if(currentUser!= null){
            startActivity(Intent(this@GirisActivity,ProfilActivity::class.java))
            finish()
        }
        //giriş yao buttonuna tıklandığında
        binding.girisyapbutton.setOnClickListener() {
            var girisemail = binding.girisemail.text.toString()
            var girisparola = binding.girisparola.text.toString()
            if(girisemail == null ){
                binding.girisemail.error= "lütfen E-mail adresininzi yazınız"
                return@setOnClickListener
            }
            if(girisparola== null) {
                binding.girisparola.error = "lütfen parolanızı yazınız"
                return@setOnClickListener
            }
            if (girisemail!= null && girisparola!= null) {
                //giirş bilgilerimizi doğrulayıp giriş yapıyoruz
                auth.signInWithEmailAndPassword(girisemail, girisparola)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            intent = Intent(applicationContext, ProfilActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Giriş hatalı lütfen tekrar deneyiniz.",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
            }


        }
        //parolamı unuttum  sayfasına gitmek için
        binding.girisparolaunuttum.setOnClickListener(){
            intent= Intent(applicationContext, PsifirlaActivity::class.java)
            startActivity(intent)
            finish()
        }
        //yeni üyelik sayfasına gitmek için
        binding.girisyeniuyelik.setOnClickListener {
            intent= Intent(applicationContext,UyeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}