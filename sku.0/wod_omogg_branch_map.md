# WOD Omogg Conversation Branch Map

Source script:

- `sys.server/compiled/game/script/conversation/wod_omogg_rep.java`
- String file used by script: `conversation/wod_omogg_rep`
- NPC type: `omogg_rep` in `sys.server/compiled/game/datatables/mob/creatures.tab`
- Spawn name: `omogg_rep` in `sys.server/compiled/game/datatables/buildout/dathomir/dathomir_5_6.tab`

This file maps Omogg's generated conversation flow and the two walkabout quests he controls. The script has inline dialogue comments, so the branch text below comes from the Java import.

## What Quest To Reset

Most Omogg blockers are one of these two quests:

- Reset `quest/wod_prologue_walkabout_01` if the player is on the first photo trip, cannot get Omogg to accept the first holograms, or Omogg only says "You still have work to do."
- Reset `quest/wod_prologue_walkabout_02` if the player already finished the first photo trip, accepted the second trip, cannot get Omogg to accept the second holograms, or is stuck before Rubina.

Quick state tells:

- If task `returnToRep01` is active, talk to Omogg should complete `wod_prologue_walkabout_01` by sending signal `returnToRep01`.
- If task `returnToRep02` is active, talk to Omogg should advance `wod_prologue_walkabout_02` by sending signal `returnToRep02`.
- If task `speakWithRubina` is active, Omogg is done; the next NPC is Rubina, and the quest involved is still `quest/wod_prologue_walkabout_02`.

## Start Priority

`OnStartNpcConversation` checks branches in this exact order. First true condition wins.

1. `canGrantQuest1`
2. `onReturn1`
3. `onReturn2`
4. `finishedQuest2`
5. `onQuests`
6. `finishedQuest1`
7. default fallback

The order matters: active return tasks are handled before the generic "still have work to do" response.

## State Checks

### canGrantQuest1

Condition:

- `!groundquests.isQuestActive(player, "wod_prologue_walkabout_01")`
- `!groundquests.hasCompletedQuest(player, "wod_prologue_walkabout_01")`

Effect:

- Starts the first job offer path.

### onReturn1

Condition:

- `groundquests.isTaskActive(player, "wod_prologue_walkabout_01", "returnToRep01")`

Effect:

- Allows Omogg to accept the first set of holograms.

### onReturn2

Condition:

- `groundquests.isTaskActive(player, "wod_prologue_walkabout_02", "returnToRep02")`

Effect:

- Allows Omogg to accept the second set of holograms.

### finishedQuest2

Condition:

- Looks up `quest/wod_prologue_walkabout_02`.
- Looks up task `returnToRep02`.
- Returns true when that task is complete for the player.

Important note:

- This check does not require the whole quest to be complete. After Omogg receives the second holograms, `wod_prologue_walkabout_02` continues to a Rubina step.

### onQuests

Condition:

- `groundquests.isQuestActive(player, "wod_prologue_walkabout_01")`
- OR `groundquests.isQuestActive(player, "wod_prologue_walkabout_02")`

Effect:

- Omogg says the player still has work to do.
- This catches active walkabout quests that are not currently on an Omogg return task.

### finishedQuest1

Condition:

- `groundquests.hasCompletedQuest(player, "wod_prologue_walkabout_01")`

Effect:

- Offers the second trip if `wod_prologue_walkabout_02` is not active.

## Branches

### First Job Offer

Start condition:

- `canGrantQuest1`

Conversation:

- Omogg: `s_19` - "Hey you! Are you interested in making some easy credits?"
- Player: `s_21` - "Easy credits? Excuse me if I'm a little skeptical."
- Omogg: `s_26` - "All I need is for you to take a quick trip out to a couple of scenic spots not too far away. I'm collecting pictures, sales material actually, for the planet. I need pictures that will make Dathomir look like a beautiful and peaceful planet."
- Player: `s_28` - "Dathomir? Beautiful? Peaceful?"
- Omogg: `s_30` - "It's just a little harmless marketing."

Accept:

- Player: `s_32` - "Why not? I'll take the job."
- Action: `groundquests.grantQuest(player, "wod_prologue_walkabout_01")`
- Omogg: `s_34` - "Perfect. Your first stop will be some local waterfalls."

Decline:

- Player: `s_38` - "I think I'll pass. Sounds like trouble."
- Omogg: `s_40` - "That's a shame. For some reason it's hard to find wildlife photographers here on Dathomir. I wonder why?"

Quest granted:

- `quest/wod_prologue_walkabout_01`

### Return From First Walkabout

Start condition:

- `onReturn1`
- Active task: `wod_prologue_walkabout_01:returnToRep01`

Conversation:

- Omogg: `s_6` - "You're back."
- Player: `s_12` - "I got the holograms."
- Action: `groundquests.sendSignal(player, "returnToRep01")`
- Omogg: `s_13` - "Great. I hope there'll be enough holograms without monsters, witches and other horrors to put together a good looking brochure."
- Player: `s_35` - "Well, good luck."
- Omogg: `s_36` - "Wait, I think I'll need some more pictures. Interested in a second trip?"

Accept second trip:

- Player: `s_44` - "Yeah, why not?"
- Action: `groundquests.grantQuest(player, "wod_prologue_walkabout_02")`
- Omogg: `s_46` - "Great. Your first stop will be northwest of here."

Decline second trip:

- Player: `s_48` - "Not right now, maybe later."
- Omogg: `s_50` - "Maybe later, then."

Quest completed by return:

- `quest/wod_prologue_walkabout_01`

Signal sent:

- `returnToRep01`

Optional quest granted:

- `quest/wod_prologue_walkabout_02`

### Offer Second Trip Later

Start condition:

- `finishedQuest1`
- Player has completed `wod_prologue_walkabout_01`.
- Player is not currently active on either walkabout quest.

Conversation:

- Omogg: `s_42` - "So are you ready for another little trip?"

Accept:

- Player: `s_44` - "Yeah, why not?"
- Action: `groundquests.grantQuest(player, "wod_prologue_walkabout_02")`
- Omogg: `s_46` - "Great. Your first stop will be northwest of here."

Decline:

- Player: `s_48` - "Not right now, maybe later."
- Omogg: `s_50` - "Maybe later, then."

Quest granted:

- `quest/wod_prologue_walkabout_02`

### Return From Second Walkabout

Start condition:

- `onReturn2`
- Active task: `wod_prologue_walkabout_02:returnToRep02`

Conversation:

- Omogg: `s_23` - "Welcome back."
- Player: `s_24` - "Here's the holograms I recorded."
- Action: `groundquests.sendSignal(player, "returnToRep02")`
- Omogg: `s_25` - "Not bad. We might be able to use some of these."

Quest advanced:

- `quest/wod_prologue_walkabout_02`

Signal sent:

- `returnToRep02`

Next quest step:

- `wod_prologue_walkabout_02` sends a comm message, then waits on `speakWithRubina`.
- Rubina completes that wait task with signal `speakWithRubina`.

### Finished Second Omogg Return

Start condition:

- `finishedQuest2`
- Specifically, task `returnToRep02` in `quest/wod_prologue_walkabout_02` is complete.

Conversation:

- Omogg: `s_4` - "Thank you for your work. I don't have anything else for you to do, I need to get back to Coronet and get these holograms edited."

Important note:

- If the whole quest is not complete yet, this state likely means the player should be talking to Rubina, not Omogg.

### Active But Not Ready

Start condition:

- `onQuests`
- Either walkabout quest is active, but the active task is not `returnToRep01` or `returnToRep02`.

Conversation:

- Omogg: `s_17` - "You still have work to do. Return here when you got the pictures."

Reset clue:

- If this repeats when the journal says to return to Omogg, inspect/reset the active walkabout quest whose return task should be active.

### Default Fallback

Start condition:

- None of the above matched.

Conversation:

- Omogg: `s_52` - "I don't have anything to talk to you about."

## Quest Task Tables

### `wod_prologue_walkabout_01`

Quest list:

- `sys.shared/compiled/game/datatables/questlist/quest/wod_prologue_walkabout_01.tab`

Task table:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_prologue_walkabout_01.tab`

Important tasks:

- Location: Dathomir `3055, 19, 1366`
- Comm: task name `repCom1`, Omogg appearance `object/mobile/dressed_wod_omogg_rep.iff`
- Location: Dathomir `4747, 115, 2399`
- Location: Dathomir `5702, 122, 1937`
- Wait for signal: task name `returnToRep01`, signal `returnToRep01`, waypoint near Omogg at Dathomir `552, 6, 3074`
- Complete quest
- Timer: `waitTen`
- Comm: task name `comm1`

### `wod_prologue_walkabout_02`

Quest list:

- `sys.shared/compiled/game/datatables/questlist/quest/wod_prologue_walkabout_02.tab`

Task table:

- `sys.shared/compiled/game/datatables/questtask/quest/wod_prologue_walkabout_02.tab`

Important tasks:

- Location: Dathomir `-800, 124, 4123`
- Location: Dathomir `-1800, 36, 5104`
- Location: Dathomir `-4128, 149, 115`
- Location: Dathomir `558, 6, 3073`
- Wait for signal: task name `returnToRep02`, signal `returnToRep02`
- Comm: task name `comm4`
- Wait for signal: task name `speakWithRubina`, signal `speakWithRubina`, waypoint near Rubina at Dathomir `-3177, 21, 3184`
- Complete quest

## NPC Placement

Buildout row:

- File: `sys.server/compiled/game/datatables/buildout/dathomir/dathomir_5_6.tab`
- Spawner name: `omogg_rep`
- Spawn type: `omogg_rep`
- Approximate location: Dathomir `552, 6, 1026`

Creature row:

- File: `sys.server/compiled/game/datatables/mob/creatures.tab`
- Creature: `omogg_rep`
- Level: `90`
- Mobile template: `dressed_wod_omogg_rep.iff`
- Conversation script: `conversation.wod_omogg_rep`

## Conversation Manager Implementation Outline

Goal:

- Rebuild Omogg as the first WOD table-driven conversation.
- Do not edit `sys.server/compiled/game/script/conversation/wod_omogg_rep.java`.
- Keep the current script available as the known-good reference while the manager version is tested separately.

Existing manager pieces:

- Runtime script: `sys.server/compiled/game/script/systems/conversation_manager/table_conversation.java`
- Loader: `sys.server/compiled/game/script/systems/conversation_manager/conversation_loader.java`
- Engine: `sys.server/compiled/game/script/systems/conversation_manager/conversation_engine.java`
- Condition handlers: `sys.server/compiled/game/script/systems/conversation_manager/conversation_conditions.java`
- Action handlers: `sys.server/compiled/game/script/systems/conversation_manager/conversation_actions.java`

Manager activation model:

- An NPC needs script `systems.conversation_manager.table_conversation`.
- The NPC needs objvar `conversation_manager.table`.
- The loader reads `datatables/conversation_manager/<table>.iff`.
- `conversationId` is currently the table name.
- String file comes from table column `stringFile`, or defaults to `conversation/<table>`.

For Omogg, the table should use:

- Table name: `wod_omogg_rep`
- String file: `conversation/wod_omogg_rep`
- Data source file to add: `sys.server/compiled/game/datatables/conversation_manager/wod_omogg_rep.tab`
- Runtime compiled path expected by loader: `datatables/conversation_manager/wod_omogg_rep.iff`

## Recommended Implementation Order

The safest order is to add only the generic manager support first, then add the Omogg table, then wire Omogg to the manager after the table can be inspected and tested.

1. Add the missing generic condition handlers.
   - Add `questNotActiveAndNotComplete`.
   - Add `anyQuestActive`.
   - Keep both handlers quest-agnostic so they are reusable by later WOD conversations.

2. Verify the existing manager behavior that Omogg depends on.
   - Confirm start nodes are evaluated in numeric priority order.
   - Confirm response action sets run before moving to the next node or ending.
   - Confirm `grantQuest`, `sendQuestSignal`, `taskActive`, `taskComplete`, `questComplete`, and `always` already match the Omogg table needs.

3. Add the Omogg conversation manager data table.
   - Add `sys.server/compiled/game/datatables/conversation_manager/wod_omogg_rep.tab`.
   - Define the seven start nodes in the exact priority order from the old script.
   - Define the node chain, response rows, and action sets from this map.
   - Use string file `conversation/wod_omogg_rep`.

4. Compile or convert the table into the runtime `.iff` form expected by the loader.
   - Expected runtime path: `datatables/conversation_manager/wod_omogg_rep.iff`.
   - Verify the loader can find the table by `conversation_manager.table = wod_omogg_rep`.

5. Add a test or temporary spawn wiring path before replacing the live Omogg script.
   - Use script `systems.conversation_manager.table_conversation`.
   - Set objvar `conversation_manager.table` to `wod_omogg_rep`.
   - Keep the original generated conversation script available as the reference behavior.

6. Test each start priority state.
   - Fresh player gets the first job offer.
   - `returnToRep01` active gets the first return branch.
   - `returnToRep02` active gets the second return branch.
   - Completed `returnToRep02` gets the finished second return text.
   - Active walkabout without an Omogg return task gets the "still have work to do" text.
   - Completed first walkabout but no second walkabout active gets the second trip offer.
   - No matching state gets the fallback text.

7. Test action effects against the ground quest tables.
   - Accepting the first offer grants `wod_prologue_walkabout_01`.
   - First return sends `returnToRep01`.
   - Accepting the second trip grants `wod_prologue_walkabout_02`.
   - Second return sends `returnToRep02`.
   - After `returnToRep02`, the quest advances to `speakWithRubina`.

8. Switch the live Omogg creature/buildout wiring only after the manager version matches the generated script.
   - Replace the conversation script reference with `systems.conversation_manager.table_conversation`.
   - Ensure `conversation_manager.table` is set for the live Omogg spawn or creature template.
   - Leave the generated Java script in place as historical reference unless there is a separate cleanup task.

## Debug/Test Conversation State

God-mode testing should be represented as normal table data instead of hard-coded Omogg logic.

Recommended additions:

- Add condition `isGod`.
  - Returns `isGod(ctx.player)`.
  - This is different from the existing `isGm` condition, which checks player objvar `gm`.
- Add high-priority test/debug start nodes guarded by `isGod`.
  - These should sort before the normal Omogg quest states.
  - This lets a tester reach reset/clear/grant/complete options regardless of the current quest state.
- Add generic quest testing actions as reusable manager actions.
  - `grantQuest`
  - `clearQuest`
  - `sendQuestSignal`
  - `completeQuest` if the ground quest library exposes a safe helper for it.
  - `resetQuest`, implemented as clear then grant, if that is the intended behavior.

Packed parameter shape:

- Keep a single packed parameter column for conditions and one for actions when possible.
- Use ordered pipe-separated values for fixed parameters.
- Use comma-separated values inside one ordered value when the parameter itself is a list.

Examples:

| Type | Name | Packed params | Meaning |
| --- | --- | --- | --- |
| Condition | `taskActive` | `wod_prologue_walkabout_01|returnToRep01` | Quest plus task |
| Condition | `anyQuestActive` | `wod_prologue_walkabout_01,wod_prologue_walkabout_02` | Quest list |
| Action | `grantQuest` | `wod_prologue_walkabout_01` | Grant quest |
| Action | `sendQuestSignal` | `returnToRep01` | Send signal |
| Action | `resetQuest` | `wod_prologue_walkabout_01` | Clear then grant quest |

Conversation state tracking:

- When the manager chooses a start row, save the selected row priority and/or node id on the player as a script var.
- The current node id is still the main state for response handling.
- Saving the priority is useful when multiple rows point at the same node shape but represent different resolved states.
- Clear the saved priority and current node script vars when the conversation ends.
- Do not persist this state as an objvar; it only needs to live for the active conversation session.

### Manager Gaps To Add

Omogg cannot be represented perfectly with the current generic condition set.

Needed condition types:

- `questNotActiveAndNotComplete`
  - `arg1`: quest name, for example `wod_prologue_walkabout_01`
  - Returns true when the quest is neither active nor completed.
  - Used for Omogg's `canGrantQuest1`.
- `anyQuestActive`
  - `arg1`: comma-separated quest names, for example `wod_prologue_walkabout_01,wod_prologue_walkabout_02`
  - Returns true when any listed quest is active.
  - Used for Omogg's generic active quest blocker.

Existing condition types that Omogg can already use:

- `taskActive`
  - Used for `returnToRep01` and `returnToRep02`.
- `taskComplete`
  - Used for the `finishedQuest2` behavior, checking `wod_prologue_walkabout_02:returnToRep02`.
- `questComplete`
  - Used for the `finishedQuest1` behavior.
- `always`
  - Used for normal responses and fallback nodes.

Existing action types that Omogg can already use:

- `grantQuest`
  - Grants `wod_prologue_walkabout_01` or `wod_prologue_walkabout_02`.
- `sendQuestSignal`
  - Sends `returnToRep01` or `returnToRep02`.
- `facePlayer`
  - Turns the NPC toward the player when the conversation begins or when a configured node/action needs it.

### Table Start Nodes

The table should define these start nodes, with priority matching the old script order:

| Priority | Node ID | Condition | Arg 1 | Arg 2 | Text |
| --- | --- | --- | --- | --- | --- |
| 1 | `offer_first` | `questNotActiveAndNotComplete` | `wod_prologue_walkabout_01` | | `s_19` |
| 2 | `return_first` | `taskActive` | `wod_prologue_walkabout_01` | `returnToRep01` | `s_6` |
| 3 | `return_second` | `taskActive` | `wod_prologue_walkabout_02` | `returnToRep02` | `s_23` |
| 4 | `finished_second_return` | `taskComplete` | `wod_prologue_walkabout_02` | `returnToRep02` | `s_4` |
| 5 | `active_not_ready` | `anyQuestActive` | `wod_prologue_walkabout_01,wod_prologue_walkabout_02` | | `s_17` |
| 6 | `offer_second_later` | `questComplete` | `wod_prologue_walkabout_01` | | `s_42` |
| 7 | `fallback` | `always` | | | `s_52` |

End-node starts:

- `finished_second_return`
- `active_not_ready`
- `fallback`

Interactive starts:

- `offer_first`
- `return_first`
- `return_second`
- `offer_second_later`

### Table Node Chain

First offer chain:

- `offer_first` uses text `s_19`.
- Response `s_21` goes to node `first_offer_detail`.
- `first_offer_detail` uses text `s_26`.
- Response `s_28` goes to node `first_offer_marketing`.
- `first_offer_marketing` uses text `s_30`.
- Response `s_32` runs action set `grant_first` and ends with `s_34`.
- Response `s_38` ends with `s_40`.

First return chain:

- `return_first` uses text `s_6`.
- Response `s_12` runs action set `signal_return_first` and goes to node `first_return_received`.
- `first_return_received` uses text `s_13`.
- Response `s_35` goes to node `ask_second_trip`.
- `ask_second_trip` uses text `s_36`.
- Response `s_44` runs action set `grant_second` and ends with `s_46`.
- Response `s_48` ends with `s_50`.

Second trip later chain:

- `offer_second_later` uses text `s_42`.
- Response `s_44` runs action set `grant_second` and ends with `s_46`.
- Response `s_48` ends with `s_50`.

Second return chain:

- `return_second` uses text `s_23`.
- Response `s_24` runs action set `signal_return_second` and ends with `s_25`.

### Table Action Sets

| Action set | Order | Type | Arg 1 |
| --- | --- | --- | --- |
| `grant_first` | 0 | `grantQuest` | `wod_prologue_walkabout_01` |
| `grant_second` | 0 | `grantQuest` | `wod_prologue_walkabout_02` |
| `signal_return_first` | 0 | `sendQuestSignal` | `returnToRep01` |
| `signal_return_second` | 0 | `sendQuestSignal` | `returnToRep02` |

### What The Manager Must Do

Omogg is a good first conversion because the conversation manager does not need to run the actual walkabout. The ground quest task tables already handle movement, waypoints, comm messages, and task progression. The manager only needs to choose the right conversation node and fire quest grants or signals.

Manager responsibilities:

1. Load the Omogg table from `conversation_manager.table`.
2. Evaluate start nodes in priority order.
3. Show Omogg's current NPC text from `conversation/wod_omogg_rep`.
4. Show only the responses valid for that node.
5. Track the current node on the player while the conversation is open.
6. On response selection, execute the response action set.
7. Move to the next node or end the conversation with the configured end text.
8. Clear the player current-node script var when the conversation ends.

Ground quest responsibilities:

1. After `grantQuest wod_prologue_walkabout_01`, create the first walkabout path.
2. Move the player through the first set of Dathomir locations.
3. Send the configured comm messages, including Omogg's first comm and Rubina/witch messages.
4. Activate wait task `returnToRep01` when the player should return to Omogg.
5. Complete the first walkabout when the manager sends signal `returnToRep01`.
6. After `grantQuest wod_prologue_walkabout_02`, create the second walkabout path.
7. Move the player through the second set of Dathomir locations.
8. Activate wait task `returnToRep02` when the player should return to Omogg.
9. Advance the second walkabout when the manager sends signal `returnToRep02`.
10. Send the next comm message and activate wait task `speakWithRubina`.
11. Complete the quest when Rubina sends signal `speakWithRubina`.

Omogg-specific runtime flow:

1. Player starts conversation with Omogg.
2. Manager loads `wod_omogg_rep`.
3. Manager checks whether the player can receive `wod_prologue_walkabout_01`.
4. If yes, manager runs the first offer branch and grants `wod_prologue_walkabout_01` on acceptance.
5. Player leaves Omogg and the quest table moves them through the photo locations.
6. When `returnToRep01` is active, manager runs the first return branch.
7. On "I got the holograms", manager sends signal `returnToRep01`.
8. Manager then offers `wod_prologue_walkabout_02` immediately.
9. If the player accepts, manager grants `wod_prologue_walkabout_02`.
10. Player leaves Omogg again and the quest table moves them through the second photo route.
11. When `returnToRep02` is active, manager runs the second return branch.
12. On "Here's the holograms I recorded", manager sends signal `returnToRep02`.
13. The quest table advances to the Rubina leg and activates `speakWithRubina`.
14. Further Omogg conversations use the finished/redirect text while Rubina owns the next step.

What the manager does not need for Omogg:

- It does not need to create waypoints.
- It does not need to detect player location.
- It does not need to send the travel comm messages.
- It does not need special Rubina logic.
- It does not need to modify `wod_prologue_quests`.
- It does not need custom Java for Omogg if the two generic condition gaps are added.

### Repeating Quest Conversation Pattern

Omogg shows the basic pattern the manager needs to support for longer quest chains.

For each quest in a chain, the manager repeats this state sequence:

1. Player has not started the quest:
   - Offer the quest.
   - On accept response, run `grantQuest`.
   - End with the NPC's "go do the thing" text.
2. Player has the quest active and is on the NPC return task:
   - Show the return conversation.
   - On return response, run `sendQuestSignal`.
   - Continue to the next node or next quest offer.
3. Player has the quest active but is not on the NPC return task:
   - Show the "still have work to do" text.
   - End conversation.
4. Player has completed the quest:
   - Skip that quest's offer and return branches.
   - Continue evaluating the next quest in the chain.
5. Player has completed every quest in the chain:
   - Show the NPC's terminal message.
   - For Omogg this is `s_4` after the second return task is complete, or `s_52` when there is truly no matching state.

For Omogg specifically:

1. `wod_prologue_walkabout_01` not started: offer first walkabout.
2. `wod_prologue_walkabout_01:returnToRep01` active: send `returnToRep01`.
3. `wod_prologue_walkabout_01` complete: evaluate second walkabout.
4. `wod_prologue_walkabout_02` not started: offer second walkabout.
5. `wod_prologue_walkabout_02:returnToRep02` active: send `returnToRep02`.
6. `wod_prologue_walkabout_02:returnToRep02` complete: show Omogg's finished message.
7. No state matches: show "I don't have anything to talk to you about."

This can scale to Rubina and the other WOD prologue NPCs as long as each state is represented as a prioritized start node with data-driven conditions and response action sets.

### Standard Conversation Actions

The manager should treat facing the player as part of the normal conversation startup behavior.

Current support:

- `table_conversation.OnObjectMenuRequest` already calls `faceTo(self, player)`.
- `conversation_actions` already supports action type `facePlayer`.

Recommended table convention:

- Add a common enter action set such as `start_face_player`.
- Put `facePlayer` in that action set.
- Use it on interactive start nodes if we want table-visible behavior.

Example action row:

| Action set | Order | Type | Arg 1 |
| --- | --- | --- | --- |
| `start_face_player` | 0 | `facePlayer` | |

For Omogg this is optional because the runtime script already faces the NPC on object menu request, but including it in the table makes the behavior explicit and reusable.

## Datatable-First Prototype

Prototype goal:

- Express Omogg almost entirely as data.
- Keep conversation scripts generic.
- Add new conversation manager condition/action primitives only when a reusable table primitive is missing.
- Do not write Omogg-specific Java unless the generic manager cannot reasonably express the behavior.

### Prototype Files

Manager code already exists here:

- `sys.server/compiled/game/script/systems/conversation_manager/table_conversation.java`
- `sys.server/compiled/game/script/systems/conversation_manager/conversation_loader.java`
- `sys.server/compiled/game/script/systems/conversation_manager/conversation_engine.java`
- `sys.server/compiled/game/script/systems/conversation_manager/conversation_conditions.java`
- `sys.server/compiled/game/script/systems/conversation_manager/conversation_actions.java`

Prototype data file to add:

- `sys.server/compiled/game/datatables/conversation_manager/wod_omogg_rep.tab`

Runtime table path expected by the loader:

- `datatables/conversation_manager/wod_omogg_rep.iff`

Test NPC setup:

- Attach script `systems.conversation_manager.table_conversation`.
- Set objvar `conversation_manager.table` to `wod_omogg_rep`.
- Optionally set objvar `conversation_manager.debug` to `true`.

### Existing Table Schema

The current loader expects one flat table with three row types mixed together:

- Node rows, identified by non-empty `nodeId`.
- Response rows, identified by non-empty `responseId`.
- Action rows, identified by non-empty `actionSetName`.

Node columns:

| Column | Purpose |
| --- | --- |
| `stringFile` | String file for text keys. Usually only needs to be set on the first row. |
| `nodeId` | Unique node name. |
| `nodeText` | NPC text key, for example `s_19`. |
| `nodeCondition` | Condition type for this node. |
| `conditionArg1` | First condition argument. |
| `conditionArg2` | Second condition argument. |
| `priority` | Lower number wins among valid start nodes. |
| `startNode` | `1` if this node can start a conversation. |
| `endNode` | `1` if entering this node immediately ends the conversation with `nodeText`. |
| `enterActionSet` | Optional action set run when entering this node. |

Response columns:

| Column | Purpose |
| --- | --- |
| `responseId` | Unique response row id. |
| `responseNode` | Node this response belongs to. |
| `responseText` | Player response text key. |
| `responseCondition` | Optional response condition type. |
| `responseConditionArg1` | First response condition argument. |
| `responseConditionArg2` | Second response condition argument. |
| `actionSet` | Optional action set run when this response is selected. |
| `nextNode` | Node to enter after actions run. |
| `endConversation` | `1` if this response ends the conversation. |
| `endText` | NPC text key shown when ending from this response. |

Action columns:

| Column | Purpose |
| --- | --- |
| `actionSetName` | Name referenced by a node or response. |
| `actionOrder` | Execution order within the action set. |
| `actionType` | Generic action type. |
| `actionArg1` | First action argument. |
| `actionArg2` | Second action argument. |
| `actionArg3` | Third action argument. |

### Prototype Manager Additions

The current manager can already express grants, quest signals, task checks, completed quest checks, and facing. The prototype needs only small generic additions.

Add condition `questNotActiveAndNotComplete`:

- `arg1`: quest name.
- Returns true when `groundquests.isQuestActive(player, arg1)` is false and `groundquests.hasCompletedQuest(player, arg1)` is false.
- Used by Omogg to offer `wod_prologue_walkabout_01`.
- Reusable for any first-time quest offer.

Add condition `anyQuestActive`:

- `arg1`: comma-separated quest names.
- Returns true if any listed quest is active.
- Used by Omogg for active walkabout blocker.
- Reusable when one NPC owns a small quest chain.

Optional future condition `questNotActive`:

- `arg1`: quest name.
- Returns true when the quest is not active, regardless of completion.
- Useful only if a later NPC needs completed quests handled elsewhere.
- Omogg does not need this if start-node priority uses `questComplete` and `questNotActiveAndNotComplete`.

Optional future condition `allQuestsComplete`:

- `arg1`: comma-separated quest names.
- Returns true if all listed quests are complete.
- Omogg can avoid this by checking `taskComplete(wod_prologue_walkabout_02, returnToRep02)`, but Rubina-style chains may eventually want it.

No Omogg-specific action is needed.

Existing actions used by prototype:

- `grantQuest`
- `sendQuestSignal`
- `facePlayer`

### Prototype Omogg Table Rows

Header sketch:

```text
stringFile	nodeId	nodeText	nodeCondition	conditionArg1	conditionArg2	priority	startNode	endNode	enterActionSet	responseId	responseNode	responseText	responseCondition	responseConditionArg1	responseConditionArg2	actionSet	nextNode	endConversation	endText	actionSetName	actionOrder	actionType	actionArg1	actionArg2	actionArg3
```

Node rows:

```text
conversation/wod_omogg_rep	offer_first	s_19	questNotActiveAndNotComplete	wod_prologue_walkabout_01		1	1	0	start_face_player									
		return_first	s_6	taskActive	wod_prologue_walkabout_01	returnToRep01	2	1	0	start_face_player									
		return_second	s_23	taskActive	wod_prologue_walkabout_02	returnToRep02	3	1	0	start_face_player									
		finished_second_return	s_4	taskComplete	wod_prologue_walkabout_02	returnToRep02	4	1	1	start_face_player									
		active_not_ready	s_17	anyQuestActive	wod_prologue_walkabout_01,wod_prologue_walkabout_02		5	1	1	start_face_player									
		offer_second_later	s_42	questComplete	wod_prologue_walkabout_01		6	1	0	start_face_player									
		fallback	s_52	always			7	1	1	start_face_player									
		first_offer_detail	s_26	always			0	0	0										
		first_offer_marketing	s_30	always			0	0	0										
		first_return_received	s_13	always			0	0	0										
		ask_second_trip	s_36	always			0	0	0										
```

Response rows:

```text
										r_offer_skeptical	offer_first	s_21				first_offer_detail	0				
										r_offer_detail	first_offer_detail	s_28				first_offer_marketing	0				
										r_accept_first	first_offer_marketing	s_32			grant_first		1	s_34			
										r_decline_first	first_offer_marketing	s_38					1	s_40			
										r_return_first	return_first	s_12			signal_return_first	first_return_received	0				
										r_good_luck	first_return_received	s_35				ask_second_trip	0				
										r_accept_second_now	ask_second_trip	s_44			grant_second		1	s_46			
										r_decline_second_now	ask_second_trip	s_48					1	s_50			
										r_accept_second_later	offer_second_later	s_44			grant_second		1	s_46			
										r_decline_second_later	offer_second_later	s_48					1	s_50			
										r_return_second	return_second	s_24			signal_return_second		1	s_25			
```

Action rows:

```text
																				start_face_player	0	facePlayer			
																				grant_first	0	grantQuest	wod_prologue_walkabout_01		
																				grant_second	0	grantQuest	wod_prologue_walkabout_02		
																				signal_return_first	0	sendQuestSignal	returnToRep01		
																				signal_return_second	0	sendQuestSignal	returnToRep02		
```

The exact tab spacing can be adjusted when creating the `.tab`; the important part is that each row type fills only the columns it owns.

### Prototype Execution Model

Start conversation:

1. `table_conversation.OnStartNpcConversation` loads `wod_omogg_rep`.
2. `conversation_engine.findStartNode` evaluates every start node.
3. The valid start node with the lowest priority wins.
4. `enterNode` runs the node's `enterActionSet`.
5. `enterNode` displays NPC text and valid responses.

Select response:

1. `table_conversation.OnNpcConversationResponse` reloads `wod_omogg_rep`.
2. Engine reads current node from player script var.
3. Engine finds the selected response by text key and current node.
4. Engine runs the response's `actionSet`.
5. If `endConversation` is set, engine ends with `endText`.
6. Otherwise engine enters `nextNode`.

This gives Omogg the behavior we want:

- Accept quest response grants the quest and ends with the next Omogg line.
- Return response sends the quest signal and continues to Omogg's next node.
- Terminal start nodes end immediately with their configured NPC text.

### Prototype Startup Pipeline

The current manager chooses a start node by scanning all `startNode` rows and taking the valid row with the lowest priority. That works for Omogg, but the same behavior can also be described as a fall-through startup pipeline.

This is the desired data-driven startup model:

1. Run startup actions whose conditions pass.
2. Evaluate ordered state checks.
3. If a state check handles the conversation, stop falling through.
4. If no previous state handled it, start the offer conversation.
5. Store the response mapping for the current node.
6. Start the NPC conversation.
7. On player response, run the mapped response action.

For Omogg's first quest, the pipeline is:

1. Startup action:
   - Condition: `always`
   - Action: `facePlayer`
   - Fall through: yes
2. Active quest check:
   - Condition: `questActive(wod_prologue_walkabout_01)`
   - If the active task is `returnToRep01`, speak the return message and start the return response mapping.
   - If the active task is not `returnToRep01`, speak `s_17` and stop.
3. Completed quest check:
   - Condition: `questComplete(wod_prologue_walkabout_01)`
   - Do not offer the first quest again.
   - Continue to the second quest state checks.
4. Not-started fallback:
   - If the player is not active on the quest and has not completed it, set message `s_19`.
   - Add response mapping `s_21 -> first_offer_detail`.
   - Start the conversation.
5. Player selects response:
   - The response mapping advances to the next node.
   - The final accept response runs `grantQuest wod_prologue_walkabout_01`.

For the first offer branch, the visible conversation still has the same generated-script chain:

1. Omogg starts with `s_19`.
2. Player selects `s_21`.
3. Omogg speaks `s_26`.
4. Player selects `s_28`.
5. Omogg speaks `s_30`.
6. Player selects accept response `s_32`.
7. Manager runs `grantQuest wod_prologue_walkabout_01`.
8. Manager ends with `s_34`.

This means "stop conversing" can be data-driven:

- If a response has `endConversation = 1`, the manager ends after running the response action.
- If a response has `nextNode`, the manager continues to that node.
- If an end node has `endNode = 1`, entering that node immediately displays its message and stops.

### Startup Pipeline Table Shape

The existing start-node table can represent most of this, but a clearer prototype could split startup rules from conversation nodes.

Possible future startup rule columns:

| Column | Purpose |
| --- | --- |
| `startupRuleId` | Unique startup rule name. |
| `startupOrder` | Lower number runs first. |
| `startupCondition` | Condition type. |
| `startupConditionArg1` | First condition argument. |
| `startupConditionArg2` | Second condition argument. |
| `startupActionSet` | Action set to run if condition passes. |
| `startupNode` | Conversation node to enter if this rule handles the conversation. |
| `startupEndText` | Text key to speak if this rule ends immediately. |
| `continueStartup` | `1` to fall through after actions; `0` to stop when handled. |

Omogg first quest as startup rules:

| Order | Rule | Condition | Arg 1 | Arg 2 | Action set | Node | End text | Continue |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | `face_player` | `always` | | | `start_face_player` | | | `1` |
| 2 | `return_first` | `taskActive` | `wod_prologue_walkabout_01` | `returnToRep01` | | `return_first` | | `0` |
| 3 | `active_first_not_ready` | `questActive` | `wod_prologue_walkabout_01` | | | | `s_17` | `0` |
| 4 | `first_complete` | `questComplete` | `wod_prologue_walkabout_01` | | | `continue_second_quest_checks` | | `0` |
| 5 | `offer_first` | `always` | | | | `offer_first` | | `0` |

Current-manager equivalent:

- Use `enterActionSet = start_face_player` on start nodes.
- Put `taskActive(wod_prologue_walkabout_01, returnToRep01)` before generic `questActive(wod_prologue_walkabout_01)`.
- Put `questComplete(wod_prologue_walkabout_01)` after active checks.
- Put `questNotActiveAndNotComplete(wod_prologue_walkabout_01)` on `offer_first`.

The startup-rule model is more explicit about fall-through. The current start-node priority model is simpler and probably enough for Omogg. If Rubina or later WOD NPCs need ordered action/check/action/check behavior, adding startup rules to the manager would be a reasonable generic upgrade.

### Override Rules

Use data rows first.

Change the generic manager when:

- A condition or action is useful beyond one NPC.
- The behavior can be expressed with simple string arguments.
- The behavior does not require NPC-specific quest names hardcoded in Java.

Create an NPC-specific override only when:

- The branch depends on complex custom code that cannot be represented as generic condition/action primitives.
- The action needs custom sequencing that the action table cannot express.
- The conversation needs side effects involving several systems with custom failure handling.

For Omogg, no NPC-specific override should be needed.

### Prototype Acceptance Criteria

The prototype is successful when:

- A test Omogg using only `table_conversation` matches the old generated script's visible branches.
- The first offer grants `wod_prologue_walkabout_01`.
- The first return response sends `returnToRep01` and continues to the second-trip offer.
- The second offer grants `wod_prologue_walkabout_02`.
- The second return response sends `returnToRep02` and ends with `s_25`.
- Active-but-not-ready states show `s_17`.
- Finished state shows `s_4`.
- Fallback state shows `s_52`.
- The original `conversation.wod_omogg_rep` script remains unchanged during prototype testing.

### Manager Step Breakdown

This is the concrete implementation sequence for the table-driven Omogg pass.

### First Quest State Handling

The first manager decision for Omogg is the player's state for `wod_prologue_walkabout_01`.

For manager purposes, this quest has three meaningful states:

1. Player does not have and has not completed `wod_prologue_walkabout_01`.
2. Player has `wod_prologue_walkabout_01` active.
3. Player has completed `wod_prologue_walkabout_01`.

The generated script encodes that as:

- Offer first quest:
  - `!groundquests.isQuestActive(player, "wod_prologue_walkabout_01")`
  - AND `!groundquests.hasCompletedQuest(player, "wod_prologue_walkabout_01")`
- Handle active first quest return:
  - `groundquests.isTaskActive(player, "wod_prologue_walkabout_01", "returnToRep01")`
- Handle active first quest but not ready:
  - `groundquests.isQuestActive(player, "wod_prologue_walkabout_01")`
  - AND return task `returnToRep01` is not active.
- Move to next case:
  - `groundquests.hasCompletedQuest(player, "wod_prologue_walkabout_01")`

The manager should determine this through start-node priority, not through hardcoded nested branches.

Recommended start-node order for the first quest:

| Priority | Meaning | Condition | Result |
| --- | --- | --- | --- |
| 1 | First quest not started | `questNotActiveAndNotComplete(wod_prologue_walkabout_01)` | Start first offer branch |
| 2 | First quest is ready to turn in | `taskActive(wod_prologue_walkabout_01, returnToRep01)` | Start first return branch |
| 3 | First quest active, not ready | `questActive(wod_prologue_walkabout_01)` | Show "still have work to do" |
| 4 | First quest complete | `questComplete(wod_prologue_walkabout_01)` | Continue checking the second quest cases |

Important detail:

- `taskActive(wod_prologue_walkabout_01, returnToRep01)` must be checked before the generic `questActive(wod_prologue_walkabout_01)` blocker.
- Otherwise, a player who is correctly ready to return would be caught by the generic active-quest response and Omogg would never send `returnToRep01`.

For the first quest, the manager's behavior should be:

1. If `questNotActiveAndNotComplete(wod_prologue_walkabout_01)` is true:
   - Show the first offer conversation.
   - On acceptance, run `grantQuest wod_prologue_walkabout_01`.
   - End with Omogg's first destination text.
2. Else if `taskActive(wod_prologue_walkabout_01, returnToRep01)` is true:
   - Show the first return conversation.
   - On the hologram response, run `sendQuestSignal returnToRep01`.
   - Continue into the "second trip?" offer node.
3. Else if `questActive(wod_prologue_walkabout_01)` is true:
   - Show `s_17`, the "still have work to do" text.
   - End conversation.
4. Else if `questComplete(wod_prologue_walkabout_01)` is true:
   - Do not offer the first quest again.
   - Continue evaluating `wod_prologue_walkabout_02`.

The fourth state is not an end by itself. It is the bridge into the next state machine:

1. Is `wod_prologue_walkabout_02:returnToRep02` active?
2. Has `wod_prologue_walkabout_02:returnToRep02` already completed?
3. Is `wod_prologue_walkabout_02` active but not ready?
4. Has the second quest not been accepted yet, so Omogg can offer it?

This keeps Omogg's flow readable as two small quest-state machines instead of one large branch tangle.

### First Quest Task Ownership

The first walkabout quest table owns the travel loop.

`wod_prologue_walkabout_01` task flow:

1. Go to Dathomir location `3055, 19, 1366`.
2. On arrival, comm message `repCom1` triggers.
3. Go to Dathomir location `4747, 115, 2399`.
4. On arrival, comm message `comm2` triggers.
5. Go to Dathomir location `5702, 122, 1937`.
6. On arrival, comm message `comm3` triggers.
7. Activate wait task `returnToRep01`.
8. Player returns to Omogg.
9. Omogg conversation response sends signal `returnToRep01`.
10. Quest completes.
11. Timer task `waitTen` runs for 10 seconds.
12. Follow-up comm message `comm1` triggers.

The conversation manager only participates at step 9.

Manager action timing for the return response:

1. Player selects response `s_12`: "I got the holograms."
2. Manager executes action set `signal_return_first`.
3. `signal_return_first` calls `groundquests.sendSignal(player, "returnToRep01")`.
4. The quest system receives the signal and completes the wait task.
5. Manager continues the conversation to node `first_return_received`.
6. Omogg says `s_13`.
7. Player can continue to the second-trip offer.

This means one selected response can do both things we need:

- Gameplay effect: send the quest signal.
- Conversation effect: advance Omogg to his next line or end text.

For Omogg, this should be modeled as a response row with:

| Response | Node | Action set | Next node | End text |
| --- | --- | --- | --- | --- |
| `s_12` | `return_first` | `signal_return_first` | `first_return_received` | |

And an action row:

| Action set | Order | Type | Arg 1 |
| --- | --- | --- | --- |
| `signal_return_first` | 0 | `sendQuestSignal` | `returnToRep01` |

The second walkabout uses the same pattern:

- Player selects response `s_24`: "Here's the holograms I recorded."
- Manager executes `signal_return_second`.
- `signal_return_second` sends `returnToRep02`.
- Manager ends with Omogg text `s_25`.
- Quest system advances to the Rubina wait task.

Step 1: Add missing generic conditions.

- Add `questNotActiveAndNotComplete`.
- Add `anyQuestActive`.
- Keep both generic, with no Omogg-specific quest names in Java.

Step 2: Add the Omogg conversation table.

- Add a source table at `sys.server/compiled/game/datatables/conversation_manager/wod_omogg_rep.tab`.
- Include node rows, response rows, and action rows in the columns already expected by `conversation_loader`.
- Set `stringFile` to `conversation/wod_omogg_rep`.

Step 3: Add a test NPC path.

- Use `systems.conversation_manager.table_conversation`.
- Set objvar `conversation_manager.table = wod_omogg_rep`.
- Keep live `omogg_rep` using `conversation.wod_omogg_rep` until testing passes.

Step 4: Validate first quest grant.

- No active or completed `wod_prologue_walkabout_01`.
- Start node should be `offer_first`.
- Accept response should execute `grant_first`.
- Player should receive `wod_prologue_walkabout_01`.

Step 5: Validate first return.

- Put player on active task `wod_prologue_walkabout_01:returnToRep01`.
- Start node should be `return_first`.
- Response `s_12` should execute `signal_return_first`.
- Quest should complete or advance exactly like old Omogg.
- Second trip offer should appear in the same conversation chain.

Step 6: Validate second quest grant.

- Accept response `s_44`.
- Action set `grant_second` should grant `wod_prologue_walkabout_02`.

Step 7: Validate delayed second offer.

- Player has completed `wod_prologue_walkabout_01`.
- Player is not active on either walkabout quest.
- Start node should be `offer_second_later`.
- Accepting should grant `wod_prologue_walkabout_02`.

Step 8: Validate second return.

- Put player on active task `wod_prologue_walkabout_02:returnToRep02`.
- Start node should be `return_second`.
- Response `s_24` should execute `signal_return_second`.
- Quest should advance to the Rubina wait task.

Step 9: Validate active blockers.

- Player active on either walkabout, but not on `returnToRep01` or `returnToRep02`.
- Start node should be `active_not_ready`.
- Omogg should only show `s_17`.

Step 10: Validate post-Omogg state.

- Player has completed `returnToRep02`.
- Start node should be `finished_second_return`.
- Omogg should show `s_4`.
- If the player still has `speakWithRubina`, the fix is not Omogg; continue with Rubina.

### Safe Test Path

Use a clone or test spawn of Omogg first:

- Keep the live creature row on `conversation.wod_omogg_rep`.
- Create a separate test NPC or temporary spawn using `systems.conversation_manager.table_conversation`.
- Set objvar `conversation_manager.table` to `wod_omogg_rep`.
- Enable optional objvar `conversation_manager.debug` on the test NPC while validating branch selection.

Validation checklist:

- New player gets first offer and accepting grants `wod_prologue_walkabout_01`.
- Player on `returnToRep01` gets the first return path and signal `returnToRep01`.
- Player can accept the second trip immediately after first return.
- Player who declined the second trip can get `offer_second_later`.
- Player on `returnToRep02` gets the second return path and signal `returnToRep02`.
- Player past `returnToRep02` gets Omogg's finished text and should proceed to Rubina.
- Player active on either walkabout but not on a return task gets the "still have work to do" text.

Only after the test spawn matches the old behavior should the live Omogg attachment be switched from the old generated script to the manager script.
