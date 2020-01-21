package com.example.gogreen;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnciclopediaActivity extends AppCompatActivity {

    public static HashMap<Integer, Pair<Boolean,String>> cards = new HashMap<>();
    private DatabaseReference mFirebaseDatabaseReference;
    private ColorMatrix matrix;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enciclopedia);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        matrix = new ColorMatrix();
        matrix.setSaturation(0);
        if(cards.isEmpty()){
            //Reciclar
            cards.put(R.id.fact1, new Pair<>(false, "Cartão/Papel no azulão, Plástico/Metal no amarelão e o vidro no verdão! :)"));
            cards.put(R.id.fact2, new Pair<>(false, "Sabias que lâmpadas não podem ser colocadas no vidrão?"));
            cards.put(R.id.fact3, new Pair<>(false, "Não coloques medicamentos fora do prazo no lixo, vai entregar a farmácia!"));
            cards.put(R.id.fact4, new Pair<>(false, "Reciclar plástico consome metade da energia que é necessário para o queimar!"));
            cards.put(R.id.fact5, new Pair<>(false, "Sabias que existe um ecoponto para reciclar pilhas? Procura pelo vermelhão!"));
            cards.put(R.id.fact6, new Pair<>(false, "Reciclar uma lata consegue poupar energia suficiente " +
                                                    "para teres a tua televisão ligada durante 3 horas!"));
            //Reduzir
            cards.put(R.id.fact7, new Pair<>(false, "Sabias que uma beata demora 10 a 12 anos a decompor-se? Não as atires para o chão! "));
            cards.put(R.id.fact8, new Pair<>(false, "Não te esqueças de levar o teu saco reutilizável para as compras!"));
            cards.put(R.id.fact9, new Pair<>(false, "Usa transportes públicos em vez do teu carro! O ambiente agradece!"));
            cards.put(R.id.fact10, new Pair<>(false, "Usa lâmpadas amigas do ambiente e verás uma redução na fatura também!"));
            cards.put(R.id.fact11, new Pair<>(false, "Não coloques azeite ou óleo no ralo! Usa um oleão"));
            cards.put(R.id.fact12, new Pair<>(false, "Dezenas de milhares de mamíferos marinhos morrem por ano ao comer ou " +
                    "se emaranhar em detritos de plástico."));
            //Reutilizar
            cards.put(R.id.fact13, new Pair<>(false, "T-shirts velhas dão para ótimos panos de limpeza, sabias?"));
            cards.put(R.id.fact14, new Pair<>(false, "Lixo duns, tesouro doutros. Doa a roupa/objetos que já não usas!"));
            cards.put(R.id.fact15, new Pair<>(false, "Utiliza pilhas recarregáveis!"));
            cards.put(R.id.fact16, new Pair<>(false, "Compra utensílios usados quando possível."));
            cards.put(R.id.fact17, new Pair<>(false, "Doa livros escolares! O que hoje não sabemos, amanhã saberemos! :)"));
            cards.put(R.id.fact18, new Pair<>(false, "Garrafas de plástico dão otimos vasos para plantas!"));
        }
        ColorMatrix m = new ColorMatrix();
        m.setSaturation(1);
        for(Map.Entry<Integer, Pair<Boolean, String>> entry : cards.entrySet()) {
            Integer key = entry.getKey();
            Pair p  = entry.getValue();

            if(LoginActivity.getUserLogged().getCards() != null && LoginActivity.getUserLogged().getCards().contains(key) ){
                Pair p1 = new Pair(true,p.second);
                cards.put(key, p1);
                if(findViewById(key).getBackground().getColorFilter() != null){

                    ImageView iv = findViewById(key);
                    iv.getBackground().setColorFilter(new ColorMatrixColorFilter(m));
                    iv.setColorFilter(new ColorMatrixColorFilter(m));
                }
            }
            else{
                if(LoginActivity.getUserLogged().getCards() != null){
                    ImageView iv = findViewById(key);
                    iv.getBackground().setColorFilter(new ColorMatrixColorFilter(matrix));
                    iv.setColorFilter(new ColorMatrixColorFilter(matrix));
                }
            }

        }

        List<Integer> cardsGive = LoginActivity.getUserLogged().getCardsToGive();
        byte[] b = {1,2,3,4,5,6,7};
        SecureRandom sr = new SecureRandom(b);
        for(int i = 0 ; i < cardsGive.size(); i++){

            switch (cardsGive.get(i)){
                case 1:

                    int j = sr.nextInt(9);
                    List<Integer> list = Arrays.asList(R.id.fact1,R.id.fact2,R.id.fact3,R.id.fact7,R.id.fact8,R.id.fact9
                    ,R.id.fact13,R.id.fact14,R.id.fact15);
                    if(!LoginActivity.getUserLogged().getCards().contains(list.get(j)))
                    LoginActivity.getUserLogged().addCard(list.get(j));
                    cardsGive.remove(i);
                    break;
                case 2:

                    int z = sr.nextInt(6);
                    List<Integer> list1 = Arrays.asList(R.id.fact4,R.id.fact5,R.id.fact10,R.id.fact11
                            ,R.id.fact16,R.id.fact17);
                    if(!LoginActivity.getUserLogged().getCards().contains(list1.get(z)))
                    LoginActivity.getUserLogged().addCard(list1.get(z));
                    cardsGive.remove(i);
                    break;
                case 3:
                    int x = sr.nextInt(3);
                    List<Integer> list2 = Arrays.asList(R.id.fact6,R.id.fact12
                            ,R.id.fact18);
                    if(!LoginActivity.getUserLogged().getCards().contains(list2.get(x)))
                    LoginActivity.getUserLogged().addCard(list2.get(x));
                    cardsGive.remove(i);
                    break;
            }
            mFirebaseDatabaseReference.child("USERS").child(LoginActivity.getUserLogged().getId())
                    .child("CARDS").setValue(LoginActivity.getUserLogged().getCards());
            mFirebaseDatabaseReference.child("USERS").child(LoginActivity.getUserLogged().getId())
                    .child("CARDSTOGIVE").setValue(LoginActivity.getUserLogged().getCardsToGive());


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        ColorMatrix m = new ColorMatrix();
        m.setSaturation(1);
        for(Map.Entry<Integer, Pair<Boolean, String>> entry : cards.entrySet()) {
            Integer key = entry.getKey();
            Pair p  = entry.getValue();

            if(LoginActivity.getUserLogged().getCards() != null && LoginActivity.getUserLogged().getCards().contains(key) ){
                Pair p1 = new Pair(true,p.second);
                cards.put(key, p1);
                if(findViewById(key).getBackground().getColorFilter() != null){

                    ImageView iv = findViewById(key);
                    iv.getBackground().setColorFilter(new ColorMatrixColorFilter(m));
                    iv.setColorFilter(new ColorMatrixColorFilter(m));
                }
            }
            else{
                if(LoginActivity.getUserLogged().getCards() != null){
                    ImageView iv = findViewById(key);
                    iv.getBackground().setColorFilter(new ColorMatrixColorFilter(matrix));
                    iv.setColorFilter(new ColorMatrixColorFilter(matrix));
                }
            }

        }
    }

    public void onButtonShowPopupWindowClick(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_fact_card, null);

        // create the popup window
        int width = 900;
        int height = 1100;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView t = popupView.findViewById(R.id.popup_text);

        if(cards.get(view.getId()).first) {
            t.setText(cards.get(view.getId()).second);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken


        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
