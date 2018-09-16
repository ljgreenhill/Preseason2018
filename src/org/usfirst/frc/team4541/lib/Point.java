package org.usfirst.frc.team4541.lib;

public class Point {
	
	private double x = 0;
	private double y = 0;
	
	public Point(double xCord, double yCord) {
		this.x = xCord;
		this.y = yCord;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	/*
	 * Get the distance between the origin and the point
	 */
	public double getHypot() {
		return Math.hypot(this.getX(), this.getY());
	}
	
	@Override
	public String toString() {
		return "(" + this.getX() + ", " + this.getY() + ")";
	}
	
	/*
	 * Take the cross product between two points
	 * 
	 * @param a the first point
	 * @param b the second point
	 */
	public static double cross(Point a, Point b) {
        return a.getX() * b.getY() - a.getY() * b.getX();
    }
	
	/*
	 * Create a point which represents the delta between
	 * two points
	 * 
	 * @param a the first point
	 * @param b the second point
	 */
	public static Point getDelta(Point a, Point b) {
		return new Point(b.getX() - a.getX(), b.getY() - a.getY());
	}
	
	/*
	 * Add two points together, then return the sum
	 * 
	 * @param a the first point
	 * @param b the second point
	 */
	public static Point addPoints(Point a, Point b) {
		return new Point(a.getX() + b.getX(), a.getY() + b.getY());
	}
	
	/*
	 * Subtract point b from point a, then return the difference
	 * 
	 * @param a the first point
	 * @param b the second point
	 */
	public static Point subtractPoints(Point a, Point b) {
		return new Point(a.getX() - b.getX(), a.getY() - b.getY());
	}
	
	/*
	 * return the distance between two points
	 * 
	 * @param a the first point
	 * @param b the second point
	 */
	public static double getDistance(Point a, Point b) {
		return Math.hypot(b.getX() - a.getX(), b.getY() - a.getY());
	}
	
	/*
	 * return the angle the robot needs to get from its current position to a desired position
	 * (angle between two points and x-axis)
	 * 
	 * @param currentPos the current position of the robot
	 * @param desiredPos the point we want to be at
	 */
	public static double getAngleNeeded(Point currentPos, Point desiredPos) {
		double dx = desiredPos.getX() - currentPos.getX() ;
		double dy = desiredPos.getY() - currentPos.getY();
		return Math.atan2(dy, dx);
	}
	
	public static double getAngleForArc(Point startPoint, Point endPoint, Point centerPoint) {
		// get the three sides of the triangle
		double a = Point.getDistance(centerPoint, startPoint);
		double b = Point.getDistance(centerPoint, endPoint);
		double c = Point.getDistance(startPoint, endPoint);
		
		//solve for arc measure with law of cosines
		return Math.acos((c*c - a*a - b*b) / (-2 * a * b));
	}
	
	/*
	 * returns the side length opposite to theta
	 */
	public static double getOppSideLength(double a, double b, double thetaC) {
		//solve for side length with law of cosines
		return Math.sqrt(a*a + b*b - 2 * a * b * Math.cos(thetaC));
	}
}