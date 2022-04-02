package vttp2022.ssf.assessment.retest.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.assessment.retest.Model.Game;

@Service
public class SearchService {
    
    private final String apiKey = "d8f7fdf4d3a94bef877361e14ac10aea";
    private final String apiURL = "https://api.rawg.io/api/games";

    public List<Game> search(String searchString, Integer count) {

        List<Game> resultList = new ArrayList<>();

        String url = UriComponentsBuilder
            .fromUriString(apiURL)
            .queryParam("key", apiKey)
            .queryParam("search", searchString)
            .queryParam("page_size", count.toString())
            .toUriString();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.getForEntity(url, String.class);
        InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject object = reader.readObject();
        JsonArray array = object.getJsonArray("results");

        if (array.isEmpty()) {
            return Collections.emptyList();
        } else {
            array.forEach(v -> {
                JsonObject o = v.asJsonObject();
                Game myGame = new Game();
                myGame.setName(o.getString("name"));
                myGame.setRating(Float.parseFloat(o.get("rating").toString()));
                myGame.setBackgroundImage(o.getString("background_image"));
                resultList.add(myGame);
            });
            return resultList;
        }
    }
}
