package Generics;

/**
 * Represents a book.
 * Implements the Retriever interface with a generic type parameter of String.
 */
public class Book implements Retriever<String> {
    private String name;

    /**
     * Constructs a Book object with the specified name.
     * 
     * @param name the name of the book
     */
    public Book(String name) {
        this.name = name;
    }

    /**
     * Sets the name of the book.
     * 
     * @param name the name of the book
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the book.
     * 
     * @return the name of the book
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String retrieveData(){
        return this.name;
    }
}
