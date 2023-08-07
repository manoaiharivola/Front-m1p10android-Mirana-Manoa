package emds.example.com.util;import java.util.HashMap;
import java.util.Map;

public class CategorieManager {
    private static Map<String, String> categorieMap;

    static {
        categorieMap = new HashMap<>();
        categorieMap.put("Toute catégorie", null);
        categorieMap.put("Activité", "64ca378766ecd7b3eb614bde");
        categorieMap.put("Restauration", "64cd56df0f076bc2e714e878");
        categorieMap.put("Culture", "64cd57200f076bc2e714e879");
        categorieMap.put("Biodiversité", "64cd57520f076bc2e714e87a");
        categorieMap.put("Service", "64cd57730f076bc2e714e87b");
        categorieMap.put("Autres", "64cd57900f076bc2e714e87c");
    }

    public static String getIdByNomCategorie(String nomCategorie) {
        return categorieMap.get(nomCategorie);
    }
}


