package emds.example.com.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import emds.example.com.R;
import emds.example.com.modele.Lieu;
import emds.example.com.util.NumberFormat;

public class LieuFragment extends Fragment {
    Lieu lieu;
    TextView nom_lieu_details, description_lieu_details, note_lieu_details, abonnes_lieu_details, localisation_lieu_details, contact_lieu_details, mail_lieu_details;
    ImageView image_lieu_details;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lieu, container, false);

        nom_lieu_details = view.findViewById(R.id.nom_lieu_details);
        description_lieu_details = view.findViewById(R.id.description_lieu_details);
        note_lieu_details = view.findViewById(R.id.note_lieu_details);
        abonnes_lieu_details = view.findViewById(R.id.abonnes_lieu_details);
        localisation_lieu_details = view.findViewById(R.id.localisation_lieu_details);
        contact_lieu_details = view.findViewById(R.id.contact_lieu_details);
        mail_lieu_details = view.findViewById(R.id.mail_lieu_details);
        image_lieu_details = view.findViewById(R.id.image_lieu_details);

        Bundle args = getArguments();
        if (args != null) {
            lieu = (Lieu) args.getSerializable("lieu");
        }

        nom_lieu_details.setText(lieu.getLieu_nom());
        description_lieu_details.setText(lieu.getLieu_description());
        note_lieu_details.setText(NumberFormat.formatVueFloat(lieu.getNote_moyenne())+"/5");
        if(lieu.getAbonnes() > 1) {
            abonnes_lieu_details.setText(lieu.getAbonnes() + " abonnés");
        } else {
            abonnes_lieu_details.setText(lieu.getAbonnes() + " abonné");
        }
        localisation_lieu_details.setText(lieu.getLieu_localisation());
        contact_lieu_details.setText(lieu.getLieu_contact());
        mail_lieu_details.setText(lieu.getLieu_mail());
        if(lieu.getLieu_image()!=null) {
            Picasso.get().load(lieu.getLieu_image()).into(image_lieu_details);
        }

        return view;
    }
}