* Tool designed to process multiple files of the MzIdentML format into easy to read CSV files *

The MzIdentMlConvert, or MzIdConvert tool can be used for proteogenomics and non-proteogenomics purposes.


How to run the tool:

  1. ```java -jar MzIdentMlConvert.jar -help```  
    shows help/usage information 
  2. ```java -Xms2G -Xmx16G -jar MzIdentMLFormatConverter.jar -mzid C:\Users\User1\Input\1D50CM\1D50CM_main.txt -databases C:\Users\User1\Imput\databases_entry.txt -output C:\Users\User1\Output\ -threads 24 -outputFiles 1 2 3 4```  
    Runs the MzIdentMlConvert tool using the command line interface.
    The input requires a specific setup for the main text file which can be found in the ..\Data\Data\MzIdentMlConvert.pdf
  3. ```nohup java -Xms2G -Xmx16G -jar MzIdentMLFormatConverter.jar -mzid C:\Users\User1\Input\1D50CM\1D50CM_main.txt -databases C:\Users\User1\Imput\databases_entry.txt -output C:\Users\User1\Output\ -threads 24 -outputFiles 1 2 3 4 > cmd_output.out &```
    Runs the MzIdentMlConvert tool using the command line interface, but writes command line inteface output to a nohup.out file.
    This allows the command line interface to be used for other purposes, or allows the command line interface to be closed.
    Output of the tool itself is still written to the given output directory.


Explanation of arguments:
    
  1. ```java -jar MzIdentMLFormatConverter.jar -help```  
    Shows help/usage information 
  2. ```nohup "arguments" > cmd_output.out &```  
    Nohup allows a command line argument to run in the background, so that the current interface can either be closed or used
    for other purposes. The command starts with nohup, then the given tool and arguments are written.
    After this step the  "> cmd_output.out &" line is written behind the last argument. This line allows data to be written to
    the cmd_output.out file and allows the commandline to be used for other purposes.
  3. ```java -Xms1G -Xmx3G -jar MzIdentMLFormatConverter.jar```
    Executes the provided jar file while trying to reserve a minimum and maximum amount of memory.
    Providing memory is essential for processing large data sets. 
  4. ```-mzid C:\Users\User1\Documents\Entry\1D50CM\1D50CM_main.txt```
    Reads a text file linking to other text files that contain file paths to MzIdentML files.
    Look at the \Data\MzIdentMLFormatConverter.pdf present in this project folder for the correct format.
  6. ```-databases C:\Users\Vikthor\Documents\Entry\databases_entry.txt```
    * Optional argument *
    Using this argument also generates a scan_id.csv file containing sequences with their PEAKS associated Scan_id and database flags.
    Reads a text file linking to other text files that contain file paths to protein sequence fasta files.
    Look at the \Data\MzIdentMLFormatConverter.pdf present in this project folder for the correct format.
  6. ```-output C:\Users\Vikthor\Documents\Output\```
    File path to the output directory.
  7. ```-threads 8```
    * Optional Argument *
    Optional argument to set the amount of threads used. No argument: default threads is set at 2 threads.
    Execute the tool using 8 threads. Some parts of this tool are multithreaded which allows for faster processing.
    The 8 can be replaced by any number, but please check the available amount of threads and take other tasks into consideration.
  8. ```-outputFiles 1 2 3 4```
    Requires at least one of these numbers to generate output files.
    Number      Effect
    1           Generates DB search psm.csv output files for each sample.
    2           Generates both peptides.csv and ion-series.csv files for each sample.
    3           Generates protein-peptides.csv files for each sample.
    4           Generates proteins.csv files for each sample.