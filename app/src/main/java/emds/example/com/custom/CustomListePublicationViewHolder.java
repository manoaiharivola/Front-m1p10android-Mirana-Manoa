package emds.example.com.custom;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import emds.example.com.R;

public class CustomListePublicationViewHolder extends RecyclerView.ViewHolder {
    TextView publication_lieu, publication_utilisateur, publication_date, publication_description, publication_categorie, lieu_is_abonne, publication_nombre_reactions;
    ImageView publication_image;
    CardView cardView;
    LottieAnimationView publication_reaction;

    public CustomListePublicationViewHolder(@NonNull View itemView) {
        super(itemView);

        publication_lieu = itemView.findViewById(R.id.publication_lieu);
        publication_utilisateur = itemView.findViewById(R.id.publication_utilisateur);
        publication_date = itemView.findViewById(R.id.publication_date);
        publication_description = itemView.findViewById(R.id.publication_description);
        publication_image = itemView.findViewById(R.id.publication_image);
        cardView = itemView.findViewById(R.id.main_container);
        publication_categorie = itemView.findViewById(R.id.publication_categorie);
        lieu_is_abonne = itemView.findViewById(R.id.lieu_is_abonne);
        publication_reaction = itemView.findViewById(R.id.publication_reaction);
        publication_nombre_reactions = itemView.findViewById(R.id.publication_nombre_reactions);
    }
}
