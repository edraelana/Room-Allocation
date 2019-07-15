package cpsc433;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import cpsc433.Predicate.ParamType;

import java.io.*;

/**
 * This is class extends {@link cpsc433.PredicateReader} just as required to in
 * the assignment. You can extend this class to include your predicate
 * definitions or you can create another class that extends
 * {@link cpsc433.PredicateReader} and use that one.
 * <p>
 * I have defined this class as a singleton.
 * 
 * <p>
 * Copyright: Copyright (c) 2003-16, Department of Computer Science, University
 * of Calgary. Permission to use, copy, modify, distribute and sell this
 * software and its documentation for any purpose is hereby granted without fee,
 * provided that the above copyright notice appear in all copies and that both
 * that copyright notice and this permission notice appear in supporting
 * documentation. The Department of Computer Science makes no representations
 * about the suitability of this software for any purpose. It is provided "as
 * is" without express or implied warranty.
 * </p>
 *
 * @author <a href="http://www.cpsc.ucalgary.ca/~kremer/">Rob Kremer</a>
 *
 */
public class Environment extends PredicateReader implements SisyphusPredicates {

	private static Environment instance = null;
	protected boolean fixedAssignments = false;
	private LinkedHashMap<String, Person> people;
	private LinkedHashMap<String, Room> rooms;
	public LinkedHashMap<String, String> assignments;
	private LinkedHashMap<String, Group> groups;
	public LinkedHashMap<String, Project> projects;
	private HashSet<String> heads;
	

	public LinkedHashMap<String, Person> getPeople(){
		return this.people;
	}
	
	public LinkedHashMap<String, Room> getRooms(){
		return this.rooms;
	}
	
	public LinkedHashMap<String, Group> getGroups(){
		return this.groups;
	}
	
	public LinkedHashMap<String, Project> getProjects(){
		return this.projects;
	}
	public LinkedHashMap<String, String> getAssignments(){
		return this.assignments;
	}
	public HashSet<String> getHeads(){
		return this.heads;
	}
	
	protected Environment(String name) {
		super(name == null ? "theEnvironment" : name);
		people = new LinkedHashMap<String, Person>();
		rooms = new LinkedHashMap<String, Room>();
		assignments = new LinkedHashMap<String, String>();
		groups = new LinkedHashMap<String, Group>();
		projects = new LinkedHashMap<String, Project>();
		heads = new HashSet<String>();
	}

	/**
	 * A getter for the global instance of this class. If an instance of this
	 * class does not already exist, it will be created.
	 * 
	 * @return The singleton (global) instance.
	 */
	public static Environment get() {
		if (instance == null)
			instance = new Environment(null);
		return instance;
	}

	// UTILITY PREDICATES

	/**
	 * The help text for the exit() predicate.
	 */
	public static String h_exit = "quit the program";

	/**
	 * The definition of the exit() assertion predicate. It will exit the
	 * program abruptly.
	 */
	public void a_exit() {
		System.exit(0);
	}

	@Override
	public void a_person(String p) {

		if (!e_person(p)) {
			people.put(p, new Person(p));
		}
	}

	@Override
	public boolean e_person(String p) {

		return people.containsKey(p);
	}

	@Override
	public void a_secretary(String p) {

		if (!e_secretary(p)) {
			a_person(p);
			people.get(p).addRole("secretary");
		}
	}

	@Override
	public boolean e_secretary(String p) {

		return e_person(p) && people.get(p).hasRole("secretary");
	}

	@Override
	public void a_researcher(String p) {

		if (!e_researcher(p)) {
			a_person(p);
			people.get(p).addRole("researcher");
		}
	}

	@Override
	public boolean e_researcher(String p) {

		return e_person(p) && people.get(p).hasRole("researcher");
	}

	@Override
	public void a_manager(String p) {

		a_person(p);
		if (!e_manager(p)) { // Check if the person 'p' has the role "manager".
								// If not, give him the role "manager".
			people.get(p).addRole("manager");
			heads.add(p);
		}
	}

	@Override
	public boolean e_manager(String p) {

		return e_person(p) && people.get(p).hasRole("manager");
	}

	@Override
	public void a_smoker(String p) {

		a_person(p);
		people.get(p).smokes = true;
	}

	@Override
	public boolean e_smoker(String p) {

		return e_person(p) && people.get(p).smokes;
	}

	@Override
	public void a_hacker(String p) {

		a_person(p);
		people.get(p).hacks = true;
	}

	@Override
	public boolean e_hacker(String p) {

		return e_person(p) && people.get(p).hacks;
	}

	@Override
	public void a_group(String p, String grp) {

		a_person(p);
		a_group(grp);

		groups.get(grp).addMember(p);
	}

	@Override
	public boolean e_group(String p, String grp) {

		if ((!people.containsKey(p)) || (!groups.containsKey(grp))) {
			return false;
		}
		return groups.get(grp).hasMember(p);
	}

	@Override
	public void a_project(String p, String prj) {

		a_person(p);
		a_project(prj);

		projects.get(prj).addMember(p);
	}

	@Override
	public boolean e_project(String p, String prj) {

		return e_person(p) && e_project(prj) && projects.get(prj).hasMember(p);
	}

	@Override
	public void a_heads_group(String p, String grp) {

		a_group(p, grp);
		groups.get(grp).addHead(p);
		heads.add(p);
	}

	@Override
	public boolean e_heads_group(String p, String grp) {

		return e_person(p) && e_group(grp) && groups.get(grp).isHead(p);
	}

	@Override
	public void a_heads_project(String p, String prj) {

		a_project(p, prj);
		projects.get(prj).addHead(p);
		heads.add(p);
	}

	@Override
	public boolean e_heads_project(String p, String prj) {

		return e_person(p) && e_project(prj) && projects.get(prj).isHead(p);
	}

	@Override
	public void a_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s) {

		java.util.Iterator<Pair<ParamType, Object>> it = p2s.iterator();
		while (it.hasNext()) {
			String p2 = (String) it.next().getValue();
			a_works_with(p, p2);
		}
	}

	@Override
	public boolean e_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s) {

		java.util.Iterator<Pair<ParamType, Object>> it = p2s.iterator();
		while (it.hasNext()) {
			String p2 = (String) it.next().getValue();
			if (!e_works_with(p, p2)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void a_works_with(String p, String p2) {

		a_person(p);
		a_person(p2);

		people.get(p).addCoWorker(p2);
		people.get(p2).addCoWorker(p);
	}

	@Override
	public boolean e_works_with(String p, String p2) {

		return e_person(p) && e_person(p2) && people.get(p).hasCoWorker(p2);
	}

	@Override
	public void a_assign_to(String p, String room) throws Exception {

		a_person(p);
		a_room(room);
		assignments.put(p, room);
	}

	@Override
	public boolean e_assign_to(String p, String room) {

		return assignments.containsKey(p) && assignments.get(p).equals(room);
	}

	@Override
	public void a_room(String r) {

		if (!e_room(r)) {
			rooms.put(r, new Room(r));
		}
	}

	@Override
	public boolean e_room(String r) {

		return rooms.containsKey(r);
	}

	@Override
	public void a_close(String room, String room2) {

		a_room(room);
		a_room(room2);
		rooms.get(room).addNeighbour(room2);
		rooms.get(room2).addNeighbour(room);
	}

	@Override
	public boolean e_close(String room, String room2) {

		return e_room(room) && e_room(room2) && (rooms.get(room).neighbour(room2) || rooms.get(room2).neighbour(room));
	}

	@Override
	public void a_close(String room, TreeSet<Pair<ParamType, Object>> set) {

		java.util.Iterator<Pair<ParamType, Object>> it = set.iterator();
		while (it.hasNext()) {
			String r = (String) it.next().getValue();
			a_close(room, r);
		}
	}

	@Override
	public boolean e_close(String room, TreeSet<Pair<ParamType, Object>> set) {

		java.util.Iterator<Pair<ParamType, Object>> it = set.iterator();
		while (it.hasNext()) {
			String r = (String) it.next().getValue();
			if (!e_close(room, r)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void a_large_room(String r) {

		a_room(r);
		rooms.get(r).setRoomSize('l');
	}

	@Override
	public boolean e_large_room(String r) {

		return e_room(r) && rooms.get(r).getRoomSize() == 'l';
	}

	@Override
	public void a_medium_room(String r) {

		a_room(r);
		rooms.get(r).setRoomSize('m');
	}

	@Override
	public boolean e_medium_room(String r) {

		return e_room(r) && rooms.get(r).getRoomSize() == 'm';
	}

	@Override
	public void a_small_room(String r) {

		a_room(r);
		rooms.get(r).setRoomSize('s');
	}

	@Override
	public boolean e_small_room(String r) {

		return e_room(r) && rooms.get(r).getRoomSize() == 's';
	}

	@Override
	public void a_group(String g) {

		if (!groups.containsKey(g)) {
			groups.put(g, new Group(g));
		}
	}

	@Override
	public boolean e_group(String g) {

		return groups.containsKey(g);
	}

	@Override
	public void a_project(String p) {

		if (!e_project(p)) {
			projects.put(p, new Project(p));
		}
	}

	@Override
	public boolean e_project(String p) {

		return projects.containsKey(p);
	}

	@Override
	public void a_large_project(String prj) {

		a_project(prj);
		projects.get(prj).setLargeProj(true);
	}

	@Override
	public boolean e_large_project(String prj) {

		return e_project(prj) && projects.get(prj).isLarge();
	}

	public void createOutputFile(String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.write(personInfo());
			writer.write(roomInfo());
			writer.write(groupInfo());
			writer.write(projectInfo());
			for (String p : assignments.keySet()) {
				writer.write("assign-to(" + p + "," + assignments.get(p) + ")\n");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("IO WRITER ERROR OCCURED");
		}
	}

	public String personInfo() {
		String info = "";
		for (String name : people.keySet()) {
			info += "person(" + name + ")\n";
			if (people.get(name).smokes) {
				info += "smoker(" + name + ")\n";
			}
			if (people.get(name).hacks) {
				info += "hacker(" + name + ")\n";
			}
			java.util.Iterator<String> it = people.get(name).rolesIterator();
			while (it.hasNext()) {
				info += it.next() + "(" + name + ")\n";
			}
			it = people.get(name).coWorkerIterator();
			while (it.hasNext()) {
				info += "works-with(" + name + "," + it.next() + ")\n";
			}
			info += "\n"; // Create a new line so that it is easier to read.
		}
		return info;
	}

	public String roomInfo() {
		String info = "";
		for (String r : rooms.keySet()) {
			info += "room(" + r + ")\n";
			switch (rooms.get(r).getRoomSize()) {
			case 's':
				info += "small-room(" + r + ")\n";
				break;
			case 'm':
				info += "medium-room(" + r + ")\n";
				break;
			case 'l':
				info += "large-room(" + r + ")\n";
				break;
			}
			java.util.Iterator<String> it = rooms.get(r).closeToIterator();
			while (it.hasNext()) {
				info += "close(" + r + "," + it.next() + ")\n";
			}
			info += "\n";
		}

		return info;
	}

	public String groupInfo() {
		String info = "";
		for (String g : groups.keySet()) {
			info += "group(" + g + ")\n";
			java.util.Iterator<String> it = groups.get(g).getHeadIterator();
			while (it.hasNext()) {
				info += "heads-group(" + it.next() + "," + g + ")\n";
			}
			it = groups.get(g).membersIterator();
			while (it.hasNext()) {
				info += "group(" + it.next() + "," + g + ")\n";
			}
			info += "\n";
		}
		return info;
	}

	public String projectInfo() {
		String info = "";
		for (String p : projects.keySet()) {
			info += "project(" + p + ")\n";
			java.util.Iterator<String> it = projects.get(p).getHeadIterator();
			while (it.hasNext()) {
				info += "heads-project(" + it.next() + "," + p + ")\n";
			}
			info += (projects.get(p).isLarge()) ? "large-project(" + p + ")\n" : "";

			it = projects.get(p).membersIterator();
			while (it.hasNext()) {
				info += "project(" + it.next() + "," + p + ")\n";
			}
			info += "\n";
		}
		return info;
	}
}
