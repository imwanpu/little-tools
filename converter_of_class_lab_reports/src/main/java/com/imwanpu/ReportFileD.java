package com.imwanpu;

import java.io.File;
import java.util.ArrayList;

public class ReportFileD {
    String fileDirectory;
    String fileName;
    String suffix;

    StudentD studentD;

    String experimentName;

    private ReportFileD(String fileDirectory, String fileName, String suffix, StudentD studentD, String experimentName) {
        this.fileDirectory = fileDirectory;
        this.fileName = fileName;
        this.suffix = suffix;
        this.studentD = studentD;
        this.experimentName = experimentName;
    }

    public static void init(File source){
        ReportFileD.getReportFilePaths(source);
        ReportFileD.deconstructAbsoluteReportFiles();
    }

    static ArrayList<ReportFileD> reportFiles = new ArrayList<>();
    private static ArrayList<File> absoluteReportFiles = new ArrayList<>();
    static void getReportFilePaths(File root){
        if (root == null){
            System.out.println("root is empty.");
        }
        File[] files = root.listFiles();
        for (File file :
                files) {
            if (file.isDirectory()) {
                getReportFilePaths(file);
            } else {
                absoluteReportFiles.add(file.getAbsoluteFile());
            }
        }
    }

    static void deconstructAbsoluteReportFiles() {
        for (File f : absoluteReportFiles) {
            String fileName = f.getName();
            String fileNameWithoutSuffix = fileName.replaceFirst("[.][^.]+$","");
            String suffix = fileName.substring(f.getName().lastIndexOf('.') + 1);

            String studentNumber = "";
            String studentName = "";
            String experimentName = "";






//            Student student = new Student()
//            reportFiles.add(new ReportFileD(
//                    f.getParent(),
//                    fileNameWithoutSuffix,
//                    suffix,
//                    student,
//
//            ));
        }
    }

//    static ArrayList<String> deconstructFileName(String fileNameWithoutSuffix) {
//
//    }
}
