# Task Tracker CLI

A simple command-line application built with Java to manage tasks and track their progress.

Tasks are stored locally in a JSON file and can be created, updated, deleted, and filtered by status.

**Project:** [Task Tracker — roadmap.sh](https://roadmap.sh/projects/task-tracker)  
**Level:** Beginner

## Usage

### Add a task

```bash
task-cli add "Buy groceries"
# Output: Task added successfully (ID: 1)
```

### Update or delete a task

```bash
task-cli update 1 "Buy groceries and cook dinner"
task-cli delete 1
```

### Update task status

```bash
task-cli mark-in-progress 1
task-cli mark-done 1
```

### List tasks

```bash
# List all tasks
task-cli list

# List tasks by status
task-cli list done
task-cli list todo
task-cli list in-progress
```
