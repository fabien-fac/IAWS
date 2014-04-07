package org.iaws.comparator;

import java.util.Comparator;

import org.iaws.classes.Poteau;

public class PoteauTimeComparator implements Comparator<Poteau>{

	@Override
	public int compare(Poteau p1, Poteau p2) {
		Integer i1 = Integer.valueOf(p1.getTempsMinute());
		return i1.compareTo(p2.getTempsMinute());
	}

}
