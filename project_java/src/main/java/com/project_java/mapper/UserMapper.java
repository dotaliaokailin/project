package com.project_java.mapper;

import com.project_java.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    Boolean save(User user);

    User findByUserName(String username);

    Boolean update(User user);

    User login(String username,String password);
}
