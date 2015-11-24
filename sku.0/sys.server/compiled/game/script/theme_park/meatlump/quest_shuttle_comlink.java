package script.theme_park.meatlump;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.city;
import script.library.create;
import script.library.groundquests;
import script.library.locations;
import script.library.utils;

public class quest_shuttle_comlink extends script.base_script
{
    public quest_shuttle_comlink()
    {
    }
    public static final boolean LOGGING_ON = false;
    public static final string_id CALL_SHIP = new string_id("meatlump/meatlump", "comlink_use");
    public static final string_id MUST_DISMOUNT = new string_id("quest/ground/util/quest_giver_object", "must_dismount");
    public static final string_id PLAYER_IN_CELL = new string_id("quest/ground/util/quest_giver_object", "player_in_cell");
    public static final string_id NOT_WHILE_INCAPPED = new string_id("quest/ground/util/quest_giver_object", "not_while_incapped");
    public static final string_id SID_NOT_WHILE_IN_COMBAT = new string_id("base_player", "not_while_in_combat");
    public static final string_id SID_COMLINK_ERROR = new string_id("meatlump/meatlump", "comlink_error");
    public static final string_id SID_COMLINK_WRONG_PLANET = new string_id("meatlump/meatlump", "comlink_wrong_planet");
    public static final String MEATLUMP_LOG = "meatlump_weapons_delivery";
    public static final String QUEST_NAME = "quest/mtp_hideout_retrieve_delivery";
    public static final String QUEST_BEGINNING_TASK_ACTIVE = "useComlink";
    public static final String QUEST_SHUTTLE_TASK_ACTIVE = "shuttleLanded";
    public static final String QUEST_ENEMIES_KILLED_ACTIVE = "okCallShuttle";
    public static final String ENEMY_MOB = "mtp_delivery_ambush_ragtag_blackjack";
    public static final String SHUTTLE_TEMPLATE = "object/creature/npc/theme_park/event_transport.iff";
    public static final String SHUTTLE_LAND_QUEST_SIGNAL = "shuttleLandedDelivery";
    public static final String SHUTTLE_COMM_TOO_HOT_QUEST_SIGNAL = "shuttleTooHotCommSignal";
    public static final String SHUTTLE_COMM_OK_QUEST_SIGNAL = "shuttleOkCommSignal";
    public static final String SHUTTLE_SCRIPT = "theme_park.meatlump.quest_shuttle_event";
    public static final String ENEMY_SCRIPT = "theme_park.meatlump.quest_ragtag_attack_event";
    public static final String NO_TRADE_SCRIPT = "item.special.nomove";
    public static final String ENEMY_SPAWNED_OBJVAR = "meatlump.enemy_spawned";
    public static final String ENEMY_KILLED_OBJVAR = "meatlump.enemy_killed";
    public static final String OWNER_OBJVAR = "meatlump.owner";
    public static final String COMLINK_OBJVAR = "meatlump.comlink";
    public static final String USED_OBJVAR = "meatlump.been_used";
    public static final String RAGTAG_ARRAY_OBJVAR = "meatlump.ragtag_array";
    public static final int NORMAL_MOB_COUNT = 2;
    public static final int EPIC_MOB_COUNT = 4;
    public static final int MOB_LEVEL_HANDICAP = 6;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, NO_TRADE_SCRIPT);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("OnObjectMenuRequest init ");
        if (!utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(self, USED_OBJVAR) && !hasObjVar(self, OWNER_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(self, USED_OBJVAR) && hasObjVar(self, OWNER_OBJVAR))
        {
            if (getObjIdObjVar(self, OWNER_OBJVAR) != player)
            {
                return SCRIPT_CONTINUE;
            }
        }
        blog("OnObjectMenuRequest giving menu option.");
        int menuOption = mi.addRootMenu(menu_info_types.ITEM_USE, CALL_SHIP);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        blog("OnObjectMenuSelect init.");
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, USED_OBJVAR) && hasObjVar(self, OWNER_OBJVAR))
        {
            blog("OnObjectMenuSelect - has USED_OBJVAR and OWNER_OBJVAR. Comparing to check player owner.");
            if (getObjIdObjVar(self, OWNER_OBJVAR) != player)
            {
                blog("OnObjectMenuSelect - has USED_OBJVAR and OWNER_OBJVAR buy the incorrect player is using comlink.");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                blog("OnObjectMenuSelect - has USED_OBJVAR and OWNER_OBJVAR. Comparing to check player owner.");
            }
        }
        if (item == menu_info_types.ITEM_USE)
        {
            blog("OnObjectMenuSelect - ITEM_USE");
            if (!wildLocation(player) || !checkScene(self, player))
            {
                blog("OnObjectMenuSelect - Not WIld Loc");
                return SCRIPT_CONTINUE;
            }
            else if (getState(player, STATE_COMBAT) > 0)
            {
                sendSystemMessage(player, SID_NOT_WHILE_IN_COMBAT);
                return SCRIPT_CONTINUE;
            }
            else if (getState(player, STATE_RIDING_MOUNT) == 1)
            {
                sendSystemMessage(player, MUST_DISMOUNT);
                return SCRIPT_CONTINUE;
            }
            else if (isDead(player) || isIncapacitated(player))
            {
                sendSystemMessage(player, NOT_WHILE_INCAPPED);
                return SCRIPT_CONTINUE;
            }
            else if (getTopMostContainer(player) != player)
            {
                sendSystemMessage(player, PLAYER_IN_CELL);
                return SCRIPT_CONTINUE;
            }
            else if (!groundquests.isTaskActive(player, QUEST_NAME, QUEST_BEGINNING_TASK_ACTIVE) && !groundquests.isTaskActive(player, QUEST_NAME, QUEST_SHUTTLE_TASK_ACTIVE))
            {
                blog("OnObjectMenuSelect - QUEST_BEGINNING_TASK_ACTIVE not found");
                return SCRIPT_CONTINUE;
            }
            if (!spawnEvent(self, player))
            {
                sendSystemMessage(player, SID_COMLINK_ERROR);
                blog("OnObjectMenuSelect - spawnEvent FAILED");
            }
            else 
            {
                blog("OnObjectMenuSelect - spawnEvent SUCCESS");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void destroySelf(obj_id self) throws InterruptedException
    {
        detachScript(self, "item.special.nodestroy");
        destroyObject(self);
    }
    public boolean wildLocation(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        location loc = getLocation(player);
        if (isIdValid(loc.cell))
        {
            sendSystemMessage(player, new string_id("combat_effects", "not_indoors"));
            return false;
        }
        if (locations.isInMissionCity(loc))
        {
            sendSystemMessage(player, new string_id("combat_effects", "not_in_city"));
            return false;
        }
        if (city.isInCity(loc))
        {
            sendSystemMessage(player, new string_id("combat_effects", "not_in_city"));
            return false;
        }
        return true;
    }
    public boolean spawnEvent(obj_id self, obj_id player) throws InterruptedException
    {
        blog("spawnEvent - Init");
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        else if (!wildLocation(player))
        {
            return false;
        }
        blog("spawnEvent - Wild Location confirmed");
        if (!hasObjVar(self, USED_OBJVAR))
        {
            setObjVar(self, USED_OBJVAR, true);
        }
        if (!hasObjVar(self, OWNER_OBJVAR))
        {
            setObjVar(self, OWNER_OBJVAR, player);
        }
        if (!hasObjVar(self, ENEMY_SPAWNED_OBJVAR) && !groundquests.isTaskActive(player, QUEST_NAME, QUEST_ENEMIES_KILLED_ACTIVE))
        {
            blog("spawnEvent - Player is spawing Ragtags");
            spawnRagTags(self, player);
            return true;
        }
        else if (hasObjVar(self, ENEMY_SPAWNED_OBJVAR) && groundquests.isTaskActive(player, QUEST_NAME, QUEST_SHUTTLE_TASK_ACTIVE))
        {
            blog("spawnEvent - Player is spawing Shuttle");
            spawnShuttle(self, player);
            return true;
        }
        return false;
    }
    public boolean spawnShuttle(obj_id self, obj_id player) throws InterruptedException
    {
        blog("spawnShuttle - init");
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        blog("spawnShuttle - player validated");
        location landingLoc = getRandomLocationAroundPlayer(self, player);
        blog("spawnShuttle - received landingLoc: " + landingLoc);
        obj_id shuttle = createObject(SHUTTLE_TEMPLATE, landingLoc);
        if (!isIdValid(shuttle))
        {
            return false;
        }
        blog("spawnShuttle - shuttle: " + shuttle);
        attachScript(shuttle, SHUTTLE_SCRIPT);
        setObjVar(shuttle, OWNER_OBJVAR, player);
        groundquests.sendSignal(player, SHUTTLE_LAND_QUEST_SIGNAL);
        blog("spawnShuttle - shuttle landing sequesnce sent");
        messageTo(shuttle, "startLandingSequence", null, 0.0f, false);
        destroySelf(self);
        return true;
    }
    public boolean spawnRagTags(obj_id self, obj_id player) throws InterruptedException
    {
        blog("spawnRagTags - init");
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        blog("spawnRagTags - about to send signal to Quest");
        groundquests.sendSignal(player, SHUTTLE_COMM_TOO_HOT_QUEST_SIGNAL);
        blog("spawnRagTags - sent signal to Quest");
        messageTo(self, "spawnEnemies", null, 2, false);
        blog("spawnRagTags - setting objvar on comlink");
        setObjVar(self, ENEMY_SPAWNED_OBJVAR, true);
        return true;
    }
    public location getRandomLocationAroundPlayer(obj_id self, obj_id player) throws InterruptedException
    {
        blog("getRandomLocationAroundPlayer - init");
        if (!isIdValid(player) || !exists(player))
        {
            return null;
        }
        location loc = getLocation(player);
        float treasureXdelta = rand(15f, 18f);
        float treasureZdelta = rand(15f, 18f);
        loc.x += treasureXdelta;
        loc.z += treasureZdelta;
        loc.y = getHeightAtLocation(loc.x, loc.z);
        blog("getRandomLocationAroundPlayer - returning: " + loc);
        return loc;
    }
    public int spawnEnemies(obj_id self, dictionary params) throws InterruptedException
    {
        blog("spawnEnemies - Init");
        obj_id player = getObjIdObjVar(self, OWNER_OBJVAR);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        blog("spawnEnemies - Creating empty resizable obj_id array");
        Vector ragtags = null;
        int mobLevel = (getLevel(player) - MOB_LEVEL_HANDICAP);
        blog("spawnEnemies - Mob Level for all Spawns: " + mobLevel);
        for (int i = 0; i < NORMAL_MOB_COUNT; i++)
        {
            location spawnLoc = getRandomLocationAroundPlayer(self, player);
            obj_id mob = create.object(ENEMY_MOB, spawnLoc, mobLevel);
            if (!isValidId(mob) || !exists(mob))
            {
                return SCRIPT_CONTINUE;
            }
            attachScript(mob, ENEMY_SCRIPT);
            faceTo(mob, player);
            setObjVar(mob, OWNER_OBJVAR, player);
            setObjVar(mob, COMLINK_OBJVAR, self);
            if (i == 0)
            {
                messageTo(mob, "barkAttack", null, 2, false);
            }
            ragtags = utils.addElement(ragtags, mob);
        }
        blog("spawnEnemies - Successfully finished loop");
        if (ragtags != null && ragtags.size() > 0)
        {
            setObjVar(self, RAGTAG_ARRAY_OBJVAR, ragtags);
        }
        blog("spawnEnemies - ragtag list length: " + ragtags.size());
        return SCRIPT_CONTINUE;
    }
    public boolean checkScene(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        String scene = getCurrentSceneName();
        if (scene.equals("corellia"))
        {
            return true;
        }
        sendSystemMessage(player, SID_COMLINK_WRONG_PLANET);
        return false;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG(MEATLUMP_LOG, msg);
        }
        return true;
    }
}
