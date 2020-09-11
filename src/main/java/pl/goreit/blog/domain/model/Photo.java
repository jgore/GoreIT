package pl.goreit.blog.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class Photo {

    @Id
    private String id;
    private String userId;
    private String name;
    private String type;
    private UUID transactionId;
    private byte[] imageByte;

    public Photo(UUID transactionId, String userId, String name, String type, byte[] imageByte) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.imageByte = imageByte;
    }

    public String getId() {
        return id;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }

    public byte[] getImageByte() {
        return imageByte;
    }
}
