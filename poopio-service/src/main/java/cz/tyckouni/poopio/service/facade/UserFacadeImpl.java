package cz.tyckouni.poopio.service.facade;

import cz.tyckouni.poopio.dto.UserAuthenticateDTO;
import cz.tyckouni.poopio.dto.UserDTO;
import cz.tyckouni.poopio.entity.User;
import cz.tyckouni.poopio.facade.UserFacade;
import cz.tyckouni.poopio.service.BeanMappingService;
import cz.tyckouni.poopio.service.UserService;
import cz.tyckouni.poopio.entity.User;
import cz.tyckouni.poopio.facade.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

	private final UserService userService;

	private final BeanMappingService beanMappingService;

	@Inject
	public UserFacadeImpl(UserService userService, BeanMappingService beanMappingService) {
		this.userService = userService;
		this.beanMappingService = beanMappingService;
	}

	@Override
	public UserDTO findUserById(Long userId) {

		User user = userService.findUserById(userId);
		return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
	}

	@Override
	public UserDTO findUserByEmail(String email) {
		User user = userService.findUserByEmail(email);
		return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
	}

	@Override
	public void registerUser(UserDTO user, String unencryptedPassword) {
		User userEntity = beanMappingService.mapTo(user, User.class);
		userService.registerUser(userEntity, unencryptedPassword);
		user.setId(userEntity.getId());
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return beanMappingService.mapTo(userService.getAllUsers(), UserDTO.class);
	}

	@Override
	public boolean authenticate(UserAuthenticateDTO user) {
		return userService.authenticate(
				userService.findUserById(user.getUserId()), user.getPassword());
	}

	@Override
	public boolean isAdmin(Long userId) {
		return userService.isAdmin(userService.findUserById(userId));
	}

	@Override
	public void setAdmin(Long userId) {
		userService.setAdmin(userService.findUserById(userId));
	}

	@Override
	public void removeAdmin(Long userId) {
		userService.removeAdmin(userService.findUserById(userId));
	}

	@Override
	public void deleteUser(Long userId) {
		userService.deleteUser(userService.findUserById(userId));
	}
}
