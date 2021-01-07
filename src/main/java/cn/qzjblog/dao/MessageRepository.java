package cn.qzjblog.dao;

import cn.qzjblog.po.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by qzj on 2021/01/01 16:23
 **/
public interface MessageRepository extends JpaRepository<Message,Long> {
}
