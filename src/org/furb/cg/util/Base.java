package org.furb.cg.util;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import org.furb.cg.EditorFrame;
import org.furb.cg.model.Ponto;

/**
 * Classe base responsavel por guardar
 * funcoes de uso constante, guardar
 * atributos comuns do programa e que
 * devem ficar armazenados independete
 * do estado do programa (Singleton).
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public class Base {

	private static Base		base = new Base();
	private double			screenWidth;
	private double			screenHeight;
	private double			left;
	private double			right;
	private double			bottom;
	private double			top;
	private double 			scale = 1.0f;
	private double 			panX;
	private double 			panY;
	private EditorFrame		window;
	
	private Base() {
		super();
	}
	
	/**
	 * Retorna a Instancia da classe Base
	 * @return
	 */
	public static Base getInstace()
	{
		if( base == null ) {
			base = new Base();
		}
		
		return base;
	}
	
	/**
	 * Utilizado para normalizar a coordenada X
	 * @param posX
	 * @return
	 */
	public double normalizarX(double x)
	{
		final double xOrigem = screenWidth;		
		final double xDestino = right - left;
		
		double newX = ( x * ( xDestino / xOrigem ) ) + left;
		newX = (newX * scale) + panX;
		
		return newX;
	}
	
	/**
	 * Utilizado para normalizar a coordenada Y
	 * @param posY
	 * @return
	 */
	public double normalizarY(double y)
	{
		//tamanho da toolbar
		final double toolbarFix = 90.0f;
		final double yOrigem = screenHeight;		
		final double yDestino = bottom - (top + toolbarFix);
		
		double newY = ( y * ( yDestino / yOrigem ) ) + top;
		newY = (newY * scale) + panY;
		
		return newY;
	}
	
	public double[] rotacionarXY (double x, double y, double angulo)
	{
		final double radianos = (angulo * Math.PI) / 180;
		final double cosAngulo = Math.cos(radianos);
		final double sinAngulo = Math.sin(radianos);
		
		double newX = (x * cosAngulo) - (y * sinAngulo);
		double newY = (y * cosAngulo) + (x * sinAngulo);
		
		double[] valores = new double[2];
		valores [0] = newX; 
		valores [1] = newY; 
		
		return valores;
	}
	

	/**
	 * Formula usada para calcular a distancia
	 * entre dois pontos.
	 * @param p1
	 * @param p2
	 * @return
	 */
	public double distancia(Ponto p1, Ponto p2) 
	{
		final double xCalc = pow((p2.getX() - p1.getX()),2);
		final double yCalc = pow((p2.getY() - p1.getY()),2);
		return sqrt( xCalc + yCalc );
	}
	
	public double getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(double screenWidth) {
		this.screenWidth = screenWidth;
	}

	public double getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(double screenHeight) {
		this.screenHeight = screenHeight;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public double getBottom() {
		return bottom;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public void setPanX(double panX) {
		this.panX = panX;
	}

	public double getPanX() {
		return panX;
	}

	public void setPanY(double panY) {
		this.panY = panY;
	}

	public double getPanY() {
		return panY;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getScale() {
		return scale;
	}

	public void setWindow(EditorFrame window) {
		this.window = window;
	}

	public EditorFrame getWindow() {
		return window;
	}
}
