package com.kenfogel.performance.models;

import javax.swing.table.AbstractTableModel;

/**
 * Table Model for map collection testing results
 *
 * Added annotations and moved initialization to constructor
 *
 * @author Ken Fogel
 * @version 4.1
 *
 */
@SuppressWarnings("serial")
public class MapSpeedTableModel extends AbstractTableModel {

    private final String[] columnNames = {"", "Hash Map", "Tree Map"};
    private final Object[][] data;

    private final Long zero = Long.valueOf(0);

    public MapSpeedTableModel() {
        this.data = new Object[][]{{"Load data", 0L, 0L}, {"Add element", 0L, 0L}, {"Find elements", 0L, 0L}};
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /**
     * JTable uses this method to determine the default renderer/ editor for
     * each cell.
     * @param c
     * @return 
     */
    @Override
    public Class<? extends Object> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * Used by DataLoader to update table after test runs
     * @param value
     * @param row
     * @param col
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    /**
     * Clear all results in the table
     */
    public void clearResults() {
        for (int x = 0; x < data.length; ++x) {
            for (int y = 1; y < data[0].length; ++y) {
                setValueAt(zero, x, y);
            }
        }
    }
}
