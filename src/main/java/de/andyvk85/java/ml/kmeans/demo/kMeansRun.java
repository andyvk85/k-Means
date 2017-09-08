package de.andyvk85.java.ml.kmeans.demo;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import de.andyvk85.java.ml.kmeans.cardata.*;
import de.andyvk85.java.ml.kmeans.cardata.Class;
import de.andyvk85.java.ml.kmeans.datastructures.*;
import de.andyvk85.java.ml.kmeans.filehandling.*;

public class kMeansRun {
	private static ArrayList<Observation> dataSet;

	public static void main(String[] args) {
		
		/*
		 *  program expects k (#clusters) as first command line argument
		 *  (we do not care about malicious inputs!)
		 */
		int k = 0;
		if(args.length != 0) {
			k = Integer.parseInt(args[0]);
		}
		if(!(k>1)) {
			System.out.println("wrong input! please determine the k param (k>1)");
			System.out.println("use: java -jar ML-P04.jar [integer for k]");
			return;
		}
		
		/*
		 * load data and separate it by attributes / columns
		 */
		try {
			dataSet = FileUtils.readData("cardata/car.data");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		/* ##################################################
		 * 1. initialization
		 * ##################################################
		 */
		ArrayList<Observation> means = new ArrayList<Observation>(k);
		ArrayList<Integer> rndNumbers = new ArrayList<Integer>(k);
		int min = 0;
		int max = dataSet.size() - 1;
		
		/*
		 * choose randomly k unequal instances from given data set as means
		 *   - generate random vector rndNumbers
		 *   - use its random numbers to pick out some data tuples from dataSet
		 */
		while(rndNumbers.size()!=k) {
			int rndNumber = ThreadLocalRandom.current().nextInt(min, max + 1);
			
			if(!rndNumbers.contains(rndNumber)) {
				rndNumbers.add(rndNumber);
			}
		}
		
		for(Integer rndNumber: rndNumbers) {
			try {
				means.add(dataSet.get(rndNumber).clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		
		
		/* ##################################################
		 * 2. assignment of instances to cluster prototypes
		 * ##################################################
		 *
		 * 'iterations' holds clusters for the last two iterations
		 * 'clusters' holds each single cluster with its assigned instances
		 */
		ArrayList<ArrayList<Set<Observation>>> iterations = new ArrayList<ArrayList<Set<Observation>>>(2);
		boolean convergence = false;
		
		while(!convergence) {
			ArrayList<Set<Observation>> clusters = new ArrayList<Set<Observation>>(k);
			for (int i = 0; i < k; i++) {
				Set<Observation> cluster = new HashSet<Observation>();
				clusters.add(cluster);
			}
			
			/*
			 * compute distance matrix and at the same time
			 * set the cluster assignment for each instance
			 */
			computeDistanceMatrixAndDoAssignment(dataSet, means);
			
			/*
			 * now each instance has a cluster assignment (as property)
			 * add each instance to the corresponding cluster
			 * (so we are able to check for convergence later by looking for changing clusters)
			 * add the clusters to 'iterations'
			 * check for convergence
			 */
			dataSet.forEach(instance -> clusters.get(instance.getMeanAffiliation()).add(instance));
			iterations.add(clusters);

			/*
			 * check for convergence
			 * for that we need only the last and current clusters
			 */
			if(iterations.size()==2) {
				ArrayList<Set<Observation>> lastClusters = iterations.get(0);
				ArrayList<Set<Observation>> currentClusters = iterations.get(1);
				convergence = lastClusters.equals(currentClusters);
				iterations.remove(lastClusters);
			}
			
			
			/* ##################################################
			 * 3. update means of cluster prototypes
			 * ##################################################
			 */
			means.clear();
			for (Set<Observation> cluster : clusters) {
				Observation updatedMean = new Observation();
				double average = 0;
	
				for (CarDataStructure attribute : CarDataStructure.values()) {
					if(attribute.isIncludedInDistanceCalc()) { //only take marked attributes for updating means!
						average = cluster.stream().collect(Collectors.averagingDouble(instance -> instance.getAttributeValue(attribute.toString())));
						updatedMean.setAttributeValue(attribute.toString(), average);
					}
				}
				means.add(updatedMean);
			}
		}
		
		// print results
		clusterStats(iterations.get(0));
	}
	
	private static void clusterStats(ArrayList<Set<Observation>> clusters) {
		/*
		 * for each cluster we detect the major class
		 *   - by counting instances for each class attribute value
		 *   - and taking the highest value
		 * then we assume that all instances for a certain cluster are classified by this major class
		 * and calculate the classification error rate (cl-err-rate) for each cluster
		 * for the overall cl-err-rate we weight each single cluster err-rate and take the average them (that is, using a weighting scheme)
		 */
		
		int classDim = Class.values().length;
		int[] classAttributeValues = new int[classDim];
		double overallErrRate = 0;
		
		for (int j = 0; j < clusters.size(); j++) { // for each cluster
			
			Set<Observation> cluster = clusters.get(j);
			int sum = cluster.size();
			
			for(Observation ob: cluster) { // for each instance
				classAttributeValues[(int)ob.getClassAttributeValue()]++;
			}
			
			System.out.println("### Cluster" + (j+1) + " Stats:");
			System.out.println("contains " + sum + " instances");
			
			int max = 0;
			int majorClass = 0;
			
			for (int i = 0; i < classDim; i++) {
				if(classAttributeValues[i]>max) {
					max = classAttributeValues[i];
					majorClass = i;
				}
				
				System.out.println("instances with class attribute value = " + Class.values()[i].name() + ": " + classAttributeValues[i]);
			}
			
			double errRate = 1 - (double)max/(double)sum;
			double weight = (double)cluster.size()/(double)sum;
			double weightedErrRate = weight*errRate;
			overallErrRate += weightedErrRate*1/clusters.size();
			
			System.out.println("majority class is " + Class.values()[majorClass].name() + " with " + max + " instances");
			System.out.println("cl-err-rate = " + new DecimalFormat("0.0000").format(errRate));
			System.out.println("weighted cl-err-rate = " + new DecimalFormat("0.0000").format(weightedErrRate));
			System.out.println();
			
			classAttributeValues = new int[classDim];
		}
		
		System.out.println("overall cl-err-rate = " + new DecimalFormat("0.0000").format(overallErrRate));
	}
	
	private static double[][] computeDistanceMatrixAndDoAssignment(ArrayList<Observation> dataSet, ArrayList<Observation> means) {
		/*
		 * for each instance we measure the Euclidean distance to all means
		 * we save the results in a distance matrix
		 *   with means as columns (j counter) and
		 *   with instances as rows (i counter)
		 * at the same time we also assign each instance to the nearest mean
		 * that happens with the property meanAffiliation of an instance
		 */
		
		int numMeans = means.size();
		int numInstances = dataSet.size();
		double[][] distanceMatrix = new double[numInstances][numMeans];
		
		for(int i=0; i<numInstances; i++) { //for each instance
			Observation currentInstance = dataSet.get(i);
			double min = Double.POSITIVE_INFINITY;
			int meanAffiliation = -1;
			
			for(int j=0; j<numMeans; j++) { //for each mean
				Observation currentMean = means.get(j);
				
				distanceMatrix[i][j] = distance(currentInstance, currentMean);
				
				// determine which mean is the nearest
				if(distanceMatrix[i][j]<min) {
					meanAffiliation = j;
					min = distanceMatrix[i][j];
				}
			}
			
			currentInstance.setMeanAffiliation(meanAffiliation);
		}
		
		return distanceMatrix;
	}
	
	private static double distance(Observation ob1, Observation ob2) {
		//using Euclidean distance measure
		double sum = 0.0d;
		double value1, value2;
		
		for (CarDataStructure attribute : CarDataStructure.values()) {
			if(attribute.isIncludedInDistanceCalc()) { //only take marked attributes for distance computation!
				value1 = ob1.getAttributeValue(attribute.name());
				value2 = ob2.getAttributeValue(attribute.name());
				sum += Math.pow(value1 - value2, 2);
			}
		}
		
		return Math.sqrt(sum);
	}

}
