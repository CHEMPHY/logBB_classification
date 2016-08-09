
# coding: utf-8

# In[1]:

from rdkit.Chem import AllChem as Chem
from rdkit.Chem import ChemicalFeatures
from rdkit.Chem import Descriptors
from rdkit import RDConfig
from sys import argv
import os



# In[2]:

fdefName = os.path.join(RDConfig.RDDataDir,'BaseFeatures.fdef')
factory = ChemicalFeatures.BuildFeatureFactory(fdefName)


# In[3]:

isFirst = True
smiles = []
lines = []
with open(argv[1]) as f:
    for line in f:
        if isFirst == True:
            lines.append(line[0:-1])
            isFirst = False
        else:
            lines.append(line[0:-1])
            temp = line[0:-1].split(',')
            smiles.append(temp[0])
    f.close()


# In[4]:

with open(argv[2],'w') as f:
    f.write(lines[0]+','+','.join(['TPSA_CDK','MolLogP','NumHDonors','NumHAcceptors','NumHDonors+NumHAcceptors'])+'\n')
    for mol, line in zip(smiles,lines[1:]):
        f.write(line)
        m = Chem.MolFromSmiles(mol)
        f.write(','+','.join([str(Descriptors.TPSA(m)),str(Descriptors.MolLogP(m)),
                str(Descriptors.NumHDonors(m)),str(Descriptors.NumHAcceptors(m)),
                str(Descriptors.NumHDonors(m)+Descriptors.NumHAcceptors(m))])
                +'\n')
    f.close()

