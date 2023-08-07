package emds.example.com.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import emds.example.com.R;
import emds.example.com.modele.Lieu;
import emds.example.com.util.NumberFormat;

public class CustomListeLieuAdapter extends RecyclerView.Adapter<CustomListeLieuViewHolder>{
    private Context context;
    private List<Lieu> lieux;

    public CustomListeLieuAdapter(Context context, List<Lieu> lieux) {
        this.context = context;
        this.lieux = lieux;
    }

    @NonNull
    @Override
    public CustomListeLieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomListeLieuViewHolder(LayoutInflater.from(context).inflate(R.layout.lieu_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomListeLieuViewHolder holder, int position) {
        holder.nom_lieu.setText(lieux.get(position).getLieu_nom());
        holder.localisation_lieu.setText(lieux.get(position).getLieu_localisation());
        if(lieux.get(position).getAbonnes() > 1) {
            holder.abonnes_lieu.setText(lieux.get(position).getAbonnes() + " abonnés");
        } else {
            holder.abonnes_lieu.setText(lieux.get(position).getAbonnes() + " abonné");
        }
        holder.note_lieu.setText(NumberFormat.formatVueFloat(lieux.get(position).getNote_moyenne()) + "/5");
    }

    @Override
    public int getItemCount() {
        return lieux.size();
    }
}
