package sample.FXML.Visualisierung;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Spiel;
import sample.Spielsysteme.Spielsystem;
import sample.Team;

import java.util.ArrayList;
import java.util.Comparator;

public class PlatzierungsTabelle implements Visualisierung {
    private int xObenLinks;
    private int yObenLinks;
    private int zellenHoehe;
    private Spielsystem spielsystem;
    private GraphicsContext gc;
    private int xPlatz;
    private int xPlatzBreite;
    private int xTeamSpieler;
    private int xTeamSpielerBreite;
    private int xSpiele;
    private int xSpieleBreite;
    private int xBHZ;
    private int xBHZBreite =0;
    private int xSaetze;
    private int xSaetzeBreite;
    private int xPunkte;
    private int xPunkteBreite;
    private boolean endrunde;

    public PlatzierungsTabelle(int xObenLinks, int yObenLinks, int zellenHoehe,int xPlatzBreite,int xTeamSpielerBreite, int xSpieleBreite,int xSaetzeBreite,int xPunkteBreite, Spielsystem spielsystem, GraphicsContext gc, boolean endrunde) {
        this.xObenLinks = xObenLinks;
        this.yObenLinks = yObenLinks;
        this.zellenHoehe = zellenHoehe;
        this.spielsystem = spielsystem;
        this.gc = gc;
        this.xPlatz = xObenLinks;
        this.xPlatzBreite = xPlatzBreite;
        this.xTeamSpieler = xObenLinks+xPlatzBreite;
        this.xTeamSpielerBreite = xTeamSpielerBreite;
        this.xSpiele = xTeamSpieler+xTeamSpielerBreite;
        this.xSpieleBreite = xSpieleBreite;
        this.xSaetze = xSpiele+xSpieleBreite;
        this.xSaetzeBreite = xSaetzeBreite;
        this.xPunkte = xSaetze+xSaetzeBreite;
        this.xPunkteBreite = xPunkteBreite;
        this.endrunde = endrunde;
        platzierungsTabelleErstellen(endrunde);
    }

    public PlatzierungsTabelle(int xObenLinks, int yObenLinks, int zellenHoehe,int xPlatzBreite,int xTeamSpielerBreite, int xSpieleBreite, int xBHZBreite, int xSaetzeBreite,int xPunkteBreite, Spielsystem spielsystem, GraphicsContext gc,boolean endrunde) {
        this.xObenLinks = xObenLinks;
        this.yObenLinks = yObenLinks;
        this.zellenHoehe = zellenHoehe;
        this.spielsystem = spielsystem;
        this.gc = gc;
        this.xPlatz = xObenLinks;
        this.xPlatzBreite = xPlatzBreite;
        this.xTeamSpieler = xObenLinks+xPlatzBreite;
        this.xTeamSpielerBreite = xTeamSpielerBreite;
        this.xSpiele = xTeamSpieler+xTeamSpielerBreite;
        this.xSpieleBreite = xSpieleBreite;
        this.xBHZ = xSpiele+xSpieleBreite;
        this.xBHZBreite = xBHZBreite;
        this.xSaetze = xBHZ+xBHZBreite;
        this.xSaetzeBreite = xSaetzeBreite;
        this.xPunkte = xSaetze+xSaetzeBreite;
        this.xPunkteBreite = xPunkteBreite;
        this.endrunde = endrunde;
        platzierungsTabelleErstellen(endrunde);
        spielsystem.setVisualisierung(this);
    }

    private void platzierungsTabelleErstellen(boolean endrunde){
        gc.beginPath();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.beginPath();
        gc.moveTo(xPlatz, yObenLinks);
        gc.lineTo(xPlatz + xPlatzBreite, yObenLinks);
        gc.lineTo(xPlatz + xPlatzBreite,  yObenLinks + zellenHoehe);
        gc.lineTo(xPlatz, yObenLinks + zellenHoehe);
        gc.lineTo(xPlatz, yObenLinks);
        gc.fillText("#", xPlatz+5,yObenLinks+14);

        gc.moveTo(xTeamSpieler, yObenLinks);
        gc.lineTo(xTeamSpieler + xTeamSpielerBreite, yObenLinks);
        gc.lineTo(xTeamSpieler + xTeamSpielerBreite, yObenLinks + zellenHoehe);
        gc.lineTo(xTeamSpieler, yObenLinks + zellenHoehe);
        gc.fillText("Team / Spieler", xTeamSpieler + 5,yObenLinks+14);

        gc.moveTo(xSpiele, yObenLinks);
        gc.lineTo(xSpiele + xSpieleBreite, yObenLinks);
        gc.lineTo(xSpiele + xSpieleBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xSpiele , yObenLinks+zellenHoehe);
        gc.fillText("Spiele", xSpiele + 5,yObenLinks+14);
        if(xBHZ!=0) {
            gc.moveTo(xBHZ, yObenLinks);
            gc.lineTo(xBHZ + xBHZBreite, yObenLinks);
            gc.lineTo(xBHZ + xBHZBreite, yObenLinks + zellenHoehe);
            gc.lineTo(xBHZ, yObenLinks + zellenHoehe);
            gc.fillText("BHZ", xBHZ + 5, yObenLinks + 14);
        }
        gc.moveTo(xSaetze, yObenLinks);
        gc.lineTo(xSaetze +xSaetzeBreite, yObenLinks);
        gc.lineTo(xSaetze +xSaetzeBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xSaetze, yObenLinks+zellenHoehe);
        gc.fillText("Sätze", xSaetze + 5,yObenLinks+14);

        gc.moveTo(xPunkte, yObenLinks);
        gc.lineTo(xPunkte + xPunkteBreite, yObenLinks);
        gc.lineTo(xPunkte + xPunkteBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xPunkte, yObenLinks+zellenHoehe);
        gc.fillText("Punkte", xPunkte + 5,yObenLinks+14);

        gc.stroke();
        gc.closePath();



        ArrayList<Team> platzierungsliste = spielsystem.getSetzliste();
        for (int i=platzierungsliste.size()-1; i>=0;i--){
            Team team = platzierungsliste.get(i);
            if(team.isFreilos()){
                platzierungsliste.remove(team);
            }
        }
        platzierungsliste.sort(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                return o1.compareTo(o2);
            }
        });
        for (int i=0;i<platzierungsliste.size();i++){
            teamZeileErstellen(yObenLinks+(i+1)*zellenHoehe,platzierungsliste.get(i), i+1, endrunde);
        }


    }
    private void teamZeileErstellen(int yObenLinks,Team team, int platzierung, boolean endrunde)
    {
        int gespielteSpiele = team.getBisherigeGegner().size();
        int gewonneneSpiele = team.getGewonneneSpiele();
        int gewonnenePunkte = team.getGewonnnenePunkte();
        int verlorenePunkte = team.getVerlorenePunkte();
        int gewonneneSaetze = team.getGewonneneSaetze();
        int verloreneSaetze = team.getVerloreneSaetze();
        int bhz = team.getBHZ();
        if(endrunde){
            for(int i=0;i<team.getGespielteSpiele().size();i++){
                Spiel spiel = team.getGespielteSpiele().get(i);

            }
        }

        gc.beginPath();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        gc.moveTo(xPlatz + xPlatzBreite, yObenLinks);
        gc.lineTo(xPlatz + xPlatzBreite,  yObenLinks + zellenHoehe);
        gc.lineTo(xPlatz, yObenLinks + zellenHoehe);
        gc.lineTo(xPlatz, yObenLinks);
        gc.fillText(platzierung+".", xPlatz+5,yObenLinks+14);

        gc.moveTo(xTeamSpieler + xTeamSpielerBreite, yObenLinks);
        gc.lineTo(xTeamSpieler + xTeamSpielerBreite, yObenLinks + zellenHoehe);
        gc.lineTo(xTeamSpieler, yObenLinks + zellenHoehe);
        gc.fillText(team.toString(), xTeamSpieler + 5,yObenLinks+14);

        gc.moveTo(xSpiele + xSpieleBreite, yObenLinks);
        gc.lineTo(xSpiele + xSpieleBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xSpiele , yObenLinks+zellenHoehe);
        gc.fillText(gewonneneSpiele+":"+(gespielteSpiele-gewonneneSpiele), xSpiele+ 5,yObenLinks+14);
        if(xBHZ!=0) {
            gc.moveTo(xBHZ + xBHZBreite, yObenLinks);
            gc.lineTo(xBHZ + xBHZBreite, yObenLinks + zellenHoehe);
            gc.lineTo(xBHZ, yObenLinks + zellenHoehe);
            gc.fillText(bhz + "", xBHZ + 5, yObenLinks + 14);
        }
        gc.moveTo(xSaetze +xSaetzeBreite, yObenLinks);
        gc.lineTo(xSaetze +xSaetzeBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xSaetze, yObenLinks+zellenHoehe);
        gc.fillText(gewonneneSaetze+":"+verloreneSaetze, xSaetze + 5,yObenLinks+14);


        gc.moveTo(xPunkte + xPunkteBreite, yObenLinks);
        gc.lineTo(xPunkte + xPunkteBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xPunkte, yObenLinks+zellenHoehe);
        gc.fillText(gewonnenePunkte+":"+verlorenePunkte, xPunkte + 5,yObenLinks+14);

        gc.stroke();
        gc.closePath();
    }

    @Override
    public void update() {
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        platzierungsTabelleErstellen(endrunde);
    }

    @Override
    public void drucken() {

    }
}
