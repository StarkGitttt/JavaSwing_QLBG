/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author DELL
 */
public class XImg {
     public static Image logoApp() {
        ImageIcon logo = new ImageIcon("src\\icons\\favicon.png");
        return logo.getImage();
    }
    public static boolean save(File src) {
//        Destination : Điểm đến
        File dst = new File("src\\avatar", src.getName());
        if(!dst.getParentFile().exists()) {
            dst.getParentFile().mkdir();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());        
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }      
    }
     public static boolean saveImgProduct(File src) {
//        Destination : Điểm đến
        File dst = new File("src\\imageproduct", src.getName());
        if(!dst.getParentFile().exists()) {
            dst.getParentFile().mkdir();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());        
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }      
    }
    public static ImageIcon read(String fileName) {
        File path = new File("src\\avatar", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
    public static ImageIcon readImgProduct(String fileName) {
        File path = new File("src\\imageproduct", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
        public static boolean saveDAD(String fromFile, String nameFile) {
//        Destination : Điểm đến
        File dst = new File("src\\avatar", nameFile);
        if(!dst.getParentFile().exists()) {
            dst.getParentFile().mkdir();
        }
        try {
            Path from = Paths.get(fromFile);
            Path to = Paths.get(dst.getAbsolutePath());      
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
        public static boolean saveDADImgProduct(String fromFile, String nameFile) {
//        Destination : Điểm đến
        File dst = new File("src\\imageproduct", nameFile);
        if(!dst.getParentFile().exists()) {
            dst.getParentFile().mkdir();
        }
        try {
            Path from = Paths.get(fromFile);
            Path to = Paths.get(dst.getAbsolutePath());      
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }      
    }
}
