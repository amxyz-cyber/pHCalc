# pHCalc - It calculates the equivalence point, its pH value and plots or prints the results!

## What is it?
**pHCalc** performs several tasks after the titration of an acid using
a base as the titrant has been finished. It calculates the [equivalence 
point](supplement/ep.txt) together with its pH value based on the second derivative. After 
having performed the calculation, it prints the [results](supplement/results.txt) into a neatly 
formatted table and stores it in a file.
Additionally, it [plots](supplement/titration-all.png) a titration curve
together with the first and second derivatives. To accomplish it, it
needs the values of the added `titrant` in `mL` and the measured `pH` 
values during the addition of the titrant. 

## System Requirements
**JDK**:
* 11 or above. Both the Java compiler and Java runtime (JRE) need to 
support 64-bit. Compatible Java compilers as well as JREs can be 
downloaded from [Bellsoft](https://bell-sw.com/pages/downloads/).
    
**Memory**:
* No minimum requirement.

**Disk**:
* Approximately 450 KiB is required for the compilation of pHCalc 
  itself. In addition to that, 56 MiB disk space will be used for 
  pHCalc. 

**Operating System**:
* Windows:
      Windows 2000 or above.
* Unix based systems (Linux, Solaris and Mac OS X) and others:
      No minimum requirement.
   
**Gnuplot**:
 * pHCalc uses [**gnuplot**](http://www.gnuplot.info/) to plot the
   diagram. So you need to install gnuplot first before using pHCalc.

## Compiling pHCalc 
If you decide to compile this project then you can do it with [maven](https://maven.apache.org/).
Under Linux open a terminal and go to the directory of this project. 
Then run the following command which will compile the source files:
```bash
$ mvn clean install
```
After a successful compilation the `.jar` containing all the libraries
will be placed in the subfolder called `target`.

## Installing pHCalc
If you want to use the provided binaries, i.e. the release package
instead, then the installation process is simple. Please note, there
are two different binaries, one java binary that you can run like a
script in your terminal and another one which you run just like any
other `jar` file.

1. Unpack the archive where you would like to store the binaries, e.g.:
	```bash
	$ tar -xvzf pHCalc-x.y.zip
	```
2. A directory called **myPHCalc** will be created.

## Starting pHCalc
* Unix-based operating systems (Linux, Solaris and Mac OS X)
	```bash
	$ java -jar pHCalc-x.y.jar -h
	```
	
* Windows
Open the windows command prompt or the powershell and enter the
  following command to display the help message:
  ```bash
  $ java -jar pHCalc-x.y.jar -h
  ```  

* Alternatively, use the script like java file:
	```bash
	$ ./pHCalc -h
	```
	
## Preconditions
1. To perform some calculations, print its results and create a diagram
you'll need a **data file** with two columns in the following order:
`Volume of Titrant in mL (=X)`, `pH (=Y)` [see example file](supplement/titration_ex.dat)

2. The pHCalc's jar or script file as well as the settings file 
[phcalc.properties](phcalc.properties) need to share the same directory.

3. Additionally, you'll need to edit the settings file 
`phcalc.properties`. Especially, the paths to the data file and to the
new diagram file have to be entered. Without a correct path to the data
file, pHCalc won't work.

## Glossar (phcalc.properties)
* ### titration tool settings
**log.size**=\<Number\> 
> Define the size of the log that will be printed after pHCalc has 
finished a task. 

* ### Language settings
**language**=(en | de)
> possible values: `de` for German, `en` for English

* ### Acid Parameters
**mol.mass.acid**=\<Number: g/mole\>
> Enter the molar mass of the acid used in grams per mole.

**sample**=\<Number: mL\>
> Enter the volume of sample that has been used for the titration.

**dilution**=\<Number | 0 |: mL\>
> If the sample of the acid used has been diluted then you can enter the
(amount of water + sample size of the acid)

**solution**=\<Number | 0 |: mL\>
> If the acid has been diluted and the titration has been performed
using a specific amount of the diluted acid then you'll enter the
amount of the final solution here. For example, original sample of the acid 
is 50 mL. Then you dilute this sample until the flask reaches 100 mL. 
From this diluted amount you take a 20 mL solution. Then you enter 20.

**acid.formula**=\<Formula of the acid: Text\>

**acid.name**=\<Name of the acid: Text\>

* ### Titrant Parameters
**mol.mass.titrant**=\<Number: g/mole\>
> Enter the molar mass of the base, i.e. titrant used in grams per mole.

**titrant.formula**=\<Formula of the base: Text\>

**titrant.name**=\<Name of the base: Text\>

* ### Titration
**threshold**=\<Number\>
> Specify the difference between two pH values where you think the 
equivalence point has been reached. A good difference is for example, 
1.5 or 2.5. It depends on your data rows.

* ### Sample
**sample.name**=\<Name of the sample: Text\>
> Enter the sample name that should appear in the results table or
displayed on the chart.

* ### Chart
**chart.title**=\<Title of the chart: Text\>

**y.label**=\<Label of the y axis: Text\>

**x.label**=\<Label of the x axis: Text\>

**data.file**=\<Path/to/data.dat\>

**chart.file**=\<Path/to/chart.eps\>
> The chart will be created as a .eps file.

**xtic**=\<Number: xtics\>

**ytic**=\<Number: ytics\>

**xtic.rotate**=\<Number: Rotation of the xtics labels in degrees\> 

* ### Curve Fit: Logistic Regression
**param.b**=\<Number | 0\>
> Growth rate. For a standard logistic curve this is 1 so 1 is a 
reasonable estimate

**param.n**=\<Number | 0\>
> Parameter that affects near which asymptote maximum growth occurs. 
1.0 if we assume the curve is symmetric

**param.q**=\<Number | 0\>
> Parameter that affects the position of the curve along the ordinate 
axis.

**step**=\<Number | 1\>
> Specify of how many x values the fitted curve should consist of. Enter
1 if the fitted curve should contain as many x values as the data file.

## Usage
The subcommands, its options and parameters apply to both the jar file
and the script file. Please note that __*__ stands for required option.


1. Print the help message and the available subcommands
```bash
$ ./pHCalc [-hVv] [--] (compute | print | plot)
```
| Option | Argument |  Long Option   | Option Description                                                        |
|:------:|:---------|:---------------|:--------------------------------------------------------------------------|
|-v      | 			|--[no-]verbose  |Specify multiple -v options to increase verbosity: `-v -v -v -v` or `-vvvv`|
|-V      |          |--version 	     |show version number                                                        |
|-h      |          |--help          |Show this help message                                                     |

* For example: Display the help menu for the subcommand **plot**
```bash
$ ./pHCalc help plot
```

* For example: Display the general help message
```bash
$ ./pHCalc -h
```


2. Calculate the equivalence point as well as its pH value
```bash
$ ./pHCalc [OPTION] compute [<FILE>]
```
| Option | Argument |  Long Option   | Option Description                                                        |
|:------:|:---------|:---------------|:--------------------------------------------------------------------------|
|-v      | 			|--[no-]verbose  |Specify multiple -v options to increase verbosity: `-v -v -v -v` or `-vvvv`|
|-V      |          |--version 	     |Show version number                                                        |
|-h      |          |--help          |Show this help message                                                     | 
|        |FILE      |                |Save the table in a file                                        # optional |

* For example:
```bash
$ ./pHCalc compute -v file1.txt
```


3. Print the calculations in a neatly formatted table
```bash
$ ./pHCalc [OPTION] print [-t=<TYPE>] [<FILE>]
```
| Option | Argument |  Long Option   | Option Description                                                        |
|:------:|:---------|:---------------|:--------------------------------------------------------------------------|
|-v      | 			|--[no-]verbose  |Specify multiple -v options to increase verbosity: `-v -v -v -v` or `-vvvv`|
|-V      |          |--version 	     |show version number                                                        |
|-h      |          |--help          |Show this help message                                                     |
|* -t    |TYPE      |--type          |Type of table: all, results, data                                          |
|        |FILE      |                |Saves the table in a file                                       # optional |

* For example: Print the data rows together with the first and second
derivatives in one table and save it in a file:
```bash
$ ./pHCalc print -v -t=data data.txt
```


4. Plot the calculations or the data rows.
```bash
$ ./pHCalc [OPTION] plot [-fsyx] [-t=<TYPE>] [-a=<ASYMPTOTE>] [--color-curve=<COLOR>] [--color-ep=<COLOR>] [--color-firstDerivative=<COLOR>] [--color-secondDerivative=<COLOR>]
```
| Option | Argument |  Long Option           | Option Description                                                        |
|:------:|:---------|:-----------------------|:--------------------------------------------------------------------------|
|-v      | 			|--[no-]verbose          |Specify multiple -v options to increase verbosity: `-v -v -v -v` or `-vvvv`|
|-V      |          |--version 	             |show version number                                                        |
|-h      |          |--help                  |Show this help message                                                     |
|-lc     |          |--list-colors           |Display available colors                                                   |
|* -t    |TYPE      |--type                  |Type of plot: curve, derivatives, all                                      |
|-f      |          |--[no-]fit              |Fit the titration curve                                         # optional |
|-s      |          |--[no-]smooth           |Smooth the curve(s)                                             # optional |
|-x      |          |--[no-]xtics            |Insert xtics                                                    # optional |
|-y      |          |--[no-]ytics            |Insert ytics                                                    # optional |
|-a      |ASYMPTOTE |--asymptote             |Set the parameters n and b used for curve fitting               # optional |
|-c      |COLOR     |--color-curve           |Color of the titration curve                                    # optional |
|-cep    |COLOR     |--color-ep              |Color of the equivalence point                                  # optional |
|-c1     |COLOR     |--color-firstDerivative |Color of the first derivative                                   # optional |
|-c2     |COLOR     |--color-secondDerivative|Color of the second derivative                                  # optional |

* For example: Display all the available colors used for plotting the
curves:
```bash
$ ./pHCalc plot --list-colors
```

* For example: Plot only the titration curve using logistic regression
to fit the curve:
```bash
$ ./pHCalc plot -a=STEEP_CURVE -t=curve -c=DARK_RED -fsy -vv
```

* For example: Plot the titration curve including the derivatives
by fitting the titration curve and smoothing all the curves:
```bash
$ ./pHCalc plot -a=STEEP_CURVE -c=DARK_MAGENTA -c1=FOREST_GREEN -c2=ORANGE_RED -fsx -t=derivatives -vv 
```

* For example: Plot the titration curve including the derivatives and the
equivalence point by fitting the titration curve and smoothing 
all the curves:
```bash
$ ./pHCalc plot -a=STEEP_CURVE -c=DARK_RED -c1=FOREST_GREEN -c2=ORANGE_RED -cep=NAVY -fsx -t=all -vv  
```

## Hints
### `plot`: Curve Fitting
If fitting is applied then pHCalc will use logistic regression to fit
the titration curve. However, to fit the titration curve successfully the
parameters `n` and `b` need to be suitable for a specific curve. These
parameters need to be set if option `-f` is applied. But there are 
arguments of the `asymptote` which contain predefined values or which
have phCalc find suitable values:

**SYMMETRIC_CURVE**: Use this asymptote if the curve looks somehow 
symmetric. An example of it can be found [here](supplement/titration-curve.png).

**FLAT_CURVE**: Use this asymptote if the titration curve is quite flat 
as in the titration of the phosphoric acid.

**STEEP_CURVE**: Use this asymptote if the curve is rather steep. 

**ADVANCED_CALC**: Use this asymptote if parameters `n` and `b` should
be calculated by `pHCalc`.

**SIMPLE_CALC**: The same as the `advanced_calc` argument but the 
calculation method is simpler.

**NONE**: Use this asymptote if you want to set the parameters `n` and 
`b` by yourself in the settings file `phcalc.properties`.

Please note, the predefined values as well as the calculated ones
don't guarantee that the logistic regression will accept these values.
`pHCalc` will catch the exception and you'll notice it if you turn on
the third level of verbosity `-vvv`. In thoses cases you need to enter
the values for the paramters `n` and `b` manually in `phcalc.properties`
while on the command line you need to specify `-a=NONE` as the argument.

### `print` | `compute` Tables
It's not necessary to enter a file name for displaying the calculations 
in neatly formatted tables. Without entering a file, you can view the
table in your terminal.


`pHCalc`(R) Version 1.0 19/01/2023
