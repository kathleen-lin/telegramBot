package practice.telebot;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jakarta.ws.rs.core.UriBuilder;

@Service
public class GetFoodSvc {
    
    @Autowired
    private WebClient webClient;

    @Value("${google.place.token}")
    private String googleToken;

    public List<Listing> getNearByFood(Double l1, Double l2){

        List<Listing> placesOpenNow = new LinkedList<>();

        String location = String.valueOf(l1) + "%2C" + String.valueOf(l2);

        URI uri = UriBuilder.fromUri("https://maps.googleapis.com/maps/api/place/nearbysearch/json").queryParam("keyword", "food").queryParam("location", location).queryParam("radius","1500").queryParam("opennow","true").queryParam("key",googleToken).build();
        
        String resp = webClient.get()
                        .uri(uri)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        
        JsonObject jsonResult = new Gson().fromJson(resp, JsonObject.class);

        JsonArray resultsArr = jsonResult.getAsJsonArray("results");

        // int numOfResult = 0;

        for (JsonElement r: resultsArr){
            // numOfResult++;
            // every result as jsonobject
            JsonObject jsonObjectPlace =  r.getAsJsonObject();
            Listing place = new Listing();
            Double lat = jsonObjectPlace.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble();
            Double lng = jsonObjectPlace.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble();
            // System.out.println(place.getLat() + place.getLng());

            place.setLat(lat);
            place.setLng(lng);
            place.setName(jsonObjectPlace.get("name").getAsString());
            place.setPlace_id(jsonObjectPlace.get("place_id").getAsString());
            place.setRating(jsonObjectPlace.get("rating").getAsString());
            place.setUrl(lat, lng);
            place.setVincity(jsonObjectPlace.get("vicinity").getAsString());
            
            System.out.println(place.toString());

            placesOpenNow.add(place);

            // if (numOfResult > 8){
            //     break;
            // }
        }

        System.out.println(placesOpenNow.size());
        


        return placesOpenNow;
    }

   
}

