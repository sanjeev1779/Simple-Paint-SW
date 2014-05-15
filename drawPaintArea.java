/*
 * It draws the paint area for painting. Used Flood fill algorithm for bucket fill.
 */
package paintsw;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author cyber
 */
public class drawPaintArea extends JPanel implements MouseListener, ActionListener,MouseMotionListener {
    
    public int x1,y1,x2,y2;
    private static boolean istext= false;
    private Dimension paint_area_size= new Dimension(700,100);
    public static Cursor cursor_type= new Cursor(Cursor.CROSSHAIR_CURSOR); 
    public boolean ispressed = false; // initially set false
    public boolean isdragged= false; // initially set false
    static BufferedImage image_set;
    public drawPaintArea()
    {
        //creating the paint area panel
        setBackground(Color.white);
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(paint_area_size);
        setCursor(cursor_type);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));        
    }
    
    // creating the graphics and paint component of the application
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        if(image_set==null )
        {
            int w= this.getWidth();
            int h = this.getHeight()+100;
            image_set= (BufferedImage)this.createImage(w,h);
            Graphics2D gc= image_set.createGraphics();
            gc.setColor(Color.white);
            gc.fillRect(0,0,w,h); // set the background and its color as white
            
        }
        g2.drawImage(image_set, null, 0,0);
        if(isdragged)
            drawShape(g2);
            
    } // paint component ended
    
    private void drawShape(Graphics2D g2)
    {
       g2.setColor(ToolBarMenu.getSelectedForeColor());
       if(ToolBarMenu.getSelectedTool()!=5 && ToolBarMenu.getSelectedForeColor()==Color.WHITE)
           g2.setColor(Color.BLACK);
       g2.setStroke( new BasicStroke(ToolBarMenu.getStrokeSize()));
      
     //  String drawing_tools[]={"pencil","line-tool","rectangle","oval","polygon","eraser","text","rectangle_fill","oval_fill","polygon_fill","bucket"};
      
       switch(ToolBarMenu.getSelectedTool())
       {
            case 0:
            {
                g2.drawLine(x1, y1, x2, y2);
                x1=x2;
                y1=y2;
                break;
            }
            case 1:
            {
                g2.drawLine(x1, y1, x2, y2);
                break;
            }
            case 2: 
            case 4:
            case 7: 
            case 9: 
            case 3: 
            case 8:
              {
                int x=x1,y=y1;
                                 if(x2>x1 &&y2>y1) ;
                                 else  if(x2>x1 && y2 <y1)
                                   y=y2;
                                 else if(x2<x1 && y2 >y1)
                                         x=x2;
                                 else
                                         {x=x2;y=y2;}
                                 
                                 if(ToolBarMenu.getSelectedTool()==2)
                                 {
                                     g2.drawRect(x, y, Math.abs(x1-x2), Math.abs(y2-y1));
                                     //repaint();
                                 }
				 if(ToolBarMenu.getSelectedTool()==7)
                                 {
                                     g2.fillRect(x, y, Math.abs(x1-x2), Math.abs(y2-y1));
                                     
                                 }
				 if(ToolBarMenu.getSelectedTool()==9)
					 g2.fillRoundRect(x, y,Math.abs(x2-x1), Math.abs(y2-y1), 10, 10);
				 if(ToolBarMenu.getSelectedTool()==4) 
					 g2.drawRoundRect(x, y,Math.abs(x2-x1), Math.abs(y2-y1), 10, 10);

				 if(ToolBarMenu.getSelectedTool()==3)
					 g2.drawOval(x, y, Math.abs(x2-x1), Math.abs(y2-y1));
				 if(ToolBarMenu.getSelectedTool()==8)
					 g2.fillOval(x, y, Math.abs(x2-x1), Math.abs(y2-y1));
                                 
                                 break;
              }
            case 5: //eraser
            {
                ToolBarMenu.setForeColor(Color.WHITE);
                g2.setColor(Color.WHITE);
                
                g2.drawLine(x1, y1, x2, y2);
                x1=x2;
                y1=y2;
                break;
            }
            
            case 6: // text area
            {
                isdragged= false;
                istext=true;
                String str= JOptionPane.showInputDialog(this,"Enter Text Here");
                String str1 =JOptionPane.showInputDialog(this,"Enter Font size");
                int font_size;
                if(str1.equals(""))
                    font_size=24;
                else 
                font_size= Integer.parseInt(str1);
                
                g2.setFont(new Font("Arial", Font.PLAIN,font_size));
                g2.drawString(str, x1, y1);
                istext= false;
                
                int x= ToolBarMenu.getSelectedTool();
                break;
            }
            case 10:
                image_set = fill(image_set,x1,y1,ToolBarMenu.getSelectedForeColor());
                break;
            default:
                g2.drawLine(x1, y1, x2, y2);
                x1=x2;
                y1=y2;
                
         } // switch statement ends
               
    
            
        } // draw Shape ends here
    
    // mouse events
    @Override
    public void mouseDragged(MouseEvent me)
    {
        isdragged= true;
        x2 =me.getX();
        y2 =me.getY();
        if(ToolBarMenu.getSelectedTool()==0 || ToolBarMenu.getSelectedTool()==5)
        {
            drawShape(image_set.createGraphics());
        }
        this.repaint();
    }// mouse dragged end
    
   

    @Override
    public void mouseClicked(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     @Override
	public void mousePressed(MouseEvent me) {
		x1=me.getX();
		y1=me.getY();
		ispressed=true;
	}
    @Override
	public void mouseReleased(MouseEvent me) {
		isdragged=false;
		ispressed=false;
		x2=me.getX();
		y2=me.getY();
		drawShape(image_set.createGraphics());
		this.repaint();
    }
    // flood fill algorithm for filling the bounded area 
    	public BufferedImage fill(Image img, int xSeed, int ySeed, Color col)
	  {
	    BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    bi.getGraphics().drawImage(img, 0, 0, null);
	    int x = xSeed;
	    int y = ySeed;
	    int width = bi.getWidth();
	    int height = bi.getHeight();

	    DataBufferInt data = (DataBufferInt) (bi.getRaster().getDataBuffer());
	    int[] pixels = data.getData();

	    if (x >= 0 && x < width && y >= 0 && y < height)
	    {

	      int oldColor = pixels[y * width + x];
	      int fillColor = col.getRGB();

	      if (oldColor != fillColor)
	      {
	        floodIt(pixels, x, y, width, height, oldColor, fillColor);
	      }
	    }
	    return bi;
	  }


	  private void floodIt(int[] pixels, int x, int y, int width, int height, int oldColor, int fillColor)
	  {

	    int[] point = new int[] { x, y };
	    LinkedList<int[]> points = new LinkedList<int[]>();
	    points.addFirst(point);

	    while (!points.isEmpty())
	    {
	      point = points.remove();

	      x = point[0];
	      y = point[1];
	      int xr = x;

	      int yp = y * width;
	      int ypp = yp + width;
	      int ypm = yp - width;

	      do
	      {
	        pixels[xr + yp] = fillColor;
	        xr++;
	      }
	      while (xr < width && pixels[xr + y * width] == oldColor);

	      int xl = x;
	      do
	      {
	        pixels[xl + yp] = fillColor;
	        xl--;
	      }
	      while (xl >= 0 && pixels[xl + y * width] == oldColor);

	      xr--;
	      xl++;

	      boolean upLine = false;
	      boolean downLine = false;

	      for (int xi = xl; xi <= xr; xi++)
	      {
	        if (y > 0 && pixels[xi + ypm] == oldColor && !upLine)
	        {
	          points.addFirst(new int[] { xi, y - 1 });
	          upLine = true;
	        }
	        else
	        {
	          upLine = false;
	        }
	        if (y < height - 1 && pixels[xi + ypp] == oldColor && !downLine)
	        {
	          points.addFirst(new int[] { xi, y + 1 });
	          downLine = true;
	        }
	        else
	        {
	          downLine = false;
	        }
	      }
	    }
	  }
          
          // flood fill algorithm ends here
}// main 1st bracket ends here
    

