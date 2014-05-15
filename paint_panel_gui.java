/*
 * 
    Author: sanjeev1779
    sanjeevkumar@iitj.ac.in
 */
 
 // This file will create the paint area and add toolbar and menubar to the frame.
package paintsw;

import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author cyber
 */
public class paint_panel_gui  extends JPanel{
    
    static JPanel draw_paint_area;
    static ToolBarMenu tool_bar_menu;
    public paint_panel_gui() throws IOException //default constructor
    {
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS)); // set the tool bars in the right side of the window
        tool_bar_menu = new ToolBarMenu(); // make an instance of the tooolbarmenu
        draw_paint_area= new drawPaintArea(); // make an instance of the drawpaintarea
        add(draw_paint_area); // for the painting area
        add(tool_bar_menu); // add tool bars, colors and other main menus in the window
        
    }
}
