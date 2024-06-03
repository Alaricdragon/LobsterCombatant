package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.crewReplacer_Crew;

import java.util.ArrayList;

public class LobsterCombatant_CustomCrew_Hullmod extends crewReplacer_Crew {
    public String hullModID = "repair_gantry";
    public float[] powerPerSize = {1,2,4,8};
    public float[] lossesTemp;
    public float HP = 100;
    float crMin = 0.35f;
    public ArrayList<FleetMemberAPI> getFleetMembers(CargoAPI cargo){
        ArrayList<FleetMemberAPI> out = new ArrayList<>();
        for (FleetMemberAPI a : cargo.getFleetData().getMembersListCopy()){
            if(a.getVariant().getHullMods().contains(hullModID) && a.getRepairTracker().getCR() >= crMin) {
                out.add(a);
            }
        }
        return out;
    }
    public float[] getCRLosses(CargoAPI cargo, float numberOfItems,ArrayList<FleetMemberAPI> fleet){
        float[] out = new float[fleet.size()];
        float CRTotal = this.getCrewInCargo(cargo);
        float HPLossPerCR = numberOfItems / CRTotal;
        for (int a = 0; a < fleet.size(); a++) {
            FleetMemberAPI b = fleet.get(a);
            float cr = b.getRepairTracker().getCR();
            float loss = HPLossPerCR*cr;
            out[a] = Math.min(loss,cr);
        }
        return out;
    }
    public float getSizePower(FleetMemberAPI ship){
        String size = ship.getVariant().getHullSpec().getHullSize().name();
        if (size.equals(ShipAPI.HullSize.FRIGATE.name())) return powerPerSize[0];
        if (size.equals(ShipAPI.HullSize.DESTROYER.name())) return powerPerSize[1];
        if (size.equals(ShipAPI.HullSize.CRUISER.name())) return powerPerSize[2];
        if (size.equals(ShipAPI.HullSize.CAPITAL_SHIP.name())) return powerPerSize[3];
        return powerPerSize[1];
    }
    @Override
    public float getCrewInCargo(CargoAPI cargo) {
        try {
            float power = 0;
            for (FleetMemberAPI a : getFleetMembers(cargo)) {
                power += getSizePower(a)*HP*a.getRepairTracker().getCR();
            }
            return power;
        }catch (Exception e){
            return 0f;
        }
    }
    @Override
    public float getCrewDefence(CargoAPI cargo) {
        return super.getCrewDefence(cargo)/HP;
    }

    @Override
    public float getCrewPower(CargoAPI cargo) {
        return super.getCrewPower(cargo)/HP;
    }

    @Override
    public void removeCrew(CargoAPI cargo, float CrewToLost) {
        ArrayList<FleetMemberAPI> fleet = getFleetMembers(cargo);
        for (int a = 0; a < lossesTemp.length; a++){
            fleet.get(a).getRepairTracker().setCR(fleet.get(a).getRepairTracker().getCR() - lossesTemp[a]);
        }
    }

    @Override
    public String getCrewIcon(CargoAPI cargo) {
        return Global.getSettings().getSpriteName("systemMap", "icon_fleet0");
    }

    @Override
    public String getDisplayName(CargoAPI cargo) {
        return "salvage ships";
    }

    @Override
    public void displayCrewAvailable(CargoAPI cargo, float numberOfItems, TextPanelAPI text) {
        numberOfItems = getFleetMembers(cargo).size();
        super.displayCrewAvailable(cargo, numberOfItems, text);
    }

    @Override
    public void displayCrewLost(CargoAPI cargo, float numberOfItems, TextPanelAPI text) {
        ArrayList<FleetMemberAPI> fleet = getFleetMembers(cargo);
        lossesTemp = getCRLosses(cargo,numberOfItems,fleet);
        for (int a = 0; a < lossesTemp.length; a++){
            if (lossesTemp[a] != 0) {
                String displayName = fleet.get(a).getShipName();
                TooltipMakerAPI tt = text.beginTooltip();
                TooltipMakerAPI iwt = tt.beginImageWithText(fleet.get(a).getHullSpec().getSpriteName(), 24);
                String numberStr = (int) (lossesTemp[a]*100) + "%";
                String CRLeft = (int)((fleet.get(a).getRepairTracker().getCR() - lossesTemp[a])*100)+"%";
                LabelAPI label = iwt.addPara("%s lost %s of its CR. it has %s CR remaining", 0, Misc.getHighlightColor(),displayName, numberStr,CRLeft);
                tt.addImageWithText(0);
                text.addTooltip();
            }
        }
    }
}
