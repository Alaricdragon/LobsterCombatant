package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.CrewReplacer_Log;
import data.scripts.crewReplacer_Crew;

public class LobsterCombatant_CustomCrew_DaveTheLegendaryDoomPirate extends crewReplacer_Crew {
    public int maxHP= 10000;
    public String memory = "$LobsterCombatant_DavesHPPercent";
    public String HPGainedMemory = "$LobsterCombatant_TimeSinceDaveHealed";
    float davesLostTemp=0;
    float davesHPLost=0;
    public int getDaves(CargoAPI cargo){
        return (int)cargo.getCommodityQuantity(this.name);
    }
    public void attemptHealing(CargoAPI cargo){
        float hpTemp = Global.getSector().getMemory().getFloat(memory);
        if (hpTemp == 1 || hpTemp == 0){
            Global.getSector().getMemory().set(HPGainedMemory,Global.getSector().getClock().getTimestamp());
            return;
        }
        float days = Global.getSector().getClock().getElapsedDaysSince(Global.getSector().getMemory().getLong(HPGainedMemory));
        if (days < 1) return;
        hpTemp+= (days*0.01);
        hpTemp = Math.min(hpTemp,1);
        Global.getSector().getMemory().set(HPGainedMemory,Global.getSector().getClock().getTimestamp());
        setHP(cargo,hpTemp);
    }
    public float getHP(CargoAPI cargo){
        attemptHealing(cargo);
        float a = Global.getSector().getMemory().getFloat(memory);
        if (a == 0) a = 1;
        a *= getMaxHP(cargo);
        return a;
    }
    public void setHP(CargoAPI cargo,float HP){
        Global.getSector().getMemory().set(memory,HP);
    }
    public float getMaxHP(CargoAPI cargo){
        return maxHP * getDaves(cargo);
    }
    @Override
    public float getCrewInCargo(CargoAPI cargo) {
        return getHP(cargo);
    }

    @Override
    public float getCrewPower(CargoAPI cargo) {
        return this.crewPower*(getDaves(cargo) / getCrewInCargo(cargo));
    }

    @Override
    public float getCrewDefence(CargoAPI cargo) {
        return this.crewDefence*(getDaves(cargo) / getCrewInCargo(cargo));
    }

    @Override
    public void displayCrewLost(CargoAPI cargo, float numberOfItems, TextPanelAPI text) {
        numberOfItems = davesLostTemp;
        String displayName = getDisplayName(cargo);

        TooltipMakerAPI tt = text.beginTooltip();
        TooltipMakerAPI iwt = tt.beginImageWithText(getCrewIcon(cargo), 24);
        String numberStr = (int) numberOfItems + "";
        LabelAPI label = iwt.addPara(numberStr + " " + displayName, 0, Misc.getHighlightColor(), numberStr);
        int hpTemp = (int)Math.floor(100 * davesHPLost);//(crewLossMultiTemp-1));
        if(hpTemp != 0) {
            String HP = hpTemp+"%";
            iwt.addPara("dave's lost %s hp", 0, Misc.getHighlightColor(),HP);
        }
        tt.addImageWithText(0);

        text.addTooltip();
    }
    @Override
    public void displayCrewAvailable(CargoAPI cargo, float numberOfItems, TextPanelAPI text) {
        super.displayCrewAvailable(cargo, getDaves(cargo), text);
    }

    @Override
    public float getCrewToLose(CargoAPI cargo, float crewUsed, float crewLost) {
        float hpLost = 0;
        float hp = getHP(cargo);
        float daves = getDaves(cargo);
        float losses = 0;
        float hpPerDave = maxHP/daves;
        while(crewLost >= hpPerDave && crewLost > 0){
            hp -= Math.min(crewLost,hpPerDave);
            losses++;
            crewLost-=hpPerDave;
            hpLost+=hpPerDave;
        }
        hp-=crewLost;
        hpLost+=crewLost;
        davesHPLost = hpLost / getMaxHP(cargo);
        setHP(cargo,hp / getMaxHP(cargo));
        davesLostTemp = losses;
        return super.getCrewToLose(cargo, crewUsed, crewLost);
    }

    @Override
    public void removeCrew(CargoAPI cargo, float CrewToLost) {
        super.removeCrew(cargo, davesLostTemp);
    }
}
