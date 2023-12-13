package com.example.bloggam

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.bloggam.databinding.FragmentProfilBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage



class ProfilFragment : Fragment() {
    lateinit var binding: FragmentProfilBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    private val PICK_IMAGE_REQUEST_CODE = 1
    val applicationContext = null
    var imageFileName =null
    var selectedImageUri = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        val view = binding.root

        // getInstance -->
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = Firebase.storage
        databaseReference = database?.reference!!.child("profile")
        val storageRef = FirebaseStorage.getInstance().reference

        var currentUser = auth.currentUser
        //binding.ProfileEmailText.text ="Email: " + currentUser?.email/

        //Oturum açmış kişinin isim ve soyismini getirir.
        var userReference = databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.profilfragmentisimsoyisim.text = " " + snapshot.child("adisoyadi").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        // HESABIMI SİL BUTONUNA TIKLANDIĞINDA:
        binding.profilfragmenthesapsilbutton.setOnClickListener() {
            currentUser?.delete()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    var currentUserDb =
                        currentUser?.let { it1 -> databaseReference?.child(it1.uid) }
                    currentUserDb?.removeValue()
                    Toast.makeText(
                        requireContext(), "Hesap başarıyla silindi.", Toast.LENGTH_LONG
                    ).show()
                    auth.signOut()
                    var intent = Intent(requireContext(), ProfilFragment::class.java)
                    startActivity(intent)


                } else {
                    Toast.makeText(
                        requireContext(), "Hesap silinemedi. Tekrar deneyiniz.", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


        // Bilgilerimi Güncelle Sayfasına gitmek için
        binding.profilfragmentbilgilerimiguncellebutton.setOnClickListener() {
            var intent = Intent(requireContext(), GuncelleActivity::class.java)
            startActivity(intent)

        }
        //Profil fotoğrafımı değiştir butonuna basıldığında
        binding.profilfragmentfotografdegis.setOnClickListener() {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*" //Storage dosyasının adı.
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }
        //Çıkış yap butonuna basıldığında
        binding.profilfragmentcKSyapbutton.setOnClickListener() {
            var intent = Intent(requireContext(),GirisActivity::class.java)
            startActivity(intent)

        }





        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                val storageRef = FirebaseStorage.getInstance().reference
                val imagesRef = storageRef.child("images") // İstenilen klasörü belirleyin

                val imageFileName = "image_" + System.currentTimeMillis() + ".jpg"
                val imageRef = imagesRef.child(imageFileName)

                val uploadTask = imageRef.putFile(selectedImageUri)

                uploadTask.addOnSuccessListener { taskSnapshot ->
                    // Yükleme başarılı olduğunda yapılacak işlemler
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val imageURL = uri.toString()
                        Glide.with(this)
                            .load(imageURL)
                            .into( binding.imageView) // yourImageView, resmin yükleneceği ImageView'in referansı olmalıdır
                        //Toast.makeText(applicationContext, "Yükleme Başarılı", Toast.LENGTH_SHORT).show()
                        // Firebase Storage'da bulunan dosyanın URL'sini almak için

                        // imageURL'ü istediğiniz yerde kullanabilirsiniz
                        //}.addOnFailureListener { exception ->
                        // URL alınamazsa hata işlemleri
                        //Toast.makeText(applicationContext, "URL alınamadı", Toast.LENGTH_SHORT).show()
                    }
                    // }.addOnFailureListener { exception ->
                    // Yükleme başarısız olduğunda yapılacak işlemler
                    //Toast.makeText(applicationContext, "Yükleme Başarısız", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
