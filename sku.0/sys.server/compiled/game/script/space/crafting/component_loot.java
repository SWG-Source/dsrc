package script.space.crafting;

import script.library.space_crafting;
import script.library.utils;
import script.obj_id;
import script.space.rare_loot.space_rare_loot;

public class component_loot extends script.base_script
{
    public component_loot()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "ship_comp.armor_hitpoints_current"))
        {
            space_crafting.initializeSpaceShipComponent(self);
        }
        int flags = getIntObjVar(self, "ship_comp.flags");
        boolean isBitSet = (flags & ship_component_flags.SCF_reverse_engineered) != 0;
        obj_id player = utils.getContainingPlayer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if(self == null || self == obj_id.NULL_ID || !isIdValid(self)){
            return SCRIPT_CONTINUE;
        }
        if ((names == null) || (attribs == null) || (names.length != attribs.length))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, space_rare_loot.VAR_ITEM_IS_RARE_SPACE_LOOT))
        {
            int idx = utils.getValidAttributeIndex(names);
            if (idx == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "rare_loot_category";
            attribs[idx] = "\\#ed8d16" + getRareSpaceLootDisplayName(self);
        }
        int flags = getIntObjVar(self, "ship_comp.flags");
        boolean isBitSet = (flags & ship_component_flags.SCF_reverse_engineered) != 0;
        if (isBitSet)
        {
            int idx = utils.getValidAttributeIndex(names);
            if (idx == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "reverseEngineered";
            attribs[idx] = "";
        }
        else 
        {
            int level = space_crafting.getReverseEngineeringLevel(self);
            int idx = utils.getValidAttributeIndex(names);
            if (idx == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "reverseEngineeringLevel";
            attribs[idx] = Integer.toString(level);
        }
        if (hasObjVar(self, "isMiningLaser"))
        {
            float min = space_crafting.getWeaponMinimumDamage(self);
            float max = space_crafting.getWeaponMaximumDamage(self);
            min = min / 100;
            max = max / 100;
            String atr = min + " - " + max;
            int idx = utils.getValidAttributeIndex(names);
            if (idx == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "miningExtractionRate";
            attribs[idx] = atr;
        }
        return SCRIPT_CONTINUE;
    }

    public String getRareSpaceLootDisplayName(obj_id self) throws InterruptedException
    {
        String qualityName = "";
        if (hasObjVar(self, space_rare_loot.VAR_ITEM_REWARD_QUALITY_NAME))
        {
            qualityName = getStringObjVar(self, space_rare_loot.VAR_ITEM_REWARD_QUALITY_NAME);
        }
        else if (hasObjVar(self, space_rare_loot.VAR_ITEM_REWARD_QUALITY))
        {
            qualityName = space_rare_loot.getRewardQualityName(getIntObjVar(self, space_rare_loot.VAR_ITEM_REWARD_QUALITY));
        }
        if (qualityName == null || qualityName.equals(""))
        {
            qualityName = "rare";
        }
        return toDisplayCase(qualityName) + " Space Part";
    }

    public String toDisplayCase(String value) throws InterruptedException
    {
        if (value == null || value.equals(""))
        {
            return "";
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }
}
