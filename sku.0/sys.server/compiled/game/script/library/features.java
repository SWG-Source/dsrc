package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class features extends script.base_script
{
    public features()
    {
    }
    public static final int GAME_BASE = 0x0001;
    public static final int GAME_COLLECTORS_EDITION = 0x0002;
    public static final int GAME_SPACE_EXPANSION_BETA = 0x0004;
    public static final int GAME_SPACE_EXPANSION_PRE_ORDER = 0x0008;
    public static final int GAME_SPACE_EXPANSION_RETAIL = 0x0010;
    public static final int GAME_SWG_RETAIL = 0x0020;
    public static final int GAME_SPACE_EXPANSION_PROMOTION = 0x0040;
    public static final int GAME_JAPANESE_RETAIL = 0x0080;
    public static final int GAME_JAPANESE_COLLECTORS = 0x0100;
    public static final int GAME_EPISODE3_EXPANSION_RETAIL = 0x0200;
    public static final int GAME_EPISODE3_PREORDER_DIGITAL_DOWNLOAD = 0x0800;
    public static final int GAME_TRIALS_OF_OBIWAN_PREORDER = 0x4000;
    public static final int GAME_TRIALS_OF_OBIWAN_RETAIL = 0x8000;
    public static final int SUBSCRIPTION_BASE = 0x1;
    public static final int SUBSCRIPTION_FREE_TRIAL = 0x2;
    public static final int SUBSCRIPTION_COMBAT_BALANCE_ACCESS = 0x4;
    public static boolean hasCollectorEdition(obj_id player) throws InterruptedException
    {
        return (getGameFeatureBits(player) & GAME_COLLECTORS_EDITION) != 0;
    }
    public static boolean hasSpaceExpansion(obj_id player) throws InterruptedException
    {
        return (getGameFeatureBits(player) & GAME_SPACE_EXPANSION_RETAIL) != 0;
    }
    public static boolean hasSpaceExpansionPromotion(obj_id player) throws InterruptedException
    {
        return (getGameFeatureBits(player) & GAME_SPACE_EXPANSION_PROMOTION) != 0;
    }
    public static boolean hasJapaneseCollectorEdition(obj_id player) throws InterruptedException
    {
        return (getGameFeatureBits(player) & GAME_JAPANESE_COLLECTORS) != 0;
    }
    public static boolean hasEpisode3Expansion(obj_id player) throws InterruptedException
    {
        return (hasEpisode3PreOrderDigitalDownload(player) || hasEpisode3ExpansionRetail(player));
    }
    public static boolean hasEpisode3PreOrderDigitalDownload(obj_id player) throws InterruptedException
    {
        return (getGameFeatureBits(player) & GAME_EPISODE3_PREORDER_DIGITAL_DOWNLOAD) != 0;
    }
    public static boolean hasEpisode3ExpansionRetail(obj_id player) throws InterruptedException
    {
        return (getGameFeatureBits(player) & GAME_EPISODE3_EXPANSION_RETAIL) != 0;
    }
    public static boolean hasTrialsOfObiwanExpansionRetail(obj_id player) throws InterruptedException
    {
        return (getGameFeatureBits(player) & GAME_TRIALS_OF_OBIWAN_RETAIL) != 0;
    }
    public static boolean hasTrialsOfObiwanExpansionPreorder(obj_id player) throws InterruptedException
    {
        return (getGameFeatureBits(player) & GAME_TRIALS_OF_OBIWAN_PREORDER) != 0;
    }
    public static boolean hasTrialsOfObiwanExpansion(obj_id player) throws InterruptedException
    {
        return hasTrialsOfObiwanExpansionRetail(player) || hasTrialsOfObiwanExpansionPreorder(player);
    }
    public static boolean hasMustafarExpansionRetail(obj_id player) throws InterruptedException
    {
        return hasTrialsOfObiwanExpansion(player);
    }
    public static boolean hasFreeTrial(obj_id player) throws InterruptedException
    {
        return (getSubscriptionFeatureBits(player) & SUBSCRIPTION_FREE_TRIAL) != 0;
    }
    public static boolean hasCombatBalanceAccess(obj_id player) throws InterruptedException
    {
        return (getSubscriptionFeatureBits(player) & SUBSCRIPTION_COMBAT_BALANCE_ACCESS) != 0;
    }
    public static boolean isCollectorEdition(obj_id player) throws InterruptedException
    {
        return hasCollectorEdition(player);
    }
    public static boolean isSpaceEdition(obj_id player) throws InterruptedException
    {
        return hasSpaceExpansion(player);
    }
    public static boolean isJPCollectorEdition(obj_id player) throws InterruptedException
    {
        return hasJapaneseCollectorEdition(player);
    }
    public static boolean isDecemberRegistration(obj_id player) throws InterruptedException
    {
        return hasSpaceExpansionPromotion(player);
    }
}
