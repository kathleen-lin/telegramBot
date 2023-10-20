package practice.telebot;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.ws.rs.core.UriBuilder;

@Service
public class GetFoodSvc {
    
    @Autowired
    private WebClient webClient;

    @Value("${google.place.token}")
    private String googleToken;

    public String getNearByFood(Double l1, Double l2){
        String location = String.valueOf(l1) + "%2C" + String.valueOf(l2);

        URI uri = UriBuilder.fromUri("https://maps.googleapis.com/maps/api/place/nearbysearch/json").queryParam("keyword", "food").queryParam("location", location).queryParam("radius","1500").queryParam("opennow","true").queryParam("key",googleToken).build();
        
        String resp = webClient.get()
                        .uri(uri)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        JsonObject jsonResult = new Gson().fromJson(resp, JsonObject.class);
        JsonObject result1 = jsonResult.getAsJsonArray("results").get(0).getAsJsonObject();
        JsonObject result2 = jsonResult.getAsJsonArray("results").get(1).getAsJsonObject();
        JsonObject result3 = jsonResult.getAsJsonArray("results").get(2).getAsJsonObject();

        Listing listing1 = new Gson().fromJson(result1, Listing.class);
        Listing listing2 = new Gson().fromJson(result2, Listing.class);
        Listing listing3 = new Gson().fromJson(result3, Listing.class);

        Double listing1Lat = result1.getAsJsonObject("geometry").getAsJsonObject("location").get("lat").getAsDouble();
        Double listing1Lng = result1.getAsJsonObject("geometry").getAsJsonObject("location").get("lng").getAsDouble();

        Double listing2Lat = result2.getAsJsonObject("geometry").getAsJsonObject("location").get("lat").getAsDouble();
        Double listing2Lng = result2.getAsJsonObject("geometry").getAsJsonObject("location").get("lng").getAsDouble();

        Double listing3Lat = result3.getAsJsonObject("geometry").getAsJsonObject("location").get("lat").getAsDouble();
        Double listing3Lng = result3.getAsJsonObject("geometry").getAsJsonObject("location").get("lng").getAsDouble();

        listing1.setUrl(listing1Lat, listing1Lng);        
        listing2.setUrl(listing2Lat, listing2Lng);
        listing3.setUrl(listing3Lat, listing3Lng);

        // System.out.println("result 1: " + listing1.toString());        
        // System.out.println("result 2: " + listing2.toString());
        // System.out.println("result 3: " + listing3.toString());

        String returnMessage = "result 1: \n" + listing1.toString() +"\n" + "result 2: \n" + listing2.toString() + "\n" + "result 3: " + "\n" + listing3.toString();
        System.out.println(returnMessage);
        System.out.println(listing1Lat);        
        System.out.println(listing1Lng);


        return returnMessage;
    }

   
}

