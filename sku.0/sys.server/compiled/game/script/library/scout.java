package script.library;

import script.*;

public class scout extends script.base_script
{
    public scout()
    {
    }
    public static final string_id SID_SYS_SCENTMASK_BREAK = new string_id("skl_use", "sys_scentmask_break");
    public static final string_id SID_SYS_SCENTMASK_BREAK_COMBAT = new string_id("skl_use", "sys_scentmask_break_combat");
    public static final string_id SID_SYS_SCENTMASK_SUCCESS = new string_id("skl_use", "sys_scentmask_success");
    public static final string_id SID_SYS_CONCEAL_NOSKILL = new string_id("skl_use", "sys_conceal_noskill");
    public static final string_id SID_SYS_CONCEAL_FAIL = new string_id("skl_use", "sys_conceal_fail");
    public static final string_id SID_SYS_CONCEAL_CANT = new string_id("skl_use", "sys_conceal_cant");
    public static final string_id SID_SYS_CANT_CONCEAL_OTHER = new string_id("skl_use", "sys_cant_conceal_other");
    public static final string_id SID_SYS_CONCEAL_NOTPLAYER = new string_id("skl_use", "sys_conceal_notplayer");
    public static final string_id SID_SYS_TARGET_CONCEALED = new string_id("skl_use", "sys_target_concealed");
    public static final string_id SID_SYS_CONCEAL_REMOVE = new string_id("skl_use", "sys_conceal_remove");
    public static final string_id SID_SYS_CONCEAL_NOKIT = new string_id("skl_use", "sys_conceal_nokit");
    public static final string_id SID_SYS_CONCEAL_SCENTMASKED = new string_id("skl_use", "sys_conceal_scentmasked");
    public static final string_id SID_SYS_CONCEAL_APPLY = new string_id("skl_use", "sys_conceal_apply");
    public static final string_id SID_SYS_CONCEAL_DELAY = new string_id("skl_use", "sys_conceal_delay");
    public static final int MASK_BREAK_DELAY = 60;
    public static boolean isScentMasked(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(npc))
        {
            return false;
        }
        location playerLoc = getLocation(player);
        location npcLoc = getLocation(npc);
        if (playerLoc == null || npcLoc == null)
        {
            return false;
        }
        if (isIdValid(playerLoc.cell) || isIdValid(npcLoc.cell))
        {
            if (!isIdValid(playerLoc.cell))
            {
                return false;
            }
            else if (!isIdValid(npcLoc.cell))
            {
                return false;
            }
            else if (playerLoc.cell != npcLoc.cell)
            {
                return false;
            }
        }
        boolean concealed = utils.hasScriptVar(player, "scentmask.camokit");
        if (!concealed && !ai_lib.isMonster(npc))
        {
            return false;
        }
        if (pet_lib.isPet(npc))
        {
            return false;
        }
        if (ai_lib.aiGetNiche(npc) == NICHE_DROID || ai_lib.aiGetNiche(npc) == NICHE_ANDROID)
        {
            return false;
        }
        int scentMasked = getState(player, STATE_MASK_SCENT);
        if (scentMasked == 0)
        {
            messageTo(player, "removeScentMaskNoNotify", null, 0, false);
            return false;
        }
        int concealBonus = utils.getIntScriptVar(player, "scentmask.camoquality");
        if (concealed)
        {
            String camoPlanet = utils.getStringScriptVar(player, "scentmask.camokit");
            String thisPlanet = getCurrentSceneName();
            if (thisPlanet.startsWith("kashyyyk"))
            {
                thisPlanet = "kashyyyk";
            }
            if (!camoPlanet.equals(thisPlanet))
            {
                return false;
            }
        }
        int maskMod;
        if (concealed)
        {
            maskMod = concealBonus;
            if (maskMod > 0)
            {
                maskMod += getEnhancedSkillStatisticModifier(player, "camouflage") / 2;
            }
        }
        else 
        {
            maskMod = getEnhancedSkillStatisticModifier(player, "mask_scent");
            if (maskMod > 0)
            {
                maskMod = maskMod / 2;
            }
        }
        if (maskMod < 5)
        {
            return false;
        }
        if (!ai_lib.isAggro(npc) && !ai_lib.isStalkingCreature(npc))
        {
            return true;
        }
        int level = getLevel(npc);
        int playerLevel = skill.getGroupLevel(player);
        if ((playerLevel - level) >= 10)
        {
            return true;
        }
        float sneakChance = (100 + maskMod) - level;
        int locomotion = getLocomotion(player);
        if (locomotion == LOCOMOTION_WALKING)
        {
            sneakChance -= 5;
        }
        else if (locomotion == LOCOMOTION_RUNNING)
        {
            sneakChance -= 10;
        }
        int posture = getPosture(player);
        if (posture == POSTURE_PRONE)
        {
            sneakChance += 15;
        }
        if (level > 2 * maskMod)
        {
            sneakChance -= 20;
        }
        if (concealed)
        {
            if (sneakChance > 98)
            {
                sneakChance = 98;
            }
        }
        else 
        {
            if (sneakChance > 95)
            {
                sneakChance = 95;
            }
        }
        if (!ai_lib.isMonster(npc))
        {
            sneakChance -= 5;
        }
        int dieRoll = rand(1, 100);
        if (dieRoll < sneakChance)
        {
            dictionary outparams = new dictionary();
            if (concealed)
            {
                obj_id conplayer = utils.getObjIdScriptVar(player, "scentmask.camoapply");
                if (isIdValid(conplayer))
                {
                    outparams.put("player", conplayer);
                    outparams.put("concealed", 1);
                }
                else 
                {
                    String msg = "scout.scriptlib, isScentMasked player " + player + "has no scentmask.camoapply scriptvar";
                    LOG("scout", msg);
                    debugServerConsoleMsg(null, "WARNING: " + msg);
                    outparams.put("player", player);
                }
            }
            else 
            {
                outparams.put("player", player);
            }
            outparams.put("creaturediff", level);
            messageTo(npc, "handleMaskScentSneak", outparams, 0.f, false);
            utils.setScriptVar(player, "scentmask.creatureDiff", level);
            utils.setScriptVar(player, "scentmask.time", getGameTime());
        }
        else 
        {
            breakScentMask(player, false);
            return false;
        }
        return true;
    }
    public static void breakScentMask(obj_id player, boolean combat) throws InterruptedException
    {
        int maskMod = getEnhancedSkillStatisticModifier(player, "mask_scent") / 2;
        int remaskDelay = 70 - maskMod;
        dictionary params = new dictionary();
        params.put("remaskDelay", remaskDelay);
        messageTo(player, "removeScentMaskNoNotify", params, 0.f, false);
        if (combat)
        {
            sendSystemMessage(player, SID_SYS_SCENTMASK_BREAK_COMBAT);
        }
        else 
        {
            sendSystemMessage(player, SID_SYS_SCENTMASK_BREAK);
        }
    }
    public static boolean isScentMaskedCheck(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        int scentMasked = getState(player, STATE_MASK_SCENT);
        if (scentMasked == 0)
        {
            messageTo(player, "removeScentMaskNoNotify", null, 0, false);
            return false;
        }
        return true;
    }
    public static boolean isSnared(obj_id npc) throws InterruptedException
    {
        return hasAttribModifier(npc, "movement.snare");
    }
    public static void conceal(obj_id self, obj_id target) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "remaskDelay"))
        {
            int delay = utils.getIntScriptVar(self, "remaskDelay");
            int timeDiff = getGameTime() - delay;
            if (timeDiff >= 0)
            {
                utils.removeScriptVar(self, "remaskDelay");
            }
            else 
            {
                prose_package pp = prose.getPackage(SID_SYS_CONCEAL_DELAY, -timeDiff);
                sendSystemMessageProse(self, pp);
                return;
            }
        }
        int skillMod = getSkillStatisticModifier(self, "camouflage") / 2;
        if (skillMod < 1)
        {
            skillMod = 20;
        }
        if (isIdValid(target) && target != self)
        {
            if (skillMod == 0)
            {
                sendSystemMessage(self, SID_SYS_CANT_CONCEAL_OTHER);
                return;
            }
            if (!isPlayer(target))
            {
                sendSystemMessage(self, SID_SYS_CONCEAL_NOTPLAYER);
                return;
            }
            if (!pvpCanHelp(self, target))
            {
                return;
            }
            int scentMasked = getState(target, STATE_MASK_SCENT);
            if (scentMasked == 1)
            {
                sendSystemMessage(self, SID_SYS_TARGET_CONCEALED);
                return;
            }
        }
        else 
        {
            int scentMasked = getState(self, STATE_MASK_SCENT);
            int count = utils.getIntScriptVar(self, "scentmask.count");
            if (scentMasked == 1)
            {
                dictionary outparams = new dictionary();
                outparams.put("count", count);
                if (utils.hasScriptVar(self, "scentmask.camokit"))
                {
                    messageTo(self, "removeConceal", outparams, 0, false);
                }
                else 
                {
                    sendSystemMessage(self, SID_SYS_CONCEAL_SCENTMASKED);
                }
                return;
            }
        }
        String planetName = getCurrentSceneName();
        if (planetName.startsWith("kashyyyk"))
        {
            planetName = "kashyyyk";
        }
        obj_id pInv = utils.getInventoryContainer(self);
        if (!isIdValid(pInv))
        {
            return;
        }
        obj_id[] contents = getContents(pInv);
        obj_id kit = null;

        for (obj_id content : contents) {
            if (hasObjVar(content, "camokit")) {
                if (getStringObjVar(content, "camokit").equals(planetName)) {
                    kit = content;
                    break;
                }
            }
        }
        if (kit == null)
        {
            sendSystemMessage(self, SID_SYS_CONCEAL_NOKIT);
            return;
        }
        int count = getCount(kit);
        if (count == 1)
        {
            destroyObject(kit);
        }
        else 
        {
            count--;
            setCount(kit, count);
        }
        int maskTime = 40 * skillMod;
        dictionary outparams = new dictionary();
        outparams.put("planetName", planetName);
        outparams.put("maskTime", maskTime);
        outparams.put("skillMod", skillMod);
        outparams.put("player", self);
        if (isIdValid(target))
        {
            prose_package pp = prose.getPackage(SID_SYS_CONCEAL_APPLY, target);
            sendSystemMessageProse(self, pp);
            messageTo(target, "concealEnable", outparams, 0.f, false);
        }
        else 
        {
            messageTo(self, "concealEnable", outparams, 0.f, false);
        }
    }
}
