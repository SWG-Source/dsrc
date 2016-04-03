package script.cybernetic;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.string_id;

public class cybernetic_item extends script.base_script
{
    public cybernetic_item()
    {
    }
    public static final string_id SID_ERROR = new string_id("ep3/cybernetic", "cannot_equip");
    public static final string_id SID_REQUIRE_EP3 = new string_id("ep3/cybernetic", "ep3_required");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        cybernetic.setHueColor(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        cybernetic.setupArmorValues(self);
        if (!utils.hasScriptVar(self, "isSetup"))
        {
            obj_id currentLoc = getContainedBy(self);
            if (isIdValid(currentLoc) && isPlayer(currentLoc))
            {
                cybernetic.applyCyberneticMods(currentLoc, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id currentLoc = getContainedBy(self);
        if (isIdValid(currentLoc))
        {
            if (isPlayer(currentLoc))
            {
                if (isPlayer(transferer))
                {
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    cybernetic.removeCyberneticMods(currentLoc, self);
                    cybernetic.revokeCyberneticSkillMods(currentLoc, self);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (!isPlayer(destContainer))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String templateName = getTemplateName(self);
            if (!templateName.contains("s01"))
            {
                if (!features.hasEpisode3Expansion(destContainer))
                {
                    if (!templateName.equals("object/tangible/wearables/cybernetic/s04/cybernetic_s04_arm_r.iff"))
                    {
                        sendSystemMessage(destContainer, SID_REQUIRE_EP3);
                        return SCRIPT_OVERRIDE;
                    }
                }
            }
        }
        if (!isPlayer(transferer))
        {
            cybernetic.applyCyberneticMods(destContainer, self);
            cybernetic.grantCyberneticSkillMods(destContainer, self);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(destContainer, SID_ERROR);
        return SCRIPT_OVERRIDE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id player = destContainer;
        if (isIdValid(destContainer) && isPlayer(destContainer))
        {
            armor.recalculateArmorForPlayer(player);
            proc.buildCurrentProcList(player);
            proc.buildCurrentReacList(player);
        }
        else if (isIdValid(sourceContainer) && isPlayer(sourceContainer))
        {
            player = sourceContainer;
            armor.recalculateArmorForPlayer(player);
            proc.buildCurrentProcList(player);
            proc.buildCurrentReacList(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id currentLoc = getContainedBy(self);
        if (isPlayer(currentLoc))
        {
            cybernetic.removeCyberneticMods(currentLoc, self);
            cybernetic.revokeCyberneticSkillMods(currentLoc, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary cyberRow = dataTableGetRow(cybernetic.CYBORG_TABLE, getTemplateName(self));
        if (cyberRow == null)
        {
            return SCRIPT_CONTINUE;
        }
        String procEffectString = cyberRow.getString("procEffectString");
        if (procEffectString != null && !procEffectString.equals(""))
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "proc"));
            attribs[free++] = utils.packStringId(new string_id("ep3/cybernetic", procEffectString));
        }
        String moveRateBuff = cyberRow.getString("moveRateBuff");
        if (moveRateBuff != null && !moveRateBuff.equals(""))
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "move_rate_buff"));
            attribs[free++] = utils.packStringId(new string_id("ep3/cybernetic", moveRateBuff));
        }
        if (cyberRow.getInt("throwRangeMod") != 0)
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "throw_range_mod"));
            if (cyberRow.getInt("throwRangeMod") > 0)
            {
                attribs[free++] = "+" + cyberRow.getInt("throwRangeMod");
            }
            else 
            {
                attribs[free++] = "" + cyberRow.getInt("throwRangeMod");
            }
        }
        if (cyberRow.getInt("rangedRangeMod") != 0)
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "ranged_range_mod"));
            if (cyberRow.getInt("rangedRangeMod") > 0)
            {
                attribs[free++] = "+" + cyberRow.getInt("rangedRangeMod");
            }
            else 
            {
                attribs[free++] = "" + cyberRow.getInt("rangedRangeMod");
            }
        }
        if (cyberRow.getInt("healingMod") != 0)
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "healing_mod"));
            if (cyberRow.getInt("healingMod") > 0)
            {
                attribs[free++] = "+" + cyberRow.getInt("healingMod");
            }
            else 
            {
                attribs[free++] = "" + cyberRow.getInt("healingMod");
            }
        }
        if (cyberRow.getInt("rangedAccuracyMod") != 0)
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "ranged_accuracy_mod"));
            if (cyberRow.getInt("rangedAccuracyMod") > 0)
            {
                attribs[free++] = "+" + cyberRow.getInt("rangedAccuracyMod");
            }
            else 
            {
                attribs[free++] = "" + cyberRow.getInt("rangedAccuracyMod");
            }
        }
        if (cyberRow.getInt("meleeDefMod") != 0)
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "melee_def_mod"));
            if (cyberRow.getInt("meleeDefMod") > 0)
            {
                attribs[free++] = "+" + cyberRow.getInt("meleeDefMod");
            }
            else 
            {
                attribs[free++] = "" + cyberRow.getInt("meleeDefMod");
            }
        }
        if (cyberRow.getInt("meleeAccuracyMod") != 0)
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "melee_accuracy_mod"));
            if (cyberRow.getInt("meleeAccuracyMod") > 0)
            {
                attribs[free++] = "+" + cyberRow.getInt("meleeAccuracyMod");
            }
            else 
            {
                attribs[free++] = "" + cyberRow.getInt("meleeAccuracyMod");
            }
        }
        if (cyberRow.getInt("commandoLegs") != 0)
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "commando_legs"));
            attribs[free++] = utils.packStringId(new string_id("ep3/cybernetic", "commando_legs_desc"));
        }
        String specialCommand = cyberRow.getString("specialCommand");
        if (specialCommand != null && !specialCommand.equals(""))
        {
            names[free] = utils.packStringId(new string_id("ep3/cybernetic", "special_command"));
            attribs[free] = utils.packStringId(new string_id("ep3/cybernetic", specialCommand));
        }
        return SCRIPT_CONTINUE;
    }
}
