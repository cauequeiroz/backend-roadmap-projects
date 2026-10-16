package domain;

import com.google.gson.JsonObject;

public class GithubEvent {
    private String type;
    private GithubRepo repo;
    private JsonObject payload;

    public GithubEvent(String type, GithubRepo repo, JsonObject payload) {
        this.type = type;
        this.repo = repo;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public GithubRepo getRepo() {
        return repo;
    }

    public JsonObject getPayload() {
        return payload;
    }
}
