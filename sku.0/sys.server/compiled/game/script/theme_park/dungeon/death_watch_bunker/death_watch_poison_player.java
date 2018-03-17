package script.theme_park.dungeon.death_watch_bunker;

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

public class death_watch_poison_player extends script.base_script
{
    public death_watch_poison_player()
    {
    }
    public static final String SCRIPT_POISON = "theme_park.dungeon.death_watch_bunker.death_watch_poison_player";
    public static final string_id BAD_AIR = new string_id("dungeon/death_watch", "bad_air");
    public static final int HEALTH_COST = 5000;
    public static final int ACTION_COST = 2000;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        poisonAir(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        poisonAirLogIn(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        poisonAir(self);
        return SCRIPT_CONTINUE;
    }
    public boolean checkForGasMask(obj_id self) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(self, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/wearables/goggles/rebreather.iff"))
                {
                    obj_id mask = objContents[intI];
                    if (hasObjVar(mask, "death_watch_ready"))
                    {
                        obj_id holder = getContainedBy(mask);
                        if (holder == self)
                        {
                            return true;
                        }
                    }
                }
                if (strItemTemplate.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_helmet.iff"))
                {
                    obj_id mandHelm = objContents[intI];
                    obj_id holder = getContainedBy(mandHelm);
                    if (holder == self)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void poisonAirLogIn(obj_id self) throws InterruptedException
    {
        messageTo(self, "handlePoisonAir", null, 30f, false);
        return;
    }
    public void poisonAir(obj_id self) throws InterruptedException
    {
        messageTo(self, "handlePoisonAir", null, 10f, false);
        return;
    }
    public boolean validateRoom(obj_id self) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        if (!isIdValid(structure))
        {
            return false;
        }
        obj_id cell54 = getCellId(structure, "medroom54");
        obj_id cell56 = getCellId(structure, "storageroom56");
        obj_id cell60 = getCellId(structure, "tramroom60");
        obj_id cell57 = getCellId(structure, "smallroom57");
        location myLoc = getLocation(self);
        obj_id myCell = myLoc.cell;
        if (myCell == cell54 || myCell == cell56 || myCell == cell60 || myCell == cell57)
        {
            return false;
        }
        return true;
    }
    public int handlePoisonAir(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        if (!isIdValid(structure))
        {
            detachScript(self, SCRIPT_POISON);
            return SCRIPT_CONTINUE;
        }
        String strBuildingName = getTemplateName(structure);
        if (!strBuildingName.equals("object/building/general/bunker_allum_mine.iff"))
        {
            detachScript(self, SCRIPT_POISON);
            return SCRIPT_CONTINUE;
        }
        if (validateRoom(self) == false)
        {
            poisonAir(self);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(structure, "death_watch.air_vent_on"))
        {
            poisonAir(self);
            return SCRIPT_CONTINUE;
        }
        if (checkForGasMask(self) == true)
        {
            poisonAir(self);
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || isIncapacitated(self))
        {
            poisonAir(self);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, BAD_AIR);
        int health = getAttrib(self, HEALTH);
        int action = getAttrib(self, ACTION);
        int healthcost = HEALTH_COST;
        int actioncost = ACTION_COST;
        if (health <= healthcost)
        {
            healthcost = health - 1;
        }
        if (action <= actioncost)
        {
            actioncost = action - 1;
        }
        healthcost = healthcost * -1;
        addToHealth(self, healthcost);
        drainAttributes(self, actioncost, 0);
        messageTo(self, "handlePoisonAir", null, 30f, false);
        return SCRIPT_CONTINUE;
    }
}
