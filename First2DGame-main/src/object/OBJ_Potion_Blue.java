package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Blue extends Entity {

    GamePanel gp;
    public static final String objName = "Blue Potion";

    public OBJ_Potion_Blue(GamePanel gp) {
        super(gp);

        this.gp = gp;
        type = type_consumable;
        name = objName;
        value = 2;
        down1 = setup("/objects/potion_blue", gp.tileSize, gp.tileSize);
        description = "[Blue Potion]\nHeals your life by " + value + ".";
        price = 25;
        stackable = true;

        setDialogue();
    }

    public void setDialogue() {

        dialogues[0][0] = "You drink the " + name + "!\n"
                + "Your mana has been recorvered by " + value + ".";
    }

    public boolean use(Entity entity) {

        startDialogue(this, 0);
        entity.mana += value;
        gp.playSE(2);
        return true;
    }
}
