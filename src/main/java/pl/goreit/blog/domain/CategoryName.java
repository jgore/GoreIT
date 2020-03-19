package pl.goreit.blog.domain;

public enum CategoryName {
    January("Styczeń"),
    February("Luty"),
    March("Marzec"),
    April("Kwiecień"),
    May("Maj"),
    June("Czerwiec"),
    July("Lipiec"),
    August("Sierpień"),
    September("Wrzesień"),
    October("Październik"),
    November("Listopad"),
    December("Grudzień");

    private String value;

    CategoryName(String value) {
        this.value = value;
    }
}
