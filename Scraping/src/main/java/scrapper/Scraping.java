package scrapper;

import com.google.gson.JsonArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Scraping implements BookingSource {

    public static Document getHtmlDocument(String url) {
        Document html = null;

        try {
            html = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get(); //nos conectamos a la url
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
        }
        return html;
    }

    @Override //LOCATIONS
    public List<Location> totalLocations(String URL) {
        List<Location> Locations = new ArrayList<>(); //array con la informacion de el hotel y su localizacion
        Elements locations = getHtmlDocument(URL).select("div.wrap-hotelpage-top"); //seleccionamos el bloque de informacion de el cabecero donde se expone dicha informacion
        for (Element elem : locations) {
            String name = elem.getElementsByClass("d2fee87262").text(); //extraemos nombre de hotel
            String location = elem.getElementsByClass("hp_address_subtitle").text(); //extraemos localizacion del mismo
            Locations.add(new Location(name, location)); //añadimos al arraylist de locations

            //para mostrar en pantalla
            System.out.println(name + "\n" + location +"\n");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        return Locations;
    }

    @Override //RATINGS
    public List<Ratings> totalRatings(String url) {
            List<Ratings> Ratings= new ArrayList<>(); //array Ratings donde seguarara toda la informacion de las valoraciones generales
            Map<String, String> hm = new HashMap<String, String>(); //almacenara una clave (tipo de clasificacion) y el valor (clasificacion en cuestion)
            Elements ratings = getHtmlDocument(url).select("div.a1b3f50dcd.b2fe1a41c3.a1f3ecff04.e187349485.d19ba76520"); //seleccionamos el bloque donde se encuentran los ratings
            try {
                for (Element rating : ratings) {
                    String name = rating.getElementsByClass("d6d4671780").text(); //extraemos el nombre del tipo de clasificacion
                    String punctuation = rating.getElementsByClass("ee746850b6 b8eef6afe1").text(); //extraemos su calificacion
                    hm.put(name, punctuation); //los almacenamos en el hashmap
                }
                Set<String> keys = hm.keySet(); //devolvera el conjunto de cada clave valor para recorrerlo a continuacion
                for (String key : keys) {
                    String value = hm.get(key); //extraemos el valor
                    Ratings.add(new Ratings(key, value)); //alacenamos clave y valor en Ratings

                    //para mostrar en pantalla
                    System.out.println("Tipo: " + key + "\n" + "Puntuacion: " + value + "\n");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
            return Ratings;
    }



    @Override //SERVICIOS
    public List<Service>totalServices(String url) {
        List<Service> servicies = new ArrayList<>(); //array con los servicios
        Elements services = getHtmlDocument(url).select("div.hotel-facilities-group"); //entramos en el bloque de los servicios
        for (Element elem : services) {
            String service = elem.getElementsByClass("bui-title").text(); //extraemos el texto del nombre del servicio
            String adittional_service = elem.getElementsByClass("bui-list__description").text(); //extraemos el texto de informacion adicional del servicio
            servicies.add(new Service(service.trim(), adittional_service.trim())); //añadimos los servicios separados por espacios al arraylist services

            //para mostrar en pantalla
            System.out.println("Servicio: " + service + "\n" + "Informacion adicional: " + adittional_service + "\n");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        return servicies;
    }

    @Override //COMENTARIOS
    public List<Comments> totalComments(String URLComments) {
        List<Comments> comments = new ArrayList<>();
        Document doc = getHtmlDocument(URLComments);
        if (doc != null) {
            //seleccionamos el comentario y recorreremos a partid de aqui todos los elementos que nos interesen del comentario de cada persona
            Elements commentary = doc.select("li.review_item.clearfix");
            for (Element elem : commentary) {

                //recorremos los usuarios
                JsonArray name = new JsonArray(); //array name con nombre del usuario
                JsonArray country = new JsonArray(); //array country con pais del usuario
                Elements users = elem.select("div.review_item_reviewer"); //entramos en el bloque de informacion del usuario
                for (Element user : users) {
                    String user_name = user.getElementsByClass("reviewer_name").text(); //en forma de texto conseguimos el nombre del usuario
                    String user_country = user.getElementsByClass("reviewer_country").text(); //en forma de texto conseguimos el pais del usuario
                    //los añadimos a los arrays nombre y pais
                    name.add(user_name);
                    country.add(user_country);
                }

                //recorremos las valoraciones
                JsonArray punctuation = new JsonArray(); //array punctuation donde se almacenara la puntuacion del usuario
                JsonArray review = new JsonArray(); //array review donde se almacenara las reseñas
                Elements punctuations = elem.select("div.review_item_header_score_container"); //entramos en el bloque de informacion de puntuaciones
                for (Element punctuation_number : punctuations) {
                    String user_punctuation = punctuation_number.text(); //conseguimos la puntuacion del usuario
                    punctuation.add(user_punctuation); //la añadimos al array punctuation
                }

                //recorremos las reseñas
                Elements reviews = elem.select("div.review_item_header_content"); //entramos en el bloque de informacion de reseñas
                for (Element r : reviews) {
                    String user_review = r.text(); //conseguimos la reseña del usuario en texto
                    review.add(user_review); //almacenamos en array review
                }

                //recorremos los aspectos positivos y negativos
                JsonArray positive = new JsonArray(); //array de elementos positivos de la reseña
                JsonArray negative = new JsonArray(); //array de elementos negativos de la reseña
                JsonArray days = new JsonArray(); //array de fecha de alojamiento de el usuario
                Elements annotations = elem.select("div.review_item_review_content"); //las anotaciones negativas y positivas de la reseña (donde tambien se encuentra la fecha de alojamiento)
                for (Element annotation : annotations) {
                    String user_positive = annotation.getElementsByClass("review_pos").text(); //conseguimos anotaciones positivas
                    String user_negative = annotation.getElementsByClass("review_neg").text(); //negativas
                    String user_staydate = annotation.getElementsByClass("review_staydate").text(); //la fecha de su estancia
                    //las añadimos a su respectivo arraylist
                    positive.add(user_positive);
                    negative.add(user_negative);
                    days.add(user_staydate);
                }
                //mostraremos todos los elementos scrapeados:
                System.out.println("Nombre: " + name + "\n" +  "País: " + country+ "\n" + "Puntuacion: " + punctuation+ "\n" + "Reseña: " + review + "\n" + "Aspectos positivos: " + positive + "\n" + "Aspectos negativos: " + negative + "\n" + "¿Cuando se alojó?: " + days);
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                comments.add(new Comments(name.getAsString(),
                        country.getAsString(),
                        punctuation.getAsString(),
                        review.getAsString(),
                        positive.getAsString(),
                        negative.getAsString(),
                        days.getAsString()));
            }
        }
        return comments;
    }
}