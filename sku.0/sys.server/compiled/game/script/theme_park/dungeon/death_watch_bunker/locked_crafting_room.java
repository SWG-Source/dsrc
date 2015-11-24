package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.group;

public class locked_crafting_room extends script.base_script
{
    public locked_crafting_room()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        permissionsMakePrivate(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        permissionsMakePrivate(self);
        return SCRIPT_CONTINUE;
    }
    public int unlockYourself(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (player == null)
        {
            CustomerServiceLog("DUNGEON_DeathWatchBunker", "*Death Watch Problem: Player with a null ID tried to unlock the door...");
            return SCRIPT_CONTINUE;
        }
        removeAll(self);
        addPartyToList(player);
        dictionary webster = new dictionary();
        webster.put("player", player);
        messageTo(self, "cleanOutRoom", webster, 300, false);
        return SCRIPT_CONTINUE;
    }
    public void addPartyToList(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String fname = getFirstName(player);
        permissionsAddAllowed(self, fname);
        if (group.isGrouped(player))
        {
            obj_id groupObj = getGroupObject(player);
            obj_id[] groupMembers = getGroupMemberIds(groupObj);
            int numGroupMembers = groupMembers.length;
            for (int f = 0; f < numGroupMembers; f++)
            {
                obj_id groupie = groupMembers[f];
                if (isIdValid(groupie))
                {
                    if (groupie != player)
                    {
                        String firstName = getFirstName(groupie);
                        permissionsAddAllowed(self, firstName);
                    }
                }
            }
        }
        else 
        {
            permissionsAddAllowed(self, fname);
        }
        return;
    }
    public void removeAll(obj_id self) throws InterruptedException
    {
        String[] currentList = permissionsGetAllowed(self);
        int listNum = currentList.length;
        if (listNum > 0)
        {
            for (int i = 0; i < listNum; i++)
            {
                String thisMember = currentList[i];
                permissionsRemoveAllowed(self, thisMember);
            }
        }
        return;
    }
    public int addTailorDroid(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = params.getObjId("droid");
        setObjVar(self, "tailorDroid", droid);
        return SCRIPT_CONTINUE;
    }
    public int addEngineerDroid(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = params.getObjId("droid");
        setObjVar(self, "engineerDroid", droid);
        return SCRIPT_CONTINUE;
    }
    public int addArmorDroid(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = params.getObjId("droid");
        setObjVar(self, "armorDroid", droid);
        return SCRIPT_CONTINUE;
    }
    public int addJetPackDroid(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = params.getObjId("droid");
        setObjVar(self, "jetPackDroid", droid);
        return SCRIPT_CONTINUE;
    }
    public int cleanOutRoom(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = null;
        if (hasObjVar(self, "tailorDroid"))
        {
            droid = getObjIdObjVar(self, "tailorDroid");
            if (isIdValid(droid))
            {
                messageTo(droid, "roomReset", null, 1, false);
            }
        }
        if (hasObjVar(self, "engineerDroid"))
        {
            droid = getObjIdObjVar(self, "engineerDroid");
            if (isIdValid(droid))
            {
                messageTo(droid, "roomReset", null, 1, false);
            }
        }
        if (hasObjVar(self, "jetPackDroid"))
        {
            droid = getObjIdObjVar(self, "jetPackDroid");
            if (isIdValid(droid))
            {
                messageTo(droid, "roomReset", null, 1, false);
            }
        }
        if (hasObjVar(self, "armorDroid"))
        {
            droid = getObjIdObjVar(self, "armorDroid");
            if (isIdValid(droid))
            {
                messageTo(droid, "roomReset", null, 1, false);
            }
        }
        obj_id player = params.getObjId("player");
        location exitPoint = new location(-4648, 0, 4331, "endor", null);
        if (group.isGrouped(player))
        {
            obj_id groupObj = getGroupObject(player);
            obj_id[] groupMembers = getGroupMemberIds(groupObj);
            int numGroupMembers = groupMembers.length;
            for (int f = 0; f < numGroupMembers; f++)
            {
                obj_id groupie = groupMembers[f];
                if (isIdValid(groupie))
                {
                    if (groupie != player)
                    {
                        utils.warpPlayer(groupie, exitPoint);
                        detachScript(groupie, "theme_park.dungeon.death_watch_bunker.death_script");
                    }
                }
            }
        }
        else 
        {
            utils.warpPlayer(player, exitPoint);
            detachScript(player, "theme_park.dungeon.death_watch_bunker.death_script");
        }
        removeAll(self);
        return SCRIPT_CONTINUE;
    }
}
