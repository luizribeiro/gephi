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
package org.gephi.io.container.standard;

import org.gephi.io.container.EdgeDefault;

/**
 *
 * @author Mathieu Bastian
 */
public class ImportContainerParameters {

    private boolean selfLoops = true;
    private boolean parallelEdges = true;
    private boolean autoNode = true;
    private EdgeDefault edgeDefault = EdgeDefault.MIXED;

    public boolean isAutoNode() {
        return autoNode;
    }

    public void setAutoNode(boolean autoNode) {
        this.autoNode = autoNode;
    }

    public boolean isParallelEdges() {
        return parallelEdges;
    }

    public void setParallelEdges(boolean parallelEdges) {
        this.parallelEdges = parallelEdges;
    }

    public boolean isSelfLoops() {
        return selfLoops;
    }

    public void setSelfLoops(boolean selfLoops) {
        this.selfLoops = selfLoops;
    }

    public EdgeDefault getEdgeDefault() {
        return edgeDefault;
    }

    public void setEdgeDefault(EdgeDefault edgeDefault) {
        this.edgeDefault = edgeDefault;
    }
}
