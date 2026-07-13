package monpackage;

import java.awt.*;

/**
 * Panel personnalise qui affiche un degrade (transition progressive)
 * entre deux couleurs, au lieu d'une couleur unie.
 */
public class PanelDegrade extends Panel {

    private final Color couleurHaut;
    private final Color couleurBas;

    public PanelDegrade(LayoutManager layout, Color couleurHaut, Color couleurBas) {
        super(layout);
        this.couleurHaut = couleurHaut;
        this.couleurBas  = couleurBas;
    }

    @Override
    public void paint(Graphics g) {
        int largeur = getWidth();
        int hauteur = getHeight();

        Graphics2D g2 = (Graphics2D) g;
        // GradientPaint : dessine une transition entre 2 points et 2 couleurs
        GradientPaint degrade = new GradientPaint(
            0, 0, couleurHaut,        // point de depart (haut) + sa couleur
            0, hauteur, couleurBas    // point d'arrivee (bas) + sa couleur
        );
        g2.setPaint(degrade);
        g2.fillRect(0, 0, largeur, hauteur);

        // Important : on doit quand meme dessiner les enfants (liste, labels, etc.)
        super.paint(g);
    }
}