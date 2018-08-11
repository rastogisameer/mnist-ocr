package com.syntel.ecm.ocr.classification;

public class Neighbour implements Comparable<Neighbour> {
	private String label;
	private double distance;
	private int vote = 0;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	public Neighbour(){
		
	}
	public Neighbour(String label, double distance){
		this.label = label;
		this.distance = distance;
	}

	@Override
	public int compareTo(Neighbour node) {
		// TODO Auto-generated method stub
				
		
		if(this.vote == 0 || node.vote == 0)
			return Double.compare(this.distance, node.distance);//(int) (this.distance - node.distance);
		return Double.compare(this.distance / this.vote, node.distance / node.vote);//((int) (this.distance / this.vote - node.distance / node.vote);
		
	}
	@Override
	public boolean equals(Object o){
		
		return this.label == ((Neighbour)o).label;
	}
	public void vote(){
		this.vote++;
	}
}
