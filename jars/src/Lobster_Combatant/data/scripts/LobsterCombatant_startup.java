package Lobster_Combatant.data.scripts;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

public class LobsterCombatant_startup extends BaseModPlugin {
    @Override
    public void onGameLoad(boolean newGame) {
        super.onGameLoad(newGame);
        Global.getSector().getListenerManager().addListener(new LobsterCombatant_CommodityTooltipModifier(),true);
    }
}