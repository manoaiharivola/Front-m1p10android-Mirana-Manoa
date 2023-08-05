package emds.example.com.interfaces;

import java.util.HashMap;

import emds.example.com.modele.LoginResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/authentification/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);
}
