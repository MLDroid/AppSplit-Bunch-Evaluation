@echo off 

python MDG_Generator.py Inputs\DependencyMatrix.txt Inputs\Labels.txt Outputs\MDG_W.mdg Outputs\OrphanModules -w
python MDG_Generator.py Inputs\DependencyMatrix.txt Inputs\Labels.txt Outputs\MDG_NW.mdg Outputs\OrphanModules -wo

java BunchCommandLine Outputs\MDG_W.mdg Outputs\DOT_default_W Config\config_default.txt
java BunchCommandLine Outputs\MDG_NW.mdg Outputs\DOT_default_NW Config\config_default.txt

Python DotToCsvGenerator.py Outputs\DOT_default_W.dot Outputs\Bunch_default_W.csv
Python DotToCsvGenerator.py Outputs\DOT_default_NW.dot Outputs\Bunch_default_NW.csv

python ClusterToRsfConverter.py Inputs\GT.csv Outputs\GT.rsf
python ClusterToRsfConverter.py Outputs\Bunch_default_W.csv Outputs\Bunch_default_W.rsf Outputs\OrphanModules
python ClusterToRsfConverter.py Outputs\Bunch_default_NW.csv Outputs\Bunch_default_NW.rsf Outputs\OrphanModules

java mojo.MoJo Outputs\Bunch_default_W.rsf Outputs\GT.rsf -fm >Outputs\mojofm_default_W.txt
java mojo.MoJo Outputs\Bunch_default_NW.rsf Outputs\GT.rsf -fm >Outputs\mojofm_default_NW.txt

pause 