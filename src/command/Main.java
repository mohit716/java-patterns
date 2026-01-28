package command;

import java.util.ArrayList;
import java.util.List;

// Command Pattern = encapsulate a request as an object.
// So you can store it, queue it, run it later, undo it, etc.

interface Command {
    void execute();
    void undo();
}

// Receiver (the real object that does the work)
class Light {
    private boolean isOn = false;

    public void on() {
        isOn = true;
        System.out.println("Light is ON");
    }

    public void off() {
        isOn = false;
        System.out.println("Light is OFF");
    }

    public boolean isOn() {
        return isOn;
    }
}

// Concrete Commands
class LightOnCommand implements Command {
    private final Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }

    public void undo() {
        light.off();
    }
}

class LightOffCommand implements Command {
    private final Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.off();
    }

    public void undo() {
        light.on();
    }
}

// Invoker (button / remote)
class RemoteControl {
    private final List<Command> history = new ArrayList<>();

    public void press(Command cmd) {
        cmd.execute();
        history.add(cmd); // store it so we can undo later
    }

    public void undoLast() {
        if (history.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }
        Command last = history.remove(history.size() - 1);
        last.undo();
    }
}

public class Main {
    public static void main(String[] args) {
        Light light = new Light();

        Command on = new LightOnCommand(light);
        Command off = new LightOffCommand(light);

        RemoteControl remote = new RemoteControl();

        remote.press(on);   // Light ON
        remote.press(off);  // Light OFF

        remote.undoLast();  // Undo OFF => Light ON
        remote.undoLast();  // Undo ON  => Light OFF
        remote.undoLast();  // Nothing to undo
    }
}
