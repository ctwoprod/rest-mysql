package org.rf.service.impl;

import java.util.List;

import org.rf.dao.UsersDao;
import org.rf.entities.Users;
import org.rf.model.json.UserVO;
import org.rf.service.UserService;
import org.rf.util.ExtendedSpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersDao userDao;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Users> findAllUser() {
		return (List<Users>) userDao.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UserVO findUserById(String id) {
		Users user = userDao.findOne(id);
		UserVO result = new UserVO();
		result = convertPnsIntoPnsDataUtama(user, result);
		return result;
	}

	private UserVO convertPnsIntoPnsDataUtama(Users user, UserVO result) {
		ExtendedSpringBeanUtil.copySpecificProperties(user, result, new String[] { "id", "nama", "email", "userName" });
		return result;
	}

}
