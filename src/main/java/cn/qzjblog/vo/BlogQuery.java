package cn.qzjblog.vo;

/**
 * Created by limi on 2017/10/20.
 */
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommended;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

}
