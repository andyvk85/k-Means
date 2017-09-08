package de.andyvk85.java.ml.kmeans.cardata;

public enum Doors {
	FIVE_MORE(6), FOUR(4), THREE(3), TWO(2);
	private double value;
	private static boolean includedInDistanceCalc = true;

	private Doors(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

	public static boolean isIncludedInDistanceCalc() {
		return includedInDistanceCalc;
	}
};