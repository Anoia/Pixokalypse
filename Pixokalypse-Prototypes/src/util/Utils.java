package util;

import java.util.Random;

import agents.Agent;

public class Utils {
	
	private static Random random = new Random();

    /** Returns a random number between 0 (inclusive) and the specified value (inclusive). */
    static public int random (int range) {
        return random.nextInt(range + 1);
    }

    /** Returns a random number between start (inclusive) and end (inclusive). */
    static public int random (int start, int end) {
        return start + random.nextInt(end - start + 1);
    }

    static public boolean randomBoolean () {
        return random.nextBoolean();
    }

    static public float random () {
        return random.nextFloat();
    }

    /** Returns a random number between 0 (inclusive) and the specified value (inclusive). */
    static public float random (float range) {
        return random.nextFloat() * range;
    }

    /** Returns a random number between start (inclusive) and end (inclusive). */
    static public float random (float start, float end) {
        return start + random.nextFloat() * (end - start);
    }
    
    
    //Get distance between things
	public static float getDistance (Agent a1, Agent a2){
		return getDistance(a1.x, a1.y, a2.x, a2.y);
	}
	
	public static float getDistance (float startX, float startY, float endX, float endY){
		float distance = (float) Math.sqrt(Math.pow(startX-endX, 2) + Math.pow(startY-endY, 2));
		return distance;
	}
	
	public static float getDistance (int startX, int startY, int endX, int endY){
		return (int) getDistance(startX, startY, endX, endY);
	}
	

}
