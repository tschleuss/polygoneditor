package org.furb.cg.model;

import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.GLUT;

public class GLBezier {

	private final int steps = 10;
	private ArrayList<GLPoint> points;
	private GLUT glut;
	private GL gl;
	private int glFont;
	
	private int selectedGroupPoint, selectedPoint;
	
	public GLBezier(){
		this.setPoints(new ArrayList<GLPoint>());
		this.glFont = GLUT.BITMAP_HELVETICA_10;
		this.glut = new GLUT();
		setPointGroupSelected(-1);
		setPointSelected(-1);
	}
	
	public void draw(GLAutoDrawable glDrawable) {
			
		this.gl = glDrawable.getGL();
		
		int i, j;
		
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glPointSize(3.0f);
		 
		//desenha os pontos
		gl.glBegin(GL.GL_POINTS);
		    for (i = 0; i < getPoints().size(); i++) {

		    	 gl.glColor3f(0.0f, 0.0f, 0.0f);
		    	
		    	 if(isSelected(i)){
		    		 gl.glColor3f(1.0f, 0.0f, 0.0f);
		    	 }
		    	
		    	gl.glVertex2f(getPoints().get(i).getX() ,getPoints().get(i).getY());		    	
		    }
		gl.glEnd();
		
		//desenha o label dos pontos
	    for (i = 0; i < getPoints().size(); i++) {
		    generateNumber(i+1, getPoints().get(i).getX()-1,getPoints().get(i).getY());	    	
	    }
		
		gl.glPointSize(2.0f);
		gl.glColor3f(9.0f, 0.8f, 0.0f);
		
		if(getPoints().size() >= 4){
			
			//desenha o spline
			gl.glBegin(GL.GL_LINE_STRIP);
	
			   GLPoint p = null;
				
	      	   p = evaluatePoint(0, 0);  	  
	      	   gl.glVertex2f(p.getX(), p.getY());
			   
			    //a cada 4 pontos tem que calcular
			    for (i = 0; i < getPoints().size()-3; i+=3) {
			    	
			        for (j = 1; j <= steps; j++) {
			      	  
			      	  p = evaluatePoint(i, (float) j / steps );
			      	  gl.glVertex2f(p.getX(), p.getY());
			      	  
			        }
			    }
			
			gl.glEnd();
			
			gl.glColor3f(0.0f, 6.0f, 0.0f);
			
			//desenha poliedros de controle
			gl.glBegin(GL.GL_LINE_STRIP);
			
			    for (i = 0; i < getPoints().size(); i++) {
			    	gl.glVertex2f(getPoints().get(i).getX(), getPoints().get(i).getY());
			    }
			
			gl.glEnd();
			
		}
	}
	
	private void generateNumber(int number, float x, float y)
	{
		 gl.glColor3f(0.0f, 0.0f, 0.0f);

		 gl.glRasterPos2f(x, y);
		 glut.glutBitmapString(glFont, String.valueOf(number));
	}
	
	private GLPoint evaluatePoint(int numArco, float t) {
		  
	    float x = 0;
	    float y = 0;
	    float result;
	    GLPoint point;
	    
	    //a partir do primeiro nó, percorre os 3 próximos
	    for (int pontoArco = 0; pontoArco<=3; pontoArco++){
	    	
	    	result = calcBezier(pontoArco,t);
	    	point = this.getPoints().get(numArco+pontoArco);
	    	
	    	x += result * point.getX();
	      	y += result * point.getY();
	    }
	    
	    return new GLPoint(x,y);
	}
	
	private float calcBezier(int i, float t) {
	
		double base = (1 - t);
	
		switch (i) {
			case 0:
				return (float) Math.pow(base, 3);
			case 1:
				return (float)(3 * t * Math.pow(base, 2));
			case 2:
				return (float)(3 * Math.pow(t, 2) * base);
			case 3:
				return (float)Math.pow(t, 3);
		}
		return 0;
	}

	public void addPoint(int x, int y) {
		this.getPoints().add(new GLPoint(x, y));
		
		int qtdPontos = getPointsCount();
		
		//se falta 1 pra fechar o ciclo
		if(((qtdPontos + 1) % 4 == 0 && qtdPontos > 4) || qtdPontos == 4){
			this.getPoints().add(new GLPoint(x, y));
		}
	}
	
	public int getPointsCount(){
		return this.getPoints().size();
	}
	
	public GLPoint getSelectedPoint()
	{
		if(this.points.size() > 0){
			return this.points.get(getSelected());
		}else
		{
			return null;
		}
	}
	
	private int getSelected(){
		return ((getPointGroupSelected()-1) * 4) + selectedPoint;
	}
	
	private boolean isSelected(int i){
		
		if(i == getSelected())
			return true;	
		
		return false;
	}

	public void setPointGroupSelected(int pointGroupSelected) {
		
		if(pointGroupSelected == -1){
			this.selectedGroupPoint = pointGroupSelected;
		}
		else
		{
			int qtdGrupos = (getPointsCount() / 4) +1;
			pointGroupSelected--;
			
			if(qtdGrupos > pointGroupSelected){
				this.selectedGroupPoint = pointGroupSelected;
				setPointSelected(1);
			}
		}
	}

	public int getPointGroupSelected() {
		return selectedGroupPoint +1;
	}

	public void setPointSelected(int pointSelected) {
		
		if(pointSelected == -1){
			this.selectedPoint = pointSelected;
		}
		else
		{
			int relativePoint = ((getPointGroupSelected()-1) * 4) + pointSelected;
			
			if(getPointsCount() >= relativePoint)
				this.selectedPoint = pointSelected - 1;
		}
	}

	public int getPointSelected() {
		return selectedPoint +1;
	}

	public void setPoints(ArrayList<GLPoint> points) {
		this.points = points;
	}

	public ArrayList<GLPoint> getPoints() {
		return points;
	}
	  
}
