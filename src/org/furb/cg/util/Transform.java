package org.furb.cg.util;

import org.furb.cg.model.Matrix;
import org.furb.cg.model.Ponto;

/**
 * Classe base responsavel por guardar
 * funcoes que envolvam o calculo das transforma��es 
 * de rota��o e escala de um ponto 2D (Singleton).
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 01/05/2010
 */
public final class Transform {
	
	private static Transform transform = new Transform();
	
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
	

//						[ matrix[0] matrix[4] matrix[8]  matrix[12] ]
//transformMatrix = 	[ matrix[1] matrix[5] matrix[9]  matrix[13] ]
//						[ matrix[2] matrix[6] matrix[10] matrix[14] ]
//						[ matrix[3] matrix[7] matrix[11] matrix[15] ]
	private Matrix transformMatrix;
	
	/**
	 * Configura a matrix inicial
	 */
	public void ResetTransformMatrix(){
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
	public void ConfigScaleMatrix(double orignX, double orignY, double orignZ){
		double	Lx = -orignX;		
		double	Ly = -orignY;		
		double	Lz = -orignZ;
		
		double	Sx = scale;		
		double	Sy = Sx;		
		double	Sz = Sx;
		
		this.setElement(0, 0, Sx); 				this.setElement(0, 1, 0);				this.setElement(0, 2, 0);				this.setElement(0, 3, 0);
		this.setElement(1, 0, 0); 				this.setElement(1, 1, Sy);				this.setElement(1, 2, 0);				this.setElement(1, 3, 0);
		this.setElement(2, 0,0);				this.setElement(2, 1,0);				this.setElement(2, 2,Sz);				this.setElement(2, 3,0);
		this.setElement(3, 0,(Lx * Sx) -Lx);	this.setElement(3, 1,(Ly * Sy) - Ly);	this.setElement(3, 2,(Lz * Sz) -Lz);	this.setElement(3, 3,1);
	}
	
	/**
	 * Configura a matrix global para Rota��o
	 * @param orignX
	 * @param orignY
	 */
	public void ConfigRotateMatrix(double orignX, double orignY){
		
		 //C�lcula o cos e o sen
		double radian = (angle * Math.PI) / 180;
		double cosAngle = Math.cos(radian);
		double sinAngle = Math.sin(radian);
		
		double	Lx = -orignX;
		double	Ly = -orignY;
		
		double pos3_0 = ((Lx * cosAngle) + (Ly * sinAngle)) -Lx;
		double pos3_1 = ((Lx * (-sinAngle)) + (Ly * cosAngle))- Ly;
		double pos3_2 = 1;
		
		this.setElement(0, 0, cosAngle); 	this.setElement(0, 1, -sinAngle);	this.setElement(0, 2, 0);		this.setElement(0, 3, 0);
		this.setElement(1, 0, sinAngle); 	this.setElement(1, 1, cosAngle);	this.setElement(1, 2, 0);		this.setElement(1, 3, 0);
		this.setElement(2, 0,0);			this.setElement(2, 1,0);			this.setElement(2, 2,1);		this.setElement(2, 3,0);
		this.setElement(3, 0,pos3_0);		this.setElement(3, 1,pos3_1);		this.setElement(3, 2,pos3_2);	this.setElement(3, 3,1);
	}

	/**
	 * Aplica a matrix de transforma��o a um ponto
	 * @param p
	 */
	public void transformPoint(Ponto p) {
		
		Matrix result = p.getMatrix().product(this.transformMatrix);
		
		p.setX( result.getAt(0, 0));
		p.setY( result.getAt(0, 1));
		p.setZ( result.getAt(0, 2));
		p.setW( result.getAt(0, 3));
	}
	
	/**
	 * Busca elemento em uma determinada posi��o da matriz de transforma��o
	 * @param linha
	 * @param coluna
	 * @return value
	 */
	public double getElement(int  linha, int coluna ) {
		return this.transformMatrix.getAt(linha, coluna);
	}
	
	/**
	 * Atribui valor a um elemento da matriz de transforma��o
	 * @param linha
	 * @param coluna
	 * @param value
	 */
	public void setElement(int  linha, int coluna, double value) {
		this.transformMatrix.setAt(linha, coluna, value);
	}

	/**
	 * Seta a  que ser� utilizada no escalonamento do objeto
	 * @param scale
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getScale() {
		return scale;
	}

	/**
	 * Seta o �ngulo que ser� utilizado na rota��o do objeto
	 * @param scale
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}

}
