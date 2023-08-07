package emds.example.com.modele;

public class PublicationAPublier {
    public String imageUrl;

    public PublicationAPublier() {
    }

    public PublicationAPublier(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
