#This code is used to convert the ClusterDetails.csv (or GT.csv) to RSF format. During the process, the orphan modules will be added as standalone clusters.
#arg1: ClusterDetails.csv file
#arg2: output RSF file
#arg3: orphan modules list (optional)

import sys
import numpy
from pprint import pprint

def main():

	StringData = []
	with open(sys.argv[1],'r') as f:
		for line in f:
			StringData.append(list(map(str,line.split(','))))
	RSF = []

	for i in xrange (0, len(StringData)): 
		RowData = StringData[i][1].split() 
		for j in xrange (0, len(RowData)): 
			RSF.append (('contain','Cluster_'+str(i),RowData[j]))


	if len(sys.argv) > 3:
		OrphanModules = [Line.strip() for Line in open(str(sys.argv[3]), "r").readlines()]
		for Line in range(len(OrphanModules)):
			RSF.append(('contain', 'OrphanCluster'+str(Line), OrphanModules[Line]))
	
	FH = open (sys.argv[2], "w")
	for Elem in RSF:
		print>>FH,  " ".join(str(Component) for Component in Elem)
	FH.close()
	
if __name__ == '__main__':
	main()