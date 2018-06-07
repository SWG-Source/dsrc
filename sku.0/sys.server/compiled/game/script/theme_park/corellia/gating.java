package script.theme_park.corellia;

import script.obj_id;

public class gating extends script.base_script
{
    public gating()
    {
    }
    public static final String QUALDO_QUEST = "qualdoNewbieQuest1";
    public static final String WEDGE_QUEST = "wedgeNewbieQuest1";
    public static final String LANCE_QUEST = "lanceNewbieQuest1";
    public static final String LANCE_QUEST2 = "LanceNewbieQuest2";
    public static final String LANCE_QUEST3 = "lanceNewbieQuest3";
    public static final String WEDGE_QUEST2 = "wedgeIntQuest1";
    public static final String ACKBAR_QUEST = "ackbarIntQuest1";
    public static final String ACKBAR_QUEST2 = "ackbarIntQuest2";
    public static final String LEIA_QUEST = "leiaAdvQuest1";
    public static final String LEIA_QUEST2 = "leiaAdvQuest2";
    public static final String LEIA_QUEST3 = "leiaAdvQuest3";
    public static final String LEIA_CRAFTING_1 = "leiaCrafting1";
    public static final String LEIA_CRAFTING_2 = "leiaCrafting2";
    public static final String LEIA_COMBAT_1 = "leiaCombat1";
    public static final String LEIA_COMBAT_2 = "leiaCombat2";
    public static boolean canTakeQuest(obj_id player, String questId) throws InterruptedException
    {
        return true;
    }
    public static boolean canTakeInfiltrationMission(obj_id player, String questId) throws InterruptedException
    {
        return true;
    }
}
