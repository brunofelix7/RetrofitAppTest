package net.codifica.retrofitapptest;

import net.codifica.retrofitapptest.models.UdacityCatalog;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface de contrato (regras) para acessar a API
 * O Retrofit transforma minha API HTTP em uma Interface Java
 */
public interface UdacityService {

    // URL base da minha API
    String URL = "https://www.udacity.com/public-api/v0/";

    // Cada chamada pode fazer uma solicitação HTTP síncrona ou assíncrona para o servidor web remoto.
    @GET("courses")
    Call<UdacityCatalog> listCatalog();

}
