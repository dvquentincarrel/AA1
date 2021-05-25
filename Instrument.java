package aa1;

import java.io.Serializable;
import java.util.HashSet;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@univ-grenoble-alpes.fr>
 */
public class Instrument implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private final String nom;
    private HashSet<Seance> seances = new HashSet<Seance>();

    public Instrument(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public HashSet<Seance> getSeance() {
        return seances;
    }

    public void setSeance(Seance seance) {
        // Oui bon, plutôt addSeance que setSeance, mais on se comprend.
        seances.add(seance);
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
