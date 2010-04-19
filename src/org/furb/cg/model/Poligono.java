package org.furb.cg.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.furb.cg.util.Mode;

/**
 * Representa a estrutura dos poligonos.
 * Armazena todos os dados referentes ao mesmo.
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public class Poligono {

	//Atributos do Poligono
	private List<float[]>	pontos		= new ArrayList<float[]>();
	private List<float[]>	origem		= new ArrayList<float[]>();
	private boolean			selected	= false;
	private Mode			mode		= null;
	private Color			color		= Color.BLACK;
	
	//Valores da bound box
	private float			maxX		= Float.MIN_VALUE;
	private float			minX		= Float.MAX_VALUE;
	private float			maxY		= Float.MIN_VALUE;
	private float			minY		= Float.MAX_VALUE;
	
	public Poligono() {
		super();
	}
	
	/**
	 * Atualiza os valores da bound box
	 * de acordo com os parametros
	 * ja previamente setados
	 */
	public void updateBoundBox() 
	{ 
		float vX = 0.0f;
		float vY = 0.0f;
		
		for( float points[] : pontos )
		{
			vX = points[0];
			vY = points[1];
			
			if ( vX > maxX ) { maxX = vX; }
			if ( vX < minX ) { minX = vX; }
			if ( vY > maxY ) { maxY = vY; }
			if ( vY < minY ) { minY = vY; }
		}
	}
	
	/**
	 * Verifica se as coordenadas passadas
	 * esta entre os pontos que formam o boundbox
	 * deste poligono
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isOverBoundBox(float x, float y)
	{
		if( (x >= minX) && (x <= maxX ) ) 
		{
			if( (y >= minY) && (y <= maxY ) ) 
			{
				return true;
			}	
		}
		
		return false;
	}
	
	/**
	 * Metodo utilizado para resetar a bound box
	 * do poligono.
	 */
	public void resetBoundBox()
	{
		maxX = Float.MIN_VALUE;
		minX = Float.MAX_VALUE;
		maxY = Float.MIN_VALUE;
		minY = Float.MAX_VALUE;
	}

	public List<float[]> getPontos() {
		return pontos;
	}

	public void setPontos(List<float[]> pontos) {
		this.pontos = pontos;
	}

	public List<float[]> getOrigem() {
		return origem;
	}

	public void setOrigem(List<float[]> origem) {
		this.origem = origem;
	}

	public float getMaxX() {
		return maxX;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

	public float getMinX() {
		return minX;
	}

	public void setMinX(float minX) {
		this.minX = minX;
	}

	public float getMaxY() {
		return maxY;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

	public float getMinY() {
		return minY;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
