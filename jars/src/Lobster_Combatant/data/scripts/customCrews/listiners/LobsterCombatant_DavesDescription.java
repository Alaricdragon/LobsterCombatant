package Lobster_Combatant.data.scripts.customCrews.listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.listeners.CommodityTooltipModifier;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class LobsterCombatant_DavesDescription implements CommodityTooltipModifier {
    public String memory = "$LobsterCombatant_DavesHPPercent";
    public String HPGainedMemory = "$LobsterCombatant_TimeSinceDaveHealed";
    public String crew = "LobsterCombatant_Dave";
    @Override
    public void addSectionAfterPrice(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack) {
        if (!stack.getCommodityId().equals(crew)) return;
        attemptHealing();
        info.addPara("daves currently at %s HP",3, Misc.getHighlightColor(),(int)(Global.getSector().getMemory().getFloat(memory)*100)+"%");
    }
    public void attemptHealing(){
        float hpTemp = Global.getSector().getMemory().getFloat(memory);
        if (hpTemp == 1 || hpTemp == 0){
            Global.getSector().getMemory().set(HPGainedMemory,Global.getSector().getClock().getTimestamp());
            setHP(1);
            return;
        }
        float days = Global.getSector().getClock().getElapsedDaysSince(Global.getSector().getMemory().getLong(HPGainedMemory));
        if (days < 1) return;
        hpTemp+= (days*0.01);
        hpTemp = Math.min(hpTemp,1);
        Global.getSector().getMemory().set(HPGainedMemory,Global.getSector().getClock().getTimestamp());
        setHP(hpTemp);
    }
    public void setHP(float HP){
        Global.getSector().getMemory().set(memory,HP);
    }
}
