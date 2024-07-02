package adaptador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.example.R;
import com.example.example.card_view;

import org.json.JSONException;
import org.json.JSONObject;

import global.info;

public class adaptador extends RecyclerView.Adapter<adaptador.MyActivity> {

    public Context context;
    int itemCount,aux=0;
    SharedPreferences archivo;
    @NonNull
    @Override
    public adaptador.MyActivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.holder, null);
        adaptador.MyActivity obj = new adaptador.MyActivity(v);
        return obj;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptador.MyActivity activity, int i) {
        final int position=i+1;

        String url = "http://192.168.11.22/movil/muestra.php?id=";
        url=url+position;
        Log.d("ps",String.valueOf(position));
        Log.d("ps",url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("nom")!=null) {
                                activity.nom.setText(response.getString("nom"));
                                activity.apep.setText(response.getString("apep"));
                            }
                            else
                                onBindViewHolder(activity,position+1);
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
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
        //miactivity.nombre.setText(info.lista_p.get(position).getNombre());
        //miactivity.placa.setText(info.lista_p.get(position).getPlaca())
        int finalPosition = position;
        activity.nom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent card = new Intent(context, card_view.class);
                card.putExtra("pos", finalPosition);
                context.startActivity(card);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(itemCount==0)
            getRowCount();
        return itemCount;
    }

    public static class MyActivity extends RecyclerView.ViewHolder{

        TextView nom,apep;

        public MyActivity(@NonNull View itemView) {
            super(itemView);
            nom=itemView.findViewById(R.id.nombre);
            apep=itemView.findViewById(R.id.apellido);
        }
    }
    private void getRowCount() {
        // Crea una instancia de la cola de solicitudes de Volley
        String url = "http://192.168.11.22/movil/cuenta.php";
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());

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
                            notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja los errores de la solicitud

                    }
                });

        // Agrega la solicitud a la cola
        queue.add(request);
    }

}
