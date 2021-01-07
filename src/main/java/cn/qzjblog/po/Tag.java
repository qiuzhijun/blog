package cn.qzjblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by qzj on 2020/12/12 20:23
 **/

@Entity
@Table(name="t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags",fetch = FetchType.EAGER)
    private List<Blog> blog = new ArrayList<>();

    public Tag() {
    }


    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blog=" + blog +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Tag setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Tag setName(String name) {
        this.name = name;
        return this;
    }

    public List<Blog> getBlog() {
        return blog;
    }

    public Tag setBlog(List<Blog> blog) {
        this.blog = blog;
        return this;
    }
}
