package iesmm.pmdm.pmdm_t4_agenda;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Elementos del layout del CardView
        private TextView nombre, email, telefono;
        ImageView imagen;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.tvNombre);
            email = (TextView) itemView.findViewById(R.id.tvEmail);
            telefono = (TextView) itemView.findViewById(R.id.tvTelefono);
            imagen = (ImageView) itemView.findViewById(R.id.imagenContacto);
        }
    }

    public List<ContactoModelo> contactoLista; // Lista de contactos
    public Context contextoMainActivity; // Almacena el contexto de MainActivity
    // Constructor
    public RecyclerViewAdaptador(List<ContactoModelo> contactoLista, Context context) {
        this.contactoLista = contactoLista;
        this.contextoMainActivity = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Asigna los valores correspondientes a los elementos del layout
        holder.nombre.setText(contactoLista.get(position).getNombre());
        holder.email.setText(contactoLista.get(position).getEmail());
        holder.telefono.setText(contactoLista.get(position).getTelefono());
        holder.imagen.setImageResource(R.drawable.anonymous_icon);
        // Asignamos el escuchador para los CardView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlertDialog con 3 opciones
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(contactoLista.get(position).getNombre());
                builder.setMessage("Selecciona una opción");
                // Opción de Cancelar, no hace nada, simplemente vuelve atrás
                builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                // Opción de llamada
                builder.setPositiveButton("Llamada", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        // Obtiene el teléfono del contacto pulsado
                        intent.setData(Uri.parse("tel:+34" + contactoLista.get(position).getTelefono()));
                        contextoMainActivity.startActivity(intent);
                    }
                });
                // Opción de WhatsApp
                builder.setNegativeButton("WhatsApp", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        try {
                            // Abre WhatsApp sin preguntar por qué aplicación quiere mandar el mensaje
                            sendIntent.setPackage("com.whatsapp");
                            contextoMainActivity.startActivity(sendIntent);
                        } catch (ActivityNotFoundException e) { // Si WhatsApp no está instalado
                            Toast.makeText(contextoMainActivity, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactoLista.size();
    }
}