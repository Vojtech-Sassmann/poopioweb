package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.dao.MonsterDao;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.dao.MonsterDao;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Service
public class MonsterServiceImpl implements MonsterService {

	private final MonsterDao monsterDao;

	@Inject
	public MonsterServiceImpl(MonsterDao monsterDao) {
		this.monsterDao = monsterDao;
	}

	@Override
	public List<Monster> getAllMonsters() {
		return monsterDao.getAll();
	}

	@Override
	public void createMonster(Monster monster) {
		monsterDao.create(monster);
	}

	@Override
	public List<Monster> getTheMostWidespreadMonsters() {
		List<Monster> allMonsters = monsterDao.getAll();
		int maximumOccurrence = 0;
		for (Monster monster : allMonsters) {
			if (monster.getAreas().size() > maximumOccurrence) {
				maximumOccurrence = monster.getAreas().size();
			}
		}
		int finalMaximumOccurrence = maximumOccurrence;
		return allMonsters.stream()
				.filter(monster -> monster.getAreas().size() == finalMaximumOccurrence)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteMonster(Monster monster) {
		monsterDao.delete(monster);
	}

	@Override
	public Monster findById(Long id) {
		return monsterDao.findById(id);
	}

	@Override
	public Monster findByName(String name) {
		return monsterDao.findByName(name);
	}

	@Override
	public List<Monster> getAllForAgility(MonsterAgility agility) {
		return monsterDao.getAllForAgility(agility);
	}
}
