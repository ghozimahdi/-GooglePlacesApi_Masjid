package bcode.pe.hu.googleplacesapi.MODEL;

/**
 * Created by bcode.pe.hu on 11/10/16.
 * Dev : Ghozi mahdi
 * Wed Dev : Ghozi Mahdi
 */
public class DataGooglePlaces {
    String geo_lat,geo_lng,places_id,id,places_name,places_vicinity;
    int places_rating;

    public String getGeo_lat() {
        return geo_lat;
    }

    public void setGeo_lat(String geo_lat) {
        this.geo_lat = geo_lat;
    }

    public String getGeo_lng() {
        return geo_lng;
    }

    public void setGeo_lng(String geo_lng) {
        this.geo_lng = geo_lng;
    }

    public String getPlaces_id() {
        return places_id;
    }

    public void setPlaces_id(String places_id) {
        this.places_id = places_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaces_name() {
        return places_name;
    }

    public void setPlaces_name(String places_name) {
        this.places_name = places_name;
    }

    public String getPlaces_vicinity() {
        return places_vicinity;
    }

    public void setPlaces_vicinity(String places_vicinity) {
        this.places_vicinity = places_vicinity;
    }

    public int getPlaces_rating() {
        return places_rating;
    }

    public void setPlaces_rating(int places_rating) {
        this.places_rating = places_rating;
    }
}
