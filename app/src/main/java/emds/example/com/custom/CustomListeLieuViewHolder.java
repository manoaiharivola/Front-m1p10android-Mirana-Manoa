package emds.example.com.custom;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import emds.example.com.R;

public class CustomListeLieuViewHolder extends RecyclerView.ViewHolder{
    TextView nom_lieu, localisation_lieu, abonnes_lieu, note_lieu;

    public CustomListeLieuViewHolder(@NonNull View itemView) {
        super(itemView);
        nom_lieu = itemView.findViewById(R.id.nom_lieu);
        localisation_lieu = itemView.findViewById(R.id.localisation_lieu);
        abonnes_lieu = itemView.findViewById(R.id.abonnes_lieu);
        note_lieu = itemView.findViewById(R.id.note_lieu);
    }
}
