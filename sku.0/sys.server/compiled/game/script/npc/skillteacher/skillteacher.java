package script.npc.skillteacher;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.skill;
import script.library.utils;
import script.library.city;
import script.library.jedi;
import script.library.chat;
import script.library.prose;
import script.library.anims;
import script.library.sui;
import script.library.money;
import script.library.xp;
import script.library.fs_quests;
import script.library.features;

public class skillteacher extends script.base_script
{
    public skillteacher()
    {
    }
    public static final String VAR_SKILL_TEMPLATE = "npcSkillTemplate";
    public static final String CONVONAME = "skill_teacher";
    public static final String CONVOFILE = "skill_teacher";
    public static final String JEDI_TRAINER = "jedi_trainer";
    public static final String JEDI_TRAINER_LIGHT = "jedi_trainer_light";
    public static final String JEDI_TRAINER_DARK = "jedi_trainer_dark";
    public static final String SKILL_N = "skl_n";
    public static final String SKILL_D = "skl_d";
    public static final String SKILL_T = "skl_t";
    public static final String SCRIPT_NPC_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String FACETO_VOLUME_NAME = "faceToTriggerVolume";
    public static final string_id[] OPT_DEFAULT = 
    {
        new string_id(CONVOFILE, "opt1_1"),
        new string_id(CONVOFILE, "opt1_2")
    };
    public static final string_id[] OPT_YES_BACK = 
    {
        new string_id(CONVOFILE, "yes"),
        new string_id(CONVOFILE, "back")
    };
    public static final string_id[] OPT_YES_NO = 
    {
        new string_id(CONVOFILE, "yes"),
        new string_id(CONVOFILE, "no")
    };
    public static final String TBL = "datatables/skill/skills.iff";
    public static final string_id PROSE_NSF = new string_id(CONVOFILE, "prose_nsf");
    public static final string_id PROSE_PAY = new string_id(CONVOFILE, "prose_pay");
    public static final string_id PROSE_SKILL_LEARNED = new string_id(CONVOFILE, "prose_skill_learned");
    public static final string_id PROSE_TRAIN_FAILED = new string_id(CONVOFILE, "prose_train_failed");
    public static final string_id SID_TRAINING_COST_REFUNDED = new string_id(CONVOFILE, "training_cost_refunded");
    public static final string_id SID_ALREADY_HAVE_THIS_SKILL = new string_id(CONVOFILE, "already_have_this_skill");
    public static final string_id SID_DO_NOT_HAVE_SKILL = new string_id(CONVOFILE, "do_not_have_skill");
    public static final int STATUS_UNKNOWN = -1;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_LEARN = 1;
    public static final int STATUS_INFO = 2;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String teacherType = getStringObjVar(self, "trainer");
        if (teacherType != null)
        {
            if (teacherType.equals("trainer_shipwright"))
            {
                setCondition(self, CONDITION_CONVERSABLE);
                setCondition(self, CONDITION_SPACE_INTERESTING);
            }
            else 
            {
                setCondition(self, CONDITION_CONVERSABLE);
            }
        }
        createTriggerVolume(FACETO_VOLUME_NAME, 8.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        clearCondition(self, CONDITION_SPACE_INTERESTING);
        detachScript(self, "npc.skillteacher.skillteacher");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, skill.SCRIPTVAR_SKILLS))
        {
            String tbl_trainer_skills = "datatables/npc_customization/skill_table.iff";
            String teacherType = getStringObjVar(self, "trainer");
            String[] skillList = dataTableGetStringColumnNoDefaults(tbl_trainer_skills, teacherType);
            if (teacherType != null && (!teacherType.equals("") && teacherType.equals("trainer_creaturehandler")))
            {
                if (!hasScript(self, "systems.pet_tradein.pet_tradein"))
                {
                    attachScript(self, "systems.pet_tradein.pet_tradein");
                }
            }
            if (skillList == null || skillList.length == 0)
            {
                detachScript(self, "npc.skillteacher.skillteacher");
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                if (teacherType != null)
                {
                    if (teacherType.equals("trainer_shipwright"))
                    {
                        setCondition(self, CONDITION_SPACE_INTERESTING);
                        setCondition(self, CONDITION_CONVERSABLE);
                    }
                    else 
                    {
                        setCondition(self, CONDITION_CONVERSABLE);
                    }
                }
                utils.setBatchScriptVar(self, skill.SCRIPTVAR_SKILLS, skillList);
            }
            skillList = dataTableGetStringColumnNoDefaults(tbl_trainer_skills, "trainer_jedi");
            if (skillList != null && skillList.length > 0)
            {
                utils.setBatchScriptVar(self, skill.SCRIPTVAR_JEDI_SKILLS, skillList);
            }
        }
        createTriggerVolume(FACETO_VOLUME_NAME, 8.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (hasObjVar(speaker, "jedi.usingSui"))
        {
            string_id strSpam = new string_id("jedi_spam", "cant_train_while_converting");
            chat.chat(self, speaker, strSpam, chat.ChatFlag_targetOnly);
            return SCRIPT_CONTINUE;
        }
        int city_id = getCityAtLocation(getLocation(self), 0);
        if (cityExists(city_id) && city.isCityBanned(speaker, city_id))
        {
            sendSystemMessage(speaker, new string_id("city/city", "city_banned"));
            return SCRIPT_CONTINUE;
        }
        String trainerType = "trainer_unknown";
        if (hasObjVar(self, "trainer"))
        {
            trainerType = getStringObjVar(self, "trainer");
        }
        if (trainerType.equals("trainer_shipwright") && !features.isSpaceEdition(speaker))
        {
            doAnimationAction(self, "thumbs_down");
            sendSystemMessage(speaker, new string_id("skill_teacher", "requires_jtl"));
            chat.publicChat(self, speaker, new string_id("skill_teacher", "too_complicated"));
            return SCRIPT_CONTINUE;
        }
        faceTo(self, speaker);
        if (!checkSkillStatus(self, speaker))
        {
            return SCRIPT_CONTINUE;
        }
        if (jedi.isJediTrainerForPlayer(speaker, self))
        {
            if (hasObjVar(speaker, "jedi.intFindNewTrainer"))
            {
                messageTo(speaker, "findNewTrainer", null, 0, false);
                string_id strSpam = new string_id("jedi_spam", "not_your_trainer");
                chat.chat(self, speaker, strSpam, chat.ChatFlag_targetOnly);
                return SCRIPT_CONTINUE;
            }
        }
        string_id msg = new string_id(CONVOFILE, trainerType);
        if (isJedi(speaker) && jedi.isJediTrainerForPlayer(speaker, self))
        {
            String jedi_convo = getJediConvoFile(speaker);
            if (jedi_convo != null && !jedi_convo.equals(""))
            {
                msg = new string_id(jedi_convo, "greeting");
            }
            else 
            {
                msg = new string_id(JEDI_TRAINER, "greeting");
            }
        }
        npcStartConversation(speaker, self, CONVONAME, msg, OPT_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int OnEndNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        utils.removeScriptVar(speaker, self.toString());
        removeObjVar(self, "confirmTeach." + speaker);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id speaker, string_id sid_response) throws InterruptedException
    {
        String trainerType = getStringObjVar(self, "trainer");
        if (trainerType != null)
        {
            if (trainerType.equals("trainer_shipwright") && !features.isSpaceEdition(speaker))
            {
                doAnimationAction(self, "thumbs_down");
                sendSystemMessage(speaker, new string_id("skill_teacher", "requires_jtl"));
                chat.publicChat(self, speaker, new string_id("skill_teacher", "too_complicated"));
                return SCRIPT_CONTINUE;
            }
        }
        if (!convoName.equals(CONVONAME) && !convoName.equals(JEDI_TRAINER))
        {
            return SCRIPT_CONTINUE;
        }
        if (!checkSkillStatus(self, speaker))
        {
            return SCRIPT_CONTINUE;
        }
        String tbl = sid_response.getTable();
        String response = sid_response.getAsciiId();
        int status = utils.getIntScriptVar(speaker, self.toString());
        boolean checkArray = true;
        String convo = CONVOFILE;
        if (isJedi(speaker) && jedi.isJediTrainerForPlayer(speaker, self))
        {
            String jedi_convo = getJediConvoFile(speaker);
            if (jedi_convo != null && !jedi_convo.equals(""))
            {
                convo = jedi_convo;
            }
        }
        if (tbl.equals(SKILL_N))
        {
            string_id msg = new string_id(CONVOFILE, "msg3_1");
            boolean skillGranted = false;
            switch (status)
            {
                case STATUS_LEARN:
                String[] qualifiedSkills = skill.getQualifiedTeachableSkills(speaker, self);
                if (qualifiedSkills == null)
                {
                }
                else 
                {
                    if (utils.getElementPositionInArray(qualifiedSkills, response) > -1)
                    {
                        string_id sid_skillName = new string_id(SKILL_N, response);
                        int cost = 1;
                        float skillMod = getEnhancedSkillStatisticModifier(speaker, "force_persuade");
                        skillMod = skillMod * .01f;
                        float discount = cost * skillMod;
                        cost = cost - (int)discount;
                        boolean newbieTraining = hasObjVar(speaker, "newbie.hasSkill");
                        if (hasObjVar(speaker, "newbie.trained"))
                        {
                            newbieTraining = false;
                        }
                        if (cost > 0 && (!newbieTraining))
                        {
                            int totalMoney = getTotalMoney(speaker);
                            if (totalMoney < cost)
                            {
                                prose_package pp = prose.getPackage(PROSE_NSF, sid_skillName, cost);
                                sendSystemMessageProse(speaker, pp);
                            }
                            else 
                            {
                                int ptsLeft = 0;
                                int ptsCost = 1;
                                if (ptsLeft < ptsCost)
                                {
                                    int diff = ptsCost - ptsLeft;
                                    string_id PROSE_NSF_SKILL_PTS = new string_id(convo, "nsf_skill_pts");
                                    prose_package ppNsfSkillPts = prose.getPackage(PROSE_NSF_SKILL_PTS, sid_skillName, diff);
                                    npcSpeak(speaker, ppNsfSkillPts);
                                    npcSetConversationResponses(speaker, OPT_DEFAULT);
                                    return SCRIPT_CONTINUE;
                                }
                                String ovPath = "confirmTeach." + speaker;
                                setObjVar(self, ovPath + ".sid_skillname", sid_skillName);
                                setObjVar(self, ovPath + ".cost", cost);
                                string_id PROSE_COST = new string_id(convo, "prose_cost");
                                prose_package ppConfirm = prose.getPackage(PROSE_COST, sid_skillName, cost);
                                npcSpeak(speaker, ppConfirm);
                                npcSetConversationResponses(speaker, OPT_YES_NO);
                                return SCRIPT_CONTINUE;
                            }
                            npcSpeak(speaker, new string_id(convo, "msg1_1"));
                            npcSetConversationResponses(speaker, OPT_DEFAULT);
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            int ptsLeft = 0;
                            int ptsCost = 1;
                            if (ptsLeft < ptsCost)
                            {
                                int diff = ptsCost - ptsLeft;
                                string_id PROSE_NSF_SKILL_PTS = new string_id(convo, "nsf_skill_pts");
                                prose_package ppNsfSkillPts = prose.getPackage(PROSE_NSF_SKILL_PTS, sid_skillName, diff);
                                npcSpeak(speaker, ppNsfSkillPts);
                                npcSetConversationResponses(speaker, OPT_DEFAULT);
                                return SCRIPT_CONTINUE;
                            }
                            if (completeSkillPurchase(speaker, response))
                            {
                                if (response.equals("jedi_light_side_journeyman_novice") || response.equals("jedi_dark_side_journeyman_novice"))
                                {
                                    npcSpeak(speaker, new string_id(JEDI_TRAINER, "chosen_path"));
                                    npcEndConversation(speaker);
                                    setObjVar(speaker, "jedi.intFindNewTrainer", 1);
                                    return SCRIPT_CONTINUE;
                                }
                                msg = new string_id(convo, "msg3_2");
                                doAnimationAction(self, anims.PLAYER_FC_WINK);
                                if (response.equals("combat_bountyhunter_novice"))
                                {
                                    dictionary dctParams = new dictionary();
                                    dctParams.put("eventName", "BountyHunterNoviceGranted");
                                    messageTo(speaker, "handleHolocronEvent", dctParams, 0, false);
                                }
                                skillGranted = true;
                                if (hasObjVar(speaker, "newbie.hasSkill"))
                                {
                                    setObjVar(speaker, "newbie.trained", true);
                                }
                                if (hasSurpassedTrainer(self, speaker))
                                {
                                    prose_package ppFarewell = prose.getPackage(new string_id(convo, "surpass_trainer"), speaker);
                                    String chatType = chat.getChatType(self);
                                    String moodType = chat.getChatMood(self);
                                    npcSpeak(speaker, ppFarewell);
                                    npcEndConversation(speaker);
                                    return SCRIPT_CONTINUE;
                                }
                            }
                            else 
                            {
                                msg = new string_id(convo, "error_grant_skill");
                                doAnimationAction(self, anims.PLAYER_SHRUG_HANDS);
                            }
                        }
                    }
                }
                break;
                case STATUS_INFO:
                msg = new string_id(CONVOFILE, "msg3_3");
                String[] skillData = getSkillData(response, speaker);
                if ((skillData != null) && (skillData.length > 0))
                {
                    if (utils.getElementPositionInArray(getSkillListingForPlayer(speaker), response) > -1)
                    {
                        sui.listbox(speaker, utils.packStringId(SID_ALREADY_HAVE_THIS_SKILL), "@" + tbl + ":" + response, skillData);
                    }
                    else 
                    {
                        prose_package ppDoNotHaveSkill = prose.getPackage(SID_DO_NOT_HAVE_SKILL);
                        prose.setTO(ppDoNotHaveSkill, new string_id("skl_d", response));
                        String prompt = " \0" + packOutOfBandProsePackage(null, ppDoNotHaveSkill);
                        sui.listbox(speaker, prompt, "@" + tbl + ":" + response, skillData);
                    }
                }
                break;
                default:
                break;
            }
            npcSpeak(speaker, msg);
            npcSetConversationResponses(speaker, OPT_DEFAULT);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            Vector opt = utils.concatArrays(null, OPT_DEFAULT);
            String[] skills = null;
            string_id msg = new string_id(convo, "msg2_1");
            if (response.equals("opt1_1"))
            {
                msg = new string_id(convo, "msg2_1");
                skills = skill.getQualifiedTeachableSkills(speaker, self);
                utils.setScriptVar(speaker, self.toString(), STATUS_LEARN);
            }
            else if (response.equals("opt1_2"))
            {
                msg = new string_id(convoName, "msg2_2");
                skills = skill.getTeachableSkills(speaker, self);
                utils.setScriptVar(speaker, self.toString(), STATUS_INFO);
            }
            else if (response.equals("yes"))
            {
                String ovPath = "confirmTeach." + speaker;
                if (hasObjVar(self, ovPath))
                {
                    string_id sid_skillName = getStringIdObjVar(self, ovPath + ".sid_skillname");
                    int cost = getIntObjVar(self, ovPath + ".cost");
                    if (sid_skillName != null && cost > 0)
                    {
                        prose_package pp = prose.getPackage(PROSE_PAY, sid_skillName, cost);
                        sendSystemMessageProse(speaker, pp);
                        dictionary d = new dictionary();
                        d.put("skillName", sid_skillName.getAsciiId());
                        money.requestPayment(speaker, self, cost, "attemptedPayment", d, true);
                    }
                    removeObjVar(self, ovPath);
                }
                msg = new string_id(convo, "msg_yes");
                checkArray = false;
                utils.removeScriptVar(speaker, self.toString());
            }
            else if (response.equals("no"))
            {
                msg = new string_id(convo, "msg_no");
                checkArray = false;
                utils.removeScriptVar(speaker, self.toString());
            }
            else 
            {
                checkArray = false;
                utils.removeScriptVar(speaker, self.toString());
            }
            if ((checkArray) && ((skills == null) || (skills.length == 0)))
            {
                msg = new string_id(convo, "error_empty_category");
            }
            else if (!checkArray)
            {
            }
            else 
            {
                opt.clear();
                for (int i = 0; i < skills.length; i++)
                {
                    opt = utils.addElement(opt, new string_id(SKILL_N, skills[i]));
                }
                opt = utils.addElement(opt, new string_id(CONVOFILE, "back"));
            }
            npcSpeak(speaker, msg);
            npcSetConversationResponses(speaker, opt);
            return SCRIPT_CONTINUE;
        }
    }
    public int attemptedPayment(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int retCode = money.getReturnCode(params);
        if (retCode != money.RET_SUCCESS)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player) || (!isPlayer(player)))
        {
            return SCRIPT_CONTINUE;
        }
        String skillName = params.getString("skillName");
        if ((skillName == null) || (skillName.equals("")))
        {
            return SCRIPT_CONTINUE;
        }
        int cost = params.getInt(money.DICT_TOTAL);
        if (completeSkillPurchase(player, skillName))
        {
            money.bankTo(self, money.ACCT_SKILL_TRAINING, cost);
        }
        else 
        {
            prose_package ppCostRefunded = prose.getPackage(SID_TRAINING_COST_REFUNDED);
            prose.setDI(ppCostRefunded, cost);
            sendSystemMessageProse(player, ppCostRefunded);
            money.bankTo(self, player, cost);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean completeSkillPurchase(obj_id player, String skillName) throws InterruptedException
    {
        if (!isIdValid(player) || (!isPlayer(player)))
        {
            return false;
        }
        if ((skillName == null) || (skillName.equals("")))
        {
            return false;
        }
        boolean learned = true;
        prose_package pp;
        if (skill.purchaseSkill(player, skillName))
        {
            pp = prose.getPackage(PROSE_SKILL_LEARNED, new string_id(SKILL_N, skillName));
            if (fs_quests.isVillageEligible(player))
            {
                if (!hasObjVar(player, fs_quests.VAR_VILLAGE_COMPLETE))
                {
                    if (skillName.indexOf("force_sensitive_") > -1)
                    {
                        if (fs_quests.getBranchesLearned(player) >= 6)
                        {
                            setObjVar(player, fs_quests.VAR_VILLAGE_COMPLETE, 1);
                            CustomerServiceLog("fs_quests", "%TU has completed the village by attaining six FS skill branches.", player, null);
                        }
                    }
                }
            }
        }
        else 
        {
            pp = prose.getPackage(PROSE_TRAIN_FAILED, new string_id(SKILL_N, skillName));
            learned = false;
        }
        sendSystemMessageProse(player, pp);
        return learned;
    }
    public String[] getSkillData(String skillName, obj_id player) throws InterruptedException
    {
        if (skillName.equals(""))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        ret = utils.addElement(ret, "REQUIRED SKILLS");
        String[] skillReqs = getSkillPrerequisiteSkills(skillName);
        if (skillReqs == null)
        {
            ret = utils.addElement(ret, " none");
        }
        else 
        {
            for (int i = 0; i < skillReqs.length; i++)
            {
                String sName = getString(new string_id("skl_n", skillReqs[i]));
                ret = utils.addElement(ret, " " + sName);
            }
        }
        ret = utils.addElement(ret, "XP COSTS");
        dictionary xpReqs = getSkillPrerequisiteExperience(skillName);
        if ((xpReqs == null) || (xpReqs.isEmpty()))
        {
            ret = utils.addElement(ret, " none");
        }
        else 
        {
            java.util.Enumeration xp = xpReqs.keys();
            while (xp.hasMoreElements())
            {
                String xpType = (String)xp.nextElement();
                String sXp = getString(new string_id("exp_n", xpType));
                ret = utils.addElement(ret, " " + sXp + " = " + xpReqs.getInt(xpType));
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
    public boolean checkSkillStatus(obj_id trainer, obj_id player) throws InterruptedException
    {
        if (!isIdValid(trainer) || !isIdValid(player))
        {
            return false;
        }
        if (isJedi(player))
        {
            if (jedi.isJediTrainerForPlayer(player, trainer))
            {
                return true;
            }
        }
        obj_id self = trainer;
        obj_id speaker = player;
        String[] pSkills = getSkillListingForPlayer(speaker);
        String[] tSkills = skill.getTeacherSkills(trainer, speaker);
        if (tSkills == null || tSkills.length == 0)
        {
            return false;
        }
        String convo = CONVOFILE;
        if (jedi.isJediTrainerForPlayer(player, trainer))
        {
            convo = JEDI_TRAINER;
        }
        String[] lowSkills = getSkillPrerequisiteSkills(tSkills[0]);
        if (lowSkills != null && lowSkills.length > 0)
        {
            if (!utils.isSubset(pSkills, lowSkills))
            {
                string_id msg = new string_id(convo, "no_qualify");
                chat.chat(self, speaker, msg, chat.ChatFlag_targetOnly);
                npcEndConversation(speaker);
                Vector entries = new Vector();
                entries.setSize(0);
                for (int i = 0; i < lowSkills.length; i++)
                {
                    entries = utils.addElement(entries, "@skl_n:" + lowSkills[i]);
                }
                if (entries != null && entries.size() > 0)
                {
                    String title = "@skill_teacher:no_qualify_title";
                    String prompt = "@skill_teacher:no_qualify_prompt";
                    sui.listbox(self, player, prompt, sui.OK_ONLY, title, entries, "noHandler");
                }
                return false;
            }
        }
        if (utils.isSubset(pSkills, tSkills))
        {
            string_id msg = new string_id(convo, "topped_out");
            chat.chat(self, speaker, msg, chat.ChatFlag_targetOnly);
            npcEndConversation(speaker);
            return false;
        }
        string_id msg = new string_id(convo, "no_skill_pts");
        chat.chat(self, speaker, msg, chat.ChatFlag_targetOnly);
        npcEndConversation(speaker);
        return false;
    }
    public boolean hasSurpassedTrainer(obj_id trainer, obj_id player) throws InterruptedException
    {
        String[] pSkills = getSkillListingForPlayer(player);
        String[] tSkills = skill.getTeacherSkills(trainer, player);
        if (tSkills == null || tSkills.length == 0)
        {
            return false;
        }
        return utils.isSubset(pSkills, tSkills);
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (!volumeName.equals(FACETO_VOLUME_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (isInNpcConversation(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (canSee(self, breacher))
        {
            faceTo(self, breacher);
        }
        return SCRIPT_CONTINUE;
    }
    public String getJediConvoFile(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isJedi(player))
        {
            return null;
        }
        if (hasSkill(player, "jedi_light_side_journeyman_novice"))
        {
            return JEDI_TRAINER_LIGHT;
        }
        if (hasSkill(player, "jedi_dark_side_journeyman_novice"))
        {
            return JEDI_TRAINER_DARK;
        }
        return JEDI_TRAINER;
    }
}
