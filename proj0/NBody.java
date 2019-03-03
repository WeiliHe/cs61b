

public class NBody {
	public static double readRadius(String filename){
		In in = new In(filename);
		int N = in.readInt();
		double Radius = in.readDouble();
		return Radius;
	}
	// dont need to import from the Body class? 
	public static Body[] readBodies(String filename){
		In in = new In(filename);
		int N = in.readInt();
		double R = in.readDouble();

        Body[] allBodies = new Body[N];
        int i = 0;
        // why here cannot use the in.isEmpty()?
		while(i < N){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			Body b = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
			allBodies[i] = b;
			i = i + 1;
		}
		return allBodies;
	}
	// public static Body[] readBodies(String filename) {
 //        In in = new In(filename);
 //        int num = in.readInt();
 //        in.readDouble();
 //        Body[] allBodies = new Body[num];
 //        int i = 0;
 //        while (i < num) {
 //            allBodies[i] = new Body(in.readDouble(), in.readDouble(),
 //                    in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
 //            i++;
 //        }
 //        return allBodies;
 //    }
	public static void main (String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double Radius = readRadius(filename);
		Body[] allBodies = readBodies(filename);
		// draw the background
		StdDraw.enableDoubleBuffering();
		// StdDraw.setScale(-Radius, Radius);
		// StdDraw.clear();
		// StdDraw.picture(0, 0, "images/starfield.jpg");
		// for (Body b: allBodies){
		// 	b.draw();
		// }
		// StdDraw.show();
		int N = allBodies.length;
		double t = 0;
		while(t < T){
			double[] xForces = new double[N];
			double[] yForces = new double[N];
			// update the forces
			for (int i = 0; i < N; i ++){
				xForces[i] = allBodies[i].calcNetForceExertedByX(allBodies);
				yForces[i] = allBodies[i].calcNetForceExertedByY(allBodies);
			}
			// update the position
			for (int i = 0; i < N; i ++){
				allBodies[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.setScale(-Radius, Radius);
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
           	for (Body b: allBodies){
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			t += dt;
		}
		StdOut.printf("%d\n", allBodies.length);
		StdOut.printf("%.2e\n", Radius);
		for (int i = 0; i < allBodies.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  allBodies[i].xxPos, allBodies[i].yyPos, allBodies[i].xxVel,
                  allBodies[i].yyVel, allBodies[i].mass, allBodies[i].imgFileName);   
}
	}
}