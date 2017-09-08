package de.andyvk85.java.ml.kmeans.filehandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import de.andyvk85.java.ml.kmeans.cardata.CarDataStructure;
import de.andyvk85.java.ml.kmeans.datastructures.Observation;

public class FileUtils {
	private static ArrayList<Observation> dataSet = new ArrayList<Observation>();

	public static ArrayList<Observation> readData(String filePath) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(filePath));
		int id=1;

		while (reader.hasNext()) {
			String line = reader.nextLine();
			String[] parts = line.split(",");
			Observation instance = new Observation();

			// traverse over preassigned data structure
			for (CarDataStructure attribute : CarDataStructure.values()) {
				instance.addAttribute(attribute.name(), attribute.convert(parts[attribute.getValue()]));
			}
			
			dataSet.add(instance);
			instance.setId(id);
			id++;
		}
		
		reader.close();
		return dataSet;
	}
}
