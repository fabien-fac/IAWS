package org.iaws.classes;

public class LikeUnlike {

	private int like;
	private int unlike;
	
	public LikeUnlike(int nbLike, int nbUnlike){
		this.like = nbLike;
		this.unlike = nbUnlike;
	}
	
	public int getLike() {
		return like;
	}
	
	public int getUnlike() {
		return unlike;
	}
}