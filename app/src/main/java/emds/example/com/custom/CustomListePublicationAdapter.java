package emds.example.com.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import emds.example.com.R;
import emds.example.com.modele.Publication;
import emds.example.com.util.DateFormat;

public class CustomListePublicationAdapter extends RecyclerView.Adapter<CustomListePublicationViewHolder> {
    private Context context;
    private List<Publication> publications;
    private CustomSelectLieuListener lieuListener;

    public CustomListePublicationAdapter(Context context, List<Publication> publications, CustomSelectLieuListener lieuListener) {
        this.context = context;
        this.publications = publications;
        this.lieuListener = lieuListener;
    }

    @NonNull
    @Override
    public CustomListePublicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomListePublicationViewHolder(LayoutInflater.from(context).inflate(R.layout.publication_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomListePublicationViewHolder holder, int position) {
        holder.publication_lieu.setText(publications.get(position).getLieuDetails().getLieu_nom());
        holder.publication_utilisateur.setText(publications.get(position).getUserDetails().getUtilisateur_pseudo());
        holder.publication_date.setText(DateFormat.formatVueDate(publications.get(position).getDate_publication()));
        holder.publication_description.setText(publications.get(position).getPublication_description());
        holder.publication_categorie.setText(publications.get(position).getCategorieDetails().getCategorie_nom());
        if(publications.get(position).getPublication_image()!=null) {
            Picasso.get().load(publications.get(position).getPublication_image()).into(holder.publication_image);
        } else {
            holder.publication_image.setImageResource(R.drawable.not_available);
        }

        holder.publication_lieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lieuListener.onLieuClicked(publications.get(position).getLieuDetails());
            }
        });
    }

    @Override
    public int getItemCount() {
        return publications.size();
    }
}
