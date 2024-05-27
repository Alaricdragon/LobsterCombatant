package Lobster_Combatant.data.scripts.customCrews.listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.*;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.CrewReplacer_Log;

public class LobsterCombatant_CustomCrew_XP_cargoHandler implements ColonyInteractionListener,
        CommodityTooltipModifier,
        CommodityIconProvider,
        CargoScreenListener {
    public static final String commodity = "LobsterCombatant_CombatLobster";
    private final static String memoryKey = "$LobsterCombatant_LobsterXP";

    @Override
    public int getHandlingPriority(Object params) {
        return 0;
    }

    @Override
    public void reportCargoScreenOpened() {

    }

    @Override
    public void reportPlayerLeftCargoPods(SectorEntityToken entity) {

    }

    @Override
    public void reportSubmarketOpened(SubmarketAPI submarket) {

    }

    @Override
    public void reportPlayerOpenedMarket(MarketAPI market) {

    }

    @Override
    public void reportPlayerClosedMarket(MarketAPI market) {

    }

    @Override
    public void reportPlayerOpenedMarketAndCargoUpdated(MarketAPI market) {

    }


    @Override
    public String getIconName(CargoStackAPI stack) {
        return null;
    }


    @Override
    public void reportPlayerNonMarketTransaction(PlayerMarketTransaction transaction, InteractionDialogAPI dialog) {
        processTransaction();
    }

    @Override
    public void reportPlayerMarketTransaction(PlayerMarketTransaction transaction) {
        processTransaction();
    }

    @Override
    public String getRankIconName(CargoStackAPI stack) {
        if (stack == null || stack.getCargo() == null || stack.getCommodityId() == null || !stack.getCommodityId().equals(commodity) || stack.getCargo().getFleetData() == null || !stack.getCargo().getFleetData().getFleet().isPlayerFleet() || stack.isPickedUp())
            return null;
        int[] thresholds = {25, 50, 75, 100};
        String[] ranks = {"icon_crew_green", "icon_crew_regular", "icon_crew_veteran", "icon_crew_elite"};
        int xp = getEffectiveXPPercent();
        for (int a = 0; a < thresholds.length; a++) {
            if (thresholds[a] >= xp) {
                return Global.getSettings().getSpriteName("ui", ranks[a]);
            }
        }
        return Global.getSettings().getSpriteName("ui", ranks[0]);
    }

    @Override
    public void addSectionAfterPrice(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack) {
        if (stack == null || stack.getCargo() == null || stack.getCommodityId() == null || !stack.getCommodityId().equals(commodity) || stack.getCargo().getFleetData() == null || !stack.getCargo().getFleetData().getFleet().isPlayerFleet() || stack.isPickedUp())
            return;
        float xp = (float) Global.getSector().getMemory().getFloat(memoryKey);
        info.addPara("total XP is: %s", 3, Misc.getHighlightColor(), xp + "");
        info.addPara("current XP is: %s", 3, Misc.getHighlightColor(), getEffectiveXPPercent() + "%");
    }

    public void processTransaction() {
        float xp = Global.getSector().getMemory().getFloat(memoryKey);
        float commoditys = Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(commodity);
        xp = Math.min(xp, commoditys);
        Global.getSector().getMemory().set(memoryKey, xp);// * -ratio);
    }

    public static int getEffectiveXPPercent() {
        float xp = (float) Global.getSector().getMemory().getFloat(memoryKey);
        float commoditys = Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(commodity);
        return (int) Math.min(Math.floor((xp * 100) / commoditys), 100);
    }
}