package de.andyvk85.java.ml.kmeans.cardata;

public enum Persons {
	MORE(6), FOUR(4), TWO(2);
	private double value;
	private static boolean includedInDistanceCalc = true;

	private Persons(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	public static boolean isIncludedInDistanceCalc() {
		return includedInDistanceCalc;
	}
};