package Lobster_Combatant.data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.listeners.CommodityTooltipModifier;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class LobsterCombatant_CommodityTooltipModifier implements CommodityTooltipModifier {
    @Override
    public void addSectionAfterPrice(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack) {
        //protection
        if (stack.getCommodityId().equals("LobsterCombatant_CombatLobster")){
            //highlights
            Color highlight = Misc.getHighlightColor();
            info.addPara("going to highlight '%s', and '%s'", 5, highlight, "firstHighlight","secondHighlight");

            //F1 support
            if (expanded) {
                info.addPara("OH NO! NOT THE EXPANDED TEXT!!!",5);
            }
        }
    }
}
