package com.example.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import global.info;


public class card_view extends AppCompatActivity {

    TextView nombre, apep, apem, edad, telefono, domicilio, ale, sangre, tutor, sexo;
    Toolbar toolbar;
    Button llamar;

    SharedPreferences archivo;

    int posi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = findViewById(R.id.cv_nombre);
        apep = findViewById(R.id.cv_apellidop);
        apem = findViewById(R.id.cv_apellidom);
        edad = findViewById(R.id.cv_edad);
        telefono = findViewById(R.id.cv_tel);
        domicilio = findViewById(R.id.cv_dom);
        ale = findViewById(R.id.cv_ale);
        sangre = findViewById(R.id.cv_tsangre);
        tutor = findViewById(R.id.cv_tutor);
        sexo = findViewById(R.id.cv_sexo);
        archivo=this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        llamar = findViewById(R.id.llamar);
        posi=getIntent().getIntExtra("pos",-1);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String url = "http://192.168.11.22/movil/muestra.php?id=";
        url=url+posi;
        Log.d("ps",String.valueOf(posi));
        Log.d("ps",url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            nombre.setText(response.getString("nom"));
                            apep.setText(response.getString("apep"));
                            apem.setText(response.getString("apem"));
                            edad.setText(response.getString("edad"));
                            telefono.setText(response.getString("tel"));
                            domicilio.setText(response.getString("dom"));
                            ale.setText(response.getString("ale"));
                            sangre.setText(response.getString("tipo"));
                            tutor.setText(response.getString("tutor"));
                            sexo.setText(response.getString("sexo"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("er", error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLlamar();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void onClickLlamar() {
        Intent llamada = new Intent(Intent.ACTION_CALL);
        llamada.setData(Uri.parse("tel:"+telefono.getText().toString()));
        if(ActivityCompat.checkSelfPermission
                (this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CALL_PHONE}, 10);
            return;
        }
        startActivity(llamada);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                startActivity(home);
                Toast.makeText(this, "Cambiado a principal", Toast.LENGTH_SHORT).show();
                break;

            case R.id.recycler:
                Intent recycler = new Intent(this, recycler.class);
                startActivity(recycler);
                break;

            case R.id.mod:
                Intent ed = new Intent(this, recycler.class);
                ed.putExtra("ps", 0);
                Toast.makeText(this, "Modificar", Toast.LENGTH_SHORT).show();
                startActivity(ed);
                break;

            case R.id.trash:
                Intent eliminar = new Intent(this, borrar.class);
                startActivity(eliminar);
                Toast.makeText(this, "Cambiado a eliminar", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cerrar:
                if(archivo.contains("id_usuario")){
                    SharedPreferences.Editor editor = archivo.edit();
                    editor.remove("id_usuario");
                    editor.apply();
                    Intent fin = new Intent(this, iniciar.class);
                    startActivity(fin);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


