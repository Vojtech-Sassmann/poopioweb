package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.dao.UserDao;
import cz.tyckouni.poopio.entity.User;
import cz.tyckouni.poopio.enums.UserRole;
import cz.tyckouni.poopio.dao.UserDao;
import cz.tyckouni.poopio.entity.User;
import cz.tyckouni.poopio.enums.UserRole;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	@Inject
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User findUserById(Long userId) {
		return userDao.findById(userId);
	}

	@Override
	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public void registerUser(User user, String unencryptedPassword) {
		user.setPasswordHash(createHash(unencryptedPassword));
		if(user.getRole() == null) {
			user.setRole(UserRole.REGULAR);
		}
		userDao.create(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAll();
	}

	@Override
	public boolean authenticate(User user, String password) {
		return validatePassword(password, user.getPasswordHash());
	}

	@Override
	public boolean isAdmin(User user) {
		//must get a fresh copy from database
		user = userDao.findById(user.getId());
		return user.getRole() == UserRole.ADMIN;
	}

	@Override
	public void setAdmin(User user) {
		if(isAdmin(user)) return;
		user.setRole(UserRole.ADMIN);
	}

	@Override
	public void removeAdmin(User user) {
		if(!isAdmin(user)) return;
		user.setRole(UserRole.REGULAR);
	}

	@Override
	public void deleteUser(User user) {
		userDao.delete(user);
	}

	private static String createHash(String password) {
		final int SALT_BYTE_SIZE = 24;
		final int HASH_BYTE_SIZE = 24;
		final int PBKDF2_ITERATIONS = 1000;
		// Generate a random salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		// Hash the password
		byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		// format iterations:salt:hash
		return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
	}

	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
		try {
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
			return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean validatePassword(String password, String correctHash) {
		if(password==null) return false;
		if(correctHash==null) throw new IllegalArgumentException("password hash is null");
		String[] params = correctHash.split(":");
		int iterations = Integer.parseInt(params[0]);
		byte[] salt = fromHex(params[1]);
		byte[] hash = fromHex(params[2]);
		byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
		return slowEquals(hash, testHash);
	}

	/**
	 * Compares two byte arrays in length-constant time. This comparison method
	 * is used so that password hashes cannot be extracted from an on-line
	 * system using a timing attack and then attacked off-line.
	 *
	 * @param a the first byte array
	 * @param b the second byte array
	 * @return true if both byte arrays are the same, false if not
	 */
	private static boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		return diff == 0;
	}

	private static byte[] fromHex(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	}

	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
	}
}
