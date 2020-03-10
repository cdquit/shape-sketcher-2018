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
public class Circle extends Oval {
    private int size;
    
    public Circle(Point startPoint)
    {
        this(startPoint, startPoint);
    }
    
    public Circle(Point startPoint, Point controlPoint)
    {
        super(startPoint, controlPoint);
        calculateSize();
    }
    
    public void calculateSize()
    {
        size = Math.min(width, height);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(getColour());
        
        int xStart = startPoint.x - size;
        int yStart = startPoint.y - size;
        
        //Make sure that xStart cannot be outside the panel.
        //Make sure that yStart is reduced by the same - stay in the square bounding box.
        if (xStart < 0)
        {
            size += xStart;
            yStart -= xStart;
            xStart = 0;
        }
        
        //Make sure that yStart cannot be outside the panel.
        //Make sure that xStart is reduced by the same - stay in the square bounding box.
        if (yStart < 0)
        {
            size += yStart;
            xStart -= yStart;
            yStart = 0;
        }
        
        if (filled)
            g.fillOval(xStart, yStart, size * 2, size * 2);
        else
            g.drawOval(xStart, yStart, size * 2, size * 2);
    }
}
