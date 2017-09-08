package de.andyvk85.java.ml.kmeans.cardata;

public enum Lug_boot {
	BIG(15), MED(10), SMALL(5);
	private double value;
	private static boolean includedInDistanceCalc = true;

	private Lug_boot(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

	public static boolean isIncludedInDistanceCalc() {
		return includedInDistanceCalc;
	}
};