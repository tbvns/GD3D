package xyz.tbvns.Object;

public class Color {
    public Color(float r, float g, float b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }
    public float red = 0;
    public float green = 0;
    public float blue = 0;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Color) {
            Color color = (Color) obj;
            if (this.blue == color.blue && this.red == color.red && this.green == color.green) {
                return true;
            }
        }
        return false;
    }
}
