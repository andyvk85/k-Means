package de.andyvk85.java.ml.kmeans.cardata;

public enum CarDataStructure {
	BUYING(0), MAINT(1), DOORS(2), PERSONS(3), LUG_BOOT(4), SAFETY(5), CLASS(6);
	private int value;
	private static int COUNT = CarDataStructure.values().length;

	private CarDataStructure(int value) {
		this.value = value;
	}

	public double convert(String s) {
		switch (this) {
		case BUYING:
			return Buying.valueOf(s.toUpperCase()).getValue();
		case MAINT:
			return Maint.valueOf(s.toUpperCase()).getValue();
		case DOORS:
			switch (s) {
			case "2":
				return Doors.valueOf("two".toUpperCase()).getValue();
			case "3":
				return Doors.valueOf("three".toUpperCase()).getValue();
			case "4":
				return Doors.valueOf("four".toUpperCase()).getValue();
			case "5more":
				return Doors.valueOf("five_more".toUpperCase()).getValue();
			}
		case PERSONS:
			switch (s) {
			case "2":
				return Persons.valueOf("two".toUpperCase()).getValue();
			case "4":
				return Persons.valueOf("four".toUpperCase()).getValue();
			case "more":
				return Persons.valueOf("more".toUpperCase()).getValue();
			}
		case LUG_BOOT:
			return Lug_boot.valueOf(s.toUpperCase()).getValue();
		case SAFETY:
			return Safety.valueOf(s.toUpperCase()).getValue();
		case CLASS:
			return Class.valueOf(s.toUpperCase()).getValue();
		default:
			throw new AssertionError("Unknown member " + this);
		}
	}

	public int getValue() {
		return value;
	}

	public static int getCOUNT() {
		return COUNT;
	}
	
	public boolean isIncludedInDistanceCalc() {
		switch (this) {
		case BUYING:
			return Buying.isIncludedInDistanceCalc();
		case MAINT:
			return Maint.isIncludedInDistanceCalc();
		case DOORS:
			return Doors.isIncludedInDistanceCalc();
		case PERSONS:
			return Persons.isIncludedInDistanceCalc();
		case LUG_BOOT:
			return Lug_boot.isIncludedInDistanceCalc();
		case SAFETY:
			return Safety.isIncludedInDistanceCalc();
		case CLASS:
			return Class.isIncludedInDistanceCalc();
		default:
			throw new AssertionError("Unknown member " + this);
		}
	}

};