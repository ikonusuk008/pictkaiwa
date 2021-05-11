package test;

public class timer {

	public static void main(String args[]) {
		int t;
		try {
			if (args.length < 1)
				t = 10;
			else
				t = Integer.parseInt(args[0]);

			timertask tt = new timertask(t);
			java.util.Timer ti = new java.util.Timer();
			ti.schedule(tt, 0, 1000);

			while (!tt.CheckTimeout()) {
				Thread.sleep(100);
			}
			tt.cancel();
			System.out.println("Time Out");

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}