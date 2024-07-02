package com.example.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.Objects;

import adaptador.eliminar;
import global.info;

public class borrar extends AppCompatActivity {

    SharedPreferences archivo;
    TextView nombre, apellido;
    CheckBox check;
    Toolbar toolbar;
    RecyclerView rv;
    Button btn_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);



        rv=findViewById(R.id.borrar_reycler);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        eliminar av= new eliminar();
        av.context=this;

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);

        rv.setAdapter(av);

        nombre=findViewById(R.id.eliminar_nombre);
        apellido=findViewById(R.id.eliminar_apellido);
        check=findViewById(R.id.eliminar_check);
        btn_eliminar=findViewById(R.id.btn_borrar);

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                Toast.makeText(borrar.this, "Usuarios eliminados", Toast.LENGTH_SHORT).show();

            }
        });

        archivo=this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
    }


    private void delete() {
        for (int i = 0; i< info.lista_eliminar.size(); i++)
        {
            String url = "http://192.168.11.22/movil/eliminar.php?id=";
            url=url+info.lista_eliminar.get(i);
            Log.d("ps",url);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getInt("eliminar")!=-1){
                                    Toast.makeText(borrar.this, "Datos eliminados", Toast.LENGTH_SHORT).show();
                                }
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

        }
        info.lista_eliminar.clear();
        Toast.makeText(this, "Datos eliminados", Toast.LENGTH_SHORT).show();
        Intent aux=new Intent(this,borrar.class);
        rv.getAdapter().notifyDataSetChanged();
        rv.getAdapter().notifyDataSetChanged();
        startActivity(aux);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate sirve para agregar los elementos del menu que se vean en el toolbar que se vean en el main activity
        getMenuInflater().inflate(R.menu.menu,menu);// en res, subcarpeta menu, que se llama menu y contiene menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                startActivity(home);
                Toast.makeText(this, "Cambiado a principal", Toast.LENGTH_SHORT).show();
                break;

            case R.id.recycler:
                Intent h = new Intent(this, recycler.class);
                startActivity(h);
                break;

            case R.id.mod:

                    Intent modificar = new Intent(this, com.example.example.modificar.class);
                     modificar.putExtra("ps", 0);
                    startActivity(modificar);
                    Toast.makeText(this, "Cambiado a modificar", Toast.LENGTH_SHORT).show();

                break;

            case R.id.trash:
                Toast.makeText(this, "Ya estas en eliminar", Toast.LENGTH_SHORT).show();
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