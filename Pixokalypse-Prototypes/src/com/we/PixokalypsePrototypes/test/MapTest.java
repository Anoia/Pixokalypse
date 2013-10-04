package com.we.PixokalypsePrototypes.test;

public class MapTest {
	
	public enum Field {
	    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
	    THURSDAY, FRIDAY, SATURDAY 
	}
	
	public void randMap(){
				final int FELDER = 40; //auch im unit controller ändern!

				final int STRASSENTEILER = 100; // bla n�ster 10nerpotenz wo FELDER rein passt
				final int FELDERPROBLOCK = 7;//)
				final int FELDERPROBLOCKMULTIPLIKATOR = 1; // 2,1 oder 0
				final int FELDERPROBLOCKRANDOME = 3;
				final int MAPUMRANDUNG = 5;
				//LeeresFeld	
				final int EMPTYANZ = 3;
				final int STRANZ = 1;	//also immer strassensets machen!
				final int BAUMANZ = 1;
				//Spielplatz
				final int S1ANZ = 3;
				final int S2ANZ = 2;
				final int S3ANZ = 3;
				//Haus
				final int H1ANZ = 4;
				final int H2ANZ = 3;
				final int H3ANZ = 3;
				//Park
				final int P1ANZ = 4;
				final int P2ANZ = 4;
				final int P3ANZ = 4;
				//Gesch�ft	
				final int G1ANZ = 3;
				final int G2ANZ = 2;
				final int G3ANZ = 1;


				/*map erzeugen*/
				//feldgrösse vielfache 25 + x * 10 {x E N} :D(hoffentlich nicht falsch :D)
				def drucken = ""
				Field field = Field.
				def fieldMerker




				for (int ycor = 0; ycor < FELDER;++ycor){
					for (int xcor = 0; xcor < FELDER;++xcor){
						field = new Map(xaxis:xcor,yaxis:ycor,fieldtype:"empty",dateiName: "empty_1.png").save()
					}
				}

				def random = new Random()


				/*
				 //Strassen Algo 1 schachbrettmap
				 for(int i = 0;i < 1; i++){
				 for (int ycor = 0; ycor < FELDER;ycor+=(3+random.nextInt(4))){
				 for (int xcor = 0; xcor < FELDER;++xcor){
				 field = Map.findByXaxisAndYaxis(xcor,ycor)
				 field.fieldtype = "str"
				 }
				 }
				 for (int ycor = 0; ycor < FELDER;ycor+=(3+random.nextInt(3))){
				 for (int xcor = 0; xcor < FELDER;++xcor){
				 field = Map.findByXaxisAndYaxis(ycor,xcor)
				 field.fieldtype = "str"
				 }
				 }
				 }
				 */
		////////////von mitte aus im kreis rum

				
				

					def startx = (int)(FELDER/2)
					def starty = (int)(FELDER/2)

					def distance = 1
					def distancecopy = distance
					def weiter = true
					def times = 2
					def richtungen = [
						"oben",
						"rechts",
						"unten",
						"links"
					]
					def richtunglauf = 0
					def richtung = richtungen[richtunglauf]
					def laufendeBlockId = 0
					while(weiter){
			
							if(distancecopy <= 0){
								times--
								if(richtunglauf < 3){
									richtunglauf++
									richtung = richtungen[richtunglauf]
								}else{
									richtunglauf = 0
									richtung = richtungen[richtunglauf]
								}
								if(times == 0){
									times = 2
									distance++
								}
								distancecopy = distance
							}
							distancecopy--
							switch(richtung){
								case "oben":
									starty -= 1
									//System.out.println("oben")
									break
								case "rechts":
									startx += 1
									//System.out.println("rechts")
									break
								case "unten":
									starty += 1
									//System.out.println("unten")
									break
								case "links":
									startx -= 1
									//System.out.println("links")
									break
							}
					

							field = Map.findByXaxisAndYaxis(startx,starty)
							if (field.blockId > 9999 && field.fieldtype.getKey() != "str"){//vielleicht unn�tige doppelabfrage str
								laufendeBlockId++
								//System.out.println("\n\n==============================\n Erzeuge Block mit blockId:"+laufendeBlockId+"\nStarcoordinaten: ("+xcor+"/"+ycor+")")
								setzeFelder(startx,starty,laufendeBlockId)
							}
							
							
							
							
							if(startx == 0+MAPUMRANDUNG || starty == 0+MAPUMRANDUNG){weiter=false}

					}
				
				
					
					System.out.println("fertig nach "+((new Date().time - zeit.time)/1000)+" Sec.")
				
		////////////ende von mitte aus im kreis rum
				drucken = "Algo schrit 1 Fertig\n\n"
				for (int ycor = 0; ycor < FELDER;++ycor){
					for (int xcor = 0; xcor < FELDER;++xcor){
						field = Map.findByXaxisAndYaxis(xcor,ycor)
						if(field.fieldtype.getKey() == "str"){
							drucken += "+"
						}else drucken += " "
					}
					drucken += "\n"
				}
				System.out.println("\n\n "+drucken)
				System.out.println("\n\n")


	}
	public static void main(String[] args) {
		System.out.println("hihi");
	}
}
