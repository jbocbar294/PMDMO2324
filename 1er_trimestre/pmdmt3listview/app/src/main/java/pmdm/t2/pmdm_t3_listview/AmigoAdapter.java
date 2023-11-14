package pmdm.t2.pmdm_t3_listview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AmigoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Amigo> listaAmigos;
    private LayoutInflater inflater;
    public AmigoAdapter(Activity context, ArrayList<Amigo> listaAmigos) {
        this.context = context;
        this.listaAmigos = listaAmigos;
        inflater = LayoutInflater.from(context);
    }
    static class ViewHolder {
        TextView nif;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        // Si la View es null se crea de nuevo
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fila, null);
            holder = new ViewHolder();
            holder.nif = (TextView) convertView.findViewById(R.id.nif);
            convertView.setTag(holder);
        }
        /*
         * En caso de que la View no sea null se reutilizará con los
         * nuevos valores
         */
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Amigo amigo = listaAmigos.get(position);
        holder.nif.setText(amigo.getNif());
        return convertView;
    }
    @Override
    public int getCount() {
        return listaAmigos.size();
    }
    @Override
    public Object getItem(int posicion) {
        return listaAmigos.get(posicion);
    }
    @Override
    public long getItemId(int posicion) {
        return posicion;
    }
}