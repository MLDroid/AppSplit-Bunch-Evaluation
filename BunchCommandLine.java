/*This is a command line code to use the Bunch tool.
To use this code, first use "javac BunchCommandLine.java", then run "java BunchCommandLine inputfile.mdg outputfile config.txt".
As shown above, there are 3 system arguments when running this code:
1. inputfile.mdg: This is the name/directory of the input MDG file.
2. outputfile: This is the name/directory of the output DOT file. PLEASE NOTE THAT THIS ARGUMENT DOES NOT HAVE AN EXTENSION, UNLIKE THE ARGUMENT 1.
3. config.txt This is the name/directory of the configuration file.
*/

import java.util.*;
import java.io.*;
import bunch.api.*;
//This 3 lines are used to import all the required java libraries. PLEASE NOTE THAT "bunch.api.*" MIGHT NEED BE SET MANUALLY IN CLASSPATH.


class BunchCommandLine { //This is the main class of the code. Class name is "BunchCommandLine".

	String MDG_OUTPUT_MODE = null;	//set string variable MDG_OUTPUT_MODE, initialize it to empty.
	String CLUSTERING_ALG = null; //set string variable CLUSTERING_ALG, initialize it to empty.
	String ALG_HC_POPULATION_SZ = null; //set string variable ALG_HC_POPULATION_SZ, initialize it to empty.
	String ALG_HC_HC_PCT = null; //set string variable ALG_HC_HC_PCT, initialize it to empty.
	
	public static void main(String args[]) {	//This is the main method required by Java.
		BunchCommandLine bcl = new BunchCommandLine(args);	//The main method only do one thing: build a instance of class "BunchCommandLine" by using the constructor method BunchCommandLine(String[] args).
	}
	
	public BunchCommandLine(String[] args) {	//This is the constructor method of class BunchCommandLine. It requires a string array called "args". Constructor method create this class and all its variables.
		ConfigSetup(args[2]); //This line calls the method ConfigSetup with argument "args[2]", which should be the configuration file name/directory.
		//System.out.println(Arrays.toString(args)); //This line is used to test the content of the arguments.
		BunchProcess(args[0],args[1]); //This line calls the method ConfigSetup with arguments "args[0]" and "args[1]" which are the names/directories of input MDG file and output DOT file.
	}
	
	public void ConfigSetup(String ConfigFileName) //This method is to configure all the parameters we concerned, and required by Bunch. It has an argument called "ConfigFileName".
	{
		try{ //attempt the following code.
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ConfigFileName))); //Create a content reader class instance named "br".
			String str; //Create a string to read the content of "br".
			String content=""; //Create a string to hold 
			while((str = br.readLine()) != null){ //Read line by line til the end of file
				content = content + str + "\n";  //Add the read content to the String "content".
			}
			String[] contentArray = content.split(";");  //Split the string "content": each line will be keep in one cell of array "contentArray".
			//System.out.println(Arrays.toString(contentArray)); //This line is used to check the content of "contentArray".
			for (int i=0;i<contentArray.length;i++) { //Check through each row in "contentArray".
				if (contentArray[i].contains("MDG_OUTPUT_MODE")) { //if parameter "MDG_OUTPUT_MODE" is found in the current line;
					MDG_OUTPUT_MODE = contentArray[i].substring(contentArray[i].indexOf("\"")+1,contentArray[i].lastIndexOf("\"")); //Get the value of this parameter, which is quoted. Save it to the "MDG_OUTPUT_MODE" string variable.
					//System.out.println(MDG_OUTPUT_MODE); //Check the parameter value.
				}
				if (contentArray[i].contains("CLUSTERING_ALG")) { //if parameter "CLUSTERING_ALG" is found in the current line;
					CLUSTERING_ALG = contentArray[i].substring(contentArray[i].indexOf("\"")+1,contentArray[i].lastIndexOf("\"")); //Get the value of this parameter, which is quoted. Save it to the "CLUSTERING_ALG" string variable.
					//System.out.println(CLUSTERING_ALG); //Check the parameter value.
				}
				if (contentArray[i].contains("ALG_HC_POPULATION_SZ")) { //if parameter "ALG_HC_POPULATION_SZ" is found in the current line;
					ALG_HC_POPULATION_SZ = contentArray[i].substring(contentArray[i].indexOf("\"")+1,contentArray[i].lastIndexOf("\"")); //Get the value of this parameter, which is quoted. Save it to the "ALG_HC_POPULATION_SZ" string variable.
					//System.out.println(ALG_HC_POPULATION_SZ); //Check the parameter value.
				}
				if (contentArray[i].contains("ALG_HC_HC_PCT")) { //if parameter "ALG_HC_HC_PCT" is found in the current line;
					ALG_HC_HC_PCT = contentArray[i].substring(contentArray[i].indexOf("\"")+1,contentArray[i].lastIndexOf("\"")); //Get the value of this parameter, which is quoted. Save it to the "ALG_HC_HC_PCT" string variable.
					//System.out.println(ALG_HC_HC_PCT); //Check the parameter value.
				}
			}
		} catch (Exception e) {  //If attempt to do above failed
            e.printStackTrace();  //I don't know what it does when exception is caught......
        }
	}

	public void BunchProcess(String MDGFileName, String DOTFileName) //This is the method to proceed the Bunch. It has two arguments, input MDG file "MDGFileName", and output DOT file "DOTFileName".
	{
		//Create the required objects
		BunchAPI api = new BunchAPI();
		BunchProperties bp = new BunchProperties();
		
		bp.setProperty(BunchProperties.MDG_INPUT_FILE_NAME, "." + "\\" + MDGFileName); // setting the input MDG file
		bp.setProperty(BunchProperties.OUTPUT_FILE, "." + "\\" + DOTFileName); // setting the output DOT file
		bp.setProperty(BunchProperties.OUTPUT_FORMAT,BunchProperties.DOT_OUTPUT_FORMAT); //fixed
		switch (MDG_OUTPUT_MODE) {
			case "BunchProperties.OUTPUT_DETAILED": {
				bp.setProperty(BunchProperties.MDG_OUTPUT_MODE, BunchProperties.OUTPUT_DETAILED);
				break;
			}
			case "BunchProperties.OUTPUT_MEDIAN": {
				bp.setProperty(BunchProperties.MDG_OUTPUT_MODE, BunchProperties.OUTPUT_MEDIAN);
				break;
			}
			case "BunchProperties.OUTPUT_TREE": {
				bp.setProperty(BunchProperties.MDG_OUTPUT_MODE, BunchProperties.OUTPUT_TREE);
				break;
			}
			default: bp.setProperty(BunchProperties.MDG_OUTPUT_MODE, BunchProperties.OUTPUT_MEDIAN);
		}
		switch (CLUSTERING_ALG) {
			case "BunchProperties.ALG_HILL_CLIMBING": {
				bp.setProperty(BunchProperties.CLUSTERING_ALG, BunchProperties.ALG_HILL_CLIMBING);
				break;
			}
			case "BunchProperties.ALG_EXHAUSTIVE": { 
				bp.setProperty(BunchProperties.CLUSTERING_ALG, BunchProperties.ALG_EXHAUSTIVE);
				break;
			}
			case "BunchProperties.ALG_GA": {
				bp.setProperty(BunchProperties.CLUSTERING_ALG, BunchProperties.ALG_GA);
				break;
			}
			default: bp.setProperty(BunchProperties.CLUSTERING_ALG, BunchProperties.ALG_HILL_CLIMBING);
		}
		bp.setProperty(BunchProperties.ALG_HC_POPULATION_SZ,ALG_HC_POPULATION_SZ);
		bp.setProperty(BunchProperties.ALG_HC_HC_PCT,ALG_HC_HC_PCT);
		//The above part is used to configure the bunch parameters. Details can be found in the Bunch Programming Appendix A.
		api.setProperties(bp);
		//Now execute the clustering process
		api.run();
		//Get the results and display statistics for the execution time,
		//and the number of MQ evaluations that were performed.
		Hashtable results = api.getResults();
		System.out.println("Results:");
		String rt = (String)results.get(BunchAPI.RUNTIME);
		String evals = (String)results.get(BunchAPI.MQEVALUATIONS);
		System.out.println("Runtime = " + rt + " ms.");
		System.out.println("Total MQ Evaluations = " + evals);
	}

}
