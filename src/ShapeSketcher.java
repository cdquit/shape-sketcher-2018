/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author jmm4115
 */
public class ShapeSketcher extends JPanel {
    private SketchArea sketch;
    private JRadioButton line, oval, circle, rectangle, square;
    private JCheckBox fill;
    private JButton colour, clear, undo, open, save;
    private ArrayList<Shape> shapes;
    
    public ShapeSketcher()
    {
        super(new BorderLayout());
        sketch = new SketchArea();
        add(sketch, BorderLayout.CENTER);
        
        line = new JRadioButton("Line");
        oval = new JRadioButton("Oval");
        circle = new JRadioButton("Circle");
        rectangle = new JRadioButton("Rectangle");
        square = new JRadioButton("Square");
        ButtonGroup group = new ButtonGroup();
        group.add(line);
        group.add(oval);
        group.add(circle);
        group.add(rectangle);
        group.add(square);
        
        fill = new JCheckBox("Fill");
        colour = new JButton("Colour");
        colour.addActionListener(sketch);
        clear = new JButton("Clear");
        clear.addActionListener(sketch);
        undo = new JButton("Undo");
        undo.addActionListener(sketch);
        JPanel south = new JPanel();
        south.add(line);
        south.add(oval);
        south.add(circle);
        south.add(rectangle);
        south.add(square);
        south.add(fill);
        south.add(colour);
        south.add(clear);
        south.add(undo);
        add(south, BorderLayout.SOUTH);
        
        open = new JButton("Open");
        open.addActionListener(sketch);
        save = new JButton("Save");
        save.addActionListener(sketch);
        JPanel north = new JPanel(new GridLayout(1, 2));
        north.add(open);
        north.add(save);
        add(north, BorderLayout.NORTH);
        
        shapes = new ArrayList<Shape>();
    }
    
    public class SketchArea extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
        private Shape currentShape;
        private Point startPoint;
        private Point controlPoint;
        private Point previousControlPoint;
        private Color shapeColour;
        
        public SketchArea()
        {
            setPreferredSize(new Dimension(800, 600));
            setBackground(Color.WHITE);
            currentShape = null;
            startPoint = null;
            controlPoint = null;
            previousControlPoint = null;
            shapeColour = Color.BLACK;
            addMouseListener(this);
            addMouseMotionListener(this);
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            for (Shape shape: shapes)
                shape.draw(g);

            if (currentShape != null)
            {
                currentShape.draw(g);
                
                if (shapeColour != Color.BLACK)
                    g.setColor(Color.BLACK);
                
                displayMousePosition(g);
            }
        }
        
        private void displayMousePosition(Graphics g)
        {
            String coordText = "";

            if ((previousControlPoint.x - controlPoint.x) == 0)
                coordText += "V ";

            if ((previousControlPoint.y - controlPoint.y) == 0)
                coordText += "H ";

            coordText += controlPoint.x + ", " + controlPoint.y;

            int coordX = controlPoint.x + 20;
            if (controlPoint.x >= getWidth() - 70)
                coordX -= 100;

            int coordY = controlPoint.y + 20;
            if (controlPoint.y >= getHeight() - 30)
                coordY -= 30;

            g.drawString(coordText, coordX, coordY);
        }

        //press and release to consider a click.
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();
            controlPoint = startPoint;
            
            //Creating an instance of shape depending on what shape is selected.
            if (line.isSelected())
            {
                currentShape = new Line(startPoint);
            }
            else if (oval.isSelected())
            {
                currentShape = new Oval(startPoint);
            }
            else if (circle.isSelected())
            {
                currentShape = new Circle(startPoint);
            }
            else if (rectangle.isSelected())
            {
                currentShape = new Rectangle(startPoint);
            }
            else if (square.isSelected())
            {
                currentShape = new Square(startPoint);
            }
            
            if (fill.isSelected() && currentShape instanceof EnclosesRegion)
                ((EnclosesRegion) currentShape).setFilled(true);
            
            if (currentShape != null)
                currentShape.setColour(shapeColour);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (currentShape != null)
                shapes.add(currentShape);
            
            currentShape = null;
            startPoint = null;
            controlPoint = null;
            previousControlPoint = null;
            repaint(); //just to remove mouse position showing.
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseDragged(MouseEvent e) {
            previousControlPoint = controlPoint;
            controlPoint = e.getPoint();
            
            //Make sure that controlPoint is within panel bound - panelWidth and panelHeight.
            if (controlPoint.x < 0)
                controlPoint.x = 0;
            else if (controlPoint.x > sketch.getWidth())
                controlPoint.x = sketch.getWidth();
            
            if (controlPoint.y < 0)
                controlPoint.y = 0;
            else if (controlPoint.y > sketch.getHeight())
                controlPoint.y = sketch.getHeight();
            
            if (currentShape != null)
            {
                currentShape.setControlPoint(controlPoint);
                
                //Calculate width and height again as controlPoint changes, so shape sizes change.
                if (currentShape instanceof Oval)
                {
                    ((Oval) currentShape).calculateWidth(getWidth());
                    ((Oval) currentShape).calculateHeight(getHeight());
                    
                    if (currentShape instanceof Circle)
                        ((Circle) currentShape).calculateSize();
                }
                else if (currentShape instanceof Rectangle)
                {
                    ((Rectangle) currentShape).calculateWidth();
                    ((Rectangle) currentShape).calculateHeight();
                    
                    if (currentShape instanceof Square)
                        ((Square) currentShape).calculateSize();
                }
                
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            
            if (source == colour)
            {
                shapeColour = JColorChooser.showDialog(null, "Colour", Color.BLACK);                
            }
            else if (source == clear)
            {
                shapes.clear();
                repaint();
            }
            else if (source == undo)
            {
                if (!shapes.isEmpty())
                    shapes.remove(shapes.size() - 1);
                repaint();
            }
            else if (source == open)
            {
                JFileChooser fileChooser = new JFileChooser(new File("."));
                int isOpen = fileChooser.showOpenDialog(null);
                
                if (isOpen == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
                        shapes = (ArrayList) ois.readObject();
                        ois.close();
                        repaint();
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, ex, "Error Opening File", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (ClassNotFoundException ex)
                    {
                        JOptionPane.showMessageDialog(null, ex, "Error With Object Cast", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else if (source == save)
            {
                JFileChooser fileChooser = new JFileChooser(new File("."));
                int isSave = fileChooser.showSaveDialog(null);
                
                if (isSave == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()));
                        oos.writeObject(shapes);
                        oos.flush();
                        oos.close();
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, ex, "Error Saving File", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        ShapeSketcher myPanel = new ShapeSketcher();
        JFrame frame = new JFrame("SketchPad"); //create frame to hold our JPanel subclass	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(myPanel);  //add instance of MyGUI to the frame
        frame.pack(); //resize frame to fit our Jpanel
        frame.setResizable(false);
        //Position frame on center of screen 
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2), (screenHeight / 2) - (frame.getHeight() / 2)));
        //show the frame	
        frame.setVisible(true);
    }
}
