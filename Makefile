all: main

main: src/CDKfeatureGeneration.java
	javac -cp "./bin/cdk-1.5.13.jar:/Applications/ChemAxon/MarvinBeans/lib/MarvinBeans.jar" -d ./bin ./src/CDKfeatureGeneration.java
clean:
	rm bin/*.class