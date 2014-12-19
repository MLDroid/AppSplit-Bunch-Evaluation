@echo off 
cd Dataset
for /D %%s in (*) do ( 
cd %%s
md OutputFiles
cd..
cd..
python MDG_Generator.py Dataset\%%s\smali\DependencyMatrix.txt Dataset\%%s\smali\Labels.txt Dataset\%%s\OutputFiles\MDG_W.mdg Dataset\%%s\OutputFiles\OrphanModules -w
python MDG_Generator.py Dataset\%%s\smali\DependencyMatrix.txt Dataset\%%s\smali\Labels.txt Dataset\%%s\OutputFiles\MDG_NW.mdg Dataset\%%s\OutputFiles\OrphanModules -wo

java BunchCommandLine Dataset\%%s\OutputFiles\MDG_W.mdg Dataset\%%s\OutputFiles\DOT_HC_W_Low Config\config_HC_Low.txt
java BunchCommandLine Dataset\%%s\OutputFiles\MDG_NW.mdg Dataset\%%s\OutputFiles\DOT_HC_NW_Low Config\config_HC_Low.txt
java BunchCommandLine Dataset\%%s\OutputFiles\MDG_W.mdg Dataset\%%s\OutputFiles\DOT_HC_W_Medium Config\config_HC_Medium.txt
java BunchCommandLine Dataset\%%s\OutputFiles\MDG_NW.mdg Dataset\%%s\OutputFiles\DOT_HC_NW_Medium Config\config_HC_Medium.txt

Python DotToCsvGenerator.py Dataset\%%s\OutputFiles\DOT_HC_NW_Low.dot Dataset\%%s\OutputFiles\Bunch_HC_NW_Low.csv
Python DotToCsvGenerator.py Dataset\%%s\OutputFiles\DOT_HC_NW_Medium.dot Dataset\%%s\OutputFiles\Bunch_HC_NW_Medium.csv
Python DotToCsvGenerator.py Dataset\%%s\OutputFiles\DOT_HC_W_Low.dot Dataset\%%s\OutputFiles\Bunch_HC_W_Low.csv
Python DotToCsvGenerator.py Dataset\%%s\OutputFiles\DOT_HC_W_Medium.dot Dataset\%%s\OutputFiles\Bunch_HC_W_Medium.csv

python ClusterToRsfConverter.py Dataset\%%s\smali\GT.csv Dataset\%%s\OutputFiles\GT.rsf
python ClusterToRsfConverter.py Dataset\%%s\OutputFiles\Bunch_HC_NW_Low.csv Dataset\%%s\OutputFiles\Bunch_HC_NW_Low.rsf Dataset\%%s\OutputFiles\OrphanModules
python ClusterToRsfConverter.py Dataset\%%s\OutputFiles\Bunch_HC_NW_Medium.csv Dataset\%%s\OutputFiles\Bunch_HC_NW_Medium.rsf Dataset\%%s\OutputFiles\OrphanModules
python ClusterToRsfConverter.py Dataset\%%s\OutputFiles\Bunch_HC_W_Low.csv Dataset\%%s\OutputFiles\Bunch_HC_W_Low.rsf Dataset\%%s\OutputFiles\OrphanModules
python ClusterToRsfConverter.py Dataset\%%s\OutputFiles\Bunch_HC_W_Medium.csv Dataset\%%s\OutputFiles\Bunch_HC_W_Medium.rsf Dataset\%%s\OutputFiles\OrphanModules

java mojo.MoJo Dataset\%%s\OutputFiles\Bunch_HC_W_Low.rsf Dataset\%%s\OutputFiles\GT.rsf -fm >Dataset\%%s\OutputFiles\mojofm_HC_W_Low.txt
for /f "delims=" %%t in (Dataset\%%s\OutputFiles\mojofm_HC_W_Low.txt) do ( echo %%s	%%t >> DatasetEvaluationResult\mojofm_HC_W_Low.txt )
java mojo.MoJo Dataset\%%s\OutputFiles\Bunch_HC_NW_Low.rsf Dataset\%%s\OutputFiles\GT.rsf -fm >Dataset\%%s\OutputFiles\mojofm_HC_NW_Low.txt
for /f "delims=" %%t in (Dataset\%%s\OutputFiles\mojofm_HC_NW_Low.txt) do ( echo %%s	%%t >> DatasetEvaluationResult\mojofm_HC_NW_Low.txt )
java mojo.MoJo Dataset\%%s\OutputFiles\Bunch_HC_W_Medium.rsf Dataset\%%s\OutputFiles\GT.rsf -fm >Dataset\%%s\OutputFiles\mojofm_HC_W_Medium.txt
for /f "delims=" %%t in (Dataset\%%s\OutputFiles\mojofm_HC_W_Medium.txt) do ( echo %%s	%%t >> DatasetEvaluationResult\mojofm_HC_W_Medium.txt )
java mojo.MoJo Dataset\%%s\OutputFiles\Bunch_HC_NW_Medium.rsf Dataset\%%s\OutputFiles\GT.rsf -fm >Dataset\%%s\OutputFiles\mojofm_HC_NW_Medium.txt
for /f "delims=" %%t in (Dataset\%%s\OutputFiles\mojofm_HC_NW_Medium.txt) do ( echo %%s	%%t >> DatasetEvaluationResult\mojofm_HC_NW_Medium.txt )

cd Dataset
) 
cd..
pause 