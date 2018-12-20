package cz.tyckouni.poopio.rest.controllers.unsecured;

import cz.tyckouni.poopio.facade.MonsterFacade;
import cz.tyckouni.poopio.facade.WeaponFacade;
import cz.tyckouni.poopio.rest.ApiUris;
import cz.tyckouni.poopio.rest.controllers.WeaponsController;
import cz.tyckouni.poopio.rest.security.RoleResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_UNSECURED_WEAPONS)
public class NotSecuredWeaponsController extends WeaponsController {
	public NotSecuredWeaponsController(WeaponFacade weaponFacade, MonsterFacade monsterFacade, RoleResolver roleResolver) {
		super(weaponFacade, monsterFacade, roleResolver);
	}
}
