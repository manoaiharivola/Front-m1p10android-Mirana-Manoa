package emds.example.com.interfaces;

import java.util.HashMap;

import emds.example.com.modele.APIResult;
import emds.example.com.modele.LieuApiResponse;
import emds.example.com.modele.PublicationApiResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/authentification/login")
    Call<APIResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/authentification/register")
    Call<APIResult> executeInscription(@Body HashMap<String, String> map);

    @GET("/publications")
    Call<PublicationApiResponse> getPublications(@Header("Authorization") String bearerToken, @Query("fk_lieu_id") String fk_lieu_id, @Query("fk_categorie_id") String fk_categorie_id);

    @GET("/lieux")
    Call<LieuApiResponse> getLieux(
            @Header("Authorization") String bearerToken,
            @Query("lieu_nom") String lieu_nom
    );

    @POST("/publications")
    Call<APIResult> createPublication(@Header("Authorization") String bearerToken, @Body HashMap<String, String> map);

    @POST("/lieux/subscribe")
    Call<APIResult> sAbonner(@Header("Authorization") String bearerToken, @Body HashMap<String, String> map);


    @POST("/lieux/unsubscribe")
    Call<APIResult> seDesabonner(@Header("Authorization") String bearerToken, @Body HashMap<String, String> map);

    @POST("/reactions")
    Call<APIResult> reagir(@Header("Authorization") String bearerToken, @Body HashMap<String, String> map);

    @POST("/reactions/delete")
    Call<APIResult> supprimerReaction(@Header("Authorization") String bearerToken, @Body HashMap<String, String> map);
}
