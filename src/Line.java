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
public class Line extends Shape {
    
    public Line(Point startPoint)
    {
        this(startPoint, startPoint);
    }
    
    public Line(Point startPoint, Point controlPoint)
    {
        super(startPoint);
        setControlPoint(controlPoint);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(getColour());
        g.drawLine(startPoint.x, startPoint.y, controlPoint.x, controlPoint.y);
    }
}
