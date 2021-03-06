package de.andyvk85.java.ml.kmeans.cardata;

public enum Buying {
	VHIGH(15), HIGH(10), MED(5), LOW(0);
	private double value;
	private static boolean includedInDistanceCalc = true;

	private Buying(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

	public static boolean isIncludedInDistanceCalc() {
		return includedInDistanceCalc;
	}
};