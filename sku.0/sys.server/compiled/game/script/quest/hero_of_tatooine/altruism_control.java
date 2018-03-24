package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.create;
import script.library.groundquests;
import script.library.player_structure;
import script.library.utils;

public class altruism_control extends script.base_script
{
    public altruism_control()
    {
    }
    public static final String MARK_OF_ALTRUISM = "object/tangible/loot/quest/hero_of_tatooine/mark_altruism.iff";
    public static final String ALTRUISM_OBJVAR = "quest.hero_of_tatooine.altruism";
    public static final String ALTRUISM_WAYPOINT = ALTRUISM_OBJVAR + "_waypoint";
    public static final String ALTRUISM_COMPLETE = ALTRUISM_OBJVAR + ".complete";
    public static final String ALTRUISM_LAST_SUCCESS = ALTRUISM_OBJVAR + ".last_success";
    public static final String ALTRUISM_CONTROL = ALTRUISM_OBJVAR + ".control";
    public static final String ALTRUISM_LEADER = ALTRUISM_OBJVAR + ".leader";
    public static final String ALTRUISM_ACTIVE = ALTRUISM_OBJVAR + ".active";
    public static final String ALTRUISM_EXPLOSIVE = ALTRUISM_ACTIVE + ".explosive";
    public static final String ALTRUISM_BIG_ROCK = ALTRUISM_ACTIVE + ".big_rock";
    public static final String ALTRUISM_LITTLE_ROCK = ALTRUISM_ACTIVE + ".little_rock";
    public static final String ALTRUISM_MOTHER = ALTRUISM_ACTIVE + ".mother";
    public static final String ALTRUISM_DAUGHTER = ALTRUISM_ACTIVE + ".daughter";
    public static final string_id EXPLOSION_DMG = new string_id("quest/hero_of_tatooine/system_messages", "altruism_explosion_damage");
    public static final string_id QUEST_FAIL = new string_id("quest/hero_of_tatooine/system_messages", "altruism_quest_fail");
    public static final string_id QUEST_SUCCESS = new string_id("quest/hero_of_tatooine/system_messages", "altruism_quest_success");
    public static final string_id NPC_FAREWELL = new string_id("quest/hero_of_tatooine/system_messages", "altruism_npc_farewell");
    public static final string_id ALTRUISM_INV_FULL = new string_id("quest/hero_of_tatooine/system_messages", "altruism_inv_full");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, ALTRUISM_ACTIVE))
        {
            cleanupObjects(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isIdValid(srcContainer) || !isIdValid(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(item, ALTRUISM_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(item, ALTRUISM_COMPLETE))
        {
            return SCRIPT_CONTINUE;
        }
        int status = getIntObjVar(item, ALTRUISM_OBJVAR);
        if (status != 1)
        {
            if (status == 2)
            {
                obj_id mother = obj_id.NULL_ID;
                obj_id[] objects = player_structure.getObjectsInBuilding(self);
                if (objects != null || objects.length > 0)
                {
                    for (int i = 0; i < objects.length; i++)
                    {
                        obj_id object = objects[i];
                        if (isIdValid(object) && isMob(object))
                        {
                            String cName = getCreatureName(object);
                            if (cName != null && cName.equals("quest_hero_of_tatooine_farmer_wife"))
                            {
                                mother = object;
                                break;
                            }
                        }
                    }
                }
                if (!isIdValid(mother))
                {
                    dictionary webster = new dictionary();
                    webster.put("player", item);
                    messageTo(self, "handleAltruismResetCheck", webster, 1, false);
                }
                else 
                {
                    obj_id currentQuestPlayer = getObjIdObjVar(mother, ALTRUISM_LEADER);
                    if (currentQuestPlayer != item && !badge.hasBadge(item, "poi_factoryliberation"))
                    {
                        setObjVar(item, ALTRUISM_OBJVAR, 1);
                    }
                }
            }
            return SCRIPT_CONTINUE;
        }
        String cellName = getCellName(srcContainer);
        if (cellName == null || cellName.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (cellName.equals("r1"))
        {
            if (hasObjVar(self, ALTRUISM_LAST_SUCCESS))
            {
                int last_time = getIntObjVar(self, ALTRUISM_LAST_SUCCESS);
                int current_time = getGameTime();
                int time_delta = current_time = last_time;
                if (time_delta < 360)
                {
                }
            }
            if (!hasObjVar(self, ALTRUISM_ACTIVE))
            {
                spawnQuestObjects(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!hasObjVar(self, ALTRUISM_ACTIVE))
        {
            return SCRIPT_CONTINUE;
        }
        if (destContainer == null || !isIdValid(destContainer))
        {
            if (isPlayer(item))
            {
                if (hasScript(item, "quest.hero_of_tatooine.altruism_player"))
                {
                    return SCRIPT_CONTINUE;
                }
                if (!hasObjVar(item, ALTRUISM_OBJVAR))
                {
                    return SCRIPT_CONTINUE;
                }
                if (hasObjVar(item, ALTRUISM_COMPLETE))
                {
                    return SCRIPT_CONTINUE;
                }
                if (!caveHasQuestPlayers(self))
                {
                    cleanupObjects(self);
                }
            }
            else 
            {
                obj_id mother = getObjIdObjVar(self, ALTRUISM_MOTHER);
                if (isIdValid(mother))
                {
                    if (item == mother)
                    {
                        succeedQuest(self, item);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAltruismResetCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (isIdValid(player) && exists(player))
        {
            if (!badge.hasBadge(player, "poi_factoryliberation"))
            {
                setObjVar(player, ALTRUISM_OBJVAR, 1);
                cleanupObjects(self);
                spawnQuestObjects(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleExplosion(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id big_rock = getObjIdObjVar(self, ALTRUISM_BIG_ROCK);
        obj_id little_rock = getObjIdObjVar(self, ALTRUISM_LITTLE_ROCK);
        obj_id[] players_dmg = getPlayerCreaturesInRange(little_rock, 4.0f);
        if (players_dmg != null && players_dmg.length > 0)
        {
            for (int i = 0; i < players_dmg.length; i++)
            {
                attribute[] attrib = getAttribs(players_dmg[i]);
                int dmg = 200;
                for (int j = 0; j < attrib.length; j += NUM_ATTRIBUTES_PER_GROUP)
                {
                    attrib[j] = new attribute(attrib[j].getType(), (attrib[j].getValue() - dmg));
                    if (attrib[j].getValue() < 1)
                    {
                        attrib[j] = new attribute(attrib[j].getType(), 1);
                    }
                }
                if (setAttribs(players_dmg[i], attrib))
                {
                    sendSystemMessage(players_dmg[i], EXPLOSION_DMG);
                }
            }
        }
        obj_id mother = getObjIdObjVar(self, ALTRUISM_MOTHER);
        obj_id player = params.getObjId("player");
        setObjVar(mother, ALTRUISM_LEADER, player);
        if (isIdValid(big_rock) && exists(big_rock))
        {
            destroyObject(big_rock);
        }
        if (isIdValid(little_rock) && exists(little_rock))
        {
            obj_id[] contents = getContents(little_rock);
            if (contents != null && contents.length > 0)
            {
                for (int k = 0; k < contents.length; k++)
                {
                    destroyObject(contents[k]);
                }
            }
            destroyObject(little_rock);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestFail(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        destroyWaypoint(player);
        removeObjVar(player, ALTRUISM_OBJVAR);
        detachScript(player, "quest.hero_of_tatooine.altruism_player");
        cleanupObjects(self);
        sendSystemMessage(player, QUEST_FAIL);
        return SCRIPT_CONTINUE;
    }
    public int handleValidateContent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, ALTRUISM_ACTIVE))
        {
            return SCRIPT_CONTINUE;
        }
        if (!caveHasQuestPlayers(self))
        {
            cleanupObjects(self);
        }
        else 
        {
            messageTo(self, "handleValidateContent", null, 3600.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int resetContent(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupObjects(self);
        return SCRIPT_CONTINUE;
    }
    public int setupContent(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupObjects(self);
        spawnQuestObjects(self);
        return SCRIPT_CONTINUE;
    }
    public boolean caveHasQuestPlayers(obj_id self) throws InterruptedException
    {
        obj_id[] players = player_structure.getPlayersInBuilding(self);
        if (players == null || players.length == 0)
        {
            return false;
        }
        for (int i = 0; i < players.length; i++)
        {
            if (hasObjVar(players[i], ALTRUISM_OBJVAR) && !hasObjVar(players[i], ALTRUISM_COMPLETE))
            {
                return true;
            }
        }
        return false;
    }
    public void spawnQuestObjects(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, ALTRUISM_LAST_SUCCESS))
        {
            removeObjVar(self, ALTRUISM_LAST_SUCCESS);
        }
        location my_loc = getLocation(self);
        obj_id explosive_cell = getCellId(self, "r11");
        obj_id rock_cell = getCellId(self, "r8");
        obj_id family_cell = getCellId(self, "r9");
        location explosive_loc = new location(77.76f, -46.33f, -136.96f, my_loc.area, explosive_cell);
        location big_rock_loc = new location(157.68f, -66f, -97.51f, my_loc.area, rock_cell);
        location little_rock_loc = new location(151.69f, -66f, -93.78f, my_loc.area, rock_cell);
        location mother_loc = new location(187.11f, -65.90f, -103.53f, my_loc.area, family_cell);
        location daughter_loc = new location(187.42f, -65.80f, -105.15f, my_loc.area, family_cell);
        obj_id explosive = createObject("object/tangible/item/quest/hero_of_tatooine/explosives_crate.iff", explosive_loc);
        obj_id big_rock = createObject("object/static/structure/general/cave_wall_tato_style_01.iff", big_rock_loc);
        obj_id little_rock = createObject("object/tangible/container/quest/hero_of_tatooine/rock_crevice.iff", little_rock_loc);
        obj_id mother = create.object("quest_hero_of_tatooine_farmer_wife", mother_loc);
        obj_id daughter = create.object("quest_hero_of_tatooine_farmer_daughter", daughter_loc);
        setYaw(mother, -102);
        setYaw(daughter, -106);
        setObjVar(little_rock, ALTRUISM_CONTROL, self);
        setObjVar(mother, ALTRUISM_DAUGHTER, daughter);
        setObjVar(daughter, ALTRUISM_MOTHER, mother);
        attachScript(explosive, "quest.hero_of_tatooine.explosives_crate");
        attachScript(little_rock, "quest.hero_of_tatooine.rock_crevice");
        attachScript(mother, "conversation.quest_hero_of_tatooine_mother");
        attachScript(mother, "quest.hero_of_tatooine.altruism_mother");
        setObjVar(self, ALTRUISM_EXPLOSIVE, explosive);
        setObjVar(self, ALTRUISM_BIG_ROCK, big_rock);
        setObjVar(self, ALTRUISM_LITTLE_ROCK, little_rock);
        setObjVar(self, ALTRUISM_MOTHER, mother);
        setObjVar(self, ALTRUISM_DAUGHTER, daughter);
        messageTo(self, "handleValidateContent", null, 360.0f, false);
    }
    public void succeedQuest(obj_id control, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(npc, ALTRUISM_LEADER))
        {
            return;
        }
        obj_id player = getObjIdObjVar(npc, ALTRUISM_LEADER);
        if (!isIdValid(player))
        {
            return;
        }
        destroyWaypoint(player);
        obj_id mark_altruism = createObjectInInventoryAllowOverload(MARK_OF_ALTRUISM, player);
        removeObjVar(player, ALTRUISM_OBJVAR);
        detachScript(player, "quest.hero_of_tatooine.altruism_player");
        setObjVar(player, ALTRUISM_COMPLETE, 1);
        setObjVar(control, ALTRUISM_LAST_SUCCESS, getGameTime());
        if (!badge.hasBadge(player, "poi_factoryliberation"))
        {
            badge.grantBadge(player, "poi_factoryliberation");
        }
        groundquests.sendSignal(player, "hero_of_tatooine_main_altruism");
        CustomerServiceLog("quest", "HERO OF TATOOINE - %TU has acquired the Mark of Altruism", player);
        sendSystemMessage(player, QUEST_SUCCESS);
        if (!isIdValid(mark_altruism))
        {
            sendSystemMessage(player, ALTRUISM_INV_FULL);
            setObjVar(player, "quest.hero_of_tatooine.owed.altruism", 1);
        }
        ai_lib.aiStopFollowing(npc);
        chat.chat(npc, NPC_FAREWELL);
        ai_lib.pathAwayFrom(npc, control);
        messageTo(control, "resetContent", null, 20.0f, false);
    }
    public void destroyWaypoint(obj_id player) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, ALTRUISM_WAYPOINT);
        if (isIdValid(waypoint))
        {
            setWaypointActive(waypoint, false);
            setWaypointVisible(waypoint, false);
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(player, ALTRUISM_WAYPOINT);
    }
    public void cleanupObjects(obj_id self) throws InterruptedException
    {
        obj_id explosive = getObjIdObjVar(self, ALTRUISM_EXPLOSIVE);
        obj_id big_rock = getObjIdObjVar(self, ALTRUISM_BIG_ROCK);
        obj_id little_rock = getObjIdObjVar(self, ALTRUISM_LITTLE_ROCK);
        obj_id mother = getObjIdObjVar(self, ALTRUISM_MOTHER);
        obj_id daughter = getObjIdObjVar(self, ALTRUISM_DAUGHTER);
        if (isIdValid(explosive) && exists(explosive))
        {
            destroyObject(explosive);
        }
        if (isIdValid(big_rock) && exists(big_rock))
        {
            destroyObject(big_rock);
        }
        if (isIdValid(little_rock) && exists(little_rock))
        {
            destroyObject(little_rock);
        }
        if (isIdValid(mother) && exists(mother))
        {
            destroyObject(mother);
        }
        if (isIdValid(daughter) && exists(daughter))
        {
            destroyObject(daughter);
        }
        removeObjVar(self, ALTRUISM_ACTIVE);
    }
}
