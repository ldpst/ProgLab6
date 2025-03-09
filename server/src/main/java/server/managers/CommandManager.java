package server.managers;

import server.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commands = new HashMap<String, Command>();
    private final CollectionManager collectionManager;

    public CommandManager(CollectionManager collectionManager) {
        commands.put("show", new Show(collectionManager));
        commands.put("add", new Add(collectionManager));
        commands.put("update", new Update(collectionManager));
        commands.put("remove_by_id", new RemoveById(collectionManager));
        this.collectionManager = collectionManager;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
