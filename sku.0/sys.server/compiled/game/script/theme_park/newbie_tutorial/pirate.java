package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.loot;
import script.library.chat;
import script.library.ai_lib;

public class pirate extends script.theme_park.newbie_tutorial.tutorial_base
{
    public pirate()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_AGGRESSIVE);
        setObjVar(self, "ai.recentlyBarked", true);
        chat.setAngryMood(self);
        createTriggerVolume("playerEntered", 13.0f, true);
        setLevel(self, 1);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        permissionsMakePublic(getCellId(getTopMostContainer(self), "r9"));
        setObjVar(player, "newbie.killedPirate", true);
        if (hasObjVar(self, "newbie.messageSent"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "newbie.messageSent", true);
        messageTo(player, "handleCongrats", null, 2, false);
        ai_lib.aiSetPosture(self, POSTURE_INCAPACITATED);
        messageTo(player, "moveOnToNextRoom", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerInRoom(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.didThis"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "newbie.didThis", true);
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pirateInv = getObjectInSlot(self, "inventory");
        if (pirateInv != null)
        {
            createObject(PIRATE_WEAPON, pirateInv, "");
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(player, "newbie.killedPirate"))
        {
            setObjVar(self, loot.VAR_DENY_LOOT, true);
        }
        faceToBehavior(self, player);
        if (!ai_lib.isInCombat(self))
        {
            chat.setAngryMood(self);
            chat.chat(self, new string_id(NEWBIE_CONVO, "pirate_taunt1"));
        }
        messageTo(self, "handleTauntTwo", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handleTauntTwo(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!ai_lib.isInCombat(self))
        {
            chat.setAngryMood(self);
            chat.chat(self, new string_id(NEWBIE_CONVO, "pirate_taunt2"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "newbie.isAttacking"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getPlayer(self);
        if (player != breacher)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isInRoom(player, "r8"))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("playerEntered"))
        {
            if (!ai_lib.isInCombat(self))
            {
                chat.setAngryMood(self);
                chat.chat(self, new string_id(NEWBIE_CONVO, "pirate_attack"));
                startCombat(self, player);
                setObjVar(self, "newbie.isAttacking", true);
            }
        }
        messageTo(self, "handleTauntThree", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleTauntThree(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "newbie.beenHereToo"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            setObjVar(self, "newbie.beenHereToo", true);
        }
        obj_id player = getPlayer(self);
        chat.setAngryMood(self);
        chat.chat(self, new string_id(NEWBIE_CONVO, "pirate_taunt3"));
        messageTo(self, "handleTauntFour", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handleTauntFour(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getPlayer(self);
        chat.setAngryMood(self);
        chat.chat(self, new string_id(NEWBIE_CONVO, "pirate_taunt4"));
        messageTo(self, "handleExplosion", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleExplosion(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        location death = getLocation(self);
        obj_id player = getPlayer(self);
        playClientEffectObj(player, "clienteffect/combat_explosion_lair_large.cef", self, "");
        ai_lib.incapacitateMob(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isInRoom(player, "r8"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleTauntThree", null, 30, false);
        removeStaticWaypoint(self);
        return SCRIPT_CONTINUE;
    }
}
