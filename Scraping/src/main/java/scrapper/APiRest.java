package scrapper;

import java.util.List;

import static spark.Spark.*;

// http://localhost:4567/ ------> hostname y port para las solicitudes web
public class APiRest {
    public void startServer() {
    port(4567);
    staticFiles.location("/public");
    }

    private static Scraping scraping = new Scraping();

    public static void main(String[] args) {

        //totalLocations peticion GET
        get("/hotels/:name ", (request, response) -> { //path para la peticion de la informacion de locations
            response.type("application/json");
            String url = "https://www.booking.com/hotel/es/club-magic-life-fuerteventura-imperial.es.html#tab-main";
            List<Location> locations = scraping.totalLocations(url); //llamamos al scrapper para poder devolverle la informacion a la solicitud get de la funcion totallocations
            System.out.println(locations);
            return locations;
        }, new JsonTransformer()); //llama a clase "JsonTransformer para convertir la informacion a formato json (el formato exigido por la solicitud)


        //totalRatings peticion GET
        get("/hotels/:name/ratings", (request, response) -> { //path para la peticion de la informacion de ratings
            response.type("application/json");
            String url = "https://www.booking.com/hotel/es/club-magic-life-fuerteventura-imperial.es.html#tab-main";
            List<Ratings> ratings = scraping.totalRatings(url); //llamamos al scrapper para poder devolverle la informacion a la solicitud get de la funcion totalratings
            System.out.println(ratings);
            return ratings;
        }, new JsonTransformer()); //llama a clase "JsonTransformer para convertir la informacion a formato json (el formato exigido por la solicitud)

        //comments peticion GET
        get("/hotels/:name/comments", (request, response) -> { //path para la peticion de la informacion de los comentarios
            response.type("application/json");
            String url = "https://www.booking.com/reviews/es/hotel/club-magic-life-fuerteventura-imperial.es.html?label=gen173nr-1BCA0oRkImY2x1Yi1tYWdpYy1saWZlLWZ1ZXJ0ZXZlbnR1cmEtaW1wZXJpYWxIM1gEaEaIAQGYAQq4ARfIAQzYAQHoAQGIAgGoAgO4AuPr0Z0GwAIB0gIkZmIxMWM2YTMtMDNkMy00ZjQ2LThmOWEtYjdiMWEyMjI1ZmRh2AIF4AIB&sid=e05abe58545cf13950a11cfa606b4d4c&customer_type=total&hp_nav=0&keep_landing=1&order=featuredreviews&page=1&r_lang=es&rows=75&#tab-main";
            List<Comments> comments = scraping.totalComments(url); //llamamos al scrapper para poder devolverle la informacion a la solicitud get de la funcion totalcomments
            System.out.println(comments);
            return comments;
        }, new JsonTransformer()); //llama a clase "JsonTransformer para convertir la informacion a formato json (el formato exigido por la solicitud)

        //totalServices peticion GET
        get("/hotels/:name/services", (request, response) -> { //path para la peticion de la informacion de los servicios
            response.type("application/json");
            String url = "https://www.booking.com/hotel/es/club-magic-life-fuerteventura-imperial.es.html#tab-main";
            List<Service> services = scraping.totalServices(url); //llamamos al scrapper para poder devolverle la informacion a la solicitud get de la funcion totalservices
            System.out.println(services);
            return services;
        }, new JsonTransformer()); //llama a clase "JsonTransformer para convertir la informacion a formato json (el formato exigido por la solicitud)
    }
}

