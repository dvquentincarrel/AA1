package aa1;

import java.io.Serializable;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@univ-grenoble-alpes.fr>
 */
public class Seance implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private Jour jour;
    private Enfant enfant;
    private Instrument instrument;

    public Seance(Jour jour, Enfant enfant, Instrument instrument) {
        this.jour = jour;
        this.enfant = enfant;
        this.instrument = instrument;
    }

    public Jour getJour(){
        return jour;
    }

    public Enfant getEnfant(){
        return enfant;
    }

    public Instrument getInstrument(){
        return instrument;
    }
}
