package Lobster_Combatant.data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.listeners.CommodityIconProvider;

public class LobsterCombatant_Commodity_iconAndImageChanger implements CommodityIconProvider{
    @Override
    public int getHandlingPriority(Object params) {
            return 0;
        }
    @Override
    public String getIconName(CargoStackAPI stack) {
        if(stack == null) return null;
        if (!stack.getCommodityId().equals("LobsterCombatant_CombatLobster")) return null;
        if (stack.getSize() < 150) return null;
        //return Global.getSettings().getSpriteName("misc","LobsterCombatant_ExstraLobsters");
        return Global.getSettings().getSpriteName("systemMap","icon_jump_point");
    }

    public static final String commodity = "LobsterCombatant_CombatLobster";
    @Override
    public String getRankIconName(CargoStackAPI stack) {
        if(stack == null) return null;
        if (!stack.getCommodityId().equals("LobsterCombatant_CombatLobster")) return null;
        return Global.getSettings().getSpriteName("ui","icon_crew_elite");
    }
}

/*
        int[] thresholds = {25,50,75,100};
        String[] ranks = {"icon_crew_green","icon_crew_regular","icon_crew_veteran","icon_crew_elite"};
        for (int a = 0; a < thresholds.length; a++){
            if (thresholds[a] >= stack.getSize()){
                return Global.getSettings().getSpriteName("ui", ranks[a]);
            }
        }
* */