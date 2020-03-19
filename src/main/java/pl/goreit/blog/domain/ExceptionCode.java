package pl.goreit.blog.domain;

public enum ExceptionCode {

    FOR_SELL_01("GoreIT.01", "Product does not exist"),
    FOR_SELL_02("GoreIT.02", "Category does not exist"),
    FOR_SELL_03("GoreIT.03", "Comment can be added only to available produdts");

    private final String message;
    private String code;

    private ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
