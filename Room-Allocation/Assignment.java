package cpsc433;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Assignment {
	
	private Room room;
	private HashSet<Person> people;
	
	public Assignment(){
		this.room = null;
		this.people = null;
	}
	public Assignment(Assignment a){
		this.room = a.getRoom();
		this.people = new HashSet<Person>(a.getPeople());
	}
	public Assignment(Room room, Person per){
		this.room = room;
		this.people = new HashSet<Person>();
		this.people.add(per);
	}
	public Assignment(Room room, HashSet<Person> people ){
		this.room = room;
		this.people = people;
	}
	public Assignment(Room room, ArrayList<Person> people){
		this.room = room;
		HashSet<Person> temp = new HashSet<Person>();
		for(Person per: people){
			temp.add(per);
		}
		this.people = temp;
	}
	public Person randomPerson(){
		Random rand = new Random();
		Integer i = rand.nextInt(people.size());
		return personAt(i);
	}
	public void removePerson(Person p){
		people.remove(p);
	}
	public void addPerson(Person p){
		this.people.add(p);
	}
	public boolean isEmpty(){
		return people.isEmpty();
	}
	public int size(){
		return people.size();
	}
		
	public Person personAt(int index){
		Iterator<Person> people = this.people.iterator();
		int count = 0;
		while(people.hasNext()){
			if(count == index){
				return people.next();
			}
			people.next();
			count++;
		}
		return null;
	}
			
	public boolean contains(Person p){
		return people.contains(p);
	}
	
	public Room getRoom(){
		return room;
	}
	
	public HashSet<Person> getPeople(){
		return people;
	}
	
	@Override 
	public String toString(){
		String a = "[";
		Iterator<Person> it = people.iterator();
		while(it.hasNext()){
			a+= it.next();
			if(it.hasNext()) a+=",";
		}
		return "<" + this.room + "," + a + "] >";  
	}
	
}
