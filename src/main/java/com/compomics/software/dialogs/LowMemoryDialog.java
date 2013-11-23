package com.compomics.software.dialogs;

import com.compomics.software.CompomicsWrapper;
import com.compomics.util.examples.BareBonesBrowserLaunch;
import com.compomics.util.preferences.UtilitiesUserPreferences;
import java.awt.Color;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFrame;
import no.uib.jsparklines.renderers.util.Util;

/**
 * A dialog for showing low memory issues.
 *
 * @author Harald Barsnes
 */
public class LowMemoryDialog extends javax.swing.JDialog {

    /**
     * The main instance of the GUI.
     */
    private JavaOptionsDialogParent javaOptionsDialogParent;
    /**
     * The frame parent.
     */
    private JFrame frameParent;
    /**
     * The name of the tool, e.g., PeptideShaker.
     */
    private String toolName;
    /**
     * A reference to the Welcome Dialog.
     */
    private JDialog welcomeDialog;

    /**
     * Creates a new LowMemoryDialog.
     *
     * @param parent the parent frame
     * @param javaOptionsDialogParent reference to the JavaOptionsDialogParent
     * @param toolName the name of the tool, e.g., PeptideShaker
     * @param welcomeDialog reference to the Welcome Dialog, can be null
     * @param modal
     */
    public LowMemoryDialog(JFrame parent, JavaOptionsDialogParent javaOptionsDialogParent, JDialog welcomeDialog, String toolName, boolean modal) {
        super(parent, modal);
        this.frameParent = parent;
        this.javaOptionsDialogParent = javaOptionsDialogParent;
        this.welcomeDialog = welcomeDialog;
        this.toolName = toolName;
        initComponents();
        setUpGUI();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    /**
     * Set up the GUI.
     */
    private void setUpGUI() {
        String javaHome = System.getProperty("java.home") + File.separator + "bin" + File.separator;
        javaHomeLabel.setText(javaHome);

        if (CompomicsWrapper.is64BitJava()) {
            bitLabel.setText("64 Bit Java");
        } else {
            bitLabel.setText("32 Bit Java");
            bitLabel.setForeground(Color.red);
        }

        try {
            UtilitiesUserPreferences utilitiesUserPreferences = UtilitiesUserPreferences.loadUserPreferences();
            int maxMemory = utilitiesUserPreferences.getMemoryPreference();
            memoryLabel.setText(Util.roundDouble(maxMemory * 0.000976563, 1) + " GB");

            if (maxMemory < 4000) {
                memoryLabel.setForeground(Color.red);
            }
        } catch (Exception e) {
            memoryLabel.setText("Error...");
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundsPanel = new javax.swing.JPanel();
        javaHomePanel = new javax.swing.JPanel();
        javaHomeLabel = new javax.swing.JLabel();
        bitPanel = new javax.swing.JPanel();
        bitLabel = new javax.swing.JLabel();
        bitRecommendationLabel = new javax.swing.JLabel();
        memoryPanel = new javax.swing.JPanel();
        memoryLabel = new javax.swing.JLabel();
        memoryRecommendationLabel = new javax.swing.JLabel();
        editMemoryJLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        javaHelpJLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Low Memory");

        backgroundsPanel.setBackground(new java.awt.Color(230, 230, 230));

        javaHomePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Java Home"));
        javaHomePanel.setOpaque(false);

        javaHomeLabel.setText("Java Home...");

        javax.swing.GroupLayout javaHomePanelLayout = new javax.swing.GroupLayout(javaHomePanel);
        javaHomePanel.setLayout(javaHomePanelLayout);
        javaHomePanelLayout.setHorizontalGroup(
            javaHomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javaHomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(javaHomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        javaHomePanelLayout.setVerticalGroup(
            javaHomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javaHomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(javaHomeLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bitPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("32 Bit or 64 Bit"));
        bitPanel.setOpaque(false);

        bitLabel.setText("64 Bit Java");

        bitRecommendationLabel.setFont(bitRecommendationLabel.getFont().deriveFont((bitRecommendationLabel.getFont().getStyle() | java.awt.Font.ITALIC)));
        bitRecommendationLabel.setText("Recommended: 64 Bit Java");

        javax.swing.GroupLayout bitPanelLayout = new javax.swing.GroupLayout(bitPanel);
        bitPanel.setLayout(bitPanelLayout);
        bitPanelLayout.setHorizontalGroup(
            bitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bitPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(376, 376, 376)
                .addComponent(bitRecommendationLabel)
                .addContainerGap())
        );
        bitPanelLayout.setVerticalGroup(
            bitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bitPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bitLabel)
                    .addComponent(bitRecommendationLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        memoryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Memory"));
        memoryPanel.setOpaque(false);

        memoryLabel.setText("60 GB");

        memoryRecommendationLabel.setFont(memoryRecommendationLabel.getFont().deriveFont((memoryRecommendationLabel.getFont().getStyle() | java.awt.Font.ITALIC)));
        memoryRecommendationLabel.setText("Recommended: at least 4 GB");

        editMemoryJLabel.setForeground(new java.awt.Color(0, 0, 255));
        editMemoryJLabel.setText("<html><u>Edit</u></html>");
        editMemoryJLabel.setToolTipText("Open Java Help");
        editMemoryJLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                editMemoryJLabelMouseReleased(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editMemoryJLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editMemoryJLabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout memoryPanelLayout = new javax.swing.GroupLayout(memoryPanel);
        memoryPanel.setLayout(memoryPanelLayout);
        memoryPanelLayout.setHorizontalGroup(
            memoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(memoryLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(editMemoryJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(363, 363, 363)
                .addComponent(memoryRecommendationLabel)
                .addContainerGap())
        );
        memoryPanelLayout.setVerticalGroup(
            memoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(memoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(memoryLabel)
                    .addComponent(memoryRecommendationLabel)
                    .addComponent(editMemoryJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javaHelpJLabel.setForeground(new java.awt.Color(0, 0, 255));
        javaHelpJLabel.setText("<html><u><i>Java setup help</i></u></html>");
        javaHelpJLabel.setToolTipText("Open Java Help");
        javaHelpJLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                javaHelpJLabelMouseReleased(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                javaHelpJLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                javaHelpJLabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout backgroundsPanelLayout = new javax.swing.GroupLayout(backgroundsPanel);
        backgroundsPanel.setLayout(backgroundsPanelLayout);
        backgroundsPanelLayout.setHorizontalGroup(
            backgroundsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(javaHomePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(memoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundsPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(javaHelpJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okButton))
                    .addComponent(bitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        backgroundsPanelLayout.setVerticalGroup(
            backgroundsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(javaHomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(javaHelpJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Change the cursor to a hand cursor.
     *
     * @param evt
     */
    private void javaHelpJLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_javaHelpJLabelMouseEntered
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_javaHelpJLabelMouseEntered

    /**
     * Change the cursor back to the default cursor.
     *
     * @param evt
     */
    private void javaHelpJLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_javaHelpJLabelMouseExited
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_javaHelpJLabelMouseExited

    /**
     * Open the JavaTroubleShooting web page.
     *
     * @param evt
     */
    private void javaHelpJLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_javaHelpJLabelMouseReleased
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        BareBonesBrowserLaunch.openURL("http://code.google.com/p/compomics-utilities/wiki/JavaTroubleShooting");
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_javaHelpJLabelMouseReleased

    /**
     * Close the dialog.
     *
     * @param evt
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * Open the JavaOptionsDialog.
     *
     * @param evt
     */
    private void editMemoryJLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMemoryJLabelMouseReleased
        new JavaOptionsDialog(frameParent, javaOptionsDialogParent, welcomeDialog, toolName);
    }//GEN-LAST:event_editMemoryJLabelMouseReleased

    /**
     * Change the cursor to a hand cursor.
     *
     * @param evt
     */
    private void editMemoryJLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMemoryJLabelMouseEntered
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_editMemoryJLabelMouseEntered

    /**
     * Change the cursor back to the default cursor.
     *
     * @param evt
     */
    private void editMemoryJLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMemoryJLabelMouseExited
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_editMemoryJLabelMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundsPanel;
    private javax.swing.JLabel bitLabel;
    private javax.swing.JPanel bitPanel;
    private javax.swing.JLabel bitRecommendationLabel;
    private javax.swing.JLabel editMemoryJLabel;
    private javax.swing.JLabel javaHelpJLabel;
    private javax.swing.JLabel javaHomeLabel;
    private javax.swing.JPanel javaHomePanel;
    private javax.swing.JLabel memoryLabel;
    private javax.swing.JPanel memoryPanel;
    private javax.swing.JLabel memoryRecommendationLabel;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}