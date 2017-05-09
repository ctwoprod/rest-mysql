package org.rf.service;

import java.util.List;

import org.rf.entities.Users;
import org.rf.model.json.UserVO;

public interface UserService {
	List<Users> findAllUser();

	UserVO findUserById(String id);
}
