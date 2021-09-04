package com.example.animalesjunin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import java.io.IOException;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.squareup.picasso.Picasso;


public class Visor extends AppCompatActivity {

    private TextView tx1;
    private TextView tx2;
    private int indexx = 0;
    private int found = 0;
    private int code = 0;
    private int index = 0;
    private ImageView Imgurl;
    String [] values;
    int r;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor);
        ImageView img = (ImageView) findViewById(R.id.logocarga);
        img.setVisibility(View.VISIBLE);
        ScrollView scrol = (ScrollView) findViewById(R.id.scrol);
        scrol.setVisibility(View.INVISIBLE);
        Imgurl = findViewById(R.id.imgurl);
        tx1 = (TextView) findViewById(R.id.OAvrab);
        tx2 = (TextView) findViewById(R.id.OAdes);
        gssact();
    }

    public void gssact() {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory factory = JacksonFactory.getDefaultInstance();
        final Sheets sheetsService = new Sheets.Builder(transport, factory, null)
                .setApplicationName("My Awesome App")
                .build();
        final String spreadsheetId = Config.spreadsheet_id;

        new Thread() {
            @Override
            public void run() {
                try {
                    /*
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Visor.this, "Espere. Esto puede llevar unos segundos.", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    String range = "B:B";
                    ValueRange result = sheetsService.spreadsheets().values().get(spreadsheetId, range).setKey(Config.google_api_key).execute();
                    int numRows = result.getValues() != null ? result.getValues().size() : 0;
                    Object[] values = result.getValues().toArray();

                    int intervalo = (numRows) / 4;
                    String z;
                    code = Integer.parseInt(z = getIntent().getStringExtra("dato"));
                    int int1 = Integer.parseInt(z = String.valueOf(values[intervalo]).substring(1).replace("]", ""));
                    int int2 = Integer.parseInt(z = String.valueOf(values[intervalo * 2]).substring(1).replace("]", ""));
                    int int3 = Integer.parseInt(z = String.valueOf(values[intervalo * 3]).substring(1).replace("]", ""));
                    int search = 0;

                    if (code <= int1)
                        search = 1;
                    else if (code <= int2)
                        search = 2;
                    else if (code <= int3)
                        search = 3;
                    else if (code > int3)
                        search = 4;
                    else
                        search = 0;
                    int inter = search;
                    index = 0;
                    String r;
                    int h = 0;

                    int s1 = search, s2 = 0, s3 = 0, s4 = 0, s = 1;

                    while (h != 1) {
                        switch (search) {
                            case 0:
                                found = 0;
                                break;
                            case 1:
                                int iterator = 1;
                                Log.d("code intervalo", "= 1");
                                while (iterator != intervalo) {
                                    int act = Integer.parseInt(r = String.valueOf(values[iterator]).substring(1).replace("]", ""));
                                    if (act == code) {
                                        found = 1;
                                        index = iterator;
                                        h = 1;
                                        break;
                                    }
                                    iterator++;
                                }
                                break;
                            case 2:
                                int iterator2 = intervalo;
                                Log.d("code intervalo", "= 2");
                                while (iterator2 != (intervalo * 2)) {
                                    int act = Integer.parseInt(r = String.valueOf(values[iterator2]).substring(1).replace("]", ""));
                                    if (act == code) {
                                        found = 1;
                                        index = iterator2;
                                        h = 1;
                                        break;
                                    }
                                    iterator2++;
                                }
                                break;
                            case 3:
                                int iterator3 = (intervalo * 2);
                                Log.d("code intervalo", "= 3");
                                while (iterator3 != (intervalo * 3)) {
                                    int act = Integer.parseInt(r = String.valueOf(values[iterator3]).substring(1).replace("]", ""));
                                    if (act == code) {
                                        found = 1;
                                        index = iterator3;
                                        h = 1;
                                        break;
                                    }
                                    iterator3++;
                                }
                                break;
                            case 4:
                                int iterator4 = (intervalo * 3);
                                Log.d("code intervalo", "= 4");
                                while (iterator4 != (numRows)) { //o numrows -1
                                    int act = Integer.parseInt(r = String.valueOf(values[iterator4]).substring(1).replace("]", ""));
                                    if (act == code) {
                                        found = 1;
                                        index = iterator4;
                                        h = 1;
                                        break;
                                    }
                                    iterator4++;
                                }
                                break;
                        }
                        if (found == 0) {
                            if (s1 != 1 && s2 != 1 && s3 != 1 && s4 != 1) {
                                search = 1;
                                if (s == 1) {
                                    s2 = 1;
                                }
                                if (s == 2) {
                                    s3 = 1;
                                }
                                if (s == 3) {
                                    s4 = 1;
                                }
                            } else if (s1 != 2 && s2 != 2 && s3 != 2 && s4 != 2) {
                                search = 2;
                                if (s == 1) {
                                    s2 = 2;
                                }
                                if (s == 2) {
                                    s3 = 2;
                                }
                                if (s == 3) {
                                    s4 = 2;
                                }
                            } else if (s1 != 3 && s2 != 3 && s3 != 3 && s4 != 3) {
                                search = 3;
                                if (s == 1) {
                                    s2 = 3;
                                }
                                if (s == 2) {
                                    s3 = 3;
                                }
                                if (s == 3) {
                                    s4 = 3;
                                }
                            } else if (s1 != 4 && s2 != 4 && s3 != 4 && s4 != 4) {
                                search = 4;
                                if (s == 1) {
                                    s2 = 4;
                                }
                                if (s == 2) {
                                    s3 = 4;
                                }
                                if (s == 3) {
                                    s4 = 4;
                                }
                            } else {
                                h = 1;
                            }
                            s++;
                        }
                    }
                    if (found == 1) {
                        indexx = index + 1;
                        Log.d("code", code + " - index = " + indexx);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Visor.this, "ERROR. CODIGO NO ENCONTRADO", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                } catch (IOException e) {
                    Log.e("Sheets failed", e.getLocalizedMessage());
                    finish();
                }
            }
        }.start();
    }

    public void show() {
        //Toast.makeText(Visor.this, "Cargando datos", Toast.LENGTH_SHORT).show();
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory factory = JacksonFactory.getDefaultInstance();
        final Sheets sheetsService = new Sheets.Builder(transport, factory, null)
                .setApplicationName("My Awesome App")
                .build();
        final String spreadsheetId = Config.spreadsheet_id;

        new Thread() {
            @Override
            public void run() {
                try {
                    String range = "B" + indexx + ":" + indexx;
                    ValueRange result = sheetsService.spreadsheets().values().get(spreadsheetId, range).setKey(Config.google_api_key).execute();
                    Object[] val = result.getValues().toArray();
                    String val2 = String.valueOf(val[0]).substring(1).replace("]", "");
                    values = val2.split(",");
                    Log.d("code:", values[0]);
                    r = 1;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            fin();
                        }
                    });
                } catch (IOException e) {
                    Toast.makeText(Visor.this, "ERROR\n No se pudo conectar", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }.start();

    }

    public void fin()
    {
        ImageButton z = (ImageButton) findViewById(R.id.cros);
        z.setVisibility(View.VISIBLE);
        Log.d("fin", "inicio");
        TextView a = (TextView) findViewById(R.id.Ocod);
        a.setText(values[0]);
        TextView b = (TextView) findViewById(R.id.Ovet);
        b.setText(values[1]);
        TextView c = (TextView) findViewById(R.id.Onom);
        c.setText(values[2]);
        TextView d = (TextView) findViewById(R.id.Odni);
        d.setText(values[3]);
        TextView e = (TextView) findViewById(R.id.Otel);
        e.setText(values[4]);
        TextView f = (TextView) findViewById(R.id.Opeso);
        f.setText(values[11]);
        TextView g = (TextView) findViewById(R.id.OLoc);
        g.setText(values[5]);
        TextView h = (TextView) findViewById(R.id.Ocal);
        h.setText(values[6]);
        TextView i = (TextView) findViewById(R.id.Onro);
        i.setText(values[7]);
        TextView j = (TextView) findViewById(R.id.Opso);
        j.setText(values[8]);
        TextView k = (TextView) findViewById(R.id.OAsec);
        k.setText(values[10]);
        TextView l = (TextView) findViewById(R.id.OAnom);
        l.setText(values[9]);
        TextView m = (TextView) findViewById(R.id.OAesp);
        m.setText(values[12]);
        TextView n = (TextView) findViewById(R.id.OApel);
        n.setText(values[13]);
        TextView o = (TextView) findViewById(R.id.OAtall);
        o.setText(values[14]);

        tx1.setText(values[15]);

        tx2.setText(values[16]);
        TextView r = (TextView) findViewById(R.id.OAcas);
        r.setText(values[17]);
        TextView s = (TextView) findViewById(R.id.OAraz);
        s.setText(values[18]);
        TextView t = (TextView) findViewById(R.id.OAnac);
        t.setText(values[19]);
        TextView u = (TextView) findViewById(R.id.OAedd);
        u.setText(values[21]);

        String[] id = new String[2];
        id[1] = "1";
        Log.d("before result", ": " + id[1]);
        if(values[20].length() < 11)
            values[20] = "id=0";
        Log.d("result", ": " + values[20].length());
        id = values[20].split("id=");
        Log.d("result", ": " + id[1]);

        int largo = values.length;
        Log.d("largo", ":" + largo);
        if( largo >= 23)
        {
            if(values[22].equals(" 1"))
                tx1.setTextColor(Color.parseColor("#FF0000"));
        }
        if( largo == 24) {
            if (values[23].equals(" 1")) {
                Log.d("color", "rojooooooo");
                tx2.setTextColor(Color.parseColor("#FF0000"));

            }
            else
                Log.d("color","Noooorojo" + values[23].length());
        }
        Picasso.get().load("https://drive.google.com/uc?export=view&id="+id[1]).error(R.drawable.ic_launcher_foreground).into(Imgurl);

        ImageView img = (ImageView) findViewById(R.id.logocarga);
        img.setVisibility(View.GONE);
        ScrollView scrl = (ScrollView) findViewById(R.id.scrol);
        scrl.setVisibility(View.VISIBLE);
    }
    public void cross(View view){
        finish();
    }
}
