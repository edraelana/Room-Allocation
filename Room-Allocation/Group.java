package cpsc433;

import java.util.HashSet;
import java.util.Iterator;

public class Group{
	private String name;	// Name of the group
	private HashSet<String> heads;	// Name of the person who heads the group
	private HashSet<String> members;	// A HashSet of all the members of the group
	
	public Group(String name){	// Constructor
		this.name = name;
		heads = new HashSet<String>();
		members = new HashSet<String>();
	}
	
	public String getName(){	// Returns the name of the group
		return name;
	}
	
	public void addHead(String person){	// Sets the head of the group
		heads.add(person);
	}
	public java.util.Iterator<String> getHeadIterator(){
		return heads.iterator();
	}
	
	public boolean isHead(String person){	// A method that checks if the given person heads the group
		return heads.contains(person);
	}
	
	public void addMember(String person){	// Adds a person to the HashSet. If they already exist, they are not added to 
		members.add(person);		// the set
	}
	
	public boolean hasMember(String person){	// Evaluates if the given person is apart of the 'members' HashSet
		return members.contains(person);
	}
	
	public Iterator<String> membersIterator(){
		java.util.Iterator<String> it = members.iterator();
		return it;
	}
	public HashSet<String> getMembers(){
		return members;
	}
	public HashSet<String> getHeads(){
		return heads;
	}
}

