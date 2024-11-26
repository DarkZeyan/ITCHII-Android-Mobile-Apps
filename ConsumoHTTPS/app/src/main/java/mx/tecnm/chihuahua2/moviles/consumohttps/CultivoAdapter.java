package mx.tecnm.chihuahua2.moviles.consumohttps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CultivoAdapter extends RecyclerView.Adapter<CultivoAdapter.ViewHolder>{

    private Context context;
    private List<Cultivo> cultivo_list;
    public CultivoAdapter(Context context, List<Cultivo> cultivo_list){
        this.context = context;
        this.cultivo_list   = cultivo_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cultivo cultivo = cultivo_list.get(position);
        holder.textId.setText(String.valueOf(cultivo.getId_graminea()));
        holder.textNombreComun.setText(cultivo.getNombre_comun());
        holder.textNombreCientifico.setText(cultivo.getNombre_cientifico());
    }

    @Override
    public int getItemCount() {
        return cultivo_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textId, textNombreComun, textNombreCientifico;

        public ViewHolder(View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.main_id_graminea);
            textNombreComun = itemView.findViewById(R.id.main_nombre_comun);
            textNombreCientifico = itemView.findViewById(R.id.main_nombre_cientifico);
        }
    }

}
