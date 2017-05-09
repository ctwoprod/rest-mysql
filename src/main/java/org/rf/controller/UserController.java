package org.rf.controller;

import org.rf.model.json.UserVO;
import org.rf.service.UserService;
import org.rf.util.ControllerRestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The UserController class
 *
 * @author Roberto
 * @version 1.0
 */

@RestController
@RequestMapping("/user")
@Validated
public class UserController extends ControllerRestUtil {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserVO> findDriverById(@PathVariable("id") String id) {
		UserVO userVO = userService.findUserById(id);
		if (userVO == null) {
			return ControllerRestUtil.returnJsonResponse(userVO, HttpStatus.NO_CONTENT);
		} else {
			return ControllerRestUtil.returnJsonResponse(userVO, HttpStatus.OK);
		}
	}

}
