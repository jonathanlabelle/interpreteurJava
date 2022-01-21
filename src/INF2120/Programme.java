/**
 *
 * Verifie la validiter des commandes fournies
 *
 * @author Jonathan Labelle
 * @codePermanent LABJ28039006
 * @Date 07/04/2021
 */

package INF2120;

import java.util.*;


public class Programme extends Commande implements ContexteInterpretation {

    private static ArrayList<String> listeDeCommande = new ArrayList<>();
    private static Commande commande;


    public static ArrayList<String> getListeDeCommande() {
        return listeDeCommande;
    }

    /**
     * Ajoute une commande (En string) a partir du String du programme fourni au arraylist listeDeCommande
     *
     * La methode enleve egalement les elements vides dans le tableau
     *
     * @param fichierTexteProgramme le String cree a partir du fichier fourni par l'utilisateur
     */
    protected static void creationListeDeCommandesString(String fichierTexteProgramme) {

        Scanner scanner = new Scanner(fichierTexteProgramme);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            listeDeCommande.add(line.trim());
        }

        listeDeCommande.removeIf(item -> item == null || "".equals(item));

    }

    /**
     * cree une commande a partir d'un string
     *
     * Veririe d'abord si la commande (String) contient deux parentheses. Si oui, envoie la creation a la methode
     * creationCommandeAvecParentheses qui s'occupe des commandes avec parentheses
     * Si elle ne contient aucune parentheses, envoie la creation de methode a creationCommandeSansParentheses
     * Si le String n'est pas interpreter a ce moment, il y a un probleme avec le format, lance une erreur et ferme le
     * programme
     * @param commandeString nom de la commande a interpreter en String
     * @return
     */
    protected static Commande interpreteCommande(String commandeString) {

        if (commandeString.contains("(") && commandeString.contains(")")) {
            commande = (creationCommandeAvecParentheses(commandeString));
        }
        else if (!commandeString.contains("(") && !commandeString.contains(")")) {
            commande = (creationCommandeSansParentheses(commandeString));
        }
        else {
            System.err.println(Messages.ERREUR_FORMAT + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

        return commande;
    }


    /**
     * Cree une une de ces trois commandes: Abstrait, ClasseFin, MethodeFin
     *
     * Verifie si le string envoyer contient un des trois noms de commandes. Si le string n`en contient pas,
     * lance une erreur et ferme le programme
     * @param commande la ligne de commande a creer
     * @return la commande cree
     */
    private static Commande creationCommandeSansParentheses (String commande) {

        Commande commandeEnCours = null;

        if (commande.contains("abstrait")) {
            commandeEnCours = new Abstrait();
        }
        else if (commande.contains("classeFin")) {
            commandeEnCours = new ClasseFin();
        }
        else if (commande.contains("methodeFin")) {
            commandeEnCours = new MethodeFin();
        }

        if (commandeEnCours == null) {
            System.err.println(Messages.ERREUR_FORMAT + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

        return commandeEnCours;
    }


    /**
     * Cree une de ces 4 commandes: ClasseDebut, Attribut, MethodeDebut, Parametre
     *
     * Verifie le le string recu contient un de ces 4 noms. Si non, lance une erreur et ferme le programme
     * @param commande la ligne de commande a creer
     * @return la commande cree
     */
    private static Commande creationCommandeAvecParentheses(String commande) {

        Commande commandeEnCours = null;

        if (commande.contains("classeDebut") && verificationNombreIdentificateur(commande) == 1 ) {
            commandeEnCours = new ClasseDebut();
        }
        else if (commande.contains("attribut") && verificationNombreIdentificateur(commande) == 2) {
            commandeEnCours = new Attribut();
        }
        else if (commande.contains("methodeDebut") && verificationNombreIdentificateur(commande) == 2) {
            commandeEnCours = new MethodeDebut();
        }
        else if (commande.contains("parametre") && verificationNombreIdentificateur(commande) == 2) {
            commandeEnCours = new Parametre();
        }

        if (commandeEnCours == null) {
            System.err.println(Messages.ERREUR_FORMAT + Messages.FIN_PROGRAMME);
            System.exit(-1);
        }

        return commandeEnCours;
    }

    /**
     * Cree un arraylist d'identificateur a partir d'un string
     *
     * Ajoute l'identificateur apres la prenthese ouvrante au arrayliste. S'il y a virgule, ajoute l'identificateur
     * qui la suit au arrayList
     * Avant de retourner le arraylist, verifie si le format de l'identificateur est valide
     * @param ligne string des identificateurs a separer
     * @return arraylist contenant un ou deux identificateurs
     */
    protected static ArrayList<String> separationDesIdentificateurs(String ligne) {
        ArrayList<String> listeIdentificateurs = new ArrayList<>();

        if (ligne.contains("(") && ligne.contains(")")) {

            String identificateurs = ligne.substring(ligne.indexOf("("));

            identificateurs = identificateurs.substring(identificateurs.indexOf("(")+1, identificateurs.indexOf(")"));
            identificateurs = identificateurs.replace(" ","");

            if (identificateurs.contains(",")) {
                String[] identificateur =  identificateurs.split(",");
                listeIdentificateurs.addAll(Arrays.asList(identificateur));
            } else {
                listeIdentificateurs.add(identificateurs);
            }
        }
        verificationPremierCaractereIdentificateurs((listeIdentificateurs));
        return listeIdentificateurs;
    }

    /**
     * Verifie le nombre d'identificateurs dans une commande
     *
     * @param commande la commande en String
     * @return nombre d'identificateur
     */
    private static int verificationNombreIdentificateur(String commande) {
        commande = commande.substring(commande.indexOf("("));
        String[] listeIdentificateurs = commande.split(",");
        return listeIdentificateurs.length;
    }

    /**
     * Verifie si le premier caractere d'un identificateur n'est pas un chiffre
     *
     * Si c'est un chiffre, lance une erreur et ferme le programme
     * @param identificateurs arraylist des identificateurs a verifier
     */
    private static void verificationPremierCaractereIdentificateurs(ArrayList<String> identificateurs) {
        for(String identicateur: identificateurs) {
            char premierCaratere = identicateur.charAt(0);
            if(Character.isDigit(premierCaratere)) {
                System.err.println(Messages.ERREUR_INDENTIFICATEUR_PREMIER_CARACTERE + Messages.FIN_PROGRAMME);
                System.exit(0);
            }
        }
    }

    @Override
    public void genDebutClasse(ClasseDebut motDebutClasse) {

    }

    @Override
    public void genFinClasse(ClasseFin motFinClasse) {

    }

    @Override
    public void genDebutMethode(MethodeDebut motDebutMethode) {

    }

    @Override
    public void genAttribut(Attribut motAttribut) {

    }

    @Override
    public void genAbstrait(Abstrait motAbstrait) {

    }

    @Override
    public void genParametre(Parametre motParametre) {

    }

    @Override
    public void genFinMethode(MethodeFin motFinMethode) {

    }

    @Override
    public void interprete(ContexteInterpretation contexte) {

    }
}
