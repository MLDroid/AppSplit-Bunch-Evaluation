#Description:
#This is the generator of RSF file.
#The input should be a dependency matrix text file, as well as a labels text file.
#Input command: "python rsfGenerator.py arg1 arg2
#arg1 - Name / Location of the input DOT file.
#arg2 - Name / Location of the orphan modules file.
#arg3 - Name / Location of the output RSF file.

import sys
import numpy
from pprint import pprint
#import all the libraries required for this code.

def main(): #The main function of code
	
	#create the string array "StringData" from the input dot file. For each line of dot file, a string is created in the array. First 5 lines of the dot file, which are the headers, are skipped.
	#StringData = numpy.genfromtxt (str(sys.argv[1]), dtype = 'str', delimiter = '**', skip_header = 5, filling_values = ' ')
	#create an array "RSF" to hold the obtained strings from the dot file.
	StringData = []
	with open(sys.argv[1],'r') as f:
		for line in f:
			StringData.append(list(map(str,line.split('**'))))
	RSF = []
	
	for RowIndex in xrange (0, len(StringData)): #Start finding cluster labels from the string array line by line
		if ('label = "' in StringData[RowIndex][0]): #if a line with string 'label ="' is found, that means this line contains the cluster label name 
			ClusterLabel = StringData[RowIndex][0][StringData[RowIndex][0].find('label = "')+9:StringData[RowIndex][0].rfind('";')] #find out the label name between the string 'label = "' and '";'
			for RowScanner in xrange (RowIndex+1, len(StringData)): #from this Cluster Label line, scan for the lines containing Element names
				if ('"[label="' in StringData[RowScanner][0]): #check if symbolic string exist in current line
					Element = StringData[RowScanner][0][StringData[RowScanner][0].find('"')+1:StringData[RowScanner][0].find('"[')] #find the Element name between the string '"' and '"[label="' in the current line 
					#print Element
					RSF.append(('contain', ClusterLabel, Element)) #write to the RSF array row in "'contain' + cluster name + element name" format 
				if ('label = "' in StringData[RowScanner][0]): #if scan to the next Cluster Label line, then scanning stops 
					break
				if (RowScanner == len(StringData)-1): #if scan to the last row, then scanning stops
					break
					
	OrphanModules = [Line.strip() for Line in open(str(sys.argv[2]), "r").readlines()]
	
	for Line in range(len(OrphanModules)):
		OrphanModulesLabel = 1
		RSF.append(('contain', 'OrphanCluster'+str(OrphanModulesLabel), OrphanModules[Line]))
	
	#output the RSF array to external file
	FH = open (sys.argv[3], "w")
	for Elem in RSF:
		print>>FH,  " ".join(str(Component) for Component in Elem)
	FH.close()

if __name__ == '__main__':
	main()