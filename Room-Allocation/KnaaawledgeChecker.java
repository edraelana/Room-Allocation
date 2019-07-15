package cpsc433;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class KnaaawledgeChecker {

	public KnaaawledgeChecker() {
	}

	public boolean checkTwoToARoom(Assignment a) {
		if (a.getPeople().size() >= 3) {
			return false;
		}
		return true;
	}

	public boolean checkManager(Assignment a, Environment env) {
		Iterator<Person> it = null;
		Person person = null;
		HashSet<Person> people = a.getPeople();

		// because we check this, by default if one of them is a manager, we
		// KNOW it's going to fail
		if (people.size() > 1) {
			it = people.iterator();
			while (it.hasNext()) {
				person = (Person) it.next();
				// Check if the current person is a manager
				if (env.e_manager(person.name)) {
					return false;
				}

				// Check if this person is a group head
				for (Map.Entry<String, Group> group : env.getGroups().entrySet()) {
					Group g = group.getValue();
					if (env.e_heads_group(person.name, g.getName())) {
						return false;
					}
				}

				// Check if this person is a project head
				for (Map.Entry<String, Project> project : env.projects.entrySet()) {
					Project p = project.getValue();
					if (env.e_heads_project(person.name, p.getName())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean checkSofties(Assignment a, Environment e) {
		boolean ret = false;
		int score = 0;

		HashSet<Person> people = a.getPeople();
		Room assigned = a.getRoom();
		boolean secretary = false;
		boolean smoker = false;
		boolean hacker = false;

		// check constraint 16
		if (assigned.getRoomSize() == 's') {
			if (people.size() > 1) {
				score -= 25;
			}
		}
		for (Person per : people) {
			// check constraint 1
			for (Group grp : e.getGroups().values()) {
				if (grp.getHeads().contains(per)) {
					if (assigned.getRoomSize() != 'l') {
						score -= 40;
						break;
					}
				}
			}
			// check constraint 4
			if (per.hasRole("secretary")) {
				secretary = true;
			}
			if (!per.hasRole("secretary")) {
				if (secretary) {
					score -= 5;
				}
			}
			// check constraint 11
			if (per.smokes) {
				smoker = true;
			}
			if (!per.smokes) {
				if (smoker) {
					score -= 50;
				}
			}
			// check constraint 13
			if (per.hacks && !per.hasRole("secretary")) {
				hacker = true;
			}
			if (!per.hacks && !per.hasRole("secretary")) {
				if (hacker) {
					score -= 2;
				}
			}
		}

		if (score > -70) {
			ret = true;
		}
		return ret;
	}

}
