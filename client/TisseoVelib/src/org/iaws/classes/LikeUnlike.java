package org.iaws.classes;

public class LikeUnlike {

	@Override
	public String toString() {
		return "LikeUnlike [like=" + like + ", unlike=" + unlike + "]";
	}

	private String id;
	private int like;
	private int unlike;
	private String rev;
	
	public LikeUnlike(String id,int nbLike, int nbUnlike, String rev){
		this.id = id;
		this.like = nbLike;
		this.unlike = nbUnlike;
		this.rev = rev;
	}
	
	public int getLike() {
		return like;
	}
	
	public int getUnlike() {
		return unlike;
	}
	
	public String getRev() {
		return rev;
	}

	public String getId() {
		return id;
	}

	
}
