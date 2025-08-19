package reversi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIView implements IView
{
	
	IModel mod;
	IController cont;
	BoardSquare[] squares1;
	BoardSquare[] squares2;
	JLabel title1;
	JLabel title2;
	
	@Override
	public void initialise(IModel model, IController controller)
	{
		mod = model;
		cont = controller;
		
		int i = 0;
		int width = mod.getBoardWidth();
		int height = mod.getBoardHeight();
		int x1 = 0;
		int y1 = 0;
		int x2 = mod.getBoardWidth() - 1;
		int y2 = mod.getBoardHeight() - 1;
		
		
		//make first frame
		JFrame frame1 = new JFrame();
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setTitle("player 1");	
		
		title1 = new JLabel();
		title1.setText("White player - choose where to put your piece");
		JPanel board1 = new JPanel();
		board1.setLayout(new GridLayout(height, width));
		squares1 = new BoardSquare[height * width];
		JPanel buttons1 = new JPanel();
		buttons1.setLayout(new GridLayout());
		JButton b1 = new JButton("Greedy AI (play white)");
		JButton b2 = new JButton("Restart");
		b1.addActionListener(new Greedy(1));
		b2.addActionListener(new Restart());
		buttons1.add(b1);
		buttons1.add(b2);
		
		//make second frame
		JFrame frame2 = new JFrame();
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setTitle("player 2");		
		title2 = new JLabel();
		title2.setText("Black player - not your turn");
		JPanel board2 = new JPanel();
		board2.setLayout(new GridLayout(height, width));
		squares2 = new BoardSquare[height * width];
		JPanel buttons2 = new JPanel();
		buttons2.setLayout(new GridLayout());
		JButton b3 = new JButton("Greedy AI (play black)");
		JButton b4 = new JButton("Restart");
		b3.addActionListener(new Greedy(2));
		b4.addActionListener(new Restart());
		buttons2.add(b3);
		buttons2.add(b4);
		
		//fill boards
		for(i = 0; i < (height * width); i++)
		{	
			squares1[i] = new BoardSquare(mod, cont, x1, y1, 1, this);
			board1.add(squares1[i]);
			
			if(x1 == (width - 1))
			{
				x1 = 0;
				y1++;
			}
			else
			{
				x1++;
			}
			
			squares2[i] = new BoardSquare(mod, cont, x2, y2, 2, this);
			board2.add(squares2[i]);
			
			if(x2 == 0)
			{
				x2 = (width - 1);
				y2--;
			}
			else
			{
				x2--;
			}
		}
		
		//add x and y labels to first frame
		JPanel board1x = new JPanel();
		JPanel xs1 = new JPanel(new GridLayout(1, width));
		JPanel ys1 = new JPanel(new GridLayout(height, 1));
		JLabel[] xlabs1 = new JLabel[width];
		JLabel[] ylabs1 = new JLabel[height];
		
		for(i = 0; i < width; i++)
		{
			xlabs1[i] = new JLabel(String.valueOf(i));
			xs1.add(xlabs1[i]);
		}
		
		for(i = 0; i < height; i++)
		{
			ylabs1[i] = new JLabel(String.valueOf(i));
			ys1.add(ylabs1[i]);
		}
		
		board1x.setLayout(new BorderLayout());
		board1x.add(xs1, BorderLayout.NORTH);
		board1x.add(ys1, BorderLayout.WEST);
		board1x.add(board1, BorderLayout.CENTER);
		
		//add x and y labels to second frame
		JPanel board2x = new JPanel();
		JPanel xs2 = new JPanel(new GridLayout(1, width));
		JPanel ys2 = new JPanel(new GridLayout(height, 1));
		JLabel[] xlabs2 = new JLabel[width];
		JLabel[] ylabs2 = new JLabel[height];
		
		for(i = (width - 1); i >= 0; i--)
		{
			xlabs2[i] = new JLabel(String.valueOf(i));
			xs2.add(xlabs2[i]);
		}
		
		for(i = (height - 1); i >= 0; i--)
		{
			ylabs2[i] = new JLabel(String.valueOf(i));
			ys2.add(ylabs2[i]);
		}
		
		board2x.setLayout(new BorderLayout());
		board2x.add(xs2, BorderLayout.NORTH);
		board2x.add(ys2, BorderLayout.WEST);
		board2x.add(board2, BorderLayout.CENTER);
		
		//display first frame
		frame1.getContentPane().setLayout(new BorderLayout());
		frame1.getContentPane().add(title1, BorderLayout.NORTH);
		frame1.getContentPane().add(board1x, BorderLayout.CENTER);
		frame1.getContentPane().add(buttons1, BorderLayout.SOUTH);
		frame1.setSize(400, 400);
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
		
		//display second frame
		frame2.getContentPane().setLayout(new BorderLayout());
		frame2.getContentPane().add(title2, BorderLayout.NORTH);
		frame2.getContentPane().add(board2x, BorderLayout.CENTER);
		frame2.getContentPane().add(buttons2, BorderLayout.SOUTH);
		frame2.setSize(400, 400);
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);
		
		cont.startup();
		cont.update();
		refreshView();
	}

	@Override
	public void refreshView()
	{
		int width = mod.getBoardWidth();
		int height = mod.getBoardHeight();
		int i = 0;
		
		for(i = 0; i < (height * width); i++)
		{
			squares1[i].repaint();
			squares2[i].repaint();
		}
	}

	@Override
	public void feedbackToUser(int player, String message)
	{
		if(player == 1)
		{
			title1.setText(message);
		}
		else if(player == 2)
		{
			title2.setText(message);
		}
	}
	
	public class Greedy implements ActionListener
	{
		
		private int p;
		
		public Greedy(int player)
		{
			p = player;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			cont.doAutomatedMove(p);
		}
		
	}
	
	public class Restart implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			cont.startup();
			cont.update();
			refreshView();
		}
		
	}

}
