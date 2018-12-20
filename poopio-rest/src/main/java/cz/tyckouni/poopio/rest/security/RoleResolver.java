package cz.tyckouni.poopio.rest.security;

import cz.tyckouni.poopio.dto.UserDTO;
import cz.tyckouni.poopio.enums.UserRole;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public interface RoleResolver {

	/**
	 * Returns true if given request contains user with given role
	 *
	 * @param request request
	 * @param role role
	 * @return true if given request contains user with given role, false otherwise
	 */
	boolean hasRole(HttpServletRequest request, UserRole role);

	/**
	 * Returns true if user from request is given user
	 *
	 * @param request request
	 * @param user user
	 * @return Returns true if user from request has given id, false otherwise
	 */
	boolean isSelf(HttpServletRequest request, UserDTO user);
}
