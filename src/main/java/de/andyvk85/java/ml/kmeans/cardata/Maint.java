package de.andyvk85.java.ml.kmeans.cardata;

public enum Maint {
	VHIGH(15), HIGH(10), MED(5), LOW(0);
	private double value;
	private static boolean includedInDistanceCalc = true;

	private Maint(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public static boolean isIncludedInDistanceCalc() {
		return includedInDistanceCalc;
	}
};
