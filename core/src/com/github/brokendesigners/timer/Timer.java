package com.github.brokendesigners.timer;


import com.badlogic.gdx.Gdx;

public class Timer {
	private float elapsedTime;
	private float delay;
	private float percentage;
	private boolean running;
	private boolean complete;

	public Timer(TimerManager manager, float delay){
		this.delay = delay;
		this.elapsedTime = 0;
		this.percentage = 0;
		this.running = false;
	}
	public void startTimer(){
		this.running = true;
	}
	public void stopTimer(){
		this.running = false;
	}
	public boolean isRunning() {
		return running;
	}
	public void update(){
		this.elapsedTime += Gdx.graphics.getDeltaTime();
		this.percentage = this.elapsedTime / this.delay;
		if (elapsedTime >= delay){
			this.stopTimer();
			this.complete = true;
		}
	}
	public boolean isComplete() {
		return complete;
	}

}
