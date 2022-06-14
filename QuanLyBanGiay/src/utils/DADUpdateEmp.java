/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author DELL
 */
public class DADUpdateEmp implements DropTargetListener {
    JLabel imageLabel = new JLabel();
    JLabel pathLabel = new JLabel();
    public DADUpdateEmp(JLabel image) {
        imageLabel = image;
    }
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
       
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        dtde.acceptDrop(DnDConstants.ACTION_COPY);
        // Lớp Transferable cung cấp dữ liệu cho một hành động truyền
        Transferable transfer = dtde.getTransferable();
        // Get data format of the items
        DataFlavor[] dataFlavor = transfer.getTransferDataFlavors();
        
        // Loop DataFlavor
        for(DataFlavor flav : dataFlavor) {
            try {
                if(flav.isFlavorJavaFileListType()) {
                    List<File> files = (List<File>) transfer.getTransferData(flav);
                    for(File file : files) {
                        displayImage(file.getPath());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void displayImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (Exception e) {
            
        }
        ImageIcon icon = new ImageIcon(img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
                BufferedImage.SCALE_SMOOTH));
        imageLabel.setIcon(icon);
        imageLabel.setToolTipText(path.substring(path.lastIndexOf("\\") + 1));
        imageLabel.setName(path);
        XImg.saveDAD(path, imageLabel.getToolTipText());
    }
}
