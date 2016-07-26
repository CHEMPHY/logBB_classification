import java.lang.String;
import java.io.*;

import org.openscience.cdk.*;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.interfaces.*;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.exception.*;
import org.openscience.cdk.qsar.*;
import org.openscience.cdk.qsar.descriptors.molecular.*;
import org.openscience.cdk.qsar.result.*;

import chemaxon.struc.*;
import chemaxon.formats.*;
import chemaxon.marvin.calculations.*;
import chemaxon.marvin.plugin.*;


public class featureGeneration {
	public static void main(String[] args) {
		try {
			String inputFileName = "../data/mol.txt";
			String outputFileName = "../data/features.csv";
			String data;
			BufferedReader br = new BufferedReader(new FileReader(inputFileName));
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
			SmilesParser sp  = new SmilesParser(SilentChemObjectBuilder.getInstance());
			IAtomContainer m;
			IMolecularDescriptor TPSA = new TPSADescriptor();
			IMolecularDescriptor ALOGP = new ALOGPDescriptor();
			IMolecularDescriptor XLOGP = new XLogPDescriptor();
			IMolecularDescriptor MannholdLogP = new MannholdLogPDescriptor();
			IMolecularDescriptor n_acid = new AcidicGroupCountDescriptor();
			IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
			n_acid.initialise(builder);
			IMolecularDescriptor n_base = new BasicGroupCountDescriptor();
			n_base.initialise(builder);
		    Molecule msmol;
		    // create plugin
		    MajorMicrospeciesPlugin plugin = new MajorMicrospeciesPlugin();
		    // set pH
		    plugin.setpH(7);
		    bw.write(
		    	"SMILES" + "," + "TPSA" + "," + "ALOGP" + ","
		    	+ "XLOGP" + "," + "MannholdLogP" + "," + "n_acid" + "," + "n_base" + ","
		    	+ "n_acid" + "+" + "n_base");
		    bw.newLine();
			while(true) {
				data = br.readLine();
				if( data == null ) {
					break;
				}
				// read input molecule
		   		Molecule mol = MolImporter.importMol(data);
		   		// set target molecule
		   		plugin.setMolecule(mol);
		    	// run the calculation
		    	plugin.run();
		    	// get result
		    	msmol = plugin.getMajorMicrospecies();
				m = sp.parseSmiles(MolExporter.exportToFormat(msmol, "smiles"));
				bw.write(
					MolExporter.exportToFormat(msmol, "smiles") + ","
					+ TPSA.calculate(m).getValue() + ","
					+ ALOGP.calculate(m).getValue().toString().split(",")[0] + ","
					+ XLOGP.calculate(m).getValue().toString().split(",")[0] + ","
					+ MannholdLogP.calculate(m).getValue().toString() + ","
					+ n_acid.calculate(m).getValue() + ","
					+ n_base.calculate(m).getValue() + ","
					+ String.valueOf(Integer.parseInt(n_acid.calculate(m).getValue().toString())+Integer.parseInt(n_base.calculate(m).getValue().toString()))
				);
				bw.newLine();
			}
			bw.close();
		} catch(PluginException e) {
			System.err.println(e.getMessage());
		} catch (InvalidSmilesException e) {
			System.err.println(e.getMessage());
		} catch (CDKException e) {
			System.err.println(e.getMessage());
		} catch(IOException e){
			System.err.println(e.getMessage());
		}
	}
}
