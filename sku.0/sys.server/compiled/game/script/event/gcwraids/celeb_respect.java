package script.event.gcwraids;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.combat;
import script.library.utils;
import script.library.pclib;
import script.library.pet_lib;

public class celeb_respect extends script.base_script
{
    public celeb_respect()
    {
    }
    public static final String EMOTE_DATATABLE = "datatables/npc/stormtrooper_attitude/emote.iff";
    public static final String STF_FILE = "event/gcw_raids";
    public int OnSawEmote(obj_id self, obj_id emoteSayer, String emotein) throws InterruptedException
    {
        obj_id emotetarget = getLookAtTarget(emoteSayer);
        int type = getIntObjVar(self, "event.gcwraids.type");
        dictionary params = new dictionary();
        if (type == 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(emotetarget) || ai_lib.isInCombat(self) || ai_lib.isInCombat(emoteSayer) || isIncapacitated(self) || isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (emotetarget == self)
        {
            int belligerence = getEmoteBeligerence(emotein);
            if (type == 0 && belligerence > 0)
            {
                params.put("emoteSayer", emoteSayer);
                messageTo(self, "forceChokeThisGuy", params, 1, false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int forceChokeThisGuy(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id emoteSayer = params.getObjId("emoteSayer");
        faceTo(self, emoteSayer);
        doAnimationAction(self, "force_choke");
        doAnimationAction(emoteSayer, "heavy_cough_vomit");
        int type = getIntObjVar(self, "event.gcwraids.type");
        int randomLine = rand(0, 9);
        string_id myLine = new string_id(STF_FILE, "thwap_" + type + "_" + randomLine);
        chat.chat(self, chat.CHAT_SAY, chat.MOOD_NONE, myLine);
        params.put("emoteSayer", emoteSayer);
        messageTo(self, "finishHim", params, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int finishHim(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id emoteSayer = params.getObjId("emoteSayer");
        faceTo(self, emoteSayer);
        playKnockdown(emoteSayer, self);
        setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
        return SCRIPT_CONTINUE;
    }
    public int getEmoteBeligerence(String emote) throws InterruptedException
    {
        int emote_row = dataTableSearchColumnForString(emote, 0, EMOTE_DATATABLE);
        int st_emote_type = dataTableGetInt(EMOTE_DATATABLE, emote_row, 1);
        if (st_emote_type == 3)
        {
            return 3;
        }
        else if (st_emote_type == 2)
        {
            return 2;
        }
        else if (st_emote_type == 1)
        {
            return 1;
        }
        else if (st_emote_type == 0)
        {
            return 0;
        }
        else 
        {
            return -1;
        }
    }
    public void playKnockdown(obj_id victim, obj_id attacker) throws InterruptedException
    {
        if (!isIdValid(victim) || !isIdValid(attacker))
        {
            return;
        }
        String strPlaybackScript = "";
        obj_id objWeapon = getCurrentWeapon(attacker);
        int intWeaponType = getWeaponType(objWeapon);
        int intWeaponCategory = combat.getWeaponCategory(intWeaponType);
        if (intWeaponCategory == combat.RANGED_WEAPON)
        {
            strPlaybackScript = "ranged_melee_light";
        }
        else 
        {
            strPlaybackScript = "attack_high_center_light_0";
        }
        if (pet_lib.isMounted(victim))
        {
            pet_lib.doDismountNow(victim, false);
        }
        attacker_results cbtAttackerResults = new attacker_results();
        defender_results[] cbtDefenderResults = new defender_results[1];
        cbtDefenderResults[0] = new defender_results();
        cbtAttackerResults.id = attacker;
        cbtAttackerResults.endPosture = getPosture(attacker);
        cbtAttackerResults.weapon = objWeapon;
        cbtDefenderResults[0].endPosture = POSTURE_INCAPACITATED;
        cbtDefenderResults[0].result = COMBAT_RESULT_HIT;
        cbtDefenderResults[0].id = victim;
        doCombatResults(strPlaybackScript, cbtAttackerResults, cbtDefenderResults);
    }
}
