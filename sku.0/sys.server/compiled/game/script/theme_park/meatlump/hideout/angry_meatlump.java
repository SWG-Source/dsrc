package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.anims;
import script.library.buff;
import script.library.chat;
import script.library.groundquests;
import script.library.prose;
import script.library.utils;

public class angry_meatlump extends script.base_script
{
    public angry_meatlump()
    {
    }
    public static final String ANGRY_MEATLUMP_DATATABLE = "datatables/theme_park/meatlump/angry_meatlump.iff";
    public static final String EMOTES_COLUMN = "requiredEmote";
    public static final String HINTS_COLUMN = "hintString";
    public static final String STF = "theme_park/corellia/quest";
    public static final String REQUIRED_EMOTE_OBJVAR = "angryMeatlump.requiredEmote";
    public static final String HIDEOUT_ID_OBJVAR = "angryMeatlump.hideout";
    public static final String[] NEGATIVE_EMOTES = 
    {
        "dismiss",
        "mock",
        "refuse_offer_affection",
        "rude",
        "shoo"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAngryMealumpRequiredEmote(self);
        return SCRIPT_CONTINUE;
    }
    public int OnSawEmote(obj_id self, obj_id emoteSayer, String emoteSeen) throws InterruptedException
    {
        if (!isPlayer(emoteSayer) || ai_lib.isInCombat(emoteSayer) || isIncapacitated(emoteSayer) || isDead(emoteSayer))
        {
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isQuestActive(emoteSayer, "mtp_angry_meatlumps"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id emoteTarget = getIntendedTarget(emoteSayer);
        if (!isIdValid(emoteTarget))
        {
            return SCRIPT_CONTINUE;
        }
        if (emoteTarget == self)
        {
            if (!hasObjVar(self, REQUIRED_EMOTE_OBJVAR))
            {
                setAngryMealumpRequiredEmote(self);
            }
            String requiredEmote = getStringObjVar(self, REQUIRED_EMOTE_OBJVAR);
            faceTo(self, emoteSayer);
            if (emoteSeen.equals(requiredEmote))
            {
                doAnimationAction(self, "celebrate");
                becomeHappyMeatlump(self, emoteSayer);
            }
            else 
            {
                doAnimationAction(self, NEGATIVE_EMOTES[rand(0, NEGATIVE_EMOTES.length - 1)]);
                if (!utils.hasScriptVar(emoteSayer, "angryMeatlumpHint_" + self))
                {
                    int row = dataTableSearchColumnForString(requiredEmote, EMOTES_COLUMN, ANGRY_MEATLUMP_DATATABLE);
                    if (row > -1)
                    {
                        String hintString = dataTableGetString(ANGRY_MEATLUMP_DATATABLE, row, HINTS_COLUMN);
                        if (hintString != null && hintString.length() > 0)
                        {
                            string_id hintMsg = new string_id(STF, hintString);
                            prose_package pp = prose.getPackage(hintMsg, emoteSayer, emoteSayer);
                            String pronounTO = getGender(self) == GENDER_MALE ? "he" : "she";
                            String pronounTT = getGender(self) == GENDER_MALE ? "his" : "her";
                            prose.setTO(pp, pronounTO);
                            prose.setTT(pp, pronounTT);
                            chat.chat(emoteSayer, emoteSayer, chat.CHAT_EMOTE, null, pp);
                            utils.setScriptVar(emoteSayer, "angryMeatlumpHint_" + self, true);
                        }
                    }
                }
                else 
                {
                    utils.removeScriptVar(emoteSayer, "angryMeatlumpHint_" + self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void setAngryMealumpRequiredEmote(obj_id self) throws InterruptedException
    {
        String[] listOfEmotes = dataTableGetStringColumn(ANGRY_MEATLUMP_DATATABLE, EMOTES_COLUMN);
        String requiredEmote = listOfEmotes[rand(0, listOfEmotes.length - 1)];
        setObjVar(self, REQUIRED_EMOTE_OBJVAR, requiredEmote);
        return;
    }
    public void becomeHappyMeatlump(obj_id self, obj_id player) throws InterruptedException
    {
        dictionary webster = new dictionary();
        String creatureName = "mtp_angry_meatlump";
        String socialGroup = "angry_meatlump";
        webster.put("creatureName", creatureName);
        webster.put("location", getLocation(player));
        webster.put("socialGroup", socialGroup);
        messageTo(player, "receiveCreditForKill", webster, 0.0f, false);
        obj_id hideout = getObjIdObjVar(self, HIDEOUT_ID_OBJVAR);
        if (isIdValid(hideout))
        {
            webster.put("happyMeatlump", self);
            messageTo(hideout, "makeNewAngryMeatlump", webster, 5, false);
        }
        buff.applyBuff(self, "mtp_meatlump_happy");
        return;
    }
    public int OnIncapacitated(obj_id self, obj_id objAttacker) throws InterruptedException
    {
        obj_id hideout = getObjIdObjVar(self, HIDEOUT_ID_OBJVAR);
        if (isIdValid(hideout))
        {
            dictionary webster = new dictionary();
            webster.put("happyMeatlump", self);
            messageTo(hideout, "makeNewAngryMeatlump", webster, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id hideout = getObjIdObjVar(self, HIDEOUT_ID_OBJVAR);
        if (isIdValid(hideout))
        {
            dictionary webster = new dictionary();
            webster.put("happyMeatlump", self);
            messageTo(hideout, "makeNewAngryMeatlump", webster, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
}
