package org.iaws.classes;

public class CalculDistance {

	public double calculVolDOiseau(long lat1, long lat2, long lon1, long lon2){
		double dist_metres = 11*10000*Math.sqrt(Math.pow(lat1-lat2,2)+Math.pow(lon1-lon2,2));
		return dist_metres;
	}
}
