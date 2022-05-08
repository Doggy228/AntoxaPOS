package edu.doggy228.antoxapos;

import com.fasterxml.jackson.databind.JsonNode;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoyaltySystemService {
    @GET("ls")
    Call<JsonNode> listLoyaltySystemAll();
}
