package adaptador;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

import org.json.JSONException;
import org.json.JSONObject;

import global.info;

public class eliminar extends RecyclerView.Adapter<eliminar.Actividad> {
    
    public Context context;
    int itemCount;
    
    @NonNull
    @Override
    public eliminar.Actividad onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.borrar_holder, null);
        eliminar.Actividad obj = new eliminar.Actividad(v);
        return obj;
    }

    @Override
    public void onBindViewHolder(@NonNull eliminar.Actividad holder, int i) {
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
                            holder.nom.setText(response.getString("nom"));
                            holder.apep.setText(response.getString("apep"));
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
        holder.checkBox.setChecked(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox)view).isChecked()){
                    info.lista_eliminar.add(position);
                }
                else {
                    info.lista_eliminar.remove(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if(itemCount==0)
            getRowCount();
        return itemCount;
    }

    public class Actividad extends RecyclerView.ViewHolder {
        TextView nom,apep;
        CheckBox checkBox;

        public Actividad(@NonNull View itemView) {
            super(itemView);
            nom=itemView.findViewById(R.id.eliminar_nombre);
            apep=itemView.findViewById(R.id.eliminar_apellido);
            checkBox=itemView.findViewById(R.id.eliminar_check);
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


