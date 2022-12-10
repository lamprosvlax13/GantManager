package Parser;

public class IloaderFactory {

	public ILoader createLoadFactory(String typeLoadFile) {

		if (typeLoadFile != null) {

			if (typeLoadFile.endsWith(".tsv") || typeLoadFile.endsWith(".txt")) {
				return new LoaderTsvFile();
			} else {
				return new NullLoader();
				
			}
		}
		return new NullLoader();
		

	}
}
