import domain.GithubEvent;
import service.GithubService;

import java.util.Arrays;

public class GithubUserActivity {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException("You must provide a username as first parameter.");
            }

            if (args[0].isEmpty() || args[0].isBlank()) {
                throw new IllegalArgumentException("You must provide a valid username as first parameter.");
            }

            String username = args[0];
            GithubService githubService = new GithubService(username);
            GithubEvent[] events = githubService.fetchActivity();

            System.out.println("Output:");
            Arrays.stream(events)
                    .forEach(event ->
                            System.out.println("- " + githubService.translateEvent(event))
                    );
        } catch (Exception e) {
            System.out.printf("[ERROR: %s]", e.getMessage());
        }
    }
}
