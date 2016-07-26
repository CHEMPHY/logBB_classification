all: main

main: src/featureGeneration.java
	javac -cp "./bin/cdk-1.5.13.jar:/Applications/ChemAxon/MarvinBeans/lib/MarvinBeans.jar" -d ./bin ./src/featureGeneration.java
clean:
	rm bin/*.class