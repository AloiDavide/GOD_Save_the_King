import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public enum Scene {

    PROLOGUE_1("res/backgrounds/castle.gif", List.of(
            new Line("Re Edward","Re Edward: Buongiorno a tutti, oggi è il mio compleanno e sono molto felice!"),
            new Line("Soldato 1", "Auguri di buon compleanno, Vostra Maestà!"),
            new Line("Soldato 2", "Sì, auguri di buon compleanno, Maestà!"),
            new Line("Re Edward", "Grazie a entrambi. Guardate cosa abbiamo qui, un regalo da parte di Re Helmered!"),
            new Line("Soldato 1", "Oh, che bello!"),
            new Line("Re Edward", "Sì, sembra una bottiglia di vino. Lo apriremo per brindare al mio compleanno!"),
            new Line("Soldato 2", "Maestà, forse dovreste fare attenzione. Non sappiamo se il vino sia stato avvelenato."),
            new Line("Re Edward", "Non essere sciocco, Soldato. Re Helmered è un nostro alleato,\n perché dovrebbe mandarci un vino avvelenato?"),
            new Line("Soldato 1", "Ha ragione, Maestà. Non c'è motivo di preoccuparsi."),
            new Line("Re Edward", "Esatto. Adesso apri la bottiglia e brindiamo al mio compleanno!"),
            new Line("Re Edward", "Ah, questo vino è delizioso. Grazie ancora a Re Helmered per il regalo."),
            new Line("Narratore","Poco dopo, Re Edward inizia a sentirsi male e \nviene scoperto che il vino era stato avvelenato"),
            new Line("Soldato 2", "Maestà, state bene? Avevate ragione, il vino era stato avvelenato!"),
            new Line("Re Edward", "(debolmente) Chiamate un medico, presto!"),
            new Line ("Soldato 1", "Subito, Maestà! (chiama il medico)"),
            new Line("Soldato 3", "Maestà, ho trovato questo biglietto accanto alla bottiglia di vino.\nIl mittente è Re Helmred e ci dà un ultimatum" ),
            new Line("Soldato 3",  "dobbiamo consegnare la corona del regno di Viserys entro sette giorni, \naltrimenti verremo uccisi."),
            new Line("Re Edward", "(con sorpresa e indignazione) Cosa? Re Helmered ci ha traditi? Non posso crederci!"),
            new Line("Soldato 2", "È difficile credere che un alleato possa fare una cosa del genere, Maestà."),
            new Line("Re Edward", "Sì, avete ragione. Dobbiamo proteggere il nostro regno a ogni costo."),
            new Line("Re Edward", "Inizieremo subito a lavorare su un piano per proteggere la corona \ne affrontare Re Helmred per questo atto malvagio."),
            new Line("Soldato 1", "Sì, Maestà. Faremo tutto il possibile per proteggere il regno.")
    )){
        public void endScene(){
            Game.getGameInstance().dialogue = new Dialogue(Scene.PROLOGUE_2, Game.getGameInstance());
        }
    },
    PROLOGUE_2 ("res/backgrounds/forest.gif", List.of(
            new Line("Garlan", "COSA?! Mio padre ha bevuto del vino avvelenato?"),
            new Line("Olivia",  "Orribile, chi farebbe una cosa del genere?"),
            new Line("Garlan",  "Gli ho sempre detto di non fidarsi di Helmred.\nSapevo che un giorno ci avrebbe pugnalati alle spalle!"),
            new Line("Drurd", "Tutti i principali generali dell'esercito erano al ballo,\nin una sola mossa ha messo fuori uso l'intera milizia del nostro paese."),
            new Line("Olivia",  "Se Garlan non odiasse le formalità saremmo rimasti vittime anche noi."),
            new Line("Garlan",  "Se così stanno le cose, non abbiamo altra scelta."),
            new Line("Drurd",  "Hey, non starai pensando..."),
            new Line("Olivia",  "Mi sa proprio che lo sta pensando."),
            new Line("Garlan",  "Andremo noi tre a recuperare quell'antidoto personalmente!\nE non ci faremo fermare da niente e nessuno!"),
            new Line("Drurd",  "Tu e i tuoi piani spericolati."),
            new Line("Olivia",  "Ma Garlan ha ragione, non possiamo dargliela vinta così.\nConta su di me e le mie frecce."),
            new Line("Garlan",  "Noi andiamo Drurd, tu che fai?"),
            new Line("Drurd",  "Ho capito, tanto senza la mia magia di cura non ne uscireste mai vivi.\nAlmeno promettimi che non farai irruzione dal cancello principale.\n...Hey mi stai ascoltando?")
            )){
        public void endScene(){
            Game.getGameInstance().refreshTurnOrder();
            Game.getGameInstance().setGameState(Game.State.MOVING);
            turnChange = true;
        }
    },

    EPILOGUE ("res/backgrounds/castle.gif", List.of(
            new Line("Addio", "Grazie per aver giocato la beta di \"GOD Save The King\""),
            new Line("Addio", "Il gioco è terminato per adesso, grazie per l'attenzione.")
    )){
        public void endScene(){
            System.exit(0);
        }
    };



    private List<Line> script;
    private BufferedImage bg;
    public boolean turnChange = false;

    Scene(String bgPath, List<Line> script){
        if (bgPath == null) this.bg = null;
        else{
            try {
                this.bg = ImageIO.read(getClass().getResourceAsStream(bgPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.script = script;
    }

    public List<Line> getScript(){return script;}
    public BufferedImage getBg(){return bg;}

    abstract void endScene();
}
