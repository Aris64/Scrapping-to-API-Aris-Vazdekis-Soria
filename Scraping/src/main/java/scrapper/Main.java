package scrapper;

public class Main {
    public static void main(String[] args) {
        Scraping scraper = new Scraping();

        //He utilizado el hotel Club Magic Life Fuerteventura de la pagina booking.com para el desarrollo del scraping
        String URL = "https://www.booking.com/hotel/es/club-magic-life-fuerteventura-imperial.es.html#tab-main";
        String URLComments = "https://www.booking.com/reviews/es/hotel/club-magic-life-fuerteventura-imperial.es.html?label=gen173nr-1BCA0oRkImY2x1Yi1tYWdpYy1saWZlLWZ1ZXJ0ZXZlbnR1cmEtaW1wZXJpYWxIM1gEaEaIAQGYAQq4ARfIAQzYAQHoAQGIAgGoAgO4AuPr0Z0GwAIB0gIkZmIxMWM2YTMtMDNkMy00ZjQ2LThmOWEtYjdiMWEyMjI1ZmRh2AIF4AIB&sid=e05abe58545cf13950a11cfa606b4d4c&customer_type=total&hp_nav=0&keep_landing=1&order=featuredreviews&page=1&r_lang=es&rows=75&#tab-main";

        scraper.totalLocations(URL); //llama al scraper y le cede la url
        scraper.totalServices(URL);
        scraper.totalRatings(URL);
        scraper.totalComments(URLComments);

        APiRest scrapingAPI = new APiRest();
        scrapingAPI.main(args); //llama a la api rest para poder generar las peticiones web
    }
}


