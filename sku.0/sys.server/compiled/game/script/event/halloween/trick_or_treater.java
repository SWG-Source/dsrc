package script.event.halloween;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.player_structure;
import script.library.sui;
import script.library.static_item;
import script.library.buff;
import script.library.chat;
import script.library.ai_lib;
import script.library.beast_lib;
import script.library.factions;
import script.library.vehicle;
import script.library.event_perk;
import java.util.Enumeration;

public class trick_or_treater extends script.base_script
{
    public trick_or_treater()
    {
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (costumeBuffExists(self) == false)
        {
            detachScript(self, "event.halloween.trick_or_treater");
        }
        if (buff.hasBuff(self, "event_halloween_coin_limit"))
        {
            if (event_perk.newDayOrNot(self) == true)
            {
                buff.removeBuff(self, "event_halloween_coin_limit");
            }
        }
        if (event_perk.newDayOrNot(self) == true)
        {
            if (hasObjVar(self, event_perk.COUNTER))
            {
                removeObjVar(self, event_perk.COUNTER);
            }
            if (hasObjVar(self, event_perk.COUNTER_RESTARTTIME))
            {
                removeObjVar(self, event_perk.COUNTER_RESTARTTIME);
            }
            if (hasObjVar(self, event_perk.COUNTER_TIMESTAMP))
            {
                removeObjVar(self, event_perk.COUNTER_TIMESTAMP);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (buff.hasBuff(self, "event_halloween_coin_limit"))
        {
            if (event_perk.newDayOrNot(self) == true)
            {
                buff.removeBuff(self, "event_halloween_coin_limit");
            }
        }
        if (event_perk.newDayOrNot(self) == true)
        {
            if (hasObjVar(self, event_perk.COUNTER))
            {
                removeObjVar(self, event_perk.COUNTER);
            }
            if (hasObjVar(self, event_perk.COUNTER_RESTARTTIME))
            {
                removeObjVar(self, event_perk.COUNTER_RESTARTTIME);
            }
            if (hasObjVar(self, event_perk.COUNTER_TIMESTAMP))
            {
                removeObjVar(self, event_perk.COUNTER_TIMESTAMP);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (costumeBuffExists(self) == false)
        {
            detachScript(self, "event.halloween.trick_or_treater");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (speaker == self)
        {
            if (isGod(speaker))
            {
                if (text.equals("setDailyLimitTwoMinutes"))
                {
                    sendSystemMessageTestingOnly(self, "The daily limit will get reset 2 minutes from now");
                    int now = getCalendarTime();
                    int secondsUntil = 120;
                    int then = now + secondsUntil;
                    setObjVar(self, event_perk.COUNTER_RESTARTTIME, then);
                    if (hasObjVar(self, event_perk.COUNTER_TIMESTAMP))
                    {
                        setObjVar(self, event_perk.COUNTER_TIMESTAMP, then);
                    }
                }
            }
            if (costumeBuffExists(self) == false)
            {
                detachScript(self, "event.halloween.trick_or_treater");
                return SCRIPT_CONTINUE;
            }
            if ((toLower(text)).indexOf("trick or treat") > -1)
            {
                location speakerLocation = getLocation(speaker);
                if (isInCity(speakerLocation))
                {
                    obj_id target = getIntendedTarget(self);
                    float distance = utils.getDistance2D(getLocation(self), getLocation(target));
                    if (distance > 11.0f)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (!isPlayerActive(speaker))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    int stealth = buff.getBuffOnTargetFromGroup(self, "invisibility");
                    if (stealth != 0)
                    {
                        sendSystemMessage(speaker, event_perk.STEALTHED);
                        return SCRIPT_CONTINUE;
                    }
                    if (vehicle.isVehicle(target))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (isDead(target))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (beast_lib.isBeast(target))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (hasScript(target, "theme_park.outbreak_prolog.dead_npc_script"))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    String targetName = getCreatureName(target);
                    if (targetName.equals("halloween_vendor"))
                    {
                        sendSystemMessage(self, event_perk.ZOZ);
                        return SCRIPT_CONTINUE;
                    }
                    if (hasObjVar(target, "celebrity"))
                    {
                        sendSystemMessage(self, event_perk.STATIC_NPC);
                        return SCRIPT_CONTINUE;
                    }
                    if (event_perk.timeStampCheck(self) == false)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    int myNiche = ai_lib.aiGetNiche(target);
                    if (myNiche == 5 || ai_lib.isNpc(target) || myNiche == 10 || ai_lib.isAndroid(target))
                    {
                        if (isCreatureStatic(target))
                        {
                            sendSystemMessage(self, event_perk.STATIC_NPC);
                            return SCRIPT_CONTINUE;
                        }
                        String template = getTemplateName(target);
                        int mobile = template.indexOf("object/mobile/");
                        if (mobile > -1 && !hasObjVar(target, "hologram_performer"))
                        {
                            if (!ai_lib.isInCombat(self) || !ai_lib.isInCombat(target))
                            {
                                int currentTime = getGameTime();
                                int newLockOutTime = currentTime + event_perk.LOCKOUT_LENGTH;
                                if (utils.hasScriptVar(self, event_perk.LIST_VAR + "." + target))
                                {
                                    int lockoutTime = utils.getIntScriptVar(self, event_perk.LIST_VAR + "." + target);
                                    if (currentTime > lockoutTime)
                                    {
                                        utils.setScriptVar(self, event_perk.LIST_VAR + "." + target, newLockOutTime);
                                        event_perk.handlePayout(self, target);
                                    }
                                    else 
                                    {
                                        chat.chat(target, self, chat.CHAT_SAY, event_perk.TOO_SOON, chat.ChatFlag_targetOnly);
                                    }
                                }
                                else 
                                {
                                    utils.setScriptVar(self, event_perk.LIST_VAR + "." + target, newLockOutTime);
                                    event_perk.handlePayout(self, target);
                                }
                            }
                        }
                    }
                    else 
                    {
                        return SCRIPT_CONTINUE;
                    }
                    String path = event_perk.LIST_VAR;
                    if (utils.hasScriptVarTree(self, path))
                    {
                        int time = getGameTime();
                        deltadictionary vars = self.getScriptVars();
                        Enumeration keys = vars.keys();
                        while (keys.hasMoreElements())
                        {
                            String key = (String)(keys.nextElement());
                            if (key.startsWith(event_perk.LIST_VAR + "."))
                            {
                                int data = vars.getInt(key);
                                if (data < time)
                                {
                                    vars.remove(key);
                                }
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isInCity(location here) throws InterruptedException
    {
        region[] regionsHere = getRegionsAtPoint(here);
        if (regionsHere != null && regionsHere.length > 0)
        {
            for (int i = 0; i < regionsHere.length; i++)
            {
                region testRegion = regionsHere[i];
                String regionName = testRegion.getName();
                if (regionName.endsWith("mos_eisley"))
                {
                    return true;
                }
                if (regionName.endsWith("moenia"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean costumeBuffExists(obj_id player) throws InterruptedException
    {
        if (buff.hasBuff(player, "event_halloween_costume_jawa"))
        {
            return true;
        }
        if (buff.hasBuff(player, "event_halloween_costume_droid"))
        {
            return true;
        }
        if (buff.hasBuff(player, "event_halloween_costume_kowakian"))
        {
            return true;
        }
        if (buff.hasBuff(player, "event_halloween_costume_hutt_female"))
        {
            return true;
        }
        if (buff.hasBuff(player, "event_halloween_costume_toydarian"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
}
