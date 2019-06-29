package com.example.gaye.gezgin.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gaye.gezgin.R;
import com.example.gaye.gezgin.adapters.CustomPostAdapter;
import com.example.gaye.gezgin.models.PostModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ListView lvPosts;
    private List<PostModel> postList = new ArrayList<>();

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        lvPosts = (ListView) v.findViewById(R.id.lv_posts);

        postList.add(new PostModel(R.drawable.img_mardin, "Mardin", "Halkların ve dinlerin buluşma noktası olan Mardin, zengin sosyolojik yapısı ile dikkat çekiyor. Bu özelliği sayesinde kültür turlarının vazgeçilmez duraklarından birisi haline gelen kentte keşfedilmeyi bekleyen düzinelerce tarihi yapı, lezzetli mutfak kültürü ve eşsiz el işi ürünler sizleri bekliyor. Mardin gezilecek yerler listesinde sizlere anlattığım mekânları ziyaret ederek, kentin barındırdığı bu zenginliklerin tadını doyasıya çıkartabilirsiniz."));
        postList.add(new PostModel(R.drawable.img_istanbul, "İstanbul", "İstanbul, Türkiye'de yer alan şehir ve ülkenin 81 ilinden biri.İstanbul gezisi planlayan İstanbullular veya İstanbul’a dışarıdan gelecek olanlar için İstanbul’da nereye gidilir sorusuna yanıt olabilecek bu liste ile İstanbul’da görülmesi gereken yerler ile alakalı tarihi yerler, saraylar, camiler, müzeler, parklar ve turistlik yerler olmak üzere İstanbul’da gidilecek en güzel noktalar bulunuyor.Listede yer alan mekanlar ve semtler ile ilgili olarak detay sayfalarına tıklayabilir ve detaylı bilgilerine ulaşabilirsiniz."));
        postList.add(new PostModel(R.drawable.img_izmir, "İzmir", "İzmir, dünyaca ünlü turizm merkezlerine ve dokusu bozulmamış köylere ev sahipliği yapıyor. İzmir'de yapılacak elbette çok fazla şey var. Gezilecek doğal yerlerden tutun da merkezde gezilebilecek ya da tarihi yerleri görüp yolculuk yapabileceğiniz pek çok nokta var. Sadece merkez değil ilçelerine, semtlere de vakit ayırmayı unutmayın. Urla'dan Karşıyaka'ya, Selçuk'tan Alaçatı, Seferihisar'a, Bornova'ya..."));
        postList.add(new PostModel(R.drawable.img_trabzon, "Trabzon", "Anadolu’nun en eski limanları arasında sayılan Trabzon gezilecek yerler bakımından doğal güzellikleri ve tarihi yapıları ziyaretçilerine bir arada sunuyor. M.Ö. 9. yüzyılda Sinop’tan gelen Yunan kolonicilerin kurduğu kentin sıcakkanlı insanı, maharetli elleriyle bu zenginlikleri sosyal hayata aktarmış. Üstelik kentin gezilecek yerlerin ve bu yerlerin çevresindeki sosyal olanakların sizlere verecekleri unutulmaz hazları yazın yaylada kışınsa şehir merkezindeki uygun fiyatlı otellerde konaklayarak yaşayabilirsiniz."));

        CustomPostAdapter postAdapter = new CustomPostAdapter(getLayoutInflater(), postList);
        lvPosts.setAdapter(postAdapter);

        lvPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bilgi");
                String selectedName = postList.get(position).getPostName();
                String selectedDescription = postList.get(position).getPostDescription();
                int selectedIcon = postList.get(position).getPostPicture();
                String message = selectedName + " " + selectedDescription;
                builder.setIcon(selectedIcon);
                builder.setMessage(message);
                builder.setNegativeButton("TAMAM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        return v;
    }
}
