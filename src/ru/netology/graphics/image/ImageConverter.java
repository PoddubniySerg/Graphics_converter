package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;


public class ImageConverter implements TextGraphicsConverter {

    private int maxWidth;
    private int maxHeight;
    private double maxRatio;
    private final TextColorSchema defaultSchema = new ColorSchema();
    private TextColorSchema userSchema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));

        //проверка на соотношение сторон
        double imgRatio = img.getWidth() / img.getHeight();
        if (maxRatio != 0 && imgRatio > this.maxRatio) {
            throw new BadImageSizeException(imgRatio, maxRatio);
        }

        //задание размеров и масштабирование новой картинки в соответствии с заданными параметрами
        int newWidth = img.getWidth();
        int newHeight = img.getHeight();
        double scale = 0;
        Image scaledImage = null;
        if (maxWidth != 0 && img.getWidth() > maxWidth) {
            newWidth = maxWidth;
            scale = img.getWidth() / maxWidth;
            newHeight = (int) (img.getHeight() / scale);
        }
        if (maxHeight != 0 && img.getHeight() > maxHeight) {
            newHeight = maxHeight;
            scale = img.getHeight() / maxHeight;
            newWidth = (int) (img.getWidth() / scale);
        }
        if (scale > 0) {
            scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }

        //создание копии картинки в черно-белом цвете
        BufferedImage bwImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImage.createGraphics();
        graphics.drawImage(scale > 0 ? scaledImage : img, 0, 0, null);

        //получение и соединение в одну струкктуру символов цветов пикселей и переносов строк
        WritableRaster bwRaster = bwImage.getRaster();
        int[] colorPixel = new int[3];
        StringBuilder textImage = new StringBuilder();
        for (int column = 0; column < bwImage.getHeight(); column++) {
            for (int row = 0; row <= bwImage.getWidth(); row++) {
                if (row == bwImage.getWidth()) {
                    //добавить в очередь перенос строки
                    textImage.append("\n");
                } else {
                    int color = bwRaster.getPixel(row, column, colorPixel)[0];
                    textImage.append(userSchema == null ? defaultSchema.convert(color) : userSchema.convert(color));
                }
            }
        }
        return textImage.toString();//конвертирование полученного набора символов в строку
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = Math.max(width, 0);
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = Math.max(height, 0);
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio > 0 ? maxRatio : 0;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.userSchema = schema;
    }
}