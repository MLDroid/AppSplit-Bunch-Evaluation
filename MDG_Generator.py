#Description:
#This is the generator of MDG file.
#The input should be a dependency matrix text file, as well as a labels text file.
#Input command: "python mdgGenerator.py arg1 arg2 arg3 
#arg1 - Name / Location of the input DependencyMatrix.txt file.
#arg2 - Name / Location of the input Labels.txt file.
#arg3 - Name / Location of the output ModulesDependencyGraph.mdg file.
#arg4 - Name / Location of the output orphan module file.
#arg5 - Option for generating MDG file with/without weight. Using "-w" for with weight and "-wo" for without weight.

import sys
import numpy
from pprint import pprint
#import all the libraries required for this code.

def main(): #The main function of code

	#DependencyMatrix = numpy.genfromtxt (str(sys.argv[1]), dtype = 'int', delimiter = ' ')
	DependencyMatrix = []
	with open(sys.argv[1],'r') as f:
		for line in f:
			DependencyMatrix.append(list(map(int,line.split(' '))))
	Labels = [Line.strip() for Line in open(str(sys.argv[2]), "r").readlines()]
	MDG = []
	OrphanModules = []
	
	#print "Analyzing Dependency Matrix file and Labels file..."

	for RIndex in xrange(0,len(Labels)):
		for CIndex in xrange(0,len(Labels)):
			if (DependencyMatrix[RIndex][CIndex] != 0):
				#have to include the row elem name and col elem name in the MDG file
				RowElem = Labels[RIndex]
				ColElem = Labels[CIndex]
				if ((sys.argv[5]) == "-w"): DepValue = DependencyMatrix[RIndex][CIndex]
				if((sys.argv[5]) == "-wo"): DepValue = ""

				MDG.append((RowElem, ColElem, DepValue))
				
	#print(DependencyMatrix)
	#print(Labels)
				
	for LabelIndex in xrange(0,len(Labels)):
		NoOutgoingDependency = 1
		NoIncomingDependency = 1
		for ColumnIndex in range(len(Labels)):
			if ColumnIndex != LabelIndex:
				if DependencyMatrix[LabelIndex][ColumnIndex] != 0:
					NoOutgoingDependency = 0
		for RowIndex in range(len(Labels)):
			if RowIndex != LabelIndex:
				if DependencyMatrix[RowIndex][LabelIndex] != 0:
					NoIncomingDependency = 0
		if NoOutgoingDependency == 1 and NoIncomingDependency == 1:
			OrphanModules.append((Labels[LabelIndex]))
				
	#print "Generating MDG file..."
	FH = open (sys.argv[3], "w")
	for Elem in MDG:
		print>>FH,  " ".join(str(Component) for Component in Elem)
	FH.close()
	
	FH2 = open (sys.argv[4], "w")
	for Elem in OrphanModules:
		print>>FH2,  "".join(str(Component) for Component in Elem)
	FH2.close()
	#print "MDG file generated!"



if __name__ == '__main__':
	main()