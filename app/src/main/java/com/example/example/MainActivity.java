package com.example.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import POJO.pacientes;
import global.info;

public class MainActivity extends AppCompatActivity
{
    EditText nom,apep,apem,edad,telefono,domicilio,ale,sangre,tutor;

    Spinner sexo;
    Button agregar,borrar;
    Toolbar toolbar;
    String valsexo;
    SharedPreferences archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregar=findViewById(R.id.agregar);
        borrar=findViewById(R.id.borrar);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nom=findViewById(R.id.PersonaNombre);
        apep=findViewById(R.id.PersonaApellidoP);
        apem=findViewById(R.id.PersonaApellidoM);
        edad=findViewById(R.id.PersonaEdad);
        telefono=findViewById(R.id.PersonaTel);
        domicilio=findViewById(R.id.PersonaDom);
        ale=findViewById(R.id.PersonaAlergias);
        sangre=findViewById(R.id.PersonaTSangre);
        tutor=findViewById(R.id.Personatutor);
        sexo=findViewById(R.id.Personasexo);
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
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_borrarr();
            }
        });

        archivo=this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch(id) {
            case R.id.home:
                Toast.makeText(this, "Ya estas en home", Toast.LENGTH_SHORT).show();

                break;

            case R.id.recycler:

                Intent recycler = new Intent(this, recycler.class);
                startActivity(recycler);
                break;

            case R.id.mod:
                Intent ed=new Intent(this,modificar.class);
                ed.putExtra("ps", 0);
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
    public void btn_borrarr() {

        nom.setText("");
        apep.setText("");
        apem.setText("");
        edad.setText("");
        telefono.setText("");
        domicilio.setText("");
        ale.setText("");
        sangre.setText("");
        tutor.setText("");

        Toast.makeText(this, "Informacion borrada", Toast.LENGTH_SHORT).show();

    }
    public void btn_agregar(View view) {
        String nombre, apellidop, apellidom, ed, tel, dom, alergias, san, tut, sex;
        nombre=nom.getText().toString();
        apellidop=apep.getText().toString();
        apellidom=apem.getText().toString();
        ed=edad.getText().toString();
        tel=edad.getText().toString();
        dom=domicilio.getText().toString();
        alergias=ale.getText().toString();
        san=sangre.getText().toString();
        tut=tutor.getText().toString();
        sex=valsexo;

        if(nombre.isEmpty()||apellidop.isEmpty()||apellidop.isEmpty()||apellidom.isEmpty()
                ||ed.isEmpty()||tel.isEmpty()||dom.isEmpty()||alergias.isEmpty()||san.isEmpty()
                ||tut.isEmpty()||sex.isEmpty()){
            Toast.makeText(this, "Falta informacion", Toast.LENGTH_SHORT).show();
        }
        else {
            String url = "http://192.168.11.22/movil/agregar.php?nom=";
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


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt("id") != -1) {
                                    Toast.makeText(MainActivity.this, "Se guardó la información", Toast.LENGTH_SHORT).show();
                                    nom.setText("");
                                    apep.setText("");
                                    apem.setText("");
                                    edad.setText("");
                                    telefono.setText("");
                                    domicilio.setText("");
                                    ale.setText("");
                                    sangre.setText("");
                                    tutor.setText("");
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



}