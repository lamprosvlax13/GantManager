package Reports;

import backend.ReportType;

public class ReportFileFactory {

	public IReportFile createReportFile(ReportType type) {

		if (type != null) {
			if (type.equals(ReportType.TEXT)) {
				return new ExportAsTxt();

			}
			if (type.equals(ReportType.HTML)) {
				return new ExportAsHtml();

			}
			if (type.equals(ReportType.MD)) {
				return new ExportAsMarkDown();

			}

			System.out.println("wrong file type");
			return new NullExport();
		}

		return new NullExport();
	}

}
