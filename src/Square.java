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
public class Square extends Rectangle {
    private int size;
    
    public Square(Point startPoint)
    {
        this(startPoint, startPoint);
    }
    
    public Square(Point startPoint, Point controlPoint)
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
        
        int xStart = startPoint.x;
        if (controlPoint.x < startPoint.x) //Make sure the xStart is top left corner.
            xStart -= size;
        
        int yStart = startPoint.y;
        if (controlPoint.y < startPoint.y) //Make sure the yStart is top left corner.
            yStart -= size;
        
        if (filled)
            g.fillRect(xStart, yStart, size, size);
        else
            g.drawRect(xStart, yStart, size, size);
    }
}
