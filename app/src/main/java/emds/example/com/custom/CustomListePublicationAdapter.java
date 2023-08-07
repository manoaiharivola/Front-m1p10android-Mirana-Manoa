package emds.example.com.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import emds.example.com.R;
import emds.example.com.interfaces.RetrofitInterface;
import emds.example.com.modele.APIResult;
import emds.example.com.modele.Lieu;
import emds.example.com.modele.Publication;
import emds.example.com.modele.PublicationApiResponse;
import emds.example.com.util.DateFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomListePublicationAdapter extends RecyclerView.Adapter<CustomListePublicationViewHolder> {
    private Context context;
    private List<Publication> publications;
    private CustomSelectLieuListener lieuListener;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    public CustomListePublicationAdapter(Context context, List<Publication> publications, CustomSelectLieuListener lieuListener) {
        this.context = context;
        this.publications = publications;
        this.lieuListener = lieuListener;
    }

    @NonNull
    @Override
    public CustomListePublicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
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

        if(publications.get(position).isAbonne() != true) {
            holder.lieu_is_abonne.setVisibility(View.GONE);
        }
        else {
            holder.lieu_is_abonne.setVisibility(View.VISIBLE);
        }

        if(publications.get(position).isHasReacted()) {
            holder.publication_reaction.setSpeed(-1);
            holder.publication_reaction.pauseAnimation();
        } else {
            holder.publication_reaction.setSpeed(1);
            holder.publication_reaction.pauseAnimation();
        }

        holder.publication_nombre_reactions.setText(String.valueOf(publications.get(position).getReactionsCount()));

        holder.publication_lieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lieu lieu = publications.get(position).getLieuDetails();
                lieu.setAbonne(publications.get(position).isAbonne());
                lieuListener.onLieuClicked(lieu);
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String accessToken = sharedPreferences.getString("access_token", "");
        HashMap<String, String> map = new HashMap<>();
        map.put("fk_publication_id", publications.get(position).get_id());

        holder.publication_reaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(publications.get(position).isHasReacted()) {
                    holder.publication_reaction.setSpeed(-1);
                    holder.publication_reaction.playAnimation();
                    publications.get(position).setHasReacted(false);
                    holder.publication_nombre_reactions.setText(String.valueOf(Integer.parseInt((String) holder.publication_nombre_reactions.getText())-1));
                    Call<APIResult> call = retrofitInterface.supprimerReaction("Bearer " + accessToken, map);
                    call.enqueue(new Callback<APIResult>() {
                        @Override
                        public void onResponse(Call<APIResult> call, Response<APIResult> response) {
                            APIResult result = response.body();
                            if (result.getStatus() != 200) {
                                holder.publication_reaction.setSpeed(1);
                                holder.publication_reaction.playAnimation();
                                publications.get(position).setHasReacted(true);
                                holder.publication_nombre_reactions.setText(String.valueOf(Integer.parseInt((String) holder.publication_nombre_reactions.getText())+1));
                                Toast.makeText(context, "Erreur !", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<APIResult> call, Throwable t) {
                            Toast.makeText(context, "Erreur serveur !", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    holder.publication_reaction.setSpeed(1);
                    holder.publication_reaction.playAnimation();
                    publications.get(position).setHasReacted(true);
                    holder.publication_nombre_reactions.setText(String.valueOf(Integer.parseInt((String) holder.publication_nombre_reactions.getText())+1));
                    Call<APIResult> call = retrofitInterface.reagir("Bearer " + accessToken, map);
                    call.enqueue(new Callback<APIResult>() {
                        @Override
                        public void onResponse(Call<APIResult> call, Response<APIResult> response) {
                            APIResult result = response.body();
                            if (result.getStatus() != 201) {
                                holder.publication_reaction.setSpeed(-1);
                                holder.publication_reaction.playAnimation();
                                publications.get(position).setHasReacted(false);
                                holder.publication_nombre_reactions.setText(String.valueOf(Integer.parseInt((String) holder.publication_nombre_reactions.getText())-1));
                                Toast.makeText(context, "Erreur !", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<APIResult> call, Throwable t) {
                            Toast.makeText(context, "Erreur serveur !", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return publications.size();
    }
}
