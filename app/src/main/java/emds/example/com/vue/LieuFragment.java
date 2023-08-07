package emds.example.com.vue;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import emds.example.com.R;
import emds.example.com.interfaces.RetrofitInterface;
import emds.example.com.modele.APIResult;
import emds.example.com.modele.Lieu;
import emds.example.com.util.CategorieManager;
import emds.example.com.util.NumberFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LieuFragment extends Fragment {
    Lieu lieu;
    TextView nom_lieu_details, description_lieu_details, note_lieu_details, abonnes_lieu_details, localisation_lieu_details, contact_lieu_details, mail_lieu_details, nom_lieu_details_publier;
    ImageView image_lieu_details;
    EditText editTextTextMultiLineDescription;

    private Button buttonPublier;
    private ImageButton imageButtonPublier;
    private ImageView imageViewPublier;
    private ProgressBar progressBarPublier;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;

    private RadioGroup radioGroupCategorie;
    private String selectedCategorie = "Activité";

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lieu, container, false);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        nom_lieu_details = view.findViewById(R.id.nom_lieu_details);
        description_lieu_details = view.findViewById(R.id.description_lieu_details);
        note_lieu_details = view.findViewById(R.id.note_lieu_details);
        abonnes_lieu_details = view.findViewById(R.id.abonnes_lieu_details);
        localisation_lieu_details = view.findViewById(R.id.localisation_lieu_details);
        contact_lieu_details = view.findViewById(R.id.contact_lieu_details);
        mail_lieu_details = view.findViewById(R.id.mail_lieu_details);
        image_lieu_details = view.findViewById(R.id.image_lieu_details);
        nom_lieu_details_publier = view.findViewById(R.id.nom_lieu_details_publier);
        editTextTextMultiLineDescription = view.findViewById(R.id.editTextTextMultiLineDescription);

        buttonPublier = view.findViewById(R.id.buttonPublier);
        imageButtonPublier = view.findViewById(R.id.imageButtonPublier);
        imageViewPublier = view.findViewById(R.id.imageViewPublier);
        progressBarPublier = view.findViewById(R.id.progressBarPublier);

        progressBarPublier.setVisibility(View.INVISIBLE);

        imageButtonPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });


        Bundle args = getArguments();
        if (args != null) {
            lieu = (Lieu) args.getSerializable("lieu");
        }

        nom_lieu_details.setText(lieu.getLieu_nom());
        nom_lieu_details_publier.setText(lieu.getLieu_nom());
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

        buttonPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idCategorie = CategorieManager.getIdByNomCategorie(selectedCategorie);
                String description = editTextTextMultiLineDescription.getText().toString();
                String idLieu = lieu.get_id();
                if(imageUri != null) {
                    publier(imageUri, idLieu, idCategorie, description);
                } else {
                    Toast.makeText(getContext(), "Veuillez selectionner une image !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroupCategorie = view.findViewById(R.id.radioGroupCategorie);

        radioGroupCategorie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    String selectedText = selectedRadioButton.getText().toString();
                    selectedCategorie = selectedText;
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data !=null) {
            imageUri = data.getData();
            imageViewPublier.setImageURI(imageUri);
        }
    }

    private void publier(Uri uri, String idLieu, String idCategorie, String description) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                        String accessToken = sharedPreferences.getString("access_token", "");
                        String imageUrl = uri.toString();
                        HashMap<String, String> map = new HashMap<>();
                        map.put("fk_lieu_id", idLieu);
                        map.put("fk_categorie_id", idCategorie);
                        map.put("publication_description", description);
                        map.put("publication_image", imageUrl);
                        Call<APIResult> call = retrofitInterface.createPublication("Bearer " + accessToken, map);
                        call.enqueue(new Callback<APIResult>() {
                             @Override
                             public void onResponse(Call<APIResult> call, Response<APIResult> response) {
                                 APIResult result = response.body();
                                 if (result.getStatus() == 201) {
                                     progressBarPublier.setVisibility(View.INVISIBLE);
                                     Toast.makeText(getContext(), "Nouvelle Publication !", Toast.LENGTH_SHORT).show();
                                     imageViewPublier.setImageResource(R.drawable.ic_baseline_photo_24);
                                 } else {
                                     progressBarPublier.setVisibility(View.INVISIBLE);
                                     Toast.makeText(getContext(), "Erreur !", Toast.LENGTH_LONG).show();
                                     imageViewPublier.setImageResource(R.drawable.ic_baseline_photo_24);
                                 }
                             }

                            @Override
                            public void onFailure(Call<APIResult> call, Throwable t) {
                                Toast.makeText(getContext(), "Erreur serveur !", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                progressBarPublier.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                progressBarPublier.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Erreur de l'ajout de photo !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}