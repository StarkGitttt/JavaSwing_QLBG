/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class MsgBox {
    static String title = "Hệ Thống";
    public static void alert(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, title,
                JOptionPane.INFORMATION_MESSAGE, null);
    }
    public static boolean confirm(Component parent, String message) {
        int choice = JOptionPane.showConfirmDialog(parent, message,title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        return choice == JOptionPane.YES_OPTION;
    }
    public static String prompt(Component parent, String message) {
        return JOptionPane.showInputDialog(parent, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
