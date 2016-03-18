package script.library;

import script.*;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

public class skill extends script.base_script
{
    public skill()
    {
    }
    public static final String DELIM_RANGE = "..";
    public static final String SCRIPTVAR_SKILLS = "trainer.skills";
    public static final String SCRIPTVAR_JEDI_SKILLS = "trainer.jedi_skills";
    public static final String DATATABLE_SKILL_TEMPLATE = "datatables/skill_template/skill_template.iff";
    public static final String DATATABLE_RACIAL_STATS = "datatables/skill/racial_stats.iff";
    public static final String DATATABLE_EXPERTISE = "datatables/expertise/expertise.iff";
    public static final String DATATABLE_TEMPLATE = "TEMPLATE";
    public static final String DATATABLE_BASE_SKILLS = "BASE_SKILLS";
    public static final String DATATABLE_INHERITS_LIST = "INHERITS_TEMPLATES";
    public static final String DATATABLE_RECURSE = "RECURSE";
    public static final String PLAYER_BASE = "player_base";
    public static final String PLAYER_BETA = "player_beta";
    public static final String DICT_CODE = "code";
    public static final String DICT_DELTA = "delta";
    public static final String DICT_DELTA_SCALE = "deltaScale";
    public static final String DICT_DELTA_PERCENT = "deltaPercent";
    public static final int CODE_FAIL = 0;
    public static final int CODE_PASS = 1;
    public static final int CODE_ERROR = -1;
    public static final int CODE_INSUFFICIENT_SKILL = -2;
    public static final int PHASE_ONE = 1;
    public static final int PHASE_TWO = 2;
    public static final int PHASE_THREE = 3;
    public static final int PHASE_FOUR = 4;
    public static final int HEALTH_POINTS_PER_STAMINA = 2;
    public static final int HEALTH_POINTS_PER_CONSTITUTION = 8;
    public static final int ACTION_POINTS_PER_STAMINA = 8;
    public static final int ACTION_POINTS_PER_CONSTITUTION = 2;
    public static final int NUM_STATS = 6;
    public static final String[] WEAPON_TYPES = 
    {
        "unarmed",
        "polearm",
        "sword1h",
        "sword2h",
        "rifle",
        "carbine",
        "pistol"
    };
    public static final String[] MOD_TYPES = 
    {
        "accuracy",
        "speed",
        "damage"
    };
    public static final String DICT_SKILLNAME = "skillName";
    public static final String HANDLER_SKILL_GRANTED = "skillGranted";
    public static final String JEDI_SKILL_REQUIREMENTS_DATATABLE = "datatables/jedi/jedi_skill_requirements.iff";
    public static final String DEFAULT_SKILL_GRANT_SOUND = "sound/music_acq_bountyhunter.snd";
    public static final string_id PROSE_NSF_SKILL_PTS = new string_id("base_player", "prose_nsf_skill_pts");
    public static final string_id PROSE_BAD_SPECIES = new string_id("base_player", "prose_bad_species");
    public static final String TBL_SKILL = "datatables/skill/skills.iff";
    public static final String SKILL_N = "skl_n";
    public static final String CONVOFILE = "skill_teacher";
    public static final string_id PROSE_SKILL_LEARNED = new string_id(CONVOFILE, "prose_skill_learned");
    public static final string_id PROSE_TRAIN_FAILED = new string_id(CONVOFILE, "prose_train_failed");
    public static final string_id SID_EXPERTISE_WRONG_PROFESSION = new string_id("spam", "expertise_wrong_profession");
    public static boolean grant(obj_id target, String skillName) throws InterruptedException
    {
        if (grantSkillToPlayer(target, skillName))
        {
            dictionary d = new dictionary();
            d.put(DICT_SKILLNAME, skillName);
            messageTo(target, HANDLER_SKILL_GRANTED, d, 0, true);
            return true;
        }
        return grantSkill(target, skillName);
    }
    public static boolean grantSkillToPlayer(obj_id player, String skillName) throws InterruptedException
    {
        if (!isIdValid(player) || (!isPlayer(player)) || skillName == null || skillName.equals(""))
        {
            return false;
        }
        string_id sid_skillName = new string_id("skl_n", skillName);
        String dtSpeciesReq = dataTableGetString("datatables/skill/skills.iff", skillName, "SPECIES_REQUIRED");
        if (dtSpeciesReq != null && !dtSpeciesReq.equals(""))
        {
            dictionary species = getSkillPrerequisiteSpecies(skillName);
            int speciesId = getSpecies(player);
            String speciesName = utils.getPlayerSpeciesName(speciesId);
            boolean allowSpecies = species.getBoolean(speciesName);
            if (!allowSpecies)
            {
                string_id sid_species = new string_id("species", speciesName);
                prose_package ppBadSpecies = prose.getPackage(PROSE_BAD_SPECIES, sid_species, sid_skillName);
                sendSystemMessageProse(player, ppBadSpecies);
                return false;
            }
        }
        if (grantSkill(player, skillName))
        {
            String soundFile = DEFAULT_SKILL_GRANT_SOUND;
            if (skillName.startsWith("combat_"))
            {
                soundFile = DEFAULT_SKILL_GRANT_SOUND;
            }
            else if (utils.isProfession(player, utils.TRADER))
            {
                soundFile = "sound/music_acq_academic.snd";
            }
            else if (skillName.startsWith("outdoors_"))
            {
                if (skillName.startsWith("outdoors_miner_") || skillName.startsWith("outdoors_farmer_"))
                {
                    soundFile = "sound/music_acq_academic.snd";
                }
                else 
                {
                    soundFile = DEFAULT_SKILL_GRANT_SOUND;
                }
            }
            else if (skillName.startsWith("science_"))
            {
                soundFile = "sound/music_acq_healer.snd";
            }
            else if (skillName.startsWith("social_"))
            {
                soundFile = "sound/music_acq_thespian.snd";
            }
            else if (skillName.startsWith("pilot_neutral"))
            {
                if (space_flags.isSpaceTrack(player, space_flags.PRIVATEER_TATOOINE))
                {
                    soundFile = "sound/music_themequest_acc_criminal.snd";
                }
                else 
                {
                    soundFile = "sound/music_themequest_acc_general.snd";
                }
            }
            else if (skillName.startsWith("pilot_rebel"))
            {
                soundFile = "sound/music_themequest_acc_rebel.snd";
            }
            else if (skillName.startsWith("pilot_imperial"))
            {
                soundFile = "sound/music_themequest_acc_imperial.snd";
            }
            if (soundFile.equals(""))
            {
                playMusic(player, DEFAULT_SKILL_GRANT_SOUND);
            }
            else 
            {
                playMusic(player, soundFile);
            }
            return true;
        }
        return false;
    }
    public static boolean purchaseSkill(obj_id player, String skillName) throws InterruptedException
    {
        if (!isIdValid(player) || (!isPlayer(player)) || skillName == null || skillName.equals(""))
        {
            return false;
        }
        boolean hasSkills = hasRequiredSkillsForSkillPurchase(player, skillName);
        boolean hasXp = hasRequiredXpForSkillPurchase(player, skillName);
        boolean alreadyHasSkill = hasSkill(player, skillName);
        if (hasSkills && hasXp && !alreadyHasSkill)
        {
            boolean skillGrantSuccessful = false;
            if (skillName.startsWith("pilot_"))
            {
                skillGrantSuccessful = noisyGrantSkill(player, skillName);
            }
            else 
            {
                skillGrantSuccessful = grantSkillToPlayer(player, skillName);
            }
            if (skillGrantSuccessful)
            {
                if (deductXpCostForSkillPurchase(player, skillName))
                {
                    dictionary holocronParams = new dictionary();
                    holocronParams.put("eventName", "TrainedSkillBox");
                    messageTo(player, "handleHolocronEvent", holocronParams, 0, false);
                    return true;
                }
                else 
                {
                    revokeSkill(player, skillName);
                    CustomerServiceLog("Skill", "skill.purchaseSkill(): (" + player + ") " + getName(player) + " was unable to pay xp costs and had skill '" + skillName + "' revoked during purchase");
                    return false;
                }
            }
            return false;
        }
        return false;
    }
    public static boolean hasRequiredSkillsForSkillPurchase(obj_id player, String skillName) throws InterruptedException {
        if (!isIdValid(player) || (!isPlayer(player)) || skillName == null || skillName.equals("")) {
            return false;
        }
        String[] pSkills = getSkillListingForPlayer(player);
        String[] skillReqs = getSkillPrerequisiteSkills(skillName);
        return skillReqs == null || pSkills != null && utils.isSubset(pSkills, skillReqs);
    }
    public static boolean hasRequiredXpForSkillPurchase(obj_id player, String skillName) throws InterruptedException
    {
        if (!isIdValid(player) || (!isPlayer(player)) || skillName == null || skillName.equals(""))
        {
            return false;
        }
        dictionary xpReqs = getSkillPrerequisiteExperience(skillName);
        if ((xpReqs == null) || (xpReqs.isEmpty()))
        {
            return true;
        }
        boolean qualifies = true;
        java.util.Enumeration e = xpReqs.keys();
        String xpType;
        while (e.hasMoreElements())
        {
            xpType = (String) (e.nextElement());
            int xpCost = xpReqs.getInt(xpType);
            if (getExperiencePoints(player, xpType) < xpCost)
            {
                qualifies = false;
            }
        }
        return qualifies;
    }
    public static boolean deductXpCostForSkillPurchase(obj_id player, String skillName) throws InterruptedException
    {
        if (!isIdValid(player) || (!isPlayer(player)) || skillName == null || skillName.equals(""))
        {
            return false;
        }
        dictionary xpReqs = getSkillPrerequisiteExperience(skillName);
        if ((xpReqs == null) || (xpReqs.isEmpty()))
        {
            return true;
        }
        boolean qualifies = true;
        java.util.Enumeration e = xpReqs.keys();
        String xpType;
        while (e.hasMoreElements())
        {
            xpType = (String) (e.nextElement());
            int xpCost = xpReqs.getInt(xpType);
            if (xpCost != 0)
            {
                if (getExperiencePoints(player, xpType) < xpCost)
                {
                    qualifies = false;
                }
                else 
                {
                    qualifies &= (grantExperiencePoints(player, xpType, -xpCost) != XP_ERROR);
                }
            }
        }
        return qualifies;
    }
    public static String[] getAllRequiredSkills(String skillName) throws InterruptedException
    {
        if (skillName == null || skillName.equals(""))
        {
            return null;
        }
        String[] reqs = getSkillPrerequisiteSkills(skillName);
        if ((reqs == null) || (reqs.length == 0))
        {
            return null;
        }
        Vector ret = new Vector(Arrays.asList(reqs));
        String[] tmp;
        for (String req : reqs) {
            tmp = getAllRequiredSkills(req);
            if ((tmp != null) && (tmp.length != 0)) {
                for (String aTmp : tmp) {
                    int pos = utils.getElementPositionInArray(ret, aTmp);
                    if (pos == -1) {
                        ret = utils.addElement(ret, aTmp);
                    }
                }
            }
        }
        String[] _ret = new String[0];
        if (ret != null)
        {
            _ret = new String[ret.size()];
            ret.toArray(_ret);
        }
        return _ret;
    }
    public static boolean assignSkillTemplate(obj_id target, String template) throws InterruptedException
    {
        if (!isIdValid(target) || (!isMob(target)) || template == null || template.equals(""))
        {
            return false;
        }
        if (dataTableOpen(DATATABLE_SKILL_TEMPLATE))
        {
            String[] templates = dataTableGetStringColumn(DATATABLE_SKILL_TEMPLATE, DATATABLE_TEMPLATE);
            if ((templates == null) || (templates.length == 0))
            {
                return false;
            }
            String skill_list;
            for (int i = 0; i < templates.length; i++)
            {
                if ((toLower(templates[i])).equals(toLower(template)))
                {
                    skill_list = dataTableGetString(DATATABLE_SKILL_TEMPLATE, i, DATATABLE_BASE_SKILLS);
                    if (skill_list.startsWith("\""))
                    {
                        skill_list = skill_list.substring(1);
                    }
                    if (skill_list.endsWith("\""))
                    {
                        skill_list = skill_list.substring(0, skill_list.length() - 1);
                    }
                    if (!skill_list.equals(""))
                    {
                        boolean recurse = dataTableGetInt(DATATABLE_SKILL_TEMPLATE, i, DATATABLE_RECURSE) == 1;
                        boolean litmus = true;
                        java.util.StringTokenizer skills = new java.util.StringTokenizer(toLower(skill_list), ",");
                        String skillName;
                        String[] reqs;
                        while (skills.hasMoreTokens())
                        {
                            skillName = skills.nextToken();
                            litmus &= grantSkill(target, skillName);
                            if (recurse)
                            {
                                reqs = getAllRequiredSkills(skillName);
                                if ((reqs != null) && (reqs.length != 0)) {
                                    for (String req : reqs) {
                                        litmus &= grantSkill(target, req);
                                    }
                                }
                            }
                        }
                        String inherits_list = dataTableGetString(DATATABLE_SKILL_TEMPLATE, i, DATATABLE_INHERITS_LIST);
                        if ((inherits_list != null) && (!inherits_list.equals("")))
                        {
                            java.util.StringTokenizer inheritTokens = new java.util.StringTokenizer(toLower(inherits_list), ",");
                            String inheritTemplate;
                            while (inheritTokens.hasMoreTokens())
                            {
                                inheritTemplate = inheritTokens.nextToken();
                                if (!inheritTemplate.equals(""))
                                {
                                    litmus &= assignSkillTemplate(target, inheritTemplate);
                                }
                            }
                        }
                        return litmus;
                    }
                }
            }
        }
        return false;
    }
    public static boolean revokeAllSkills(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || (!isMob(target)))
        {
            return false;
        }
        String skills[] = getSkillListingForPlayer(target);
        if ((skills == null) || (skills.length == 0))
        {
            return false;
        }
        for (String skill : skills) {
            revokeSkill(target, skill);
            CustomerServiceLog("Skill", "skill.revokeAllSkills(): (" + target + ") " + getName(target) + " is having all skills revoked!");
        }
        return true;
    }
    public static void revokeAllProfessionSkills(obj_id player) throws InterruptedException
    {
        String[] skillList = getSkillListingForPlayer(player);
        int attempts = skillList.length;
        if ((skillList.length != 0))
        {
            while (skillList.length > 0 && attempts > 0)
            {
                for (String skillName : skillList) {
                    if (!skillName.startsWith("species_") && !skillName.startsWith("social_language_") && !skillName.startsWith("social_politician_") && !skillName.startsWith("utility_") && !skillName.startsWith("common_") && !skillName.startsWith("demo_") && !skillName.startsWith("force_title_") && !skillName.startsWith("force_sensitive_") && !skillName.startsWith("combat_melee_basic") && !skillName.startsWith("pilot_") && !skillName.startsWith("internal_expertise_") && !skillName.startsWith("combat_ranged_weapon_basic") && !skillName.equals("expertise")) {
                        revokeSkillSilent(player, skillName);
                    }
                }
                skillList = getSkillListingForPlayer(player);
                --attempts;
            }
        }
    }
    public static String[] getTeachableSkills(obj_id target, obj_id teacher) throws InterruptedException
    {
        return null;
    }
    public static String[] getQualifiedTeachableSkills(obj_id target, obj_id teacher) throws InterruptedException
    {
        if (!isIdValid(target) || (!isMob(target)) || (isIdNull(teacher)) || (!isMob(teacher)))
        {
            return null;
        }
        String[] teachableSkills = getTeachableSkills(target, teacher);
        if (teachableSkills == null)
        {
            return null;
        }
        Vector qualifiedSkills = new Vector();
        qualifiedSkills.setSize(0);
        dictionary d;
        Object o;
        String xpType;
        Enumeration species_keys;
        String key;
        String trainer_type;
        String branch;
        String skillName;

        for (String teachableSkill : teachableSkills) {
            boolean qualifies = true;
            d = getSkillPrerequisiteExperience(teachableSkill);
            if (d != null && !d.isEmpty()) {
                Enumeration keys = d.keys();
                while (keys.hasMoreElements()) {
                    o = keys.nextElement();
                    if (o instanceof String) {
                        xpType = (String) o;
                        int xpCost = d.getInt(xpType);
                        int playerXP = getExperiencePoints(target, xpType);
                        if (playerXP < xpCost) {
                            qualifies = false;
                        }
                    } else {
                        return null;
                    }
                }
            }
            dictionary species = getSkillPrerequisiteSpecies(teachableSkill);
            assert d != null;
            if (species != null && !d.isEmpty()) {
                species_keys = species.keys();
                while (species_keys.hasMoreElements()) {
                    o = species_keys.nextElement();
                    if (o instanceof String) {
                        key = (String) o;
                        if (species.getBoolean(key)) {
                            qualifies = false;
                        }
                    }
                }
            }
            trainer_type = getStringObjVar(teacher, "trainer");
            if (trainer_type != null && trainer_type.equals("trainer_fs")) {
                if (qualifies) {
                    if (fs_quests.isVillageEligible(target)) {
                        branch = fs_quests.getBranchFromSkill(teachableSkill);
                        if (!fs_quests.hasUnlockedBranch(target, branch)) {
                            qualifies = false;
                        }
                    } else {
                        qualifies = false;
                    }
                }
            }
            if (hasObjVar(target, "newbie.hasSkill") && !hasObjVar(target, "newbie.trained")) {
                skillName = getStringObjVar(target, "newbie.hasSkill");
                if (skillName.equals(teachableSkill)) {
                    qualifies = true;
                }
            }
            if (qualifies) {
                qualifiedSkills = utils.addElement(qualifiedSkills, teachableSkill);
            }
        }
        if ((qualifiedSkills != null) && (qualifiedSkills.size() > 0))
        {
            String[] _qualifiedSkills = new String[qualifiedSkills.size()];
            qualifiedSkills.toArray(_qualifiedSkills);
            return _qualifiedSkills;
        }
        return null;
    }
    public static String[] deltaTeacherSkills(obj_id target, obj_id teacher) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(teacher))
        {
            return null;
        }
        if (isPlayer(teacher))
        {
            return deltaPlayerTeacherSkills(target, teacher);
        }
        String[] targetSkills = getSkillListingForPlayer(target);
        String[] teacherSkills = getTeacherSkills(teacher, target);
        if (targetSkills == null || teacherSkills == null)
        {
            return null;
        }
        Vector delta = new Vector();
        delta.setSize(0);
        for (String teacherSkill : teacherSkills) {
            if (utils.getElementPositionInArray(targetSkills, teacherSkill) == -1) {
                delta = utils.addElement(delta, teacherSkill);
            }
        }
        if ((delta != null) && (delta.size() != 0))
        {
            String[] _delta = new String[delta.size()];
            delta.toArray(_delta);
            return _delta;
        }
        return null;
    }
    public static String[] getTeacherSkills(obj_id teacher, obj_id target) throws InterruptedException
    {
        if (!isIdValid(teacher) || !isIdValid(target))
        {
            return null;
        }
        if (!jedi.isJediTrainerForPlayer(target, teacher))
        {
            return utils.getStringBatchScriptVar(teacher, SCRIPTVAR_SKILLS);
        }
        else 
        {
            return utils.getStringBatchScriptVar(teacher, SCRIPTVAR_JEDI_SKILLS);
        }
    }
    public static String[] deltaPlayerTeacherSkills(obj_id target, obj_id teacher) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(teacher))
        {
            return null;
        }
        if (!isPlayer(teacher))
        {
            return deltaTeacherSkills(target, teacher);
        }
        String[] targetSkills = getSkillListingForPlayer(target);
        String[] teacherSkills = getSkillListingForPlayer(teacher);
        if ((targetSkills == null) || (teacherSkills == null))
        {
            return null;
        }
        Vector delta = new Vector();
        delta.setSize(0);
        for (String teacherSkill : teacherSkills) {
            if (utils.getElementPositionInArray(targetSkills, teacherSkill) == -1) {
                delta = utils.addElement(delta, teacherSkill);
            }
        }
        if ((delta != null) && (delta.size() != 0))
        {
            String[] _delta = new String[delta.size()];
            delta.toArray(_delta);
            return _delta;
        }
        return null;
    }
    public static int check(obj_id target, String skillmod, String scale) throws InterruptedException
    {
        if (!isIdValid(target) || (skillmod.equals("")))
        {
            return -1;
        }
        dictionary params = skillModCheck(target, skillmod, scale);
        int retCode = params.getInt(DICT_CODE);
        if (retCode < 0)
        {
            return -1;
        }

        return params.getInt(DICT_DELTA_PERCENT);
    }
    public static dictionary skillModCheck(obj_id target, String skillmod, String scale) throws InterruptedException
    {
        dictionary ret = new dictionary();
        if (!isIdValid(target) || (skillmod.equals("")))
        {
            ret.put(DICT_CODE, CODE_ERROR);
            return ret;
        }
        int modmin = 1;
        int modmax = 100;
        int scaleIdx = scale.indexOf(DELIM_RANGE);
        if (!scale.equals("")) {
            if (scaleIdx > 0)
			{
				java.util.StringTokenizer rng = new java.util.StringTokenizer(scale, DELIM_RANGE);
				String smin;
				String smax;
				if (rng.countTokens() == 2)
				{
					smin = rng.nextToken();
					smax = rng.nextToken();
				}
				else
				{
					ret.put(DICT_CODE, CODE_ERROR);
					return ret;
				}
				modmin = utils.stringToInt(smin);
				modmax = utils.stringToInt(smax);
			}
			else
			{
				modmax = utils.stringToInt(scale);
			}
        }
        if ((modmin < 0) || (modmax <= 0) || (modmin >= modmax))
        {
            ret.put(DICT_CODE, CODE_ERROR);
            return ret;
        }
        int modval = getSkillStatMod(target, skillmod);
        if (modval < modmin)
        {
            ret.put(DICT_CODE, CODE_INSUFFICIENT_SKILL);
            return ret;
        }
        int deltaScale = modmax - modmin;
        int roll = rand(modmin, modmax);
        int delta = modval - roll;
        int deltaPercent = (int)((delta / deltaScale) * 100);
        if (delta >= 0)
        {
            ret.put(DICT_CODE, CODE_PASS);
        }
        else 
        {
            ret.put(DICT_CODE, CODE_FAIL);
        }
        ret.put(DICT_DELTA, delta);
        ret.put(DICT_DELTA_SCALE, deltaScale);
        ret.put(DICT_DELTA_PERCENT, deltaPercent);
        return ret;
    }
    public static int getGroupLevel(obj_id objPlayer) throws InterruptedException
    {
        obj_id objGroup = getGroupObject(objPlayer);
        if (isIdValid(objGroup))
        {
            return getGroupObjectLevel(objGroup);
        }
        return getLevel(objPlayer);
    }
    public static void checkForJediAbility(obj_id objPlayer, String strSkill, int intDelay) throws InterruptedException
    {
        if (!getEnableNewJediTracking())
        {
            LOG("jedi", "System disabled");
            return;
        }
        if (isJedi(objPlayer))
        {
            return;
        }
        if (hasObjVar(objPlayer, "jedi.timeStamp"))
        {
            removeObjVar(objPlayer, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS);
            return;
        }
        Vector strSkillsNeeded = getResizeableStringArrayObjVar(objPlayer, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS);
        if (strSkillsNeeded == null)
        {
            if (!hasJediSlot(objPlayer))
            {
                if (!hasObjVar(objPlayer, "jedi.timeStamp"))
                {
                    if (!hasObjVar(objPlayer, "jedi.postponeGrant"))
                    {
                        CustomerServiceLog("jedi", "CATASTROPHIC JEDI FAILURE, DATA IS MUNGED ON " + objPlayer);
                    }
                }
            }
            LOG("jedi", "no array");
            return;
        }
        if (strSkillsNeeded.size() == 0)
        {
            LOG("jedi", "Array is 0?");
            return;
        }
        int intElement = utils.getElementPositionInArray(strSkillsNeeded, strSkill);
        LOG("jedi", "checking for " + strSkill + " intIndex is " + intElement);
        if (intElement != -1)
        {
            strSkillsNeeded = utils.removeElementAt(strSkillsNeeded, intElement);
        }
        updateJediSkillRequirements(objPlayer, strSkillsNeeded);
    }
    public static void updateJediSkillRequirements(obj_id player, Vector skillsNeeded) throws InterruptedException
    {
        if (!isIdValid(player) || isJedi(player) || skillsNeeded == null)
        {
            return;
        }
        if (skillsNeeded.size() > 0)
        {
            setObjVar(player, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS, skillsNeeded);
            if (skillsNeeded.size() <= 4)
            {
                CustomerServiceLog("jedi", "CLOSE_PLAYER: " + getFirstName(player) + " (" + player + ") needs " + skillsNeeded.size() + " more skills to activate their FS slot.");
            }
            else if (skillsNeeded.size() <= 6)
            {
                CustomerServiceLog("jedi", getFirstName(player) + " (" + player + ") needs " + skillsNeeded.size() + " more skills to activate their FS slot.");
            }
        }
        else 
        {
            removeObjVar(player, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS);
            setObjVar(player, "jedi.timeStamp", 0);
            messageTo(player, "makeJedi", null, 0.1f, false);
        }
    }
    public static void setJediSkills(obj_id objPlayer) throws InterruptedException
    {
        if (!isJedi(objPlayer) && !hasJediSlot(objPlayer) && !hasObjVar(objPlayer, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS))
        {
            int numRequired = 0;
            String[] strSkills = dataTableGetStringColumn(JEDI_SKILL_REQUIREMENTS_DATATABLE, "strSkillList");
            numRequired = dataTableGetInt(JEDI_SKILL_REQUIREMENTS_DATATABLE, 0, "intNumRequired");
            debugServerConsoleMsg(null, "intNumRequired is " + numRequired);
            if (numRequired > strSkills.length)
            {
                debugServerConsoleMsg(null, "NO JEDI ENABLED, SKILLS REQUIRED GREATER THAN SKILLS IN LIST!");
                return;
            }
            int numAvailable = strSkills.length;
            String[] strSkillsNeeded = new String[numRequired];
            for (int i = 0; i < numRequired; ++i)
            {
                int randomNumber = rand(0, numAvailable - 1);
                strSkillsNeeded[i] = strSkills[randomNumber];
                System.arraycopy(strSkills, randomNumber + 1, strSkills, randomNumber, numAvailable - 1 - randomNumber);
                strSkills[--numAvailable] = null;
            }
            if (strSkillsNeeded.length > 0)
            {
                setObjVar(objPlayer, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS, strSkillsNeeded);
            }
            else 
            {
                CustomerServiceLog("jedi", "Failed to set jedi required skills for %TU", objPlayer);
            }
        }
    }
    public static void fixTerrainNegotiationMods(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "_notskill.mods.slope_move"))
        {
            int mod = getIntObjVar(player, "_notskill.mods.slope_move");
            applySkillStatisticModifier(player, "slope_move", -mod);
        }
    }
    public static boolean noisyGrantSkill(obj_id player, String skillName) throws InterruptedException
    {
        if (grantSkillToPlayer(player, skillName))
        {
            sendSystemMessageProse(player, prose.getPackage(PROSE_SKILL_LEARNED, new string_id(SKILL_N, skillName)));
            return true;
        }
        else 
        {
            sendSystemMessageProse(player, prose.getPackage(PROSE_TRAIN_FAILED, new string_id(SKILL_N, skillName)));
            return false;
        }
    }
    public static void doPlayerLeveling(obj_id objPlayer, int intOldLevel, int intNewLevel) throws InterruptedException
    {
        int levelDelta = intNewLevel - intOldLevel;
        if (levelDelta == 1)
        {
            showFlyText(objPlayer, new string_id("cbt_spam", "level_up"), 2.5f, colors.CORNFLOWERBLUE);
            playClientEffectObj(objPlayer, "clienteffect/level_granted.cef", objPlayer, null);
        }
        newbieTutorialHighlightUIElement(objPlayer, "/GroundHUD.MFDStatus.vsp.role.targetLevel", 5.0f);
        setPlayerStatsForLevel(objPlayer, intNewLevel);
    }
    public static void setPlayerStatsForLevel(obj_id objPlayer, int intLevel) throws InterruptedException
    {
        if (intLevel < 1)
        {
            LOG("skill.scriptlib", "getPlayerStatForLevel BAD level");
            return;
        }
        String strProfession = getProfessionName(getSkillTemplate(objPlayer));
        player_levels.level_data stats = player_levels.getPlayerLevelData(strProfession, intLevel);
        if (stats == null)
        {
            LOG("skill.scriptlib", "setPlayerStatsForLevel stats == null");
            return;
        }
        if ((strProfession != null) && (!strProfession.equals("")))
        {
            int intStat = getPlayerStatForLevel(objPlayer, intLevel, "luck");
            if (intStat >= 0)
            {
                int oldStat = getSkillStatMod(objPlayer, "luck");
                applySkillStatisticModifier(objPlayer, "luck", -oldStat);
                applySkillStatisticModifier(objPlayer, "luck", intStat);
            }
            intStat = getPlayerStatForLevel(objPlayer, intLevel, "precision");
            if (intStat >= 0)
            {
                int oldStat = getSkillStatMod(objPlayer, "precision");
                applySkillStatisticModifier(objPlayer, "precision", -oldStat);
                applySkillStatisticModifier(objPlayer, "precision", intStat);
            }
            intStat = getPlayerStatForLevel(objPlayer, intLevel, "strength");
            if (intStat >= 0)
            {
                int oldStat = getSkillStatMod(objPlayer, "strength");
                applySkillStatisticModifier(objPlayer, "strength", -oldStat);
                applySkillStatisticModifier(objPlayer, "strength", intStat);
            }
            intStat = getPlayerStatForLevel(objPlayer, intLevel, "constitution");
            if (intStat >= 0)
            {
                int oldStat = getSkillStatMod(objPlayer, "constitution");
                applySkillStatisticModifier(objPlayer, "constitution", -oldStat);
                applySkillStatisticModifier(objPlayer, "constitution", intStat);
            }
            intStat = getPlayerStatForLevel(objPlayer, intLevel, "stamina");
            if (intStat >= 0)
            {
                int oldStat = getSkillStatMod(objPlayer, "stamina");
                applySkillStatisticModifier(objPlayer, "stamina", -oldStat);
                applySkillStatisticModifier(objPlayer, "stamina", intStat);
            }
            intStat = getPlayerStatForLevel(objPlayer, intLevel, "agility");
            if (intStat >= 0)
            {
                int oldStat = getSkillStatMod(objPlayer, "agility");
                applySkillStatisticModifier(objPlayer, "agility", -oldStat);
                applySkillStatisticModifier(objPlayer, "agility", intStat);
            }
            intStat = getPlayerStatForLevel(objPlayer, intLevel, "health");
            if (intStat >= 0)
            {
                setMaxAttrib(objPlayer, HEALTH, intStat);
            }
            float fltStat = stats.health_regen;
            if (fltStat >= 0)
            {
                setRegenRate(objPlayer, HEALTH, fltStat);
            }
            intStat = getPlayerStatForLevel(objPlayer, intLevel, "action");
            if (intStat >= 0)
            {
                setMaxAttrib(objPlayer, ACTION, intStat);
            }
            fltStat = stats.action_regen;
            if (fltStat >= 0)
            {
                setRegenRate(objPlayer, ACTION, fltStat);
            }
            recalcPlayerPools(objPlayer, true);
        }
    }
    public static int getPlayerStatForLevel(obj_id player, int intLevel, String statString) throws InterruptedException
    {
        int intStat = 0;
        if (intLevel < 1 || intLevel > 90)
        {
            LOG("skill.scriptlib", "getPlayerStatForLevel BAD level");
            return 0;
        }
        int playerRace = getSpecies(player);
        String strProfession = getProfessionName(getSkillTemplate(player));
        player_levels.level_data stats = player_levels.getPlayerLevelData(strProfession, intLevel);
        if (stats == null)
        {
            LOG("skill.scriptlib", "setPlayerStatsForLevel stats == null");
            return 0;
        }
        if (playerRace > 7)
        {
            if (playerRace == 33)
            {
                playerRace = 8;
            }
            else if (playerRace == 49)
            {
                playerRace = 9;
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "Unknown race, defaulting to human");
                LOG("npe", "library.skill - getPlayerStatForLevel invalid race, using human defaults");
                playerRace = 0;
            }
        }
        int racialStatColumnReference = (playerRace * NUM_STATS);
        if (statString.equals("luck"))
        {
            intStat = stats.luck;
            intStat += dataTableGetInt(DATATABLE_RACIAL_STATS, intLevel - 1, racialStatColumnReference + 1);
            return intStat;
        }
        if (statString.equals("precision"))
        {
            intStat = stats.precision;
            intStat += dataTableGetInt(DATATABLE_RACIAL_STATS, intLevel - 1, racialStatColumnReference + 2);
            return intStat;
        }
        if (statString.equals("strength"))
        {
            intStat = stats.strength;
            intStat += dataTableGetInt(DATATABLE_RACIAL_STATS, intLevel - 1, racialStatColumnReference + 3);
            return intStat;
        }
        if (statString.equals("constitution"))
        {
            intStat = stats.constitution;
            intStat += dataTableGetInt(DATATABLE_RACIAL_STATS, intLevel - 1, racialStatColumnReference + 4);
            return intStat;
        }
        if (statString.equals("stamina"))
        {
            intStat = stats.stamina;
            intStat += dataTableGetInt(DATATABLE_RACIAL_STATS, intLevel - 1, racialStatColumnReference + 5);
            return intStat;
        }
        if (statString.equals("agility"))
        {
            intStat = stats.agility;
            intStat += dataTableGetInt(DATATABLE_RACIAL_STATS, intLevel - 1, racialStatColumnReference + 6);
            return intStat;
        }
        if (statString.equals("health"))
        {
            intStat = stats.health;
            return intStat;
        }
        if (statString.equals("action"))
        {
            intStat = stats.action;
            return intStat;
        }
        return intStat;
    }
    public static void sendlevelUpStatChangeSystemMessages(obj_id player, int oldCombatLevel, int newCombatLevel) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return;
        }
        if (newCombatLevel < 1 || newCombatLevel > 90)
        {
            return;
        }
        int levelDelta = newCombatLevel - oldCombatLevel;
        if (levelDelta != 1)
        {
            return;
        }
        String[] statStrings = 
        {
            "luck",
            "precision",
            "strength",
            "constitution",
            "stamina",
            "agility"
        };
        for (int i = 0; i < statStrings.length; i++)
        {
            int currentStat = getPlayerStatForLevel(player, newCombatLevel, statStrings[i]);
            int lastStat = getPlayerStatForLevel(player, oldCombatLevel, statStrings[i]);
            int statDelta = currentStat - lastStat;
            if (statDelta > 0)
            {
                prose_package ppStatGainSpam = new prose_package();
                ppStatGainSpam.stringId = new string_id("spam", "level_up_stat_gain_" + i);
                ppStatGainSpam.digitInteger = statDelta;
                sendSystemMessageProse(player, ppStatGainSpam);
            }
        }
        int currentCon = getPlayerStatForLevel(player, newCombatLevel, "constitution");
        int lastCon = getPlayerStatForLevel(player, oldCombatLevel, "constitution");
        int currentSta = getPlayerStatForLevel(player, newCombatLevel, "stamina");
        int lastSta = getPlayerStatForLevel(player, oldCombatLevel, "stamina");
        int currentHealth = getPlayerStatForLevel(player, newCombatLevel, "health");
        int lastHealth = getPlayerStatForLevel(player, oldCombatLevel, "health");
        int currentAction = getPlayerStatForLevel(player, newCombatLevel, "action");
        int lastAction = getPlayerStatForLevel(player, oldCombatLevel, "action");
        int forRealsHealthCurrent = currentHealth + (currentCon * HEALTH_POINTS_PER_CONSTITUTION) + (currentSta * HEALTH_POINTS_PER_STAMINA);
        int forRealsHealthLast = lastHealth + (lastCon * HEALTH_POINTS_PER_CONSTITUTION) + (lastSta * HEALTH_POINTS_PER_STAMINA);
        int forRealsActionCurrent = currentAction + (currentCon * ACTION_POINTS_PER_CONSTITUTION) + (currentSta * ACTION_POINTS_PER_STAMINA);
        int forRealsActionLast = lastAction + (lastCon * ACTION_POINTS_PER_CONSTITUTION) + (lastSta * ACTION_POINTS_PER_STAMINA);
        int healthDelta = forRealsHealthCurrent - forRealsHealthLast;
        int actionDelta = forRealsActionCurrent - forRealsActionLast;
        if (healthDelta > 0)
        {
            prose_package ppStatGainSpam = new prose_package();
            ppStatGainSpam.stringId = new string_id("spam", "level_up_stat_gain_6");
            ppStatGainSpam.digitInteger = healthDelta;
            sendSystemMessageProse(player, ppStatGainSpam);
        }
        if (actionDelta > 0)
        {
            prose_package ppStatGainSpam = new prose_package();
            ppStatGainSpam.stringId = new string_id("spam", "level_up_stat_gain_7");
            ppStatGainSpam.digitInteger = actionDelta;
            sendSystemMessageProse(player, ppStatGainSpam);
        }
    }
    public static String getProfessionName(String strTemplate) throws InterruptedException
    {
        player_levels.skill_template_data professionData = player_levels.getSkillTemplateData(strTemplate);
        if (professionData == null)
        {
            return null;
        }
        return professionData.strClassName;
    }
    public static void recalcPlayerPools(obj_id objPlayer, boolean boolHealEverything) throws InterruptedException
    {
        if (!isPlayer(objPlayer))
        {
            return;
        }
        int intLevel = getLevel(objPlayer);
        if (intLevel < 0)
        {
            intLevel = 1;
        }
        else 
        {
            if (intLevel > 90)
            {
                intLevel = 90;
            }
        }
        String strProfession = getProfessionName(getSkillTemplate(objPlayer));
        if (strProfession != null && strProfession.length() > 0)
        {
            player_levels.level_data stats = player_levels.getPlayerLevelData(strProfession, intLevel);
            if (stats == null)
            {
                LOG("skill.scriptlib", "recalcPlayerPools stats == null");
                return;
            }
            int intBaseHealth = stats.health;
            int intBaseAction = stats.action;
            int intConstitution = getSkillStatisticModifier(objPlayer, "constitution");
            int intStamina = getSkillStatisticModifier(objPlayer, "stamina");
            intConstitution += getEnhancedSkillStatisticModifierUncapped(objPlayer, "constitution_modified");
            intStamina += getEnhancedSkillStatisticModifierUncapped(objPlayer, "stamina_modified");
            intBaseHealth = intBaseHealth + (HEALTH_POINTS_PER_CONSTITUTION * intConstitution);
            intBaseHealth = intBaseHealth + (HEALTH_POINTS_PER_STAMINA * intStamina);
            intBaseAction = intBaseAction + (ACTION_POINTS_PER_STAMINA * intStamina);
            intBaseAction = intBaseAction + (ACTION_POINTS_PER_CONSTITUTION * intConstitution);
            setMaxAttrib(objPlayer, ACTION, intBaseAction);
            setMaxAttrib(objPlayer, HEALTH, intBaseHealth);
            int[] myBuffs = buff.getAllBuffs(objPlayer);
            String thisBuffEffect;
            for (int myBuff : myBuffs) {
                thisBuffEffect = buff.getEffectParam(myBuff, 1);
                float thisBuffValue = buff.getEffectValue(myBuff, 1);
                if (thisBuffEffect != null && thisBuffValue < 0 && thisBuffEffect.equals("healthPercent")) {
                    boolHealEverything = false;
                    break;
                }
            }
            if (boolHealEverything)
            {
                setAttrib(objPlayer, ACTION, getMaxAttrib(objPlayer, ACTION));
                setAttrib(objPlayer, HEALTH, getMaxAttrib(objPlayer, HEALTH));
            }
        }
    }
    public static void grantAllPoliticianSkills(obj_id player) throws InterruptedException
    {
        String[] skillNames = 
        {
            "social_politician_novice",
            "social_politician_fiscal_01",
            "social_politician_fiscal_02",
            "social_politician_fiscal_03",
            "social_politician_fiscal_04",
            "social_politician_martial_01",
            "social_politician_martial_02",
            "social_politician_martial_03",
            "social_politician_martial_04",
            "social_politician_civic_01",
            "social_politician_civic_02",
            "social_politician_civic_03",
            "social_politician_civic_04",
            "social_politician_urban_01",
            "social_politician_urban_02",
            "social_politician_urban_03",
            "social_politician_urban_04",
            "social_politician_master"
        };
        setObjVar(player, "clickRespec.granting", true);
        for (String skillName : skillNames) {
            if (!hasSkill(player, skillName)) {
                grantSkill(player, skillName);
            }
        }
        removeObjVar(player, "clickRespec.granting");
    }
    public static int getProfessionPhase(obj_id player) throws InterruptedException
    {
        String[] phaseFourSkills = 
        {
            "class_forcesensitive_phase4_novice",
            "class_bountyhunter_phase4_novice",
            "class_smuggler_phase4_novice",
            "class_commando_phase4_novice",
            "class_officer_phase4_novice",
            "class_spy_phase4_novice",
            "class_medic_phase4_novice",
            "class_entertainer_phase4_novice",
            "class_domestics_phase4_novice",
            "class_structures_phase4_novice",
            "class_munitions_phase4_novice",
            "class_engineering_phase4_novice"
        };
        String[] phaseThreeSkills = 
        {
            "class_forcesensitive_phase3_novice",
            "class_bountyhunter_phase3_novice",
            "class_smuggler_phase3_novice",
            "class_commando_phase3_novice",
            "class_officer_phase3_novice",
            "class_spy_phase3_novice",
            "class_medic_phase3_novice",
            "class_entertainer_phase3_novice",
            "class_domestics_phase3_novice",
            "class_structures_phase3_novice",
            "class_munitions_phase3_novice",
            "class_engineering_phase3_novice"
        };
        String[] phaseTwoSkills = 
        {
            "class_forcesensitive_phase2_novice",
            "class_bountyhunter_phase2_novice",
            "class_smuggler_phase2_novice",
            "class_commando_phase2_novice",
            "class_officer_phase2_novice",
            "class_spy_phase2_novice",
            "class_medic_phase2_novice",
            "class_entertainer_phase2_novice",
            "class_domestics_phase2_novice",
            "class_structures_phase2_novice",
            "class_munitions_phase2_novice",
            "class_engineering_phase2_novice"
        };
        for (String phaseFourSkill : phaseFourSkills) {
            if (hasSkill(player, phaseFourSkill)) {
                return PHASE_FOUR;
            }
        }
        for (String phaseThreeSkill : phaseThreeSkills) {
            if (hasSkill(player, phaseThreeSkill)) {
                return PHASE_THREE;
            }
        }
        for (String phaseTwoSkill : phaseTwoSkills) {
            if (hasSkill(player, phaseTwoSkill)) {
                return PHASE_TWO;
            }
        }
        return PHASE_ONE;
    }
    public static boolean validateExpertise(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        String[] expertiseSkills = expertise.getExpertiseAllocation(player);
        if (expertiseSkills == null || expertiseSkills.length <= 0)
        {
            return false;
        }
        String skillTemplate = getSkillTemplate(player);
        String profession = getProfessionName(skillTemplate);
        boolean isTrader = false;
        if (profession.equals("trader"))
        {
            isTrader = true;
            if (skillTemplate.equals("trader_0a"))
            {
                profession = "trader_dom";
            }
            if (skillTemplate.equals("trader_0b"))
            {
                profession = "trader_struct";
            }
            if (skillTemplate.equals("trader_0c"))
            {
                profession = "trader_mun";
            }
            if (skillTemplate.equals("trader_0d"))
            {
                profession = "trader_eng";
            }
        }
        for (String expertiseSkill : expertiseSkills) {
            int row = dataTableSearchColumnForString(expertiseSkill, "NAME", DATATABLE_EXPERTISE);
            if (row < 0) {
                continue;
            }
            String reqProf = dataTableGetString(DATATABLE_EXPERTISE, row, "REQ_PROF");
            if (!reqProf.equals(profession)) {
                if ((!reqProf.equals("trader") || !isTrader) && !reqProf.equals("all")) {
                    sendSystemMessage(player, SID_EXPERTISE_WRONG_PROFESSION);
                    utils.fullExpertiseReset(player, false);
                    CustomerServiceLog("SuspectedCheaterChannel: ", "DualProfessionCheat: Player " + getFirstName(player) + "(" + player + ") has an expertise that is not for thier profession. All expertises have been revoked.");
                    CustomerServiceLog("SuspectedCheaterChannel: ", "DualProfessionCheat: Player " + getFirstName(player) + "(" + player + ")'s profession is " + profession + " and the skill they have is " + expertiseSkill + ".");
                    return false;
                }
            }
        }
        return true;
    }
}
