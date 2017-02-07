package net.codifica.retrofitapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import net.codifica.retrofitapptest.models.Course;
import net.codifica.retrofitapptest.models.Instructor;
import net.codifica.retrofitapptest.models.UdacityCatalog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Retrofit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gerando a implementação da interface da minha API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UdacityService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Classe que implementa meu contrato, retorna uma classe que implementa a interface UdacityService
        UdacityService service = retrofit.create(UdacityService.class);

        // Objeto que fará a chamada do meu Endpoint, de forma síncrona ou assíncrona
        // requestCatalog possui os métodos execute() - Síncrona e o enqueue() - Assíncrona
        Call<UdacityCatalog> requestCatalog = service.listCatalog();

        requestCatalog.enqueue(new Callback<UdacityCatalog>() {

            // Não significa que você obteve uma resposta com sucesso, servidor com erro por exemplo, cai aqui
            @Override
            public void onResponse(Call<UdacityCatalog> call, Response<UdacityCatalog> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "Error: " + response.code());
                }else{
                    // Requisição retornada com sucesso "200"
                    // Converte a resposta
                    UdacityCatalog catalog = response.body();   // response.headers();

                    // Percorrendo e trazendo os resultados
                    for(Course course : catalog.getCourses()){
                        Log.d(TAG, String.format("%s: %s", course.getTitle(), course.getSubtitle()));

                        for(Instructor instructor : course.getInstructors()){
                            Log.d(TAG, String.format("%s: %s", instructor.getName(), instructor.getBio()));
                        }
                        Log.d(TAG, "------------");
                    }
                }
            }

            // Quando eu não consigo fazer uma requisição
            @Override
            public void onFailure(Call<UdacityCatalog> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
            }
        });

    }
}
