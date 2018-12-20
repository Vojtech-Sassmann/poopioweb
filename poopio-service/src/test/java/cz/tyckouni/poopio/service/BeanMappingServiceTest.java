package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.dto.MonsterDTO;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.service.config.ServiceConfiguration;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

	@Inject
	private BeanMappingService beanMappingService;

	private Monster zombie;
	private	Monster chicken;

	private	MonsterDTO zombieDTO;
	private	MonsterDTO chickenDTO;

	@BeforeMethod
	public void createEntities() {
		zombie = new Monster();
		zombie.setName("Zombie");
		zombie.setAgility(MonsterAgility.SLOW);
		zombie.setId(1L);
		zombie.setHeight(25.0);
		zombie.setWeight(5.4);

		chicken = new Monster();
		chicken.setName("Chicken");
		chicken.setAgility(MonsterAgility.SLOW);
		chicken.setId(2L);
		chicken.setHeight(55.0);
		chicken.setWeight(52.4);

		zombieDTO = new MonsterDTO();
		zombieDTO.setName(zombie.getName());
		zombieDTO.setAgility(zombie.getAgility());
		zombieDTO.setId(zombie.getId());
		zombieDTO.setHeight(zombie.getHeight());
		zombieDTO.setWeight(zombie.getWeight());

		chickenDTO = new MonsterDTO();
		chickenDTO.setName(chicken.getName());
		chickenDTO.setAgility(chicken.getAgility());
		chickenDTO.setId(chicken.getId());
		chickenDTO.setHeight(chicken.getHeight());
		chickenDTO.setWeight(chicken.getWeight());
	}

	@Test
	public void testMapToDTO() {
		MonsterDTO monsterDTO = beanMappingService.mapTo(zombie, MonsterDTO.class);

		assertThat(areMonstersSame(zombie, monsterDTO));
	}

	@Test
	public void testMapFromDTO() {
		Monster monster = beanMappingService.mapTo(zombieDTO, Monster.class);

		assertThat(areMonstersSame(monster, zombieDTO));
	}

	@Test
	public void testMapToDTOList() {
		List<Monster> monsters = Arrays.asList(zombie, chicken);
		List<MonsterDTO> monsterDTOS = beanMappingService.mapTo(monsters, MonsterDTO.class);

		assertThat(monsters.size()).isEqualTo(monsterDTOS.size());

		for (int i = 0; i < monsters.size(); i++) {
			assertThat(areMonstersSame(monsters.get(i), monsterDTOS.get(i)));
		}
	}

	@Test
	public void testMapFromDTOList() {
		List<MonsterDTO> monsterDTOS = Arrays.asList(zombieDTO, chickenDTO);
		List<Monster> monsters = beanMappingService.mapTo(monsterDTOS, Monster.class);

		assertThat(monsters.size()).isEqualTo(monsterDTOS.size());

		for (int i = 0; i < monsters.size(); i++) {
			assertThat(areMonstersSame(monsters.get(i), monsterDTOS.get(i)));
		}
	}

	private boolean areMonstersSame(Monster monster, MonsterDTO monsterDTO) {
		return monsterDTO.getId().equals(monster.getId()) &&
				monsterDTO.getAgility().equals(monster.getAgility()) &&
				monsterDTO.getHeight().equals(monster.getHeight()) &&
				monsterDTO.getWeight().equals(monster.getWeight()) &&
				monsterDTO.getName().equals(monster.getName());
	}
}
