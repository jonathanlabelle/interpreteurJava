/**
 * Ce logiciel permet d'interpreter, grace a trois interpreteurs differents,
 * un programme ecrit dans un fichier texte. Le texte literal sera transforme
 * en langage java et un uml.
 *
 * @author Jonathan Labelle
 * @codePermanent LABJ28039006
 * @Date 07/04/2021
 */

package INF2120;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Principal {


    public static void main(String[] args) {

        InterpreteurUn interpreteurUn = new InterpreteurUn();
        InterpreteurDeux interpreteurDeux = new InterpreteurDeux();
        InterpreteurTrois interpreteurTrois = new InterpreteurTrois();

        String nomFichier = demanderNomFichier();
        String fichierAInterpreter = fichierEnString(nomFichier);
        Programme.creationListeDeCommandesString(fichierAInterpreter);

        interpreteurUn.lancerInterpreteurUn(Programme.getListeDeCommande());
        interpreteurDeux.lancerInterpreteurDeux(Programme.getListeDeCommande());
        interpreteurTrois.lancerInterpreteurTrois(Programme.getListeDeCommande());

    }


    /**
     * Sollicite le nom du fichier a l'utilisateur
     * Utilisation de la Classe Scanner pour sollicitaiton.
     * @return Sle nom du fichier a ouvrir
     */
    public static String demanderNomFichier() {

        Scanner scan = new Scanner(System.in);
        System.out.println(Messages.SOLLICITATION_NOM_FICHIER );
        String nomFichier = scan.nextLine();

        return nomFichier;

    }

    /**
     * Creation d'un String a partir du fichier
     *
     * Utilisation d'un stream pour creer le fichier ligne par ligne.
     * Si le fichier fourni n'existe pas, lance une erreur et ferme le programme
     * Si le fichier est valide et que le String est creer, les caracteres du String
     * sont verifies
     *
     * Utilisaiton d'un stream pour passer au travers d
     * @param nomFichier le nom du fichier a interpreter
     * @return String valide a interpreter
     */
    private static String fichierEnString(String nomFichier) {

        StringBuilder contenuDuFichier = new StringBuilder();

        try {
            Stream<String> stream = Files.lines(Paths.get(nomFichier)); {
                stream.forEach(s -> contenuDuFichier.append(s).append("\n"));
            }
        }

        catch(IOException e) {
            System.err.println(Messages.ERREUR_FICHIER_INEXSITANT + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

        verificationCaracteresValides(contenuDuFichier.toString());

        return contenuDuFichier.toString();

    }

    /**
     * Verification si tout les caracteres sont des caracteres admissibles
     * Si un ou plus caractere n'est pas bon, lance une erreur et ferme le programme
     *
     * @param texte programme a interpreter
     */
    private static void verificationCaracteresValides(String texte) {

        String regex = "[0-9, a-z, A-Z,/()_\\n]+";
        boolean caracteresValides = texte.matches(regex);

        if (!caracteresValides) {
            System.err.println(Messages.ERREUR_CARACTERE_INVALIDE + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

    }

}
