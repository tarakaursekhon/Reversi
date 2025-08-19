package reversi;

public class ReversiController implements IController
{

	private IModel mod;
	private IView vw;
	private int width;
	private int height;
	
	@Override
	public void initialise(IModel model, IView view)
	{
		mod = model;
		vw = view;
		width = mod.getBoardWidth();
		height = mod.getBoardHeight();
	}

	@Override
	public void startup()
	{
		mod.setFinished(false);
		mod.setPlayer(1);
		mod.clear(0);
		mod.setBoardContents((width / 2 - 1), (height / 2 - 1), 1);
		mod.setBoardContents((width / 2 - 1), ((height / 2)), 2);
		mod.setBoardContents(((width / 2)), (height / 2 - 1), 2);
		mod.setBoardContents(((width / 2)), ((height / 2)), 1);
	}

	@Override
	public void update()
	{
		boolean next = true;
		boolean done = true;
		int i = 0;
		int j = 0;
		int white = 0;
		int black = 0;
		
		for(i = 0; i < width; i++)
		{
			for(j = 0; j < height; j++)
			{				
				if(countDisksCheck(mod.getPlayer(), i, j) > 0)
				{
					next = false;
				}
			}
		}
		
		if(next && mod.getPlayer() == 1)
		{
			mod.setPlayer(2);
		}
		else if(next && mod.getPlayer() == 2)
		{
			mod.setPlayer(1);
		}
		
		if(next)
		{
			for(i = 0; i < width; i++)
			{
				for(j = 0; j < height; j++)
				{					
					if(countDisksCheck(mod.getPlayer(), i, j) > 0)
					{
						done = false;
					}
				}
			}
		}
		else
		{
			done = false;
		}
		
		if(done)
		{
			mod.setFinished(true);
		}
		else
		{
			mod.setFinished(false);
		}
		
		if(mod.hasFinished() == true)
		{
			mod.setPlayer(0);
			
			for(i = 0; i < width; i++)
			{
				for(j = 0; j < height; j++)
				{
					if(mod.getBoardContents(i, j) == 1)
					{
						white++;
					}
					else if(mod.getBoardContents(i, j) == 2)
					{
						black++;
					}
				}
			}
			
			if(white > black)
			{
				vw.feedbackToUser(1, "White won. White " + white + " to Black " + black + ". Reset the game to replay.");
				vw.feedbackToUser(2, "White won. White " + white + " to Black " + black + ". Reset the game to replay.");
			}
			else if(black > white)
			{
				vw.feedbackToUser(1, "Black won. Black " + black + " to White " + white + ". Reset the game to replay.");
				vw.feedbackToUser(2, "Black won. Black " + black + " to White " + white + ". Reset the game to replay.");
			}
			else
			{
				vw.feedbackToUser(1, "Draw. Both players ended with " + white + " pieces. Reset game to replay.");
				vw.feedbackToUser(2, "Draw. Both players ended with " + white + " pieces. Reset game to replay.");
			}
		}
		
		if(mod.getPlayer() == 1)
		{
			vw.feedbackToUser(1, "White player - choose where to put your piece");
			vw.feedbackToUser(2, "Black player - not your turn");
		}
		else if(mod.getPlayer() == 2)
		{
			vw.feedbackToUser(2, "Black player - choose where to put your piece");
			vw.feedbackToUser(1, "White player - not your turn");
		}
	}

	@Override
	public void squareSelected(int player, int x, int y)
	{
		boolean valid = countDisks(player, x, y);
		vw.refreshView();
		
		if(player == 1 && valid)
		{
			mod.setPlayer(2);
		}
		else if(player == 2 && valid)
		{
			mod.setPlayer(1);
		}
		
		update();
	}
	
	public boolean countDisks(int player, int x, int y)
	{
		int disk = player;
		int count = 0;
		int moves = 0;
		int i = -1;
		int j = -1;
		int n = 1;
		int m = 1;
		
		if(mod.getBoardContents(x, y) == 0)
		{
			for(i = -1; i < 2; i++)
			{
				for(j = -1; j < 2; j++)
				{
					if(!(i == 0 && j == 0))
					{
						if(disk == 1)
						{
							if((x + (i * n) >= 0) && (x + (i * n) < width) && (y + (j * n) >= 0) && (y + (j * n) < height))
							{
								while(mod.getBoardContents(x + (i * n), y + (j * n)) == 2)
								{
									n++;
									count++;
									
									if((x + (i * n) < 0) || (x + (i * n) >= width) || (y + (j * n) < 0) || (y + (j * n) >= height))
									{
										count = 0;
										break;
									}
								}
							}
							
							if((x + (i * n) >= 0) && (x + (i * n) < width) && (y + (j * n) >= 0) && (y + (j * n) < height))
							{
								if(mod.getBoardContents(x + (i * n), y + (j * n)) == 1 && count != 0)
								{
									for(m = 0; m <= count; m++)
									{
										mod.setBoardContents(x + (i * m), y + (j * m), 1);
									}
									
									moves++;
								}
							}
						}
						else
						{
							if((x + (i * n) >= 0) && (x + (i * n) < width) && (y + (j * n) >= 0) && (y + (j * n) < height))
							{
								while(mod.getBoardContents(x + (i * n), y + (j * n)) == 1)
								{
									n++;
									count++;
									
									if((x + (i * n) < 0) || (x + (i * n) >= width) || (y + (j * n) < 0) || (y + (j * n) >= height))
									{
										count = 0;
										break;
									}
								}
							}
							
							if((x + (i * n) >= 0) && (x + (i * n) < width) && (y + (j * n) >= 0) && (y + (j * n) < height))
							{
								if(mod.getBoardContents(x + (i * n), y + (j * n)) == 2 && count != 0)
								{
									for(m = 0; m <= count; m++)
									{
										mod.setBoardContents(x + (i * m), y + (j * m), 2);
									}
									
									moves++;
								}
							}
						}
						
						n = 1;
						count = 0;
					}
				}
			}
		}
		
		if(moves == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public int countDisksCheck(int player, int x, int y)
	{
		int disk = player;
		int count = 0;
		int ccount = 0;
		int i = -1;
		int j = -1;
		int n = 1;
		
		if(mod.getBoardContents(x, y) == 0)
		{
			for(i = -1; i < 2; i++)
			{
				for(j = -1; j < 2; j++)
				{
					if(!(i == 0 && j == 0))
					{
						if(disk == 1)
						{
							if((x + (i * n) >= 0) && (x + (i * n) < width) && (y + (j * n) >= 0) && (y + (j * n) < height))
							{
								while(mod.getBoardContents(x + (i * n), y + (j * n)) == 2)
								{
									n++;
									count++;
									
									if((x + (i * n) < 0) || (x + (i * n) >= width) || (y + (j * n) < 0) || (y + (j * n) >= height))
									{
										count = 0;
										break;
									}
								}
							}
							
							if((x + (i * n) >= 0) && (x + (i * n) < width) && (y + (j * n) >= 0) && (y + (j * n) < height))
							{
								if(mod.getBoardContents(x + (i * n), y + (j * n)) == 1 && count != 0)
								{	
									ccount += count;
								}
							}
						}
						else
						{
							if((x + (i * n) >= 0) && (x + (i * n) < width) && (y + (j * n) >= 0) && (y + (j * n) < height))
							{
								while(mod.getBoardContents(x + (i * n), y + (j * n)) == 1)
								{
									n++;
									count++;
									
									if((x + (i * n) < 0) || (x + (i * n) >= width) || (y + (j * n) < 0) || (y + (j * n) >= height))
									{
										count = 0;
										break;
									}
								}
							}
							
							if((x + (i * n) >= 0) && (x + (i * n) < width) && (y + (j * n) >= 0) && (y + (j * n) < height))
							{
								if(mod.getBoardContents(x + (i * n), y + (j * n)) == 2 && count != 0)
								{
									ccount += count;
								}
							}
						}
						
						n = 1;
						count = 0;
					}
				}
			}
		}
		
		return ccount;
	}

	@Override
	public void doAutomatedMove(int player)
	{
		int a = 0;
		int b = 0;
		int curr = 0;
		
		if(player == mod.getPlayer())
		{
			for(int i = 0; i < width; i++)
			{
				for(int j = 0; j < height; j++)
				{					
					if(countDisksCheck(player, i, j) > curr)
					{
						a = i;
						b = j;
						curr = countDisksCheck(player, i, j);
					}
				}
			}
			
			countDisks(player, a, b);
			
			if(player == 1)
			{
				mod.setPlayer(2);
			}
			else
			{
				mod.setPlayer(1);
			}
			
			vw.refreshView();
			update();
		}
		else
		{
			if(player == 1)
			{
				vw.feedbackToUser(1, "It is not your turn!");
			}
			else if(player == 2)
			{
				vw.feedbackToUser(2, "It is not your turn!");
			}
		}
	}

}
