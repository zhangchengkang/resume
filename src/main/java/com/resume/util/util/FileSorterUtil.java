package com.resume.util.util;

import java.io.File;
import java.sql.Date;
import java.text.Collator;
import java.util.Comparator;

/**
 * 文件排序
 */
public class FileSorterUtil implements Comparator<File> {
    /**
     * 默认排序的方式， 按目录，文件排序TYPE_DIR
     */
    public static final int TYPE_DEFAULT = -1;
    /**
     * 按修改时间，降序
     */
    public static final int TYPE_MODIFIED_DATE_DOWN = 1;
    /**
     * 按修改时间，升序
     */
    public static final int TYPE_MODIFIED_DATE_UP = 2;
    /**
     * 按文件大小，降序
     */
    public static final int TYPE_SIZE_DOWN = 3;
    /**
     * 按文件大小，升序
     */
    public static final int TYPE_SIZE_UP = 4;
/*  public static final int TYPE_NAME_DOWN            = 5;
    public static final int TYPE_NAME_UP            = 6;*/
    /**
     * 按文件名
     */
    public static final int TYPE_NAME = 5;
    /**
     * 按目录，文件排序
     */
    public static final int TYPE_DIR = 7;
    /**
     * 文件名为数字排序
     */
    public static final int TYPE_INT_NAME = 8;
    private int mType = -1;

    public FileSorterUtil(int type) {
        if (type < 0 || type > 8) {
            type = TYPE_DIR;
        }
        mType = type;
    }

    @Override
    public int compare(File object1, File object2) {
        int result = 0;
        switch (mType) {
            case TYPE_MODIFIED_DATE_DOWN://last modified date down
                result = compareByModifiedDateDown(object1, object2);
                break;
            case TYPE_MODIFIED_DATE_UP://last modified date up
                result = compareByModifiedDateUp(object1, object2);
                break;
            case TYPE_SIZE_DOWN:    // file size down
                result = compareBySizeDown(object1, object2);
                break;
            case TYPE_SIZE_UP:        //file size up
                result = compareBySizeUp(object1, object2);
                break;
            case TYPE_NAME:            //name
                result = compareByName(object1, object2);
                break;
            case TYPE_DIR:            //dir or file
                result = compareByDir(object1, object2);
                break;
            case TYPE_INT_NAME:            //dir or file
                result = compareByModifiedDateUp11(object1, object2);
                break;
            default:
                result = compareByDir(object1, object2);
                break;
        }
        return result;
    }

    private int compareByModifiedDateDown(File object1, File object2) {
        long d1 = object1.lastModified();
        long d2 = object2.lastModified();
        if (d1 == d2) {
            return 0;
        } else {
            return d1 < d2 ? 1 : -1;
        }
    }

    private int compareByModifiedDateUp(File object1, File object2) {
        long d1 = object1.lastModified();
        long d2 = object2.lastModified();
        if (d1 == d2) {
            return 0;
        } else {
            return d1 > d2 ? 1 : -1;
        }
    }

    private int compareByModifiedDateUp11(File object1, File object2) {
        long d1 = Integer.valueOf(object1.getName());
        long d2 = Integer.valueOf(object2.getName());
        if (d1 == d2) {
            return 0;
        } else {
            return d1 > d2 ? 1 : -1;
        }
    }

    private int compareBySizeDown(File object1, File object2) {
        if (object1.isDirectory() && object2.isDirectory()) {
            return 0;
        }
        if (object1.isDirectory() && object2.isFile()) {
            return -1;
        }
        if (object1.isFile() && object2.isDirectory()) {
            return 1;
        }
        long s1 = object1.length();
        long s2 = object2.length();
        if (s1 == s2) {
            return 0;
        } else {
            return s1 < s2 ? 1 : -1;
        }
    }

    private int compareBySizeUp(File object1, File object2) {
        if (object1.isDirectory() && object2.isDirectory()) {
            return 0;
        }
        if (object1.isDirectory() && object2.isFile()) {
            return -1;
        }
        if (object1.isFile() && object2.isDirectory()) {
            return 1;
        }
        long s1 = object1.length();
        long s2 = object2.length();
        if (s1 == s2) {
            return 0;
        } else {
            return s1 > s2 ? 1 : -1;
        }
    }

    private int compareByName(File object1, File object2) {
        Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
        return cmp.compare(object1.getName(), object2.getName());
    }

    private int compareByDir(File object1, File object2) {
        if (object1.isDirectory() && object2.isFile()) {
            return -1;
        } else if (object1.isDirectory() && object2.isDirectory()) {
            return compareByName(object1, object2);
        } else if (object1.isFile() && object2.isDirectory()) {
            return 1;
        } else {  //object1.isFile() && object2.isFile())
            return compareByName(object1, object2);
        }
    }

    //for test
    private static void printFileArray(File[] list) {
        System.out.println("文件大小\t\t文件修改日期\t\t文件类型\t\t文件名称");
        for (File f : list) {
            System.out.println(f.length() + "\t\t" + new Date(f.lastModified()).toString() + "\t\t" + (f.isDirectory() ? "目录" : "文件") + "\t\t" + f.getName());
        }
    }
    //for test
//    public static void main(String[] args) {
//        String local = "E:\\senddata\\005214-201507180333400360-201507180445480440\\task";
//        List<FileInfo> list1 = LocalFileUtils.listFileAll(local, null);
//        for (FileInfo one : list1) {
//            System.out.println(one.getFileName());
//        }
//        File[] list = new File("E:\\senddata\\005214-201507180333400360-201507180445480440\\task").listFiles();
//        Arrays.sort(list, new FileSorterUtil(FileSorterUtil.TYPE_INT_NAME));
//        Arrays.sort(list, new FileSorterUtil(FileSorterUtil.TYPE_NAME));
//        printFileArray(list);
//    }
}