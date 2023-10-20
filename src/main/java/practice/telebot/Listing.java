package practice.telebot;

public class Listing {
    
    private String name;
    private String rating;
    private String vicinity;
    
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
    @Override
    public String toString() {
        return "Name: " + name + "\n" + "Rating: " + rating + "\n" + "Address: " + vicinity + "\n";
    }

    
    
}
