package com.example.bloggam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.bloggam.databinding.ActivityBloglarDetayBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets


class Bloglar_detay : AppCompatActivity() {
    lateinit var binding: ActivityBloglarDetayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bloglar_detay)
        val binding = ActivityBloglarDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var bundle: Bundle? = intent.extras
        var adi = bundle!!.getString("constAdi")
        var aciklama = bundle!!.getString("constAciklama")
        var resim = bundle!!.getInt("constResim")

        binding.detayAdi.text = adi
        binding.detayAciklama.text = aciklama
        binding.detayResim.setImageResource(resim)

        binding.blogdetaycikisyapbutton.setOnClickListener {
            intent = Intent(applicationContext, HomeFragment::class.java)
            startActivity(intent)
            finish()
        }
        binding.blogdetaykaydetbutton.setOnClickListener {
            val saveFragment = SaveFragment()

            // Verileri bundle'a ekle
            val blogBundle = Bundle()
            blogBundle.putString("constAdi", adi)
            blogBundle.putString("constAciklama", aciklama)
            blogBundle.putInt("constResim", resim)
            // Bundle'ı SaveFragment'a gönder
            saveFragment.arguments = blogBundle
            // FragmentTransaction başlat ve SaveFragment'ı göster
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frame_container, saveFragment)
                .addToBackStack(null)
                .commit()
        }
        val cevir: Button = findViewById(R.id.cevir)
        val detay: TextView = findViewById(R.id.detayAciklama)
        val detayText = detay.text
        cevir.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                // Çeviri isteği için gerekli veriler
                val requestData = """
        {
            "text": "$detayText",
            "sourceLanguage": "tr",
            "targetLanguage": "en"
        }
    """.trimIndent()

                try {
                    val url = URL("https://drema.info/test.php")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true
                    val out: OutputStream = connection.outputStream
                    val bytes = requestData.toByteArray(StandardCharsets.UTF_8)
                    out.write(bytes)
                    out.flush()
                    out.close()
                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val response = connection.inputStream.bufferedReader().use { it.readText() }
                        runOnUiThread {
                            detay.text =
                                response.split(":")[1].substring(
                                    1,
                                    response.split(":")[1].length - 2
                                )
                        }
                    } else {
                        println("Hata: $responseCode")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("HataQ: ${e.message}" + requestData.toString())
                }
            }
        }
    }
}
