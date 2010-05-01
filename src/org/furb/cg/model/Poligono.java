package org.furb.cg.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.furb.cg.util.Mode;
import org.furb.cg.util.Transform;

/**
 * Representa a estrutura dos poligonos.
 * Armazena todos os dados referentes ao mesmo.
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public class Poligono {

	//Atributos do Poligono
	private List<Ponto>		pontos		= new ArrayList<Ponto>();
	private boolean			selected	= false;
	private Mode			mode		= null;
	private Color			color		= Color.BLACK;
	
	//Valores da bound box
	private double			maxX		= Double.NEGATIVE_INFINITY;
	private double			minX		= Double.POSITIVE_INFINITY;
	private double			maxY		= Double.NEGATIVE_INFINITY;
	private double			minY		= Double.POSITIVE_INFINITY;
	private double			centerX  	= Double.POSITIVE_INFINITY;;
	private double			centerY		= Double.POSITIVE_INFINITY;;
	
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
		double vX = 0.0;
		double vY = 0.0;
		
		for( Ponto points : pontos )
		{
			vX = points.getX();
			vY = points.getY();
			
			if ( vX > maxX ) { maxX = vX; }
			if ( vX < minX ) { minX = vX; }
			if ( vY > maxY ) { maxY = vY; }
			if ( vY < minY ) { minY = vY; }
		}
		
		//centros da boundbox
		centerX = maxX - minX;
		centerX = centerX / 2;
		centerX += minX;
		
		centerY = maxY - minY;
		centerY = centerY / 2;
		centerY += minY;

	}
	
	/**
	 * Verifica se as coordenadas passadas
	 * esta entre os pontos que formam o boundbox
	 * deste poligono
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isOverBoundBox(double x, double y)
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
		maxX = Double.NEGATIVE_INFINITY;
		minX = Double.POSITIVE_INFINITY;
		maxY = Double.NEGATIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY;
	}

	public void setSelected(boolean selected) {
		this.resetBoundBox();
		this.updateBoundBox();
		this.selected = selected;
	}
	
	private void rotatePoints(){
		
		Transform.getInstace().ConfigRotateMatrix(centerX, centerY);
		
		for( Ponto ponto : pontos )
		{
			Transform.getInstace().transformPoint(ponto);
		}
		
		this.resetBoundBox();
		this.updateBoundBox();
	}
	
	
	private void scalePoints(){

		Transform.getInstace().ConfigScaleMatrix(centerX, centerY, 0);
		
		for( Ponto ponto : pontos )
		{
			Transform.getInstace().transformPoint(ponto);
		}
		
		this.resetBoundBox();
		this.updateBoundBox();
	}
	
	public void setRotate(boolean rotate) {
		if(rotate){
			rotatePoints();
		}
	}
	

	public void setScale(boolean scale) {
		if(scale && this.isSelected()){
			scalePoints();
		}
	}

	public List<Ponto> getPontos() {
		return pontos;
	}

	public void setPontos(List<Ponto> pontos) {
		this.pontos = pontos;
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

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public boolean isSelected() {
		return selected;
	}
}
