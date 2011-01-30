/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>, Eduardo Ramos <eduramiba@gmail.com>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.visualization.apiimpl.contextmenuitems;

import java.util.ArrayList;
import javax.swing.Icon;
import org.gephi.data.attributes.api.AttributeRow;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.graph.api.Node;
import org.gephi.visualization.spi.GraphContextMenuItem;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 */
@ServiceProvider(service = GraphContextMenuItem.class)
public class OpenURL implements GraphContextMenuItem {

    private Node node;

    public void setup(HierarchicalGraph graph, Node[] nodes) {
        if (nodes.length == 1) {
            node = nodes[0];
        }else{
            node=null;
        }
    }

    public void execute() {
    }

    public GraphContextMenuItem[] getSubItems() {
        ArrayList<GraphContextMenuItem> subItems = new ArrayList<GraphContextMenuItem>();

        //Always provide OpenURLLastItem so its shortcut is available:
        subItems.add(new OpenURLLastItem());
        if (node != null) {
            AttributeRow row = (AttributeRow) node.getNodeData().getAttributes();
            for (int i = 0; i < row.countValues(); i++) {
                if ((row.getColumnAt(i).getType() == AttributeType.STRING || row.getColumnAt(i).getType() == AttributeType.DYNAMIC_STRING) && row.getValue(i) != null) {
                    subItems.add(new OpenURLSubItem(row.getColumnAt(i).getTitle(), i));
                }
            }
        }
        return subItems.toArray(new GraphContextMenuItem[0]);
    }

    public String getName() {
        return NbBundle.getMessage(OpenURL.class, "GraphContextMenu_OpenURL");
    }

    public String getDescription() {
        return NbBundle.getMessage(OpenURL.class, "GraphContextMenu_OpenURL.description");
    }

    public boolean isAvailable() {
        return node != null;
    }

    public boolean canExecute() {
        return true;
    }

    public int getType() {
        return 400;
    }

    public int getPosition() {
        return 300;
    }

    public Icon getIcon() {
        return ImageUtilities.loadImageIcon("org/gephi/visualization/api/resources/globe-network.png", false);
    }

    public Integer getMnemonicKey() {
        return null;
    }
}
