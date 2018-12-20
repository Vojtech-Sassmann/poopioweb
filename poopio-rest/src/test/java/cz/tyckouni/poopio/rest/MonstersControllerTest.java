package cz.tyckouni.poopio.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tyckouni.poopio.dto.MonsterCreateDTO;
import cz.tyckouni.poopio.dto.MonsterDTO;
import cz.tyckouni.poopio.dto.MonsterUpdateDTO;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.facade.MonsterFacade;
import cz.tyckouni.poopio.rest.controllers.GlobalExceptionController;
import cz.tyckouni.poopio.rest.controllers.MonstersController;
import cz.tyckouni.poopio.rest.security.RoleResolver;
import cz.tyckouni.poopio.dto.MonsterCreateDTO;
import cz.tyckouni.poopio.dto.MonsterDTO;
import cz.tyckouni.poopio.dto.MonsterUpdateDTO;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.facade.MonsterFacade;
import cz.tyckouni.poopio.rest.controllers.GlobalExceptionController;
import cz.tyckouni.poopio.rest.controllers.MonstersController;
import cz.tyckouni.poopio.rest.security.RoleResolver;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public class MonstersControllerTest {

	private MonsterFacade monsterFacade = mock(MonsterFacade.class);
	private RoleResolver roleResolver = mock(RoleResolver.class);

	@InjectMocks
	private MonstersController monstersController = new MonstersController(monsterFacade, roleResolver);

	private MockMvc mockMvc;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(monstersController)
				.setMessageConverters(new MappingJackson2HttpMessageConverter())
				.setHandlerExceptionResolvers(createExceptionResolver())
				.build();
	}

	@BeforeMethod
	public void setUpResolver() {
		when(roleResolver.isSelf(any(), any())).thenReturn(true);
		when(roleResolver.hasRole(any(), any())).thenReturn(true);
	}

	@Test
	public void debugTest() throws Exception {
		doReturn(Collections.unmodifiableList(this.createMonsters())).when(
				monsterFacade).getAllMonsters();
		mockMvc.perform(get("/pa165/rest/auth/monsters")).andDo(print());
	}

	@Test
	public void testGetAllMonsters() throws Exception {

		doReturn(Collections.unmodifiableList(this.createMonsters())).when(
				monsterFacade).getAllMonsters();

		mockMvc.perform(get("/pa165/rest/auth/monsters"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(
						jsonPath("$.[?(@.id==1)].name").value("Zombie"))
				.andExpect(
						jsonPath("$.[?(@.id==2)].name").value("Chicken"))
				.andExpect(
						jsonPath("$.[?(@.id==3)].name").value("Spider"));

	}

	@Test
	public void testCreateMonster() throws Exception {
		MonsterCreateDTO monsterCreateDTO = new MonsterCreateDTO();
		monsterCreateDTO.setName("Aslan");

		doReturn(4L).when(monsterFacade).createMonster(monsterCreateDTO);

		String json = convertObjectToJsonBytes(monsterCreateDTO);

		mockMvc.perform(
				post("/pa165/rest/auth/monsters/create").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				.andExpect(status().isOk());

	}

	@Test
	public void testDeleteMonster() throws Exception {

		mockMvc.perform(delete("/pa165/rest/auth/monsters/1"))
				.andExpect(status().isOk());

		verify(monsterFacade, times(1)).deleteMonster(1L);
	}

	@Test
	public void testDeleteMonsterNonExisting() throws Exception {

		doThrow(new RuntimeException("the product does not exist")).when(monsterFacade).deleteMonster(1L);

		mockMvc.perform(delete("/pa165/rest/auth/monsters/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateMonster() throws Exception {

		MonsterUpdateDTO updateDTO = new MonsterUpdateDTO();
		updateDTO.setId(1L);
		updateDTO.setName("Motorku");
		updateDTO.setHeight(5222.1);
		updateDTO.setWeight(1656.2);
		updateDTO.setAgility(MonsterAgility.FAST);

		MonsterDTO updatedMonster = new MonsterDTO();
		updatedMonster.setId(1L);
		updatedMonster.setName(updateDTO.getName());
		updatedMonster.setAgility(updateDTO.getAgility());
		updatedMonster.setWeight(updateDTO.getWeight());
		updatedMonster.setHeight(updateDTO.getHeight());

		when(monsterFacade.updateMonster(updateDTO)).thenReturn(updatedMonster);

		String json = convertObjectToJsonBytes(updateDTO);

		mockMvc.perform(put("/pa165/rest/auth/monsters/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[?(@.id==1)].agility").value("FAST"))
				.andExpect(jsonPath("$.[?(@.id==1)].name").value("Motorku"))
				.andExpect(jsonPath("$.[?(@.id==1)].weight").value(1656.2))
				.andExpect(jsonPath("$.[?(@.id==1)].height").value(5222.1));
	}

	@Test
	public void testGetAllForAgility() throws Exception {
		List<MonsterDTO> monsters = createMonsters();
		when(monsterFacade.getAllForAgility(MonsterAgility.SLOW)).thenReturn(Arrays.asList(monsters.get(0), monsters.get(1)));

		mockMvc.perform(get("/pa165/rest/auth/monsters/filter/agility/SLOW"))
				.andExpect(jsonPath("$.[?(@.id==1)].name").value("Zombie"))
				.andExpect(jsonPath("$.[?(@.id==2)].name").value("Chicken"));
	}

	@Test
	public void testFindById() throws Exception {

		List<MonsterDTO> monsters = createMonsters();

		when(monsterFacade.findById(1L)).thenReturn(monsters.get(0));

		mockMvc.perform(get("/pa165/rest/auth/monsters/1"))
				.andExpect(jsonPath("$.[?(@.id==1)].name").value("Zombie"));
	}

	@Test
	public void testFindByName() throws Exception {

		List<MonsterDTO> monsters = createMonsters();

		when(monsterFacade.findByName("Zombie")).thenReturn(monsters.get(0));

		mockMvc.perform(get("/pa165/rest/auth/monsters/filter/name/Zombie"))
				.andExpect(jsonPath("$.[?(@.id==1)].name").value("Zombie"));
	}

	@Test
	public void testGetTheMostWideSpreadMonsters() throws Exception {

		List<MonsterDTO> monsters = createMonsters();

		when(monsterFacade.getTheMostWidespreadMonsters()).thenReturn(monsters);

		mockMvc.perform(get("/pa165/rest/auth/monsters/filter/mostWidespread"))
				.andExpect(jsonPath("$.[?(@.id==1)].name").value("Zombie"))
				.andExpect(jsonPath("$.[?(@.id==2)].name").value("Chicken"))
				.andExpect(jsonPath("$.[?(@.id==3)].name").value("Spider"));
	}

	private List<MonsterDTO> createMonsters() {
		MonsterDTO zombie = new MonsterDTO();
		MonsterDTO chicken = new MonsterDTO();
		MonsterDTO spider = new MonsterDTO();

		zombie.setName("Zombie");
		chicken.setName("Chicken");
		spider.setName("Spider");

		zombie.setId(1L);
		chicken.setId(2L);
		spider.setId(3L);

		zombie.setAgility(MonsterAgility.SLOW);
		chicken.setAgility(MonsterAgility.SLOW);
		spider.setAgility(MonsterAgility.FAST);

		return Arrays.asList(zombie, chicken, spider);
	}

	private static String convertObjectToJsonBytes(Object object)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsString(object);
	}

	private ExceptionHandlerExceptionResolver createExceptionResolver() {
		ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
			protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
				Method method = new ExceptionHandlerMethodResolver(GlobalExceptionController.class).resolveMethod(exception);
				return new ServletInvocableHandlerMethod(new GlobalExceptionController(), method);
			}
		};
		exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		exceptionResolver.afterPropertiesSet();
		return exceptionResolver;
	}
}
