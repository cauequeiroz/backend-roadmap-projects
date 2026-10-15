package service;

import domain.Status;
import domain.TaskList;
import helper.ArgumentValidator;

public class TaskListService {
    private TaskList taskList;

    public TaskListService(TaskList taskList) {
        this.taskList = taskList;
    }

    public void processCommands(String[] args) {
        String command = args[0];

        try {
            switch (command) {
                case "add":
                    taskList.add(
                            ArgumentValidator.checkValidString(args[1], "You must pass a valid Task description.")
                    );
                    break;
                case "update":
                    taskList.update(
                            ArgumentValidator.checkValidNumber(args[1], "You must pass a valid Task id."),
                            ArgumentValidator.checkValidString(args[2], "You must pass a valid Task description.")
                    );
                    break;
                case "delete":
                    taskList.delete(
                            ArgumentValidator.checkValidNumber(args[1], "You must pass a valid Task id.")
                    );
                    break;
                case "mark-in-progress":
                    taskList.markInProgress(
                            ArgumentValidator.checkValidNumber(args[1], "You must pass a valid Task id.")
                    );
                    break;
                case "mark-done":
                    taskList.markDone(
                            ArgumentValidator.checkValidNumber(args[1], "You must pass a valid Task id.")
                    );
                    break;
                case "list":
                    if (args.length >= 2) {
                        Status status = ArgumentValidator.checkValidStatus(args[1], "You must pass a valid Task status.");
                        System.out.println("All tasks with status "+status+":");
                        taskList.list(status).forEach(System.out::println);
                    } else {
                        System.out.println("All tasks:");
                        taskList.list().forEach(System.out::println);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
