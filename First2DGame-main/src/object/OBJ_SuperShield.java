package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_SuperShield extends Entity{ 

    public static final String objName = "Super Shield";

    public OBJ_SuperShield(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = objName;
        down1 = setup("/objects/supershield", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nMade by wood.";
        price = 35;

    }

}