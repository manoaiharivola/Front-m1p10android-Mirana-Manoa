package emds.example.com.vue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import emds.example.com.R;
import emds.example.com.custom.CustomListePublicationAdapter;
import emds.example.com.custom.CustomSelectLieuListener;
import emds.example.com.interfaces.RetrofitInterface;
import emds.example.com.modele.Lieu;
import emds.example.com.modele.Publication;
import emds.example.com.modele.PublicationApiResponse;
import emds.example.com.modele.PublicationDataAPIResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements CustomSelectLieuListener {

    private LoadingBagage loadingBagage;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    private List<Publication> publicationList;
    private CustomListePublicationAdapter adapter;
    ProgressDialog dialog;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final HomeFragment thisFragment = this;

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        loadingBagage = new LoadingBagage(getContext());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String accessToken = sharedPreferences.getString("access_token", "");

        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recycler_main);

        if (activeNetwork != null && activeNetwork.isConnected()) {
            dialog = new ProgressDialog(getContext());
            dialog.setTitle("Chargement en cours ...");
            dialog.show();

            if (accessToken != "") {
                Call<PublicationApiResponse> call = retrofitInterface.getPublications("Bearer " + accessToken, null,null);
                call.enqueue(new Callback<PublicationApiResponse>() {
                    @Override
                    public void onResponse(Call<PublicationApiResponse> call, Response<PublicationApiResponse> response) {
                        PublicationApiResponse result = response.body();
                        if (result.getStatus() == 200) {
                            PublicationDataAPIResponse publicationDataAPIResponse = result.getData();
                            List<Publication> publications = publicationDataAPIResponse.getPublications();
                            publicationList.clear();
                            publicationList.addAll(publications);
                            if(publicationList.isEmpty()) {
                                Toast.makeText(getContext(), "Aucun résultat", Toast.LENGTH_LONG).show();
                            }
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else if (result.getStatus() == 401) {
                            loadingBagage.show();
                            Handler handler = new Handler();
                            Runnable runnable = new Runnable() {

                                @Override
                                public void run() {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.remove("access_token");
                                    editor.apply();
                                    startActivity(new Intent(getContext(), Login.class));
                                    requireActivity().finish();
                                    Toast.makeText(getContext(), "Session expirée! Authentification requise", Toast.LENGTH_LONG).show();
                                }
                            };
                            handler.postDelayed(runnable, 2000);
                        } else {
                            Toast.makeText(getContext(), "Erreur !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PublicationApiResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Erreur serveur !", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                startActivity(new Intent(getContext(), Login.class));
                requireActivity().finish();
                Toast.makeText(getContext(), "Authentification requise", Toast.LENGTH_LONG).show();
            }

            publicationList = new ArrayList<>();
            RecyclerView recyclerView = view.findViewById(R.id.recycler_main);
            adapter = new CustomListePublicationAdapter(getContext(), publicationList, thisFragment);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    dialog = new ProgressDialog(getContext());
                    dialog.setTitle("Chargement en cours ...");
                    dialog.show();

                    if (accessToken != "") {
                        Call<PublicationApiResponse> call = retrofitInterface.getPublications("Bearer " + accessToken, null,null);
                        call.enqueue(new Callback<PublicationApiResponse>() {
                            @Override
                            public void onResponse(Call<PublicationApiResponse> call, Response<PublicationApiResponse> response) {
                                PublicationApiResponse result = response.body();
                                if (result.getStatus() == 200) {
                                    PublicationDataAPIResponse publicationDataAPIResponse = result.getData();
                                    List<Publication> publications = publicationDataAPIResponse.getPublications();
                                    publicationList.clear();
                                    publicationList.addAll(publications);
                                    if(publicationList.isEmpty()) {
                                        Toast.makeText(getContext(), "Aucun résultat", Toast.LENGTH_LONG).show();
                                    }
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                } else if (result.getStatus() == 401) {
                                    loadingBagage.show();
                                    Handler handler = new Handler();
                                    Runnable runnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.remove("access_token");
                                            editor.apply();
                                            startActivity(new Intent(getContext(), Login.class));
                                            requireActivity().finish();
                                            Toast.makeText(getContext(), "Session expirée! Authentification requise", Toast.LENGTH_LONG).show();
                                        }
                                    };
                                    handler.postDelayed(runnable, 2000);
                                } else {
                                    Toast.makeText(getContext(), "Erreur !", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PublicationApiResponse> call, Throwable t) {
                                Toast.makeText(getContext(), "Erreur serveur !", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        startActivity(new Intent(getContext(), Login.class));
                        requireActivity().finish();
                        Toast.makeText(getContext(), "Authentification requise", Toast.LENGTH_LONG).show();
                    }

                    publicationList = new ArrayList<>();
                    RecyclerView recyclerView = view.findViewById(R.id.recycler_main);
                    adapter = new CustomListePublicationAdapter(getContext(), publicationList, thisFragment);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                else {
                    Toast.makeText(getContext(), "Pas de connexion Internet", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onLieuClicked(Lieu lieu) {

        LieuFragment lieuFragment = new LieuFragment();
        Bundle args = new Bundle();
        args.putSerializable("lieu", lieu);
        lieuFragment.setArguments(args);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, lieuFragment)
                .addToBackStack(null)
                .commit();
    }
}