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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import adaptador.adaptador;
import global.info;

public class recycler extends AppCompatActivity
{
    Toolbar toolbar;
    RecyclerView rv;

    SharedPreferences archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        rv=findViewById(R.id.rv_recycler);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adaptador av= new adaptador();
        av.context=this;

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);

        rv.setAdapter(av);

        archivo=this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
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
                Toast.makeText(this, "Principal", Toast.LENGTH_SHORT).show();
                break;

            case R.id.recycler:
                Toast.makeText(this, "Ya estas ahi", Toast.LENGTH_SHORT).show();
                break;

            case R.id.mod:

                    Intent ed = new Intent(this, modificar.class);
                    ed.putExtra("ps", 0);
                    startActivity(ed);
                    Toast.makeText(this, "Modificar", Toast.LENGTH_SHORT).show();
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

}