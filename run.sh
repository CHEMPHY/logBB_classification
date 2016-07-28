cd bin
java -cp "cdk-1.5.13.jar:/Applications/ChemAxon/MarvinBeans/lib/MarvinBeans.jar:." featureGeneration ../data/train_mols.txt ../data/train_features.csv
java -cp "cdk-1.5.13.jar:/Applications/ChemAxon/MarvinBeans/lib/MarvinBeans.jar:." featureGeneration ../data/test_mols.txt ../data/test_features.csv
cd ..