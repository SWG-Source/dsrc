package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.library.create;
import script.library.group;
import script.library.player_structure;
import script.library.utils;

import java.util.Vector;

public class storage_door_terminal extends script.base_script
{
    public storage_door_terminal()
    {
    }
    public static final String TBL_STORAGE_WAVE = "datatables/dungeon/death_watch/storage_wave.iff";
    public static final string_id MNU_OPEN_DOOR = new string_id("dungeon/death_watch", "mnu_open_door");
    public static final string_id ACCESS_GRANTED = new string_id("dungeon/death_watch", "access_granted");
    public static final string_id ACCESS_DENIED = new string_id("dungeon/death_watch", "denied_access");
    public static final string_id TERMINAL_LOCKED = new string_id("dungeon/death_watch", "terminal_locked");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        setObjVar(structure, "death_watch.storageTerminal", self);
        return SCRIPT_CONTINUE;
    }
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
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (obj_id objContent : objContents) {
                String strItemTemplate = getTemplateName(objContent);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/passkey_storage.iff")) {
                    obj_id passkey = objContent;
                    sendSystemMessage(player, ACCESS_GRANTED);
                    unlockDoors(structure, player);
                    destroyObject(passkey);
                    return;
                }
            }
        }
        if (!utils.hasScriptVar(self, "terminal_lock_out"))
        {
            utils.setScriptVar(self, "terminal_lock_out", 1);
            sendSystemMessage(player, ACCESS_DENIED);
            messageTo(self, "handleLockDown", null, 600.0f, false);
            spawnWave(player);
            return;
        }
        if (utils.hasScriptVar(self, "terminal_lock_out"))
        {
            sendSystemMessage(player, TERMINAL_LOCKED);
            return;
        }
        return;
    }
    public void spawnWave(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id structure = getTopMostContainer(self);
        int defenderCreatures = dataTableGetNumRows(TBL_STORAGE_WAVE);
        int x = 0;
        while (x < 1)
        {
            String spawn = dataTableGetString(TBL_STORAGE_WAVE, x, "spawns");
            float xCoord = dataTableGetFloat(TBL_STORAGE_WAVE, x, "loc_x");
            float yCoord = dataTableGetFloat(TBL_STORAGE_WAVE, x, "loc_y");
            float zCoord = dataTableGetFloat(TBL_STORAGE_WAVE, x, "loc_z");
            location myself = getLocation(player);
            String planet = myself.area;
            obj_id top = getTopMostContainer(player);
            String spawnRoom = dataTableGetString(TBL_STORAGE_WAVE, x, "room");
            obj_id room = getCellId(structure, spawnRoom);
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object(spawn, spawnPoint);
            attachScript(spawnedCreature, "theme_park.dungeon.death_watch_bunker.storage_wave_01");
            x = x + 1;
        }
        return;
    }
    public void unlockDoors(obj_id structure, obj_id player) throws InterruptedException
    {
        if (!group.isGrouped(player))
        {
            setObjVar(player, "death_watch.storagePass", 1);
            obj_id openRoom = getCellId(structure, "storageroom24");
            dictionary params = new dictionary();
            params.put("player", player);
            params.put("room", openRoom);
            messageTo(openRoom, "addToList", params, 1, false);
            return;
        }
        if (group.isGrouped(player))
        {
            Vector members = group.getPCMembersInRange(player, 100.0f);
            if (members != null && members.size() > 0)
            {
                int numInGroup = members.size();
                if (numInGroup < 1)
                {
                    return;
                }
                for (Object member : members) {
                    obj_id thisMember = ((obj_id) member);
                    setObjVar(thisMember, "death_watch.storagePass", 1);
                    obj_id openRoom = getCellId(structure, "storageroom24");
                    dictionary params = new dictionary();
                    params.put("player", thisMember);
                    params.put("room", openRoom);
                    messageTo(openRoom, "addToList", params, 1, false);
                }
            }
        }
        return;
    }
    public int handleLockDown(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "terminal_lock_out");
        return SCRIPT_CONTINUE;
    }
    public int handleCallSupport1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        int defenderCreatures = dataTableGetNumRows(TBL_STORAGE_WAVE);
        int x = 0;
        while (x < 2)
        {
            String spawn = dataTableGetString(TBL_STORAGE_WAVE, x, "spawns");
            float xCoord = dataTableGetFloat(TBL_STORAGE_WAVE, x, "loc_x");
            float yCoord = dataTableGetFloat(TBL_STORAGE_WAVE, x, "loc_y");
            float zCoord = dataTableGetFloat(TBL_STORAGE_WAVE, x, "loc_z");
            location myself = getLocation(self);
            String planet = myself.area;
            obj_id top = getTopMostContainer(self);
            String spawnRoom = dataTableGetString(TBL_STORAGE_WAVE, x, "room");
            obj_id room = getCellId(structure, spawnRoom);
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object(spawn, spawnPoint);
            attachScript(spawnedCreature, "theme_park.dungeon.death_watch_bunker.storage_wave_02");
            if (x == 1)
            {
                setObjVar(spawnedCreature, "death_watch.call_backup", 1);
            }
            x = x + 1;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCallSupport2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        int defenderCreatures = dataTableGetNumRows(TBL_STORAGE_WAVE);
        int x = 0;
        while (x < defenderCreatures)
        {
            String spawn = dataTableGetString(TBL_STORAGE_WAVE, x, "spawns");
            float xCoord = dataTableGetFloat(TBL_STORAGE_WAVE, x, "loc_x");
            float yCoord = dataTableGetFloat(TBL_STORAGE_WAVE, x, "loc_y");
            float zCoord = dataTableGetFloat(TBL_STORAGE_WAVE, x, "loc_z");
            location myself = getLocation(self);
            String planet = myself.area;
            obj_id top = getTopMostContainer(self);
            String spawnRoom = dataTableGetString(TBL_STORAGE_WAVE, x, "room");
            obj_id room = getCellId(structure, spawnRoom);
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object(spawn, spawnPoint);
            attachScript(spawnedCreature, "theme_park.dungeon.death_watch_bunker.storage_wave_03");
            if (x == 3)
            {
                attachScript(spawnedCreature, "theme_park.dungeon.death_watch_bunker.storage_mini_boss");
            }
            x = x + 1;
        }
        return SCRIPT_CONTINUE;
    }
}
