package emds.example.com.modele;

public class Publication {
    private String _id;
    private String publication_uuid;
    private String fk_user_id;
    private String fk_lieu_id;
    private String fk_categorie_id;
    private String publication_description;
    private String publication_image;
    private String date_publication;
    private LieuDetails lieuDetails;
    private CategorieDetails categorieDetails;
    private UserDetails userDetails;
    private int reactionsCount;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPublication_uuid() {
        return publication_uuid;
    }

    public void setPublication_uuid(String publication_uuid) {
        this.publication_uuid = publication_uuid;
    }

    public String getFk_user_id() {
        return fk_user_id;
    }

    public void setFk_user_id(String fk_user_id) {
        this.fk_user_id = fk_user_id;
    }

    public String getFk_lieu_id() {
        return fk_lieu_id;
    }

    public void setFk_lieu_id(String fk_lieu_id) {
        this.fk_lieu_id = fk_lieu_id;
    }

    public String getFk_categorie_id() {
        return fk_categorie_id;
    }

    public void setFk_categorie_id(String fk_categorie_id) {
        this.fk_categorie_id = fk_categorie_id;
    }

    public String getPublication_description() {
        return publication_description;
    }

    public void setPublication_description(String publication_description) {
        this.publication_description = publication_description;
    }

    public String getPublication_image() {
        return publication_image;
    }

    public void setPublication_image(String publication_image) {
        this.publication_image = publication_image;
    }

    public String getDate_publication() {
        return date_publication;
    }

    public void setDate_publication(String date_publication) {
        this.date_publication = date_publication;
    }

    public LieuDetails getLieuDetails() {
        return lieuDetails;
    }

    public void setLieuDetails(LieuDetails lieuDetails) {
        this.lieuDetails = lieuDetails;
    }

    public CategorieDetails getCategorieDetails() {
        return categorieDetails;
    }

    public void setCategorieDetails(CategorieDetails categorieDetails) {
        this.categorieDetails = categorieDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public int getReactionsCount() {
        return reactionsCount;
    }

    public void setReactionsCount(int reactionsCount) {
        this.reactionsCount = reactionsCount;
    }
}
