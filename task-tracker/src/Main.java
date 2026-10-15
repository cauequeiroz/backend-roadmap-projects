import domain.TaskList;
import helper.JsonHelper;
import service.TaskListService;

public class Main {
    public static void main(String[] args) {
        JsonHelper jsonHelper = new JsonHelper("tasks.json");
        TaskList taskList = jsonHelper.getTaskListFromFile();
        TaskListService service = new TaskListService(taskList);

        service.processCommands(args);
        jsonHelper.save(taskList);
    }
}