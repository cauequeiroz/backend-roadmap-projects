package domain;

import exception.TaskNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;
    private int nextId;

    public TaskList() {
        this(1);
    }

    public TaskList(int nextId) {
        this.tasks = new ArrayList<>();
        this.nextId = nextId;
    }

    public int getNextId() {
        return this.nextId;
    }

    public void add(String description) {
        if (description.isBlank()) {
            throw new IllegalArgumentException("You must pass a description at TaskList add.");
        }
        Task task = new Task(this.nextId, description);
        this.tasks.add(task);
        this.nextId++;

        System.out.println("Task added successfully!");
        System.out.println(task);
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public void update(int id, String description) throws TaskNotFoundException {
        if (description.isBlank()) {
            throw new IllegalArgumentException("You must pass a description at TaskList update.");
        }

        Task task = this.findTaskById(id);
        task.update(description);

        System.out.println("Task updated successfully!");
        System.out.println(task);
    }

    public void markInProgress(int id) throws TaskNotFoundException {
        Task task = this.findTaskById(id);
        task.markInProgress();

        System.out.println("Task marked as in-progress successfully!");
        System.out.println(task);
    }

    public void markDone(int id) throws TaskNotFoundException {
        Task task = this.findTaskById(id);
        task.markComplete();

        System.out.println("Task marked as done successfully!");
        System.out.println(task);
    }

    public void delete(int id) throws TaskNotFoundException {
        Task task = this.findTaskById(id);
        this.tasks.remove(task);

        System.out.println("Task removed successfully!");
        System.out.println(task);
    }

    public List<Task> list() {
        return this.tasks;
    }

    public List<Task> list(Status status) {
        return this.tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
    }

    private Task findTaskById(int id) throws TaskNotFoundException {
        Task task = this.tasks.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);

        if (task == null) {
            throw new TaskNotFoundException("Cannot find Task with id " + id);
        }

        return task;
    }
}
