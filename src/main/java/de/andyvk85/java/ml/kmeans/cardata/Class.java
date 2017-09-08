package de.andyvk85.java.ml.kmeans.cardata;

public enum Class {
	UNACC(0), ACC(1), GOOD(2), VGOOD(3);
	private double value;
	private static boolean includedInDistanceCalc = false;

	private Class(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public static boolean isIncludedInDistanceCalc() {
		return includedInDistanceCalc;
	}
};
