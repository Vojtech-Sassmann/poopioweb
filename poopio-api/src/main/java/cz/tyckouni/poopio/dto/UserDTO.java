package cz.tyckouni.poopio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.tyckouni.poopio.enums.UserRole;
import cz.tyckouni.poopio.enums.UserRole;

/**
 * DTO class for User entity
 *
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String passwordHash;
	private UserRole role;

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
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPasswordHash() {
		return passwordHash;
	}

	@JsonProperty
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserDTO userDTO = (UserDTO) o;

		if (getId() != null ? !getId().equals(userDTO.getId()) : userDTO.getId() != null) return false;
		if (getFirstName() != null ? !getFirstName().equals(userDTO.getFirstName()) : userDTO.getFirstName() != null)
			return false;
		if (getLastName() != null ? !getLastName().equals(userDTO.getLastName()) : userDTO.getLastName() != null)
			return false;
		if (getEmail() != null ? !getEmail().equals(userDTO.getEmail()) : userDTO.getEmail() != null) return false;
		if (getPasswordHash() != null ? !getPasswordHash().equals(userDTO.getPasswordHash()) : userDTO.getPasswordHash() != null)
			return false;
		return getRole() == userDTO.getRole();
	}

	@Override
	public int hashCode() {
		int result = getId() != null ? getId().hashCode() : 0;
		result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
		result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
		result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
		result = 31 * result + (getPasswordHash() != null ? getPasswordHash().hashCode() : 0);
		result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", passwordHash='" + passwordHash + '\'' +
				", role=" + role +
				'}';
	}
}
