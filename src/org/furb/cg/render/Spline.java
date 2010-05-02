package org.furb.cg.render;

import javax.media.opengl.GL;

import org.furb.cg.model.Poligono;
import org.furb.cg.model.Ponto;
import org.furb.cg.util.Bezier;

/**
 * Classe responsavel pelo desenho de uma spline
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 21/04/2010
 */
public class Spline extends Base{
	
	public Spline(GL gl) {
		super(gl);
	}

	/**
	 * Metodo utilizado para desenhar
	 * a spline na tela do usuario.
	 */
	@Override
	public void draw(Poligono poligon)
	{
		
		//Seta o cor do poligono a ser renderizado
		gl.glPointSize(3.0f);
		gl.glBegin(GL.GL_POINTS);
		
		for( Ponto point : poligon.getPontos() )
		{
			gl.glVertex2d(point.getX(), point.getY());
		}
		
		gl.glEnd();
		
		if( poligon.getPontos().size() >= 4 )
		{
			gl.glBegin(GL.GL_LINE_STRIP);
			
			float[] point = Bezier.getInstace().evaluateSplinePoint(poligon, 0, 0);
			gl.glVertex2f(point[0], point[1]);
			
			for( int i = 0; i < poligon.getPontos().size() - 3; i+= 3)
			{
				for( int j = 1; j <= 36; j++ )
				{
					float[] newPoint = Bezier.getInstace().evaluateSplinePoint(poligon, i, ((float)j/36));
					
					gl.glVertex2f(newPoint[0], newPoint[1]);
				}
			}
			
			gl.glEnd();
			
		}
	}
}
