package script.theme_park.dungeon.diant_zuy;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.dot;
import script.library.player_structure;

public class explosion_player extends script.base_script
{
    public explosion_player()
    {
    }
    public static final String SCRIPT_EXPLOSION = "theme_park.dungeon.diant_zuy.explosion_player";
    public static final string_id BUNKER_EXPLODING = new string_id("dungeon/diant_bunker", "bunker_exploding");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleExplodingBunker", null, 30f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleExplodingBunker", null, 30f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        detachScript(self, SCRIPT_EXPLOSION);
        return SCRIPT_CONTINUE;
    }
    public int handleExplodingBunker(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        obj_id safetyCellOne = getCellId(structure, "entry");
        location myLoc = getLocation(self);
        obj_id myCell = myLoc.cell;
        if (myCell == safetyCellOne)
        {
            detachScript(self, SCRIPT_EXPLOSION);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(myCell))
        {
            detachScript(self, SCRIPT_EXPLOSION);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(structure) || !exists(structure))
        {
            detachScript(self, SCRIPT_EXPLOSION);
            return SCRIPT_CONTINUE;
        }
        String strBuildingName = getTemplateName(structure);
        if (!strBuildingName.equals("object/building/general/bunker_imperial_bunker_abandoned.iff"))
        {
            detachScript(self, SCRIPT_EXPLOSION);
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || isIncapacitated(self))
        {
            messageTo(self, "handleExplodingBunker", null, 30f, false);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, BUNKER_EXPLODING);
        obj_id top = getTopMostContainer(self);
        double intensity = (double)getFloatObjVar(top, "diant.explosionIntensity");
        double healthcostDbl = Math.pow(85, intensity);
        int healthcost = (int)healthcostDbl;
        int dmgLocation = 0;
        int hitLoc = rand(1, 5);
        switch (hitLoc)
        {
            case 1:
            dmgLocation = HIT_LOCATION_R_ARM;
            break;
            case 2:
            dmgLocation = HIT_LOCATION_L_ARM;
            break;
            case 3:
            dmgLocation = HIT_LOCATION_BODY;
            break;
            case 4:
            dmgLocation = HIT_LOCATION_R_LEG;
            break;
            case 5:
            dmgLocation = HIT_LOCATION_HEAD;
            break;
            default:
            dmgLocation = HIT_LOCATION_L_LEG;
            break;
        }
        damage(self, DAMAGE_BLAST, dmgLocation, healthcost);
        location playerLoc = getLocation(self);
        playClientEffectLoc(self, "clienteffect/combat_explosion_lair_large.cef", playerLoc, 10f);
        messageTo(self, "handleExplodingBunker", null, 20f, false);
        return SCRIPT_CONTINUE;
    }
}
