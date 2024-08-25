package com.example.project.project.api;
import com.example.project.project.pojos.ResponseDefault;
import com.example.project.project.pojos.ResponseLogin;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin> login(
      @Field("email") String email,
      @Field("password") String password
    );
}
