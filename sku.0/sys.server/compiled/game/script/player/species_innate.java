package script.player;

import script.library.innate;
import script.library.prose;
import script.*;

public class species_innate extends script.base_script
{
    public species_innate()
    {
    }
    public static final String SCRIPT_ME = "player.species_innate";
    public static final string_id SID_NONE = new string_id("innate", "none");
    public static final string_id SID_VALID_INNATE_PARAMS = new string_id("innate", "valid_innate_params");
    public static final string_id SID_NOT_VALID_PARAM_INNATE = new string_id("innate", "not_valid_param_innate");
    public static final string_id SID_INNATE_ABILITY_FAILED = new string_id("innate", "innate_ability_failed");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, innate.VAR_INNATE_BASE))
        {
            obj_var_list ovl = getObjVarList(self, innate.VAR_INNATE_BASE);
            if (ovl != null)
            {
                int now = getGameTime();
                int maxStamp = now + (2 * innate.ONE_HOUR);
                int numItems = ovl.getNumItems();
                for (int i = 0; i < numItems; i++)
                {
                    obj_var ov = ovl.getObjVar(i);
                    if (ov.getIntData() > maxStamp)
                    {
                        setObjVar(self, innate.VAR_INNATE_BASE + "." + ov.getName(), now - innate.ONE_HOUR);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int species = getSpecies(self);
        switch (species)
        {
            case SPECIES_BOTHAN:
            grantSkill(self, "species_bothan");
            grantSkill(self, "social_language_basic_speak");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_bothan_speak");
            grantSkill(self, "social_language_bothan_comprehend");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            case SPECIES_HUMAN:
            grantSkill(self, "species_human");
            grantSkill(self, "social_language_basic_speak");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            case SPECIES_MON_CALAMARI:
            grantSkill(self, "species_moncal");
            grantSkill(self, "social_language_basic_speak");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_moncalamari_speak");
            grantSkill(self, "social_language_moncalamari_comprehend");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            case SPECIES_RODIAN:
            grantSkill(self, "species_rodian");
            grantSkill(self, "social_language_basic_speak");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_rodian_speak");
            grantSkill(self, "social_language_rodian_comprehend");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            case SPECIES_TRANDOSHAN:
            grantSkill(self, "species_trandoshan");
            grantSkill(self, "social_language_basic_speak");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_trandoshan_speak");
            grantSkill(self, "social_language_trandoshan_comprehend");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            case SPECIES_TWILEK:
            grantSkill(self, "species_twilek");
            grantSkill(self, "social_language_basic_speak");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_twilek_speak");
            grantSkill(self, "social_language_twilek_comprehend");
            grantSkill(self, "social_language_lekku_speak");
            grantSkill(self, "social_language_lekku_comprehend");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            case SPECIES_WOOKIEE:
            grantSkill(self, "species_wookiee");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_wookiee_speak");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            case SPECIES_ZABRAK:
            grantSkill(self, "species_zabrak");
            grantSkill(self, "social_language_basic_speak");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_zabrak_speak");
            grantSkill(self, "social_language_zabrak_comprehend");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            case SPECIES_ITHORIAN:
            grantSkill(self, "species_ithorian");
            grantSkill(self, "social_language_basic_speak");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_ithorian_speak");
            grantSkill(self, "social_language_ithorian_comprehend");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            case SPECIES_SULLUSTAN:
            grantSkill(self, "species_sullustan");
            grantSkill(self, "social_language_basic_speak");
            grantSkill(self, "social_language_basic_comprehend");
            grantSkill(self, "social_language_sullustan_speak");
            grantSkill(self, "social_language_sullustan_comprehend");
            grantSkill(self, "social_language_wookiee_comprehend");
            break;
            default:
            detachScript(self, SCRIPT_ME);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdInnate(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if ((params == null) || (params.equals("")))
        {
            String msg = "";
            String[] skillMods = getSkillStatModListingForPlayer(self);
            if ((skillMods != null) && (skillMods.length > 0))
            {
                for (String skillMod : skillMods) {
                    if (skillMod.startsWith("private_innate_")) {
                        String[] s = split(skillMod, '_');
                        msg += s[s.length - 1] + ", ";
                    }
                }
            }
            prose_package ppValidInnate = prose.getPackage(SID_VALID_INNATE_PARAMS);
            if ((msg == null) || (msg.equals("")))
            {
                prose.setTO(ppValidInnate, SID_NONE);
            }
            else 
            {
                if (msg.endsWith(", "))
                {
                    msg = msg.substring(0, msg.length() - 2);
                }
                prose.setTO(ppValidInnate, msg);
            }
            return SCRIPT_CONTINUE;
        }
        String cmd = innate.parseInnateCommand(params);
        if (cmd == null)
        {
            prose_package ppNotValid = prose.getPackage(SID_NOT_VALID_PARAM_INNATE);
            prose.setTO(ppNotValid, params);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            int modval = getSkillStatMod(self, "private_innate_" + cmd);
            if (modval > 0)
            {
                switch (cmd) {
                    case innate.REGEN:
                        queueCommand(self, (1397846664), null, "", COMMAND_PRIORITY_DEFAULT);
                        return SCRIPT_CONTINUE;
                    case innate.ROAR:
                        queueCommand(self, (-1223315403), null, "", COMMAND_PRIORITY_DEFAULT);
                        return SCRIPT_CONTINUE;
                    case innate.EQUIL:
                        queueCommand(self, (136144656), null, "", COMMAND_PRIORITY_DEFAULT);
                        return SCRIPT_CONTINUE;
                    case innate.VIT:
                        queueCommand(self, (1431834648), null, "", COMMAND_PRIORITY_DEFAULT);
                        return SCRIPT_CONTINUE;
                }
            }
        }
        prose_package pp = prose.getPackage(innate.PROSE_INNATE_NA, cmd);
        return SCRIPT_CONTINUE;
    }
    public int cmdInnateFail(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int cmdRegeneration(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int mod = getSkillStatMod(self, "private_innate_regeneration");
        if (mod == 1)
        {
            innate.regeneration();
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdVitalize(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int mod = getSkillStatMod(self, "private_innate_vitalize");
        if (mod == 1)
        {
            innate.vitalize();
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdEquilibrium(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int mod = getSkillStatMod(self, "private_innate_equilibrium");
        if (mod == 1)
        {
            innate.equilibrium();
        }
        return SCRIPT_CONTINUE;
    }
}
