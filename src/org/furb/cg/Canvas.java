package org.furb.cg;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.furb.cg.model.Poligono;
import org.furb.cg.model.Ponto;
import org.furb.cg.render.BoundBox;
import org.furb.cg.render.Circle;
import org.furb.cg.render.PreviewLine;
import org.furb.cg.render.Spline;
import org.furb.cg.util.Base;
import org.furb.cg.util.Mode;
import org.furb.cg.util.MouseCursor;
import org.furb.cg.util.ScanLine;
import org.furb.cg.util.Transform;

/**
 * Classe canvas responsavel por todo o
 * processo de renderizacao da tela, atualizacao
 * das variaveis, intercaptacao dos movimentos
 * do mouse, etc.
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public class Canvas implements GLEventListener, KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
	
	private GL				gl				= null;
	private GLU				glu				= null;
	private GLAutoDrawable	glDrawable		= null;
	private Mode			mode			= null;
	private Color			color			= Color.BLACK;
	private boolean			showBoundBox	= false;
	
	private boolean 		isRotating 		= false;
	private boolean 		isScaling 		= false;

	private double			left;
	private double			right;
	private double			bottom;
	private double			top;

	private List<Poligono>	poligonos			= new ArrayList<Poligono>();
	private List<Poligono>	selecionados		= new ArrayList<Poligono>();
	private Poligono		atual				= null;
	private Poligono		linha				= null;
	
	private double 			scale				= 1.0f;
	private double 			panX				= 0.0f;
	private double 			panY				= 0.0f;
	private double			xValue				= 0.0f;
	private double			yValue				= 0.0f;
	
	//Tipos de polígonos
	private Spline spline = null;
	private BoundBox boundBox = null;
	private Circle circle = null;
	private PreviewLine line = null;
	
	public Canvas()
	{
		left	=  0.0f;
		bottom	=  0.0f;
		right	=  1000;
		top		=  600;
		
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
	public void init(GLAutoDrawable drawable)
	{
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		//inicializa os objetos que representam os tipos de polígonos
		spline = new Spline(gl);
		boundBox = new BoundBox(gl);
		circle = new Circle(gl);
		line = new PreviewLine(gl);
	}

	public void updateDimensions(double width, double height)
	{
		right = width;
		top = height;

		Base.getInstace().setScreenWidth(right);
		Base.getInstace().setScreenHeight(top);
		
		Base.getInstace().setRight(right);
		Base.getInstace().setTop(top);
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

		 glu.gluOrtho2D((left * scale) + panX, (right * scale)  + panX, (bottom * scale) + panY, (top * scale) + panY);
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
		
		Mode poligonMode;
		
		for( Poligono poligon : poligonos )
		{
			//Seta o cor do poligono a ser renderizado
			color = poligon.getColor();
			
			//verifica se foi selecionada alguma cor previamente
			if(color != null)
			{
				final double red	= color.getRed()   / 255.0;
				final double green	= color.getGreen() / 255.0;
				final double blue 	= color.getBlue()  / 255.0;
				gl.glColor3d(red,green ,blue);
				
			}
			poligonMode = poligon.getMode();
			
			poligon.setRotate(isRotating);
			poligon.setScale(isScaling);

			switch(poligonMode)
			{
				case SPLINE: {
					
					this.spline.draw(poligon);
					break;
				}
				
				default: {
					
					switch(poligonMode)
					{
						case CIRCLE: { 
							
						}
						
						case CLOSE_POLYGON: {
							
							gl.glBegin(GL.GL_LINE_LOOP);
							break;
						}
							
						default: {
							
							gl.glBegin(GL.GL_LINE_STRIP);
							break;
						}
					}

					for( Ponto pontos : poligon.getPontos() )
					{
						final double pontoX = pontos.getX();
						final double pontoY = pontos.getY();
						gl.glVertex2d(pontoX, pontoY);
					}
					
					gl.glEnd();
					
					break;
				}
			}
			
			if( poligon.isSelected() ) 
			{
				boundBox.drawOnSelctedPoligons(poligon, this.getMode());
			}
			
			if( showBoundBox )
			{
				boundBox.draw(poligon);
			}
		}
		
		if( linha != null )
		{
			this.line.draw(linha);
		}
		
		if( atual != null )
		{
			this.circle.drawPreview(atual, linha);
		}
		
		isRotating = false;
		isScaling = false;
	}

	/**
	 * Adiciona um ponto na lista de poligonos
	 * atualizando a boundbox do poligono.
	 * @param x
	 * @param y
	 */
	private void addPoint(double x, double y)
	{
		if( atual == null ) {
			this.addPoligon();
		}
		
		atual.setColor( this.getColor() );
		atual.getPontos().add( new Ponto( x, y ) );
		atual.updateBoundBox();
		
		//Se clicou novamente e for o modo circulo
		if( atual.getMode() == Mode.CIRCLE )
		{
			if( atual.getPontos().size() == 2 )
			{
				this.circle.draw(atual);
				this.cancelSelection();
			}
		}
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
		panX	= 0.0f;
		panY	= 0.0f;
		atual	= null;
		mode	= Mode.SELECTION;
		
		Base.getInstace().setScale(scale);
		Base.getInstace().setPanX(panX);
		Base.getInstace().setPanY(panY);
		
		this.refreshRender();
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
		
		this.refreshRender();
	}
	
	/**
	 * Metodo chamado para cancelar a selecao
	 * de um objeto
	 */
	public void cancelSelection() 
	{
		linha	= null;
		atual	= null;
		
		this.refreshRender();
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
		
		Base.getInstace().setScale(scale);
		
		this.refreshRender();
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
		
		Base.getInstace().setScale(scale);
		
		this.refreshRender();
	}
	
	/**
	 * Que define se a boundbox dos 
	 * polignos sera exibida ou nao
	 */
	public void showBoundBox()
	{
		this.showBoundBox = !this.showBoundBox;
	}
	
	/**
	 * Finaliza um poligono aberto ao
	 * pressionar a tecla 'F'
	 */
	private void closeOpenedPoligon()
	{
		if( atual != null )
		{
			Ponto pointA = atual.getPontos().get(0);
			this.addPoint(pointA.getX(), pointA.getY());
			cancelSelection();
		}
	}
	
	/**
	 * Metodo seguro para re-renderizar toda
	 * a tela, verificando se o metodo nao
	 * esta null;
	 */
	private void refreshRender()
	{
		if( glDrawable != null ) {
			glDrawable.display();
		}
	}
	
	/**
	 * Metodo utilizado para deletar
	 * os poligonos selecionados pelo usuario
	 */
	private void deleteSelectedObjects()
	{
		for( Poligono poligon : selecionados )
		{
			poligonos.remove(poligon);
		}
		
		selecionados.clear();
		this.refreshRender();
	}
	
	/**
	 * Metodo chamado pelo listener quando o
	 * usuario pressionar um botao do mouse.
	 */
	public void mousePressed(MouseEvent e)
	{
		if(this.mode == Mode.PAN){
			MouseCursor.getInstace().setCloseHandCursor();
		}else{
			MouseCursor.getInstace().setNormalCursor();
		}
		
		//Verificar selecao ou adicionar ponto
		final double pointX = Base.getInstace().normalizarX( Double.valueOf( e.getX() ) );
		final double pointY = Base.getInstace().normalizarY( Double.valueOf( e.getY() ) );
		
		if( e.getButton() == MouseEvent.BUTTON1 ) 
		{
			switch( this.getMode() )
			{
				case PAN:  {
					xValue = pointX;
					yValue = pointY;
					break;
				}
				
				case SCALE: {
					
				}
				
				case ROTATE: {
					
				}
				
				case MOVE_POINT: {
					
				}
				
				case SELECTION: {
					selecionados = ScanLine.getInstace().intersectionCheck(poligonos, pointX, pointY);
					xValue = pointX;
					yValue = pointY;
					break;
				}
				
				case DO_NOTHING: {
					break;
				}
				
				default: {
					this.addPoint(pointX, pointY);	
					break;
				}
			}
			
			//Remove um ponto quando clicado 2x sobre o mesmo
			if( e.getClickCount() >= 2 )
			{
				//Percorre todos os poligonos
				for( Poligono pol : selecionados )
				{
					List<Ponto> pontos = new ArrayList<Ponto>( pol.getPontos() );
					
					//Percorre todos os pontos
					for( Ponto pt : pol.getPontos() )
					{
						//Se for o selecionado, remove
						if( pt.isSelected() ) 
						{
							pontos.remove(pt);
						}
					}
					
					//Seta a nova lista de pontos
					pol.setPontos( pontos );
				}
			}
		} 
		else if ( e.getButton() == MouseEvent.BUTTON3 ) 
		{
			this.cancelSelection();
		}
		
		this.refreshRender();
	}
	
	/**
	 * Metodo chamado pelo listener quando o
	 * usuario mover o mouse.
	 */
	public void mouseMoved(MouseEvent e) 
	{
		
		final double pointX = Base.getInstace().normalizarX( Double.valueOf( e.getX() ) );
		final double pointY = Base.getInstace().normalizarY( Double.valueOf( e.getY() ) );
		
		final MessageFormat mf = new MessageFormat("({0},{1})");
		Base.getInstace().getWindow().setStatus( mf.format( new Object[]{ Math.round(pointX) , Math.round(pointY) } ) );

		if( atual != null && !atual.getPontos().isEmpty() )
		{

			Ponto pointA = null;
			Ponto pointB = null;
			
			switch( this.getMode() )
			{
				case OPEN_POLYGON: {
					
					pointA = atual.getPontos().get( atual.getPontos().size() -1 );
					pointB = new Ponto( pointX , pointY );
					linha = new Poligono();
					linha.getPontos().add(pointA);
					linha.getPontos().add(pointB);
					break;
				}
				
				case CLOSE_POLYGON: {
					
					if( atual.getPontos().size() == 1 ) 
					{
						pointA = atual.getPontos().get( atual.getPontos().size() -1 );
						pointB = new Ponto( pointX , pointY );
						linha = new Poligono();
						linha.getPontos().add(pointA);
						linha.getPontos().add(pointB);
					}
					else if( atual.getPontos().size() == 2 )
					{
						pointA = atual.getPontos().get(0);
						pointB = atual.getPontos().get(1);
						Ponto pointC = new Ponto( pointX , pointY );
						linha = new Poligono();
						linha.getPontos().add(pointA);
						linha.getPontos().add(pointB);
						linha.getPontos().add(pointC);
					}
					else 
					{
						pointA = atual.getPontos().get(0);
						pointB = atual.getPontos().get(1);
						Ponto pointC = atual.getPontos().get( atual.getPontos().size() -1 );
						Ponto pointD = new Ponto( pointX , pointY );
						linha = new Poligono();
						linha.getPontos().add(pointA);
						linha.getPontos().add(pointB);
						linha.getPontos().add(pointC);
						linha.getPontos().add(pointD);
					}
					break;
				}
					
				case CIRCLE: {
					
					if( atual.getPontos().size() == 1 )
					{
						pointA = new Ponto( pointX , pointY );
						linha = new Poligono();
						linha.getPontos().add( pointA );
					}
					break;
				}
			}
		}

		this.refreshRender();
		
	}
	
	/**
	 * Metodo chamado quando o usuario clica e arrasta
	 * o ponteiro do mouse, realizando o movimento
	 * de clicar e arrastar
	 */
	public void mouseDragged(MouseEvent e) 
	{
		final double pointX = Base.getInstace().normalizarX( Double.valueOf( e.getX() ) );
		final double pointY = Base.getInstace().normalizarY( Double.valueOf( e.getY() ) );
		
		final MessageFormat mf = new MessageFormat("({0},{1})");
		Base.getInstace().getWindow().setStatus( mf.format( new Object[]{ Math.round(pointX) , Math.round(pointY) } ) );
		
		switch(this.getMode())
		{
			case SELECTION: {
				
				if ( selecionados != null )
				{
					for( Poligono poligon : selecionados )
					{
						for( Ponto points : poligon.getPontos() )
						{
							poligon.resetBoundBox();
							points.setX( points.getX() + ( (xValue - pointX) * -1 ) );
							points.setY( points.getY() + ( (yValue - pointY) * -1 ) );
							poligon.updateBoundBox();
						}
					}
				}
				break;
			}
			
			case MOVE_POINT: {
				
				for( Poligono poligon : selecionados )
				{
					for( Ponto points : poligon.getPontos() )
					{
						if( points.isSelected() )
						{
							poligon.resetBoundBox();
							points.setX( points.getX() + ( (xValue - pointX) * -1 ) );
							points.setY( points.getY() + ( (yValue - pointY) * -1 ) );
							poligon.updateBoundBox();		
						}
					}
				}
				
				break;
			}
			
			case PAN: {
				
				panX +=	((xValue - pointX) * 0.50 );
				panY += ((yValue - pointY) * 0.50 ) ;
				
				Base.getInstace().setPanX(panX);
				Base.getInstace().setPanY(panY);
				
				break;
			}
			
			case CIRCLE: {
				
				if( atual != null && atual.getPontos().size() == 1 )
				{
					Ponto pointA = new Ponto( pointX , pointY );
					linha = new Poligono();
					linha.getPontos().add( pointA );
				}
				break;
			}
		}
		
		xValue = pointX;
		yValue = pointY;
		
		this.refreshRender();
	}
	
	/**
	 * Listener chamado quando um usuario
	 * pressiona uma tecla do seu teclado
	 * e/ou mouse.
	 */
	public void keyPressed(KeyEvent e) 
	{
		final int key = e.getKeyCode();
		final boolean closePoligon = ( key == KeyEvent.VK_F );
		final boolean deleteObject = ( key == KeyEvent.VK_DELETE || key == KeyEvent.VK_BACK_SPACE );
		
		//Se for modo Poligono aberto e o usuario pressionar a tecla F
		if( this.getMode() == Mode.OPEN_POLYGON && closePoligon )
		{
			//Fecha o poligno com o ponto inicial
			this.closeOpenedPoligon();
		}
		//Caso o usuario pressione o botao delete
		else if ( deleteObject )
		{
			//Exclui todos os objetos selcionados
			this.deleteSelectedObjects();
		}
		
		return;
	}
	
	/**
	 * Listener chamado quando o usuario
	 * gira o scroll do mouse
	 */
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		boolean refresh = false;
		int notches = e.getWheelRotation();
		
		switch(this.getMode())
		{
			case ROTATE: {
				
				double angle = 5;

		        if (notches < 0) {
		        	angle = angle * -1;
		        }

		        Transform.getInstace().setAngle(angle);
		        
		        isRotating = true;
				refresh = true;
				break;
			}
				
			case SCALE: {
				
				double scale;
				
		        if (notches < 0) {
		        	scale = 0.6;
		        }else{
		        	scale = 1.4;
		        }

				Transform.getInstace().setScale(scale);
				
				isScaling = true;
				refresh = true;
				break;
			}
		}
		
		if(refresh) {
			refreshRender();
		}
	}
	
	public void mouseReleased(MouseEvent e) 
	{
		if( this.mode == Mode.PAN ){
			MouseCursor.getInstace().setOpenHandCursor();
		}else{
			MouseCursor.getInstace().setNormalCursor();
		}
		
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

	public void reshape(GLAutoDrawable glDrawable, int windowX, int windowY, int width, int height) {
		this.updateDimensions(width, height);
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode)
	{
		this.mode = mode;
		
		if(this.mode  == Mode.PAN){
			MouseCursor.getInstace().setOpenHandCursor();
		}else{
			MouseCursor.getInstace().setNormalCursor();
		}
		
		this.cancelSelection();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
