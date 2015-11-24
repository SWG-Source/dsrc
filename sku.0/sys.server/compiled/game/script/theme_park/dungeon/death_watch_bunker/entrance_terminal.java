package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;
import script.library.create;
import script.library.group;

public class entrance_terminal extends script.base_script
{
    public entrance_terminal()
    {
    }
    public static final string_id MNU_OPEN_DOOR = new string_id("dungeon/death_watch", "mnu_open_door");
    public static final string_id ACCESS_GRANTED = new string_id("dungeon/death_watch", "access_granted");
    public static final string_id ENTRANCE_DENIED = new string_id("dungeon/death_watch", "entrance_denied");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int mnuControl = mi.addRootMenu(menu_info_types.ITEM_USE, MNU_OPEN_DOOR);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            checkAuthorization(structure, player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void checkAuthorization(obj_id structure, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(structure))
        {
            return;
        }
        obj_id self = getSelf();
        if (hasObjVar(player, "mand.acknowledge"))
        {
            CustomerServiceLog("DUNGEON_DeathWatchBunker", "*Death Watch Entry: Player %TU has entered the Death Watch Bunker.", self);
            attachScript(player, "theme_park.dungeon.death_watch_bunker.death_script");
            sendSystemMessage(player, ACCESS_GRANTED);
            unlockDoors(structure, player);
        }
        else 
        {
            sendSystemMessage(player, ENTRANCE_DENIED);
        }
        return;
    }
    public void unlockDoors(obj_id structure, obj_id player) throws InterruptedException
    {
        if (!group.isGrouped(player))
        {
            setObjVar(player, "death_watch.entrancePass", 1);
            obj_id openRoom = getCellId(structure, "smallroom2");
            dictionary info = new dictionary();
            info.put("player", player);
            info.put("room", openRoom);
            messageTo(openRoom, "addToList", info, 1, false);
            return;
        }
        if (group.isGrouped(player))
        {
            Vector members = group.getPCMembersInRange(player, 100f);
            if (members != null && members.size() > 0)
            {
                int numInGroup = members.size();
                if (numInGroup < 1)
                {
                    return;
                }
                for (int i = 0; i < numInGroup; i++)
                {
                    obj_id thisMember = ((obj_id)members.get(i));
                    setObjVar(thisMember, "death_watch.entrancePass", 1);
                    obj_id openRoom = getCellId(structure, "smallroom2");
                    dictionary params = new dictionary();
                    params.put("player", thisMember);
                    params.put("room", openRoom);
                    messageTo(openRoom, "addToList", params, 1f, false);
                }
            }
        }
        return;
    }
}
