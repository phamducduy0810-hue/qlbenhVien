/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pmqlbenhvienthucuc;

/**
 *
 * @author phamduy
 */
public class PMQLBenhVienThuCuc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Run main frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gui.FrmMain().setVisible(true);
            }
        });
    }
}
