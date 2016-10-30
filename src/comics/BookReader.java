/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comics;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 *
 * @author Mehdi Raza
 */
public class BookReader extends javax.swing.JFrame {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(BookReader.class.getName());
    public static final String TOC_ENTRY_NAME="book.info";
    private Models.BookModel books;
    final int[] current=new int[1]; // current opened book id
    private Models.IndexModel indexModel;
    public static final String BOOKS_FOLDER=System.getProperty("user.dir") + "\\books\\";
    
    /**
     * Creates new form BookViewer
     */
    public BookReader() {
        initComponents();
    }

    public static void main(String args[]) {        
        try {
            /*javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels=javax.swing.UIManager.getInstalledLookAndFeels();
            for (int idx=0; idx<installedLookAndFeels.length; idx++) {
                if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
                    javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
                    break;
                }
            }*/
           UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookReader().setVisible(true);
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectBookDialog = new javax.swing.JDialog();
        prevSelectBookButton = new javax.swing.JButton();
        nextSelectBookButton = new javax.swing.JButton();
        selectBookImage = new javax.swing.JLabel();
        toolbar = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        splitPane = new javax.swing.JSplitPane();
        indexPane = new javax.swing.JScrollPane();
        indexList = new javax.swing.JList<String>();
        pagePane = new javax.swing.JScrollPane();
        page = new javax.swing.JLabel();

        selectBookDialog.setTitle("Select Book");
        selectBookDialog.setAlwaysOnTop(true);
        selectBookDialog.setLocation(new java.awt.Point(0, 0));
        selectBookDialog.setMinimumSize(new java.awt.Dimension(450, 265));
        selectBookDialog.setModal(true);
        selectBookDialog.setResizable(false);
        selectBookDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                selectBookDialogWindowOpened(evt);
            }
        });

        prevSelectBookButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/comics/resources/prev.png"))); // NOI18N
        prevSelectBookButton.setMaximumSize(new java.awt.Dimension(30, 109));
        prevSelectBookButton.setMinimumSize(null);
        selectBookDialog.getContentPane().add(prevSelectBookButton, java.awt.BorderLayout.WEST);

        nextSelectBookButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/comics/resources/next.png"))); // NOI18N
        nextSelectBookButton.setMaximumSize(new java.awt.Dimension(30, 109));
        nextSelectBookButton.setMinimumSize(null);
        selectBookDialog.getContentPane().add(nextSelectBookButton, java.awt.BorderLayout.EAST);

        selectBookImage.setToolTipText("Click to open Book");
        selectBookImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectBookImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectBookImageMouseClicked(evt);
            }
        });
        selectBookDialog.getContentPane().add(selectBookImage, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Comic Books Reader");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        toolbar.setFloatable(false);
        toolbar.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/comics/resources/book-open-icon.png"))); // NOI18N
        jButton1.setText("Open Book");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        toolbar.add(jButton1);

        getContentPane().add(toolbar, java.awt.BorderLayout.NORTH);

        indexList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        indexList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        indexList.setMinimumSize(new java.awt.Dimension(100, 0));
        indexList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                indexListValueChanged(evt);
            }
        });
        indexPane.setViewportView(indexList);

        splitPane.setLeftComponent(indexPane);

        pagePane.setViewportView(page);

        splitPane.setRightComponent(pagePane);

        getContentPane().add(splitPane, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(900, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        LOG.info("Loading books..");        
        books=new Models.BookModel(Loader.getInstance().loadBooks());
        selectBookDialog.setVisible(true);
    }//GEN-LAST:event_formWindowOpened
    
    private void selectBookDialogWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_selectBookDialogWindowOpened
        
        nextSelectBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    selectBookImage.setIcon(new ImageIcon(books.nextBookImage(current).getScaledInstance(selectBookImage.getWidth(), -1, Image.SCALE_SMOOTH)));
                    selectBookDialog.setTitle(books.getTitle(current[0]));
                } catch (IOException ex) {
                    Logger.getLogger(BookReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }        
        });
        
        prevSelectBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    selectBookImage.setIcon(new ImageIcon(books.prevBookImage(current).getScaledInstance(selectBookImage.getWidth(), -1, Image.SCALE_SMOOTH)));
                    selectBookDialog.setTitle(books.getTitle(current[0]));
                } catch (IOException ex) {
                    Logger.getLogger(BookReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }        
        });
        selectBookDialog.setLocationRelativeTo(this); // align center
        try {
            Image img=books.nextBookImage(current).getScaledInstance(selectBookImage.getWidth(), -1, Image.SCALE_SMOOTH);
            ImageIcon imgIcon=new ImageIcon(img);
            selectBookImage.setIcon(imgIcon);
            selectBookDialog.setTitle(books.getTitle(current[0]));
        } catch (IOException ex) {
            Logger.getLogger(BookReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_selectBookDialogWindowOpened

    private void selectBookImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectBookImageMouseClicked
        LOG.log(Level.INFO, "indexModel ...{0}", indexModel);
        try {
            indexModel=books.open(current[0]);
            indexModel.load();
            indexList.setModel(indexModel);
            selectBookDialog.setVisible(false);
            this.setTitle(indexModel.getBook().getBookName());
        } catch (InvalidBookException ex) {
            Logger.getLogger(BookReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_selectBookImageMouseClicked

    private void indexListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_indexListValueChanged
        if(evt.getValueIsAdjusting()) return; // not interesting in adjusting events
        try {
            LOG.log(Level.FINEST, "selectIndex {0}", indexList.getSelectedIndex());
            page.setIcon(new ImageIcon(indexModel.getImage(indexList.getSelectedIndex())));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_indexListValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        selectBookDialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> indexList;
    private javax.swing.JScrollPane indexPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton nextSelectBookButton;
    private javax.swing.JLabel page;
    private javax.swing.JScrollPane pagePane;
    private javax.swing.JButton prevSelectBookButton;
    private javax.swing.JDialog selectBookDialog;
    private javax.swing.JLabel selectBookImage;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
}
