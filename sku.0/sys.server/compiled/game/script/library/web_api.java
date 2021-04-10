package script.library;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Web API
 * Methods for sending scripted content from the game server to the web via POST
 *
 * SWG Source Addition - 2021
 * Authors: Aconite
 * Adapted from work by:
 * https://github.com/thomasdarimont
 */
public class web_api extends script.base_script {

    public web_api()
    {
    }

    private static final boolean API_ENABLED = utils.checkConfigFlag("GameServer", "scriptWebApiEnabled");

    /**
     * @param url the URL of the PHP script receiving the data
     * @param data a map with the name of the POST field as the key and the field data as the value
     *
     * Simple Example:
     * Map<Object, Object> data = new HashMap<>();
     * data.put("player", getPlayerName(somePlayer));
     * web_api.sendDataAsPost("swg.com/someScript.php", data);
     */
    public static void sendDataAsPost(String url, Map<Object, Object> data) {
        if(!API_ENABLED) {
            return;
        }
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(buildFormDataFromMap(data))
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body);
        } catch (Exception e) {
           WARNING("Java Exception in web_api.sendDataAsPost(): "+e.getMessage());
        }
    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

}
