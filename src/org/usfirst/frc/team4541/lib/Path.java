package org.usfirst.frc.team4541.lib;

import java.util.ArrayList;
import org.usfirst.frc.team4541.robot.Constants;

public class Path {
	ArrayList<Segment> segmentList = new ArrayList<Segment>();
	boolean didFinish;
	VelocityManager manager;
	
	public Path() {
		didFinish = false;
		manager = new VelocityManager();
	}
	
	/*
	 * adds a segment to the path
	 */
	public void addSegment(Segment segment) {
		segmentList.add(segment);
	}
	
	/*
	 * updates which segment of the path the robot is currently driving on and creates a
	 * RobotCmd containing left and right wheel velocities to make the robot stay on the path
	 * 
	 * @param state: the current position + velocity of the robot
	 */
	public RobotCmd update(RobotPos robotPos) {
		Segment currentSegment = this.getCurrentSegment();
		Point currentPos = robotPos.position;
		
		if (Point.getDistance(currentSegment.getEndPoint(), currentPos) < Constants.kPathPursuitTolerance) {
			// if the current robot position is within tol of the end point, move on to the next segment
			// or say that the robot is done following the path
			if (this.canMoveOnToNextSegment()) {
				this.moveOnToNextSegment();
			}
		}
		if (!this.canMoveOnToNextSegment()) {
			if (Point.getDistance(currentSegment.getEndPoint(), currentPos) < Constants.kStopSteeringDistance) {
				manager.freezeHeading(robotPos.heading);
			}
			
			if (robotPos.getVelocity() < 0.5 && Point.getDistance(robotPos.position, currentSegment.getEndPoint()) < Constants.kPathPursuitTolerance) {
				this.didFinish = true;
			}
		}
		
		return manager.getVelCmd(currentSegment, robotPos);
	}
	
	/*
	 * returns if the robot can continue on to another segment or if it is at the end of a path
	 */
	public boolean canMoveOnToNextSegment() {
		return segmentList.size() > 1;
	}
	
	/*
	 * moves the robot onto the next segment of the path
	 */
	public void moveOnToNextSegment() {
		segmentList.remove(0);
	}
	
	/*
	 * returns the segment of the path the robot is driving on
	 */
	public Segment getCurrentSegment() {
		return segmentList.get(0);
	}
	
	/*
	 * returns if the robot reached the end of the final segment of the path
	 */
	public boolean isFinished() {
		return didFinish;
	}
	
}
