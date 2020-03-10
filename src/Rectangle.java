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
public class Rectangle extends Shape implements EnclosesRegion {
    protected int width;
    protected int height;
    protected boolean filled;
    
    public Rectangle(Point startPoint)
    {
        this(startPoint, startPoint);
    }
    
    public Rectangle(Point startPoint, Point controlPoint)
    {
        super(startPoint);
        setControlPoint(controlPoint);
        calculateWidth();
        calculateHeight();
        setFilled(false);
    }
    
    public void calculateWidth()
    {
        width = Math.abs(controlPoint.x - startPoint.x);
    }
    
    public void calculateHeight()
    {
        height = Math.abs(controlPoint.y - startPoint.y);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(getColour());
        
        int xStart = startPoint.x;
        if (controlPoint.x < startPoint.x) //Make sure the xStart is top left corner.
            xStart -= width;
        
        int yStart = startPoint.y;
        if (controlPoint.y < startPoint.y) //Make sure the yStart is top left corner.
            yStart -= height;
        
        if (filled)
            g.fillRect(xStart, yStart, width, height);
        else
            g.drawRect(xStart, yStart, width, height);
    }

    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
