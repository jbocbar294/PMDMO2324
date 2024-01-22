package iesmm.pmdm.pmdm_t4_agenda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

    public List<ContactoModelo> contactoLista;

    public RecyclerViewAdaptador(List<ContactoModelo> contactoLista) {
        this.contactoLista = contactoLista;
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
        holder.nombre.setText(contactoLista.get(position).getNombre());
        holder.email.setText(contactoLista.get(position).getEmail());
        holder.telefono.setText(contactoLista.get(position).getTelefono());
        holder.imagen.setImageResource(R.drawable.anonymous);
    }

    @Override
    public int getItemCount() {
        return contactoLista.size();
    }
}