# Objective

Reads one of the bundled demiliter separated files and generates an output in HTML formt

## Instruction to run the project

Go to the root of the project and issue the command 
`$ sbt clean compile test package publishLocal run`

One would be asked to choose which file to process. Make your choice and the program would specify the path of the HTML file containing input file's data.
### Result
![Result](doc/images/result.png "Result")

## Remarks

- **PRN format**: PRN file format is a text file created by the Lotus 1-2-3 spreadsheet software;
[which is tab delimited according to IBM](https://www.ibm.com/support/knowledgecenter/en/SSPN2D_10.2.1/com.ibm.swg.im.cognos.cp_a.10.2.1.doc/cp_a_id71792DefineColumnsinanASCIIFile.html "It's tab delimited according to IBM"). Please also look at the [information from Microsoft on PRN file.](https://docs.microsoft.com/en-us/office/troubleshoot/excel/prn-file-limited-to-240-characters
 "information from Microsoft on PRN file.")
