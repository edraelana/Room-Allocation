package cpsc433;

import java.util.HashSet;

public class Person {
	public String name;
	public boolean smokes;
	public boolean hacks;
	private HashSet<String> roles;
	private HashSet<String> coWorkers;
	
	public Person(String name){
		this.name = name;
		roles = new HashSet<String>();
		coWorkers = new HashSet<String>();
	}
	
	public void addRole(String role){
		roles.add(role);
	}
	
	public boolean hasRole(String role){	// This method returns true if this person has the role specified by the argument
		if(roles.contains(role)){	// given. 'role' should be one of "secretary", "manager", or "researcher".
			return true;
		}
		else{
			return false;
		}
	}
	
	public java.util.Iterator<String> rolesIterator(){
		return roles.iterator();
	}
	
	// This method accepts a person 'p' and adds them to the 'coWorker' Set via the person's name. Since this is a HashSet, it
	// will only add the person's name if it is not already in the set.
	public void addCoWorker(String p){
		coWorkers.add(p);
	}
	
	public boolean hasCoWorker(String p){
		return coWorkers.contains(p);
	}
	
	public java.util.Iterator<String> coWorkerIterator(){
		return coWorkers.iterator();
	}
	@Override
	public String toString(){
		return name;
	}
}
