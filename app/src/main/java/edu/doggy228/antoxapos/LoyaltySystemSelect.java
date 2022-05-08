package edu.doggy228.antoxapos;

import android.app.Activity;
import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;

public class LoyaltySystemSelect implements View.OnClickListener {
    public Activity activity;
    public Context c;
    public String id;
    public String name;
    public String vcAlias;
    public String vcName;
    public String vcRate;
    public String vcKoef;
    public int vcScale;

    public LoyaltySystemSelect(Activity activity, Context c, JsonNode jnLoyaltySystem){
        this.activity=activity;
        this.c = c;
        this.id = jnLoyaltySystem.path("id").asText();
        this.name = jnLoyaltySystem.path("name").asText();
        this.vcAlias = jnLoyaltySystem.path("vcAlias").asText();
        this.vcName = jnLoyaltySystem.path("vcName").asText();
        this.vcRate = jnLoyaltySystem.path("vcRate").asText();
        this.vcKoef = jnLoyaltySystem.path("vcKoef").asText();
        this.vcScale = jnLoyaltySystem.path("vcScale").asInt();
    }

    @Override
    public void onClick(View view) {
        AttrStorage.loyaltySystemSet(c,this.id);
        AttrStorage.payBillSet(c,"");
        LinearLayout layoutPay = new LinearLayout(c);
        layoutPay.setOrientation(layoutPay.VERTICAL);
        ImageView imageViewLogo = new ImageView(c);
        int resId = c.getResources().getIdentifier("ls_"+id, "drawable", "edu.doggy228.antoxapos");
        if (resId <= 0) resId = c.getResources().getIdentifier("ls_def", "drawable", "edu.doggy228.antoxapos");
        imageViewLogo.setImageResource(resId);
        layoutPay.addView(imageViewLogo);
        TextView textViewTitleAmount = new TextView(c);
        textViewTitleAmount.setText("Сума чеку:");
        layoutPay.addView(textViewTitleAmount);
        EditText editTextAmount = new EditText(c);
        editTextAmount.setText("");
        layoutPay.addView(editTextAmount);
        Button btnPay = new Button(c);
        btnPay.setText("Сплатити");
        layoutPay.addView(btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BigDecimal bigDecimal = new BigDecimal(editTextAmount.getText().toString());
                    bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_DOWN);
                    if(bigDecimal.signum() <= 0) throw new Exception("Сума від'ємна, або нульова");
                    AttrStorage.payBillSet(c,bigDecimal.toPlainString());
                } catch (Exception e){
                    editTextAmount.setText("Хибний формат суми");
                }
            }
        });
        activity.setContentView(layoutPay);
    }
}
