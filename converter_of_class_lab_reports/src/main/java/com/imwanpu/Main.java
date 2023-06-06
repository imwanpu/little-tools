package com.imwanpu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        (new ReportFileController()).addReportFiles2sharedDataCenter(sharedDataCenter.source);
        (new TargetReportPathMapController()).generateTargetReportPathMap(sharedDataCenter.target);

        (new TargetReportPathMapController()).copy();


    }
}

class Student {
    String number;
    String name;

    public Student(String number, String name) {
        this.number = number;
        this.name = name;
    }
}


class ReportFile {
    private final File absolutPath;
    private final String dir;
    private final NameWithoutSuffix nameWithoutSuffix;
    private final String suffix;


    public ReportFile(File absolutPath) {
        // 解析 dir
        String dir = absolutPath.getParent();
        // 解析 nameWithoutSuffix
        NameWithoutSuffix nameWithoutSuffix = deconstructNameWithoutSuffix(absolutPath.getName().replaceFirst("[.][^.]+$", ""));
        // 解析 suffix
        String suffix = absolutPath.getName().substring(absolutPath.getName().lastIndexOf('.') + 1);

        // 给私有变量赋值
        this.absolutPath = absolutPath;
        this.dir = dir;
        this.nameWithoutSuffix = nameWithoutSuffix;
        this.suffix = suffix;
    }

    private NameWithoutSuffix deconstructNameWithoutSuffix(String fileNameWithoutSuffix) {
        // 抽函数 ↓ // 文件间隔符有变化时 就改这个函数
        String[] fileNameWithoutSuffixArray = fileNameWithoutSuffix.split("-");
//        if (fileNameWithoutSuffixArray.length != 3) {
//            System.out.println("error format of report file name: " + absolutPath);
//        }
        String studentName = fileNameWithoutSuffixArray[0];
        String studentNumber = fileNameWithoutSuffixArray[1];
        Student student = new Student(studentNumber, studentName);
        // 解析 student
        // 解析 experimentName
        String experimentName = fileNameWithoutSuffix
                .replaceFirst(studentName,"")
                .replaceFirst("-","")
                .replaceFirst(studentNumber,"")
                .replaceFirst("-","");
        return new NameWithoutSuffix(student, experimentName);
        // 抽函数 ↑ 返回 nameWithoutSuffix
    }

    public String getDir() {
        return dir;
    }

    public NameWithoutSuffix getNameWithoutSuffix() {
        return nameWithoutSuffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public File getAbsolutPath() {
        return absolutPath;
    }
}

class NameWithoutSuffix {
    Student student;
    String experimentName;

    public NameWithoutSuffix(Student student, String experimentName) {
        this.student = student;
        this.experimentName = experimentName;
    }
}

class sharedDataCenter {
    // STATIC
    static File source = new File("D:\\SSKtmp\\source");
    static File target = new File("D:\\SSKtmp\\target");

    static ArrayList<ReportFile> reportFiles = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();
    static HashMap<File, File> targetReportPathMap = new HashMap<>();

}

class ReportFileController {
    // 填充 sharedDataCenter 的 reportFiles
    // 根据 source 获取完整目录列表 返回 File ArrayList, 私有
    private final ArrayList<File> absoluteReportFiles = new ArrayList<>();

    private void getAllAbsolutReportFile(File source) {
        if (source != null) {
            for (File file : Objects.requireNonNull(source.listFiles())) {
                if (file.isDirectory()) {
                    getAllAbsolutReportFile(file);
                } else {
                    absoluteReportFiles.add(file);
                }
            }
        } else {
            System.out.println("source is null");
        }


    }

    // 根据 File ArrayList 的每一条, 解析并 add to sharedDataCenter 的 reportFiles
    void addReportFiles2sharedDataCenter(File source) {
        this.getAllAbsolutReportFile(source);
        // 填充 sharedDataCenter 的 reportFiles
        for (File file : absoluteReportFiles) {
            sharedDataCenter.reportFiles.add(new ReportFile(file));
        }
    }


}


class TargetReportPathMapController {
    // 填充 targetReportPathMap 源路径为key 目标路径为value
    void generateTargetReportPathMap(File target) {
        for (ReportFile reportFile : sharedDataCenter.reportFiles) {
            File sourceFile = reportFile.getAbsolutPath();
            File targetFile = new File(target.getPath() + "/" +
                    reportFile.getNameWithoutSuffix().student.number + reportFile.getNameWithoutSuffix().student.name + "/" +
                    reportFile.getNameWithoutSuffix().experimentName + "." + reportFile.getSuffix()
            );
            sharedDataCenter.targetReportPathMap.put(sourceFile, targetFile);
        }

    }

    // copy 文件
    void copy() {
        for (File source : sharedDataCenter.targetReportPathMap.keySet()) {
            File target = sharedDataCenter.targetReportPathMap.get(source);

            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            try (FileInputStream fis = new FileInputStream(source); // 创建文件输入流
                 FileOutputStream fos = new FileOutputStream(target)) { // 创建文件输出流
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) { // 读取源文件内容并写入目标文件
                    fos.write(buffer, 0, len);
                }
                System.out.println("文件复制成功！");
            } catch (IOException e) {
                System.out.println("文件复制失败！");
                e.printStackTrace();
            }
        }
    }
//    void copy() {
//        File source = new File("D:\\SSKtmp\\source\\实验四\\杨君易-20205068-实验四：模拟黑客攻击.docx");
//        File target = new File("D:\\SSKtmp\\target\\20205068杨君易\\实验四：模拟黑客攻击.docx");
//
//        if (!target.getParentFile().exists()) {
//            target.getParentFile().mkdirs();
//        }
//
//        try (FileInputStream fis = new FileInputStream(source); // 创建文件输入流
//             FileOutputStream fos = new FileOutputStream(target)) { // 创建文件输出流
//            byte[] buffer = new byte[1024];
//            int len;
//            while ((len = fis.read(buffer)) > 0) { // 读取源文件内容并写入目标文件
//                fos.write(buffer, 0, len);
//            }
//            System.out.println("文件复制成功！");
//        } catch (IOException e) {
//            System.out.println("文件复制失败！");
//            e.printStackTrace();
//        }
//    }
}