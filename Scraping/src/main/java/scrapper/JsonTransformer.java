package scrapper;
import com.google.gson.Gson;
import spark.ResponseTransformer;


public class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        //esta funcion se encargara de transformar los objetos a json, para entregarlos a la peticion get de la API en formato json
        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }
}
