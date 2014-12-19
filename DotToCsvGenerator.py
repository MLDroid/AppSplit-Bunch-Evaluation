#This code is used to convert Bunch output Dotty file to RSF format.
#arg1: Dot file
#arg2: output CSV file

import sys
from pprint import pprint

def main(): 

	DotContent = []
	with open(sys.argv[1],'r') as f:
		for line in f:
			DotContent.append(list(map(str,line.split('**'))))
	CSV = []
	CSVLineCounter = 0
			
	for RowIndex in range(len(DotContent)):
		if ('label = "' in DotContent[RowIndex][0]):
			CSV.append(str(CSVLineCounter+1) + ',')
			for RowScanner in xrange (RowIndex+1, len(DotContent)):
				if ('"[label="' in DotContent[RowScanner][0]):
					Element = DotContent[RowScanner][0][DotContent[RowScanner][0].find('"')+1:DotContent[RowScanner][0].find('"[label="')]
					CSV[CSVLineCounter] = CSV[CSVLineCounter] + Element + ' '
				if ('label = "' in DotContent[RowScanner][0]):
					break
				if (RowScanner == len(DotContent)-1):
					break
			CSVLineCounter = CSVLineCounter + 1
					
	
	FH = open (sys.argv[2], "w")
	for Elem in CSV:
		print>>FH,  "".join(str(Component) for Component in Elem)
	FH.close()

if __name__ == '__main__':
	main()