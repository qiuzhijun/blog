package cn.qzjblog.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Create by qzj on 2021/01/01 12:12
 **/
@Entity
@Table(name="t_message")
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private String message;

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Message setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public Message(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}
