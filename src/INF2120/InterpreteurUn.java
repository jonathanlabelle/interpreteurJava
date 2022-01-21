

package INF2120;

import java.util.ArrayList;

public class InterpreteurUn implements ContexteInterpretation {

    private enum MODES {DClasse, DAttribut, DMethode, DParametre, FClasse, FMethode}
    MODES mode = MODES.FClasse;

    boolean estAbstrait = false;
    int nbrOuverture = 0;


    //faire une for i intepreteur dans programme
    protected void lancerInterpreteurUn(ArrayList<String> listeDeCommande) {
        for (String commandeEnString : listeDeCommande) {
            Commande commande = Programme.interpreteCommande(commandeEnString);
            if (commande instanceof Abstrait) {
                genAbstrait(new Abstrait());
            };
            if (commande instanceof ClasseDebut) {
                genDebutClasse(new ClasseDebut());
            }
            if (commande instanceof ClasseFin) {
                genFinClasse(new ClasseFin());
            }
            if (commande instanceof Attribut) {
                genAttribut(new Attribut());
            }
            if (commande instanceof MethodeDebut) {
                genDebutMethode(new MethodeDebut());
            }
            if (commande instanceof Parametre) {
                genParametre(new Parametre());
            }
            if (commande instanceof MethodeFin) {
                genFinMethode(new MethodeFin());
            }
        }

        verificationFinInterpreteur();

    }

    private void verificationFinInterpreteur() {

        if (mode != MODES.FClasse) {
            System.err.println(Messages.ERREUR_FERMETURE_CLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }
        if (estAbstrait) {
            System.err.println(Messages.ERREUR_FERMETURE_ABSTRAIT + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }
        if (nbrOuverture != 0) {
            System.err.println(Messages.ERREUR_FERMETURE_OUVERTURE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genAbstrait(Abstrait motAbstrait) {

        if (estAbstrait) {
            System.err.println(Messages.ERREUR_CLASSE_ABSTRAITE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

        estAbstrait = true;
    }


    @Override
    public void genDebutClasse(ClasseDebut motDebutClasse) {

        if (mode == MODES.DClasse || mode == MODES.FClasse ||
                mode == MODES.DAttribut || mode == MODES.FMethode) {
            nbrOuverture++;
            mode = MODES.DClasse;
            estAbstrait = false;
        }
        else {
            System.err.println(Messages.ERREUR_CLASSE_DEBUT + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genFinClasse(ClasseFin motFinClasse) {

        if ((mode == MODES.DClasse || mode == MODES.FClasse ||
                mode == MODES.DAttribut || mode == MODES.FMethode)
                && (!estAbstrait && nbrOuverture > 0)) {
            nbrOuverture --;
            mode = MODES.FClasse;
        }
        else {
            System.err.println(Messages.ERREUR_FERMETURE_CLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genAttribut(Attribut motAttribut) {

        if ((mode == MODES.DClasse || mode == MODES.DAttribut) & (!estAbstrait)) {
            mode = MODES.DAttribut;
        }
        else {
            System.err.println(Messages.ERREUR_CREATION_ATTRIBUT + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genDebutMethode(MethodeDebut motDebutMethode) {

        if (mode == MODES.DClasse || mode == MODES.DAttribut || mode == MODES.FMethode) {
            mode = MODES.DMethode;
            estAbstrait = false;
        }
        else {
            System.err.println(Messages.ERREUR_CREATION_METHODE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }
    }


    @Override
    public void genParametre(Parametre motParametre) {

        if ((mode == MODES.DMethode || mode == MODES.DParametre) && (!estAbstrait)) {
            mode = MODES.DParametre;
        }
        else {
            System.err.println(Messages.ERREUR_ATTRIBUTION_PARAMETRE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genFinMethode(MethodeFin motFinMethode) {

        if ((mode == MODES.DMethode || mode == MODES.DParametre) && (!estAbstrait)) {
            mode = MODES.FMethode;
        }
        else {
            System.err.println(Messages.ERREUR_FERMETURE_METHODE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }
}
