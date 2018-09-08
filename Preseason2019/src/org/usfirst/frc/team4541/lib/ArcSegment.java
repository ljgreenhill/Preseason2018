package org.usfirst.frc.team4541.lib;

public class ArcSegment implements Segment {
	
	private Point startPoint;
	private Point endPoint;
	private Point centerPoint;
	
	private Point deltaStart;
	private Point deltaEnd;
	
	double radius;
	
	double maxVel;
	double endVel;
	
	public ArcSegment(Point startPoint, Point endPoint, Point centerPoint, double maxVel, double endVel) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.centerPoint = centerPoint;
		
		this.radius = Point.getDistance(this.startPoint, this.centerPoint);
		
		this.deltaStart = Point.getDelta(centerPoint, startPoint);
		this.deltaEnd = Point.getDelta(centerPoint, endPoint);
		
		this.maxVel = maxVel;
		this.endVel = endVel;
	}
	
	public ArcSegment(Point startPoint, Point endPoint, Point centerPoint, double maxVel) {
		this(startPoint, endPoint, centerPoint, maxVel, maxVel);
	}
	
	/*
	 * Gets the closest point on the arc segment to the given point.  Approach was modified from
	 * Team 254's on github:
	 * https://github.com/Team254/FRC-2017-Public/blob/master/src/com/team254/lib/util/control/PathSegment.java
	 * 
	 * @param lookaheadPoint: the robot's position + lookahead
	 */
	@Override
	public Point getClosestPointOnSegment(Point lookaheadPoint) {
		// Map the lookahead point to the closest point on the circle
		Point deltaPose = Point.getDelta(centerPoint, lookaheadPoint);
		double scale = deltaStart.getHypot() / deltaPose.getHypot();
		deltaPose = new Point(deltaPose.getX() * scale, deltaPose.getY() * scale);
		Point arcPoint = Point.addPoints(centerPoint, deltaPose);
		
		// Get the angle of the arcs between the circle's point and the endpoints of the arc
		double startAngle = Point.getAngleForArc(startPoint, arcPoint, centerPoint);
		double endAngle = Point.getAngleForArc(arcPoint, endPoint, centerPoint);
		
		double totalAngle = Point.getAngleForArc(startPoint, endPoint, centerPoint);
		
		// if the arcs between the endpoints equal the total length of the arc, we are on the arc
		if (MathHelper.areApproxEqual(startAngle + endAngle, totalAngle)) {
			// We are on the part of the arc we want, just return the point
			return arcPoint;
		} else { 
			// Otherwise we are not on the arc so return the closest endpoint
			if (Point.getDistance(arcPoint, startPoint) < Point.getDistance(arcPoint, endPoint)) {
				return startPoint;
			} 
			return endPoint;
		}
		
	}

	@Override
	public Point getStartPoint() {
		return this.startPoint;
	}

	@Override
	public Point getEndPoint() {
		return this.endPoint;
	}
	
	public Point getCenterPoint() {
		return this.centerPoint;
	}

	@Override
	public double getDistanceToEndpoint(Point lookaheadPos) {
		//get the arc angle of the amount of distance left to drive
		double remainingArcRadians = Point.getAngleForArc(lookaheadPos, this.endPoint, this.centerPoint);
		
//		System.out.println(lookaheadPos + ", " +  this.endPoint + ", " + this.centerPoint);
		//get total arc distance with s = theta * r
		return remainingArcRadians * this.radius;
	}

	@Override
	public double getMaxVelocity() {
		return this.maxVel;
	}

	@Override
	public double getEndVelocity() {
		return this.endVel;
	}

}
