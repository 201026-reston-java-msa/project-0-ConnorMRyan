package com.revature.Utils;

import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Scanner;

public class Address {
    Scanner in = new Scanner(System.in);
    int ID;
    int streetNumber;
    String streetName;
    String city;
    String postalCode;
    String country;
    String state;

    public Address(){
        try{
            setPostalCode();
            setStreetName();
            setStreetNumber();
        String APIKEY = System.getenv("API_KEY");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zipcodeapi.com/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ZipService zipService = retrofit.create(ZipService.class);
        Call<JSONObject> call = zipService.zipInfo(APIKEY,postalCode);
        Response<JSONObject> js = call.execute();
        JSONObject qs = js.body();
            assert qs != null;
            city = (String) qs.get("city");
            state = (String) qs.get("state");

            setCountry();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getID() {
        return ID;
    }

    public void setID() {
        this.ID = ID;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber() {
    System.out.println("street number:");
        if(in.hasNextInt()){
            int streetNumber = in.nextInt();
    } else {
      this.streetNumber = 0;
        }
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName() {
    System.out.println("street name");
        this.streetName = in.nextLine();
    }

    public String getCity() {
        return city;
    }

    public void setCity() {
        this.city = in.nextLine();
    }


    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode() {
    System.out.println("postal code:");
        this.postalCode = in.nextLine();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry() {
    System.out.println("Country: ");
        this.country = in.nextLine();
    }

    public String getState() {
        return state;
    }

    public void setState() {
        this.state = in.nextLine();
    }

    @Override
    public String toString() {
        return "Address{" +
                "in=" + in +
                ", ID=" + ID +
                ", streetNumber=" + streetNumber +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
