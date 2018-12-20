package cz.tyckouni.poopio.rest.controllers.unsecured;

import cz.tyckouni.poopio.facade.UserFacade;
import cz.tyckouni.poopio.rest.ApiUris;
import cz.tyckouni.poopio.rest.controllers.UsersController;
import cz.tyckouni.poopio.rest.security.RoleResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_UNSECURED_USERS)
public class NotSecuredUsersController extends UsersController {
	public NotSecuredUsersController(UserFacade monsterFacade, RoleResolver roleResolver) {
		super(monsterFacade, roleResolver);
	}
}
