package emds.example.com.modele;

import java.util.List;

public class PublicationDataAPIResponse {
    private int totalPublications;
    private List<Publication> publications;

    public int getTotalPublications() {
        return totalPublications;
    }

    public void setTotalPublications(int totalPublications) {
        this.totalPublications = totalPublications;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }
}