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
    //private ArrayList<Seance> seances = new ArrayList<Seance>(); // A DELETE

    public Enfant(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public Collection<Seance> getSeance() { //A REVOIR
        return seances.keySet();
    }

    public void setSeance(Seance seance) {
    //Maximum une séance par jour et 3 séances au total
        if (seances.size() == 3){
            System.out.println("Cet enfant est déjà inscrit à 3 séances"); //A REVOIR
            return;
        }
        else if (!seances.values().contains(seance.getJour())) {
            seances.put(seance,seance.getJour());
        }
        else {
            System.out.println("Cet enfant est déjà inscrit un " + seance.getJour());
        }

    }

    @Override
    public String toString() {
        return this.nom;
    }
}
