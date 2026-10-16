package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.GithubEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GithubService {
    private String username;

    public GithubService(String username) {
        this.username = username;
    }

    public GithubEvent[] fetchActivity() {
        System.out.printf("Loading events for username: %s...\n\n", username);
        String url = String.format("https://api.github.com/users/%s/events", username);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Call to Github API returns " + response.statusCode());
            }

            String json = response.body();
            Gson gson = new Gson();
            GithubEvent[] events = gson.fromJson(json, GithubEvent[].class);

            return events;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String translateEvent(GithubEvent event) {
        String repositoryName = event.getRepo().getName();
        JsonObject payload = event.getPayload();

        String action = switch (event.getType()) {
            case "PullRequestReviewCommentEvent" ->
                    "created a pull request review comment on";
            case "PullRequestReviewEvent", "PullRequestEvent" ->
                    payload.get("action").getAsString() + " a pull request on";
            case "PushEvent" ->
                    "pushed a commit to";
            case "CreateEvent" ->
                    "created a " + payload.get("ref_type").getAsString() + " on";
            case "DeleteEvent" ->
                    "deleted a " + payload.get("ref_type").getAsString() + " on";
            case "IssueCommentEvent" ->
                    "created a issue comment on";
            case "WatchEvent" ->
                    "started";
            default ->
                    "[event " + event.getType() + " unknown]";
        };

        return action + " " + repositoryName;
    }
}
