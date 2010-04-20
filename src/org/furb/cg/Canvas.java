package org.furb.cg;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.furb.cg.model.Poligono;
import org.furb.cg.util.Base;
import org.furb.cg.util.Mode;

import com.sun.opengl.util.GLUT;

/**
 * Classe canvas responsavel por todo o
 * processo de renderizacao da tela, atualizacao
 * das variaveis, intercaptacao dos movimentos
 * do mouse, etc.
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public class Canvas implements GLEventListener, KeyListener, MouseMotionListener, MouseListener {
	
	private GL				gl			= null;
	private GLU				glu			= null;
	private GLUT			glut 		= null;
	private GLAutoDrawable	glDrawable	= null;
	private EditorFrame		window		= null;
	private Mode			mode		= null;
	private Color			color		= Color.BLACK;

	private float			left;
	private float			right;
	private float			bottom;
	private float			top;
	
	private List<Poligono>	poligonos			= new ArrayList<Poligono>();
	private List<Poligono>	selecionados		= new ArrayList<Poligono>();
	private int				selectedPointIdx	= 0;
	private Poligono		selectedPoint		= null;
	private Poligono		atual				= null;
	private Poligono		linha				= null;
	private float 			scale				= 1.0f;
	private float			xValue				= 0.0f;
	private float			yValue				= 0.0f;
	
	public Canvas()
	{
		left	=  0.0f;
		right	=  1000.0f;
		bottom	=  0.0f;
		top		=  600.0f;
		
		Base.getInstace().setLeft(left);
		Base.getInstace().setRight(right);
		Base.getInstace().setBottom(bottom);
		Base.getInstace().setTop(top);
	}
	
	/**
	 * Metodo inicial chamado para instanciar
	 * as principais classes utilizadas pelo
	 * JOGL para poder desenha os elementos
	 * na tela.
	 */
	public void init(GLAutoDrawable drawable) {
		
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
	}

	/**
	 * Metodo chamado pelo listener do JOGL
	 * ou por algum outro metodo, para redesenhar
	 * todos os elementos na tela
	 */
	public void display(GLAutoDrawable drawable) 
	{
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 gl.glMatrixMode(GL.GL_MODELVIEW);
		 gl.glLoadIdentity();

		 glu.gluOrtho2D(left * scale, right * scale, bottom * scale, top * scale);
		 gl.glColor3f(0.0f, 0.0f, 0.0f);

		 //Desenha tudo
		 this.drawPoligons();
		 
		 gl.glFlush();
	}
	
	/**
	 * Desenha todos os poligonos na tela
	 * de acordo com os Modos ativos, 
	 * as cores setadas, etc.
	 */
	private void drawPoligons()
	{
		Color color = this.getColor();
		
		for( Poligono poligon : poligonos )
		{
			//Seta o cor do poligono a ser renderizado
			color = poligon.getColor();
			final float red	= color.getRed()   / 255.0f;
			final float gre	= color.getGreen() / 255.0f;
			final float blu = color.getBlue()  / 255.0f;
			gl.glColor3f(red,gre,blu);

			if( poligon.getMode() == Mode.SPLINE ) 
			{
				this.drawSpline(poligon);
			}
			else
			{
				if( poligon.getMode() == Mode.CLOSE_POLYGON)
				{
					gl.glBegin(GL.GL_LINE_LOOP);
				}
				else
				{
					gl.glBegin(GL.GL_LINE_STRIP);
				}
				
				for( float[] pontos : poligon.getPontos() )
				{
					final float pontoX = pontos[0];
					final float pontoY = pontos[1];
					gl.glVertex2f(pontoX, pontoY);
				}
				
				gl.glEnd();
			}
			
			//TESTE - Renderiza a boundbox dos elementos
			final boolean showBox = false;
			if( showBox ) 
			{
				gl.glColor3f(1.0f, 1.0f, 0.0f);
				gl.glBegin(GL.GL_LINE_LOOP);
					gl.glVertex2d(poligon.getMinX(),  poligon.getMinY());	
					gl.glVertex2d(poligon.getMaxX(),  poligon.getMinY());	
					gl.glVertex2d(poligon.getMaxX(),  poligon.getMaxY());	
					gl.glVertex2d(poligon.getMinX(),  poligon.getMaxY());	
				gl.glEnd();
			}
		}
		
		this.drawLinePreviw();
		this.drawCirclePreview();
	}
	
	/**
	 * Metodo utilizado para desenhar
	 * a spline na tela do usuario.
	 */
	private void drawSpline(Poligono poligon)
	{
		//Seta o cor do poligono a ser renderizado
		gl.glPointSize(3.0f);
		gl.glBegin(GL.GL_POINTS);
		
		for( float[] point : poligon.getPontos() )
		{
			gl.glVertex2f(point[0], point[1]);
		}
		
		gl.glEnd();
		
		if( poligon.getPontos().size() >= 4 )
		{
			gl.glBegin(GL.GL_LINE_STRIP);
			
			float[] point = this.evaluateSplinePoint(poligon, 0, 0);
			gl.glVertex2f(point[0], point[1]);
			
			for( int i = 0; i < poligon.getPontos().size() - 3; i+= 3)
			{
				for( int j = 1; j <= 10; j++ )
				{
					float[] newPoint = this.evaluateSplinePoint(poligon, i, ((float)j/10));
					gl.glVertex2f(newPoint[0], newPoint[1]);
				}
			}
			
			gl.glEnd();
		}
	}
	
	/**
	 * Recupera os pontos adicionados pelo usuario
	 * com o listener do mouse.
	 * @param poligon
	 * @param numArco
	 * @param t
	 * @return
	 */
	private float[] evaluateSplinePoint(Poligono poligon, int numArco, float t) 
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
	private float calculeBezier(int i, float t) 
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
	
	/**
	 * Desenha o preview da linha enquanto
	 * o usuario move o mouse.
	 */
	private void drawLinePreviw()
	{
		if( linha != null )
		{
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glBegin(GL.GL_LINE_LOOP);
			
			for( float[] point : linha.getPontos() )
			{
				gl.glVertex2f(point[0], point[1]);
			}
			
			gl.glEnd();
		}
	}
	
	/**
	 * Desenha o preview do circulo enquanto
	 * o usuario move o mouse
	 */
	private void drawCirclePreview()
	{
		if( atual != null )
		{
			if( !atual.getPontos().isEmpty() && atual.getMode() == Mode.CIRCLE ) 
			{
				if( linha != null && !linha.getPontos().isEmpty() ) 
				{
					Poligono poligon = new Poligono();
					poligon.getPontos().add( atual.getPontos().get(0) );
					poligon.getPontos().add( linha.getPontos().get(0) );
					this.drawCircle(poligon);
					
					gl.glBegin(GL.GL_LINE_STRIP);
					
					for( float[] point : poligon.getPontos() ) { 
						gl.glVertex2d( point[0], point[1] );	
					}
					
					gl.glEnd();
				}
			}
		}
	}
	
	/**
	 * Metodo utilizado para desenhar um circulo
	 * na tela do usuario
	 * @param poligon
	 */
	private void drawCircle(Poligono poligon) 
	{
		float[] origem	= poligon.getPontos().get(0);
		float[] raio	= poligon.getPontos().get(1);
		float radius	= Float.valueOf( String.valueOf( floor( distance(origem, raio) ) ) );
		
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
	 * Formula usada para calcular a distancia
	 * entre dois pontos.
	 * @param p1
	 * @param p2
	 * @return
	 */
	private float distance(float[] p1, float[] p2) 
	{
		final float xCalc = Float.valueOf( String.valueOf( pow((p2[0] - p1[0]),2) ) );
		final float yCalc = Float.valueOf( String.valueOf( pow((p2[1] - p1[1]),2) ) );
		return Float.valueOf( String.valueOf( sqrt( xCalc + yCalc ) ) );
	}
	
	/**
	 * Adiciona um ponto na lista de poligonos
	 * atualizando a boundbox do poligono.
	 * @param x
	 * @param y
	 */
	private void addPoint(float x, float y)
	{
		if( atual == null ) {
			this.addPoligon();
		}
		
		atual.setColor( this.getColor() );
		atual.getPontos().add( new float[]{ x, y } );
		atual.updateBoundBox();
		
		//Se clicou novamente e for o modo circulo
		if( atual.getMode() == Mode.CIRCLE )
		{
			if( atual.getPontos().size() == 2 )
			{
				this.drawCircle(atual);
				this.cancelSelection();
			}
		}
	}
	
	/**
	 * Metodo utilizado para validar a interseccao
	 * entre o mouse o possivel objeto na tela
	 * @return
	 */
	public void intersectionCheck(float x, float y)
	{
		//Cria um array para armazenar o poligonos pre-selcionados
		List<Poligono> preSelecteds = new ArrayList<Poligono>();
		selecionados.clear();
		
		//Para todos os poligonos da tela
		for( Poligono poligon : poligonos )
		{
			//Nenhum esta selecionado ainda
			poligon.setSelected(false);
			
			//Se o ponto do clique esta dentro de sua boundbox
			if( poligon.isOverBoundBox(x, y) )
			{
				//Pre-selciona o poligono
				preSelecteds.add(poligon);
			}
		}
		
		//Para todos os poligonos pre-selecionados
		for( Poligono poligon : preSelecteds )
		{
			//Recupera todos os pares de pontos x,y do poligono pre-selecionado
			Iterator<float[]> iterator = poligon.getPontos().iterator();
			float[] ini = null;
			float[] end = null;
			boolean last = false;
			int count = 0;
			
			while( true ) 
			{
				//Se comecou agora
				if( ini == null ) 
				{
					//Recupera x,y do primeiro ponto
					ini = iterator.next();
				}
				
				//Se ainda ha pontos
				if( iterator.hasNext() )
				{
					//Recupera o proximo x,y do segundo ponto
					end = iterator.next();
				}
				else
				{
					//Senao so ha 1 ponto
					end = poligon.getPontos().get(0);
					last = true;
				}
				
				//Se o ponto inicial x1,y1 for diferente do ponto final x2,y2
				if( ini[1] != end[1] )
				{
					//Utiliza scan line para verifica se o ponto intersecta
					if( this.intersec2d(ini, end, x, y) )
					{
						//Soma a quantidade de interseccoes
						count++;
					}
				}
				
				//Finaliza a linha
				ini = end;
				
				//Se for a ultina, termina!
				if( last )
				{
					break;
				}
			}
			
			//Se colidiu com valor impar de pontos
			if( (count % 2) != 0 )
			{
				//Seleciona o poligono e adiciona na lista de selecionados
				poligon.setSelected(true);
				selecionados.add(poligon);
			}
		}
	}
	
	/**
	 * Verifica interseccao 2D
	 * @param ini
	 * @param end
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean intersec2d(float[] ini, float[] end, float x, float y)
	{
		float det = ini[0] + (end[0] - ini[0]) * ( (y - ini[1]) / (end[1] - ini[1]) ); 

		if ( Float.isInfinite(det) ) {
			return false; // nao ha interseccao
		}

		if(det > x && y > Math.min(ini[1], end[1]) && y <= Math.max(ini[1], end[1])){
			return true; // ha interseccao
		}

		return false;
	}
	
	/**
	 * Finaliza as variaveis, criando assim
	 * um novo "documento" com a tela limpa
	 * sem ponto adicionados.
	 */
	public void newDocument()
	{
		if( poligonos != null ) 
		{
			poligonos.clear();
		}
		
		scale	= 1.0f;
		atual	= null;
		mode	= Mode.SELECTION;
		
		glDrawable.display();
	}
	
	/**
	 * Metodo utilizado para adicionar
	 * um novo poligono
	 */
	private void addPoligon() 
	{
		if( atual != null ) 
		{
			selecionados.clear();
			selecionados.add(atual);
		}
		
		atual = new Poligono();
		atual.setMode( this.getMode() );
		poligonos.add(atual);
	}
	
	/**
	 * Metodo chamado para cancelar a selecao
	 * de um objeto
	 */
	public void cancelSelection() 
	{
		linha	= null;
		atual	= null;
	}
	
	/**
	 * Metodo utilizado para realizar
	 * o efeito de incremento do zoom
	 */
	public void zoomIn()
	{
		if( scale > 0.0 ) {
			scale -= 0.05;
		}
		
		glDrawable.display();
	}

	/**
	 * Metodo utilizado para realizar
	 * o efeito de decremento do zoom
	 */
	public void zoomOut()
	{
		if( scale < 100.0 ) {
			scale += 0.05;
		}
		
		glDrawable.display();
	}
	
	/**
	 * Metodo chamado pelo listener quando o
	 * usuario pressionar um botao do mouse.
	 */
	public void mousePressed(MouseEvent e)
	{
		//Verificar selecao ou adicionar ponto
		final float pointX = Base.getInstace().normalizarX( Float.valueOf( e.getX() ) );
		final float pointY = Base.getInstace().normalizarY( Float.valueOf( e.getY() ) );
		
		selectedPoint = null;
		selectedPointIdx = -1;
		
		if( e.getButton() == MouseEvent.BUTTON1 ) 
		{
			if( this.getMode() == Mode.NEW || this.getMode() == Mode.COLOR )
			{
				//Nao faz nada ainda =)
			}
			else if( this.getMode() == Mode.SELECTION ) 
			{
				intersectionCheck(pointX, pointY);
				xValue = pointX;
				yValue = pointY;
			}
			else
			{
				this.addPoint(pointX, pointY);	
			}
		} 
		else if ( e.getButton() == MouseEvent.BUTTON3 ) 
		{
			this.cancelSelection();
		}
		
		glDrawable.display();
	}
	
	/**
	 * Metodo chamado pelo listener quando o
	 * usuario mover o mouse.
	 */
	public void mouseMoved(MouseEvent e) 
	{
		final float pointX = Base.getInstace().normalizarX( Float.valueOf( e.getX() ) );
		final float pointY = Base.getInstace().normalizarY( Float.valueOf( e.getY() ) );
		
		final MessageFormat mf = new MessageFormat("({0},{1})");
		this.getWindow().setStatus( mf.format( new Object[]{ pointX , pointY } ) );
		
		if( atual != null && !atual.getPontos().isEmpty() )
		{
			if( this.getMode() == Mode.OPEN_POLYGON )
			{
				float[] pointA = atual.getPontos().get( atual.getPontos().size() -1 );
				float[] pointB = new float[]{ pointX , pointY };
				linha = new Poligono();
				linha.getPontos().add(pointA);
				linha.getPontos().add(pointB);
			}
			else if ( this.getMode() == Mode.CLOSE_POLYGON)
			{
				if( atual.getPontos().size() == 1 ) 
				{
					float[] pointA = atual.getPontos().get( atual.getPontos().size() -1 );
					float[] pointB = new float[]{ pointX , pointY };
					linha = new Poligono();
					linha.getPontos().add(pointA);
					linha.getPontos().add(pointB);
				}
				else if( atual.getPontos().size() == 2 )
				{
					float[] pointA = atual.getPontos().get(0);
					float[] pointB = atual.getPontos().get(1);
					float[] pointC = new float[]{ pointX , pointY };
					linha = new Poligono();
					linha.getPontos().add(pointA);
					linha.getPontos().add(pointB);
					linha.getPontos().add(pointC);
				}
				else 
				{
					float[] pointA = atual.getPontos().get(0);
					float[] pointB = atual.getPontos().get(1);
					float[] pointC = atual.getPontos().get( atual.getPontos().size() -1 );
					float[] pointD = new float[]{ pointX , pointY };
					linha = new Poligono();
					linha.getPontos().add(pointA);
					linha.getPontos().add(pointB);
					linha.getPontos().add(pointC);
					linha.getPontos().add(pointD);
				}
			}
			else if ( this.getMode() == Mode.CIRCLE ) 
			{
				if( atual.getPontos().size() == 1 )
				{
					float[] pointA = new float[]{ pointX , pointY };
					linha = new Poligono();
					linha.getPontos().add( pointA );
				}
			}
		}
		
		if( glDrawable != null ) {
			glDrawable.display();
		}
	}
	
	public void mouseDragged(MouseEvent e) 
	{
		final float pointX = Base.getInstace().normalizarX( Float.valueOf( e.getX() ) );
		final float pointY = Base.getInstace().normalizarY( Float.valueOf( e.getY() ) );
		
		final MessageFormat mf = new MessageFormat("({0},{1})");
		this.getWindow().setStatus( mf.format( new Object[]{ pointX , pointY } ) );
		
		if( this.getMode() == Mode.SELECTION )
		{
			if( selectedPoint != null )
			{
				if( selectedPointIdx >= 0 && selectedPointIdx < selectedPoint.getPontos().size() )
				{
					Poligono poligon = selectedPoint;
					int idx = selectedPointIdx;
					
					poligon.resetBoundBox();
					poligon.getPontos().get(idx)[0] += ( (xValue - pointX) * -1 );
					poligon.getPontos().get(idx)[1] += ( (yValue - pointY) * -1 );
					poligon.updateBoundBox();
				}
			}
			else if ( selecionados != null )
			{
				for( Poligono poligon : selecionados )
				{
					for( float[] points : poligon.getPontos() )
					{
						poligon.resetBoundBox();
						points[0] += ( (xValue - pointX) * -1 );
						points[1] += ( (yValue - pointY) * -1 );
						poligon.updateBoundBox();
					}
				}
			}
		}
		else if ( this.getMode() == Mode.CIRCLE ) 
		{
			if( atual != null && atual.getPontos().size() == 1 )
			{
				float[] pointA = new float[]{ pointX , pointY };
				linha = new Poligono();
				linha.getPontos().add( pointA );
			}
		}
		
		xValue = pointX;
		yValue = pointY;
		
		if( glDrawable != null ) {
			glDrawable.display();
		}
	}
	
	public void keyPressed(KeyEvent e) {
		return;
	}
	
	public void mouseReleased(MouseEvent e) {
		return;
	}
	
	public void keyReleased(KeyEvent e) {
		return;
	}

	public void keyTyped(KeyEvent e) {
		return;
	}
	
	public void mouseClicked(MouseEvent e) {
		return;
	}
	
	public void mouseEntered(MouseEvent e) {
		return;
	}

	public void mouseExited(MouseEvent e) {
		return;
	}
	
	public void displayChanged(GLAutoDrawable glDrawable, boolean arg1, boolean arg2) {
		return;
	}

	public void reshape(GLAutoDrawable glDrawable, int windowX, int windowY, int maxX, int maxY) {
		return;
	}

	public EditorFrame getWindow() {
		return window;
	}

	public void setWindow(EditorFrame window) {
		this.window = window;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		
		if( mode == Mode.EXIT ) {
			System.exit(0);
		}
		
		this.mode = mode;
		this.cancelSelection();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
