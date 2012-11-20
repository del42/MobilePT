/**
 * @author Jude Morrissey Cruz and Delgersuren Bold
 * 			San Francisco State University, Fall 2011, CSC 780
 */

package mobilePT.decisionTrees;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.Attribute;

@SuppressWarnings("deprecation")
public class DecisionTreeHelperFunctions {
	
	/**
	 * this is the bread and butter method that checks the collected accelerometer
	 * data (instances of accelerometer data) against the decision tree to determine
	 * how much of the data falls under classifications (correct movement, excessive, etc)
	 * 
	 * basically mimics the code from Hunvil and Tilila's Jogger's Music App, just modified
	 * so that the dependency to an intrinsic class "DataMiner" is eliminated
	 * 
	 * @param iSet is the collection of accelerometer instances, collected everytime
	 * 			the accelerometer sensor changes
	 * @param featureV is a vector containing the different attributes to be
	 * 			classified against
	 * @return a specifically formatted string of the results
	 */
	public static ArrayList<ArrayList<String>> classifySet(Instances iSet, Classifier dTree, 
			FastVector<Attribute> featureV, FastVector<String> classList) {
		Double result = null;
		ArrayList<Statistic> results = new ArrayList<Statistic>(featureV.size());
		ArrayList<ArrayList<String>> returnMe = new ArrayList<ArrayList<String>>();

		// classify each element in the set
		for (int i = 0; i < iSet.numInstances(); i++) {
			try {
				result = dTree.classifyInstance(iSet.instance(i));

				boolean flag = false;

				for (Statistic s : results) {
					if (s.getClassID().equals(result)) {
						s.incrementCount();
						flag = true;
					}
				}

				if (flag == false) {
					Statistic element = new Statistic();
					element.setClassID(result);
					element.setLabel(classList.get(result.intValue()));
					element.incrementCount();
					results.add(element);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// end of for loop

		// record the percentage of each class
		int sumOfElements = iSet.numInstances();

		// assemble the classifications and their percentages into the array
		for (Statistic s : results) {
			s.setPercentage((Double) (double) s.getCount() / (double) sumOfElements);
			
			// make an ArrayList of Strings that contains the label and the percentage
			ArrayList<String> addMe = new ArrayList<String>();
			addMe.add(s.getLabel());
			addMe.add(String.valueOf(s.getPercentage()));
			
			// add the created ArrayList to the to-be-returned ArrayList
			returnMe.add(addMe);
		}

		// return result result
		return returnMe;
	}
	
	
	/**
	 * this method is used to initialize and return a FastVector() containing string
	 * names of the different possible classifiers that can be found across the different
	 * datasets
	 * @return FastVector<String> containing the string names of classifications
	 */
	public static FastVector<String> initializeClassifierVector() {
		FastVector<String> returnMe = new FastVector<String>();
		
		returnMe.addElement("ArmWaveUpDownTooMuch");
		returnMe.addElement("ArmWaveUpDownTooFast");
		returnMe.addElement("ArmWaveUpDown");
		returnMe.addElement("ArmWaveSideToSide");
		returnMe.addElement("WristFlexorPalmUp");
		returnMe.addElement("WristFlexorPalmUpTooFast");
		
		return returnMe;
	}
	
	
	/**
	 * this method is used to initialize and return a FastVector() containing string
	 * names of the different possible classifiers that is contained in the passed
	 * argument aClasses (able to use arbitrary names)
	 * 
	 * @param aClasses contains the string names of classifications
	 * @return FastVector<String> containing the string names of classifications
	 */
	public static FastVector<String> initializeClassifierVector(ArrayList<String> aClasses) {
		FastVector<String> returnMe = new FastVector<String>();
		
		for (int i = 0; i < aClasses.size(); i++) {
			returnMe.add(aClasses.get(i));
		}
		
		return returnMe;
	}
	
}
