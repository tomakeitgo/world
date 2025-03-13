import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");


        ArrayList<CompletableFuture<HttpResponse<String>>> list = new ArrayList<>();
        var client = HttpClient.newHttpClient();
        for (int i = 0; i < 10; i++) {
            list.add(client.sendAsync(
                    HttpRequest.newBuilder()
                            .header("magic-token", "secret")
                            .uri(URI.create("https://api.tomakeitgo.com/move-ship"))
                            .POST(HttpRequest.BodyPublishers.ofString("{ \"playerId\": \"f2326334-370b-4c33-8593-c6bd7424160c\", \"shipId\": \"0eb5a7f2-7d73-464f-a253-367ebe637932\", \"x\": 0, \"y\": 0, \"z\": 1 }"))
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            ));

        }

        list.forEach(i -> {
            try {
                System.out.println(i.get(1, TimeUnit.HOURS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
