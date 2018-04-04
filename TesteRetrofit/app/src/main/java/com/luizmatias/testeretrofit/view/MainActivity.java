package com.luizmatias.testeretrofit.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.luizmatias.testeretrofit.R;
import com.luizmatias.testeretrofit.model.GithubServiceAPI;
import com.luizmatias.testeretrofit.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RETROFIT SERVICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUsuario();

    }

    public void getUsuario(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubServiceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubServiceAPI service = retrofit.create(GithubServiceAPI.class);
        Call<Usuario> call = service.getUsuario("luiz-matias");

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Carregando");
        dialog.setMessage("Recuperando informações do usuário...");
        dialog.setCancelable(false);
        dialog.show();

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                dialog.dismiss();
                if(response.isSuccessful()){
                    Usuario usuario = response.body();
                    Log.d(TAG, "onResponse: Usuário retornado:\n"+ usuario.toString());
                    getSeguidores();
                }else{
                    Log.d(TAG, "onResponse: Erro de código "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                dialog.dismiss();
                Log.d(TAG, "onFailure: Erro:\n"+t.getMessage());
            }
        });
    }

    public void getSeguidores(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubServiceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubServiceAPI service = retrofit.create(GithubServiceAPI.class);
        Call<List<Usuario>> call = service.getSeguidores("luiz-matias");

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Carregando");
        dialog.setMessage("Recuperando informações dos seguidores...");
        dialog.setCancelable(false);
        dialog.show();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                dialog.dismiss();
                if(response.isSuccessful()){
                    List<Usuario> usuarios = response.body();
                    for(Usuario usuario : usuarios){
                        Log.d(TAG, "onResponse: Seguidor retornado:\n"+ usuario.toString());
                    }
                }else{
                    Log.d(TAG, "onResponse: Erro de código "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                dialog.dismiss();
                Log.d(TAG, "onFailure: Erro:\n"+t.getMessage());
            }
        });
    }
}
