cd bin
java -cp "cdk-1.5.13.jar:/Applications/ChemAxon/MarvinBeans/lib/MarvinBeans.jar:." CDKfeatureGeneration ../data/train_mols.txt ../data/train_CDKfeatures.csv
java -cp "cdk-1.5.13.jar:/Applications/ChemAxon/MarvinBeans/lib/MarvinBeans.jar:." CDKfeatureGeneration ../data/test_mols.txt ../data/test_CDKfeatures.csv
cd ../src
python3 appendRDkitFeatureGeneration.py ../data/train_CDKfeatures.csv ../data/train_mergeFeatures.csv
python3 appendRDkitFeatureGeneration.py ../data/test_CDKfeatures.csv ../data/test_mergeFeatures.csv
cd ..