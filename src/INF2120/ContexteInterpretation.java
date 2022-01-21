/**
 *
 * @author Jonathan Labelle
 * @codePermanent LABJ28039006
 * @Date 07/04/2021
 */

package INF2120;

public interface ContexteInterpretation {

    void genDebutClasse( ClasseDebut motDebutClasse );
    void genFinClasse( ClasseFin motFinClasse);
    void genDebutMethode( MethodeDebut motDebutMethode);
    void genAttribut( Attribut motAttribut);
    void genAbstrait( Abstrait motAbstrait);
    void genParametre( Parametre motParametre);
    void genFinMethode( MethodeFin motFinMethode);

}
