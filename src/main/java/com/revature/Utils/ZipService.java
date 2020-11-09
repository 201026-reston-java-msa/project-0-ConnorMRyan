package com.revature.Utils;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;

public interface ZipService {
    @GET("{apikey}/info.json/{zipcode}/degrees")
    Call<JSONObject> zipInfo(@Path("apikey") String apikey,@Path("zipcode") String zipcode);
}
