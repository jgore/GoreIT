package pl.goreit.blog.domain.model.postgres;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "batch_product_import")
public class BatchProductImport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String products_json;

    private LocalDateTime creationTime;

    public BatchProductImport(String products_json, LocalDateTime creationTime) {
        this.products_json = products_json;
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }

    public String getProducts_json() {
        return products_json;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}
