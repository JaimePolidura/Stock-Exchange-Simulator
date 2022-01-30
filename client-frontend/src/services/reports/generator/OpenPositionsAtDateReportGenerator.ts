import {ReportGenerator} from "./ReportGenerator";
import {ReportType} from "../ReportType";

export class OpenPositionsAtDateReportGenerator extends ReportGenerator {
    generateReport(data: any): any {
        //TODO
    }

    reportType(): ReportType {
        return ReportType.OPEN_POSITIONS_AT_DATE;
    }

}