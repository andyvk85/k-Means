package de.andyvk85.java.ml.kmeans.datastructures;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import de.andyvk85.java.ml.kmeans.cardata.CarDataStructure;

public class Observation implements Cloneable {
	Hashtable<String, Double> attributes;
	private int meanAffiliation;
	private int id;

	public Observation() {
		super();
		attributes = new Hashtable<String, Double>();
		meanAffiliation = -1;	//-1 means that it's not assigned to a mean
	}
	
	public int getMeanAffiliation() {
		return meanAffiliation;
	}

	public void setMeanAffiliation(int meanAffiliation) {
		this.meanAffiliation = meanAffiliation;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addAttribute(String name, double value) {
		attributes.put(name, value);
	}

	public double getAttributeValue(String name) {
		return attributes.get(name);
	}
	
	public void setAttributeValue(String name, double value) {
		attributes.put(name, value);
	}
	
	public void getAllAttributePairs() {
		Set<String> keys = attributes.keySet();
		Iterator<String> itr = keys.iterator();
		String key;

		while (itr.hasNext()) {
			key = itr.next();
			System.out.println((key + " " + attributes.get(key)));
		}
	}
	
	public double getClassAttributeValue() {
		double attributeValue = -1;
		
		for (CarDataStructure attribute : CarDataStructure.values()) {
			if(!attribute.isIncludedInDistanceCalc()) { //find the class attribute
				attributeValue = attributes.get(attribute.name());
			}
		}

		return attributeValue;
	}
	
	@Override
    public Observation clone() throws CloneNotSupportedException {
		Observation cloned = new Observation();
		Set<String> keys = attributes.keySet();
		Iterator<String> itr = keys.iterator();
		String key;
		
		while (itr.hasNext()) { 
		       key = itr.next();
		       cloned.addAttribute(key, attributes.get(key));
		}
		
		cloned.setMeanAffiliation(getMeanAffiliation());

        return cloned;
    }
	
}
