package com.society.util;

public class MovingAlertThread  implements Runnable{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String alert="Please open ALERTS !!!!!";
			int width= 120;
			while(!Thread.currentThread().isInterrupted())
			{
				for(int i=0;i<5*alert.length();i++)
				{
					 System.out.print("\r" + " ".repeat(i) + alert);
	                    Thread.sleep(100); // Delay for movement effect
	                    System.out.print("\r" + " ".repeat(width)); // Clear the line
				}
			}
			
		}catch(InterruptedException e)
		{
            //System.out.println("\nAlerts display interrupted.");

		}
		
	}

}
