package server.commands;

import general.objects.Movie;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.builders.MovieBuilder;
import server.managers.CollectionManager;
import server.requests.Request;
import server.response.Response;
import server.response.ResponseType;
import server.server.UDPDatagramChannel;

import java.io.IOException;

public class Update extends Command {
    private final CollectionManager collectionManager;
    private final UDPDatagramChannel channel;

    private final Logger logger = LogManager.getLogger(Update.class);

    public Update(CollectionManager collectionManager, UDPDatagramChannel channel) {
        super("update id {element} ", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.channel = channel;
    }

    @Override
    public Response execute(Request request) throws IOException {
        logger.debug("Выполнение команды...");
        String[] message = request.getMessage().split(" ");
        if (message.length < 2) {
            return new Response(RED + "Неверный формат команды\n" + RESET, ResponseType.ERROR);
        }
        try {
            int id = Integer.parseInt(message[1]);
            if (collectionManager.checkIfIdExists(id)) {
                Movie newMovie = new MovieBuilder(channel, request.getClientAddress(), logger).build();
                collectionManager.updateById(id, newMovie);
                logger.debug("Команда выполнена");
                return new Response(GREEN + "Элемент с id " + id + " успешно обновлен\n" + RESET, ResponseType.ERROR);
            } else {
                return new Response(RED + "Элемента с данным id не существует\n" + RESET, ResponseType.ERROR);
            }
        } catch (NumberFormatException e) {
            return new Response(RED + "Введённый id не является целым числом\n" + RESET, ResponseType.ERROR);
        }
    }
}
