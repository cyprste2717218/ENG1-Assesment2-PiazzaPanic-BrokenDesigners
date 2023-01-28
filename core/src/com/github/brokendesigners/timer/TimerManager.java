package com.github.brokendesigners.timer;

import java.util.ArrayList;

public class TimerManager {
	ArrayList<Timer> timers;

	public TimerManager(){

	}
	public void addTimer(Timer timer){
		timers.add(timer);
	}
	public void removeTimer(Timer timer){
		timers.remove(timer);
	}
	public void updateTimers(){
		for (Timer timer : timers){
			if (timer.isRunning()){
				timer.update();
			}
		}
	}
}
