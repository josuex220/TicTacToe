package br.com.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class ActivityMultiplayer extends AppCompatActivity {
    private ImageButton[] arrayButton = new ImageButton[10];
    private String vez = "x";
    private int jogadas = 0;
    private int pla3 = 0;
    private int pla1 = 0;
    private int pla2 = 0;
    private String[] matriz = new String[10];
    private String jogador1, jogador2;
    Context context;
    private RewardedAd mRewardedAd;
    private String keyRewarded = "ca-app-pub-7512531881250307/5036044920";

    private final String TAG = "ActivityMultiplayer";
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        jogador1 = "Player 1";
        jogador2 = "Player 2";
        TextView jg1 = (TextView) findViewById(R.id.jogador1);
        TextView jg2 = (TextView) findViewById(R.id.jogador2);
        jg1.setText(String.valueOf(jogador1));
        jg2.setText(String.valueOf(jogador2));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        jogador1Add();
        inicializaButtons();
        onClickButtons();

    }

    private void anuncio(){
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, keyRewarded,
                adRequest, new RewardedAdLoadCallback(){
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        if (mRewardedAd != null) {
                            Activity activityContext = ActivityMultiplayer.this;
                            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    // Handle the reward.

                                    int rewardAmount = rewardItem.getAmount();
                                    String rewardType = rewardItem.getType();
                                }
                            });
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't ready yet.");
                        }
                    }
                });
    }
    private  void placar(){
        TextView pl1 = (TextView) findViewById(R.id.placar1);
        TextView pl2 = (TextView) findViewById(R.id.placar2);
        TextView pl3 = (TextView) findViewById(R.id.placar3);
        pl1.setText(String.valueOf(pla1));
        pl2.setText(String.valueOf(pla2));
        pl3.setText(String.valueOf(pla3));
    }
    private void jogador1Add(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View dialogpage = factory.inflate(R.layout.jogador, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(dialogpage);
        dialogpage.findViewById(R.id.buttonjogador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText jogadorid = (EditText) dialog.findViewById(R.id.jogadorid);
                TextView jg1 = (TextView) findViewById(R.id.jogador1);
                if(jogadorid.getText().length() > 0){
                    jg1.setText(String.valueOf(jogadorid.getText()));
                    jogador1 = String.valueOf(jogadorid.getText());

                    dialog.dismiss();
                    jogador2Add();
                }
            }
        });
        dialog.setTitle("Jogador 1");
        dialog.show();



    }
    private void jogador2Add(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View dialogpage = factory.inflate(R.layout.jogador, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(dialogpage);
        dialogpage.findViewById(R.id.buttonjogador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText jogadorid = (EditText) dialog.findViewById(R.id.jogadorid);
                TextView jg2 = (TextView) findViewById(R.id.jogador2);
                if(jogadorid.getText().length() > 0){
                    jg2.setText(String.valueOf(jogadorid.getText()));
                    jogador2 = String.valueOf(jogadorid.getText());

                    dialog.dismiss();
                }
            }
        });
        dialog.setTitle("Jogador 2");
        dialog.show();



    }
    private void inicializaButtons(){
        arrayButton[1] = (ImageButton) findViewById(R.id.btn1);
        arrayButton[2] = (ImageButton) findViewById(R.id.btn2);
        arrayButton[3] = (ImageButton) findViewById(R.id.btn3);
        arrayButton[4] = (ImageButton) findViewById(R.id.btn4);
        arrayButton[5] = (ImageButton) findViewById(R.id.btn5);
        arrayButton[6] = (ImageButton) findViewById(R.id.btn6);
        arrayButton[7] = (ImageButton) findViewById(R.id.btn7);
        arrayButton[8] = (ImageButton) findViewById(R.id.btn8);
        arrayButton[9] = (ImageButton) findViewById(R.id.btn9);
    }
    private void onClickButtons(){
        for (int x = 1; x < 10; x++){
            final int finalX = x;
            arrayButton[x].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jogada(finalX);
                }
            });
            matriz[x] = "";
        }
    }
    private void jogada(int x){
        if(matriz[x] == ""){
            matriz[x] = vez;
            jogadas++;
            if(vez == "x"){
                vez = "o";
                TextView textView = (TextView) findViewById(R.id.jogador1);
                TextView textView1 = (TextView) findViewById(R.id.jogador2);
                textView1.setTextColor(Color.parseColor("#e94141"));
                textView.setTextColor(Color.parseColor("#555555"));

            }else{
                vez = "x";
                TextView textView = (TextView) findViewById(R.id.jogador1);
                TextView textView1 = (TextView) findViewById(R.id.jogador2);
                textView.setTextColor(Color.parseColor("#e94141"));
                textView1.setTextColor(Color.parseColor("#555555"));
            }

        }
        exibirButtons();
        verifica();
    }
    private void exibirButtons(){


        for (int x = 1; x < 10; x++) {
            if (matriz[x] == "x"){
                arrayButton[x].setImageResource(R.drawable.ic_cross);
            }if (matriz[x] == "o"){
                arrayButton[x].setImageResource(R.drawable.ic_circle);
            }
            if(matriz[x].equals("")){
                arrayButton[x].setImageResource(android.R.color.transparent);
            }
        }
    }
    private void verifica(){
        if(matriz[1].equals(matriz[2]) && matriz[1].equals(matriz[3]) && matriz[1].toString() != ""){
            ganhador(matriz[1]);
            return;
        }

        if(matriz[4].equals(matriz[5]) && matriz[4].equals(matriz[6]) && matriz[4].toString() != ""){
            ganhador(matriz[4]);
            return;
        }

        if(matriz[7].equals(matriz[8]) && matriz[7].equals(matriz[9]) && matriz[7].toString() != ""){
            ganhador(matriz[7]);
            return;
        }

        if(matriz[1].equals(matriz[4]) && matriz[1].equals(matriz[7]) && matriz[1].toString() != ""){
            ganhador(matriz[1]);
            return;
        }

        if(matriz[2].equals(matriz[5]) && matriz[2].equals(matriz[8]) && matriz[2].toString() != ""){
            ganhador(matriz[2]);
            return;
        }

        if(matriz[3].equals(matriz[6]) && matriz[3].equals(matriz[9]) && matriz[3].toString() != ""){
            ganhador(matriz[3]);
            return;
        }

        if(matriz[1].equals(matriz[5]) && matriz[1].equals(matriz[9]) && matriz[1].toString() != ""){
            ganhador(matriz[1]);
            return;
        }

        if(matriz[3].equals(matriz[5]) && matriz[3].equals(matriz[7]) && matriz[3].toString() != ""){
            ganhador(matriz[3]);
            return;
        }

        if (jogadas == 9){
            ganhador("");
            return;
        }
    }
    private void ganhador(String ganhador){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMultiplayer.this);
        builder.setTitle("Resultado");

        anuncio();
        if(ganhador.equals("")){
            builder.setMessage("Empate");
            pla3++;
        }else{
            if (ganhador.equals("x")){
                builder.setMessage("Quem venceu foi "+jogador1);
                pla1++;

            }if (ganhador.equals("o")){
                builder.setMessage("Quem venceu foi "+jogador2);
                pla2++;
            }



        }
        builder.setPositiveButton("Jogar Novamente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                jogadas = 0;
                for (int x=1; x<10; x++){
                    matriz[x] = "";
                }
                exibirButtons();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        placar();
    }
    @Override
    public void onBackPressed() {
       Intent i = new Intent(getApplicationContext(), MainActivity.class);
       startActivity(i);
       finish();
    }
}