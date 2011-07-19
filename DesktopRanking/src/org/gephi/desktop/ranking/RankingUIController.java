/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
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
package org.gephi.desktop.ranking;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.project.api.WorkspaceListener;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.RankingModel;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.spi.TransformerBuilder;
import org.gephi.ranking.spi.TransformerUI;
import org.openide.util.Lookup;

/**
 *
 * @author Mathieu Bastian
 */
public class RankingUIController {

    private final Map<String, LinkedHashMap<String, Transformer>> transformers;
    private final String[] elementTypes = new String[]{Ranking.NODE_ELEMENT, Ranking.EDGE_ELEMENT};
    private RankingUIModel model;

    public RankingUIController(final ChangeListener modelChangeListener) {
        transformers = new HashMap<String, LinkedHashMap<String, Transformer>>();
        initTransformers();

        final ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        final RankingController rc = Lookup.getDefault().lookup(RankingController.class);
        pc.addWorkspaceListener(new WorkspaceListener() {

            public void initialize(Workspace workspace) {
            }

            public void select(Workspace workspace) {
                model = workspace.getLookup().lookup(RankingUIModel.class);
                if (model == null) {
                    RankingModel rankingModel = rc.getModel(workspace);
                    model = new RankingUIModel(RankingUIController.this, rankingModel);
                    workspace.add(model);
                }
                modelChangeListener.stateChanged(new ChangeEvent(model));
            }

            public void unselect(Workspace workspace) {
            }

            public void close(Workspace workspace) {
            }

            public void disable() {
                model = null;
                modelChangeListener.stateChanged(new ChangeEvent(model));
            }
        });

        if (pc.getCurrentWorkspace() != null) {
            model = pc.getCurrentWorkspace().getLookup().lookup(RankingUIModel.class);
            if (model == null) {
                RankingModel rankingModel = rc.getModel(pc.getCurrentWorkspace());
                model = new RankingUIModel(this, rankingModel);
                pc.getCurrentWorkspace().add(model);
                modelChangeListener.stateChanged(new ChangeEvent(model));
            }
        }
    }

    public RankingUIModel getModel() {
        return model;
    }

    private void initTransformers() {
        for (String elementType : elementTypes) {
            LinkedHashMap<String, Transformer> elmtTransformers = new LinkedHashMap<String, Transformer>();
            transformers.put(elementType, elmtTransformers);
        }

        for (TransformerBuilder builder : Lookup.getDefault().lookupAll(TransformerBuilder.class)) {
            for (String elementType : elementTypes) {
                Map<String, Transformer> elmtTransformers = transformers.get(elementType);
                if (builder.isTransformerForElement(elementType)) {
                    elmtTransformers.put(builder.getName(), builder.buildTransformer());
                }
            }
        }
    }

    public Transformer[] getTransformers(String elementType) {
        return transformers.get(elementType).values().toArray(new Transformer[0]);
    }

    public TransformerUI getUI(Transformer transformer) {
        for (TransformerUI ui : Lookup.getDefault().lookupAll(TransformerUI.class)) {
            if (ui.isUIForTransformer(transformer)) {
                return ui;
            }
        }
        return null;
    }

    public String[] getElementTypes() {
        return elementTypes;
    }
}
