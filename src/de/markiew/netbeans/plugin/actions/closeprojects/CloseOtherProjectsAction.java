/**
 * Copyright 2012 markiewb
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
package de.markiew.netbeans.plugin.actions.closeprojects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Project",
id = "closeotherprojects.CloseOtherProjectsAction")
@ActionRegistration(
    displayName = "#CTL_CloseOtherProjectsAction")
@Messages("CTL_CloseOtherProjectsAction=Close Other Projects")
@ActionReferences({
    @ActionReference(path = "Projects/Actions", position = 100),
    @ActionReference(path = "Menu/File", position = 710)
})
public final class CloseOtherProjectsAction implements ActionListener {

    private final List<Project> selectedProjects;

    public CloseOtherProjectsAction(List<Project> context) {
        this.selectedProjects = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        OpenProjects manager = OpenProjects.getDefault();
        List<Project> openProjects = new ArrayList<Project>(Arrays.asList(manager.getOpenProjects()));
        openProjects.removeAll(selectedProjects);

        if (!openProjects.isEmpty()) {
            Project[] otherProjectsToBeClosed = openProjects.toArray(new Project[openProjects.size()]);
            manager.close(otherProjectsToBeClosed);
        }


    }
}