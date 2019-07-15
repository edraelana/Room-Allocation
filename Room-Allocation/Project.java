package cpsc433;

public class Project extends Group{
	private boolean largeProject;
	//private HashSet<String> heads;	// Name of the person who heads the group
	//private HashSet<String> members;	// A HashSet of all the members of the group
	
	public Project(String name){
		super(name);
		largeProject = false;
	}
	
	public void setLargeProj(boolean b){
		largeProject = b;
	}
	
	public boolean isLarge(){
		return largeProject;
	}
	/*public void addHead(String person){	// Sets the head of the group
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

	public HashSet<String> getHeads() {
		return heads;
	}

	public HashSet<String> getMembers() {
		return members;
	}*/
}

