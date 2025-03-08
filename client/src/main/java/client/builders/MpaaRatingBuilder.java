package client.builders;

import client.managers.ScannerManager;
import client.managers.StreamManager;
import general.objects.MpaaRating;

import java.util.Arrays;

public class MpaaRatingBuilder extends Builder {
    public MpaaRatingBuilder(StreamManager stream, ScannerManager scanner) {
        super(stream, scanner);
    }

    @Override
    public MpaaRating build() {
        return readMpaaRating();
    }

    /**
     * Метод для чтения Мпаа Рейтинга
     *
     * @return Найденный MpaaRating
     */
    private MpaaRating readMpaaRating() {
        while (true) {
            stream.print("> Введите Мпаа Рейтинг " + Arrays.toString(MpaaRating.values()) + ":\n$ ");
            String res = scanner.nextLine().trim();
            if (res.isEmpty()) {
                return null;
            }
            try {
                return MpaaRating.checkOf(res);
            } catch (IllegalArgumentException e) {
                stream.printErr("Введенный Мпаа рейтинг не является одним из предложенных\n");
                stream.print("* Повторная попытка ввода\n");
            }
        }
    }
}
