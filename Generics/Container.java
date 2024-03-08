package Generics;

public class Container<T> implements Retriever<T>{
    private T data;

    // Constructor with parameter
    public Container(T data) {
        this.data = data;
    }

    // Getter for data
    public T getData() {
        return data;
    }

    // Setter for data
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public T retrieveData() {
        return this.data;
    }
}