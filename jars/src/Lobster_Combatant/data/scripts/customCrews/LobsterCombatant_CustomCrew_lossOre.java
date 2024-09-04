package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.campaign.fleet.CargoData;
import data.scripts.crewReplacer_Crew;

public class LobsterCombatant_CustomCrew_lossOre extends crewReplacer_Crew {
    float oreLost=0;
    @Override
    public void removeCrew(CargoAPI cargo, float CrewToLost) {
        oreLost = getOreLost(cargo, CrewToLost);
        cargo.removeCommodity("ore",oreLost);
        super.removeCrew(cargo, CrewToLost-oreLost);
    }

    @Override
    public void displayCrewLost(CargoAPI cargo, float numberOfItems, TextPanelAPI text) {
        String displayName = getDisplayName(cargo);

        TooltipMakerAPI tt = text.beginTooltip();
        TooltipMakerAPI iwt = tt.beginImageWithText(getCrewIcon(cargo), 24);
        String numberStr = (int) numberOfItems-oreLost + "";
        LabelAPI label = iwt.addPara(numberStr + " " + displayName, 0, Misc.getHighlightColor(), numberStr);
        tt.addImageWithText(0);
        text.addTooltip();


        displayName = Global.getSector().getEconomy().getCommoditySpec("ore").getName();

        tt = text.beginTooltip();
        iwt = tt.beginImageWithText(getCrewIcon(cargo), 24);
        numberStr = (int) oreLost + "";
        label = iwt.addPara(numberStr + " " + displayName, 0, Misc.getHighlightColor(), numberStr);
        tt.addImageWithText(0);
        text.addTooltip();
    }
    public float getOreLost(CargoAPI cargo, float CrewToLost){
        return Math.min(CrewToLost*0.5f,cargo.getCommodityQuantity("ore"));
    }

    @Override
    public float getCargoSpaceUse(CargoAPI cargo, float amountOfCrew) {
        return getCargoSpace(cargo,amountOfCrew);
    }

    @Override
    public float getCargoSpaceUse(CargoAPI cargo) {
        return getCargoSpace(cargo,this.getCrewInCargo(cargo));
    }

    @Override
    public float getCargoSpacePerItem(CargoAPI cargo) {
        return  super.getCargoSpacePerItem(cargo);
    }

    public float getCargoSpace(CargoAPI cargo, float amount){
        float oreuse = getOreLost(cargo,amount);
        float oreCargo = Global.getSector().getEconomy().getCommoditySpec("ore").getCargoSpace();
        float crewCargo = Global.getSector().getEconomy().getCommoditySpec(this.name).getCargoSpace();
        return (oreCargo*oreuse) + ((amount-oreuse)*crewCargo);
    }
}
