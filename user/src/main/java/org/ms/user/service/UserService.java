package org.ms.user.service;


import org.ms.user.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserEntity> getUsers();

    UserEntity createUser(UserEntity user);

    UserEntity getUser(Long id);

    boolean deleteUser(Long id);
}
