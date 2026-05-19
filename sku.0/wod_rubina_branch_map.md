# WOD Rubina Conversation Branch Map

Source script:

- `sys.server/compiled/game/script/conversation/wod_rubina.java`
- String file used by script: `conversation/wod_rubina`
- NPC attachment: `gray_witch` in `sys.server/compiled/game/datatables/mob/creatures.tab`

This file maps the behavior of the imported generated conversation script so Rubina can be repaired directly, one concrete quest NPC at a time. The original Java and the live STF were out of sync; preserve the state checks and quest effects, but treat the old generated string IDs as suspect unless they have been reconciled against the live STF.

## Current Repair Scope

`conversation/wod_rubina.java` has been remapped to live `conversation/wod_rubina` STF keys for the core prologue loop:

- Walkabout II return to Rubina.
- The generic prologue menu after Walkabout II is complete.
- The task selection menu for herbs, enemies, and wisdom.
- The task detail branches that grant those repeatable prologue quests.
- Repeatable turn-ins for herbs, enemies, and wisdom.
- Status text and the ready-to-go referrals to Nightsister Stronghold or Singing Mountain.
- The chest/data-cylinder and cache return path.

As of this pass, the script no longer references any missing `s_N` keys from the live Rubina STF.

## Global State

Rubina uses player objvar `wod_prologue_quests` as the prologue alignment score.

- Positive score means Nightsister support.
- Negative score means Singing Mountain support.
- On the generic prologue menu path, the score is initialized to `0` if missing.
- On that same path, the score is clamped to `-8..8`.

Score thresholds:

- `status == 0`: neutral / no clan support.
- `status == 1 || status == -1`: one quest complete.
- `status > 1`: slight Nightsister leaning.
- `status < -1`: slight Singing Mountain leaning.
- `status > 4`: mostly Nightsister.
- `status < -4`: mostly Singing Mountain.
- `status > 7`: full Nightsister support.
- `status < -7`: full Singing Mountain support.

## Start Priority

`OnStartNpcConversation` checks branches in this exact order. First true condition wins.

1. `onReturnWalkabout2`
2. `onReturnHerbsNS`
3. `onReturnHerbsSM`
4. `onReturnRancorNS`
5. `onReturnRancorSM`
6. `onReturnWisdomNS`
7. `onReturnWisdomSM`
8. `onReturnChest`
9. `onReturnLeftBehind`
10. `hasQuestsActive`
11. `walkabout2Finished`
12. `!walkabout2Finished`

The `hasQuestsActive` guard blocks the generic prologue menu while any repeatable prologue task is active.

## Branches

### Walkabout Return

State check:

- `groundquests.isTaskActive(player, "wod_prologue_walkabout_02", "speakWithRubina")`

Conversation:

- Start branch `1`.
- Player response `s_22`.

Trigger:

- `groundquests.sendSignal(player, "speakWithRubina")`

Quest table:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_prologue_walkabout_02.tab`
- Wait task `speakWithRubina`, completed by signal `speakWithRubina`.

### Herb Return, Nightsister Basket

State check:

- `groundquests.isTaskActive(player, "wod_prologue_herb_gathering", "returnForTreatNightsister")`

Conversation:

- Start branch `3`.
- Player response `s_45`.

Triggers:

- `groundquests.sendSignal(player, "selectBasketNightsister")`
- `wod_prologue_quests += 1`
- `modifyCollectionSlotValue(player, "wod_prologue_ns_herb_01", 1)`

Quest table:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_prologue_herb_gathering.tab`
- Wait task `returnForTreatNightsister`, completed by signal `selectBasketNightsister`.

### Herb Return, Singing Mountain Basket

State check:

- `groundquests.isTaskActive(player, "wod_prologue_herb_gathering", "returnForTreatSinging")`

Conversation:

- Start branch `5`.
- Player response `s_49`.

Triggers:

- `groundquests.sendSignal(player, "selectBasketSiging")`
- `wod_prologue_quests -= 1`
- `modifyCollectionSlotValue(player, "wod_prologue_herb_01", 1)`

Quest table:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_prologue_herb_gathering.tab`
- Wait task `returnForTreatSinging`, completed by signal `selectBasketSiging`.
- Note the spelling is `Siging` in both script and table.

### Enemy Return, Nightsister

State check:

- `groundquests.isTaskActive(player, "wod_prologue_kill_rancor", "waitForRubinaNightsisterRancor")`
- OR `groundquests.isTaskActive(player, "wod_prologue_kill_spider_clan", "waitForRubinaNightsisterSpider")`

Conversation:

- Start branch `7`.
- Player response `s_37`.

Triggers:

- If rancor task active:
  - `groundquests.sendSignal(player, "waitForRubinaNightsisterRancor")`
  - `wod_prologue_quests += 1`
  - `modifyCollectionSlotValue(player, "wod_prologue_kill_rancors_01", 1)`
- If spider task active:
  - `groundquests.sendSignal(player, "waitForRubinaNightsisterSpider")`
  - `wod_prologue_quests += 1`
  - `modifyCollectionSlotValue(player, "wod_prologue_kill_spiders_01", 1)`

Quest tables:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_prologue_kill_rancor.tab`
- `sys.shared/compiled/game/datatables/questtask/quest/wod_prologue_kill_spider_clan.tab`

### Enemy Return, Singing Mountain

State check:

- `groundquests.isTaskActive(player, "wod_prologue_kill_rancor", "waitForRubinaSingingRancor")`
- OR `groundquests.isTaskActive(player, "wod_prologue_kill_spider_clan", "waitForRubinaSingingSpider")`

Conversation:

- Start branch `9`.
- Player response `s_41`.

Triggers:

- If rancor task active:
  - `groundquests.sendSignal(player, "waitForRubinaSingingRancor")`
  - `wod_prologue_quests -= 1`
  - `modifyCollectionSlotValue(player, "wod_prologue_kill_rancors_01", 1)`
- If spider task active:
  - `groundquests.sendSignal(player, "waitForRubinaSingingSpider")`
  - `wod_prologue_quests -= 1`
  - `modifyCollectionSlotValue(player, "wod_prologue_kill_spiders_01", 1)`

Quest tables:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_prologue_kill_rancor.tab`
- `sys.shared/compiled/game/datatables/questtask/quest/wod_prologue_kill_spider_clan.tab`

### Wisdom Return, Nightsister

State check:

- Any `wod_outcast_1` through `wod_outcast_7` has task `talkRubinaNightsister` active.

Conversation:

- Start branch `11`.
- Player response `s_29`.

Triggers:

- `groundquests.sendSignal(player, "wod_outcast_nightsister_win_2")`
- `wod_prologue_quests += 1`
- `modifyCollectionSlotValue(player, "wod_prologue_nightsister_outcasts_01", 1)`

Quest tables:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_outcast_1.tab`
- Same structure repeats through `wod_outcast_7.tab`.

### Wisdom Return, Singing Mountain

State check:

- Any `wod_outcast_1` through `wod_outcast_7` has task `talkRubinaSinging` active.

Conversation:

- Start branch `13`.
- Player response `s_33`.

Triggers:

- `groundquests.sendSignal(player, "wod_outcast_singing_win_2")`
- `wod_prologue_quests -= 1`
- `modifyCollectionSlotValue(player, "wod_prologue_singing_outcasts_01", 1)`

Quest tables:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_outcast_1.tab`
- Same structure repeats through `wod_outcast_7.tab`.

### Chest / Data Cylinder Return

State check:

- `groundquests.isTaskActive(player, "wod_rubina_chest", "giveCylinder")`
- OR `groundquests.hasCompletedQuest(player, "wod_rubina_chest")`
- AND not completed `wod_left_behind`
- AND `quest/wod_left_behind` is not active

Conversation:

- Start branch `15`.
- `s_160` -> branch `16`.
- `s_174` -> sends cylinder signal, then branch `17`.
- `s_172` -> grants left-behind quest.

Triggers:

- `groundquests.sendSignal(player, "hasGivenCylinder")`
- `groundquests.grantQuest(player, "quest/wod_left_behind")`

Quest table:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_rubina_chest.tab`
- Wait task `giveCylinder`, completed by signal `hasGivenCylinder`.

### Left Behind / Cache Return

State check:

- `groundquests.isTaskActive(player, "wod_left_behind", "giveCache")`

Conversation:

- Start branch `19`.
- `s_186` -> branch `20`.
- `s_190` -> branch `21`.
- `s_194` -> completes return.

Trigger:

- `groundquests.sendSignal(player, "gaveCache")`

Quest table:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_left_behind.tab`
- Wait task `giveCache`, completed by signal `gaveCache`.

### Busy With Prologue Task

State check:

Any of these quests active:

- `quest/wod_prologue_kill_rancor`
- `quest/wod_prologue_kill_spider_clan`
- `quest/wod_prologue_herb_gathering`
- `quest/wod_outcast_1`
- `quest/wod_outcast_2`
- `quest/wod_outcast_3`
- `quest/wod_outcast_4`
- `quest/wod_outcast_5`
- `quest/wod_outcast_6`
- `quest/wod_outcast_7`

Conversation:

- Rubina chats `s_143`.
- No branch is opened.
- No trigger is fired.

### Generic Prologue Menu

State check:

- `quest/wod_prologue_walkabout_02` complete.

Side effects on entry:

- Initializes `wod_prologue_quests` to `0` if missing.
- Clamps `wod_prologue_quests` to `-8..8`.
- Clears completed repeatable prologue quests:
  - `quest/wod_prologue_kill_rancor`
  - `quest/wod_prologue_kill_spider_clan`
  - `quest/wod_prologue_herb_gathering`
  - `quest/wod_outcast_1` through `quest/wod_outcast_7`

Start branch:

- Branch `24`.

Top-level options:

- `s_68`: work/task menu.
- `s_104`: information menu.
- `s_154`: status / final referral check.

#### Work / Task Menu

Branch flow:

- `24:s_68` -> branch `25`.

Branch `25` options:

- `s_72`: asks for task details, goes to branch `26`.
- `s_88`: grants herb quest immediately.
- `s_92`: grants enemy quest immediately.
- `s_96`: grants wisdom quest immediately.

Quest grants:

- Herb:
  - `groundquests.grantQuest(player, "quest/wod_prologue_herb_gathering")`
- Enemy:
  - Random `rand(0, 100)`.
  - If `rng > 50`, grants `quest/wod_prologue_kill_rancor`.
  - Otherwise grants `quest/wod_prologue_kill_spider_clan`.
- Wisdom:
  - Grants `quest/wod_outcast_` plus random `1..7`.

Task detail sub-branches:

- `26:s_175` -> `27:s_181` -> `28:s_88` -> grants herb quest.
- `26:s_177` -> `30:s_183` -> `31:s_92` -> grants enemy quest.
- `26:s_179` -> `33:s_187` -> `34:s_96` -> grants wisdom quest.

#### Information Menu

Branch flow:

- `24:s_104` -> branch `36`.

Branch `36` options:

- `s_191`: more information submenu, goes to branch `37`.
- `s_130`: exits with Singing Mountain information.
- `s_134`: exits with Nightsister information.
- `s_139`: exits with Spider Clan information.
- `s_143`: exits with magic/Force information.
- `s_150`: exits with favor information.
- `s_163`: exits with clan opposition information.
- `s_166`: exits with changing-support information.

Submenu:

- `37:s_169` -> branch `38`.
- `38:s_118` -> exits with final informational response.

No quest triggers are fired in this information branch.

#### Status / Final Referral

Branch flow:

- `24:s_154` evaluates the current `wod_prologue_quests` score.

If `status > 7` and `wod_rubina_goto_ns` is not complete:

- Opens branch `47`.
- `47:s_127` grants `quest/wod_rubina_goto_ns`.
- `47:s_124` returns to branch `24` style menu without granting.

If `status < -7` and `wod_rubina_goto_sm` is not complete:

- Opens branch `50`.
- `50:s_123` grants `quest/wod_rubina_goto_sm`.
- `50:s_121` returns to branch `24` style menu without granting.

If not ready for a new goto quest, status-only responses end the conversation:

- `status < -7`: `SMFull`
- `status > 7`: `NSFull`
- `status < -4`: `SMMost`
- `status > 4`: `NSMost`
- `status < -1`: `SMSlight`
- `status > 1`: `NSSlight`
- `status == 1 || status == -1`: `oneQuestComplete`
- `status == 0`: `zeroQuestComplete`

Referral quest tables:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_rubina_goto_ns.tab`
  - Wait task `arrived`, completed by signal `arrivedNs`.
  - Target appears to be Dathomir `-4067 123 -113`, cell/path `default/r3`.
- `sys.shared/compiled/game/datatables/questtask/quest/wod_rubina_goto_sm.tab`
  - Wait task `arrived`, completed by signal `arrivedSm`.
  - Target appears to be Dathomir `158 432 4518`, cell/path `dathomir/guard`.

### Not Eligible Yet

State check:

- `!walkabout2Finished`

Conversation:

- Rubina chats `s_206`.
- No branch is opened.
- No trigger is fired.

## Dead / Duplicate Branch Handlers

The script contains handlers for branch IDs `49` and `52`, but no code path found in the script sets `conversation.wod_rubina.branchId` to `49` or `52`.

These look like duplicate generated copies of branch `24` / status-menu logic and should not be recreated unless later evidence shows they are reachable from another source.

## Conversation Text Problem

The generated Java references many string IDs that are missing from the live STF at:

- `/home/swg/swg-main/serverdata/string/en/conversation/wod_rubina.stf`

Before the repair pass, missing IDs included:

- `s_4`, `s_6`, `s_51`, `s_53`, `s_55`, `s_57`, `s_59`
- `s_61`, `s_62`, `s_63`, `s_64`, `s_65`, `s_67`, `s_69`, `s_71`
- `s_73`, `s_75`, `s_77`, `s_79`, `s_81`, `s_83`, `s_85`, `s_87`, `s_89`, `s_91`, `s_93`, `s_95`
- `s_108`, `s_110`, `s_112`, `s_114`, `s_116`, `s_126`, `s_128`, `s_136`, `s_138`, `s_140`, `s_142`, `s_146`, `s_149`
- `s_153`, `s_155`, `s_197`, `s_199`, `s_201`, `s_203`, `s_205`, `s_207`, `s_208`, `s_210`

This strongly suggests the conversation Java and string table were generated from different versions of Rubina's source.

The current script has been remapped so every string ID it emits or compares exists in the live STF. Some branch text is still best-effort where the STF only has generic clan-gain copy, especially the outcast wisdom turn-ins.

## Quest Checklist For Direct Repair

Rubina's script must preserve these quest operations:

- Send `speakWithRubina`.
- Grant `quest/wod_prologue_herb_gathering`.
- Grant either `quest/wod_prologue_kill_rancor` or `quest/wod_prologue_kill_spider_clan`.
- Grant one of `quest/wod_outcast_1` through `quest/wod_outcast_7`.
- Send `selectBasketNightsister`.
- Send `selectBasketSiging`.
- Send `waitForRubinaNightsisterRancor`.
- Send `waitForRubinaNightsisterSpider`.
- Send `waitForRubinaSingingRancor`.
- Send `waitForRubinaSingingSpider`.
- Send `wod_outcast_nightsister_win_2`.
- Send `wod_outcast_singing_win_2`.
- Maintain `wod_prologue_quests` score and collection slots for each return.
- Grant `quest/wod_rubina_goto_ns` when `wod_prologue_quests > 7`.
- Grant `quest/wod_rubina_goto_sm` when `wod_prologue_quests < -7`.
- Send `hasGivenCylinder`.
- Grant `quest/wod_left_behind`.
- Send `gaveCache`.
