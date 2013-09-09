/**
 * Copyright 2013 markiewb
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.markiew.netbeans.plugin.actions.closedocuments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.loaders.DataObject;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public final class DocumentManager {

    public static DocumentManager getInstance() {
        return new DocumentManager();
    }

    private DocumentManager() {
    }

    /**
     *
     * @param projectToMatch
     * @param onlyMatching if true, it returns only {@link TopComponent}s which
     * are from the same project; if false, it returns only {@link TopComponent}s
     * which are not from the given project
     * @return
     */
    public Collection<TopComponent> getDocumentsForProject(Project projectToMatch, boolean onlyMatching) {

        if (projectToMatch==null)
        {
            return Collections.emptyList();
        }

        final WindowManager wm = WindowManager.getDefault();
        final LinkedHashSet<TopComponent> result = new LinkedHashSet<TopComponent>();
        for (TopComponent tc : getCurrentEditors()) {
            if (!wm.isEditorTopComponent(tc)) {
                continue;
            }
            DataObject dob = tc.getLookup().lookup(DataObject.class);
            if (dob != null) {
                Project projectFromFile = FileOwnerQuery.getOwner(dob.getPrimaryFile());
                final boolean sameProject = projectToMatch.equals(projectFromFile);
                if ((onlyMatching && sameProject) || (!onlyMatching && !sameProject)) {
                    result.add(tc);
                }
            } else {
                if (onlyMatching) {
                    //NOP
                } else {
                    //close others also closes documents without projects
                    //for example diff windows will be closed
                    result.add(tc);
                }
            }
        }
        return result;
    }

    private Collection<TopComponent> getCurrentEditors() {
        final ArrayList<TopComponent> result = new ArrayList<TopComponent>();
        final WindowManager wm = WindowManager.getDefault();
        for (Mode mode : wm.getModes()) {
            if (wm.isEditorMode(mode)) {
                result.addAll(Arrays.asList(mode.getTopComponents()));
            }
        }
        return result;
    }

}
