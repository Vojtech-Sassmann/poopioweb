package cz.tyckouni.poopio.rest.controllers.unsecured;

import cz.tyckouni.poopio.facade.AreaFacade;
import cz.tyckouni.poopio.facade.MonsterFacade;
import cz.tyckouni.poopio.rest.ApiUris;
import cz.tyckouni.poopio.rest.controllers.AreasController;
import cz.tyckouni.poopio.rest.security.RoleResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_UNSECURED_AREAS)
public class NotSecuredAreasController extends AreasController {
	public NotSecuredAreasController(AreaFacade areaFacade, MonsterFacade monsterFacade, RoleResolver roleResolver) {
		super(areaFacade, monsterFacade, roleResolver);
	}
}
