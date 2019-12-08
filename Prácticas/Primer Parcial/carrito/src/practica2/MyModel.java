/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LockonDaniel
 */
public class MyModel extends DefaultTableModel {

    public boolean isCellEditable(int row, int column) {
        if (column <= 3) {
            return false;
        }
        return true;
    }
}