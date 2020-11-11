package com.revature.Utils.Adresses;

import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ZipService {
    @GET("{apikey}/info.json/{zipcode}/degrees")
    Call<JSONObject> zipInfo(@Path("apikey") String apikey, @Path("zipcode") String zipcode);
}
