package script.event.lifeday;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.groundquests;
import script.library.create;
import script.library.trial;
import script.library.buff;
import script.library.factions;
import script.library.stealth;
import script.library.static_item;

public class lifeday_objective extends script.base_script
{
    public lifeday_objective()
    {
    }
    public static final String LIFEDAY = "event/life_day";
    public static final string_id SID_ALREADY_DRESSED = new string_id(LIFEDAY, "already_dressed");
    public static final string_id SID_ALREADY_STOMPED = new string_id(LIFEDAY, "already_stomped");
    public static final string_id SID_WRONG_FACTION = new string_id(LIFEDAY, "wrong_faction");
    public static final string_id SID_LOCKED_OUT = new string_id(LIFEDAY, "locked_out");
    public static final string_id SID_IMPERIAL_START = new string_id(LIFEDAY, "imperial_start");
    public static final string_id SID_REBEL_START = new string_id(LIFEDAY, "rebel_start");
    public static final string_id SID_IMPERIAL_COMPETITIVE_START = new string_id(LIFEDAY, "imperial_competitive_start");
    public static final string_id SID_REBEL_COMPETITIVE_START = new string_id(LIFEDAY, "rebel_competitive_start");
    public static final string_id SID_NEUTRAL_IMP_COMP_START = new string_id(LIFEDAY, "neutral_imperial_comp_start");
    public static final string_id SID_NEUTRAL_REB_COMP_START = new string_id(LIFEDAY, "neutral_rebel_comp_start");
    public static final string_id SID_NEUTRAL_IMP_CAS_START = new string_id(LIFEDAY, "neutral_imperial_cas_start");
    public static final string_id SID_NEUTRAL_REB_CAS_START = new string_id(LIFEDAY, "neutral_rebel_cas_start");
    public static final string_id SID_MNU_DRESS = new string_id(LIFEDAY, "dress_menu_header");
    public static final string_id SID_MNU_STOMP = new string_id(LIFEDAY, "stomp_menu_header");
    public static final string_id SID_ZIP_DRESSING = new string_id(LIFEDAY, "dress_zip_bar_text");
    public static final string_id SID_ZIP_STOMPING = new string_id(LIFEDAY, "stomp_zip_bar_text");
    public static final string_id SID_TOKENS_REBEL = new string_id(LIFEDAY, "tokens_rebel");
    public static final string_id SID_TOKENS_REBEL_HIGH = new string_id(LIFEDAY, "tokens_rebel_high");
    public static final string_id SID_TOKENS_IMPERIAL = new string_id(LIFEDAY, "tokens_imperial");
    public static final string_id SID_TOKENS_IMPERIAL_HIGH = new string_id(LIFEDAY, "tokens_imperial_high");
    public static final string_id SID_PICK_PATH = new string_id(LIFEDAY, "pick_path");
    public static final string_id SID_REACHED_LIMIT = new string_id(LIFEDAY, "reached_limit");
    public static final string_id SID_MOUNTED = new string_id(LIFEDAY, "mounted");
    public static final String LOCKED_OUT = "lifeday.locked_out";
    public static final String LIFEDAY_TIMESTAMP = "lifeday.time_stamp";
    public static final String NEUTRAL_IMPERIAL = "lifeday.neutral_imperial";
    public static final String NEUTRAL_REBEL = "lifeday.neutral_rebel";
    public static final String TOKEN_COUNTER = "lifeday.token_counter";
    public static final String IMPERIAL_COMPETITIVE_BUFF = "event_lifeday_imperial_competitive";
    public static final String REBEL_COMPETITIVE_BUFF = "event_lifeday_rebel_competitive";
    public static final String IMPERIAL_COUNTER_BUFF = "event_lifeday_imperial_present_counter";
    public static final String REBEL_COUNTER_BUFF = "event_lifeday_rebel_tree_counter";
    public static final String PICKED_CASUAL_BUFF_REBEL = "event_lifeday_casual_rebel_lock_in";
    public static final String PICKED_CASUAL_BUFF_IMPERIAL = "event_lifeday_casual_imperial_lock_in";
    public static final String PICKED_COMPETITIVE_BUFF_REBEL = "event_lifeday_competitive_rebel_lock_in";
    public static final String PICKED_COMPETITIVE_BUFF_IMPERIAL = "event_lifeday_competitive_imperial_lock_in";
    public static final String TREE = "object/tangible/holiday/life_day/life_day_tree.iff";
    public static final String TREE_DRESSED = "object/tangible/holiday/life_day/life_day_tree_dressed.iff";
    public static final String PRESENTS = "object/tangible/holiday/life_day/life_day_present.iff";
    public static final String PRESENTS_STOMPED = "object/tangible/holiday/life_day/life_day_present_stomped.iff";
    public static final String REBEL_TOKEN = "item_event_lifeday_rebel_token";
    public static final String IMPERIAL_TOKEN = "item_event_lifeday_imperial_token";
    public static final float MIN_DESTROY_TIME = 60;
    public static final float MAX_DESTROY_TIME = 180;
    public static final int COUNTDOWN_TIMER = 3;
    public static final int NON_DECLARED_TOKENS = 5;
    public static final int DECLARED_TOKENS = 15;
    public static final int REBEL = 1;
    public static final int IMPERIAL = 2;
    public static final int DAILY_COUNTER_LIMIT = 9;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objectTemplate = getTemplateName(self);
        float destroyTimer = rand(MIN_DESTROY_TIME, MAX_DESTROY_TIME);
        if (objectTemplate.equals(TREE_DRESSED))
        {
            messageTo(self, "destroySelf", null, destroyTimer, false);
        }
        if (objectTemplate.equals(PRESENTS_STOMPED))
        {
            messageTo(self, "destroySelf", null, destroyTimer, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        obj_id target = getIntendedTarget(speaker);
        if (target != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isGod(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("endDay"))
        {
            int now = getCalendarTime();
            int secondsUntil = 10;
            int then = now + secondsUntil;
            int actuallyEnd = then - now;
            sendSystemMessageTestingOnly(speaker, "Day will end in " + actuallyEnd + " seconds");
            setObjVar(speaker, LIFEDAY_TIMESTAMP, then);
        }
        if (text.equals("endDayPlanet"))
        {
            obj_id tatooine = getPlanetByName("tatooine");
            int now = getCalendarTime();
            int secondsUntil = 10;
            int then = now + secondsUntil;
            int actuallyEnd = then - now;
            sendSystemMessageTestingOnly(speaker, "Leader board day will end in " + actuallyEnd + " seconds");
            if (!isIdValid(tatooine) || !exists(tatooine))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(tatooine, LIFEDAY_TIMESTAMP, then);
        }
        if (text.equals("applyCasual"))
        {
            buff.applyBuff(speaker, "event_lifeday_casual_buff");
        }
        if (text.equals("applyComp"))
        {
            buff.applyBuff(speaker, "event_lifeday_competitive_buff");
        }
        if (text.equals("resetMe"))
        {
            sendSystemMessageTestingOnly(speaker, "Resetting all life day obj vars and buffs");
            if (hasObjVar(speaker, "lifeday.locked_out"))
            {
                removeObjVar(speaker, "lifeday.locked_out");
            }
            if (hasObjVar(speaker, "lifeday.time_stamp"))
            {
                removeObjVar(speaker, "lifeday.time_stamp");
            }
            if (hasObjVar(speaker, "lifeday.neutral_imperial"))
            {
                removeObjVar(speaker, "lifeday.neutral_imperial");
            }
            if (hasObjVar(speaker, "lifeday.neutral_rebel"))
            {
                removeObjVar(speaker, "lifeday.neutral_rebel");
            }
            if (hasObjVar(speaker, "lifeday.token_counter"))
            {
                removeObjVar(speaker, "lifeday.token_counter");
            }
            if (buff.hasBuff(speaker, "event_lifeday_imperial_competitive"))
            {
                buff.removeBuff(speaker, "event_lifeday_imperial_competitive");
            }
            if (buff.hasBuff(speaker, "event_lifeday_rebel_competitive"))
            {
                buff.removeBuff(speaker, "event_lifeday_rebel_competitive");
            }
            if (buff.hasBuff(speaker, "event_lifeday_imperial_present_counter"))
            {
                buff.removeBuff(speaker, "event_lifeday_imperial_present_counter");
            }
            if (buff.hasBuff(speaker, "event_lifeday_rebel_tree_counter"))
            {
                buff.removeBuff(speaker, "event_lifeday_rebel_tree_counter");
            }
            if (buff.hasBuff(speaker, "event_lifeday_casual_buff"))
            {
                buff.removeBuff(speaker, "event_lifeday_casual_buff");
            }
            if (buff.hasBuff(speaker, "event_lifeday_competetive_buff"))
            {
                buff.removeBuff(speaker, "event_lifeday_competetive_buff");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        String objectTemplate = getTemplateName(self);
        if (!isDead(player) && !isIncapacitated(player))
        {
            if (objectTemplate.equals(TREE))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_DRESS);
                return SCRIPT_CONTINUE;
            }
            if (objectTemplate.equals(PRESENTS))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_STOMP);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        String objectTemplate = getTemplateName(self);
        if (item == menu_info_types.ITEM_USE)
        {
            if (!canInteract(player, self))
            {
                return SCRIPT_CONTINUE;
            }
            else if (!isDead(player) && !isIncapacitated(player))
            {
                if (objectTemplate.equals(TREE))
                {
                    String handler = "handleDressingCountdownTimer";
                    int startTime = 0;
                    int range = 3;
                    int flags = 0;
                    flags |= sui.CD_EVENT_INCAPACITATE;
                    flags |= sui.CD_EVENT_DAMAGED;
                    stealth.testInvisNonCombatAction(player, self);
                    int countdownSui = sui.smartCountdownTimerSUI(self, player, "quest_countdown_timer", SID_ZIP_DRESSING, startTime, COUNTDOWN_TIMER, handler, range, flags);
                    doAnimationAction(player, "manipulate_high");
                }
                if (objectTemplate.equals(PRESENTS))
                {
                    String handler = "handleStompingCountdownTimer";
                    int startTime = 0;
                    int range = 3;
                    int flags = 0;
                    flags |= sui.CD_EVENT_INCAPACITATE;
                    flags |= sui.CD_EVENT_DAMAGED;
                    stealth.testInvisNonCombatAction(player, self);
                    int countdownSui = sui.smartCountdownTimerSUI(self, player, "quest_countdown_timer", SID_ZIP_STOMPING, startTime, COUNTDOWN_TIMER, handler, range, flags);
                    doAnimationAction(player, "stamp_feet");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void treeDressed(obj_id self, obj_id player) throws InterruptedException
    {
        if (!factions.isDeclared(player))
        {
            giveTreat(player, NON_DECLARED_TOKENS, REBEL);
        }
        else 
        {
            if (factions.isDeclared(player))
            {
                giveTreat(player, DECLARED_TOKENS, REBEL);
            }
        }
        if (hasObjVar(self, "objParent"))
        {
            obj_id objParent = getObjIdObjVar(self, "objParent");
            if (utils.hasScriptVar(self, "deathTracker"))
            {
                if (hasObjVar(self, "fltRespawnTime"))
                {
                    location loc = utils.getLocationScriptVar(self, "deathTracker");
                    obj_id dressedTree = createObject(TREE_DRESSED, loc);
                    float respawnTime = getFloatObjVar(self, "fltRespawnTime");
                    float yaw = getYaw(self);
                    setObjVar(self, "dontCountDeath", 1);
                    setObjVar(dressedTree, "objParent", objParent);
                    setObjVar(dressedTree, "fltRespawnTime", respawnTime);
                    utils.setScriptVar(dressedTree, "deathTracker", loc);
                    setYaw(dressedTree, yaw);
                    attachScript(dressedTree, "event.lifeday.lifeday_objective");
                    attachScript(dressedTree, "systems.spawning.spawned_tracker");
                    trial.cleanupObject(self);
                }
                else 
                {
                    LOG("Lifeday: ", "Something went wrong, object " + self + "doesn't have the right objvars and script vars on it as it should if it had been spawned correctly. Recommend destroying object.");
                }
            }
            else 
            {
                LOG("Lifeday: ", "Something went wrong, object " + self + "doesn't have the right objvars and script vars on it as it should if it had been spawned correctly. Recommend destroying object.");
            }
        }
        else 
        {
            LOG("Lifeday: ", "Something went wrong, object " + self + "doesn't have the right objvars and script vars on it as it should if it had been spawned correctly. Recommend destroying object.");
        }
        return;
    }
    public void presentsStomped(obj_id self, obj_id player) throws InterruptedException
    {
        if (!factions.isDeclared(player))
        {
            giveTreat(player, NON_DECLARED_TOKENS, IMPERIAL);
        }
        else 
        {
            if (factions.isDeclared(player))
            {
                giveTreat(player, DECLARED_TOKENS, IMPERIAL);
            }
        }
        if (hasObjVar(self, "objParent"))
        {
            obj_id objParent = getObjIdObjVar(self, "objParent");
            if (utils.hasScriptVar(self, "deathTracker"))
            {
                location loc = utils.getLocationScriptVar(self, "deathTracker");
                obj_id stompedPresents = createObject(PRESENTS_STOMPED, loc);
                float respawnTime = getFloatObjVar(self, "fltRespawnTime");
                float yaw = getYaw(self);
                setObjVar(self, "dontCountDeath", 1);
                setObjVar(stompedPresents, "objParent", objParent);
                setObjVar(stompedPresents, "fltRespawnTime", respawnTime);
                utils.setScriptVar(stompedPresents, "deathTracker", loc);
                setYaw(stompedPresents, yaw);
                attachScript(stompedPresents, "event.lifeday.lifeday_objective");
                attachScript(stompedPresents, "systems.spawning.spawned_tracker");
                trial.cleanupObject(self);
            }
            else 
            {
                LOG("Lifeday: ", "Something went wrong, object " + self + "doesn't have the right objvars and script vars on it as it should if it had been spawned correctly. Recommend destroying object.");
            }
        }
        else 
        {
            LOG("Lifeday: ", "Something went wrong, object " + self + "doesn't have the right objvars and script vars on it as it should if it had been spawned correctly. Recommend destroying object.");
        }
        return;
    }
    public void giveTreat(obj_id player, int quantity, int faction) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id rebelTokens = utils.getStaticItemInInventory(player, REBEL_TOKEN);
        obj_id imperialTokens = utils.getStaticItemInInventory(player, IMPERIAL_TOKEN);
        if (faction == REBEL)
        {
            if (isIdValid(rebelTokens))
            {
                int currentTokens = getCount(rebelTokens);
                setCount(rebelTokens, currentTokens + quantity);
            }
            else 
            {
                obj_id tokens = static_item.createNewItemFunction(REBEL_TOKEN, pInv, quantity);
            }
            doAnimationAction(player, "cheer");
            if (quantity > NON_DECLARED_TOKENS)
            {
                sendSystemMessage(player, SID_TOKENS_REBEL_HIGH);
            }
            else 
            {
                sendSystemMessage(player, SID_TOKENS_REBEL);
            }
        }
        if (faction == IMPERIAL)
        {
            if (isIdValid(imperialTokens))
            {
                int currentTokens = getCount(imperialTokens);
                setCount(imperialTokens, currentTokens + quantity);
            }
            else 
            {
                obj_id tokens = static_item.createNewItemFunction(IMPERIAL_TOKEN, pInv, quantity);
            }
            doAnimationAction(player, "cheer");
            if (quantity > NON_DECLARED_TOKENS)
            {
                sendSystemMessage(player, SID_TOKENS_IMPERIAL_HIGH);
            }
            else 
            {
                sendSystemMessage(player, SID_TOKENS_IMPERIAL);
            }
        }
        if (!hasObjVar(player, LIFEDAY_TIMESTAMP))
        {
            int now = getCalendarTime();
            int secondsUntil = secondsUntilNextDailyTime(10, 0, 0);
            int then = now + secondsUntil;
            setObjVar(player, LIFEDAY_TIMESTAMP, then);
        }
        if (hasObjVar(player, TOKEN_COUNTER))
        {
            int tokens = getIntObjVar(player, TOKEN_COUNTER);
            int newTokens = tokens + quantity;
            setObjVar(player, TOKEN_COUNTER, newTokens);
        }
        else 
        {
            setObjVar(player, TOKEN_COUNTER, quantity);
        }
        return;
    }
    public void applyCorrectBuff(obj_id self, obj_id player) throws InterruptedException
    {
        String objectTemplate = getTemplateName(self);
        int lifeDayBuff = buff.getBuffOnTargetFromGroup(player, "lifeday_tracker");
        if (lifeDayBuff == 0)
        {
            if (factions.isImperial(player))
            {
                if (objectTemplate.equals(PRESENTS))
                {
                    if (buff.hasBuff(player, PICKED_COMPETITIVE_BUFF_IMPERIAL))
                    {
                        sendSystemMessage(player, SID_IMPERIAL_COMPETITIVE_START);
                        buff.applyBuff(player, IMPERIAL_COMPETITIVE_BUFF);
                        buff.removeBuff(player, PICKED_COMPETITIVE_BUFF_IMPERIAL);
                    }
                    if (buff.hasBuff(player, PICKED_CASUAL_BUFF_IMPERIAL))
                    {
                        sendSystemMessage(player, SID_IMPERIAL_START);
                        buff.applyBuff(player, IMPERIAL_COUNTER_BUFF);
                        buff.removeBuff(player, PICKED_CASUAL_BUFF_IMPERIAL);
                    }
                }
            }
            if (factions.isRebel(player))
            {
                if (objectTemplate.equals(TREE))
                {
                    if (buff.hasBuff(player, PICKED_COMPETITIVE_BUFF_REBEL))
                    {
                        sendSystemMessage(player, SID_REBEL_COMPETITIVE_START);
                        buff.applyBuff(player, REBEL_COMPETITIVE_BUFF);
                        buff.removeBuff(player, PICKED_COMPETITIVE_BUFF_REBEL);
                    }
                    if (buff.hasBuff(player, PICKED_CASUAL_BUFF_REBEL))
                    {
                        sendSystemMessage(player, SID_REBEL_START);
                        buff.applyBuff(player, REBEL_COUNTER_BUFF);
                        buff.removeBuff(player, PICKED_CASUAL_BUFF_REBEL);
                    }
                }
            }
            else 
            {
                if (objectTemplate.equals(PRESENTS))
                {
                    if (buff.hasBuff(player, PICKED_COMPETITIVE_BUFF_IMPERIAL))
                    {
                        sendSystemMessage(player, SID_NEUTRAL_IMP_COMP_START);
                        buff.applyBuff(player, IMPERIAL_COMPETITIVE_BUFF);
                        buff.removeBuff(player, PICKED_COMPETITIVE_BUFF_IMPERIAL);
                    }
                    if (buff.hasBuff(player, PICKED_CASUAL_BUFF_IMPERIAL))
                    {
                        sendSystemMessage(player, SID_NEUTRAL_IMP_CAS_START);
                        buff.applyBuff(player, IMPERIAL_COUNTER_BUFF);
                        buff.removeBuff(player, PICKED_CASUAL_BUFF_IMPERIAL);
                    }
                }
                if (objectTemplate.equals(TREE))
                {
                    if (buff.hasBuff(player, PICKED_COMPETITIVE_BUFF_REBEL))
                    {
                        sendSystemMessage(player, SID_NEUTRAL_REB_COMP_START);
                        buff.applyBuff(player, REBEL_COMPETITIVE_BUFF);
                        buff.removeBuff(player, PICKED_COMPETITIVE_BUFF_REBEL);
                    }
                    if (buff.hasBuff(player, PICKED_CASUAL_BUFF_REBEL))
                    {
                        sendSystemMessage(player, SID_NEUTRAL_REB_CAS_START);
                        buff.applyBuff(player, REBEL_COUNTER_BUFF);
                        buff.removeBuff(player, PICKED_CASUAL_BUFF_REBEL);
                    }
                }
            }
        }
    }
    public boolean newDay(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, LIFEDAY_TIMESTAMP))
        {
            int now = getCalendarTime();
            int then = getIntObjVar(player, LIFEDAY_TIMESTAMP);
            if (now > then)
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
        return true;
    }
    public boolean canInteract(obj_id player, obj_id self) throws InterruptedException
    {
        int now = getCalendarTime();
        int secondsUntil = secondsUntilNextDailyTime(10, 0, 0);
        int then = now + secondsUntil;
        int lifeDayBuffImperial = buff.getBuffOnTargetFromGroup(player, "lifeday_imperial");
        int lifeDayBuffRebel = buff.getBuffOnTargetFromGroup(player, "lifeday_rebel");
        obj_id mount = getMountId(player);
        if (isIdValid(mount))
        {
            sendSystemMessage(player, SID_MOUNTED);
            return false;
        }
        String objectTemplate = getTemplateName(self);
        if (objectTemplate.equals(TREE_DRESSED))
        {
            sendSystemMessage(player, SID_ALREADY_DRESSED);
            return false;
        }
        if (objectTemplate.equals(PRESENTS_STOMPED))
        {
            sendSystemMessage(player, SID_ALREADY_STOMPED);
            return false;
        }
        if (objectTemplate.equals(TREE) && factions.isImperial(player))
        {
            sendSystemMessage(player, SID_WRONG_FACTION);
            return false;
        }
        if (objectTemplate.equals(PRESENTS) && factions.isRebel(player))
        {
            sendSystemMessage(player, SID_WRONG_FACTION);
            return false;
        }
        if (objectTemplate.equals(TREE) && hasObjVar(player, NEUTRAL_IMPERIAL))
        {
            sendSystemMessage(player, SID_WRONG_FACTION);
            return false;
        }
        if (objectTemplate.equals(PRESENTS) && hasObjVar(player, NEUTRAL_REBEL))
        {
            sendSystemMessage(player, SID_WRONG_FACTION);
            return false;
        }
        if (objectTemplate.equals(TREE) && (lifeDayBuffImperial != 0))
        {
            sendSystemMessage(player, SID_WRONG_FACTION);
            return false;
        }
        if (objectTemplate.equals(PRESENTS) && (lifeDayBuffRebel != 0))
        {
            sendSystemMessage(player, SID_WRONG_FACTION);
            return false;
        }
        if (!hasObjVar(player, LIFEDAY_TIMESTAMP))
        {
            setObjVar(player, LIFEDAY_TIMESTAMP, then);
        }
        if (newDay(player))
        {
            setObjVar(player, LIFEDAY_TIMESTAMP, then);
            if (hasObjVar(player, LOCKED_OUT))
            {
                removeObjVar(player, LOCKED_OUT);
            }
            if (hasObjVar(player, TOKEN_COUNTER))
            {
                removeObjVar(player, TOKEN_COUNTER);
            }
            if (hasObjVar(player, NEUTRAL_IMPERIAL))
            {
                removeObjVar(player, NEUTRAL_IMPERIAL);
            }
            if (hasObjVar(player, NEUTRAL_REBEL))
            {
                removeObjVar(player, NEUTRAL_REBEL);
            }
            if (buff.hasBuff(player, IMPERIAL_COMPETITIVE_BUFF))
            {
                buff.removeBuff(player, IMPERIAL_COMPETITIVE_BUFF);
            }
            if (buff.hasBuff(player, REBEL_COMPETITIVE_BUFF))
            {
                buff.removeBuff(player, REBEL_COMPETITIVE_BUFF);
            }
            if (buff.hasBuff(player, IMPERIAL_COUNTER_BUFF))
            {
                buff.removeBuff(player, IMPERIAL_COUNTER_BUFF);
            }
            if (buff.hasBuff(player, REBEL_COUNTER_BUFF))
            {
                buff.removeBuff(player, REBEL_COUNTER_BUFF);
            }
            return false;
        }
        if (hasObjVar(player, LOCKED_OUT))
        {
            sendSystemMessage(player, SID_LOCKED_OUT);
            return false;
        }
        int lifeDayBuff = buff.getBuffOnTargetFromGroup(player, "lifeday");
        if (lifeDayBuff == 0)
        {
            sendSystemMessage(player, SID_PICK_PATH);
            return false;
        }
        return true;
    }
    public int handleDressingCountdownTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int pid = params.getInt("id");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            int event = params.getInt("event");
            if (event == sui.CD_EVENT_LOCOMOTION)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_locomotion"));
            }
            else if (event == sui.CD_EVENT_INCAPACITATE)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_incapacitated"));
            }
            else if (event == sui.CD_EVENT_DAMAGED)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_damaged"));
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        int test_pid = getIntObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        int lifeDayBuff = buff.getBuffOnTargetFromGroup(player, "lifeday_tracker");
        if (lifeDayBuff == 0)
        {
            applyCorrectBuff(self, player);
        }
        else if (buff.hasBuff(player, REBEL_COUNTER_BUFF))
        {
            long counter = buff.getBuffStackCount(player, REBEL_COUNTER_BUFF);
            if (counter >= DAILY_COUNTER_LIMIT)
            {
                buff.removeBuff(player, REBEL_COUNTER_BUFF);
                setObjVar(player, LOCKED_OUT, 1);
                sendSystemMessage(player, SID_REACHED_LIMIT);
            }
            else 
            {
                buff.applyBuff(player, REBEL_COUNTER_BUFF);
            }
        }
        treeDressed(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleStompingCountdownTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int pid = params.getInt("id");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            int event = params.getInt("event");
            if (event == sui.CD_EVENT_LOCOMOTION)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_locomotion"));
            }
            else if (event == sui.CD_EVENT_INCAPACITATE)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_incapacitated"));
            }
            else if (event == sui.CD_EVENT_DAMAGED)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_damaged"));
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        int test_pid = getIntObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        int lifeDayBuff = buff.getBuffOnTargetFromGroup(player, "lifeday_tracker");
        if (lifeDayBuff == 0)
        {
            applyCorrectBuff(self, player);
        }
        else if (buff.hasBuff(player, IMPERIAL_COUNTER_BUFF))
        {
            long counter = buff.getBuffStackCount(player, IMPERIAL_COUNTER_BUFF);
            if (counter >= DAILY_COUNTER_LIMIT)
            {
                buff.removeBuff(player, IMPERIAL_COUNTER_BUFF);
                setObjVar(player, LOCKED_OUT, 1);
                sendSystemMessage(player, SID_REACHED_LIMIT);
            }
            else 
            {
                buff.applyBuff(player, IMPERIAL_COUNTER_BUFF);
            }
        }
        presentsStomped(self, player);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
