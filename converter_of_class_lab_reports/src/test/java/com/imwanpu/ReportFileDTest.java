package com.imwanpu;

import org.junit.Test;

import java.io.File;

public class ReportFileDTest {
    File source = new File("D:\\SSKtmp\\source");
    File target = new File("D:\\SSKtmp\\targe");

    @Test
    public void getReportFilesTest(){
        ReportFileD.getReportFilePaths(source);
    }

    @Test
    public void deconstructAbsoluteReportFilesTest() {
        ReportFileD.init(source);
        for (ReportFileD r :
                ReportFileD.reportFiles) {
            System.out.println(r.fileDirectory);
            System.out.println(r.fileName);
            System.out.println(r.suffix);
        }
    }
}
