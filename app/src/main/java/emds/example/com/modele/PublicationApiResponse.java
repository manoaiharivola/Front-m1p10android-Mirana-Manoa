package emds.example.com.modele;

public class PublicationApiResponse {
    private int status;

    private PublicationDataAPIResponse data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PublicationDataAPIResponse getData() {
        return data;
    }

    public void setData(PublicationDataAPIResponse data) {
        this.data = data;
    }
}
