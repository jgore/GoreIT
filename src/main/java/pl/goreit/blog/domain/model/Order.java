package pl.goreit.blog.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class Order {

    @Id
    private String id;

    private String userId;

    private List<OrderLine> orderLines;

    private LocalDateTime creationTime;

    public Order(String id, String userId, List<OrderLine> orderLines, LocalDateTime creationTime) {
        this.id = id;
        this.userId = userId;
        this.orderLines = orderLines;
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}
