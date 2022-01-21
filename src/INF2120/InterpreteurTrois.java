package INF2120;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class InterpreteurTrois implements ContexteInterpretation {

    private int nbrClasse = 0;
    private Stack<InterpreteursTroisEtats> pileEtat = new Stack<>();
    private boolean estAbstrait = false;
    private boolean estPremierParametre = false;
    File fichier = new File("uml.tex");
    protected ArrayList<String> listeIdentificateurs = null;

    //ERREURS MESSAGES

    public InterpreteurTrois() {
        fichier.delete();
    }

    protected void lancerInterpreteurTrois(ArrayList<String> listeDeCommande) {

        try {

            FileWriter ecrireFW = new FileWriter("uml.tex", true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);

            ecrire.write(DescriptionLatex.FICHIER_DEBUT);

            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

        pileEtat.push(new InterpreteursTroisEtats());

        for (String commandeEnString : listeDeCommande) {
                Commande commande = Programme.interpreteCommande(commandeEnString);
                listeIdentificateurs = Programme.separationDesIdentificateurs(commandeEnString);
                if (commande instanceof Abstrait) {
                    genAbstrait(new Abstrait());
                }
                ;
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

        try {

            FileWriter ecrireFW = new FileWriter("uml.tex", true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);

            ecrire.write(DescriptionLatex.FICHIER_FIN);

            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }


        }

    @Override
    public void genAbstrait(Abstrait motAbstrait) { estAbstrait = true; }

    @Override
    public void genDebutClasse(ClasseDebut motDebutClasse) {
        //if else if
        try {

            FileWriter ecrireFW = new FileWriter("uml.tex", true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);;

            if (nbrClasse == 0) {
                ecrire.write(DescriptionLatex.PAGE_DEBUT);
            } else {

                if (pileEtat.peek().premiereClasse) {
                    ecrire.write(DescriptionLatex.CLASSE_FIN);
                    ecrire.write(DescriptionLatex.LISTE_CLASSE_DEBUT);
                    pileEtat.peek().premiereClasse = false;
                }
                ecrire.write(DescriptionLatex.CLASSE_INTERNE_PREFIX);
            }


            pileEtat.push(new InterpreteursTroisEtats());
            ecrire.write(DescriptionLatex.CLASSE_DEBUT);

            if (estAbstrait) {ecrire.write(DescriptionLatex.ABSTRAIT_DEBUT);}
            ecrire.write(listeIdentificateurs.get(0));

            if (estAbstrait) {
                ecrire.write(DescriptionLatex.ABSTRAIT_FIN);
                estAbstrait = false;
            }

            nbrClasse ++;
            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genFinClasse(ClasseFin motFinClasse) {
        try {

            FileWriter ecrireFW = new FileWriter("uml.tex", true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);

            nbrClasse--;
            ecrire.write((pileEtat.peek().premiereClasse) ? DescriptionLatex.CLASSE_FIN : DescriptionLatex.LISTE_CLASSE_FIN);
            ecrire.write((nbrClasse == 0) ? DescriptionLatex.PAGE_FIN : DescriptionLatex.CLASSE_INTERNE_SUFFIX);

            try {
                pileEtat.pop();
            } catch (EmptyStackException e) {
                System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
                System.exit(-1);
            }

            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }
    }


    @Override
    public void genAttribut(Attribut motAttribut) {

        try {

            FileWriter ecrireFW = new FileWriter("uml.tex", true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);

            if(pileEtat.peek().premierAttribut) {
                ecrire.write(DescriptionLatex.LISTE_ATTRIBUT_DEBUT);
                pileEtat.peek().premierAttribut = false;
            } else {
                ecrire.write(DescriptionLatex.LISTE_ATTRIBUT_SEP);
            }
            ecrire.write(listeIdentificateurs.get(1) + ':' + listeIdentificateurs.get(0));
            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }
    }

    @Override
    public void genDebutMethode(MethodeDebut motDebutMethode) {

        try {

            FileWriter ecrireFW = new FileWriter("uml.tex", true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);

            if (pileEtat.peek().premiereMethode) {
                ecrire.write(DescriptionLatex.LISTE_METHODE_DEBUT);
                pileEtat.peek().premiereMethode = false;
            } else {
                ecrire.write(DescriptionLatex.LISTE_METHODE_SEP);
            }

            if (estAbstrait) {ecrire.write(DescriptionLatex.ABSTRAIT_DEBUT);}
            if (!listeIdentificateurs.get(0).contains("void")) { ecrire.write(listeIdentificateurs.get(0) + " "); }
            ecrire.write(listeIdentificateurs.get(1));
            ecrire.write(DescriptionLatex.PARAMETRE_DEBUT);
            estPremierParametre = true;
            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genParametre(Parametre motParametre) {

        try {

            FileWriter ecrireFW = new FileWriter("uml.tex", true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);

            if (estPremierParametre) {
                estPremierParametre = false;
            } else {
                ecrire.write(DescriptionLatex.PARAMETRE_SEP);
            }

            ecrire.write(listeIdentificateurs.get(0));
            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genFinMethode(MethodeFin motFinMethode) {

        try {

            FileWriter ecrireFW = new FileWriter("uml.tex", true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);

            ecrire.write(DescriptionLatex.PARAMETRE_FIN);
            if (estAbstrait) {
                ecrire.write(DescriptionLatex.ABSTRAIT_FIN);
                estAbstrait = false;
            }

            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }
    }
}
