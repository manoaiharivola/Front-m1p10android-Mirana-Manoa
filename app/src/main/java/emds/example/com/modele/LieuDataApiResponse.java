package emds.example.com.modele;

import java.util.List;

public class LieuDataApiResponse {
    private int totalLieux;
    private List<Lieu> lieux;

    public int getTotalLieux() {
        return totalLieux;
    }

    public void setTotalLieux(int totalLieux) {
        this.totalLieux = totalLieux;
    }

    public List<Lieu> getLieux() {
        return lieux;
    }

    public void setLieux(List<Lieu> lieux) {
        this.lieux = lieux;
    }
}
