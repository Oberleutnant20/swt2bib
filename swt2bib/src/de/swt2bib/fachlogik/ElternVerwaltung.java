package de.swt2bib.fachlogik;

import de.swt2bib.Logger;
import de.swt2bib.ui.ElternPanel;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public abstract class ElternVerwaltung {
    
    public ArrayList<ElternPanel> panelListe = new  ArrayList<ElternPanel>();
    
    public void addPanelList(ElternPanel e) {
        panelListe.add(e);
    }
    
    public void notifyPanels() {
        Logger.debug(this,"notify");
        panelListe.stream().forEach(p -> p.update());
    }