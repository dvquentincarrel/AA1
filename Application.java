package aa1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@univ-grenoble-alpes.fr>
 */
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private final Map<String, Enfant> enfants;
    private final Map<String, Instrument> instruments;

    public Application() {
        enfants = new HashMap<>();
        instruments = new HashMap<>();
    }

    /**
     * Boucle d'interaction avec l'utilisa·teur/trice.
     *
     * La méthode {@link #run} est une méthode bloquante : tant que
     * l'utilisa·teur/trice n'a pas saisi la commande {@link Commande#QUITTER},
     * la méthode conserve le contrôle de l'exécution.
     */
    public void run() {
        Commande cmd;
        do {
            cmd = CLI.lireCommande();
//            System.out.println(cmd);
//            System.out.flush();
            switch (cmd) {
                case CREER_INSTRUMENT:
                    this.creerInstrument();
                    break;
                case CREER_ENFANT:
                    this.creerEnfant();
                    break;
                case AJOUTER_INSTRUMENT_ENFANT:
                    this.ajouterInstrumentEnfant();
                    break;
                case AFFICHER_INSCRIPTIONS_ENFANTS:
                    this.afficherInscriptionsEnfants();
                    break;
                case AFFICHER_INSCRIPTIONS_INSTRUMENTS:
                    this.afficherInscriptionsInstruments();
                    break;
                case QUITTER:
                    // rien à faire
                    break;
            }
        } while (cmd != Commande.QUITTER);
    }

    private void creerInstrument() {
        String nomInstrument = CLI.saisirNouvelInstrument(this.getInstruments().keySet());
        this.nouvelInstrument(nomInstrument);
        CLI.informerUtilisateur(
                String.join(" ", List.of("Instrument", nomInstrument, "créé.")),
                true
        );
    }

    private void creerEnfant() {
        String nomEnfant = CLI.saisirNouvelEnfant(this.getEnfants().keySet());
        this.nouvelEnfant(nomEnfant);
        CLI.informerUtilisateur(
                String.join(" ", List.of("Enfant", nomEnfant, "créé.")),
                true
        );
    }

    private void ajouterInstrumentEnfant() {

        // Récupération des enfants éligibles pour une inscription
        HashSet<String> enfantsLibres = new HashSet<String>();
        for (Enfant enfantValues : getEnfants().values()) {
            if ((enfantValues.getSeance().size() <  3) && (enfantValues.getSeance().size() < getInstruments().values().size())) {
                enfantsLibres.add(enfantValues.getNom());
            }
        }
        if (enfantsLibres.size() == 0){
            CLI.informerUtilisateur("Aucune inscription possible dans ces conditions.",false);
            return;
        }
        Enfant enfantChoisi = getEnfant(CLI.choisirEnfant(enfantsLibres));

        // Empêche d'inscrire un enfant plusieurs fois au même instrument
        HashSet<String> setNomsInstruments = new HashSet<String>(instruments.keySet()) ;
        for (Seance seance : enfantChoisi.getSeance()) {
            setNomsInstruments.remove(seance.getInstrument().getNom());
        }
        Instrument instrumentChoisi = getInstrument(CLI.choisirInstrument(setNomsInstruments));

        // Empêche d'inscrire un enfant plusieurs fois le même jour
        Jour jourChoisi;
        HashSet<Jour> joursOccupes = new HashSet<Jour>();
        for (Seance seance : enfantChoisi.getSeance()) {
            joursOccupes.add(seance.getJour());
        }
        jourChoisi = CLI.lireJour(joursOccupes);

        // Liage (c'est un vrai mot, j'ai vérifié) d'une séance à son enfant et son instrument
        Seance nouvelleSeance = new Seance(jourChoisi,enfantChoisi,instrumentChoisi);
        enfantChoisi.setSeance(nouvelleSeance);
        instrumentChoisi.setSeance(nouvelleSeance);
        CLI.informerUtilisateur("L'enfant a été inscrit à la séance renseignée.",true);
    }

    private void afficherInscriptionsEnfants() {
        CLI.afficherInscriptionsEnfants(this.getEnfants().values());
    }

    private void afficherInscriptionsInstruments() {
        CLI.afficherInscriptionsInstruments(this.getInstruments().values());
    }

    /**
     * Trouve l'instrument dont le nom est nom.
     *
     * @param nom Le nom de l'instrument recherché.
     * @return L'instrument dont le nom est nom.
     */
    public Instrument getInstrument(String nom) {
        return this.instruments.get(nom);
    }

    /**
     * Ajoute un nouvel instrument dans la liste des instruments connus.
     * <p>
     * Le nouvel instrument ne doit pas déjà exister dans l'application.
     *
     * @param nom Le nom du nouvel instrument à ajouter dans l'application.
     */
    public void nouvelInstrument(String nom) {
        assert !this.instruments.containsKey(nom);
        this.instruments.put(nom, new Instrument(nom));
    }

    /**
     * Accède au dictionnaire contenant les instruments connus.
     *
     * @return Un dictionnaire des instruments connus.
     */
    public Map<String, Instrument> getInstruments() {
        return this.instruments;
    }

    /**
     * Trouve l'enfant dont le nom est nom.
     *
     * @param nom Le nom de l'enfant recherché.
     * @return L'enfant dont le nom est nom.
     */
    public Enfant getEnfant(String nom) {
        return this.enfants.get(nom);
    }

    /**
     * Ajoute un nouvel enfant dans la liste des enfants connus.
     * <p>
     * Le nouvel enfant ne doit pas déjà exister dans l'application.
     *
     * @param nom Le nom du nouvel enfant à ajouter dans l'application.
     */
    public void nouvelEnfant(String nom) {
        assert !this.enfants.containsKey(nom);
        this.enfants.put(nom, new Enfant(nom));
    }

    /**
     * Accède au dictionnaire contenant les enfants connus.
     *
     * @return Un dictionnaire des enfants connus.
     */
    public Map<String, Enfant> getEnfants() {
        return this.enfants;
    }
}
