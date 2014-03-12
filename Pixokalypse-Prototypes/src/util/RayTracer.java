package util;

import potentialField.StaticPotentialField;

public class RayTracer {
	int[][] levelData;
	public RayTracer(StaticPotentialField environmentMap) {
		levelData = environmentMap.fieldArray;
	}
	
	public boolean castRay(int startX, int startY, int endX, int endY, int range, boolean markInLightMap){
		
		int x, y, t, dx, dy, incrementX, incrementY, pdx, pdy, ddx, ddy, es, el, err;
		
		/* Entfernung in beide Dimensionen berechnen */
        dx = endX - startX;
        dy = endY - startY;

        /* Vorzeichen des Inkrements bestimmen */
        incrementX = signum(dx);
        incrementY = signum(dy);

        if(dx < 0) dx = -dx;
        if(dy < 0) dy = -dy;

        /* Feststellen, welche Entfernung größer ist*/
        if(dx>dy){
             /* x ist schnelle Richtung */
            pdx=incrementX; pdy=0;          /* pd. ist Parallelschritt */
            ddx=incrementX; ddy=incrementY; /* dd. ist Diagonalschritt */
            es =dy;   el =dx;   /* Fehlerschritte schnell, langsam */
        }else{
            /* y ist schnelle Richtung */
            pdx=0;    pdy=incrementY; /* pd. ist Parallelschritt */
            ddx=incrementX; ddy=incrementY; /* dd. ist Diagonalschritt */
            es =dx;   el =dy;   /* Fehlerschritte schnell, langsam */
        }

        /* Initialisierungen vor Schleifenbeginn */
        x = startX;
        y = startY;
        err = el/2;
        setPixel(x, y, markInLightMap);

        /* Pixel berechnen */
        for(t=0; t < el; t++){ /* t zaehlt die Pixel, el ist auch Anzahl */
            /* Aktualisierung Fehlerterm */
            err -= es;
            if(err<0){
                /*Fehler wieder positiv machen */
                err += el;
                /*Schritt in langsame Richtung, Diagonalschritt*/
                x+=ddx;
                y+=ddy;
            } else{
                /* Schritt in schnelle Richtung, Parallelschritt */
                x += pdx;
                y += pdy;
            }
            int deltaX = x - startX;
            int deltaY = y - startY;
            if((deltaX*deltaX + deltaY*deltaY) > range*range){
                return false;
            }
            boolean success = setPixel(x, y, markInLightMap);
            if(!success){
                return false;
            }
        }
        return true;
	}
	
	
	private boolean setPixel(int x, int y, boolean markInLightMap) {
		if(markInLightMap){
			//do some marking
			return true;
		}
		return !isSolid(x, y);
	}
	
	private boolean isSolid(int x, int y){
		boolean solid = levelData[x][y] != 0;
		
		return solid;
	}

	/* gibt das Vorzeichen einer Zahl wieder */
    int signum(int x){
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
    }
	
	
	
	
	

}
