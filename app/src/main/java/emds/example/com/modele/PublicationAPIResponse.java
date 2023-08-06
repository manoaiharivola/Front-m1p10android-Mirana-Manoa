package emds.example.com.modele;

import java.util.List;

public class PublicationAPIResponse {
    List<Publication> publicationList;

    public List<Publication> getPublicationList() {
        return publicationList;
    }

    public void setPublicationList(List<Publication> publicationList) {
        this.publicationList = publicationList;
    }
}