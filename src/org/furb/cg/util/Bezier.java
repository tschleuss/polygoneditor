package org.furb.cg.util;

import org.furb.cg.model.Poligono;

/**
 * Classe responsavel por realizar
 * os cálculos necessários para desenhar 
 * uma spline (Singleton).
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 21/04/2010
 */

public class Bezier {

	private static Bezier	bezier = new Bezier();
	
	private Bezier() {
		super();
	}
	
	/**
	 * Retorna a Instancia da classe BezierTool
	 * @return
	 */
	public static Bezier getInstace()
	{
		if( bezier == null ) {
			bezier = new Bezier();
		}
		
		return bezier;
	}
	
	
	/**
	 * Recupera os pontos adicionados pelo usuario
	 * com o listener do mouse.
	 * @param poligon
	 * @param numArco
	 * @param t
	 * @return
	 */
	public float[] evaluateSplinePoint(Poligono poligon, int numArco, float t) 
	{
	    float x = 0;
	    float y = 0;
	    float result;
	    float[] points = new float[2];
	    
	    for (int pontoArco = 0; pontoArco <= 3; pontoArco++ )
	    {
	    	result = calculeBezier(pontoArco,t);
	    	points = poligon.getPontos().get(numArco+pontoArco);
	    	
	    	x += result * points[0];
	      	y += result * points[1];
	    }
	    
	    return new float[]{ x , y };
	}
	
	/**
	 * Metodo utilizado para calcular os pontos para
	 * desenhar a spline de bezier
	 * @param i
	 * @param t
	 * @return
	 */
	public float calculeBezier(int i, float t) 
	{
		double base = (1 - t);
	
		switch (i) 
		{
			case 0: {
				return (float) Math.pow(base, 3);
			}
			
			case 1: {
				return (float)(3 * t * Math.pow(base, 2));
			}
			
			case 2: {
				return (float)(3 * Math.pow(t, 2) * base);
			}
			
			case 3: {
				return (float)Math.pow(t, 3);
			}
		}
		
		return 0;
	}
	
}
