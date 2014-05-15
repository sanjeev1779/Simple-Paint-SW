/*
 * 
     Create the toolbar and menubar in the frame.
     Select the tools by using mouse cursor.
 */
 
package paintsw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author cyber
 */
public class ToolBarMenu extends JPanel implements MouseListener, ActionListener, ChangeListener
{
    private  BufferedImage img= null;
   private static int draw_tool=0;
   private static int pencil_size=3;
    private static Dimension size1 = new Dimension(40,40);
    
    private static Dimension size_of_colorbox = new Dimension(210,210);
    private static Dimension size_of_select_color = new Dimension(45,45);
    private static Color front_main_color[]= {Color.BLACK,Color.GREEN,Color.gray,Color.RED,Color.ORANGE,Color.BLUE,Color.PINK,Color.WHITE,Color.YELLOW,Color.MAGENTA,Color.DARK_GRAY,Color.cyan};
    private static Color for_color= front_main_color[0];
    private static int front_color_length= front_main_color.length;
    private JPanel select_color= new JPanel();
    public ToolBarMenu() throws IOException
    {
        setPreferredSize(size_of_colorbox);
        
        
        // color chooser starts here
        select_color.setPreferredSize(size_of_select_color);
        select_color.addMouseListener(this);
        select_color.setToolTipText("Selected Color");
        select_color.setName("C"+front_color_length);
        select_color.setBackground(Color.black);
        select_color.setBorder(BorderFactory.createLineBorder(Color.red,5));
        JPanel main_color= new JPanel(new GridLayout(1,1));
        JPanel front_color= new JPanel(new GridLayout(3,1,4,4));
        front_color.add(select_color);  
        
        JLabel color_label = new JLabel("Main Color");
        color_label.setFont(new Font("Hell",Font.ITALIC,24));
        main_color.setBorder(BorderFactory.createLineBorder(Color.black,2));
        
        //create an array of panels for each color that is shown in the main window of the paint application
        JPanel panel_for_main_color[]= new JPanel[front_color_length];
        
        int panel_for_main_color_length= panel_for_main_color.length;
        int i = 0;
        while(i<panel_for_main_color_length)
        {
        
            panel_for_main_color[i]= new JPanel();
            panel_for_main_color[i].setPreferredSize(size1);
           panel_for_main_color[i].setToolTipText(front_main_color[i].toString());
            panel_for_main_color[i].setBackground(front_main_color[i]);
            panel_for_main_color[i].addMouseListener(this);
            panel_for_main_color[i].setName("F"+Integer.toString(i));
            front_color.add(panel_for_main_color[i]);
            i=i+1;    
        }
        main_color.add(front_color); // color added in the window
        add(front_color); // color added in the main panel
                
        // end of the color panel 
        // from here drawing tool panel starts
        
       String drawing_tools[]={"pencil","line-tool","rectangle","oval","polygon","eraser","text","rectangle_fill","oval_fill","polygon_fill","bucket"};
        int drawing_tools_length= drawing_tools.length;
        JPanel paint_tool= new JPanel(new GridLayout(3,2));
        JPanel paint_tool_panel= new JPanel(new GridLayout(2,1));
        JPanel tools_panel[]= new JPanel[drawing_tools_length];
        i=0;
        while(i<drawing_tools_length)
        {
            tools_panel[i]= new JPanel();
            img=null;
                try {
                    // link in the file directory :  "C:\Users\cyber\Documents\NetBeansProjects\paintSW\src"
                    img = ImageIO.read(new File("C://Users//cyber//Documents//NetBeansProjects//paintSW//src//images//"+drawing_tools[i]+".png"));
                    } catch (IOException e) {
				System.out.println("Unable to load file "+e);
                    }
                
                    Image sized_image= img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    
            JLabel drawing_pic_label= new JLabel(new ImageIcon(sized_image));        
            drawing_pic_label.setSize(11,11);
            tools_panel[i].add(drawing_pic_label,"Center");
            tools_panel[i].setName("T"+Integer.toString(i));
            tools_panel[i].addMouseListener(this);
            tools_panel[i].setToolTipText(drawing_tools[i]);
            tools_panel[i].setBorder(BorderFactory.createLineBorder(Color.black));
            paint_tool_panel.add(tools_panel[i]);
            i++;
        }      
   
        JLabel pick_tools= new JLabel("Pick Tools");
        pick_tools.setFont(new Font("Dialog",Font.BOLD,22));
        
        paint_tool.add(paint_tool_panel); // paint tool panel addein tools list
        add(paint_tool); // paint tool added in the tool frame
        
        // tool picker implementation ends
    
        
        // slider for size of changing pencil, eraser size
        JLabel sliderLabel = new JLabel("Stroke Size", JLabel.CENTER);
        
        JSlider stroke_size= new JSlider(JSlider.HORIZONTAL,1,20,2);
        add(sliderLabel);
        add(stroke_size);
        stroke_size.addChangeListener(this);
        stroke_size.setMajorTickSpacing(19);
        stroke_size.setMinorTickSpacing(1);
        stroke_size.setPaintTicks(true);
	stroke_size.setPaintLabels(true);
	Font font = new Font("Serif", Font.ITALIC, 15);
	stroke_size.setFont(font);
// stroke slider ended
        
       
        
        // 
        
    }
    
    // color changed function
     public void changeForeColor(int color)
        {
            for_color= front_main_color[color];
            System.out.println("new color:"+ for_color);
            select_color.setBackground(for_color);
        }
     // tool changing function
     public void changeTool(int tool)
     {
         if(tool==5) changeForeColor(7); // erasing tool pressed
         draw_tool= tool;
         System.out.println("New draw tool= "+ tool);            
     }
     
     // mouse event 
    @Override
	public void mouseClicked(MouseEvent me) {

		if(me.getComponent().getName()!=null && me.getComponent().getName().charAt(0)=='F') // foreground color
		{
			changeForeColor(Integer.parseInt(me.getComponent().getName().substring(1))); }
		else if(me.getComponent().getName()!=null && me.getComponent().getName().charAt(0)=='T') // Tools color
			changeTool(Integer.parseInt(me.getComponent().getName().substring(1)));
		else if(me.getComponent().getName()!=null && me.getComponent().getName().charAt(0)=='P') // Pencil color
			;
		
		else if(me.getComponent().getName()!=null && me.getComponent().getName().charAt(0)=='C'){
			colorchooser();
		}
	}
    
    // color chooser calling (inbuilt function)
     private void colorchooser()
     {
         for_color= JColorChooser.showDialog(this, "Please select a color", for_color);
         select_color.setBackground(for_color);
         System.out.println("new color changed= "+for_color);
     }
     
     public static Color getSelectedForeColor(){
		return for_color;
	}
     
     public static int getSelectedTool(){
		return draw_tool;
	}
     
     public static int getStrokeSize(){
		return pencil_size;
	}
    public static void setForeColor(Color c){
		for_color=c;
	}
    
    @Override
	public void mouseEntered(MouseEvent me) {}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}
        	public void mouseMoved(MouseEvent mm) {
		//if(draggable)
		{
			System.out.println("Moved: "+ mm.getX()+ ": "+ mm.getY());
		}
	}

                
                // added by java netbeans (need to remove)
    @Override
    public void mousePressed(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stateChanged(ChangeEvent e) {
            JSlider slider=(JSlider)e.getSource();
		System.out.println("Stroke Size Changed"+slider.getValue());
		pencil_size=slider.getValue();
        
       
    }
    
    
    // end of netbeans default adding
    

}
