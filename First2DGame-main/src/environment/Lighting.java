package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Lighting {

	GamePanel gp;
	BufferedImage darknessFilter;
	public int dayCounter;
	public float filterAlpha = 0f;
	
	// Day state
	public final int day = 0;
	public final int dusk = 1;
	public final int night = 2;
	public final int dawn = 3;
	public int dayState = day;
	
	public Lighting(GamePanel gp) {
		this.gp = gp;
		setLightSource();
	}
	public void setLightSource() {
		// Create a buffered image
		darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
		if(gp.player.currentLight == null) {
			g2.setColor(new Color(0,0,0.1f,0.97f));
		}
		else {
			// Get the center x and y of the light circle
			int centerX = gp.player.screenX + (gp.tileSize)/2;
			int centerY = gp.player.screenY + (gp.tileSize)/2;
			
			// Create a gradation effect within the light circle
			Color color[] = new Color[12];
			float fraction[] = new float[12];
			
			color[0] = new Color(0,0,0.1f,0.1f);
			color[1] = new Color(0,0,0.1f,0.42f);
			color[2] = new Color(0,0,0.1f,0.52f);
			color[3] = new Color(0,0,0.1f,0.61f);
			color[4] = new Color(0,0,0.1f,0.69f);
			color[5] = new Color(0,0,0.1f,0.76f);
			color[6] = new Color(0,0,0.1f,0.82f);
			color[7] = new Color(0,0,0.1f,0.87f);
			color[8] = new Color(0,0,0.1f,0.91f);
			color[9] = new Color(0,0,0.1f,0.92f);
			color[10] = new Color(0,0,0.1f,0.93f);
			color[11] = new Color(0,0,0.1f,0.94f);

			
			fraction[0] = 0f;
			fraction[1] = 0.4f;
			fraction[2] = 0.5f;
			fraction[3] = 0.6f;
			fraction[4] = 0.65f;
			fraction[5] = 0.7f;
			fraction[6] = 0.75f;
			fraction[7] = 0.8f;
			fraction[8] = 0.85f;
			fraction[9] = 0.9f;
			fraction[10] = 0.95f;
			fraction[11] = 1f;
			// Create a gradation paint settings for the light circle
			RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);

			// Set the gradient data on g2
			g2.setPaint(gPaint);
		}
		
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.dispose();
	}
	public void resetDay() {
		dayState = day;
		filterAlpha = 0f;
	}
	public void update() {
		
		if(gp.player.lightUpdated == true) {
			setLightSource();
			gp.player.lightUpdated = false;
		}
		
		// Check the state of the day
		if(dayState == day) {
			
			dayCounter++;
			
			if(dayCounter > 3600) {
				dayState = dusk;
				dayCounter = 0;
			}
		}
		if(dayState == dusk) {
			
			filterAlpha += 0.0005f;
			
			if(filterAlpha > 1f) {
				filterAlpha = 1f;
				dayState = night;	
			}
		}
		if(dayState == night) {
			
			dayCounter++;
			
			if(dayCounter > 3600) {
				dayState = dawn;
				dayCounter = 0;
			}
		}
		if(dayState == dawn) {
			filterAlpha -= 0.0005f;
			
			if(filterAlpha < 0f) {
				filterAlpha = 0;
				dayState = day;
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		if(gp.currentArea == gp.oasis1 || gp.currentArea == gp.oasis2 || gp.currentArea == gp.land1 || gp.currentArea == gp.land2) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));

		}
		if(gp.currentArea == gp.oasis1 || gp.currentArea == gp.oasis2 || gp.currentArea == gp.land1 || gp.currentArea == gp.land2 || gp.currentArea == gp.maze1 || gp.currentArea == gp.maze2) {
			g2.drawImage(darknessFilter, 0, 0, null);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		// DEBUG
		String situation = switch(dayState) {
			case day -> "";
			case dusk -> "";
			case night -> "";
			case dawn -> "";
			default -> "";
		};
		g2.setColor(Color.white);
		g2.setFont(gp.ui.maruMonica.deriveFont(50f));
//		g2.setFont(g2.getFont().deriveFont(50f));
		g2.drawString(situation, gp.tileSize*gp.maxScreenCol - 100, gp.tileSize*gp.maxScreenRow - 20);

	}
	
}
// package environment;

// import java.awt.*;
// import java.awt.image.BufferedImage;
// import java.util.ArrayList;
// import java.util.Random;

// import main.GamePanel;

// public class Lighting {

//     GamePanel gp;
//     BufferedImage darknessFilter;
//     public int dayCounter;
//     public float filterAlpha = 0f;

//     // Day state
//     public final int day = 0;
//     public final int dusk = 1;
//     public final int night = 2;
//     public final int dawn = 3;
//     public int dayState = day;

//     // Weather state
//     public final int clear = 0;
//     public final int rain = 1;
//     public final int snow = 2;
//     public final int fog = 3;
//     public int weatherState = clear;

//     // Weather particles
//     ArrayList<Particle> particles = new ArrayList<>();
//     Random rand = new Random();

//     public Lighting(GamePanel gp) {
//         this.gp = gp;
//         setLightSource();
//         generateParticles();
//     }

//     public void setLightSource() {
//         darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
//         Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

//         if (gp.player.currentLight == null) {
//             g2.setColor(new Color(0, 0, 0.1f, 0.97f));
//         } else {
//             int centerX = gp.player.screenX + (gp.tileSize) / 2;
//             int centerY = gp.player.screenY + (gp.tileSize) / 2;

//             Color[] color = new Color[12];
//             float[] fraction = new float[12];

//             for (int i = 0; i < color.length; i++) {
//                 float alpha = 0.1f + i * 0.08f;
//                 color[i] = new Color(0, 0, 0.1f, alpha);
//                 fraction[i] = i / (float) (color.length - 1);
//             }

//             RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);

//             g2.setPaint(gPaint);
//         }

//         g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
//         g2.dispose();
//     }

//     public void resetDay() {
//         dayState = day;
//         filterAlpha = 0f;
//     }

//     public void update() {
//         if (gp.player.lightUpdated) {
//             setLightSource();
//             gp.player.lightUpdated = false;
//         }

//         if (dayState == day) {
//             dayCounter++;
//             if (dayCounter > 3600) {
//                 dayState = dusk;
//                 dayCounter = 0;
//             }
//         } else if (dayState == dusk) {
//             filterAlpha += 0.0005f;
//             if (filterAlpha > 1f) {
//                 filterAlpha = 1f;
//                 dayState = night;
//             }
//         } else if (dayState == night) {
//             dayCounter++;
//             if (dayCounter > 3600) {
//                 dayState = dawn;
//                 dayCounter = 0;
//             }
//         } else if (dayState == dawn) {
//             filterAlpha -= 0.0005f;
//             if (filterAlpha < 0f) {
//                 filterAlpha = 0;
//                 dayState = day;
//             }
//         }

//         updateWeather();
//     }

//     public void updateWeather() {
//         if (weatherState == rain || weatherState == snow) {
//             for (Particle p : particles) {
//                 p.update();
//             }
//         }
//     }

//     public void draw(Graphics2D g2) {
//         if (gp.currentArea == gp.land || gp.currentArea == gp.oasis) {
//             g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
//         }
//         if (gp.currentArea == gp.land || gp.currentArea == gp.oasis || gp.currentArea == gp.maze) {
//             g2.drawImage(darknessFilter, 0, 0, null);
//         }
//         g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

//         if (weatherState != clear) {
//             drawWeather(g2);
//         }

//         String situation = switch (dayState) {
//             case day -> "Day";
//             case dusk -> "Dusk";
//             case night -> "Night";
//             case dawn -> "Dawn";
//             default -> "";
//         };

//         g2.setColor(Color.white);
//         g2.setFont(gp.ui.maruMonica.deriveFont(50f));
//         g2.drawString(situation, 800, 500);
//     }

//     public void drawWeather(Graphics2D g2) {
//         if (weatherState == rain || weatherState == snow) {
//             for (Particle p : particles) {
//                 p.draw(g2);
//             }
//         } else if (weatherState == fog) {
//             g2.setColor(new Color(200, 200, 200, 50));
//             g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
//         }
//     }

//     public void generateParticles() {
//         particles.clear();
//         for (int i = 0; i < 100; i++) {
//             particles.add(new Particle(rand.nextInt(gp.screenWidth), rand.nextInt(gp.screenHeight), weatherState));
//         }
//     }
// }

// class Particle {
//     int x, y;
//     int speed;
//     int type;

//     public Particle(int x, int y, int type) {
//         this.x = x;
//         this.y = y;
//         this.type = type;
//         this.speed = type == 1 ? 5 : 2; // Rain falls faster than snow
//     }

//     public void update() {
//         y += speed;
//         if (y > 720) {
//             y = 0;
//             x = new Random().nextInt(1280);
//         }
//     }

//     public void draw(Graphics2D g2) {
//         if (type == 1) {
//             g2.setColor(Color.blue);
//             g2.drawLine(x, y, x, y + 10);
//         } else if (type == 2) {
//             g2.setColor(Color.white);
//             g2.fillOval(x, y, 5, 5);
//         }
//     }
// }
