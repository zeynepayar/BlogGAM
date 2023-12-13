package com.example.bloggam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.bloggam.databinding.ActivityPsifirlaBinding
import com.google.firebase.auth.FirebaseAuth

class PsifirlaActivity : AppCompatActivity() {
    lateinit var binding: ActivityPsifirlaBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityPsifirlaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()//firebase auth konumuna erişerek istediğmizi yapmamızı sağlıyor

        binding.psifirlamabutton.setOnClickListener {
            var psifirlaemail = binding.psifirlaemail.text.toString().trim()//trimle başında veya sonunda boşluk bıraktıysak onu kaldırmamıza yaradı
            if(TextUtils.isEmpty(psifirlaemail)){
                binding.psifirlaemail.error="lütfen E-mail adresinizi yazınız"
            }else{
                auth.sendPasswordResetEmail(psifirlaemail)
                    .addOnCompleteListener(this){ sifirlama->
                        if(sifirlama.isSuccessful){
                            binding.psifirlamesaj.text= "E-mail adresinize sıfırlama bağlantısı gönderildi lütfen kontrl ediniz"
                        }else{
                            binding.psifirlamesaj.text= "Sıfırlama işlemi başarısız "
                        }
                    }
            }
        }
        //giriş sayfasına gitmek için
        binding.psifirlamagirisyapbutton.setOnClickListener {
            intent= Intent(applicationContext,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}