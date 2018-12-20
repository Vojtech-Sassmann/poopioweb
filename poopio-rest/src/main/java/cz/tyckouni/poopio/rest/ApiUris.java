package cz.tyckouni.poopio.rest;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 * @author Jan Gol <gol.honza@gmail.com>
 */
public class ApiUris {
	public static final String REST_ROOT = "/pa165/rest";
	public static final String ROOT_AUTH = REST_ROOT + "/auth";
	public static final String ROOT_URI_MONSTERS = ROOT_AUTH + "/monsters";
	public static final String ROOT_URI_AREAS = ROOT_AUTH + "/areas";
	public static final String ROOT_URI_WEAPONS = ROOT_AUTH + "/weapons";
	public static final String ROOT_URI_USERS = ROOT_AUTH + "/users";
	public static final String ROOT_NOT_AUTH = REST_ROOT + "/notsecured";
	public static final String ROOT_URI_UNSECURED_MONSTERS = ROOT_NOT_AUTH + "/monsters";
	public static final String ROOT_URI_UNSECURED_AREAS = ROOT_NOT_AUTH + "/areas";
	public static final String ROOT_URI_UNSECURED_WEAPONS = ROOT_NOT_AUTH + "/weapons";
	public static final String ROOT_URI_UNSECURED_USERS = ROOT_NOT_AUTH + "/users";
}
