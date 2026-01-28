package memento;

import java.util.Stack;

// Memento Pattern = capture and restore an object's internal state
// without exposing its internals to other classes.

// Memento (snapshot object)
class EditorMemento {
    private final String text;

    public EditorMemento(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

// Originator (the object whose state we save/restore)
class TextEditor {
    private String text = "";

    public void type(String newText) {
        text += newText;
    }

    public String getText() {
        return text;
    }

    public EditorMemento save() {
        return new EditorMemento(text);
    }

    public void restore(EditorMemento memento) {
        this.text = memento.getText();
    }
}

// Caretaker (stores history of mementos)
class History {
    private final Stack<EditorMemento> stack = new Stack<>();

    public void push(EditorMemento m) {
        stack.push(m);
    }

    public EditorMemento pop() {
        if (stack.isEmpty()) return null;
        return stack.pop();
    }
}

public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        History history = new History();

        // Save initial state
        history.push(editor.save());

        editor.type("Hello");
        System.out.println("Text: " + editor.getText());
        history.push(editor.save());

        editor.type(" World");
        System.out.println("Text: " + editor.getText());
        history.push(editor.save());

        editor.type(" !!!");
        System.out.println("Text: " + editor.getText());

        // Undo steps (restore previous snapshots)
        System.out.println("\nUndo 1");
        EditorMemento m1 = history.pop();
        editor.restore(m1);
        System.out.println("Text: " + editor.getText());

        System.out.println("\nUndo 2");
        EditorMemento m2 = history.pop();
        editor.restore(m2);
        System.out.println("Text: " + editor.getText());

        System.out.println("\nUndo 3");
        EditorMemento m3 = history.pop();
        editor.restore(m3);
        System.out.println("Text: " + editor.getText());
    }
}
