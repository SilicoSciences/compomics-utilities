package com.compomics.util.gui.parameters.identification_parameters;

import com.compomics.util.experiment.identification.protein_sequences.SequenceFactory;
import com.compomics.util.preferences.LastSelectedFolder;
import com.compomics.util.preferences.ProteinInferencePreferences;
import com.compomics.util.protein_sequences_manager.gui.SequenceDbDetailsDialog;
import java.awt.Dialog;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Dialog for the edition of the protein inference settings
 *
 * @author Marc Vaudel
 */
public class ProteinInferenceSettingsDialog extends javax.swing.JDialog {

    /**
     * The parent frame.
     */
    private java.awt.Frame parentFrame;
    /**
     * The normal dialog icon.
     */
    private Image normalIcon;
    /**
     * The waiting dialog icon.
     */
    private Image waitingIcon;
    /**
     * The last selected folder to use.
     */
    private final LastSelectedFolder lastSelectedFolder;
    /**
     * Boolean indicating whether the user canceled the editing.
     */
    private boolean canceled = false;
    /**
     * Boolean indicating whether the settings can be edited by the user.
     */
    private boolean editable;

    /**
     * Creates a new ProteinInferenceSettingsDialog with a frame as owner.
     *
     * @param parentFrame a parent frame
     * @param proteinInferencePreferences the protein inference settings to
     * display
     * @param normalIcon the normal dialog icon
     * @param waitingIcon the waiting dialog icon
     * @param lastSelectedFolder the last selected folder to use
     * @param editable boolean indicating whether the settings can be edited by the user
     */
    public ProteinInferenceSettingsDialog(java.awt.Frame parentFrame, ProteinInferencePreferences proteinInferencePreferences, 
            Image normalIcon, Image waitingIcon, LastSelectedFolder lastSelectedFolder, boolean editable) {
        super(parentFrame, true);
        this.parentFrame = parentFrame;
        this.normalIcon = normalIcon;
        this.waitingIcon = waitingIcon;
        this.lastSelectedFolder = lastSelectedFolder;
        this.editable = editable;
        initComponents();
        setUpGui();
        populateGUI(proteinInferencePreferences);
        setLocationRelativeTo(parentFrame);
        setVisible(true);
    }

    /**
     * Creates a new ProteinInferenceSettingsDialog with a dialog as owner.
     *
     * @param owner the dialog owner
     * @param parentFrame a parent frame
     * @param proteinInferencePreferences the protein inference settings to
     * display
     * @param normalIcon the normal dialog icon
     * @param waitingIcon the waiting dialog icon
     * @param lastSelectedFolder the last selected folder to use
     * @param editable boolean indicating whether the settings can be edited by the user
     */
    public ProteinInferenceSettingsDialog(Dialog owner, java.awt.Frame parentFrame, ProteinInferencePreferences proteinInferencePreferences, 
            Image normalIcon, Image waitingIcon, LastSelectedFolder lastSelectedFolder, boolean editable) {
        super(owner, true);
        this.parentFrame = parentFrame;
        this.normalIcon = normalIcon;
        this.waitingIcon = waitingIcon;
        this.lastSelectedFolder = lastSelectedFolder;
        this.editable = editable;
        initComponents();
        setUpGui();
        populateGUI(proteinInferencePreferences);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    /**
     * Set up the GUI.
     */
    private void setUpGui() {
        if (!editable) {
            editDatabaseDetailsButton.setText("View");
        }
    }

    /**
     * Fills the GUI with the given settings.
     *
     * @param proteinInferencePreferences the protein inference settings to
     * display
     */
    private void populateGUI(ProteinInferencePreferences proteinInferencePreferences) {
        if (proteinInferencePreferences.getProteinSequenceDatabase() != null) {
            databaseSettingsTxt.setText(proteinInferencePreferences.getProteinSequenceDatabase().getAbsolutePath());
            okButton.setEnabled(true);
        }
    }

    /**
     * Returns the protein inference preferences.
     *
     * @return the protein inference preferences
     */
    public ProteinInferencePreferences getProteinInferencePreferences() {
        ProteinInferencePreferences proteinInferencePreferences = new ProteinInferencePreferences();
        proteinInferencePreferences.setProteinSequenceDatabase(new File(databaseSettingsTxt.getText()));
        return proteinInferencePreferences;
    }

    /**
     * Indicates whether the user canceled the editing.
     *
     * @return a boolean indicating whether the user canceled the editing
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        dataBasePanelSettings = new javax.swing.JPanel();
        databaseSettingsLbl = new javax.swing.JLabel();
        databaseSettingsTxt = new javax.swing.JTextField();
        editDatabaseDetailsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Protein Inference Settings");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        backgroundPanel.setBackground(new java.awt.Color(230, 230, 230));

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.setEnabled(false);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        dataBasePanelSettings.setBorder(javax.swing.BorderFactory.createTitledBorder("Database"));
        dataBasePanelSettings.setOpaque(false);

        databaseSettingsLbl.setText("Database (FASTA)");

        databaseSettingsTxt.setEditable(false);

        editDatabaseDetailsButton.setText("Edit");
        editDatabaseDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDatabaseDetailsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataBasePanelSettingsLayout = new javax.swing.GroupLayout(dataBasePanelSettings);
        dataBasePanelSettings.setLayout(dataBasePanelSettingsLayout);
        dataBasePanelSettingsLayout.setHorizontalGroup(
            dataBasePanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataBasePanelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(databaseSettingsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(databaseSettingsTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editDatabaseDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        dataBasePanelSettingsLayout.setVerticalGroup(
            dataBasePanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataBasePanelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dataBasePanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(databaseSettingsLbl)
                    .addComponent(editDatabaseDetailsButton)
                    .addComponent(databaseSettingsTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGap(0, 433, Short.MAX_VALUE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addComponent(dataBasePanelSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataBasePanelSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Close the dialog.
     * 
     * @param evt 
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * Cancel the dialog.
     * 
     * @param evt 
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        canceled = true;
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Edit the database.
     * 
     * @param evt 
     */
    private void editDatabaseDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDatabaseDetailsButtonActionPerformed

        SequenceFactory sequenceFactory = SequenceFactory.getInstance();

        // clear the factory
        if (databaseSettingsTxt.getText().trim().length() == 0) {
            try {
                sequenceFactory.clearFactory();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to clear the sequence factory.", "File Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to clear the sequence factory.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        SequenceDbDetailsDialog sequenceDbDetailsDialog = new SequenceDbDetailsDialog(parentFrame, lastSelectedFolder, editable, normalIcon, waitingIcon);

        boolean success = sequenceDbDetailsDialog.selectDB(true);
        if (success) {
            sequenceDbDetailsDialog.setVisible(true);
        }

        if (sequenceFactory.getCurrentFastaFile() != null) {
            databaseSettingsTxt.setText(sequenceFactory.getCurrentFastaFile().getAbsolutePath());
        }
    }//GEN-LAST:event_editDatabaseDetailsButtonActionPerformed

    /**
     * Cancel the dialog.
     * 
     * @param evt 
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        canceled = true;
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel dataBasePanelSettings;
    private javax.swing.JLabel databaseSettingsLbl;
    private javax.swing.JTextField databaseSettingsTxt;
    private javax.swing.JButton editDatabaseDetailsButton;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

}
