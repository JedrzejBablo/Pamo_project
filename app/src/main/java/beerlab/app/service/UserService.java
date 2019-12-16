package beerlab.app.service;

import beerlab.app.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserService {
    @GET("/api/user/me")
    Call<User> checkMe(@Header("X-Auth_Token") String authorization);
}
