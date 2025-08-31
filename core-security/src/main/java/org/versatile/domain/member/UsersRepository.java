package org.versatile.domain.member;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersRepository {
    
    Optional<User> findByUsername(String username);

    int save(User member);
}