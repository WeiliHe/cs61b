import java.lang.*;

public class Body{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	final static double G = 6.67e-11;

	public Body(double xP, double yP, double xV,
				double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	// calculate distance
	public double calcDistance(Body b){
		double dis_x = this.xxPos - b.xxPos;
		double dis_y = this.yyPos - b.yyPos;
		return Math.sqrt(dis_x * dis_x + dis_y * dis_y);
	}

	public double calcForceExertedBy(Body b){
		double dist = this.calcDistance(b);
		double F = G * this.mass * b.mass / (dist * dist);
		return F;
	}

	public double calcForceExertedByX(Body b){
		double F = this.calcForceExertedBy(b);
		double dist = this.calcDistance(b);
		double Fx = F * (b.xxPos - this.xxPos) / dist;
		return Fx;
	}

	public double calcForceExertedByY(Body b){
		double F = this.calcForceExertedBy(b);
		double dist = this.calcDistance(b);
		double Fy = F * (b.yyPos - this.yyPos) / dist;
		return Fy;
	}

	public double calcNetForceExertedByX(Body[] allBodys){
		double Fx_net = 0;
		for (Body b: allBodys){
			if (this.equals(b) == false) {
				Fx_net = Fx_net + this.calcForceExertedByX(b);
			}
		}
		return Fx_net;
	}

	public double calcNetForceExertedByY(Body[] allBodys){
		double Fy_net = 0;
		for (Body b: allBodys){
			if (this.equals(b) == false) {
				Fy_net = Fy_net + this.calcForceExertedByY(b);
			}
		}
		return Fy_net;
	}

	// update the position of the body
	public void update(double dt, double Fx_net, double Fy_net){
		double ax_net = Fx_net / this.mass;
		double ay_net = Fy_net / this.mass;
		this.xxVel = this.xxVel + dt * ax_net;
		this.yyVel = this.yyVel + dt * ay_net;
		this.xxPos = this.xxPos + this.xxVel * dt;
		this.yyPos = this.yyPos + this.yyVel * dt;
	}

	public void draw(){
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}