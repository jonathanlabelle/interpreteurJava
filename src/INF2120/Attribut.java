/**
 *
 * @author Jonathan Labelle
 * @codePermanent LABJ28039006
 * @Date 07/04/2021
 */

package INF2120;

public class Attribut extends Commande {

    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genAttribut(this);
    }

}
