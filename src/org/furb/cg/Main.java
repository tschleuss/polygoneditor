package org.furb.cg;

import java.awt.EventQueue;

/**
 * Classe inicializadora do programa.
 * Responsavel por chamar a classe que
 * iniciara a renderizacao de todos os elementos
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public class Main {
	
	public static void main(String[] args)
	{
        EventQueue.invokeLater(new Runnable() 
        {
        	feedback frame1 = null;
        	EditorFrame frame = null;
        	
            public void run() 
            {
        		frame = new EditorFrame();
        		frame.setLocationRelativeTo(null);
        		frame.setVisible(true);
        		frame.getCanvas().requestFocus();
        		
        		/*
        		frame1 = new feedback();
        		frame1.setLocationRelativeTo(null);
        		frame1.setVisible(true);
        		*/
            }
        });
	}
}
