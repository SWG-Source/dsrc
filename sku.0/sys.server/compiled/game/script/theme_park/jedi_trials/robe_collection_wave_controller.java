package script.theme_park.jedi_trials;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.create;
import script.library.prose;
import script.library.storyteller;
import script.library.sui;
import script.library.trial;
import script.library.utils;

public class robe_collection_wave_controller extends script.base_script
{
    public robe_collection_wave_controller()
    {
    }
    public static final String DATATABLE = "datatables/quest/jedi_collection/jedi_robe_collection.iff";
    public static final String DATA_SLOT_PREREQ = "slot_prereq";
    public static final String DATA_SLOT_AWARDED = "slot_awarded";
    public static final String DATA_EVENT_SPAWN = "event_spawn";
    public static final String DATA_SPAWN_X = "event_spawn_x";
    public static final String DATA_SPAWN_Y = "event_spawn_y";
    public static final String DATA_SPAWN_Z = "event_spawn_z";
    public static final String DATA_SPAWN_YAW = "event_spawn_yaw";
    public static final String DATA_SPAWN_FX = "client_effect";
    public static final String DATA_STARTING_MSG = "starting_msg";
    public static final String DATA_EVENT_SCRIPT = "event_script";
    public static final String DATA_FAILURE_DEBUFF = "failure_debuff";
    public static final String OBJVAR_EVENT_NAME = "jediRobeEventName";
    public static final String SCRIPTVAR_EVENT_PLAYER = "jediRobeEventPlayer";
    public static final String SCRIPTVAR_CHILDRENLIST = "jediRobeEventChildrenList";
    public static final int EVENT_DEFAULT_AUTO_RESET = 600;
    public static final boolean LOGGING = false;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, new string_id("quest2", "jedi_event_general_menu"));
        if (isGod(player))
        {
            menu = menuInfo.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("quest2", "jedi_event_reset_menu"));
        }
        if (isEligibleForJediEvent(self, player))
        {
            menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, new string_id("quest2", "jedi_event_start_menu"));
        }
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        if (menuInfoData != null)
        {
            menuInfoData.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, new string_id("quest2", "jedi_event_start_dead"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (ai_lib.isInCombat(player))
            {
                sendSystemMessage(player, new string_id("quest2", "jedi_event_start_in_combat"));
                return SCRIPT_CONTINUE;
            }
            if (isEligibleForJediEvent(self, player))
            {
                if (utils.hasScriptVar(self, SCRIPTVAR_EVENT_PLAYER))
                {
                    sendSystemMessage(player, new string_id("quest/groundquests", "wave_event_already_underway"));
                    return SCRIPT_CONTINUE;
                }
                jediEventBegin(self, player);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                dictionary eventData = getJediEventData(self);
                if (eventData != null)
                {
                    if (hasCompletedCollectionSlot(player, eventData.getString(DATA_SLOT_AWARDED)))
                    {
                        sendSystemMessage(player, new string_id("quest2", "jedi_event_already_has"));
                        return SCRIPT_CONTINUE;
                    }
                    else if (hasJediEventFailedBuff(self, player))
                    {
                        sendSystemMessage(player, new string_id("quest2", "jedi_event_failed_buff"));
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (isGod(player))
            {
                sendSystemMessage(player, "Reseting wave event controller...", "");
                clearEventArea(self);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(player, new string_id("quest/groundquests", "retrieve_item_no_interest"));
        return SCRIPT_CONTINUE;
    }
    public boolean isEligibleForJediEvent(obj_id self, obj_id player) throws InterruptedException
    {
        if (utils.isProfession(player, utils.FORCE_SENSITIVE))
        {
            if (hasObjVar(self, OBJVAR_EVENT_NAME))
            {
                dictionary eventData = getJediEventData(self);
                if (eventData != null)
                {
                    String slotPrereq = eventData.getString(DATA_SLOT_PREREQ);
                    String slotAwarded = eventData.getString(DATA_SLOT_AWARDED);
                    if (hasCompletedCollectionSlot(player, slotPrereq))
                    {
                        if (!hasCompletedCollectionSlot(player, slotAwarded) && !hasJediEventFailedBuff(self, player))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public dictionary getJediEventData(obj_id self) throws InterruptedException
    {
        String eventName = getStringObjVar(self, OBJVAR_EVENT_NAME);
        if (eventName != null && eventName.length() > 0)
        {
            return dataTableGetRow(DATATABLE, eventName);
        }
        return null;
    }
    public boolean hasJediEventFailedBuff(obj_id self, obj_id player) throws InterruptedException
    {
        dictionary eventData = getJediEventData(self);
        if (eventData != null)
        {
            String failureDebuff = eventData.getString(DATA_FAILURE_DEBUFF);
            if (buff.hasBuff(player, failureDebuff))
            {
                return true;
            }
        }
        return false;
    }
    public void jediEventBegin(obj_id self, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(self, OBJVAR_EVENT_NAME))
        {
            return;
        }
        String eventName = getStringObjVar(self, OBJVAR_EVENT_NAME);
        dictionary eventData = getJediEventData(self);
        if (eventData == null || eventData.isEmpty())
        {
            return;
        }
        clearEventArea(self);
        dictionary resetDict = trial.getSessionDict(self);
        messageTo(self, "defaultEventReset", resetDict, EVENT_DEFAULT_AUTO_RESET, false);
        utils.setScriptVar(self, SCRIPTVAR_EVENT_PLAYER, player);
        string_id startingMessage = new string_id("quest2", eventData.getString(DATA_STARTING_MSG));
        sendSystemMessage(player, startingMessage);
        messageTo(self, "handleStartJediEvent", null, 1, false);
        return;
    }
    public int handleStartJediEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, OBJVAR_EVENT_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_EVENT_PLAYER);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String eventName = getStringObjVar(self, OBJVAR_EVENT_NAME);
        dictionary eventData = getJediEventData(self);
        if (eventData == null || eventData.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        location statueLoc = getLocation(self);
        float statueYaw = getYaw(self);
        String primaryTarget = eventData.getString(DATA_EVENT_SPAWN);
        float spawnX = eventData.getFloat(DATA_SPAWN_X);
        float spawnY = eventData.getFloat(DATA_SPAWN_Y);
        float spawnZ = eventData.getFloat(DATA_SPAWN_Z);
        float spawnYaw = eventData.getFloat(DATA_SPAWN_YAW);
        String spawnFx = eventData.getString(DATA_SPAWN_FX);
        location spawnLoc = new location(statueLoc.x + spawnX, statueLoc.y + spawnY, statueLoc.z + spawnZ);
        if (statueYaw != 0)
        {
            spawnLoc = storyteller.rotateLocationXZ(statueLoc, spawnLoc, statueYaw);
        }
        spawnYaw -= (0 - statueYaw);
        playClientEffectLoc(getPlayerCreaturesInRange(spawnLoc, 100.0f), spawnFx, spawnLoc, 0.0f);
        obj_id spawnedTarget = create.object(primaryTarget, spawnLoc);
        if (!isIdValid(spawnedTarget))
        {
            messageTo(self, "cleanupEvent", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        setYaw(spawnedTarget, spawnYaw);
        setHibernationDelay(spawnedTarget, 3600.0f);
        setAILeashTime(spawnedTarget, 3600.0f);
        setFloatingTime(spawnedTarget, 0);
        trial.setParent(self, spawnedTarget, true);
        trial.setInterest(spawnedTarget);
        attachScript(spawnedTarget, eventData.getString(DATA_EVENT_SCRIPT));
        utils.setScriptVar(spawnedTarget, SCRIPTVAR_EVENT_PLAYER, player);
        setObjVar(spawnedTarget, OBJVAR_EVENT_NAME, eventName);
        pvpSetAlignedFaction(spawnedTarget, (-377582139));
        pvpSetPermanentPersonalEnemyFlag(spawnedTarget, player);
        startCombat(spawnedTarget, player);
        addHate(spawnedTarget, player, 1000.0f);
        Vector jediEventChildrenList = new Vector();
        jediEventChildrenList.setSize(0);
        utils.addElement(jediEventChildrenList, spawnedTarget);
        utils.setScriptVar(self, SCRIPTVAR_CHILDRENLIST, jediEventChildrenList);
        return SCRIPT_CONTINUE;
    }
    public int jediEventChildDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "cleanupEvent", null, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
    public int defaultEventReset(obj_id self, dictionary params) throws InterruptedException
    {
        int passed = params.getInt(trial.MESSAGE_SESSION);
        int current = trial.getSession(self);
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanupEvent(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public void clearEventArea(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_EVENT_PLAYER);
        utils.removeScriptVar(self, SCRIPTVAR_CHILDRENLIST);
        trial.bumpSession(self);
        obj_id[] objects = trial.getChildrenInRange(self, self, 1000.0f);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (int i = 0; i < objects.length; i++)
        {
            obj_id child = objects[i];
            if (isIdValid(child) && child != self && !isPlayer(child))
            {
                if (!hasScript(child, "corpse.ai_corpse"))
                {
                    trial.cleanupObject(child);
                }
            }
        }
        return;
    }
}
