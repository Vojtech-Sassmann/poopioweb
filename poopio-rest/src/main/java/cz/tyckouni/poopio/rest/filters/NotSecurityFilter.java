package cz.tyckouni.poopio.rest.filters;

import cz.tyckouni.poopio.dto.UserDTO;
import cz.tyckouni.poopio.enums.UserRole;
import cz.tyckouni.poopio.rest.ApiUris;
import cz.tyckouni.poopio.rest.security.SecurityUtils;
import cz.tyckouni.poopio.rest.security.SecurityUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@WebFilter(urlPatterns = {
	ApiUris.ROOT_URI_UNSECURED_MONSTERS + "/*",
	ApiUris.ROOT_URI_UNSECURED_WEAPONS + "/*",
	ApiUris.ROOT_URI_UNSECURED_AREAS + "/*",
	ApiUris.ROOT_URI_UNSECURED_USERS + "/*"})
public class NotSecurityFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		UserDTO fakeAdmin = new UserDTO();
		fakeAdmin.setRole(UserRole.ADMIN);
		fakeAdmin.setId(-1L);

		request.setAttribute(SecurityUtils.AUTHENTICATE_USER, fakeAdmin);

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
