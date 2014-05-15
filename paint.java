/*
	Author:sanjeev1779
 	IIT Jodhpur

*/

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paintsw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cyber
 */
public class paint extends JFrame implements ActionListener {
    
    public JMenuBar menubar;
    public JMenu file;
    public JMenu edit;
    public JMenu help;
    public JMenuItem new_file;
    public JMenuItem open_file;
    public JMenuItem save_file;
    public JMenuItem exit_file;
    
    public paint() throws IOException
    {
       create_paint_frame();
       create_topmenubar();
       create_paint_area();
      
       //create_tool_icons();
    }
    
    
    //create the main window of the application
    public void create_paint_frame()
    {
        setTitle("Paint");
	setLayout(new BorderLayout());
        setSize(1200,700);
        setVisible(true); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE); 
         //setting of windows look and feel=  style of the paint window
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            System.out.println("Unable to set Look and feel : "+e);             
        }
        
         setIconImage(Toolkit.getDefaultToolkit().getImage("C://Users//cyber//Documents//NetBeansProjects//Paint//src//image//icon.png"));
    }// end of the create paint frame
    
    // create the menu bar containing file, edit, help menus at the top
    
    // create the paint area
    // this is the main of the program 
    public void create_paint_area() throws IOException
    {
        paint_panel_gui gui= new paint_panel_gui();
        add(gui);
        
    }
    public void create_topmenubar()
    {
        menubar= new JMenuBar();
      
        // adding menus in the menubar
        file= new JMenu("File");
        menubar.add(file);
        file.setMnemonic('f');
        
        edit = new JMenu("Edit");
        menubar.add(edit);
        file.setMnemonic('e'); 
        
        
        help = new JMenu("Help");
        menubar.add(help);
        file.setMnemonic('h');
        
        JMenuItem about= new JMenuItem("About");
        help.add(about);
       
       about.addActionListener(new ActionListener()
       {
       	@Override
       	public void actionPerformed(ActionEvent ae)
       	{
       		JOptionPane.showMessageDialog(null, "Paint V 1.0-\n Made By Santosh \n Sanjeev \n Course: SW");
       	}
       });
        //ended menus in the menubar
        
        //adding menu items in the menus
        
       
        new_file= new JMenuItem("New File");
        new_file.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        file.add(new_file);
        new_file.addActionListener(this);
        
        save_file= new JMenuItem("Save File");
        save_file.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        file.add(save_file);
        save_file.addActionListener(this);
        
        open_file= new JMenuItem("Open File");
        open_file.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        file.add(open_file);
        open_file.addActionListener(this);
        
        exit_file= new JMenuItem("Exit File");
        file.add(exit_file);
        exit_file.addActionListener(this);
        exit_file.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
        // end of the menu items adding
        
         setJMenuBar(menubar);
        
        
        
       
        
       
        
       
    
    
    }// end of the top menu bar and its functionality
 //               -------------------------------closing of constructor--------------------------------------------
        
           //Adding action event in all the menu Items . That is, adding actionPerformed
           
           //start of action performed
           @Override
           public void actionPerformed(ActionEvent e)
           {//opening of action performed
               
             //  all the methods for action Event are defined later in the same source code
               //action for file menu
               if(e.getSource()==new_file)
               {
                   System.out.println("skndknskd");
                   new_file();
               }
               if(e.getSource()==open_file)
                   open_file();
               if(e.getSource()==save_file)
               {
                   System.out.println("hi");
                   save_file();
               }
               if(e.getSource()==exit_file)
                   exit_file();
          } // ending of the action performed calling
           
           // now time to declare all the fnctions
        
           // save_file function
           public void save_file()
         {
             JFileChooser fc= new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.addChoosableFileFilter(new FileNameExtensionFilter("PNG (.png)", "png"));
                fc.addChoosableFileFilter(new FileNameExtensionFilter("JPG (.jpg)", "jpg"));
                fc.addChoosableFileFilter(new FileNameExtensionFilter("JPEG (.jpeg)", "jpeg"));
                fc.setFileFilter(fc.getChoosableFileFilters()[1]);
                int r= fc.showSaveDialog(this);
                
                if(r==JFileChooser.CANCEL_OPTION)
                    return;
                
                File myfile = fc.getSelectedFile();
                if(myfile.equals("")||myfile.getName().equals(""))
                {
                    JOptionPane.showMessageDialog(this,"Please Select A File ","Error !",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(myfile.exists())
                {
              
                 r = JOptionPane.showConfirmDialog(null," A file of this name exists. \n Do you want to overwrite it?");
                    if(r!=0)
                        return;
                }
              setTitle(myfile.getName());
                            BufferedImage bi= drawPaintArea.image_set;
                           
				int w= bi.getWidth();
				int h= bi.getHeight();
				
				
				String ext= myfile.getName().substring(myfile.getName().lastIndexOf('.')+1);
				//JOptionPane.showMessageDialog(mf,ext);
				if(ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")  )
				{
				try
				{
					ImageIO.write(bi, ext, myfile);
                                        
				}
				
				catch(IOException ioe){
				 	ioe.printStackTrace();}
            }//closing of save_as_method
				else JOptionPane.showMessageDialog(null,"Invalid File Format ","Error !",JOptionPane.ERROR_MESSAGE);
        	
         }
     //end of save file function-----------------------------------------------------------------------------------------------
           
           // open file function starts here---------------------------------------
           public void open_file()
           {
               	System.out.println("opened");
        		JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int r= fc.showOpenDialog(null);
                
                if(r==JFileChooser.CANCEL_OPTION)
                    return;
     
                File myfile = fc.getSelectedFile();
                if(myfile==null || myfile.getName().equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Select A File ! ","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }   
                String ext= myfile.getName().substring(myfile.getName().lastIndexOf('.')+1);
                    File bi= myfile;
                    
                    
                   try
                   {
                    
                         BufferedImage image= ImageIO.read(myfile);
                         drawPaintArea.image_set= image;
                	  // JOptionPane.showMessageDialog(null," A File ! ","Error",JOptionPane.ERROR_MESSAGE);
                   }
                   
                   catch(Exception e)
                   {
                       JOptionPane.showMessageDialog(null,"File Not Found : "+ e);
                   }
           }
               //end of open file here
           
           // new file starts
           public void new_file()
           {
                
                   try
                   {
                   
                   // change the location of the icon image in the below line 
                    //Toolkit.getDefaultToolkit().getImage("C://Users//cyber//Documents//NetBeansProjects//Paint//src//image//icon.png"));
                       //File myfile=  
                       //BufferedImage image= ImageIO.read(myfile);
                         //drawPaintArea.image_set= image;
                	  // JOptionPane.showMessageDialog(null," A File ! ","Error",JOptionPane.ERROR_MESSAGE);
                   }
                   
                   catch(Exception e)
                   {
                       JOptionPane.showMessageDialog(null,"File Not Found : "+ e);
                   }
              
              
              
               
           }
           //new file
           
           // exit file
           public void exit_file()
           {
               System.exit(0);
           }
           // exit file
    
    // main function of the program
    public static void main(String args[]) throws IOException 
    {
        new paint();
    }

   
}
