package cz.tyckouni.poopio.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tyckouni.poopio.dto.MonsterDTO;
import cz.tyckouni.poopio.dto.WeaponCreateDTO;
import cz.tyckouni.poopio.dto.WeaponDTO;
import cz.tyckouni.poopio.dto.WeaponUpdateDTO;
import cz.tyckouni.poopio.enums.WeaponType;
import cz.tyckouni.poopio.facade.MonsterFacade;
import cz.tyckouni.poopio.facade.WeaponFacade;
import cz.tyckouni.poopio.rest.controllers.GlobalExceptionController;
import cz.tyckouni.poopio.rest.controllers.WeaponsController;
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
 * Weapon REST Controller test
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public class WeaponsControllerTest {

    private WeaponFacade weaponFacade = mock(WeaponFacade.class);
    private MonsterFacade monsterFacade = mock(MonsterFacade.class);
    private RoleResolver roleResolver = mock(RoleResolver.class);

    @InjectMocks
    private WeaponsController weaponsController = new WeaponsController(weaponFacade, monsterFacade, roleResolver);

    private MockMvc mockMvc;

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(weaponsController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setHandlerExceptionResolvers(createExceptionResolver())
                .build();
    }

    @BeforeMethod
    public void setUpResolver() {
        when(roleResolver.isSelf(any(), any())).thenReturn(true);
        when(roleResolver.hasRole(any(), any())).thenReturn(true);
    }


    private List<WeaponDTO> createWeapons(){
        WeaponDTO pistol = new WeaponDTO();
        pistol.setName("Pistol");
        pistol.setId(1L);
        pistol.setType(WeaponType.OTHER);
        pistol.setMagazineCapacity(10);
        pistol.setRange(100);

        WeaponDTO rifle = new WeaponDTO();
        rifle.setName("Rifle");
        rifle.setId(2L);
        rifle.setType(WeaponType.OTHER);
        rifle.setMagazineCapacity(50);
        rifle.setRange(200);

        WeaponDTO shotgun = new WeaponDTO();
        shotgun.setName("Shotgun");
        shotgun.setId(3L);
        shotgun.setType(WeaponType.SHOTGUN);
        shotgun.setMagazineCapacity(20);
        shotgun.setRange(150);

        return Arrays.asList(pistol, rifle, shotgun);
    }

    private static String convertObjectToJsonBytes(Object object) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver(){
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver(){
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception){
                Method method = new ExceptionHandlerMethodResolver(GlobalExceptionController.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new GlobalExceptionController(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }


    @Test
    public void debugTest() throws Exception{
        doReturn(Collections.unmodifiableList(this.createWeapons())).when(
                weaponFacade).getAll();
        mockMvc.perform(get("/pa165/rest/auth/weapons")).andDo(print());
    }

    @Test
    public void testCreateWeapon() throws Exception{
        WeaponCreateDTO weaponCreateDTO = new WeaponCreateDTO();
        weaponCreateDTO.setName("Weapon");
        doReturn(10L).when(weaponFacade).createWeapon(weaponCreateDTO);

        String json = convertObjectToJsonBytes(weaponCreateDTO);

        mockMvc.perform(post("/pa165/rest/auth/weapons/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteExistingWeapon() throws Exception{
        mockMvc.perform(delete("/pa165/rest/auth/weapons/delete/1")).andExpect(status().isOk());

        verify(weaponFacade, times(1)).deleteWeapon(1L);
    }

    @Test
    public void testDeleteNonExistingWeapon() throws Exception{
        doThrow(new RuntimeException("The product does not exist")).when(weaponFacade).deleteWeapon(1L);

        mockMvc.perform(delete("/pa165/rest/auth/weapons/delete/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateWeapon() throws Exception{
        WeaponUpdateDTO weaponUpdateDTO = new WeaponUpdateDTO();
        weaponUpdateDTO.setId(1L);
        weaponUpdateDTO.setType(WeaponType.SHOTGUN);
        weaponUpdateDTO.setName("Shotgun");
        weaponUpdateDTO.setRange(100);
        weaponUpdateDTO.setMagazineCapacity(20);

        WeaponDTO weaponDTO = new WeaponDTO();
        weaponDTO.setId(1L);
        weaponDTO.setType(weaponUpdateDTO.getType());
        weaponDTO.setName(weaponUpdateDTO.getName());
        weaponDTO.setRange(weaponUpdateDTO.getRange());
        weaponDTO.setMagazineCapacity(weaponUpdateDTO.getMagazineCapacity());

        when(weaponFacade.updateWeapon(weaponUpdateDTO)).thenReturn(weaponDTO);

        String json = convertObjectToJsonBytes(weaponUpdateDTO);

        mockMvc.perform(put("/pa165/rest/auth/weapons/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Shotgun"))
                .andExpect(jsonPath("$.[?(@.id==1)].type").value("SHOTGUN"))
                .andExpect(jsonPath("$.[?(@.id==1)].range").value(100))
                .andExpect(jsonPath("$.[?(@.id==1)].magazineCapacity").value(20));
    }

    @Test
    public void testGetAllWeapons() throws Exception {
        doReturn(Collections.unmodifiableList(this.createWeapons())).when(weaponFacade).getAll();
        mockMvc.perform(get("/pa165/rest/auth/weapons"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Pistol"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Rifle"))
                .andExpect(jsonPath("$.[?(@.id==3)].name").value("Shotgun"));
    }

    @Test
    public void testGetAllWeaponsForType() throws Exception{
        List<WeaponDTO> weapons = this.createWeapons();
        when(weaponFacade.getAllForType(WeaponType.OTHER)).thenReturn(Arrays.asList(weapons.get(0), weapons.get(1)));

        mockMvc.perform(get("/pa165/rest/auth/weapons/filter/type/OTHER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Pistol"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Rifle"));
    }

    @Test
    public void findWeaponById() throws Exception{
        List<WeaponDTO> weapons = this.createWeapons();
        when(weaponFacade.findById(1L)).thenReturn(weapons.get(0));

        mockMvc.perform(get("/pa165/rest/auth/weapons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Pistol"));
    }

    @Test
    public void testFindWeaponByName() throws Exception{
        List<WeaponDTO> weapons = this.createWeapons();
        when(weaponFacade.findByName("Pistol")).thenReturn(weapons.get(0));

        mockMvc.perform(get("/pa165/rest/auth/weapons/filter/name/Pistol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Pistol"));
    }

    @Test
    public void testGetTheMostEffectiveWeapon() throws Exception{
        List<WeaponDTO> weapons = this.createWeapons();

        when(weaponFacade.getTheMostEffectiveWeapon()).thenReturn(weapons);
        mockMvc.perform(get("/pa165/rest/auth/weapons/filter/mostEffectiveWeapon"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Pistol"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Rifle"))
                .andExpect(jsonPath("$.[?(@.id==3)].name").value("Shotgun"));
    }

    @Test
    public void testAddAppropriateMonster() throws Exception{
        WeaponDTO pistol = new WeaponDTO();
        pistol.setName("Pistol");
        pistol.setId(1L);
        pistol.setType(WeaponType.OTHER);
        pistol.setMagazineCapacity(10);
        pistol.setRange(100);

        MonsterDTO monsterDTO = new MonsterDTO();
        monsterDTO.setId(1L);
        monsterDTO.setName("Name");

        when(monsterFacade.findById(1L)).thenReturn(monsterDTO);
        when(weaponFacade.findById(1L)).thenReturn(pistol);
        mockMvc.perform(put("/pa165/rest/auth/weapons/1/addAppropriateMonster?monsterId=1"))
                .andExpect(status().isOk());

        verify(weaponFacade, times(1)).addAppropriateMonster(1L, monsterDTO.getId());
    }

    @Test
    public void testRemoveAppropriateMonster() throws Exception{
        WeaponDTO pistol = new WeaponDTO();
        pistol.setName("Pistol");
        pistol.setId(1L);
        pistol.setType(WeaponType.OTHER);
        pistol.setMagazineCapacity(10);
        pistol.setRange(100);

        MonsterDTO monsterDTO = new MonsterDTO();
        monsterDTO.setId(1L);
        monsterDTO.setName("Name");

        when(monsterFacade.findById(1L)).thenReturn(monsterDTO);
        when(weaponFacade.findById(1L)).thenReturn(pistol);

        weaponsController.addAppropriateMonster(1L, 1L);

        mockMvc.perform(put("/pa165/rest/auth/weapons/1/removeAppropriateMonster?monsterId=1"))
                .andExpect(status().isOk());

        verify(weaponFacade, times(1)).removeAppropriateMonster(1L, monsterDTO.getId());
    }
}
