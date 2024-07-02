package com.example.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class modificar extends AppCompatActivity {

    Toolbar toolbar;
    EditText nom,apep,apem,edad,telefono,domicilio,ale,sangre,tutor;

    int itemCount, posi;
    SharedPreferences archivo;

    Spinner sexo;
    String valsexo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nom=findViewById(R.id.edit_nombre);
        apep=findViewById(R.id.edit_apep);
        apem=findViewById(R.id.edit_apem);
        edad=findViewById(R.id.edit_edad);
        telefono=findViewById(R.id.edit_telefono);
        domicilio=findViewById(R.id.edit_domicilio);
        ale=findViewById(R.id.edit_alergias);
        sangre=findViewById(R.id.edit_tiposangre);
        tutor=findViewById(R.id.edit_tutor);
        sexo=findViewById(R.id.edit_sexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sexo.setAdapter(adapter);
        sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valsexo=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        archivo=this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        posi=getIntent().getIntExtra("ps",-1);
        posi++;

        String url ="http://192.168.11.22/movil/muestra.php?id=";
        url=url+posi;
        Log.d("ps",String.valueOf(posi));
        Log.d("ps",url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            nom.setText(response.getString("nom"));
                            apep.setText(response.getString("apep"));
                            apem.setText(response.getString("apem"));
                            edad.setText(response.getString("edad"));
                            telefono.setText(response.getString("tel"));
                            domicilio.setText(response.getString("dom"));
                            ale.setText(response.getString("ale"));
                            sangre.setText(response.getString("tipo"));
                            tutor.setText(response.getString("tutor"));
                            String sexoValue = response.getString("sexo");
                            int sexoPosition = adapter.getPosition(sexoValue);
                            sexo.setSelection(sexoPosition);
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
        getRowCount();
    }
    public void modify(View view) {
        String nombre, apellidop, apellidom, ed, tel, dom, alergias, san, met, fec;
        nombre=nom.getText().toString();
        apellidop=apep.getText().toString();
        apellidom=apem.getText().toString();
        ed=edad.getText().toString();
        tel=telefono.getText().toString();
        dom=domicilio.getText().toString();
        alergias=ale.getText().toString();
        san=sangre.getText().toString();
        met=tutor.getText().toString();
        fec= valsexo;

        if(nombre.isEmpty()||apellidop.isEmpty()||apellidop.isEmpty()||apellidom.isEmpty()
                ||ed.isEmpty()||tel.isEmpty()||dom.isEmpty()||alergias.isEmpty()||san.isEmpty()
                ||met.isEmpty()||fec.isEmpty()){
            Toast.makeText(this, "Informacion faltante",
                    Toast.LENGTH_SHORT).show();
        }else{

            String url = "http://192.168.11.22/movil/editar.php?nom=";
            url = url + nom.getText().toString();
            url = url + "&apep=";
            url = url + apep.getText().toString();
            url = url + "&apem=";
            url = url + apem.getText().toString();
            url = url + "&edad=";
            url = url + edad.getText().toString();
            url = url + "&tel=";
            url = url + telefono.getText().toString();
            url = url + "&dom=";
            url = url + domicilio.getText().toString();
            url = url + "&ale=";
            url = url + ale.getText().toString();
            url = url + "&tipo=";
            url = url + sangre.getText().toString();
            url = url + "&tutor=";
            url = url + tutor.getText().toString();
            url = url + "&sexo=";
            url = url + valsexo;
            url = url + "&id=";
            url = url + posi;
            Log.d("url",url);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt("id") != -1) {
                                    Toast.makeText(modificar.this, "Se actualizaron los datos", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("yo", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    }
    public void prev(View view) {
        if(posi==1){
            Intent act = new Intent(this, modificar.class);
            act.putExtra("ps", itemCount-1);
            startActivity(act);
        }
        else {
            Intent act = new Intent(this, modificar.class);
            act.putExtra("ps", posi - 2);
            startActivity(act);
        }
    }

    public void next(View view) {
        if(posi==itemCount){
            Intent act = new Intent(this, modificar.class);
            act.putExtra("ps", 0);
            startActivity(act);
        }
        else {
            Intent act = new Intent(this, modificar.class);
            act.putExtra("ps", posi);
            startActivity(act);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflat sirve para agregar los elementos del menu que se vean en el toolbar que se vean en el main activity
        getMenuInflater().inflate(R.menu.menu,menu);// en res, subcarpeta menu, que se llama menu y contiene menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                startActivity(home);
                Toast.makeText(this, "Principal", Toast.LENGTH_SHORT).show();
                break;

            case R.id.recycler:
                Intent recycler = new Intent(this, recycler.class);
                startActivity(recycler);
                break;

            case R.id.mod:

                Toast.makeText(this, "Ya estas ahi", Toast.LENGTH_SHORT).show();
                break;

            case R.id.trash:
                Intent eliminar = new Intent(this, borrar.class);
                startActivity(eliminar);
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
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


    private void getRowCount() {
        // Crea una instancia de la cola de solicitudes de Volley
        String url ="http://192.168.11.22/movil/cuenta.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        // Crea la solicitud GET hacia el archivo PHP
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtén el número de filas del objeto JSON
                            int rowCount = response.getInt("cue");

                            // Asigna el número de filas a la variable itemCount
                            itemCount = rowCount;

                            // Notifica al adaptador que los datos han cambiado
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja los errores de la solicitud
                        Log.e(MainActivity.class.getSimpleName(), "Error en la solicitud: " + error.getMessage());
                    }
                });

        // Agrega la solicitud a la cola
        queue.add(request);
    }


}