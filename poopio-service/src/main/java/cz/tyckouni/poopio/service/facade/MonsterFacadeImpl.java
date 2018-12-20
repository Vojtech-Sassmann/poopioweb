package cz.tyckouni.poopio.service.facade;

import cz.tyckouni.poopio.dto.MonsterChangeAgilityDTO;
import cz.tyckouni.poopio.dto.MonsterCreateDTO;
import cz.tyckouni.poopio.dto.MonsterDTO;
import cz.tyckouni.poopio.dto.MonsterUpdateDTO;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.facade.MonsterFacade;
import cz.tyckouni.poopio.service.BeanMappingService;
import cz.tyckouni.poopio.service.MonsterService;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.facade.MonsterFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class MonsterFacadeImpl implements MonsterFacade {

	private final MonsterService monsterService;

	private final BeanMappingService beanMappingService;

	@Inject
	public MonsterFacadeImpl(MonsterService monsterService, BeanMappingService beanMappingService) {
		this.monsterService = monsterService;
		this.beanMappingService = beanMappingService;
	}

	@Override
	public List<MonsterDTO> getAllMonsters() {
		return beanMappingService.mapTo(monsterService.getAllMonsters(), MonsterDTO.class);
	}

	@Override
	public Long createMonster(MonsterCreateDTO monster) {
		Monster mappedMonster = beanMappingService.mapTo(monster, Monster.class);
		monsterService.createMonster(mappedMonster);
		return mappedMonster.getId();
	}

	@Override
	public void deleteMonster(Long monsterId) {
		monsterService.deleteMonster(monsterService.findById(monsterId));
	}

	@Override
	public void changeMonsterAgility(MonsterChangeAgilityDTO change) {
		Monster monster = monsterService.findById(change.getMonsterId());
		monster.setAgility(change.getAgility());
	}

	@Override
	public MonsterDTO updateMonster(MonsterUpdateDTO update) {
		Monster monster = monsterService.findById(update.getId());

		if (monster == null) {
			return null;
		}

		monster.setName(update.getName());
		monster.setAgility(update.getAgility());
		monster.setHeight(update.getHeight());
		monster.setWeight(update.getWeight());

		return beanMappingService.mapTo(monster, MonsterDTO.class);
	}

	@Override
	public List<MonsterDTO> getAllForAgility(MonsterAgility agility) {
		return beanMappingService.mapTo(monsterService.getAllForAgility(agility), MonsterDTO.class);
	}

	@Override
	public MonsterDTO findById(Long id) {
		return beanMappingService.mapTo(monsterService.findById(id), MonsterDTO.class);
	}

	@Override
	public MonsterDTO findByName(String name) {
		return beanMappingService.mapTo(monsterService.findByName(name), MonsterDTO.class);
	}

	@Override
	public List<MonsterDTO> getTheMostWidespreadMonsters() {
		return beanMappingService.mapTo(monsterService.getTheMostWidespreadMonsters(), MonsterDTO.class);
	}
}
