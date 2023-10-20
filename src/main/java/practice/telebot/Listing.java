package practice.telebot;

public class Listing {
    
    private String name;
    private String rating;
    private String vicinity;
    private String place_id;
    private String url;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getVincity() {
        return vicinity;
    }
    public void setVincity(String vincity) {
        this.vicinity = vincity;
    }

    public String getPlace_id() {
    return place_id;
    }
    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String url() {
    return url;
    }
    public void setUrl(Double lat, Double lng) {
        this.url = "https://www.google.com/maps/search/?api=1&query=" + lat.toString() + "%2C" + lng.toString() + "&" + "query_place_id=" + this.place_id;
    }
    @Override
    public String toString() {
        return "Name: " + name + "\n" + "Rating: " + rating + "\n" + "Address: " + vicinity + "\n" + "Url: " + url + "\n";
    }

    

}


    
    