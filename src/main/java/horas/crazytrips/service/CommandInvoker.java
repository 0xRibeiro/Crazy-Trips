package horas.crazytrips.service;

import org.springframework.stereotype.Service;
import java.util.Stack;

@Service
public class CommandInvoker {
    private final Stack<Command> commandHistory = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        } else {
            throw new IllegalStateException("No commands to undo");
        }
    }

    public boolean hasCommands() {
        return !commandHistory.isEmpty();
    }
}