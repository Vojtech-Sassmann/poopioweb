package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.dao.UserDao;
import cz.tyckouni.poopio.entity.User;
import cz.tyckouni.poopio.enums.UserRole;
import cz.tyckouni.poopio.dao.UserDao;
import cz.tyckouni.poopio.entity.User;
import cz.tyckouni.poopio.enums.UserRole;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
public class UserServiceUnitTest {

	private UserDao userDao = mock(UserDao.class);

	@InjectMocks
	private UserService userService;

	private User user1;
	private User user2;
	private User user3;

	@BeforeMethod
	public void setService() {
		userService = new UserServiceImpl(userDao);
	}

	@BeforeMethod
	public void createEntities() {
		user1 = new User();
		user1.setFirstName("Lojzo");
		user1.setLastName("Hrabovsky");
		user1.setEmail("email@lojzo.com");

		user2 = new User();
		user2.setFirstName("Adam");
		user2.setLastName("Vrtky");
		user2.setEmail("email@adam.com");

		user3 = new User();
		user3.setFirstName("Lucia");
		user3.setLastName("Mala");
		user3.setEmail("email@lucia.com");
	}

	@Test
	public void registerUserTest() throws Exception {
		userService.registerUser(user1, "123456789");

		verify(userDao, times(1)).create(user1);
	}

	@Test
	public void deleteUserTest() throws Exception {
		userService.deleteUser(user2);

		verify(userDao, times(1)).delete(user2);
	}

	@Test
	public void getAllUsersTest() throws Exception {

		when(userDao.getAll()).thenReturn(Arrays.asList(user1, user2, user3));

		List<User> allUsers = userService.getAllUsers();
		assertThat(allUsers).containsOnly(user1, user2, user3);
	}

	@Test
	public void FindByIdTest() throws Exception {
		when(userDao.findById(1L)).thenReturn(user1);

		User foundUser = userService.findUserById(1L);

		assertThat(foundUser).isEqualToComparingFieldByField(user1);
	}

	@Test
	public void FindByIdNotFoundTest() throws Exception {
		when(userDao.findById(1L)).thenReturn(null);

		User foundUser = userService.findUserById(1L);

		assertThat(foundUser).isEqualTo(null);
	}

	@Test
	public void FindByEmailTest() throws Exception {
		when(userDao.findByEmail("email@lojzo.com")).thenReturn(user1);

		User foundUser = userService.findUserByEmail("email@lojzo.com");

		assertThat(foundUser).isEqualToComparingFieldByField(user1);
	}

	@Test
	public void FindByEmailNotFoundTest() throws Exception {
		when(userDao.findByEmail("email@lojzo.com")).thenReturn(null);

		User foundUser = userService.findUserByEmail("email@lojzo.com");

		assertThat(foundUser).isEqualTo(null);
	}

	@Test
	public void authenticateTest() throws Exception {
		userService.registerUser(user3, "123456789");
		boolean answer = userService.authenticate(user3,"123456789");
		assertThat(answer).isEqualTo(true);
	}

	@Test
	public void authenticateIncorrectPasswordTest() throws Exception {
		userService.registerUser(user3, "123456789");
		boolean answer = userService.authenticate(user3,"12345");
		assertThat(answer).isEqualTo(false);
	}

	@Test
	public void isAdminTest() throws Exception {
		user1.setRole(UserRole.ADMIN);

		when(userDao.findById(any(Long.class))).thenReturn(user1);

		boolean answer = userService.isAdmin(user1);

		assertThat(answer).isEqualTo(true);
	}

	@Test
	public void isAdminNotTrueTest() throws Exception {
		user1.setRole(UserRole.REGULAR);

		when(userDao.findById(any(Long.class))).thenReturn(user1);

		boolean answer = userService.isAdmin(user1);

		assertThat(answer).isEqualTo(false);
	}

	@Test
	public void setAdminTest() throws Exception {

		when(userDao.findById(any(Long.class))).thenReturn(user1);

		userService.setAdmin(user1);

		Assertions.assertThat(user1.getRole()).isEqualTo(UserRole.ADMIN);
	}

	@Test
	public void setAdminAlreadyAdminTest() throws Exception {
		user1.setRole(UserRole.ADMIN);

		when(userDao.findById(any(Long.class))).thenReturn(user1);

		userService.setAdmin(user1);

		Assertions.assertThat(user1.getRole()).isEqualTo(UserRole.ADMIN);
	}

	@Test
	public void removeAdminTest() throws Exception {
		user1.setRole(UserRole.ADMIN);

		when(userDao.findById(any(Long.class))).thenReturn(user1);

		userService.removeAdmin(user1);

		Assertions.assertThat(user1.getRole()).isEqualTo(UserRole.REGULAR);
	}

	@Test
	public void removeAdminAlreadyRegularTest() throws Exception {
		user1.setRole(UserRole.REGULAR);

		when(userDao.findById(any(Long.class))).thenReturn(user1);

		userService.removeAdmin(user1);

		Assertions.assertThat(user1.getRole()).isEqualTo(UserRole.REGULAR);
	}
}
