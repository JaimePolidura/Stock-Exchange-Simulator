import {ReportType} from "../ReportType";

export abstract class ReportGenerator {
    abstract reportType(): ReportType;
    abstract generateReport(data: any): any;
}