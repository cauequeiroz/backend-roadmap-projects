package helper;

import domain.Task;
import domain.TaskList;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class JsonHelper {
    private final String file;

    public JsonHelper(String file) {
        this.file = file;
    }

    public TaskList getTaskListFromFile() {
        Path path = Paths.get("tasks.json");

        if (!Files.exists(path)) {
            System.out.println("File not found.");
            return new TaskList();
        }

        try (Stream<String> fileStream = Files.lines(path)) {
            List<String> lines = fileStream.toList();
            int nextId = this.getNumberValue(lines.get(1));
            TaskList taskList = new TaskList(nextId);
            int position = 2;

            while(position < lines.size()) {
                if (lines.get(position).contains("\"id\":")) {
                    Task task = new Task(
                        this.getNumberValue(lines.get(position++)),
                        this.getStringValue(lines.get(position++)),
                        this.getNumberValue(lines.get(position++)),
                        this.getStringValue(lines.get(position++)),
                        this.getStringValue(lines.get(position))
                    );

                    taskList.add(task);
                }

                position++;
            }

            return taskList;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new TaskList();
    }

    public void save(TaskList taskList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        stringBuilder.append("\t\"nextId\": " + taskList.getNextId() + ",\n");
        stringBuilder.append("\t\"items\": [\n");

        Iterator<Task> iterator = taskList.list().iterator();

        while (iterator.hasNext()) {
            Task task = iterator.next();

            stringBuilder.append("\t\t{\n");
            stringBuilder.append(String.format("\t\t\t\"id\": %d,\n", task.getId()));
            stringBuilder.append(String.format("\t\t\t\"description\": \"%s\",\n", task.getDescription()));
            stringBuilder.append(String.format("\t\t\t\"status\": %d,\n", task.getStatus().ordinal()));
            stringBuilder.append(String.format("\t\t\t\"createdAt\": \"%s\",\n", task.getCreatedAt()));
            stringBuilder.append(String.format("\t\t\t\"updatedAt\": \"%s\"\n", task.getUpdatedAt()));

            if (iterator.hasNext()) {
                stringBuilder.append("\t\t},\n");
            } else {
                stringBuilder.append("\t\t}\n");
            }
        }

        stringBuilder.append("\t]\n");
        stringBuilder.append("}\n");

        try (FileWriter writer = new FileWriter(this.file)) {
            writer.write(stringBuilder.toString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int getNumberValue(String line) {
        return Integer.parseInt(line.replaceAll("\\D", ""));
    }

    private String getStringValue(String line) {
        return line.replaceAll("^.*?:\\s*\"([^\"]*)\".*$", "$1");
    }
}
