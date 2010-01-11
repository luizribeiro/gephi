/*
Copyright 2008 WebAtlas
Authors : Mathieu Bastian, Mathieu Jacomy, Julian Bilcke
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.ui.filters.partition;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;
import org.gephi.filters.partition.PartitionBuilder.PartitionFilter;
import org.gephi.partition.api.Part;
import org.gephi.partition.api.Partition;
import org.gephi.ui.components.PaletteIcon;
import org.gephi.ui.components.WrapLayout;

/**
 *
 * @author Mathieu Bastian
 */
public class PartitionPanel extends javax.swing.JPanel implements ActionListener {

    private final Object KEY = new Object();
    private PartitionFilter filter;

    public PartitionPanel() {
        super(new WrapLayout(FlowLayout.LEFT, 0, 0));
        initComponents();
    }

    public void actionPerformed(ActionEvent e) {
        JToggleButton tb = (JToggleButton)e.getSource();
        Part p = (Part)tb.getClientProperty(KEY);
        if(tb.isSelected()) {
            filter.addPart(p);
        } else {
            filter.removePart(p);
        }
    }

    public void setup(PartitionFilter filter) {
        this.filter = filter;
        removeAll();
        Partition partition = filter.getPartition();
        if (partition != null) {
            for (Part p : partition.getParts()) {
                JToggleButton tb = new JToggleButton(p.getDisplayName(), new PaletteIcon(new Color[]{p.getColor()}));
                tb.putClientProperty(KEY, p);
                add(tb);
            }
        }
        revalidate();
        repaint();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
