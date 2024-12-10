package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_SuperSword extends Entity {

    public static final String objName = "ngư thần kiếm";

    public OBJ_SuperSword(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = objName;
        down1 = setup("/objects/supersword", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword.";
        price = 20;
        knockBackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;
    }
}
