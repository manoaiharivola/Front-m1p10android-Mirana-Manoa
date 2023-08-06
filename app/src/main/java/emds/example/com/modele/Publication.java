package emds.example.com.modele;

public class Publication {
    String nomLieu;
    String nomUtilisateur;
    String datePublication;
    String descriptionPublication;
    String urlImage;

    public Publication(String nomLieu, String nomUtilisateur, String datePublication, String descriptionPublication, String urlImage) {
        this.nomLieu = nomLieu;
        this.nomUtilisateur = nomUtilisateur;
        this.datePublication = datePublication;
        this.descriptionPublication = descriptionPublication;
        this.urlImage = urlImage;
    }

    public String getNomLieu() {
        return nomLieu;
    }

    public void setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }

    public String getDescriptionPublication() {
        return descriptionPublication;
    }

    public void setDescriptionPublication(String descriptionPublication) {
        this.descriptionPublication = descriptionPublication;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
