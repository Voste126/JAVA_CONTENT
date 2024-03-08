package Generics;

// Generic class
class Box<T extends Number> {
    private T length;
    private T width;
    private T height;

    public void setDimensions(T length, T width, T height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public double volume() {
        double l = length.doubleValue();
        double w = width.doubleValue();
        double h = height.doubleValue();
        return l * w * h;
    }
}

public class AllGenerics {
    public static void main(String[] args) {
        // Create an instance of the generic class
        Box<Double> doubleBox = new Box<>();
        doubleBox.setDimensions(10.0, 5.0, 2.5);

        // Get the volume of the box
        double volume = doubleBox.volume();
        System.out.println("Volume: " + volume);
    }
}
