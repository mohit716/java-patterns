package solid.single_responsibility_principle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// ---------------------------------------------------------------------------
// SRP Example: Todo list — each class has one job.
// ---------------------------------------------------------------------------
// Task: data only (title, done).
// TaskValidator: validate task before add.
// TaskStore: manage the in-memory list (add, mark done, get incomplete).
// TaskFileStorage: persist tasks to file / load from file.
// ---------------------------------------------------------------------------

class Task {
    private final String title;
    private boolean done;

    public Task(String title) {
        this.title = title;
        this.done = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}

/** One job: validate a task. Changes only when validation rules change. */
class TaskValidator {
    public boolean isValid(Task task) {
        if (task == null) return false;
        String title = task.getTitle();
        if (title == null || title.trim().isEmpty()) return false;
        if (title.length() > 200) return false;
        return true;
    }
}

/** One job: manage the in-memory list of tasks. Changes only when list behavior changes. */
class TaskStore {
    private final List<Task> tasks = new ArrayList<>();

    public void add(Task task) {
        tasks.add(task);
    }

    public void markDoneByTitle(String title) {
        if (title == null) return;
        for (Task t : tasks) {
            if (title.equals(t.getTitle())) {
                t.setDone(true);
                return;
            }
        }
    }

    public List<String> getIncompleteTitles() {
        return tasks.stream()
                .filter(t -> !t.isDone())
                .map(Task::getTitle)
                .collect(Collectors.toList());
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}

/** One job: persist tasks to file. Changes only when file format or path changes. */
class TaskFileStorage {
    private static final String FILENAME = "todolist.txt";

    public void save(List<Task> tasks) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Task t : tasks) {
            sb.append(t.getTitle()).append("|").append(t.isDone()).append("\n");
        }
        Files.writeString(Paths.get(FILENAME), sb.toString());
        System.out.println("Tasks saved to: " + Paths.get(FILENAME).toAbsolutePath());
    }

    public List<Task> load() throws IOException {
        Path path = Paths.get(FILENAME);
        if (!Files.exists(path)) return new ArrayList<>();
        List<String> lines = Files.readAllLines(path);
        List<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            int sep = line.lastIndexOf("|");
            if (sep < 0) continue;
            String title = line.substring(0, sep);
            boolean done = Boolean.parseBoolean(line.substring(sep + 1));
            Task t = new Task(title);
            t.setDone(done);
            tasks.add(t);
        }
        return tasks;
    }
}

public class TodoListSRPExample {
    public static void main(String[] args) throws IOException {
        TaskValidator validator = new TaskValidator();
        TaskStore store = new TaskStore();
        TaskFileStorage storage = new TaskFileStorage();

        // Add tasks (validate first)
        String[] titles = { "Buy groceries", "Call mom", "Finish report" };
        for (String title : titles) {
            Task task = new Task(title);
            if (!validator.isValid(task)) {
                System.out.println("Invalid task: " + title);
                continue;
            }
            store.add(task);
        }
        System.out.println("Added 3 tasks.");

        // Mark one done
        store.markDoneByTitle("Call mom");
        System.out.println("Marked 'Call mom' done.");

        // Show incomplete
        List<String> incomplete = store.getIncompleteTitles();
        System.out.println("Incomplete: " + incomplete);

        // Save to file
        storage.save(store.getAllTasks());
    }
}
