package emds.example.com.interfaces;

import java.util.HashMap;

import emds.example.com.modele.APIResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/authentification/login")
    Call<APIResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/authentification/register")
    Call<APIResult> executeInscription(@Body HashMap<String, String> map);

    @GET("/publications")
    Call<APIResult> getPublications(@Header("Authorization") String bearerToken);
}
