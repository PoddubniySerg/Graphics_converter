package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema {

    private final char[] color;

    public ColorSchema() {
        this.color = new char[]{'#', '$', '@', '%', '*', '+', '-', '.'};
    }

    public ColorSchema(char[] color) {
        this.color = color;
    }

    @Override
    public char convert(int color) {
        return this.color[color / 32];
    }
}
