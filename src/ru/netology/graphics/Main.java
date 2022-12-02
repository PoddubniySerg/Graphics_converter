package ru.netology.graphics;

import ru.netology.graphics.image.ImageConverter;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new ImageConverter(); // Создайте тут объект вашего класса конвертера

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
//        String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
//        converter.setTextColorSchema(new ColorSchema(new char[]{'●', '◉', '◍', '◎', '○', '☉', '◌', '-'}));
//        String imgTxt = converter.convert(url);
//        System.out.println(imgTxt);
    }
}
