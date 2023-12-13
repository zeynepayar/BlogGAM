package com.example.bloggam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.example.bloggam.databinding.BlogKartiBinding
import com.example.bloggam.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseFireStore: FirebaseFirestore
    var database: FirebaseDatabase? = null
    // Binding tanımlama kısmı
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    // ArrayList ve Adapter tanımlama
    var constList =ArrayList<Bloglar>()
    var adapter:BloglarAdapter?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        val view=binding.root
        val adapter =BloglarAdapter(requireContext(), constList)
        binding.ListView.adapter = adapter
        // Adapter'ı listView'a bağlamak için
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        firebaseFireStore = FirebaseFirestore.getInstance()
        binding.ListView.adapter = adapter
        //adapter.notifyDataSetChanged()
        // constList'e veri ekleme işlemi buraya taşındı
        constList.add(Bloglar("Sağlık" ,"Sağlık, yaşamımızın en değerli varlıklarından biridir ve bu alandaki bilgiler sürekli olarak gelişmektedir. Örneğin, insan vücudu, bir kişinin ömrü boyunca yaklaşık 900 tonluk yiyecek ve 50,000 litre su tüketebilecek kadar etkileyici bir işleme kapasitesine sahiptir. Ayrıca, insan vücudu, DNA'sındaki bilgileri her bir hücreye sığdırarak inanılmaz bir genetik veri depolama kapasitesine sahiptir. Gözlerimiz, diğer tüm kas gruplarından daha hızlı ve aktif çalışan bir yapıya sahiptir; bu nedenle, bir insanın gözleri, günde yaklaşık 50 kez tam bir dönüş yapabilir. Araştırmalar, düzenli egzersizin notlar almayı, öğrenmeyi ve hatta düşünmeyi iyileştirebileceğini göstermektedir. Ayrıca, gülümsemenin sadece bir yüz ifadesi olmadığı, aynı zamanda beynin endorfin salgılamasını artırarak bir kişinin ruh halini iyileştirebileceği de bilinmektedir. Sağlıklı bir yaşam sürmek için yeterli su içmek de kritiktir; örneğin, insan vücudu yeterli su almadığında, beynin performansı ve odaklanma yeteneği azalabilir. Bu ilginç bilgiler, sağlıkla ilgili konularda farkındalığımızı artırarak daha bilinçli yaşamamıza katkıda bulunabilir.",R.drawable.saglik)
        )
        constList.add(Bloglar("Temiz Kod" , "Temiz kod, yazılım geliştiricilerin sadece çalışan bir program üretmekle kalmayıp aynı zamanda kodlarını anlaşılır, sürdürülebilir ve kolayca geliştirilebilir hale getirmelerini amaçlayan bir disiplindir. Temiz kod, programlama sanatının bir yansımasıdır ve kod yazma sürecindeki estetik bir yaklaşımı ifade eder. Okunabilir kod yazmak, kodun anlaşılabilirliğini artırır ve ekibin etkileşimini kolaylaştırır. Her bir fonksiyonun ve sınıfın tek bir sorumluluğu olmalıdır, bu da kodun karmaşıklığını azaltır ve bakım maliyetini düşürür. Açıklayıcı isimlendirme ve anlamlı değişken adları kullanmak, kodun belgelendirilmesini sağlar ve hataların daha hızlı tespit edilmesine yardımcı olur. Gereksiz yorumlardan kaçınılmalı, kodun kendini açıklamasına öncelik verilmelidir. DRY prensibi (Don't Repeat Yourself), kodun tekrarlanmasını engelleyerek hem hata oranını düşürür hem de bakım sürecini kolaylaştırır. Temiz kod yazmak, geliştiriciler arasında daha etkili iletişim kurulmasına, yeni ekip üyelerinin hızla adapte olmasına ve projenin uzun vadeli başarısına katkı sağlar.",R.drawable.temizkod)
        )
        constList.add(Bloglar("Pişmaniye", "Kocaeli, Türkiye'nin endüstriyel ve kültürel zenginlikleriyle bilinen bir şehri olmasının yanı sıra, geleneksel tatlarıyla da öne çıkmaktadır. Bu kapsamda, Kocaeli'nin gastronomi haritasında özel bir yere sahip olan lezzetlerden biri de Kocaeli pişmaniyesidir. İzmit ilçesinde özellikle üretilen bu geleneksel Türk tatlısı, ince telli yapısı ve benzersiz lezzetiyle tanınır. Pişmaniye yapımı, geleneksel el sanatlarına dayalı uzun bir süreç gerektirir ve genellikle pekmez, un ve şekerin mükemmel bir birleşimiyle elde edilen hamurun ustaca açılmasıyla oluşur. Kocaeli pişmaniyesi, diğer pişmaniye çeşitlerinden farklı olarak kendine özgü bir tat profiline sahiptir ve bu lezzet, özellikle şehirdeki pastane ve tatlıcılarda özenle üretilerek yaşatılmaktadır. Kocaeli pişmaniyesi, incecik ayrılan telleri ve hafif dokusuyla dikkat çeker; doğal malzemeler kullanılarak özenle hazırlanır ve geleneksel tarife bağlı kalarak üretilir. Şehirdeki pişmaniye ustaları, bu geleneksel tatın lezzetini ve dokusunu korumak adına büyük bir özenle çalışmaktadır. Kocaeli pişmaniyesi, genellikle özel günlerde ve bayramlarda hediye olarak tercih edilen bir lezzettir ve bu özgün tat, şehirdeki pek çok etkinlik ve festivalde ön planda yer alarak şehre özgü bir kimlik kazandırmaktadır. Bu eşsiz tat, sağlıklı ve doğal malzemeler kullanılarak üretildiği için aynı zamanda şehrin sağlıklı ve geleneksel lezzetlerinden biri olarak öne çıkar. Kocaeli pişmaniyesi, sadece bir tatlı değil, aynı zamanda şehrin kültürel mirasının bir parçasıdır ve yerel halk arasında sıkça paylaşılan bir lezzettir.", R.drawable.pismaniye))
        constList.add(
            Bloglar("İzmit Saat Kulesi",
                "İzmit’in Kemalpaşa Mahallesi'nde yükselen saat kulesi, şehrin tarihine tanıklık eden özel bir yapıdır. 1900 (1901) yılında İzmit Mutasarrıfı Musa Kazım Bey tarafından Sultan II. Abdülhamid’in tahta çıkışının 25. yıldönümü anısına yaptırılmıştır. Mimar Vedat Tek Bey tarafından inşa edilen kule, kesme taş ve mermer malzeme kullanılarak titizlikle oluşturulmuştur. Klasik üslupta dikdörtgen plana sahip olan kule, zemin katı dahil dört bölümden oluşmaktadır. Zemin katı, diğer üç kattan daha geniş bir şekilde yapılmıştır.\n" +
                        "\n" +
                        "Kulenin kuzey cephesindeki giriş kısmı, 1. katın kenarlarında mermer sütunlara ve çeşmelere ev sahipliği yapmaktadır. Basık kemerli çeşmelerin yalakları, özel bir detay olarak dikkat çeker. Çeşmelerin aynalığında, ay yıldız ve çelenk motifleri bulunmaktadır. Çeşmelerin üst kısmında ise kitabe kuşakları yer almaktadır.\n" +
                        "\n" +
                        "Kulenin 2., 3. ve 4. katları oldukça detaylı bir şekilde inşa edilmiştir. 2. katta köşelerde sütunlara ve kaş kemerli açıklıklara yer verilmiş, aynı katta dökme demir korkuluklardan oluşan bir gezinti alanı bulunmaktadır. Kule, 3. katında her cephe yüzeyinde kaç kemerli pencerelere sahiptir. Pencerelerin alt kısımlarında II. Abdülhamid’in tuğralarını içeren mermer madalyonlar bulunmaktadır. 3. katın köşelerinde ise silindirik ve elips şeklinde düz madalyonlar göze çarpar.\n" +
                        "\n" +
                        "Kulenin 4. katında, zarif çerçeveler içinde yer alan saatler dikkat çeker. Saatlerin üst kısımlarında üçer pencere kuşağı bulunmaktadır. İnce-sivri külah uygulamasının dikkat çekici olduğu kulede, saçak sistemi oldukça taşkın bir şekilde yapılmıştır.\n" +
                        "\n" +
                        "Saat kulesi, Kocaeli'nin sembolü haline gelmiş, her katında farklı mimari uygulamaların, süsleme unsurlarının denendiği bir yapıdır. 1971 yılında Seka Genel Müdürlüğü’nün katkılarıyla, 2006 yılında Kocaeli Büyükşehir Belediyesi tarafından restore edilen kule, günümüzde hala kullanılmakta ve şehrin tarihî atmosferine katkı sağlamaktadır.Bir rivayete göre de hikayesi şu şekilde Uzun yıllar önce İzmit'te meşhur nam salmış efsane bir tatlıcı varmış. Kendi yaptığı tatlıları çok meşhurmuş. Turistler onun mekanının önünde tatlı almak için sıra beklerlermiş. Bu tatlıcı ustanın sevdiği güzeller güzeli bir kız varmış. Güzeller güzeli ama şişman bir kızmış. Aşkın gözü kör olduğundan bu tatlıcı bu kızı çok sevmekteymiş. Bir gün evlenmişler ve tatlıcı yaptığı tatlıların birisinin ismine Şişmaniye ismini vermiş çünkü kız o tatlıyı çok seviyormuş. Gün geçmiş kıskançlık ve huysuzlugu yüzünden tatlıcı kızla ayrılmış. Pişman olduğu için Şişmaniye tatlısının ismini Pişmaniyeye çevirmiş.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n"+
 "\n"+
 "\n", R.drawable.saatkulesi)
        )
        constList.add(Bloglar("Şeytan Üçgeni", "Dünya üzerinde gizemli ve endişe verici bir ünü olan yerlerden biri, Şeytan Üçgeni olarak bilinen bölgedir. Bu bölge, Kuzey Atlantik Okyanusu'nda yer alır ve Bermuda, Miami ve Porto Riko arasında kalan geniş bir alana yayılmıştır. Şeytan Üçgeni, yıllar boyunca kaybolan gemiler, uçaklar ve diğer araçlarla ilişkilendirilen gizemli olaylarla ün kazanmıştır.\n" +
                "\n" +
                "Şeytan Üçgeni'nde yaşanan olaylar, genellikle gizemli ve bilinmeyen faktörlere dayanır. Bu bölgede kaybolan gemi ve uçakların sayısının neden diğer deniz veya hava bölgelerine kıyasla daha fazla olduğu hala bir bilmece olarak duruyor. Kaybolan araçların birçoğu, hiçbir iz veya açıklama olmaksızın ortadan kaybolmuştur.\n" +
                "\n" +
                "Bölgedeki gizemli olaylar arasında manyetik anomali, yoğun fırtınalar, okyanus akıntıları ve spekülasyonlara göre uzaylı varlıkların müdahalesi gibi çeşitli teoriler bulunmaktadır. Ancak, Şeytan Üçgeni'ndeki olayları tam olarak açıklayacak bilimsel bir neden henüz bulunamamıştır.\n" +
                "\n" +
                "Şeytan Üçgeni'yle ilgili bir diğer ilginç yön, bölgede meydana gelen manyetik anomalilerdir. Manyetik pusula iğnesinin normalden sapması, bölgedeki manyetik alanın dalgalanmalarına işaret eder. Bu durum, navigasyon sistemlerinin yanlış yönlendirilmesine ve gemilerin veya uçakların yanlış rotalara yönlendirilmesine neden olabilir.\n" +
                "\n" +
                "Bilim insanları, Şeytan Üçgeni'nde meydana gelen olayları çeşitli faktörlere bağlamaya çalışmaktadır. Manyetik alan dalgalanmaları, hava koşulları, jeolojik yapı ve diğer doğal olgular, bu gizemli bölgedeki olaylara açıklamalar getirmeye çalışan teoriler arasındadır.\n" +
                "\n" +
                "Şeytan Üçgeni, popüler kültürde ve efsanelerde sıkça karşımıza çıkan bir fenomendir. Birçok kitap, film ve televizyon programı, bu bölgedeki gizemli olayları konu alarak merak uyandırmıştır. Ancak, bu fenomenin tam olarak anlaşılabilmesi için daha fazla bilimsel araştırmaya ihtiyaç vardır. Şu ana kadar, Şeytan Üçgeni'nin gizemi, insanların hayal gücünü ve araştırma isteğini canlı tutmaya devam etmektedir.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n", R.drawable.seytanucgeni))
        // Firebase'den verileri alıp constlist içine ekleme (Adminin eklediği kitaplar)
        firebaseFireStore.collection("bloglar").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val blogAdi = document.getString("BlogAdi")
                    val blogAciklama = document.getString("BlogAciklama")

                    if (blogAdi != null && blogAciklama != null) {
                        constList.add(Bloglar(blogAdi, blogAciklama, R.drawable.blog))

                        // adapter.notifyDataSetChanged() ile adapter'a değişiklik olduğunu bildirilir.
                        adapter.notifyDataSetChanged()

                    }

                }
            }
            .addOnFailureListener { exception ->
                // Veri alınırken bir hata oluşursa buradan dönüt verilir.
            }

        return view



    }
    class BloglarAdapter: BaseAdapter{
        var constList= ArrayList<Bloglar>()
        var context:Context?=null

        constructor(context: Context,constList: ArrayList<Bloglar>):super(){
            this.constList=constList
            this.context=context//gerekli değişken ver kurucu metodları oluşturduk
        }
        override fun getCount(): Int {
            return  constList.size
        }

        override fun getItem(position: Int): Any {
           return constList[position]//getItem constlistin positionunu veriyor
        }

        override fun getItemId(position: Int): Long {
          return  position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val bloglarBinding: BlogKartiBinding
            val view: View
            if (convertView == null) {
                val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                bloglarBinding = BlogKartiBinding.inflate(inflater, parent, false)
                view = bloglarBinding.root
                view.tag =  bloglarBinding
            } else {
                view = convertView
                bloglarBinding = view.tag as BlogKartiBinding
            }
            val posBloglar = constList[position]

            bloglarBinding.kartAdi.text = posBloglar.constAdi
            bloglarBinding.blogAciklama.text = posBloglar.constAciklama
            bloglarBinding.kartResim.setImageResource(posBloglar.constResim!!)


            bloglarBinding.kartResim.setOnClickListener(){
               var intent = Intent(context,Bloglar_detay::class.java)
               intent.putExtra("constAdi", posBloglar.constAdi)
                intent.putExtra("constAciklama", posBloglar.constAciklama)
                intent.putExtra("constResim", posBloglar.constResim!!)
                context!!.startActivity(intent)
            }

            return view

    }
        }


    }


