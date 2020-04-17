package pl.goreit.blog.domain;

public enum CategoryName {
    MOTO("Motoryzacja"),
    ELEKTRO("Elektronika"),
    IT("IT");

    private String value;

    CategoryName(String value) {
        this.value = value;
    }
}
