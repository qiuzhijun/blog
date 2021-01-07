package cn.qzjblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by qzj on 2020/12/12 20:23
 **/
@Entity
@Table(name="t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "type",fetch = FetchType.EAGER)
    private List<Blog> blog = new ArrayList<>();

    public List<Blog> getBlog() {
        return blog;
    }

    public Type setBlog(List<Blog> blog) {
        this.blog = blog;
        return this;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blog=" + blog +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Type setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Type setName(String name) {
        this.name = name;
        return this;
    }


    public Type() {
    }
}
