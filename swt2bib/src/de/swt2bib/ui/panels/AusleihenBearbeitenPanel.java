package de.swt2bib.ui.panels;

import de.swt2bib.datenlogik.dto.Ausleihe;
import de.swt2bib.ui.ElternPanel;
import de.swt2bib.ui.PanelHandler;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class AusleihenBearbeitenPanel extends ElternPanel {

    ArrayList<Ausleihe> ausleiheListe;

    /**
     * Creates new form AusleihenBearbeitenPanel
     */
    public AusleihenBearbeitenPanel(PanelHandler panelHandler) {
        super(panelHandler);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        entfernenButton = new javax.swing.JButton();
        sucheField = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AusleiheID", "MedienID", "Date", "UserID", "KategorieIDl"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        entfernenButton.setText("Entfernen");
        entfernenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entfernenButtonActionPerformed(evt);
            }
        });

        sucheField.setText("Titelsuchen...");
        sucheField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucheFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sucheField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entfernenButton)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sucheField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(entfernenButton)
                .addContainerGap(59, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void entfernenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entfernenButtonActionPerformed
        try {
            panelHandler.deleteAusleihe(getAusleihefromIndices(getListSelections()));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_entfernenButtonActionPerformed

    private void sucheFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucheFieldActionPerformed
        panelHandler.panelUnsichtbar();
        panelHandler.getSuchePanel().setSearchTitel(sucheField.getText());
        panelHandler.getSuchePanel().setVisible(true);
    }//GEN-LAST:event_sucheFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton entfernenButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField sucheField;
    // End of variables declaration//GEN-END:variables

    public void setAusleihenListe(ArrayList<Ausleihe> ausleihe) {
        ausleiheListe = ausleihe;
    }

    private Ausleihe getAusleihefromIndices(int position) {
        Ausleihe selected = null;
        selected = ausleiheListe.get(position);
        return selected;
    }

    private int getListSelections() {
        int[] selected = jTable1.getSelectedRows();
        for (int i = 0; i < selected.length; i++) {
            selected[i] = jTable1.convertRowIndexToModel(selected[i]);
        }
        return selected[0];
    }

    public void fillTable() {
        panelHandler.loadAusleihen();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = model.getRowCount() - 1; i > -1; i--) {
            model.removeRow(i);
        }
        for (int i = 0; i < ausleiheListe.size(); i++) {
            model.addRow(addObject(i));
        }
    }

    private Object[] addObject(int i) {
        String medienName = "";
        for (int j = 0; j < panelHandler.returnMedien().size(); j++) {
            if (ausleiheListe.get(i).getMedienid() == panelHandler.returnMedien().get(j).getId()) {
                medienName = panelHandler.returnMedien().get(j).getName();
            }
        }
        String kategorieName = "";
        for (int j = 0; j < panelHandler.getKategorieListe().size(); j++) {
            if (ausleiheListe.get(i).getKategorieid() == panelHandler.getKategorieListe().get(j).getId()) {
                medienName = panelHandler.getKategorieListe().get(j).getName();
            }
        }
        return new Object[]{ausleiheListe.get(i).getId(), medienName, ausleiheListe.get(i).getDate(), panelHandler.getAktuellerUser().getUsername(), kategorieName};
    }

    @Override
    public void update() {
        fillTable();
    }
}
