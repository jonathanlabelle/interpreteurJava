/**
 *
 * @author Jonathan Labelle
 * @codePermanent LABJ28039006
 * @Date 07/04/2021
 */

package INF2120;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

public class InterpreteurDeux implements ContexteInterpretation{

    protected Stack <File> pileFichier = new Stack<>();
    protected Stack <String> pileNom = new Stack<>();
    protected boolean estAbstrait = false;
    protected boolean estPremierParametre = false;
    protected ArrayList<String> listeIdentificateurs = null;


    protected void lancerInterpreteurDeux (ArrayList<String> listeDeCommande) {

        for (String commandeEnString: listeDeCommande) {
            Commande commande = Programme.interpreteCommande(commandeEnString);
            listeIdentificateurs = Programme.separationDesIdentificateurs(commandeEnString);

            if (commande instanceof ClasseDebut) {
                genDebutClasse(new ClasseDebut());
            }
            if (commande instanceof ClasseFin) {
                genFinClasse(new ClasseFin());
            }
            if (commande instanceof Attribut) {
                genAttribut(new Attribut());
            }
            if (commande instanceof Parametre) {
                genParametre(new Parametre());
            }
            if (commande instanceof MethodeDebut) {
                genDebutMethode(new MethodeDebut());
            }
            if (commande instanceof MethodeFin) {
                genFinMethode(new MethodeFin());
            }
        }

    }

    @Override
    public void genAbstrait(Abstrait motAbstrait) { estAbstrait = true; }

    @Override
    public void genDebutClasse(ClasseDebut motDebutClasse) {

        try (FileWriter ecrireFW = new FileWriter(listeIdentificateurs.get(0) + ".java");
             BufferedWriter ecrire = new BufferedWriter(ecrireFW);) {

            ecrire.write("public ");
            File fichier = new File(listeIdentificateurs.get(0) + ".java");
            pileFichier.push(fichier);
            System.out.println(pileFichier);
            pileNom.push(listeIdentificateurs.get(0));

            if (estAbstrait) {
                ecrire.write("abstract ");
            }

            ecrire.write("classe " + listeIdentificateurs.get(0) + " ");

            if (pileNom.size() > 1) {
                ecrire.write("extends " + pileNom.get(0) + " ");
            }

            ecrire.write("{\n\n");
            estAbstrait = false;

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

        @Override
    public void genFinClasse(ClasseFin motFinClasse) {

        try {

            FileWriter ecrireFW = new FileWriter(pileFichier.lastElement(), true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);;

            ecrire.append("\n}");
            pileNom.pop();
            pileFichier.pop();
            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_FCLASSE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genAttribut(Attribut motAttribut) {

        try {

            FileWriter ecrireFW = new FileWriter(pileFichier.lastElement(), true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);;

            ecrire.append("private " + listeIdentificateurs.get(0) + " " + listeIdentificateurs.get(1) + " ;\n");
            ecrire.append("public " + listeIdentificateurs.get(0) + " get" + listeIdentificateurs.get(1) +"() {\n");
            ecrire.append("    return " + listeIdentificateurs.get(1) + " ;\n");
            ecrire.append("}\n");
            ecrire.append("public void set" + listeIdentificateurs.get(0) + "( " + listeIdentificateurs.get(0) + " "  + listeIdentificateurs.get(1) + " ) {\n");
            ecrire.append("    this." + listeIdentificateurs.get(1) + " = " + listeIdentificateurs.get(1) + " ;\n");
            ecrire.append("}\n");
            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_ATTRIBUT + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

    @Override
    public void genDebutMethode(MethodeDebut motDebutMethode) {
        try {

            FileWriter ecrireFW = new FileWriter(pileFichier.lastElement(), true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);;

            ecrire.append("public ");
            if (estAbstrait) {
                ecrire.append("abstract ");
            }
            ecrire.append(listeIdentificateurs.get(0) + " " + listeIdentificateurs.get(1) + " (");
            estPremierParametre = true;

            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_DMETHODE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }
    }

    @Override
    public void genParametre(Parametre motParametre) {
        try {

            FileWriter ecrireFW = new FileWriter(pileFichier.lastElement(), true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);;

            if (!estPremierParametre) {
                ecrire.append(" , ");
            }
            ecrire.append(listeIdentificateurs.get(0) + " " + listeIdentificateurs.get(1));
            estPremierParametre = false;

            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_PARAMETRE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }
    }

    @Override
    public void genFinMethode(MethodeFin motFinMethode) {
        try {

            FileWriter ecrireFW = new FileWriter(pileFichier.lastElement(), true);
            BufferedWriter ecrire = new BufferedWriter(ecrireFW);;

            ecrire.append(")");
            if (estAbstrait) {
                ecrire.append(" ; \n");
            } else {
                ecrire.append(" {\n} ");
            }
            estAbstrait = false;

            ecrire.close();

        } catch (IOException e) {
            System.err.println(Messages.ERREUR_ECRITURE_FMETHODE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }
    }
}
