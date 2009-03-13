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

package gephi.visualization.opengl.compatibility;

import gephi.visualization.initializer.Object3dInitializer;
import gephi.visualization.objects.Object3dClass;
import gephi.visualization.opengl.compatibility.initializer.CompatibilityObject3dInitializer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mathieu
 */
public class CompatibilityObject3dClass extends Object3dClass {

    private CompatibilityObject3dInitializer currentObject3dInitializer;

    //Initializer
    private List<CompatibilityObject3dInitializer> object3dInitializers;

    public CompatibilityObject3dClass(String name, boolean lod, boolean selectable)
    {
        super(name, lod, selectable);
        object3dInitializers = new ArrayList<CompatibilityObject3dInitializer>();
    }

    public void addObjectInitializer(Object3dInitializer object3dInitializer)
    {
        if(object3dInitializers.isEmpty())       //Set first one as current
            setCurrentObject3dInitializer(object3dInitializer);

        object3dInitializers.add((CompatibilityObject3dInitializer)object3dInitializer);
    }

    @Override
    public void setCurrentObject3dInitializer(Object3dInitializer object3dInitializer) {
        currentObject3dInitializer = (CompatibilityObject3dInitializer)object3dInitializer;
    }
    

    @Override
    public CompatibilityObject3dInitializer getCurrentObject3dInitializer() {
        return currentObject3dInitializer;
    }

    @Override
    public List<CompatibilityObject3dInitializer> getObject3dInitializers() {
        return object3dInitializers;
    }

    
}
