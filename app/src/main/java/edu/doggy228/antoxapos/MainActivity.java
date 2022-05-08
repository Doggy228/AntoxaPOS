package edu.doggy228.antoxapos;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ImageView imageViewLogo = new ImageView(this);
        imageViewLogo.setImageResource(R.drawable.logoantoxapos);
        linearLayout.addView(imageViewLogo);
        TextView textViewTitleServer = new TextView(this);
        textViewTitleServer.setText("Вкажіть сервер систем лояльності:");
        linearLayout.addView(textViewTitleServer);
        EditText editTextServerName = new EditText(this);
        editTextServerName.setText(AttrStorage.serverNameGet(this));
        linearLayout.addView(editTextServerName);
        Button btnStart = new Button(this);
        btnStart.setText("Запуск");
        linearLayout.addView(btnStart);
        TextView textViewError = new TextView(this);
        textViewError.setText("");
        linearLayout.addView(textViewError);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttrStorage.serverNameSet(MainActivity.this,editTextServerName.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://" + AttrStorage.serverNameGet(MainActivity.this) + ":8082/lsemu/api/v1/lsemu/")
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build();
                LoyaltySystemService service = retrofit.create(LoyaltySystemService.class);
                try {
                    Response<JsonNode> rsp = service.listLoyaltySystemAll().execute();
                    Log.i("test", "rsp.code=" + rsp.code());
                    if (rsp.code() != 200) throw new Exception("Помилка мережі");
                    textViewError.setText("");
                    LinearLayout layout = new LinearLayout(MainActivity.this);
                    layout.setOrientation(layout.VERTICAL);
                    Iterator<JsonNode> it = rsp.body().path("listLoyaltySystem").elements();
                    while (it.hasNext()) {
                        JsonNode loyaltySystem = it.next();
                        Button btn = new Button(MainActivity.this);
                        btn.setText(loyaltySystem.path("name").asText());
                        btn.setOnClickListener(new LoyaltySystemSelect(MainActivity.this,MainActivity.this,loyaltySystem));
                        layout.addView(btn);
                    }
                    setContentView(layout);
                } catch (Exception e) {
                    Log.e("test", "Помилка мережі", e);
                    textViewError.setText("Помилка мережі");
                }
            }
        });
        setContentView(linearLayout);
    }

    public void onClickMainStartButton(View view) {

    }
}