package script.working.wwallace;

import script.library.skill;
import script.library.utils;
import script.obj_id;

public class create_space_weapons extends script.base_script
{
    public create_space_weapons()
    {
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(self);
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equals("createWeapons"))
        {
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_armek_sw4.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_armek_sw7.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_borstel_rg9.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_corellian_1d.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_corellian_ag1g_laser.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_corellian_ag2g_quad_laser.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_gyrhil_auto_blaster.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_gyrhil_r9x.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_incom_blaster.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_incom_disruptor.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_incom_quad_blaster.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_incom_shredder.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_seinar_disruptor.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_seinar_ion_cannon.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_seinar_linked_cannon.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_seinar_ls1.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_seinar_ls72.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_slayn_ioncannon.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_subpro_tripleblaster.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_subpro_tripleblaster_mark2.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_taim_heavy_laser.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_taim_ix4.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_taim_kx5.iff", objInventory);
            createObjectOverloaded("object/tangible/ship/components/weapon/wpn_taim_kx9.iff", objInventory);
        }
        if (strCommands[0].equals("grantPilotSkills"))
        {
            skill.grantSkill(self, "pilot_rebel_navy");
            skill.grantSkill(self, "pilot_rebel_navy_novice");
            skill.grantSkill(self, "pilot_rebel_navy_master");
            skill.grantSkill(self, "pilot_rebel_navy_starships_01");
            skill.grantSkill(self, "pilot_rebel_navy_starships_02");
            skill.grantSkill(self, "pilot_rebel_navy_starships_03");
            skill.grantSkill(self, "pilot_rebel_navy_starships_04");
            skill.grantSkill(self, "pilot_rebel_navy_weapons_01");
            skill.grantSkill(self, "pilot_rebel_navy_weapons_02");
            skill.grantSkill(self, "pilot_rebel_navy_weapons_03");
            skill.grantSkill(self, "pilot_rebel_navy_weapons_04");
            skill.grantSkill(self, "pilot_rebel_navy_procedures_01");
            skill.grantSkill(self, "pilot_rebel_navy_procedures_02");
            skill.grantSkill(self, "pilot_rebel_navy_procedures_03");
            skill.grantSkill(self, "pilot_rebel_navy_procedures_04");
            skill.grantSkill(self, "pilot_rebel_navy_droid_01");
            skill.grantSkill(self, "pilot_rebel_navy_droid_02");
            skill.grantSkill(self, "pilot_rebel_navy_droid_03");
            skill.grantSkill(self, "pilot_rebel_navy_droid_04");
            skill.grantSkill(self, "pilot_imperial_navy");
            skill.grantSkill(self, "pilot_imperial_navy_novice");
            skill.grantSkill(self, "pilot_imperial_navy_master");
            skill.grantSkill(self, "pilot_imperial_navy_starships_01");
            skill.grantSkill(self, "pilot_imperial_navy_starships_02");
            skill.grantSkill(self, "pilot_imperial_navy_starships_03");
            skill.grantSkill(self, "pilot_imperial_navy_starships_04");
            skill.grantSkill(self, "pilot_imperial_navy_weapons_01");
            skill.grantSkill(self, "pilot_imperial_navy_weapons_02");
            skill.grantSkill(self, "pilot_imperial_navy_weapons_03");
            skill.grantSkill(self, "pilot_imperial_navy_weapons_04");
            skill.grantSkill(self, "pilot_imperial_navy_procedures_01");
            skill.grantSkill(self, "pilot_imperial_navy_procedures_02");
            skill.grantSkill(self, "pilot_imperial_navy_procedures_03");
            skill.grantSkill(self, "pilot_imperial_navy_procedures_04");
            skill.grantSkill(self, "pilot_imperial_navy_droid_01");
            skill.grantSkill(self, "pilot_imperial_navy_droid_02");
            skill.grantSkill(self, "pilot_imperial_navy_droid_03");
            skill.grantSkill(self, "pilot_imperial_navy_droid_04");
            skill.grantSkill(self, "pilot_neutral");
            skill.grantSkill(self, "pilot_neutral_novice");
            skill.grantSkill(self, "pilot_neutral_master");
            skill.grantSkill(self, "pilot_neutral_starships_01");
            skill.grantSkill(self, "pilot_neutral_starships_02");
            skill.grantSkill(self, "pilot_neutral_starships_03");
            skill.grantSkill(self, "pilot_neutral_starships_04");
            skill.grantSkill(self, "pilot_neutral_weapons_01");
            skill.grantSkill(self, "pilot_neutral_weapons_02");
            skill.grantSkill(self, "pilot_neutral_weapons_03");
            skill.grantSkill(self, "pilot_neutral_weapons_04");
            skill.grantSkill(self, "pilot_neutral_procedures_01");
            skill.grantSkill(self, "pilot_neutral_procedures_02");
            skill.grantSkill(self, "pilot_neutral_procedures_03");
            skill.grantSkill(self, "pilot_neutral_procedures_04");
            skill.grantSkill(self, "pilot_neutral_droid_01");
            skill.grantSkill(self, "pilot_neutral_droid_02");
            skill.grantSkill(self, "pilot_neutral_droid_03");
            skill.grantSkill(self, "pilot_neutral_droid_04");
        }
        return SCRIPT_CONTINUE;
    }
}
