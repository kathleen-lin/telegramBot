package practice.telebot;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.ws.rs.core.UriBuilder;

@Service
public class GetFoodSvc {
    
    @Autowired
    private WebClient webClient;


    public String getNearByFood(Double l1, Double l2, String token){
        String location = String.valueOf(l1) + "%2C" + String.valueOf(l2);

        URI uri = UriBuilder.fromUri("https://maps.googleapis.com/maps/api/place/nearbysearch/json").queryParam("keyword", "food").queryParam("location", location).queryParam("radius","1500").queryParam("opennow","true").queryParam("key",token).build();
        
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

        // System.out.println("result 1: " + listing1.toString());        
        // System.out.println("result 2: " + listing2.toString());
        // System.out.println("result 3: " + listing3.toString());

        String returnMessage = "result 1: \n" + listing1.toString() + "\n" + "result 2: \n" + listing2.toString() + "\n" + "result 3: " + "\n" + listing3.toString();
        System.out.println(returnMessage);

        return returnMessage;
    }

    public void testAutowire() {
        System.out.println("testing autowire");
    }
}

