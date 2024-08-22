package com.example.aconverterapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface currencyAPI {
    @GET("live")
    Call<ExchangeRatesResponse> getExchangeRates(
            @Query("access_key") String apiKey,
            @Query("currencies") String currencies,
            @Query("source") String source,
            @Query("format") int format
    );
}
