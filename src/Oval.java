/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handin;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author jmm4115
 */
public class Oval extends Shape implements EnclosesRegion {
    protected int width;
    protected int height;
    protected boolean filled;
    
    public Oval(Point startPoint)
    {
        this(startPoint, startPoint);
    }
    
    public Oval(Point startPoint, Point controlPoint)
    {
        super(startPoint);
        setControlPoint(controlPoint);
        calculateWidth(-1);
        calculateHeight(-1);
        setFilled(false);
    }
    
    public void calculateWidth(int panelWidth)
    {
        width = Math.abs(controlPoint.x - startPoint.x);
        
        //Make sure that the shape cannot be outside the panelWidth.
        if (panelWidth != - 1)
            if ((startPoint.x + width) > panelWidth)
                width = panelWidth - startPoint.x;
    }
    
    public void calculateHeight(int panelHeight)
    {
        height = Math.abs(controlPoint.y - startPoint.y);
        
        //Make sure that the shape cannot be outside the panelHeight.
        if (panelHeight != -1)
            if ((startPoint.y + height) > panelHeight)
                height = panelHeight - startPoint.y;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(getColour());
        
        int xStart = startPoint.x - width;
        int yStart = startPoint.y - height;
        
        //Make sure that xStart cannot be outside the panel.
        if (xStart < 0)
        {
            width += xStart;
            xStart = 0;
        }
        
        //Make sure that yStart cannot be outside the panel.
        if (yStart < 0)
        {
            height += yStart;
            yStart = 0;
        }
        
        if (filled)
            g.fillOval(xStart, yStart, width * 2, height * 2);
        else
            g.drawOval(xStart, yStart, width * 2, height * 2);
    }

    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
