package client.builders;

import client.managers.ScannerManager;
import client.managers.StreamManager;
import general.objects.Person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonBuilder extends Builder {
    public PersonBuilder(StreamManager stream, ScannerManager scanner) {
        super(stream, scanner);
    }

    @Override
    public Person build() {
        return new Person(
                readName(),
                readBirthday(),
                readWeight(),
                readPassportID()
        );
    }

    /**
     * Метод для чтения имени
     *
     * @return Найденная строка
     */
    private String readName() {
        while (true) {
            stream.print("> Введите имя человека:\n$ ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                stream.printErr("Имя не должно быть пустым\n");
                stream.print("* Повторная попытка ввода\n");
            } else {
                return name;
            }
        }
    }

    /**
     * Метод для чтения даты рождения по формату
     *
     * @return Найденная дата
     */
    private Date readBirthday() {
        while (true) {
            String format = "dd:MM:yyyy";
            stream.printf("> Введите дату рождения человека (формата %s):\n$ ", format);
            String res = scanner.nextLine().trim();
            if (res.isEmpty()) {
                return null;
            }
            String[] split = res.split(":");
            if (split.length != 3) {
                stream.printErr("Введенные данные неверного формата\n");
                stream.print("* Повторная попытка ввода\n");
            } else {
                int day, month, year;
                try {
                    day = Integer.parseInt(split[0]);
                    month = Integer.parseInt(split[1]);
                    year = Integer.parseInt(split[2]);
                    if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1) {
                        stream.printErr("Введена невозможная дата\n");
                        stream.print("* Повторная попытка ввода\n");
                    } else {
                        DateFormat dateFormat = new SimpleDateFormat(format);
                        try {
                            return dateFormat.parse(res);
                        } catch (ParseException e) {
                            stream.printErr("Введенные данные неверного формата\n");
                            stream.print("* Повторная попытка ввода\n");
                        }

                    }
                } catch (NumberFormatException e) {
                    stream.printErr("В дате допустимо использование только цифр и символа \":\"\n");
                    stream.print("* Повторная попытка ввода\n");
                }
            }
        }
    }

    /**
     * Метод для чтения веса
     *
     * @return Найденный вес
     */
    private long readWeight() {
        while (true) {
            stream.print("> Введите вес человека:\n$ ");
            String res = scanner.nextLine().trim();
            if (res.isEmpty()) {
                stream.printErr("Вес не должен быть пустым\n");
                stream.print("* Повторная попытка ввода\n");
            } else {
                long weight;
                try {
                    weight = Long.parseLong(res);
                    if (weight <= 0) {
                        stream.printErr("Вес должен быть больше 0\n");
                        stream.print("* Повторная попытка ввода\n");
                    } else {
                        return weight;
                    }
                } catch (NumberFormatException e) {
                    stream.printErr("Вес должен быть целым числом\n");
                    stream.print("* Повторная попытка ввода\n");
                }
            }
        }
    }

    /**
     * Метод для паспорт айди
     *
     * @return Найденный паспорт айди
     */
    private String readPassportID() {
        while (true) {
            stream.print("> Введите паспорт айди:\n$ ");
            String passportID = scanner.nextLine().trim();
            if (passportID.isEmpty()) {
                stream.printErr("Паспорт айди не может быть пустым\n");
                stream.print("* Повторная попытка ввода\n");
            } else if (!passportID.matches("\\d+")) {
                stream.printErr("Паспорт айди должен состоять только из цифр\n");
                stream.print("* Повторная попытка ввода\n");
            } else if (passportID.length() > 25) {
                stream.printErr("Паспорт айди не должен быть больше 25 символов\n");
                stream.print("* Повторная попытка ввода\n");
            } else {
                return passportID;
            }
        }
    }
}
