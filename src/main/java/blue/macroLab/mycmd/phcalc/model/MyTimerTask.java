package blue.macroLab.mycmd.phcalc.model;

import java.util.Timer;
import java.util.TimerTask;

import blue.macroLab.mycmd.phcalc.cons.Constants;

/** Represents a time that schedules tasks
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyTimerTask extends TimerTask {
	private Data model;
	private Timer timer;
	private int cancel_i;
	
	public MyTimerTask(Data m,Timer t) {
		this.model = m;
		this.timer = t;
		this.cancel_i = Constants.LENGTH_TASK * model.getSteps(); 
	}
	
	/** 
	  * Completes a task
	  * 
	  * After a task has been finished, the thread sleeps for <em>LENGTH_TASK</em> milliseconds.
	  */
    private void complete() {
    	try {
            Thread.sleep(Constants.LENGTH_TASK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /** 
	  * Increases the progress bar by a certain amount
	  * This method is being run as long as it hasn't been canceled.
	  */
	public void run() {
			this.model.inc();
			complete();
	}
	
	/** 
	  * Cancels a task
	  * 
	  * It stops the run method.
	  */
	public void cancelTask() {
		//cancel after sometime
	    try {
	        Thread.sleep(cancel_i);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    timer.cancel();
	    try {
	        Thread.sleep(Constants.DELAY);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
}
