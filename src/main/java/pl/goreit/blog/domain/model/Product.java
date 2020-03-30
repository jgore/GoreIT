package pl.goreit.blog.domain.model;

import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document
public class Product {

    @Id
    private String id;
    @Indexed(unique = true)
    private String title;
    private String text;
    private BigDecimal price;
    private List<Comment> comments;
    private Status status;

    private LocalDateTime creationDate;

    public Product( String title, String text, BigDecimal price) {
        this.title = title;
        this.text = text;
        this.price = price;
        this.status = Status.AVAILABLE;
        this.creationDate = LocalDateTime.now();
        this.comments = Lists.newArrayList();
    }

    public boolean addComment(Comment comment) throws DomainException {
        if (Status.AVAILABLE != getStatus()) {
            throw new DomainException(ExceptionCode.GOREIT_03);
        }
        return comments.add(comment);
    }

    public String getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Comment> getComments() {
        return Lists.newArrayList(comments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(getPrice(), product.getPrice()) &&
                status == product.status &&
                Objects.equals(creationDate, product.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getPrice(), status, creationDate);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", creationDate=" + creationDate +
                '}';
    }

    public enum Status {
        AVAILABLE, SOLD, ARCHIVED
    }
}
