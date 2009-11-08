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
package org.gephi.ui.project;

import org.gephi.project.ProjectImpl.ProjectMetaDataImpl;
import org.gephi.project.api.Project;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.ProjectMetaData;
import org.openide.util.Lookup;

/**
 *
 * @author Mathieu Bastian
 */
public class ProjectPropertiesEditor extends javax.swing.JPanel {

    /** Creates new form ProjectPropertiesEditor */
    public ProjectPropertiesEditor() {
        initComponents();
    }

    public void load(Project project) {
        if (project.getDataObject() != null) {
            fileLabel.setText(project.getDataObject().getName());
        }
        ProjectMetaData metaData = project.getMetaData();
        nameTextField.setText(project.getName());
        titleTextField.setText(metaData.getTitle());
        authorTextField.setText(metaData.getAuthor());
        keywordsTextField.setText(metaData.getKeywords());
        descriptionTextArea.setText(metaData.getDescription());
    }

    public void save(Project project) {
        ProjectMetaDataImpl metaData = (ProjectMetaDataImpl)project.getMetaData();
        metaData.setTitle(nameTextField.getText());
        if (!titleTextField.getText().isEmpty() && !titleTextField.getText().equals(project.getName())) {
            ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
            pc.renameProject(project, titleTextField.getText());
        }
        metaData.setAuthor(authorTextField.getText());
        metaData.setKeywords(keywordsTextField.getText());
        metaData.setDescription(descriptionTextArea.getText());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descriptionPanel = new javax.swing.JPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        labelDescription = new javax.swing.JLabel();
        labelKeywords = new javax.swing.JLabel();
        keywordsTextField = new javax.swing.JTextField();
        authorTextField = new javax.swing.JTextField();
        labelAuthor = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        fileLabel = new javax.swing.JLabel();
        labelFile = new javax.swing.JLabel();
        labelTitle = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.descriptionPanel.border.title"))); // NOI18N

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setRows(3);
        descriptionScrollPane.setViewportView(descriptionTextArea);

        labelDescription.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.labelDescription.text")); // NOI18N

        labelKeywords.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.labelKeywords.text")); // NOI18N

        keywordsTextField.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.keywordsTextField.text")); // NOI18N

        authorTextField.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.authorTextField.text")); // NOI18N

        labelAuthor.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.labelAuthor.text")); // NOI18N

        labelName.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.labelName.text")); // NOI18N

        nameTextField.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.nameTextField.text")); // NOI18N

        fileLabel.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.fileLabel.text")); // NOI18N

        labelFile.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.labelFile.text")); // NOI18N

        labelTitle.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.labelTitle.text")); // NOI18N

        titleTextField.setText(org.openide.util.NbBundle.getMessage(ProjectPropertiesEditor.class, "ProjectPropertiesEditor.titleTextField.text")); // NOI18N

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelTitle)
                    .addComponent(labelAuthor)
                    .addComponent(labelFile)
                    .addComponent(labelName)
                    .addComponent(labelKeywords)
                    .addComponent(labelDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(descriptionScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addComponent(fileLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addComponent(keywordsTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addComponent(authorTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addComponent(titleTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLabel)
                    .addComponent(labelFile))
                .addGap(12, 12, 12)
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitle)
                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAuthor)
                    .addComponent(authorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keywordsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelKeywords))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDescription)
                    .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField authorTextField;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JTextField keywordsTextField;
    private javax.swing.JLabel labelAuthor;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelFile;
    private javax.swing.JLabel labelKeywords;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField titleTextField;
    // End of variables declaration//GEN-END:variables
}
