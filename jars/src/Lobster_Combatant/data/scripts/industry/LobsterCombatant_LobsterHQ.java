package Lobster_Combatant.data.scripts.industry;

import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;

public class LobsterCombatant_LobsterHQ extends BaseIndustry {
    @Override
    public void apply() {
        supply("LobsterCombatant_CombatLobster",market.getSize() - 1);
    }
}
