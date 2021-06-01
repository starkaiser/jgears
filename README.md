jgears
=======

jgears is a program that generates parallel and bevel gear drives from a few input parameters. It is built using the 
[JCSG](https://github.com/miho/JCSG) constructive solid geometry library, written by Michael Hoffer.

The program provides the following features:
- automatic generation of parallel gear drives with spur gears, helical gears and double helical gears
- automatic generation of bevel gear drives with straight teeth, tangent teeth and herringbone teeth
- strength calculations for the gear drives
- generates the the technical report for the gear drives as a HTML file, containing the geometrical and strength parameters of the gears
- STL and OBJ import and export
- generates gear drives templates as JSON files
- a scripting interface which can be used to make complex objects using the Groovy language

![](/screenshots/jgears.png)
![](/screenshots/jgears2.png)
## Building requirements

- Java >= 11
- [JavaFX](https://openjfx.io/)
- [Apache NetBeans](https://netbeans.apache.org/)

The program is written using NetBeans 12.3. Just clone the repository and run the main method in the Jgears.java class.
