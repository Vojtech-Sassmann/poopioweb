package cz.tyckouni.poopio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import cz.tyckouni.poopio.enums.UserRole;

/**
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
@Entity
@Table(name = "Users")
public class User {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@Column(nullable = false)
	private String firstName;

	@NotNull
	@Column(nullable = false)
	private String lastName;

	@NotNull
	@Column(nullable = false, unique = true)
	private String email;

	@NotNull
	@Column(nullable = false)
	private String passwordHash;

	@Enumerated
	@NotNull
	@Column(nullable = false)
	private UserRole role;

	public User() {
	}

	public User(String firstName, String lastName, String email, String passwordHash) {
		checkFirstName(firstName);
		checkLastName(lastName);
		checkEmail(email);
		checkPasswordHash(passwordHash);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = UserRole.REGULAR;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		checkFirstName(firstName);
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		checkLastName(lastName);
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		checkEmail(email);
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		checkPasswordHash(passwordHash);
		this.passwordHash = passwordHash;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		checkRole(role);
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof User)) return false;

		User user = (User) o;

		if (!getFirstName().equals(user.getFirstName())) return false;
		if (!getLastName().equals(user.getLastName())) return false;
		return getEmail().equals(user.getEmail());
	}

	@Override
	public int hashCode() {
		int result = getFirstName().hashCode();
		result = 31 * result + getLastName().hashCode();
		result = 31 * result + getEmail().hashCode();
		return result;
	}

	private void checkFirstName(String firstName) {
		if (firstName == null) {
			throw new IllegalArgumentException("User's first name cannot be null!");
		}
		if (firstName.isEmpty()) {
			throw new IllegalArgumentException("User's first name cannot be empty!");
		}
	}
	private void checkLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("User's last name cannot be null!");
		}
		if (lastName.isEmpty()) {
			throw new IllegalArgumentException("User's last name cannot be empty!");
		}
	}

	private void checkEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("User's email cannot be null!");
		}
		if (email.isEmpty()) {
			throw new IllegalArgumentException("User's email cannot be empty!");
		}
	}

	private void checkPasswordHash(String passwordHash) {
		if (passwordHash == null) {
			throw new IllegalArgumentException("User's passwordHash cannot be null!");
		}
		if (passwordHash.isEmpty()) {
			throw new IllegalArgumentException("User's passwordHash cannot be empty!");
		}
	}

	private void checkRole(UserRole role) {
		if (role == null) {
			throw new IllegalArgumentException("User's role cannot be null!");
		}
	}
}
