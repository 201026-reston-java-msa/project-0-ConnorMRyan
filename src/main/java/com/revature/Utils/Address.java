package com.revature.Utils;


import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    DatabaseConnection db;
    final String TABLENAME = "Various.Address";


    public Address() {
        try {
            this.db = DatabaseConnection.getConnection();
            setPostalCode();
            setStreetNumber();
            setStreetName();
            String APIKEY = System.getenv("API_KEY");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.zipcodeapi.com/rest/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ZipService zipService = retrofit.create(ZipService.class);
            Call<JSONObject> call = zipService.zipInfo(APIKEY, postalCode);
            Response<JSONObject> js = call.execute();
            JSONObject qs = js.body();
            assert qs != null;
            city = (String) qs.get("city");
            state = (String) qs.get("state");
            setCountry();
            if(isCorrectAddress()) {
                deployToDB();
                setID();
            }else{
                setCity();
                setState();
                deployToDB();
                setID();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCorrectAddress(){
        System.out.println("Is this your address?: [Y/n]");
        System.out.println(this.toString());
        return in.nextLine().toUpperCase().charAt(0) == 'Y';
    }

    public int getID() {
        return ID;
    }

    boolean isUniqueAddress(){
        try {
            String SQL = "SELECT COUNT(ID) FROM Various.Address WHERE Street_Number = ? AND Street_Name = ? AND Postal_Code = ? ";
            ResultSet rs = db.getResult(SQL, "" + streetNumber, streetName, postalCode);
            rs.next();
            return (rs.getInt("") == 0);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void setID() {
        try {
            String SQL = "SELECT ID FROM Various.Address WHERE Street_Number = ? AND Street_Name = ? AND Postal_Code = ?";
            ResultSet rs = db.getResult(SQL, "" + streetNumber, streetName, postalCode);
            rs.next();
            ID = rs.getInt("ID");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber() {
        System.out.println("What is your street number: ");
        streetNumber = in.nextInt();
        in.nextLine();
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName() {
        System.out.println("What is your street name: ");
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
        System.out.println("What is your postal code?");
        this.postalCode = in.nextLine();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry() {
        this.country = "United States";
    }

    public String getState() {


        return state;


    }

    public void setState() {
        this.state = in.nextLine();
    }

    public void deployToDB(){
        if(isUniqueAddress()) {
            String SQL = "INSERT INTO Various.Address (Street_Number,Street_Name,City,Postal_Code,State_Abr,Country)" +
                    " VALUES (?,?,?,?,?,?) ";
            db.submitSQL(SQL, Integer.toString(streetNumber), streetName, city, postalCode, state, country);
        }

    }

    @Override
    public String toString() {
        return streetNumber +" "+ streetName + "\n" +
                 city + ", " + state + "\n" +
                postalCode + "\n" +
                country + "\n";
    }
}
