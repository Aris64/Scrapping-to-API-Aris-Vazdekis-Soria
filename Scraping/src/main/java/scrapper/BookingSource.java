package scrapper;

import java.util.List;

//esta interfaz se encargara de conectar las funciones que extraen la informacion de la clase "Scraping" y las unira con la api rest. La llamada la hara el main, en la misma linea que llama al scraping
public interface BookingSource {
        List<Ratings> totalRatings(String url) throws Exception;
        List<Location> totalLocations(String url) throws Exception;
        List<Comments> totalComments(String url) throws Exception;
        List<Service> totalServices(String url) throws Exception;

}
