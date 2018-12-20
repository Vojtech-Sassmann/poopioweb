package cz.tyckouni.poopio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Allow localhost origin for GUI development
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public class AllowOriginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
	                         HttpServletResponse response, Object handler)
			throws Exception {
		String origin = "localhost:4200";

		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers","Content-Type, *");
		return true;
	}
}