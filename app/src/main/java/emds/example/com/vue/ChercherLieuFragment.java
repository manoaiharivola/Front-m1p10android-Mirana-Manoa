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

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import emds.example.com.R;
import emds.example.com.custom.CustomListeLieuAdapter;
import emds.example.com.interfaces.RetrofitInterface;
import emds.example.com.modele.Lieu;
import emds.example.com.modele.LieuApiResponse;
import emds.example.com.modele.LieuDataApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChercherLieuFragment extends Fragment {

    private LoadingBagage loadingBagage;

    SearchView searchView;

    private List<Lieu> lieuxList;
    private CustomListeLieuAdapter adapter;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_chercher_lieu, container, false);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String accessToken = sharedPreferences.getString("access_token", "");

        loadingBagage = new LoadingBagage(getContext());

        searchView = view.findViewById(R.id.searh_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    dialog = new ProgressDialog(getContext());
                    dialog.setTitle("Recherche ...");
                    dialog.show();
                    if (accessToken != "") {
                        Call<LieuApiResponse> call = retrofitInterface.getLieux("Bearer " + accessToken, query);
                        call.enqueue(new Callback<LieuApiResponse>() {
                            @Override
                            public void onResponse(Call<LieuApiResponse> call, Response<LieuApiResponse> response) {
                                LieuApiResponse result = response.body();
                                if (result.getStatus() == 200) {
                                    LieuDataApiResponse lieuDataApiResponse = result.getData();
                                    List<Lieu> lieux = lieuDataApiResponse.getLieux();
                                    if(lieux.isEmpty()) {
                                        Toast.makeText(getContext(), "Aucun résultat", Toast.LENGTH_LONG).show();
                                    }
                                    lieuxList.clear();
                                    lieuxList.addAll(lieux);
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
                            public void onFailure(Call<LieuApiResponse> call, Throwable t) {
                                Toast.makeText(getContext(), "Erreur serveur !", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else {
                        startActivity(new Intent(getContext(), Login.class));
                        requireActivity().finish();
                        Toast.makeText(getContext(), "Authentification requise", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Pas de connexion Internet", Toast.LENGTH_SHORT).show();
                }

                lieuxList = new ArrayList<>();
                RecyclerView recyclerView = view.findViewById(R.id.recycler_main1);
                adapter = new CustomListeLieuAdapter(getContext(), lieuxList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }
}