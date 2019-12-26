package ml.huytools.ycnanswer.Core.Game.Graphics;

public class Color {
    public int a;
    public int r;
    public int g;
    public int b;

    public Color(int a, int r, int g, int b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(int r, int g, int b) {
        this.a = 255;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color() {
    }
}
