package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_Potion_Blue;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;


public class NPC_Merchant extends Entity {

	public NPC_Merchant(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 1;
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;

		getImage();
		setDialogue();
		setItems();
	}

	public void getImage() {
		// Cache merchant animation frames
		BufferedImage frame1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		BufferedImage frame2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
		
		// Reuse frames for all directions
		up1 = frame1;
		up2 = frame2;
		down1 = frame1;
		down2 = frame2;
		left1 = frame1;
		left2 = frame2;
		right1 = frame1;
		right2 = frame2;
	}	public void setDialogue() {
		dialogues[0][0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade?";
		dialogues[1][0] = "Come again, hehe!";
		dialogues[2][0] = "You need more coin to buy that!";
		dialogues[3][0] = "You cannot carry any more!";
		dialogues[4][0] = "You cannot sell an equipped item!";
	}

	public void setItems() {
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Potion_Blue(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Sword_Normal(gp));
		inventory.add(new OBJ_Axe(gp));
		inventory.add(new OBJ_Shield_Wood(gp));
		inventory.add(new OBJ_Shield_Blue(gp));
	}
	@Override
	public void speak() {
		facePlayer();
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}
}
