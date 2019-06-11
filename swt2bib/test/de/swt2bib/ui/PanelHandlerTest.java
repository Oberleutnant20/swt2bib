package de.swt2bib.ui;

import de.swt2bib.fachlogik.*;
import de.swt2bib.datenlogik.dto.Genre;
import de.swt2bib.datenlogik.dto.History;
import de.swt2bib.datenlogik.dto.Kategorie;
import de.swt2bib.datenlogik.dto.Medien;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author root
 */
public class PanelHandlerTest {

    @Mock
    List<Kategorie> kategorieListe;
    @Mock
    Controller controller;
    @Mock
    List<Genre> genreListe;
    PanelHandler sut;

    public PanelHandlerTest() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new PanelHandler(controller, genreListe, kategorieListe);
    }

    @Test
    public void testMapHistoryAndMediumTrue() {
        //GIVEN
        Medien medium = new Medien("isbn", 0, 0, 0, "name", 0, 0, "author", "desc");
        History history = new History(0, 0, 0, 0);
        ArrayList<Medien> liste = new ArrayList<>();
        liste.add(medium);
        //WHEN
        Mockito.when(controller.getAllMedien()).thenReturn(liste);
        //THEN
        assertTrue(medium == sut.mapHistoryAndMedium(history));
    }

    @Test
    public void testMapHistoryAndMediumFalse() {
        //GIVEN
        Medien medium = new Medien("isbn", 1, 1, 1, "name", 1, 1, "author", "desc");
        History history = new History(0, 0, 0, 0);
        ArrayList<Medien> liste = new ArrayList<>();
        liste.add(medium);
        //WHEN
        Mockito.when(controller.getAllMedien()).thenReturn(liste);
        //THEN
        assertFalse(medium == sut.mapHistoryAndMedium(history));
    }
}
