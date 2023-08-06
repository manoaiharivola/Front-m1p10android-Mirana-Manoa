package emds.example.com.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import emds.example.com.R;
import emds.example.com.modele.Publication;

public class CustomListePublicationAdapter extends RecyclerView.Adapter<CustomListePublicationViewHolder> {
    private Context context;
    private List<Publication> publications;

    public CustomListePublicationAdapter(Context context, List<Publication> publications) {
        this.context = context;
        this.publications = publications;
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
        holder.publication_date.setText(publications.get(position).getDate_publication());
        holder.publication_description.setText(publications.get(position).getPublication_description());

        if(publications.get(position).getPublication_image()!=null) {
            Picasso.get().load(publications.get(position).getPublication_image()).into(holder.publication_image);
        }
    }

    @Override
    public int getItemCount() {
        return publications.size();
    }
}
