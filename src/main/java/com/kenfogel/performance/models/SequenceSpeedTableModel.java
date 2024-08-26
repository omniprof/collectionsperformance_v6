package com.kenfogel.performance.models;

import javax.swing.table.AbstractTableModel;

/**
 * Table Model for sequence collections testing results
 *
 * Added annotations and moved initialization to constructor
 *
 * @author Ken Fogel
 * @version 4.1
 *
 */
@SuppressWarnings("serial")
public class SequenceSpeedTableModel extends AbstractTableModel {

    private final String[] columnNames = {"", "Array", "ArrayList", "Deque",
        "Linked List"};
    private final Object[][] data;

    private final Long zero = Long.valueOf(0);

    public SequenceSpeedTableModel() {
        this.data = new Object[][]{{"Load Data", 0L, 0L, 0L, 0L},
        {"Access first element", 0L, 0L, 0L, 0L},
        {"Access last element", 0L, 0L, 0L, 0L},
        {"Access middle element", 0L, 0L, 0L, 0L},
        {"Insert at start", 0L, 0L, 0L, 0L},
        {"Insert at end", 0L, 0L, 0L, 0L},
        {"Insert in middle", 0L, 0L, 0L, 0L}};
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
     *
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
