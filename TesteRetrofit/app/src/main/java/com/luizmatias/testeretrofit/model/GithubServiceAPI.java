package com.luizmatias.testeretrofit.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ICWB-01 on 04/04/2018.
 */

public interface GithubServiceAPI {

    public static final String BASE_URL = "https://api.github.com/";

    @GET("users/{usuario}")
    Call<Usuario> getUsuario(@Path("usuario") String usuario);

    @GET("users/{usuario}/followers")
    Call<List<Usuario>> getSeguidores(@Path("usuario") String usuario);

}
