package de.andyvk85.java.ml.kmeans.cardata;

public enum Safety {
	HIGH(15), MED(10), LOW(5);
	private double value;
	private static boolean includedInDistanceCalc = true;

	private Safety(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public static boolean isIncludedInDistanceCalc() {
		return includedInDistanceCalc;
	}
};