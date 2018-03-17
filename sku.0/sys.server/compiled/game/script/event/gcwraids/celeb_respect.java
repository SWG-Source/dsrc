package script.event.gcwraids;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.library.combat;
import script.library.pet_lib;
import script.obj_id;
import script.string_id;

public class celeb_respect extends script.base_script
{
    public celeb_respect()
    {
    }
    private static final String EMOTE_DATATABLE = "datatables/npc/stormtrooper_attitude/emote.iff";
    public static final String STF_FILE = "event/gcw_raids";
    public int OnSawEmote(obj_id self, obj_id emoteSayer, String emotein) throws InterruptedException
    {
        int type = getIntObjVar(self, "event.gcwraids.type");
        if (type == 1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id emotetarget = getLookAtTarget(emoteSayer);
        if (!isIdValid(emotetarget) || ai_lib.isInCombat(self) || ai_lib.isInCombat(emoteSayer) || isIncapacitated(self) || isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (emotetarget == self)
        {
            if (type == 0 && getEmoteBeligerence(emotein) > 0)
            {
                dictionary params = new dictionary();
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
        string_id myLine = new string_id(STF_FILE, "thwap_" + getIntObjVar(self, "event.gcwraids.type") + "_" + rand(0, 9));
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
    private int getEmoteBeligerence(String emote) throws InterruptedException
    {
        int st_emote_type = dataTableGetInt(EMOTE_DATATABLE, dataTableSearchColumnForString(emote, 0, EMOTE_DATATABLE), 1);
        if (st_emote_type <= 3 && st_emote_type >= 0)
        {
            return st_emote_type;
        }
        else 
        {
            return -1;
        }
    }
    private void playKnockdown(obj_id victim, obj_id attacker) throws InterruptedException
    {
        if (!isIdValid(victim) || !isIdValid(attacker))
        {
            return;
        }
        String strPlaybackScript;
        obj_id objWeapon = getCurrentWeapon(attacker);
        int intWeaponCategory = combat.getWeaponCategory(getWeaponType(objWeapon));
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
