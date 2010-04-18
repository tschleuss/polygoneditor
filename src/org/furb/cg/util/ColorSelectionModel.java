package org.furb.cg.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JColorChooser;

/**
 * Classe utilizada como listener para o
 * JColorChooser poder armazenar a cor
 * selecionada pelo usuario.
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public class ColorSelectionModel implements ActionListener, Serializable {
	
	private static final long serialVersionUID = 5365545701095523982L;
	
	private JColorChooser chooser	= null;
	private Color color				= null;

    public ColorSelectionModel(JColorChooser c) {
        chooser = c;
    }

    public void actionPerformed(ActionEvent e) {
        color = chooser.getColor();
    }

	public JColorChooser getChooser() {
		return chooser;
	}

	public void setChooser(JColorChooser chooser) {
		this.chooser = chooser;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
