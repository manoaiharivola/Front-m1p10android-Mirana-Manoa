package emds.example.com.vue;

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

import emds.example.com.R;
import emds.example.com.interfaces.RetrofitInterface;
import emds.example.com.modele.APIResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private LoadingBagage loadingBagage;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

            if (accessToken != "") {
                Call<APIResult> call = retrofitInterface.getPublications("Bearer a" + accessToken);
                call.enqueue(new Callback<APIResult>() {
                    @Override
                    public void onResponse(Call<APIResult> call, Response<APIResult> response) {
                        APIResult result = response.body();
                        if (result.getStatus() == 200) {

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
                                    Toast.makeText(getContext(), "Session expirée! Authentification requise", Toast.LENGTH_LONG).show();
                                }
                            };
                            handler.postDelayed(runnable, 3000);
                        }  else {
                            Toast.makeText(getContext(), "Erreur !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResult> call, Throwable t) {
                        Toast.makeText(getContext(), "Erreur serveur !", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                startActivity(new Intent(getContext(), Login.class));
                Toast.makeText(getContext(), "Authentification requise", Toast.LENGTH_LONG).show();
            }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}