package emds.example.com.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import emds.example.com.R;

public class ChercherLieuFragment extends Fragment {
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        searchView = requireActivity().findViewById(R.id.searh_view);

        return inflater.inflate(R.layout.fragment_chercher_lieu, container, false);
    }
}