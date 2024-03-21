package Lobster_Combatant.data.scripts.industry;

import com.fs.starfarer.api.impl.campaign.econ.impl.LionsGuardHQ;

public class LobsterCombatant_LionsGard extends LionsGuardHQ {
    @Override
    public void apply() {
        super.apply();
        supply("LobsterCombatant_CombatLobster",market.getSize() - 1);
    }
}
