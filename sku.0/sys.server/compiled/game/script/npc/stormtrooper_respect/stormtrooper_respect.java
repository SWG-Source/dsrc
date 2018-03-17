package script.npc.stormtrooper_respect;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.factions;
import script.library.ai_lib;
import script.library.sui;
import script.library.utils;
import script.library.combat;
import script.library.colors;
import script.library.money;
import script.library.anims;
import script.library.pclib;
import script.library.prose;
import script.library.pet_lib;

public class stormtrooper_respect extends script.base_script
{
    public stormtrooper_respect()
    {
    }
    public static final String EMOTE_DATATABLE = "datatables/npc/stormtrooper_attitude/emote.iff";
    public static final String RANK_DATATABLE = "datatables/npc/stormtrooper_attitude/imprank.iff";
    public static final String KEYPHRASES = "datatables/npc/stormtrooper_attitude/keyphrases.iff";
    public static final String RESPONSE_TEXT = "datatables/npc/stormtrooper_attitude/responsetext.iff";
    public static final String PP_FILE_LOC = "stormtrooper_attitude/st_response";
    public static final float HEARING_RANGE = 15.0f;
    public static final int ST_FINE_CAP = 1000000;
    public static final int FINE_TOO_HIGH = 44;
    public static string_id strFlyText = new string_id("combat_effects", "hit_head");
    public static int randQuip = 0;
    public static int fineAmt = 0;
    public static int curCash = 0;
    public static int stealCash = 0;
    public static int fineRemainder = 0;
    public int getEmoteBeligerence(String emote) throws InterruptedException
    {
        int emote_row = dataTableSearchColumnForString(emote, 0, EMOTE_DATATABLE);
        int st_emote_type = dataTableGetInt(EMOTE_DATATABLE, emote_row, 1);
        int st_emote_alien = dataTableGetInt(EMOTE_DATATABLE, emote_row, 3);
        if (st_emote_type == 3)
        {
            return 6;
        }
        else if (st_emote_type == 2 && st_emote_alien == 1)
        {
            return 5;
        }
        else if (st_emote_type == 2 && st_emote_alien == 0)
        {
            return 4;
        }
        else if (st_emote_type == 1 && st_emote_alien == 1)
        {
            return 3;
        }
        else if (st_emote_type == 1 && st_emote_alien == 0)
        {
            return 2;
        }
        else if (st_emote_type == 0 && st_emote_alien == 1)
        {
            return 1;
        }
        else if (st_emote_type == 0 && st_emote_alien == 0)
        {
            return 0;
        }
        else 
        {
            return -1;
        }
    }
    public prose_package getTroopQuip(obj_id target, int quipRow) throws InterruptedException
    {
        string_id response = new string_id(PP_FILE_LOC, utils.dataTableGetString(RESPONSE_TEXT, quipRow, 1));
        prose_package pp = prose.getPackage(response, target);
        return pp;
    }
    public void playKnockdown(obj_id victim, obj_id attacker) throws InterruptedException
    {
        if (!isIdValid(victim) || !isIdValid(attacker))
        {
            return;
        }
        String strPlaybackScript = "";
        obj_id objWeapon = getCurrentWeapon(attacker);
        int intWeaponType = getWeaponType(objWeapon);
        int intWeaponCategory = combat.getWeaponCategory(intWeaponType);
        if (intWeaponCategory == combat.RANGED_WEAPON)
        {
            strPlaybackScript = "ranged_melee_light";
        }
        else 
        {
            strPlaybackScript = "attack_high_center_light_0";
        }
        if (pet_lib.isMounted(victim))
        {
            pet_lib.doDismountNow(victim, false);
        }
        attacker_results cbtAttackerResults = new attacker_results();
        defender_results[] cbtDefenderResults = new defender_results[1];
        cbtDefenderResults[0] = new defender_results();
        cbtAttackerResults.id = attacker;
        cbtAttackerResults.endPosture = getPosture(attacker);
        cbtAttackerResults.weapon = objWeapon;
        cbtDefenderResults[0].endPosture = POSTURE_INCAPACITATED;
        cbtDefenderResults[0].result = COMBAT_RESULT_HIT;
        cbtDefenderResults[0].id = victim;
        doCombatResults(strPlaybackScript, cbtAttackerResults, cbtDefenderResults);
    }
    public int getImpRank(obj_id object) throws InterruptedException
    {
        int final_rank = 0;
        if (!hasObjVar(object, "imp_rank"))
        {
            String stName = ai_lib.getCreatureName(object);
            int npc_rank_row = dataTableSearchColumnForString(stName, 0, RANK_DATATABLE);
            int st_rank_type = dataTableGetInt(RANK_DATATABLE, npc_rank_row, 2);
            if (st_rank_type < 22)
            {
                setObjVar(object, "imp_rank", st_rank_type);
            }
            else 
            {
                switch (st_rank_type)
                {
                    case 80:
                    final_rank = rand(0, 7);
                    break;
                    case 81:
                    final_rank = (rand(0, 5) + 8);
                    break;
                    case 82:
                    final_rank = (rand(0, 7) + 14);
                    break;
                    case 83:
                    final_rank = (rand(0, 13) + 5);
                    break;
                    case 84:
                    final_rank = (rand(0, 2) + 19);
                    break;
                    case 85:
                    final_rank = -1;
                    break;
                    case 86:
                    final_rank = -1;
                    break;
                    case 87:
                    final_rank = -1;
                    break;
                    case 88:
                    final_rank = -1;
                    break;
                    case 89:
                    final_rank = -1;
                    break;
                    default:
                    final_rank = -1;
                }
                if (final_rank > -1)
                {
                    setObjVar(object, "imp_rank", final_rank);
                }
            }
        }
        else 
        {
            final_rank = getIntObjVar(object, "imp_rank");
        }
        return final_rank;
    }
    public void returnToDuties(obj_id troop) throws InterruptedException
    {
        if (utils.hasScriptVar(troop, "tmpBehavior"))
        {
            ai_lib.setDefaultCalmBehavior(troop, utils.getIntScriptVar(troop, "tmpBehavior"));
            utils.removeScriptVar(troop, "tmpBehavior");
        }
    }
    public void paySTFines(obj_id payer, obj_id payee, int amount, int Quip) throws InterruptedException
    {
        if (fineAmt > ST_FINE_CAP)
        {
            fineAmt = ST_FINE_CAP;
            chat.publicChat(payee, null, null, null, getTroopQuip(payer, FINE_TOO_HIGH));
        }
        utils.removeObjVar(payer, "trooper_fine");
        faceTo(payee, payer);
        chat.publicChat(payee, null, null, null, getTroopQuip(payer, Quip));
        money.pay(payer, money.ACCT_IMPERIAL, fineAmt, "handlePayment", null);
        utils.moneyOutMetric(payee, money.ACCT_ST_FINES, fineAmt);
        utils.removeObjVar(payer, "trooper_fine");
        if (utils.hasObjVar(payer, "overdue_fine"))
        {
            utils.removeObjVar(payer, "overdue_fine");
        }
        if (utils.hasObjVar(payer, "week_old_fine"))
        {
            utils.removeObjVar(payer, "week_old_fine");
        }
        if (hasScript(payer, "npc.stormtrooper_respect.overdue_fines"))
        {
            detachScript(payer, "npc.stormtrooper_respect.overdue_fines");
        }
    }
    public int handleFine(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        obj_id finedPlayer = sui.getPlayerId(params);
        fineAmt = utils.getIntObjVar(finedPlayer, "trooper_fine");
        int totCreds = 0;
        fineRemainder = 0;
        totCreds = money.getTotalMoney(finedPlayer);
        if (fineAmt > totCreds)
        {
            if (fineAmt > ST_FINE_CAP)
            {
                fineAmt = ST_FINE_CAP;
                chat.publicChat(self, null, null, null, getTroopQuip(finedPlayer, FINE_TOO_HIGH));
            }
            fineRemainder = fineAmt - totCreds;
            utils.removeObjVar(finedPlayer, "trooper_fine");
            utils.setObjVar(finedPlayer, "trooper_fine", fineRemainder);
            ai_lib.doAction(self, "point_accusingly");
            randQuip = (rand(0, 6) + 6);
            chat.publicChat(self, null, null, null, getTroopQuip(finedPlayer, randQuip));
            attachScript(finedPlayer, "npc.stormtrooper_respect.overdue_fines");
            if (!hasObjVar(finedPlayer, "overdue_fine"))
            {
                utils.setObjVar(finedPlayer, "overdue_fine", getGameTime());
            }
            money.pay(finedPlayer, money.ACCT_IMPERIAL, totCreds, "handlePayment", null);
            utils.moneyOutMetric(self, money.ACCT_ST_FINES, totCreds);
            returnToDuties(self);
        }
        else 
        {
            if (totCreds > fineAmt * 1000)
            {
                fineAmt += (fineAmt / 10) * rand(5, 20);
                ai_lib.doAction(self, "laugh_pointing");
                randQuip = rand(0, 5);
            }
            else if (totCreds > fineAmt * 100)
            {
                fineAmt += (fineAmt / 10) * rand(3, 15);
                ai_lib.doAction(self, "laugh_pointing");
                randQuip = rand(0, 5);
            }
            else if (totCreds > fineAmt * 10)
            {
                fineAmt += (fineAmt / 10) * rand(1, 10);
                ai_lib.doAction(self, "laugh_pointing");
                randQuip = rand(0, 5);
            }
            else 
            {
                ai_lib.doAction(self, "point_accusingly");
                randQuip = (rand(0, 3) + 45);
            }
            paySTFines(finedPlayer, self, fineAmt, randQuip);
            returnToDuties(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int stFilterHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "stFilterSpeaker") && !isIncapacitated(self) && !isDead(self) && !ai_lib.isInCombat(self))
        {
            obj_id speakerTarget = utils.getObjIdScriptVar(self, "stFilterSpeaker");
            if (isPlayer(speakerTarget) && !isIncapacitated(speakerTarget) && !isDead(speakerTarget) && !ai_lib.isInCombat(speakerTarget))
            {
                String stSpeech = utils.getStringScriptVar(speakerTarget, "stFilterSpeech");
                String[] keyphrases = dataTableGetStringColumn(KEYPHRASES, "keyphrase");
                String stSayFaction = factions.getFaction(speakerTarget);
                for (int i = 0; i < keyphrases.length; i++)
                {
                    if ((toLower(stSpeech)).indexOf(toLower(keyphrases[i])) > -1)
                    {
                        randQuip = 40;
                        chat.publicChat(self, null, null, null, getTroopQuip(speakerTarget, randQuip));
                        faceTo(self, speakerTarget);
                        factions.addFactionStanding(speakerTarget, factions.FACTION_IMPERIAL, -100);
                        location pcLoc = utils.getLocation(speakerTarget);
                        location pcAttackLoc = utils.getRandomAwayLocation(utils.getLocation(speakerTarget), 0.5f, 1.0f);
                        utils.setScriptVar(self, "coming_to_kill_u", speakerTarget);
                        setMovementRun(self);
                        pathTo(self, pcAttackLoc);
                        if (!utils.hasObjVar(speakerTarget, "trooper_fine"))
                        {
                            utils.setObjVar(speakerTarget, "trooper_fine", 10000);
                            sui.msgbox(self, speakerTarget, "@stormtrooper_attitude/st_response:imperial_fine_10000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                        }
                        else 
                        {
                            int previousDebt = utils.getIntObjVar(speakerTarget, "trooper_fine");
                            utils.removeObjVar(speakerTarget, "trooper_fine");
                            previousDebt += 10000;
                            utils.setObjVar(speakerTarget, "trooper_fine", previousDebt);
                            sui.msgbox(self, speakerTarget, getString(new string_id(PP_FILE_LOC, "imperial_fine_10000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + previousDebt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                        }
                    }
                }
            }
            utils.removeScriptVar(speakerTarget, "stFilter");
            utils.removeScriptVar(self, "stFilterSpeaker");
            utils.removeScriptVar(speakerTarget, "stFilterSpeech");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawEmote(obj_id self, obj_id emoteSayer, String emotein) throws InterruptedException
    {
        int fine_debt = 0;
        boolean incapFineResult;
        obj_id emotetarget = getLookAtTarget(emoteSayer);
        if (!isIdValid(emotetarget) || ai_lib.isInCombat(self) || ai_lib.isInCombat(emoteSayer) || isIncapacitated(self) || isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        if (here.area.equals("tutorial"))
        {
            return SCRIPT_CONTINUE;
        }
        if (emotetarget == self)
        {
            utils.setScriptVar(self, "tmpBehavior", ai_lib.getDefaultCalmBehavior(self));
            String stEmoteFaction = factions.getFaction(emoteSayer);
            if (stEmoteFaction != null && stEmoteFaction.equals(factions.FACTION_IMPERIAL))
            {
                int imp_st_rank = getImpRank(self);
                int imp_pc_rank = pvpGetCurrentGcwRank(emoteSayer);
                if (imp_st_rank > imp_pc_rank)
                {
                    int species = getSpecies(emoteSayer);
                    if (species != SPECIES_HUMAN)
                    {
                        switch (getEmoteBeligerence(emotein))
                        {
                            case 6:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            fineAmt = 5000;
                            curCash = money.getCashBalance(emoteSayer);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 5000);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -50);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_5000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                playKnockdown(emoteSayer, self);
                                showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 5000;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -50);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_5000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                playKnockdown(emoteSayer, self);
                                setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                                showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 5:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            fineAmt = 5000;
                            curCash = money.getCashBalance(emoteSayer);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 5000);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -50);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_5000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                playKnockdown(emoteSayer, self);
                                setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                                showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 5000;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -50);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_5000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                playKnockdown(emoteSayer, self);
                                setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                                showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 4:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 2000);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_2000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 2000;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_2000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 3:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 2000);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_2000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 2000;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_2000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 2:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 500);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_500", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 500;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_500")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 1:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 500);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_500", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 500;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_500")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                randQuip = (rand(0, 3) + 13);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 0:
                            if (utils.hasObjVar(emoteSayer, "trooper_fine") & emotein.equals("hail"))
                            {
                                ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                faceTo(self, emoteSayer);
                                ai_lib.doAction(self, "point_accusingly");
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "pay_outstanding_fine_prefix")) + "\n \n" + getString(new string_id(PP_FILE_LOC, "pay_fine_total_suffix")) + fine_debt + getString(new string_id(PP_FILE_LOC, "imperial_fine_credits")), sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            }
                            break;
                            default:
                            ai_lib.setDefaultCalmBehavior(self, utils.getIntScriptVar(self, "tmpBehavior"));
                            if (utils.hasScriptVar(self, "tmpBehavior"))
                            {
                                utils.removeScriptVar(self, "tmpBehavior");
                            }
                            break;
                        }
                    }
                    else 
                    {
                        switch (getEmoteBeligerence(emotein))
                        {
                            case 6:
                            fineAmt = 3500;
                            curCash = money.getCashBalance(emoteSayer);
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 3500);
                                faceTo(self, emoteSayer);
                                ai_lib.doAction(self, "point_accusingly");
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -35);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_3500", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                playKnockdown(emoteSayer, self);
                                setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                                showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 3500;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -35);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_3500")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                playKnockdown(emoteSayer, self);
                                setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                                showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 5:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 2000);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_2000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 2000;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_2000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 4:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 2000);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_2000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 2000;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_2000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")), sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 3:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 500);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_500", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 500;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_500")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 2:
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                            {
                                utils.setObjVar(emoteSayer, "trooper_fine", 500);
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                                sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_500", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            else 
                            {
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                fine_debt += 500;
                                faceTo(self, emoteSayer);
                                factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_500")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                                randQuip = (rand(0, 3) + 21);
                                chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                            }
                            break;
                            case 1:
                            ai_lib.setDefaultCalmBehavior(self, utils.getIntScriptVar(self, "tmpBehavior"));
                            if (utils.hasScriptVar(self, "tmpBehavior"))
                            {
                                utils.removeScriptVar(self, "tmpBehavior");
                            }
                            break;
                            case 0:
                            if (utils.hasObjVar(emoteSayer, "trooper_fine") & emotein.equals("hail"))
                            {
                                ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                                fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                                faceTo(self, emoteSayer);
                                ai_lib.doAction(self, "point_accusingly");
                                sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "pay_outstanding_fine_prefix")) + "\n \n" + getString(new string_id(PP_FILE_LOC, "pay_fine_total_suffix")) + fine_debt + getString(new string_id(PP_FILE_LOC, "imperial_fine_credits")), sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                                utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            }
                            break;
                            default:
                            ai_lib.setDefaultCalmBehavior(self, utils.getIntScriptVar(self, "tmpBehavior"));
                            if (utils.hasScriptVar(self, "tmpBehavior"))
                            {
                                utils.removeScriptVar(self, "tmpBehavior");
                            }
                            break;
                        }
                    }
                }
                else if (imp_pc_rank > imp_st_rank)
                {
                    ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                    switch (getEmoteBeligerence(emotein))
                    {
                        case 6:
                        faceTo(self, emoteSayer);
                        ai_lib.doAction(self, "standing_placate");
                        randQuip = (rand(0, 1) + 35);
                        chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        break;
                        case 5:
                        faceTo(self, emoteSayer);
                        ai_lib.doAction(self, "salute2");
                        randQuip = (rand(0, 1) + 35);
                        chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        break;
                        case 4:
                        faceTo(self, emoteSayer);
                        ai_lib.doAction(self, "salute2");
                        randQuip = (rand(0, 2) + 37);
                        chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        break;
                        case 3:
                        faceTo(self, emoteSayer);
                        ai_lib.doAction(self, "salute2");
                        randQuip = (rand(0, 2) + 37);
                        chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        break;
                        case 2:
                        faceTo(self, emoteSayer);
                        ai_lib.doAction(self, "salute1");
                        randQuip = (rand(0, 2) + 37);
                        chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        break;
                        case 1:
                        faceTo(self, emoteSayer);
                        ai_lib.doAction(self, "salute1");
                        randQuip = (rand(0, 2) + 37);
                        chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        break;
                        case 0:
                        faceTo(self, emoteSayer);
                        ai_lib.doAction(self, "salute1");
                        break;
                        default:
                        break;
                    }
                    ai_lib.setDefaultCalmBehavior(self, utils.getIntScriptVar(self, "tmpBehavior"));
                    if (utils.hasScriptVar(self, "tmpBehavior"))
                    {
                        utils.removeScriptVar(self, "tmpBehavior");
                    }
                }
                else 
                {
                    if (getEmoteBeligerence(emotein) > 4)
                    {
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        int species = getSpecies(emoteSayer);
                        if (species != SPECIES_HUMAN)
                        {
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -50);
                            randQuip = (rand(0, 3) + 21);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -25);
                            randQuip = (rand(0, 1) + 25);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        ai_lib.setDefaultCalmBehavior(self, utils.getIntScriptVar(self, "tmpBehavior"));
                        if (utils.hasScriptVar(self, "tmpBehavior"))
                        {
                            utils.removeScriptVar(self, "tmpBehavior");
                        }
                    }
                }
            }
            else 
            {
                int species = getSpecies(emoteSayer);
                if (species != SPECIES_HUMAN)
                {
                    switch (getEmoteBeligerence(emotein))
                    {
                        case 6:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 5000);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -50);
                            sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_5000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            playKnockdown(emoteSayer, self);
                            setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                            showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 5000;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -50);
                            sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_5000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            playKnockdown(emoteSayer, self);
                            setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                            showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 5:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 5000);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -50);
                            sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_5000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            playKnockdown(emoteSayer, self);
                            setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                            showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 5000;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -50);
                            sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_5000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            playKnockdown(emoteSayer, self);
                            setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                            showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 4:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 3500);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -35);
                            sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_3500", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 3500;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -35);
                            sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_3500")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 3:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 3500);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -35);
                            sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_3500", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 3500;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -35);
                            sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_3500")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 2:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 1000);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -10);
                            sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_1000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 1000;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -10);
                            sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_1000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 1:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 1000);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -10);
                            sui.msgbox(self, emoteSayer, "@stormtrooper_attitude/st_response:imperial_fine_1000", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 1000;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -10);
                            sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "imperial_fine_1000")) + " " + getString(new string_id(PP_FILE_LOC, "imperial_fine_outstanding")) + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            randQuip = (rand(0, 3) + 27);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 0:
                        if (utils.hasObjVar(emoteSayer, "trooper_fine") & emotein.equals("hail"))
                        {
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            faceTo(self, emoteSayer);
                            ai_lib.doAction(self, "point_accusingly");
                            sui.msgbox(self, emoteSayer, "You have an outstanding fine, I suggest you pay it! It comes to a total of " + fine_debt + " credits.", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                        }
                        break;
                        default:
                        ai_lib.setDefaultCalmBehavior(self, utils.getIntScriptVar(self, "tmpBehavior"));
                        if (utils.hasScriptVar(self, "tmpBehavior"))
                        {
                            utils.removeScriptVar(self, "tmpBehavior");
                        }
                        break;
                    }
                }
                else 
                {
                    switch (getEmoteBeligerence(emotein))
                    {
                        case 6:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 3500);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -35);
                            sui.msgbox(self, emoteSayer, "You have been fined 3500 credits by the Empire.", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            playKnockdown(emoteSayer, self);
                            setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                            showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 3500;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -35);
                            sui.msgbox(self, emoteSayer, "You have been fined 3500 credits by the Empire. \n Added to your outstanding fines, it comes to " + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            playKnockdown(emoteSayer, self);
                            setAttrib(emoteSayer, HEALTH, (rand(30, 90) * -1));
                            showFlyText(emoteSayer, strFlyText, 1.5f, colors.TOMATO);
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 5:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 2000);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                            sui.msgbox(self, emoteSayer, "You have been fined 2000 credits by the Empire.", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 2000;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                            sui.msgbox(self, emoteSayer, "You have been fined 2000 credits by the Empire. \n Added to your outstanding fines, it comes to " + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 4:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 2000);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                            sui.msgbox(self, emoteSayer, "You have been fined 2000 credits by the Empire.", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 2000;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -20);
                            sui.msgbox(self, emoteSayer, "You have been fined 2000 credits by the Empire. \n Added to your outstanding fines, it comes to " + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 3:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 500);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                            sui.msgbox(self, emoteSayer, "You have been fined 500 credits by the Empire.", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 500;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                            sui.msgbox(self, emoteSayer, "You have been fined 500 credits by the Empire. \n Added to your outstanding fines, it comes to " + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 2:
                        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                        if (!utils.hasObjVar(emoteSayer, "trooper_fine"))
                        {
                            utils.setObjVar(emoteSayer, "trooper_fine", 500);
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                            sui.msgbox(self, emoteSayer, "You have been fined 500 credits by the Empire.", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        else 
                        {
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            fine_debt += 500;
                            faceTo(self, emoteSayer);
                            factions.addFactionStanding(emoteSayer, factions.FACTION_IMPERIAL, -5);
                            sui.msgbox(self, emoteSayer, "You have been fined 500 credits by the Empire. \n Added to your outstanding fines, it comes to " + fine_debt + ".", sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                            randQuip = (rand(0, 3) + 31);
                            chat.publicChat(self, null, null, null, getTroopQuip(emoteSayer, randQuip));
                        }
                        break;
                        case 1:
                        ai_lib.setDefaultCalmBehavior(self, utils.getIntScriptVar(self, "tmpBehavior"));
                        if (utils.hasScriptVar(self, "tmpBehavior"))
                        {
                            utils.removeScriptVar(self, "tmpBehavior");
                        }
                        break;
                        case 0:
                        if (utils.hasObjVar(emoteSayer, "trooper_fine") & emotein.equals("hail"))
                        {
                            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
                            fine_debt = utils.getIntObjVar(emoteSayer, "trooper_fine");
                            faceTo(self, emoteSayer);
                            ai_lib.doAction(self, "point_accusingly");
                            sui.msgbox(self, emoteSayer, getString(new string_id(PP_FILE_LOC, "pay_outstanding_fine_prefix")) + "\n \n" + getString(new string_id(PP_FILE_LOC, "pay_fine_total_suffix")) + fine_debt + getString(new string_id(PP_FILE_LOC, "imperial_fine_credits")), sui.OK_ONLY, "@stormtrooper_attitude/st_response:imperial_fine_t", "handleFine");
                            utils.setObjVar(emoteSayer, "trooper_fine", fine_debt);
                        }
                        break;
                        default:
                        ai_lib.setDefaultCalmBehavior(self, utils.getIntScriptVar(self, "tmpBehavior"));
                        if (utils.hasScriptVar(self, "tmpBehavior"))
                        {
                            utils.removeScriptVar(self, "tmpBehavior");
                        }
                        break;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String speech) throws InterruptedException
    {
        if (speaker == self || !isPlayer(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(speaker) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self) || isDead(self) || ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        location myLoc = getLocation(self);
        location speakerLoc = getLocation(speaker);
        if (myLoc == null || speakerLoc == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (myLoc.area.equals("tutorial"))
        {
            return SCRIPT_CONTINUE;
        }
        if (getDistance(myLoc, speakerLoc) > HEARING_RANGE)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id myCell = myLoc.cell;
        obj_id speakerCell = speakerLoc.cell;
        if (!isIdValid(myCell))
        {
            if (isIdValid(speakerCell))
            {
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            if (!isIdValid(speakerCell))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (myCell != speakerCell)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (!utils.hasScriptVar(speaker, "stFilter"))
        {
            messageTo(self, "stFilterHandler", null, 1, false);
            utils.setScriptVar(speaker, "stFilter", self);
            utils.setScriptVar(self, "stFilterSpeaker", speaker);
            utils.setScriptVar(speaker, "stFilterSpeech", speech);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "coming_to_kill_u"))
        {
            obj_id victem = utils.getObjIdScriptVar(self, "coming_to_kill_u");
            String stSayFaction = factions.getFaction(victem);
            playKnockdown(victem, self);
            setAttrib(victem, HEALTH, (rand(30, 90) * -1));
            showFlyText(victem, strFlyText, 1.5f, colors.TOMATO);
            setMovementWalk(self);
            pathTo(self, getHomeLocation(self));
        }
        utils.removeScriptVar(self, "coming_to_kill_u");
        return SCRIPT_CONTINUE;
    }
}
