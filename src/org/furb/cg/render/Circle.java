package org.furb.cg.render;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;

import javax.media.opengl.GL;

import org.furb.cg.model.Poligono;
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
	public void draw(Poligono poligon) {
		float[] origem	= poligon.getPontos().get(0);
		float[] raio	= poligon.getPontos().get(1);
		float radius	= Float.valueOf( String.valueOf( floor( Base.getInstace().distance(origem, raio) ) ) );
		
		poligon.getPontos().clear();
		
		float angle   	= 	0.0f;
		float vectorY1	=	origem[1] + radius;
		float vectorX1	=	origem[0];
		float vectorX;
		float vectorY;

		for(angle = 0.0f; angle <= (2.0f * PI); angle += 0.01f ) 
		{		
			vectorX = origem[0] + (radius * (float)sin((double)angle));
			vectorY = origem[1] + (radius * (float)cos((double)angle));		
			poligon.getPontos().add( new float[] { vectorX1 , vectorY1 } );
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
					
					gl.glBegin(GL.GL_LINE_STRIP);
					
					for( float[] point : poligon.getPontos() ) { 
						gl.glVertex2d( point[0], point[1] );	
					}
					
					gl.glEnd();
				}
			}
		}
	}
	
}
