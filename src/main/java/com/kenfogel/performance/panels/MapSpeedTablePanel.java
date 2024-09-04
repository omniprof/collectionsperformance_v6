package com.kenfogel.performance.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.kenfogel.performance.models.MapSpeedTableModel;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Panel for map test results
 *
 * @author Ken Fogel
 * @version 3.0
 *
 */
@SuppressWarnings("serial")
public class MapSpeedTablePanel extends JPanel {

    private JTable table = null;

    /**
     * @param mapSpeedTableModel
     */
    public MapSpeedTablePanel(MapSpeedTableModel mapSpeedTableModel) {
        super(new GridLayout(1, 0));

        table = new JTable(mapSpeedTableModel);
        initialize();
    }

    /**
     * Create the table and add it to the panel
     */
    private void initialize() {
        // Install custom renderer for headers
        table.getTableHeader().setDefaultRenderer(new HeaderRenderer());

        // Do not allow the user to reorder columns
        table.getTableHeader().setReorderingAllowed(false);

        // Required if column widths are being re-sized
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        Font originalFont = table.getFont();
        Font largerFont = new Font(originalFont.getName(), Font.PLAIN, 14);
        table.setFont(largerFont);

        table.setRowHeight(24);

        // Set column widths
        TableColumn column;
        column = table.getColumnModel().getColumn(0);
        column.setPreferredWidth(200);
        for (int x = 1; x < 3; ++x) {
            column = table.getColumnModel().getColumn(x);
            column.setPreferredWidth(110);
        }

        // Set the size of the table by calculating the best size
        table.setPreferredScrollableViewportSize(new Dimension(700, 200));
        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to this panel.
        add(scrollPane);
    }

    /**
     * This class draws the column header of the table. Allows for change in
     * font, foreground and background colour, border and text alignment.
     *
     * Found at web site: http://www.chka.de/swing/table/faq.html
     *
     * @author Christian Kaufhold (swing@chka.de)
     *
     */
    static class HeaderRenderer extends DefaultTableCellRenderer {

        public HeaderRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(true);

            // This call is needed because DefaultTableCellRenderer calls
            // setBorder()
            // in its constructor, which is executed after updateUI()
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        }

        @Override
        public void updateUI() {
            super.updateUI();
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean selected, boolean focused, int row,
                int column) {
            JTableHeader h = table != null ? table.getTableHeader() : null;

            if (h != null) {
                setEnabled(h.isEnabled());
                setComponentOrientation(h.getComponentOrientation());

                setForeground(h.getForeground());
                setBackground(h.getBackground());

                Font originalFont = h.getFont();
                Font boldFont = new Font(originalFont.getName(), Font.BOLD, 14);
                h.setFont(boldFont);
                setFont(h.getFont());
            } else {
		 // Use sensible values instead of random leftover values from
		 // the last call
                setEnabled(true);
                setComponentOrientation(ComponentOrientation.UNKNOWN);

                setForeground(UIManager.getColor("TableHeader.foreground"));
                setBackground(UIManager.getColor("TableHeader.background"));
                setFont(UIManager.getFont("TableHeader.font"));
            }
            setValue(value);
            return this;
        }
    }
}
