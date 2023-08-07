package emds.example.com.vue;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import emds.example.com.R;
import emds.example.com.modele.Lieu;
import emds.example.com.modele.PublicationAPublier;
import emds.example.com.util.NumberFormat;

public class LieuFragment extends Fragment {
    Lieu lieu;
    TextView nom_lieu_details, description_lieu_details, note_lieu_details, abonnes_lieu_details, localisation_lieu_details, contact_lieu_details, mail_lieu_details;
    ImageView image_lieu_details;

    private Button buttonPublier;
    private ImageButton imageButtonPublier;
    private ImageView imageViewPublier;
    private ProgressBar progressBarPublier;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;

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

        buttonPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri != null) {
                    uploadToFirebase(imageUri);
                } else {
                    Toast.makeText(getContext(), "Veuillez selectionner une image !", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data !=null) {
            imageUri = data.getData();
            imageViewPublier.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        PublicationAPublier model = new PublicationAPublier(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);
                        progressBarPublier.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Succès de l'ajout de photo !", Toast.LENGTH_SHORT).show();
                        imageViewPublier.setImageResource(R.drawable.ic_baseline_photo_24);
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