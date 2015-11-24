package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class leaving extends script.base_script
{
    public leaving()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(item, "gm"))
        {
            String setting = getConfigSetting("Dungeon", "Geonosian");
            if (setting == null || setting.equals("false") || setting.equals("0"))
            {
                string_id locked = new string_id(MSGS, "no_entry");
                sendSystemMessage(item, locked);
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                CustomerServiceLog("DUNGEON_GeonosianBunker", "*Geonosian Entry: " + item + ": " + getName(item) + " has entered the Geonosian Dungeon.");
                attachScript(item, "theme_park.dungeon.geonosian_madbio_bunker.death_script");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(destinationCell))
        {
            string_id relock = new string_id(MSGS, "relock");
            sendSystemMessage(item, relock);
            clearPlayer(item);
            reLockDoor(transferrer, item);
        }
        return SCRIPT_CONTINUE;
    }
    public int relockDoor(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "locked", 1);
        return SCRIPT_CONTINUE;
    }
    public void reLockDoor(obj_id self, obj_id player) throws InterruptedException
    {
        String name = getFirstName(player);
        obj_id cave = getTopMostContainer(getSelf());
        obj_id trans2 = getCellId(cave, "transition2");
        obj_id lab1 = getCellId(cave, "lab1");
        obj_id office = getCellId(cave, "office");
        obj_id oper1 = getCellId(cave, "operatingroom1");
        obj_id oper2 = getCellId(cave, "operatingroom2");
        obj_id cage = getCellId(cave, "grandcageroom");
        obj_id endcave = getCellId(cave, "largeendcave");
        obj_id hall = getCellId(cave, "hall3");
        dictionary doorStuff = new dictionary();
        doorStuff.put("player", player);
        if (permissionsIsAllowed(trans2, player) == true)
        {
            doorStuff.put("room", trans2);
            messageTo(trans2, "removeFromList", doorStuff, 3, false);
        }
        if (permissionsIsAllowed(lab1, player) == true)
        {
            doorStuff.put("room", lab1);
            messageTo(lab1, "removeFromList", doorStuff, 3, false);
        }
        if (permissionsIsAllowed(office, player) == true)
        {
            doorStuff.put("room", office);
            messageTo(office, "removeFromList", doorStuff, 3, false);
        }
        if (permissionsIsAllowed(oper1, player) == true)
        {
            doorStuff.put("room", oper1);
            messageTo(oper1, "removeFromList", doorStuff, 3, false);
        }
        if (permissionsIsAllowed(oper2, player) == true)
        {
            doorStuff.put("room", oper2);
            messageTo(oper2, "removeFromList", doorStuff, 3, false);
        }
        if (permissionsIsAllowed(cage, player) == true)
        {
            doorStuff.put("room", cage);
            messageTo(cage, "removeFromList", doorStuff, 3, false);
        }
        if (permissionsIsAllowed(endcave, player) == true)
        {
            doorStuff.put("room", endcave);
            messageTo(endcave, "removeFromList", doorStuff, 3, false);
        }
        if (permissionsIsAllowed(hall, player) == true)
        {
            doorStuff.put("room", hall);
            messageTo(hall, "removeFromList", doorStuff, 3, false);
        }
        return;
    }
    public void clearPlayer(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "geo_lock"))
        {
            removeObjVar(player, "geo_lock");
        }
        if (hasObjVar(player, "biogenic"))
        {
            removeObjVar(player, "biogenic");
        }
        if (hasObjVar(player, "conversation.biogenic_securitytech"))
        {
            removeObjVar(player, "conversation.biogenic_securitytech");
        }
        if (hasObjVar(player, "conversation.biogenic_scientist_human"))
        {
            removeObjVar(player, "conversation.biogenic_scientist_human");
        }
        if (hasObjVar(player, "conversation.biogenic_scientist_geonosian"))
        {
            removeObjVar(player, "conversation.biogenic_scientist_geonosian");
        }
        if (hasObjVar(player, "conversation.biogenic_scientist_generic_03"))
        {
            removeObjVar(player, "conversation.biogenic_scientist_generic_03");
        }
        if (hasObjVar(player, "conversation.biogenic_scientist_generic_02"))
        {
            removeObjVar(player, "conversation.biogenic_scientist_generic_02");
        }
        if (hasObjVar(player, "conversation.biogenic_scientist_generic_01"))
        {
            removeObjVar(player, "conversation.biogenic_scientist_generic_01");
        }
        if (hasObjVar(player, "conversation.biogenic_engineertech"))
        {
            removeObjVar(player, "conversation.biogenic_engineertech");
        }
        if (hasObjVar(player, "conversation.biogenic_crazyguy"))
        {
            removeObjVar(player, "conversation.biogenic_crazyguy");
        }
        if (hasObjVar(player, "conversation.biogenic_construction"))
        {
            removeObjVar(player, "conversation.biogenic_construction");
        }
        if (hasObjVar(player, "conversation.biogenic_assistant"))
        {
            removeObjVar(player, "conversation.biogenic_assistant");
        }
        if (hasScript(player, "theme_park.dungeon.geonosian_madbio_bunker.death_script"))
        {
            detachScript(player, "theme_park.dungeon.geonosian_madbio_bunker.death_script");
        }
        return;
    }
}
