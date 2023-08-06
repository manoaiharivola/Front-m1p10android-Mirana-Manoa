package emds.example.com.modele;

public class UserDetails {
    private String _id;
    private String utilisateur_nom;
    private String utilisateur_prenom;
    private String utilisateur_pseudo;
    private String utilisateur_date_naissance;
    private String utilisateur_sexe;
    private String utilisateur_mail;
    private String utilisateur_uuid;
    private String utilisateur_connexion_id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUtilisateur_nom() {
        return utilisateur_nom;
    }

    public void setUtilisateur_nom(String utilisateur_nom) {
        this.utilisateur_nom = utilisateur_nom;
    }

    public String getUtilisateur_prenom() {
        return utilisateur_prenom;
    }

    public void setUtilisateur_prenom(String utilisateur_prenom) {
        this.utilisateur_prenom = utilisateur_prenom;
    }

    public String getUtilisateur_pseudo() {
        return utilisateur_pseudo;
    }

    public void setUtilisateur_pseudo(String utilisateur_pseudo) {
        this.utilisateur_pseudo = utilisateur_pseudo;
    }

    public String getUtilisateur_date_naissance() {
        return utilisateur_date_naissance;
    }

    public void setUtilisateur_date_naissance(String utilisateur_date_naissance) {
        this.utilisateur_date_naissance = utilisateur_date_naissance;
    }

    public String getUtilisateur_sexe() {
        return utilisateur_sexe;
    }

    public void setUtilisateur_sexe(String utilisateur_sexe) {
        this.utilisateur_sexe = utilisateur_sexe;
    }

    public String getUtilisateur_mail() {
        return utilisateur_mail;
    }

    public void setUtilisateur_mail(String utilisateur_mail) {
        this.utilisateur_mail = utilisateur_mail;
    }

    public String getUtilisateur_uuid() {
        return utilisateur_uuid;
    }

    public void setUtilisateur_uuid(String utilisateur_uuid) {
        this.utilisateur_uuid = utilisateur_uuid;
    }

    public String getUtilisateur_connexion_id() {
        return utilisateur_connexion_id;
    }

    public void setUtilisateur_connexion_id(String utilisateur_connexion_id) {
        this.utilisateur_connexion_id = utilisateur_connexion_id;
    }
}
