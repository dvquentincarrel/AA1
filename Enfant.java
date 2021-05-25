package aa1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@univ-grenoble-alpes.fr>
 */
public class Enfant implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private final String nom;
    private HashMap<Seance,Jour> seances = new HashMap<Seance,Jour>();

    public Enfant(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public Collection<Seance> getSeance() {
        return seances.keySet();
    }

    public void setSeance(Seance seance) {
        // Plutôt addSeance que setSeance, mais on se comprend.
        seances.put(seance,seance.getJour());      
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
