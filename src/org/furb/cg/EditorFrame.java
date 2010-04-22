package org.furb.cg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JToolBar.Separator;
import javax.swing.border.BevelBorder;

import org.furb.cg.util.Base;
import org.furb.cg.util.ColorSelectionModel;
import org.furb.cg.util.Mode;

/**
 * Classe responsavel por montar a interface
 * grafica do programa, visivel para o usuario.
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public class EditorFrame extends JFrame {

	private static final long serialVersionUID = 3172688540921699213L;
	
	private GLCanvas canvas			= null;
	private Canvas jogl				= null;
	
	//Botoes da GUI
	private JButton		newDocument		= null;
	private JButton		openPoligon		= null;
	private JButton		closePoligon 	= null;
	private JButton		selectPolygon	= null;
    private JButton 	btCorPadrao		= null;
    private JButton 	btSair			= null;
    private JButton 	btCirculo		= null;
    private JButton 	btSpline		= null;
    private JLabel 		lbStatus		= null;
    private JToolBar 	pnBotoes		= null;
    private JPanel 		pnStatus		= null;
    private JButton		zoomIn			= null;
    private JButton		zoomOut			= null;
    private JButton		btBoundBox		= null;
    
    /**
     * Construtor da classe
     */
	public EditorFrame() 
	{
		final int screenWidth = 1000;
		final int screenHeight = 600;
		
		Base.getInstace().setScreenWidth(screenWidth);
		Base.getInstace().setScreenHeight(screenHeight);
		
		super.setTitle("[PolygonEditor] - FURB 2010 - Computacao Grafica");
		super.setMinimumSize(new Dimension(screenWidth, screenHeight));
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		super.getContentPane().setLayout(new BorderLayout());
		
		this.initJOGL();
		this.initGUI();
	}
	
	/**
	 * Inicializa a GUI criando seus botoes, adicionando
	 * imagens, montando os paineis.
	 */
	private void initGUI()
	{
        pnBotoes		= new JToolBar();
        btCirculo		= new JButton();
        btSpline		= new JButton();
        pnStatus		= new JPanel();
        btCorPadrao 	= new JButton();
        lbStatus		= new JLabel();
        btSair 			= new JButton();
        newDocument		= new JButton();
        closePoligon	= new JButton();
        openPoligon		= new JButton();
        selectPolygon	= new JButton();
        zoomIn			= new JButton();
        zoomOut			= new JButton();
        btBoundBox		= new JButton();
        
        jogl.setWindow(this);
        jogl.setMode(Mode.SELECTION);
        
        pnBotoes.setFloatable(false);
        pnBotoes.setOrientation(0);
        pnBotoes.setRollover(true);
        pnBotoes.setMinimumSize(new Dimension(100, 100));
        pnBotoes.setPreferredSize(new Dimension(40, 40));
        
        this.initButton("Novo documento",			newDocument,	Mode.DO_NOTHING,		"newDocument.png"	, false,	false	);
        this.initButton("Desenha poligono aberto",	openPoligon,	Mode.OPEN_POLYGON,		"openPolygon.png"	, true, 	true	);
        this.initButton("Desenha poligono fechado",	closePoligon,	Mode.CLOSE_POLYGON,		"closePolygon.png"	, true, 	true	);
        this.initButton("Desenhar um circulo",		btCirculo,		Mode.CIRCLE,			"circle.png"		, true, 	true	);
        this.initButton("Desenhar uma spline",		btSpline,		Mode.SPLINE,			"spline.png"		, true, 	true	);
        this.initButton("Selecionar poligono", 		selectPolygon, 	Mode.SELECTION,			"select.png" 		, true,		true	);
        this.initButton("Incrementar Zoom", 		zoomIn, 		Mode.DO_NOTHING,		"zoomIn.png"		, false,	false	);
        this.initButton("Decrementar Zoom", 		zoomOut, 		Mode.DO_NOTHING,		"zoomOut.png"		, false,	false	);
        this.initButton("Selecionar um cor",		btCorPadrao,	Mode.DO_NOTHING, 		"paint.png"			, false, 	false	);
        this.initButton("Exibir boundbox",			btBoundBox,		Mode.DO_NOTHING, 		"boundbox.png"		, false, 	false	);
        this.initButton("Finalizar o programa",		btSair, 	  	Mode.DO_NOTHING, 		"exit.png"			, true, 	true	);
        
        lbStatus.setText(" ");
        pnStatus.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pnStatus.setPreferredSize(new Dimension(20, 20));
        pnStatus.setLayout(new BorderLayout());
        pnStatus.add(lbStatus, BorderLayout.CENTER);
        
        super.getContentPane().add(pnBotoes, BorderLayout.NORTH);
        super.getContentPane().add(pnStatus, java.awt.BorderLayout.SOUTH);
        
        btCorPadrao.setBorder(BorderFactory.createEtchedBorder());
        btCorPadrao.setBackground(Color.BLACK);
        
        //Cria listener para exibir o texto descritivo da selecao de cor
        btCorPadrao.addMouseListener( new MouseListener() 
        {
    		public void mouseEntered(MouseEvent e) {
    			jogl.getWindow().setStatus("Selecione a cor de desenho");
			}

			public void mouseExited(MouseEvent e) {
				jogl.getWindow().setStatus("");
			}

			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
    	});
        
        //Cria listener para exibir a tela de selecao de cor
        btCorPadrao.addActionListener( new ActionListener() 
        {
    	    public void actionPerformed(ActionEvent e) 
    	    {
    	    	JColorChooser pane = new JColorChooser(jogl.getColor());
    	    	ColorSelectionModel csm = new ColorSelectionModel(pane);
				JDialog dialog = JColorChooser.createDialog( jogl.getWindow() , "Escolha a cor do pincel", true, pane, csm, null);
				dialog.setVisible(true);
    	    	Color cor = csm.getColor();
    	    	btCorPadrao.setBackground(cor);
    	    	jogl.setColor(cor);
    	    }
    	});
        
        //Cria listener para criar um novo documento
        newDocument.addActionListener( new ActionListener() 
        {
			public void actionPerformed(ActionEvent e) 
			{
				jogl.newDocument();
			}
		});

        //Cria listener para incrementar o zoom
        zoomIn.addActionListener( new ActionListener() 
        {
			public void actionPerformed(ActionEvent e) 
			{
				jogl.zoomIn();
			}
		});
        
        //Cria listener para decrementar o zoom
        zoomOut.addActionListener( new ActionListener() 
        {
			public void actionPerformed(ActionEvent e) 
			{
				jogl.zoomOut();
			}
		});
        
        btSair.addActionListener( new ActionListener() 
        {	
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
        
        btBoundBox.addActionListener( new ActionListener() 
        {	
			public void actionPerformed(ActionEvent e)
			{
				jogl.showBoundBox();
			}
		});

        super.pack();
	}
	
	/**
	 * Inicializa as classes e funcoes do JOGL
	 */
	private void initJOGL() 
	{
		GLCapabilities c = new GLCapabilities();
		c.setRedBits(8);
		c.setBlueBits(8);
		c.setGreenBits(8);
		c.setAlphaBits(8);
		
		jogl = new Canvas();
		this.canvas = new GLCanvas(c);
		this.getContentPane().add(canvas,BorderLayout.CENTER);
		canvas.addGLEventListener(jogl);        
		canvas.addKeyListener(jogl);
		canvas.addMouseListener(jogl);
		canvas.addMouseMotionListener(jogl);
		canvas.requestFocus();		
	}
	
	/**
	 * Cria os botoes na tela de acordo
	 * com o nome atributo, a operacao que o mesmo
	 * ira executar, a cor e se ele sera travado ou nao.
	 * @param hint
	 * @param button
	 * @param mode
	 * @param icon
	 * @param changeColor
	 * @param lock
	 */
    private void initButton(final String hint, JButton button, 
    		final Mode mode, String icon, final boolean changeColor, final boolean lock) 
    {
    	button.setContentAreaFilled(false);
    	button.setBorderPainted(false);
    	button.setText("");
    	button.setFocusable(false);
    	button.setPreferredSize(new Dimension(38,38));
    	button.setHorizontalTextPosition(SwingConstants.CENTER);
    	button.setVerticalTextPosition(SwingConstants.BOTTOM);
    	button.setIcon(new ImageIcon(getClass().getResource("/org/furb/cg/icons/"+icon)));
    	pnBotoes.add(button);
    	pnBotoes.add( new Separator() );
    	
    	button.addMouseListener(new MouseListener() 
    	{
    		public void mouseEntered(MouseEvent e) {
				jogl.getWindow().setStatus(hint);
			}

			public void mouseExited(MouseEvent e) {
				jogl.getWindow().setStatus("");
			}

			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
    	});
    	
    	button.addActionListener( new ActionListener() 
    	{
    	    public void actionPerformed(ActionEvent e) 
    	    {
    	        jogl.setMode(mode);
    	        Color def = ((JButton)e.getSource()).getBackground();
    	        
    	        openPoligon.setBackground(def);
    	        openPoligon.setEnabled(true);
    	        
    	        closePoligon.setBackground(def);
    	        closePoligon.setEnabled(true);
    	        
    	        selectPolygon.setBackground(def);
    	        selectPolygon.setEnabled(true);
    	        
    	        btCirculo.setBackground(def);
    	        btCirculo.setEnabled(true);
    	        
    	        btSpline.setBackground(def);
    	        btSpline.setEnabled(true);
    	        
    	        zoomIn.setBackground(def);
    	        zoomIn.setEnabled(true);
    	        
    	        zoomOut.setBackground(def);
    	        zoomOut.setEnabled(true);
    	        
    	        if( changeColor ) {
    	        	((JButton)e.getSource()).setBackground(Color.DARK_GRAY);
    	        }
    	        
    	        if( lock) {
    	        	((JButton)e.getSource()).setEnabled(false);
    	        }
    	    }
    	});
	}
	
    //GETTERS E SETTERS
    
	public GLCanvas getCanvas() {
		return canvas;
	}
	
    public void setStatus( String status ) {
    	lbStatus.setText(" " + status );	
    }
}
