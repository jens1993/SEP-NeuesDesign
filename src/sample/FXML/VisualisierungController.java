package sample.FXML;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.DAO.auswahlklasse;
import sample.FXML.Visualisierung.GruppenTabelle;
import sample.FXML.Visualisierung.PlatzierungsTabelle;
import sample.FXML.Visualisierung.Turnierbaum;
import sample.Spielklasse;
import sample.Spielsysteme.GruppeMitEndrunde;
import sample.Spielsysteme.KO;
import sample.Spielsysteme.Spielsystem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Manuel Hüttermann on 20.09.2017.
 */
public class VisualisierungController implements Initializable {
    @FXML
    JFXTabPane tabPane_spielklassen;

    public void SpracheLaden()
    {

    }

    public void klassenTabsErstellen() {
        tabPane_spielklassen.getTabs().clear();
        for(int i=0;i<auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size();i++){
            Spielklasse spielklasse = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(i);
            if(spielklasse.getSpielsystem()!=null) {
                Tab tab = new Tab(spielklasse.toString());
                Text text = new Text(spielklasse.toString());
                Font font = new Font("Arial",28);
                text.setFont(font);
                double textBreite = text.getLayoutBounds().getWidth();
                if((tabPane_spielklassen.tabMinWidthProperty().get())<textBreite){
                    tabPane_spielklassen.tabMinWidthProperty().set(textBreite);
                }
                tab.setClosable(false);
                tabPane_spielklassen.getTabs().add(tab);
                klassenVisualisierung(spielklasse.getSpielsystem(),tab);
                auswahlklasse.getDashboardController().turnierbaumAktivieren();
            }
        }
    }

    private void klassenVisualisierung(Spielsystem spielsystem, Tab tab) {
        if (spielsystem.getSpielSystemArt()==1){
            gruppeVisualisierung(spielsystem, tab, false);
        }
        else if(spielsystem.getSpielSystemArt()==2){
            gruppeMitEndrundeVisualisierung(spielsystem, tab);
        }
        else if (spielsystem.getSpielSystemArt()==3){
            koVisualisierung(spielsystem, tab);
        }
        else if(spielsystem.getSpielSystemArt()==5){
            schweizerVisualisierung(spielsystem, tab);
        }

    }

    private void schweizerVisualisierung(Spielsystem spielsystem, Tab tab) {
        ScrollPane scrollPane = new ScrollPane();
        tab.setContent(scrollPane);
        int zellenHoehe = 20;
        Canvas canvas = new Canvas(600,spielsystem.getSetzliste().size()*zellenHoehe+100);
        scrollPane.setContent(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PlatzierungsTabelle platzierungsTabelle = new PlatzierungsTabelle(20,20,zellenHoehe,25,200,45,30,45,45, spielsystem,gc,false);
    }

    private void gruppeVisualisierung(Spielsystem spielsystem, Tab tab, boolean endrunde) {
        GruppenTabelle gruppenTabelle = new GruppenTabelle(spielsystem, tab);
        if(spielsystem.getSetzliste()!=null && spielsystem.getSetzliste().size()>0) {
            gruppenTabelle.erstelleGruppenTabelle(endrunde);
        }
    }

    private void gruppeMitEndrundeVisualisierung(Spielsystem spielsystem, Tab tab) {
        GruppeMitEndrunde gruppeMitEndrunde = (GruppeMitEndrunde) spielsystem;
        TabPane tabPane = new TabPane();
        tab.setContent(tabPane);
        for(int i = 0;i<gruppeMitEndrunde.getAlleGruppen().size();i++){
            Text text = new Text("Gruppe "+(i+1));
            Font font = new Font("Arial",40);
            text.setFont(font);
            double textBreite = text.getLayoutBounds().getWidth();
            if((tabPane.tabMinWidthProperty().get())<textBreite){
                tabPane.tabMinWidthProperty().set(textBreite);
            }
            Tab gruppe = new Tab("Gruppe "+(i+1));
            gruppe.setClosable(false);
            tabPane.getTabs().add(gruppe);
            gruppeVisualisierung(gruppeMitEndrunde.getAlleGruppen().get(i),gruppe, false);
        }
        if(gruppeMitEndrunde.getEndrunde() instanceof KO) {
            Tab endrunde = new Tab("Endrunde");
            endrunde.setClosable(false);
            tabPane.getTabs().add(endrunde);
            koVisualisierung(gruppeMitEndrunde.getEndrunde(), endrunde);
        }
        else {
            Tab endrunde = new Tab("Endrunde");
            endrunde.setClosable(false);
            tabPane.getTabs().add(endrunde);
            gruppeVisualisierung(gruppeMitEndrunde.getEndrunde(),endrunde, true);
        }
    }

    private void koVisualisierung(Spielsystem spielsystem, Tab tab) {
        if (spielsystem!=null) {
            Turnierbaum turnierbaum = new Turnierbaum(20, 20, 180, 50, 100, 20, tab);

            turnierbaum.erstelleTurnierbaum(spielsystem);
            //vBox.setStyle("-fx-background-color: #d8d8d8");
        }
    }

    public void zoomIn(){
        ScrollPane aktuellesScrollPane = (ScrollPane) tabPane_spielklassen.getSelectionModel().getSelectedItem().getContent();
        aktuellesScrollPane.setScaleX(2);
        aktuellesScrollPane.setScaleY(2);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auswahlklasse.setVisualisierungController(this);
        klassenTabsErstellen();

    }
}
