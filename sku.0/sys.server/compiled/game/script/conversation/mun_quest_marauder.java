package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class mun_quest_marauder extends script.base_script
{
    public mun_quest_marauder()
    {
    }
    public static String c_stringFile = "conversation/mun_quest_marauder";
    public boolean mun_quest_marauder_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mun_quest_marauder_condition_onQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mun_marauder_1");
    }
    public boolean mun_quest_marauder_condition_onLastTaskQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mun_marauder_1", "final_task");
    }
    public boolean mun_quest_marauder_condition_finishedQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "mun_marauder_1");
    }
    public boolean mun_quest_marauder_condition_onQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mun_marauder_2");
    }
    public boolean mun_quest_marauder_condition_onLastTaskQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mun_marauder_2", "final_task");
    }
    public boolean mun_quest_marauder_condition_finishedQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "mun_marauder_2");
    }
    public boolean mun_quest_marauder_condition_onQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mun_marauder_3");
    }
    public boolean mun_quest_marauder_condition_onLastTaskQ3(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mun_marauder_3", "final_task");
    }
    public boolean mun_quest_marauder_condition_finishedQ3(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "mun_marauder_3");
    }
    public boolean mun_quest_marauder_condition_onQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mun_marauder_4");
    }
    public boolean mun_quest_marauder_condition_onLastTaskQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mun_marauder_4", "final_task");
    }
    public boolean mun_quest_marauder_condition_finishedQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "mun_marauder_4");
    }
    public boolean mun_quest_marauder_condition_onQuest5(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mun_marauder_5");
    }
    public boolean mun_quest_marauder_condition_onLastTaskQ5(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mun_marauder_5", "final_task");
    }
    public boolean mun_quest_marauder_condition_finishedQ5(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "mun_marauder_5");
    }
    public boolean mun_quest_marauder_condition_onQuest6(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mun_marauder_6");
    }
    public boolean mun_quest_marauder_condition_onLastTaskQ6(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mun_marauder_6", "final_task");
    }
    public boolean mun_quest_marauder_condition_finishedQ6(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "mun_marauder_6");
    }
    public boolean mun_quest_marauder_condition_onQuest7(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mun_marauder_7");
    }
    public boolean mun_quest_marauder_condition_onLastTaskQ7(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mun_marauder_7", "final_task");
    }
    public boolean mun_quest_marauder_condition_finishedQ7(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "mun_marauder_7");
    }
    public boolean mun_quest_marauder_condition_onQuest8(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mun_marauder_8");
    }
    public boolean mun_quest_marauder_condition_onLastTaskQ8(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mun_marauder_8", "final_task");
    }
    public boolean mun_quest_marauder_condition_finishedQ8(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "mun_marauder_8");
    }
    public boolean mun_quest_marauder_condition_onQuest9(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mun_marauder_9");
    }
    public boolean mun_quest_marauder_condition_onLastTaskQ9(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mun_marauder_9", "final_task");
    }
    public boolean mun_quest_marauder_condition_finishedQ9(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "mun_marauder_9");
    }
    public boolean mun_quest_marauder_condition_isMunCrafterLvl86(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "class_munitions_phase4_05");
    }
    public boolean mun_quest_marauder_condition_hasHighQualityQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mun_quest_marauder_condition_isCraftedByPlayerQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBoots = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_boots.iff", true, false);
        obj_id[] boneBoots = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_boots.iff", true, false);
        obj_id[] mabariBoots = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_boots.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBoots.length; ++i)
        {
            obj_id crafter = getCrafter(compositeBoots[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < boneBoots.length; ++i)
        {
            obj_id crafter = getCrafter(boneBoots[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < mabariBoots.length; ++i)
        {
            obj_id crafter = getCrafter(mabariBoots[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_hasAllItemsQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBoots = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_boots.iff", true, false);
        obj_id[] boneBoots = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_boots.iff", true, false);
        obj_id[] mabariBoots = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_boots.iff", true, false);
        if (compositeBoots == null || compositeBoots.length < 3)
        {
            return false;
        }
        if (boneBoots == null || boneBoots.length < 3)
        {
            return false;
        }
        if (mabariBoots == null || mabariBoots.length < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_hasAllItemsQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bracer_l.iff", true, false);
        obj_id[] boneBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bracer_l.iff", true, false);
        obj_id[] paddedBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bracer_l.iff", true, false);
        if (compositeBracers == null || compositeBracers.length < 3)
        {
            return false;
        }
        if (boneBracers == null || boneBracers.length < 3)
        {
            return false;
        }
        if (paddedBracers == null || paddedBracers.length < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_isCraftedByPlayerQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bracer_l.iff", true, false);
        obj_id[] boneBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bracer_l.iff", true, false);
        obj_id[] paddedBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bracer_l.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBracers.length; ++i)
        {
            obj_id crafter = getCrafter(compositeBracers[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < boneBracers.length; ++i)
        {
            obj_id crafter = getCrafter(boneBracers[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < paddedBracers.length; ++i)
        {
            obj_id crafter = getCrafter(paddedBracers[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_hasAllItemsQ3(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bracer_r.iff", true, false);
        obj_id[] boneBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bracer_r.iff", true, false);
        obj_id[] paddedBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bracer_r.iff", true, false);
        if (compositeBracers == null || compositeBracers.length < 3)
        {
            return false;
        }
        if (boneBracers == null || boneBracers.length < 3)
        {
            return false;
        }
        if (paddedBracers == null || paddedBracers.length < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_isCraftedByPlayerQ3(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bracer_r.iff", true, false);
        obj_id[] boneBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bracer_r.iff", true, false);
        obj_id[] paddedBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bracer_r.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBracers.length; ++i)
        {
            obj_id crafter = getCrafter(compositeBracers[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < boneBracers.length; ++i)
        {
            obj_id crafter = getCrafter(boneBracers[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < paddedBracers.length; ++i)
        {
            obj_id crafter = getCrafter(paddedBracers[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_hasAllItemsQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bicep_r.iff", true, false);
        obj_id[] boneBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bicep_r.iff", true, false);
        obj_id[] paddedBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bicep_r.iff", true, false);
        if (compositeBiceps == null || compositeBiceps.length < 3)
        {
            return false;
        }
        if (boneBiceps == null || boneBiceps.length < 3)
        {
            return false;
        }
        if (paddedBiceps == null || paddedBiceps.length < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_isCraftedByPlayerQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bicep_r.iff", true, false);
        obj_id[] boneBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bicep_r.iff", true, false);
        obj_id[] paddedBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bicep_r.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBiceps.length; ++i)
        {
            obj_id crafter = getCrafter(compositeBiceps[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < boneBiceps.length; ++i)
        {
            obj_id crafter = getCrafter(boneBiceps[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < paddedBiceps.length; ++i)
        {
            obj_id crafter = getCrafter(paddedBiceps[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_hasAllItemsQ5(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bicep_l.iff", true, false);
        obj_id[] boneBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bicep_l.iff", true, false);
        obj_id[] paddedBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bicep_l.iff", true, false);
        if (compositeBiceps == null || compositeBiceps.length < 3)
        {
            return false;
        }
        if (boneBiceps == null || boneBiceps.length < 3)
        {
            return false;
        }
        if (paddedBiceps == null || paddedBiceps.length < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_isCraftedByPlayerQ5(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bicep_l.iff", true, false);
        obj_id[] boneBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bicep_l.iff", true, false);
        obj_id[] paddedBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bicep_l.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBiceps.length; ++i)
        {
            obj_id crafter = getCrafter(compositeBiceps[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < boneBiceps.length; ++i)
        {
            obj_id crafter = getCrafter(boneBiceps[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < paddedBiceps.length; ++i)
        {
            obj_id crafter = getCrafter(paddedBiceps[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_hasAllItemsQ6(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeGloves = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_gloves.iff", true, false);
        obj_id[] boneGloves = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_gloves.iff", true, false);
        obj_id[] mabariGloves = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_gloves.iff", true, false);
        if (compositeGloves == null || compositeGloves.length < 3)
        {
            return false;
        }
        if (boneGloves == null || boneGloves.length < 3)
        {
            return false;
        }
        if (mabariGloves == null || mabariGloves.length < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_isCraftedByPlayerQ6(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeGloves = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_gloves.iff", true, false);
        obj_id[] boneGloves = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_gloves.iff", true, false);
        obj_id[] mabariGloves = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_gloves.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeGloves.length; ++i)
        {
            obj_id crafter = getCrafter(compositeGloves[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < boneGloves.length; ++i)
        {
            obj_id crafter = getCrafter(boneGloves[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < mabariGloves.length; ++i)
        {
            obj_id crafter = getCrafter(mabariGloves[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_hasAllItemsQ7(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeHelmets = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_helmet.iff", true, false);
        obj_id[] boneHelmets = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_helmet.iff", true, false);
        obj_id[] mabariHelmets = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_helmet.iff", true, false);
        if (compositeHelmets == null || compositeHelmets.length < 3)
        {
            return false;
        }
        if (boneHelmets == null || boneHelmets.length < 3)
        {
            return false;
        }
        if (mabariHelmets == null || mabariHelmets.length < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_isCraftedByPlayerQ7(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeHelmets = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_helmet.iff", true, false);
        obj_id[] boneHelmets = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_helmet.iff", true, false);
        obj_id[] mabariHelmets = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_helmet.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeHelmets.length; ++i)
        {
            obj_id crafter = getCrafter(compositeHelmets[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < boneHelmets.length; ++i)
        {
            obj_id crafter = getCrafter(boneHelmets[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < mabariHelmets.length; ++i)
        {
            obj_id crafter = getCrafter(mabariHelmets[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_isCraftedByPlayerQ8(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeLeggings = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_leggings.iff", true, false);
        obj_id[] boneLeggings = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_leggings.iff", true, false);
        obj_id[] mabariLeggings = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_pants.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeLeggings.length; ++i)
        {
            obj_id crafter = getCrafter(compositeLeggings[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < boneLeggings.length; ++i)
        {
            obj_id crafter = getCrafter(boneLeggings[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < mabariLeggings.length; ++i)
        {
            obj_id crafter = getCrafter(mabariLeggings[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_hasAllItemsQ8(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeLeggings = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_leggings.iff", true, false);
        obj_id[] boneLeggings = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_leggings.iff", true, false);
        obj_id[] mabariLeggings = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_pants.iff", true, false);
        if (compositeLeggings == null || compositeLeggings.length < 3)
        {
            return false;
        }
        if (boneLeggings == null || boneLeggings.length < 3)
        {
            return false;
        }
        if (mabariLeggings == null || mabariLeggings.length < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_hasAllItemsQ9(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeChests = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_chest_plate.iff", true, false);
        obj_id[] boneChests = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_chest_plate.iff", true, false);
        obj_id[] mabariChests = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_chest_plate.iff", true, false);
        if (compositeChests == null || compositeChests.length < 3)
        {
            return false;
        }
        if (boneChests == null || boneChests.length < 3)
        {
            return false;
        }
        if (mabariChests == null || mabariChests.length < 3)
        {
            return false;
        }
        return true;
    }
    public boolean mun_quest_marauder_condition_isCraftedByPlayerQ9(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeChests = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_chest_plate.iff", true, false);
        obj_id[] boneChests = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_chest_plate.iff", true, false);
        obj_id[] mabariChests = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_chest_plate.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeChests.length; ++i)
        {
            obj_id crafter = getCrafter(compositeChests[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < boneChests.length; ++i)
        {
            obj_id crafter = getCrafter(boneChests[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        count = 0;
        for (int i = 0; i < mabariChests.length; ++i)
        {
            obj_id crafter = getCrafter(mabariChests[i]);
            if (crafter == player)
            {
                ++count;
            }
        }
        if (count < 3)
        {
            return false;
        }
        return true;
    }
    public void mun_quest_marauder_action_grantQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mun_marauder_1");
        groundquests.grantQuest(player, "mun_marauder_1");
    }
    public void mun_quest_marauder_action_signalQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBoots = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_boots.iff", true, false);
        obj_id[] boneBoots = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_boots.iff", true, false);
        obj_id[] mabariBoots = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_boots.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBoots.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(compositeBoots[i]);
            if (crafter == player)
            {
                destroyObject(compositeBoots[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < boneBoots.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(boneBoots[i]);
            if (crafter == player)
            {
                destroyObject(boneBoots[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < mabariBoots.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(mabariBoots[i]);
            if (crafter == player)
            {
                destroyObject(mabariBoots[i]);
                ++count;
            }
        }
        groundquests.sendSignal(player, "mun_marauder_1_signal");
    }
    public void mun_quest_marauder_action_grantQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mun_marauder_2");
        groundquests.grantQuest(player, "mun_marauder_2");
    }
    public void mun_quest_marauder_action_grantQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mun_marauder_3");
        groundquests.grantQuest(player, "mun_marauder_3");
    }
    public void mun_quest_marauder_action_signalQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bracer_l.iff", true, false);
        obj_id[] boneBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bracer_l.iff", true, false);
        obj_id[] paddedBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bracer_l.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBracers.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(compositeBracers[i]);
            if (crafter == player)
            {
                destroyObject(compositeBracers[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < boneBracers.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(boneBracers[i]);
            if (crafter == player)
            {
                destroyObject(boneBracers[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < paddedBracers.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(paddedBracers[i]);
            if (crafter == player)
            {
                destroyObject(paddedBracers[i]);
                ++count;
            }
        }
        groundquests.sendSignal(player, "mun_marauder_2_signal");
    }
    public void mun_quest_marauder_action_signalQ3(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bracer_r.iff", true, false);
        obj_id[] boneBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bracer_r.iff", true, false);
        obj_id[] paddedBracers = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bracer_r.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBracers.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(compositeBracers[i]);
            if (crafter == player)
            {
                destroyObject(compositeBracers[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < boneBracers.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(boneBracers[i]);
            if (crafter == player)
            {
                destroyObject(boneBracers[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < paddedBracers.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(paddedBracers[i]);
            if (crafter == player)
            {
                destroyObject(paddedBracers[i]);
                ++count;
            }
        }
        groundquests.sendSignal(player, "mun_marauder_3_signal");
    }
    public void mun_quest_marauder_action_grantQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mun_marauder_4");
        groundquests.grantQuest(player, "mun_marauder_4");
    }
    public void mun_quest_marauder_action_signalQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bicep_r.iff", true, false);
        obj_id[] boneBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bicep_r.iff", true, false);
        obj_id[] paddedBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bicep_r.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBiceps.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(compositeBiceps[i]);
            if (crafter == player)
            {
                destroyObject(compositeBiceps[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < boneBiceps.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(boneBiceps[i]);
            if (crafter == player)
            {
                destroyObject(boneBiceps[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < paddedBiceps.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(paddedBiceps[i]);
            if (crafter == player)
            {
                destroyObject(paddedBiceps[i]);
                ++count;
            }
        }
        groundquests.sendSignal(player, "mun_marauder_4_signal");
    }
    public void mun_quest_marauder_action_grantQuest5(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mun_marauder_5");
        groundquests.grantQuest(player, "mun_marauder_5");
    }
    public void mun_quest_marauder_action_signalQ5(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_bicep_l.iff", true, false);
        obj_id[] boneBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_bicep_l.iff", true, false);
        obj_id[] paddedBiceps = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/padded/armor_padded_s01_bicep_l.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeBiceps.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(compositeBiceps[i]);
            if (crafter == player)
            {
                destroyObject(compositeBiceps[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < boneBiceps.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(boneBiceps[i]);
            if (crafter == player)
            {
                destroyObject(boneBiceps[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < paddedBiceps.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(paddedBiceps[i]);
            if (crafter == player)
            {
                destroyObject(paddedBiceps[i]);
                ++count;
            }
        }
        groundquests.sendSignal(player, "mun_marauder_5_signal");
    }
    public void mun_quest_marauder_action_grantQuest6(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mun_marauder_6");
        groundquests.grantQuest(player, "mun_marauder_6");
    }
    public void mun_quest_marauder_action_signalQ6(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeGloves = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_gloves.iff", true, false);
        obj_id[] boneGloves = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_gloves.iff", true, false);
        obj_id[] mabariGloves = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_gloves.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeGloves.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(compositeGloves[i]);
            if (crafter == player)
            {
                destroyObject(compositeGloves[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < boneGloves.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(boneGloves[i]);
            if (crafter == player)
            {
                destroyObject(boneGloves[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < mabariGloves.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(mabariGloves[i]);
            if (crafter == player)
            {
                destroyObject(mabariGloves[i]);
                ++count;
            }
        }
        groundquests.sendSignal(player, "mun_marauder_6_signal");
    }
    public void mun_quest_marauder_action_grantQuest7(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mun_marauder_7");
        groundquests.grantQuest(player, "mun_marauder_7");
    }
    public void mun_quest_marauder_action_signalQ7(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeHelmets = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_helmet.iff", true, false);
        obj_id[] boneHelmets = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_helmet.iff", true, false);
        obj_id[] mabariHelmets = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_helmet.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeHelmets.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(compositeHelmets[i]);
            if (crafter == player)
            {
                destroyObject(compositeHelmets[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < boneHelmets.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(boneHelmets[i]);
            if (crafter == player)
            {
                destroyObject(boneHelmets[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < mabariHelmets.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(mabariHelmets[i]);
            if (crafter == player)
            {
                destroyObject(mabariHelmets[i]);
                ++count;
            }
        }
        groundquests.sendSignal(player, "mun_marauder_7_signal");
    }
    public void mun_quest_marauder_action_grantQuest8(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mun_marauder_8");
        groundquests.grantQuest(player, "mun_marauder_8");
    }
    public void mun_quest_marauder_action_signalQ8(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeLeggings = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_leggings.iff", true, false);
        obj_id[] boneLeggings = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_leggings.iff", true, false);
        obj_id[] mabariLeggings = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_pants.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeLeggings.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(compositeLeggings[i]);
            if (crafter == player)
            {
                destroyObject(compositeLeggings[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < boneLeggings.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(boneLeggings[i]);
            if (crafter == player)
            {
                destroyObject(boneLeggings[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < mabariLeggings.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(mabariLeggings[i]);
            if (crafter == player)
            {
                destroyObject(mabariLeggings[i]);
                ++count;
            }
        }
        groundquests.sendSignal(player, "mun_marauder_8_signal");
    }
    public void mun_quest_marauder_action_grantQuest9(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mun_marauder_9");
        groundquests.grantQuest(player, "mun_marauder_9");
    }
    public void mun_quest_marauder_action_signalQ9(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] compositeChests = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/composite/armor_composite_chest_plate.iff", true, false);
        obj_id[] boneChests = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/bone/armor_bone_s01_chest_plate.iff", true, false);
        obj_id[] mabariChests = utils.getAllItemsInContainerByTemplate(player, "object/tangible/wearables/armor/zam/armor_zam_wesell_chest_plate.iff", true, false);
        int count = 0;
        for (int i = 0; i < compositeChests.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(compositeChests[i]);
            if (crafter == player)
            {
                destroyObject(compositeChests[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < boneChests.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(boneChests[i]);
            if (crafter == player)
            {
                destroyObject(boneChests[i]);
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < mabariChests.length; ++i)
        {
            if (count >= 3)
            {
                break;
            }
            obj_id crafter = getCrafter(mabariChests[i]);
            if (crafter == player)
            {
                destroyObject(mabariChests[i]);
                ++count;
            }
        }
        groundquests.sendSignal(player, "mun_marauder_9_signal");
    }
    public int mun_quest_marauder_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_121"))
        {
            if (!mun_quest_marauder_condition_hasAllItemsQ9(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_238");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mun_quest_marauder_condition_isCraftedByPlayerQ9(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_242");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_signalQ9(player, npc);
                string_id message = new string_id(c_stringFile, "s_122");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_117"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_118");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_119"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_277");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_278");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_279");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_278"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest9(player, npc);
                string_id message = new string_id(c_stringFile, "s_114");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_279"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_280");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest9(player, npc);
                string_id message = new string_id(c_stringFile, "s_114");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111"))
        {
            if (!mun_quest_marauder_condition_hasAllItemsQ8(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_235");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mun_quest_marauder_condition_isCraftedByPlayerQ8(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_237");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_signalQ8(player, npc);
                string_id message = new string_id(c_stringFile, "s_112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_113");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_113"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest9(player, npc);
                string_id message = new string_id(c_stringFile, "s_114");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_107"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_109");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_108"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_273");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_274");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_275");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_274"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest8(player, npc);
                string_id message = new string_id(c_stringFile, "s_106");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_275"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_276");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_103"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest8(player, npc);
                string_id message = new string_id(c_stringFile, "s_106");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_93"))
        {
            if (!mun_quest_marauder_condition_hasAllItemsQ7(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_231");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mun_quest_marauder_condition_isCraftedByPlayerQ7(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_signalQ7(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_105"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest8(player, npc);
                string_id message = new string_id(c_stringFile, "s_106");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_91");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_308");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_309");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_310");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_309"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest7(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_310"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_311");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_87"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest7(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (!mun_quest_marauder_condition_hasAllItemsQ6(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_227");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mun_quest_marauder_condition_isCraftedByPlayerQ6(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_229");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_signalQ6(player, npc);
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest7(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_304");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_305");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_306");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_305"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest6(player, npc);
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_306"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_307");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest6(player, npc);
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_110"))
        {
            if (!mun_quest_marauder_condition_hasAllItemsQ5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_223");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mun_quest_marauder_condition_isCraftedByPlayerQ5(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_225");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_signalQ5(player, npc);
                string_id message = new string_id(c_stringFile, "s_124");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_126"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest6(player, npc);
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_136"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_300");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_301");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_302");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_301"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_156");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_302"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_303");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_144"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_156");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_148"))
        {
            if (!mun_quest_marauder_condition_hasAllItemsQ4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_219");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mun_quest_marauder_condition_isCraftedByPlayerQ4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_signalQ4(player, npc);
                string_id message = new string_id(c_stringFile, "s_152");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_154");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_154"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_156");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_160"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_162");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_164"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_296");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_298");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_297"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_184");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_298"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_299");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_172"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_184");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_176"))
        {
            if (!mun_quest_marauder_condition_hasAllItemsQ3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_215");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mun_quest_marauder_condition_isCraftedByPlayerQ3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_217");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_signalQ3(player, npc);
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_182"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_184");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_188"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_190");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_192"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_292");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_293");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_294");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_293"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_214");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_294"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_295");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_200"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_214");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_204"))
        {
            if (!mun_quest_marauder_condition_hasAllItemsQ2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_206");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mun_quest_marauder_condition_isCraftedByPlayerQ2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_208");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_signalQ2(player, npc);
                string_id message = new string_id(c_stringFile, "s_210");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_212");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_212"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_214");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_222"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_226");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_230"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_288");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_289");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_290");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_289"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_270");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_290"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_291");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_244"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_270");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_248"))
        {
            if (!mun_quest_marauder_condition_hasAllItemsQ1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_250");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mun_quest_marauder_condition_isCraftedByPlayerQ1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_256");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_signalQ1(player, npc);
                string_id message = new string_id(c_stringFile, "s_262");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 86);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_264"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_270");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_282"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_284");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_286"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_312");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_314");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 90);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch90(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_314"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_318"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_320");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch93(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_324"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_326");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch94(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_328"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_330");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_332");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch95(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_332"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 96);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch96(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_336"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                mun_quest_marauder_action_grantQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_338");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mun_quest_marauder_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_340");
                    }
                    utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 97);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mun_quest_marauder_handleBranch97(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_340"))
        {
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_342");
                utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.mun_quest_marauder");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.mun_quest_marauder");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (mun_quest_marauder_condition_finishedQ9(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onLastTaskQ9(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_98");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 2);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onQuest9(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_97");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 6);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_finishedQ8(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_99");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 10);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onLastTaskQ8(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_100");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 11);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onQuest8(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_101");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 16);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_finishedQ7(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_102");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 20);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onLastTaskQ7(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_42");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 21);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onQuest7(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_50");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 26);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_finishedQ6(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_59");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 30);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onLastTaskQ6(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_62");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 31);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onQuest6(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_70");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 36);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_finishedQ5(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_82");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 40);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onLastTaskQ5(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_96");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 41);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onQuest5(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_130");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_136");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 46);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_finishedQ4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_142");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_144");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 50);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onLastTaskQ4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_146");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_148");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 51);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onQuest4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_158");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_164");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 56);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_finishedQ3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_170");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 60);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onLastTaskQ3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_174");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_176");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 61);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onQuest3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_186");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_188");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 66);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_finishedQ2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_198");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_200");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 70);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onLastTaskQ2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_202");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_204");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 71);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onQuest2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_218");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_230");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 76);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_finishedQ1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_241");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_244");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 80);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onLastTaskQ1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_246");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_248");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 81);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_onQuest1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_272");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_282");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_286");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 88);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition_isMunCrafterLvl86(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_322");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mun_quest_marauder_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_324");
                }
                utils.setScriptVar(player, "conversation.mun_quest_marauder.branchId", 93);
                npcStartConversation(player, npc, "mun_quest_marauder", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mun_quest_marauder_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_344");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("mun_quest_marauder"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mun_quest_marauder.branchId");
        if (branchId == 2 && mun_quest_marauder_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && mun_quest_marauder_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && mun_quest_marauder_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && mun_quest_marauder_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && mun_quest_marauder_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && mun_quest_marauder_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && mun_quest_marauder_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && mun_quest_marauder_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && mun_quest_marauder_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && mun_quest_marauder_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && mun_quest_marauder_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && mun_quest_marauder_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && mun_quest_marauder_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && mun_quest_marauder_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && mun_quest_marauder_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && mun_quest_marauder_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && mun_quest_marauder_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && mun_quest_marauder_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && mun_quest_marauder_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && mun_quest_marauder_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && mun_quest_marauder_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && mun_quest_marauder_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && mun_quest_marauder_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && mun_quest_marauder_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && mun_quest_marauder_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && mun_quest_marauder_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && mun_quest_marauder_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && mun_quest_marauder_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && mun_quest_marauder_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && mun_quest_marauder_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && mun_quest_marauder_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && mun_quest_marauder_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && mun_quest_marauder_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && mun_quest_marauder_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && mun_quest_marauder_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && mun_quest_marauder_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && mun_quest_marauder_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && mun_quest_marauder_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && mun_quest_marauder_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && mun_quest_marauder_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && mun_quest_marauder_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && mun_quest_marauder_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && mun_quest_marauder_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && mun_quest_marauder_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && mun_quest_marauder_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && mun_quest_marauder_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && mun_quest_marauder_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && mun_quest_marauder_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && mun_quest_marauder_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && mun_quest_marauder_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && mun_quest_marauder_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && mun_quest_marauder_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && mun_quest_marauder_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && mun_quest_marauder_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && mun_quest_marauder_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && mun_quest_marauder_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && mun_quest_marauder_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && mun_quest_marauder_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && mun_quest_marauder_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && mun_quest_marauder_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 90 && mun_quest_marauder_handleBranch90(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 93 && mun_quest_marauder_handleBranch93(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 94 && mun_quest_marauder_handleBranch94(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 95 && mun_quest_marauder_handleBranch95(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 96 && mun_quest_marauder_handleBranch96(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 97 && mun_quest_marauder_handleBranch97(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mun_quest_marauder.branchId");
        return SCRIPT_CONTINUE;
    }
}
