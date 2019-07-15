package cpsc433;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;


public class Node {
	
	public LinkedHashMap<String, String> StringAssignments;
	public LinkedHashMap<String, Assignment> Assignments;
	public int score;
	
	public Node(){
		this.Assignments = new LinkedHashMap<String, Assignment>();
		this.StringAssignments = new LinkedHashMap<String, String>();
	}
	public Node(LinkedHashMap<String, Assignment> assigns){
		this.Assignments = assigns;
		this.score = 0;
	}
	public Node (Node n){
		this.Assignments = new LinkedHashMap<String, Assignment>();
		this.StringAssignments = new LinkedHashMap<String,String>();
		this.score = n.score;
		Iterator<String> assigns = n.Assignments.keySet().iterator();
		while(assigns.hasNext()){
			String room = assigns.next();
			this.Assignments.put(room,new Assignment(n.getAssignment(room)));
		}
		assigns = n.StringAssignments.keySet().iterator();
		while(assigns.hasNext()){
			String person = assigns.next();
			this.StringAssignments.put(person, n.getRoom(person));
		}
	}
	public void setScore(int score){
		this.score = score;
	}
	public int numRooms(){
		return Assignments.size();
	}
	public int numPeople(){
		return StringAssignments.size();
	}
	public void putStrings(LinkedHashMap<String, String> a){
		this.StringAssignments = a;
	}
	public Assignment peopleAt(int index){
		Iterator<String> assigns = Assignments.keySet().iterator();
		int count = 0;
		while(assigns.hasNext()){
			if(count == index){
				return Assignments.get(assigns.next());
			}
			assigns.next();
			count++;
		}
		return null;
	}
	public Room roomAt(int index){
		Iterator<Assignment> rooms = Assignments.values().iterator();
		int count = 0;
		while(rooms.hasNext()){
			if(count == index){
				return rooms.next().getRoom();
			}
			rooms.next();
			count++;
		}
		return null;
	}
	public Room getRoom(Person person){
		return Assignments.get(getRoom(person.name)).getRoom();
	}
	
	public String getRoom(String person){
		return StringAssignments.get(person);
	}
	public Assignment getAssignment(String r){
		return Assignments.get(r);
	}
	
	public void put(Person p, Room r){
		StringAssignments.put(p.name, r.getRoomNumber());
		if(!Assignments.containsKey(r.getRoomNumber())){
			Assignments.put(r.getRoomNumber(), new Assignment(r,p));
		}else{
			Assignments.get(r.getRoomNumber()).addPerson(p);
		}
	}
	public void remove(Person p, Room r){
		StringAssignments.remove(p.name);
		Assignments.get(r.getRoomNumber()).removePerson(p);
		if(Assignments.get(r.getRoomNumber()).isEmpty()){
			Assignments.remove(r.getRoomNumber());
		}
	}
	public Node changeRooms(Environment e){
		Node newNode = new Node(this);
		
		Random rand = new Random();
		Room room;
		Room room2;
		Person person;
		Assignment a;

		room = roomAt(rand.nextInt(numRooms()));
		a = Assignments.get(room.getRoomNumber());
		room2 = roomAt(rand.nextInt(numRooms()));
		
		person = Assignments.get(room.getRoomNumber()).randomPerson();
		
		newNode.remove(person, room);
		newNode.put(person, room2);	
		return newNode;
	}
	
	public Node swapRooms(Environment e){
		Node newNode = new Node(this);
		
		Random rand = new Random();
		Room room1;
		Room room2;
		Person person1;
		Person person2;
		
		room1 = roomAt(rand.nextInt(numRooms()));
		room2 = roomAt(rand.nextInt(numRooms()));
		person1 = Assignments.get(room1.getRoomNumber()).randomPerson();
		person2 = Assignments.get(room2.getRoomNumber()).randomPerson();
		
		newNode.remove(person1,room1);
		newNode.put(person1,room2);
		newNode.remove(person2, room2);
		newNode.put(person2, room1);		
		return newNode;
	}
	
	@Override
	public String toString(){
		Iterator<Assignment> it = Assignments.values().iterator();
		String node = this.score + "\n";
		while(it.hasNext()){
			node += it.next().toString();
			if(it.hasNext()) node+=",";
		}
		return node;
	}
	
	public String altToString(){
		Iterator<String> it = StringAssignments.keySet().iterator();
		String node = "";
		while(it.hasNext()){
			String person = it.next();
			node+= "(" + person +"," + StringAssignments.get(person) + ")";
			if(it.hasNext()) node+= ",";
		}
		
		return node;
	}
}

