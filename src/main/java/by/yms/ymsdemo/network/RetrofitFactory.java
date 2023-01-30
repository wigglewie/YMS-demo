package by.yms.ymsdemo.network;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitFactory {

    private static final String URL = "http://localhost:8080/";

    public static NetworkService service() {
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(JacksonConverterFactory.create()).build()
                .create(NetworkService.class);
    }

}
