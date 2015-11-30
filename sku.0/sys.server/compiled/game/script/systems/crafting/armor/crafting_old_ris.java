package script.systems.crafting.armor;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_old_ris extends script.base_script
{
    public crafting_old_ris()
    {
    }
    public static final String VERSION = "v0.00.00";
    public static final string_id SID_RIS_SCHEMATICS_UPDATED = new string_id("crafting", "ris_schematics_updated");
    public int OnRequestResourceWeights(obj_id self, obj_id player, String[] desiredAttribs, String[] attribs, int[] slots, int[] counts, int[] data) throws InterruptedException
    {
        if (hasOldRisBoot(player))
        {
            if (hasOldRisChest(player))
            {
                fullSchematicConversion(player);
                sendSystemMessage(player, SID_RIS_SCHEMATICS_UPDATED);
            }
            else 
            {
                bootSchematicConversion(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasOldRisChest(obj_id player) throws InterruptedException
    {
        return (hasSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_chest_plate.iff"));
    }
    public boolean hasOldRisBoot(obj_id player) throws InterruptedException
    {
        return (hasSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_boots.iff"));
    }
    public void fullSchematicConversion(obj_id player) throws InterruptedException
    {
        revokeSchematic(player, "object/draft_schematic/armor/component/armor_layer_ris.iff");
        revokeSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_bicep_l.iff");
        revokeSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_bicep_r.iff");
        revokeSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_bracer_l.iff");
        revokeSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_bracer_r.iff");
        revokeSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_chest_plate.iff");
        revokeSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_gloves.iff");
        revokeSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_helmet.iff");
        revokeSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_leggings.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_enhancement_feather.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_ris_bicep_l.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_ris_bicep_r.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_ris_bracer_l.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_ris_bracer_r.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_ris_chest.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_ris_gloves.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_ris_helmet.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_ris_leggings.iff");
        return;
    }
    public void bootSchematicConversion(obj_id player) throws InterruptedException
    {
        revokeSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_boots.iff");
        grantSchematic(player, "object/draft_schematic/armor/armor_appearance_ris_boots.iff");
        return;
    }
}
