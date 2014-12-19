@echo off
cd Dataset
for /D %%s in (*) do ( 
del /q /s %%s\OutputFiles\*.*
rd /q /s %%s\OutputFiles
)
del *.txt /f /q
pause