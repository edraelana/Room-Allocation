package cpsc433;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;


public class Generation {
	
	public int genNumber;
	public HashSet<Node> facts;
	
	public Generation(int number){
		this.genNumber = number;
		this.facts = new HashSet<Node>();
	}
	
	public void addFact(LinkedHashMap<String, Assignment> a, LinkedHashMap<String, String> b){
		Node nodeN = new Node(a);
		nodeN.putStrings(b);
		this.facts.add(nodeN);
	}

	public void addFact(Node n){
		this.facts.add(n);
	}
	public void removeFact(Node n){
		this.facts.remove(n);
	}
	
	public void addFact(HashSet<Node> set){
		this.facts = set;
	}
	
	public Node factAt(int index){
		Iterator<Node> nodes = facts.iterator();
		int count = 0;
		while(nodes.hasNext()){
			if(count == index){
				return nodes.next();
			}
			nodes.next();
			count++;
		}
		return null;
	}
	public int size(){
		return facts.size();
	}
	
	public void mutate(int numGenSwap, int numFactSwap, int numChange, Environment e){
		Random rand = new Random();
		for(int i = 0; i < numGenSwap; i++){
			this.swap(e);
		}
		for(int i = 0; i < numFactSwap; i++){
			addFact(factAt(rand.nextInt(size())).changeRooms(e));
		}
		for(int i = 0; i < numChange; i++){
			addFact(factAt(rand.nextInt(size())).swapRooms(e));
		}
		genNumber++;
	}
	
	public void swap(Environment e){
		Random rand = new Random();
		int num1 = rand.nextInt(size());
		int num2;
		do{
			num2 = rand.nextInt(size());
		}while(num2 == num1);
		
		Node n1 = new Node(factAt(num2));
		Node n2 = new Node(factAt(num1));
		Person p;
		Assignment a1;
		Room room;
		a1 = n1.peopleAt(rand.nextInt(n1.numRooms()));
		p = a1.randomPerson();
		room = n2.getRoom(p);
		
		n1.remove(p, a1.getRoom());
		n1.put(p, room);
		
		n2.remove(p, room);
		n2.put(p, a1.getRoom());
		
		addFact(n1);
		addFact(n2);
	}

	public Node bestNode(){
		Iterator<Node> nodes = facts.iterator();
		Node maxNode = nodes.next(); 
		while(nodes.hasNext()){
			Node n = nodes.next();
			if(n.score > maxNode.score){
				maxNode = n;
			}	
		}
		return maxNode;
	}
	
	public void evaluate(Environment e, Constraints c){
		Iterator<Node> nodes = facts.iterator();
		while(nodes.hasNext()){
			Node n = nodes.next();
			n.setScore(c.eval(n, e));
		}
	}
		
	/**
	 * A function that returns an Array filled with Nodes that are the top
	 * percentile of the generation
	 *
	 * @param	percentage	The portion of the best nodes in a generation
	 *						you want returned in the array
	 *
	 * @return	best		The array containing 
	 */
	public Node[] getBestOfGen(float percentage){
		Node[] best = new Node[Math.round(facts.size() * percentage)];
		
		Node[] sorted = sortGen();
		
		int j = facts.size() - 1;
		for (int i = 0; i < best.length; i++){
			best[i] = new Node(sorted[j]);
			j--;
		}
		
		return best;
	}
		
	public Node[] getWorstOfGen(float percentage){
		Node[] worst = new Node[Math.round(facts.size() * percentage)];
		
		Node[] sorted = sortGen();
		
		for (int i =0; i < worst.length; i++){
			worst[i] = new Node(sorted[i]);
		}
		
		return worst;
	}
	
	private Node[] sortGen(){
		Node[] nArray = new Node[facts.size()];
		Object[] oArray = facts.toArray();
		
		for (int i = 0; i < oArray.length; i++){
			nArray[i] = (Node) oArray[i];
		}
		
		NodeQuickSort.sort(nArray, 0, nArray.length - 1);
//		for(int i = 0; i < nArray.length; i++){
//			System.out.print(nArray[i].score+ " ");
//			
//		}
//		System.out.println();
		return nArray;
	}
		
	@Override
	public String toString(){
		Iterator<Node> nodes = facts.iterator();
		String gen = "Generation " + genNumber + "\n{";
		while(nodes.hasNext()){
			gen += nodes.next();
			if(nodes.hasNext()) gen+=",\n";
		}
		gen+="}";
		return gen;
	}
	
	public String altToString(){
		Iterator<Node> nodes = facts.iterator();
		String gen = "Generation " + genNumber + "\n{";
		while(nodes.hasNext()){
			gen += nodes.next().altToString();
			if(nodes.hasNext()) gen+=",\n";
		}
		gen+="}";
		return gen;
		
	}
			
}
