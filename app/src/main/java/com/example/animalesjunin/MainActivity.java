package com.example.animalesjunin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private EditText et1;
    private ImageButton btX;
    public int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().hide();
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txt_num_input);
        btX = (ImageButton) findViewById(R.id.imageButton);
        et1.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        //et1.setVisibility(View.INVISIBLE);

    }
    //metodo buscar
    public  void search (View view){
        String read = et1.getText().toString();
        int l = 0;
        if (read.equals("") && a == 1) {
            Toast.makeText(this, "Debe escribir un Código",Toast.LENGTH_SHORT).show();
            l = 1;
        }
        else
            l = 0;

        if (a == 0) {
            et1.setVisibility(View.VISIBLE);
            btX.setVisibility(View.VISIBLE);
            a = 1;
        }
        else if (read.length() > 10 && a == 1 && l == 0){
            Toast.makeText(this, "Código inválido",Toast.LENGTH_SHORT).show();
            l = 1;
        }
        else  if (a == 1 && l == 0){
            et1.setVisibility(View.GONE);
            btX.setVisibility(View.GONE);
            a = 0;
            Intent visor = new Intent(this, Visor.class);
            visor.putExtra("dato",et1.getText().toString());
            startActivity(visor);
            et1.setText("");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void close(View view) {
        et1.setVisibility(View.GONE);
        btX.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
        a = 0;
        et1.setText("");
    }
    public void qr(View view){
        et1.setVisibility(View.GONE);
        btX.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
        a = 0;
        et1.setText("");
        //--------------

        IntentIntegrator qr = new IntentIntegrator((this));
        qr.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        qr.setPrompt("Apunte al QR en el collar\n\n\n");
        qr.setCameraId(0);
        qr.setOrientationLocked(true);
        qr.setBeepEnabled(false);
        qr.setBarcodeImageEnabled(true);
        qr.initiateScan();
    }
    public void help (View view)  {
        Intent help = new Intent(this, Ayuda.class);
        startActivity(help);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
            } else {
                Intent visor = new Intent(this, Visor.class);
                visor.putExtra("dato",result.getContents());
                startActivity(visor);
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
