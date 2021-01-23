package cn.qzjblog.vo;

import lombok.Data;

import java.util.List;

/**
 * Create by qzj on 2021/01/20 15:50
 **/
@Data
public class Table {
    private Integer id;
    private Title title;
    private List<Comments> comments;
}
