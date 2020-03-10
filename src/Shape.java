/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author jmm4115
 */
public abstract class Shape implements Serializable {
    protected Point startPoint;
    protected Point controlPoint;
    private Color colour;
    
    public Shape()
    {
        this(new Point());
    }
    
    public Shape(Point startPoint)
    {
        this.startPoint = startPoint;
        setControlPoint(startPoint);
        setColour(Color.BLACK); //default to Black.
    }
    
    public Color getColour()
    {
        return colour;
    }
    
    public void setColour(Color colour)
    {
        this.colour = colour;
    }
    
    public void setControlPoint(Point controlPoint)
    {
        this.controlPoint = controlPoint;
    }
    
    public abstract void draw(Graphics g);
    
    @Override
    public String toString()
    {
        return "Startpoint: " + startPoint.x + ", " + startPoint.y 
                + "\nControlpoint: " + controlPoint.x + ", " + controlPoint.y;
    }
}
