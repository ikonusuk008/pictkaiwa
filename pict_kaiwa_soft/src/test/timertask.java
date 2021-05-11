package test;
class timertask extends java.util.TimerTask {

private int Time;

public timertask(int t) { super();
Time = t;
};

public void run() {
if (Time == 5) java.awt.Toolkit.getDefaultToolkit().beep();
Time --;
};

public boolean CheckTimeout() { return(Time <= 0); };

}

