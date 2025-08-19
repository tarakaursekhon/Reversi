package reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardSquare extends JButton implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	private IModel mod;
	private IController cont;
	private IView vw;
	private int x;
	private int y;
	private int f;

	public BoardSquare(IModel model, IController controller, int xin, int yin, int frame, IView view)
	{
		mod = model;
		cont = controller;
		vw = view;
		x = xin;
		y = yin;
		f = frame;
		
		this.addActionListener(this);
	}
	
	protected void paintComponent(Graphics gr)
	{
		gr.setColor(Color.green);
		gr.fillRect(0, 0, getWidth(), getHeight());
		gr.setColor(Color.black);
		gr.drawRect(0, 0, getWidth(), getHeight());
		
		if(getMod().getBoardContents(x, y) == 1)
		{
			gr.setColor(Color.white);
			gr.fillOval(0, 0, getWidth(), getHeight());
			gr.setColor(Color.black);
			gr.drawOval(0, 0, getWidth(), getHeight());
		}
		else if(getMod().getBoardContents(x, y) == 2)
		{
			gr.setColor(Color.black);
			gr.fillOval(0, 0, getWidth(), getHeight());
			gr.setColor(Color.white);
			gr.drawOval(0, 0, getWidth(), getHeight());
		}
	}
	
	public IModel getMod()
	{
		return mod;
	}
	
	public IController getCont()
	{
		return cont;
	}
	
	public void actionPerformed(ActionEvent e)
	{		
		if(f == getMod().getPlayer())
		{
			if(f == 1)
			{
				getCont().squareSelected(1, x, y);
			}
			else
			{
				getCont().squareSelected(2, x, y);
			}
		}
		else
		{
			if(f == 1)
			{
				vw.feedbackToUser(1, "It is not your turn!");
			}
			else if(f == 2)
			{
				vw.feedbackToUser(2, "It is not your turn!");
			}
		}
	}
	
}