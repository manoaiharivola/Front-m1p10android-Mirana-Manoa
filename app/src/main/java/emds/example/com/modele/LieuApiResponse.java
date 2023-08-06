package emds.example.com.modele;

public class LieuApiResponse {
    private int status;

    private LieuDataApiResponse data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LieuDataApiResponse getData() {
        return data;
    }

    public void setData(LieuDataApiResponse data) {
        this.data = data;
    }
}
