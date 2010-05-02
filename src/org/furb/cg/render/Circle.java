package org.furb.cg.render;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;

import javax.media.opengl.GL;

import org.furb.cg.model.Poligono;
import org.furb.cg.model.Ponto;
import org.furb.cg.util.Base;
import org.furb.cg.util.Mode;

/**
 * Classe responsavel pelo desenho de uma circulo
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 21/04/2010
 */
public class Circle extends org.furb.cg.render.Base {

	public Circle(GL gl) {
		super(gl);
	}

	/**
	 * Metodo utilizado para desenhar um circulo
	 * na tela do usuario
	 * @param poligon
	 */
	@Override
	public void draw(Poligono poligon) 
	{
		Ponto origem	= poligon.getPontos().get(0);
		Ponto raio		= poligon.getPontos().get(1);
		double radius	= Float.valueOf( String.valueOf( floor( Base.getInstace().distancia(origem, raio) ) ) );
		
		poligon.getPontos().clear();
		
		double angle		= 0.0f;
		double vectorY1		= origem.getY() + radius;
		double vectorX1		= origem.getX();
		double vectorX;
		double vectorY;

		for(angle = 0.0; angle <= (2.0 * PI); angle += 0.1 ) 
		{		
			vectorX = origem.getX() + (radius * sin(angle));
			vectorY = origem.getY() + (radius * cos(angle));		
			poligon.getPontos().add( new Ponto(vectorX1, vectorY1) );
			poligon.updateBoundBox();
			vectorY1 = vectorY;
			vectorX1 = vectorX;			
		}
	}

	/**
	 * Desenha o preview do circulo enquanto
	 * o usuario move o mouse
	 */
	public void drawPreview(Poligono currentPoligon, Poligono line)
	{
		if( currentPoligon != null )
		{
			if( !currentPoligon.getPontos().isEmpty() && currentPoligon.getMode() == Mode.CIRCLE ) 
			{
				if( line != null && !line.getPontos().isEmpty() ) 
				{
					Poligono poligon = new Poligono();
					poligon.getPontos().add( currentPoligon.getPontos().get(0) );
					poligon.getPontos().add( line.getPontos().get(0) );
					this.draw(poligon);
					
					gl.glBegin(GL.GL_LINE_LOOP);
					
					for( Ponto point : poligon.getPontos() ) { 
						gl.glVertex2d( point.getX(), point.getY() );	
					}
					
					gl.glEnd();
				}
			}
		}
	}
}
