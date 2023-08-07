package emds.example.com.modele;

import java.io.Serializable;

public class Lieu implements Serializable {
    private String _id;
    private String lieu_image;
    private String lieu_nom;
    private String lieu_contact;
    private String lieu_description;
    private String lieu_localisation;
    private String lieu_mail;
    private int abonnes;
    private float note_moyenne;
    private boolean isAbonne;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getLieu_image() {
        return lieu_image;
    }

    public void setLieu_image(String lieu_image) {
        this.lieu_image = lieu_image;
    }

    public String getLieu_nom() {
        return lieu_nom;
    }

    public void setLieu_nom(String lieu_nom) {
        this.lieu_nom = lieu_nom;
    }

    public String getLieu_contact() {
        return lieu_contact;
    }

    public void setLieu_contact(String lieu_contact) {
        this.lieu_contact = lieu_contact;
    }

    public String getLieu_description() {
        return lieu_description;
    }

    public void setLieu_description(String lieu_description) {
        this.lieu_description = lieu_description;
    }

    public String getLieu_localisation() {
        return lieu_localisation;
    }

    public void setLieu_localisation(String lieu_localisation) {
        this.lieu_localisation = lieu_localisation;
    }

    public String getLieu_mail() {
        return lieu_mail;
    }

    public void setLieu_mail(String lieu_mail) {
        this.lieu_mail = lieu_mail;
    }

    public int getAbonnes() {
        return abonnes;
    }

    public void setAbonnes(int abonnes) {
        this.abonnes = abonnes;
    }

    public float getNote_moyenne() {
        return note_moyenne;
    }

    public void setNote_moyenne(float note_moyenne) {
        this.note_moyenne = note_moyenne;
    }

    public boolean isAbonne() {
        return isAbonne;
    }

    public void setAbonne(boolean abonne) {
        isAbonne = abonne;
    }
}
