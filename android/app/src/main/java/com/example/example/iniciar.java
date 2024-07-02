package com.example.example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class iniciar extends AppCompatActivity {
    EditText usuario, password;
    Button ingresar;
    SharedPreferences archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);

        // Referencias a los elementos de la interfaz de usuario
        usuario = findViewById(R.id.iniciar_usuario);
        password = findViewById(R.id.iniciar_password);
        ingresar = findViewById(R.id.btn_iniciar);

        // Obtener referencia a SharedPreferences para almacenar datos de sesión
            archivo = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);//clase actual esp modo de acc prvt

        // Verificar si ya hay un usuario en sesión
        if (archivo.contains("id_usuario")) {//ver si arch con cl
            Intent ini = new Intent(this, MainActivity.class);
            startActivity(ini);//camb de ct
            finish();
        }

        // Configurar el evento onClick para el botón de ingresar
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_login();
            }
        });
    }

    private void onclick_login() {
        // Construir la URL para la solicitud al servidor
        String url ="http://192.168.11.22/movil/ingreso.php?usr=";//2 rt del recso enl serv que hr l slctd
        url += usuario.getText().toString();
        url += "&pass=";
        url += password.getText().toString();
            // Json: se utiliza para enviar una solicitud HTTP al servidor y recibir una respuesta en formato JSON.
        // Crear una solicitud JsonObjectRequest para obtener una respuesta JSON del servidor clase GEt solicita post recibe
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            //null porque no se envia nada y new.. nueva instancia donde se guarda lo recibido que se encio del servidor tipo json
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Verificar si la respuesta contiene un "usr" válido
                            if (response.getInt("usr") != -1) { // comp para vr si tiene usr val y rspons es pra extrr usr del json de resp
                                // Si el "usr" es válido, redirigir a la actividad MainActivity
                                Intent i = new Intent(iniciar.this, MainActivity.class);
                                SharedPreferences.Editor editor = archivo.edit();//crea editor
                                editor.putInt("id_usuario", response.getInt("usr"));//guarda int valor de del cmpo usr en arch bjo clave i_
                                editor.apply();
                                startActivity(i);
                                finish();
                            } else {
                                // Si el "usr" no es válido, limpiar los campos de usuario y contraseña
                                usuario.setText("");
                                password.setText("");
                            }
                        } catch (JSONException e) {
                            //manejar cualquier error que pueda ocurrir al procesar el objeto JSON. bloque del codigo con try-catch pl resv que mnja exce
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Mostrar el mensaje de error en el Log
                Log.d("yo", error.getMessage());
            }
        }
        );

        // Crear una cola de solicitudes l  sv y agregar la solicitud al servidor
        RequestQueue lanzarPeticion = Volley.newRequestQueue(this);
        lanzarPeticion.add(req);
    }//Response.Listener, se realiza el procesamiento de la respuesta recibida del servidor,
}
