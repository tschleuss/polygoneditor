package org.furb.cg.util;

import org.furb.cg.model.Matrix;
import org.furb.cg.model.Ponto;

/**
 * Classe base responsavel por guardar
 * funcoes que envolvam o calculo das transformações 
 * de rotacao e escala de um ponto 2D (Singleton).
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 01/05/2010
 */
public final class Transform {
	
	private static Transform transform = new Transform();
	private Matrix transformMatrix;
	private double scale;
	private double angle;
	
	private Transform() {
		super();
		this.ResetTransformMatrix();
	}
	
	/**
	 * Retorna a Instancia da classe Transform
	 * @return
	 */
	public static Transform getInstace()
	{
		if( transform == null ) {
			transform = new Transform();
		}
		
		return transform;
	}

	/**
	 * Configura a matrix inicial
	 */
	public void ResetTransformMatrix()
	{
		double[][] matrix = new double[][]{
				{1,0,0,0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
		};
		
		this.transformMatrix = new Matrix(matrix);
	}
	
	/**
	 * Configura a matrix global para Escala
	 * @param orignX
	 * @param orignY
	 * @param orignZ
	 */
	public void ConfigScaleMatrix(double orignX, double orignY, double orignZ)
	{
		double	Lx = -orignX;		
		double	Ly = -orignY;		
		double	Lz = -orignZ;
		
		double	Sx = scale;		
		double	Sy = Sx;		
		double	Sz = Sx;
		
		double pX0Y3 = (Lx * Sx) -Lx;
		double pX1Y3 = (Ly * Sy) - Ly;
		double pX2Y3 = (Lz * Sz) -Lz;
		
		double[][] matrix = new double[][]{
			{Sx,	0,		0,		0},
			{0, 	Sy, 	0, 		0},
			{0, 	0, 		Sz, 	0},
			{pX0Y3, pX1Y3, pX2Y3, 	1}
		};
		
		this.transformMatrix = new Matrix(matrix);
	}
	
	/**
	 * Configura a matrix global para Rotação
	 * @param orignX
	 * @param orignY
	 */
	public void ConfigRotateMatrix(double orignX, double orignY){
		
		 //Calcula o cos e o sen
		double radian = (angle * Math.PI) / 180;
		double cosAngle = Math.cos(radian);
		double sinAngle = Math.sin(radian);
		
		double	Lx = -orignX;
		double	Ly = -orignY;
		
		double pX0Y3 = ((Lx * cosAngle) + (Ly * sinAngle)) -Lx;
		double PX1Y3 = ((Lx * (-sinAngle)) + (Ly * cosAngle))- Ly;
		
		double[][] matrix = new double[][]{
			{cosAngle,	-sinAngle,	0,	0},
			{sinAngle, 	cosAngle, 	0, 	0},
			{0, 		0, 			1, 	0},
			{pX0Y3,  	PX1Y3, 		1,	1}
		};
		
		this.transformMatrix = new Matrix(matrix);
	}

	/**
	 * Aplica a matrix de transformação a um ponto
	 * @param p
	 */
	public void transformPoint(Ponto p) 
	{
		Matrix result = p.getMatrix().product(this.transformMatrix);
		
		p.setX( result.getAt(0, 0));
		p.setY( result.getAt(0, 1));
		p.setZ( result.getAt(0, 2));
		p.setW( result.getAt(0, 3));
	}
	
	/**
	 * Busca elemento em uma determinada posição da matriz de transformação
	 * @param linha
	 * @param coluna
	 * @return value
	 */
	public double getElement(int  linha, int coluna ) {
		return this.transformMatrix.getAt(linha, coluna);
	}
	
	/**
	 * Atribui valor a um elemento da matriz de transformação
	 * @param linha
	 * @param coluna
	 * @param value
	 */
	public void setElement(int  linha, int coluna, double value) {
		this.transformMatrix.setAt(linha, coluna, value);
	}

	/**
	 * Seta a  que será utilizada no escalonamento do objeto
	 * @param scale
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getScale() {
		return scale;
	}

	/**
	 * Seta o ângulo que será utilizado na rotação do objeto
	 * @param scale
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}

}
