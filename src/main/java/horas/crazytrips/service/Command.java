package horas.crazytrips.service;

public interface Command {
    void execute();
    void undo();
}