package pl.goreit.blog.domain;

public enum CategoryName {
    MOTORYZACJA("Motoryzacja"),
    ELEKTRONIKA("Elektronika"),
    IT("IT");

    private String value;

    CategoryName(String value) {
        this.value = value;
    }
}
