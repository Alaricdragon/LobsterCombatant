package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.listeners.GroundRaidObjectivesListener;
import com.fs.starfarer.api.impl.PlayerFleetPersonnelTracker;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.CrewReplacer_Log;
import data.scripts.crewReplacer_Crew;

public class LobsterCombatant_CustomCrew_XPSystem extends crewReplacer_Crew {

    private float crewUsedTemp;
    private float ratio = 0;
    private float crewLossMultiTemp = 1;
    private float maxDef = 0.2f;
    private float maxPower = 1f;
    private final static String memoryKey="$LobsterCombatant_LobsterXP";
    public void addXP(float xp,CargoAPI cargo){
        try {
            xp += Global.getSector().getMemory().getFloat(memoryKey);
            xp = Math.min(xp, getCrewInCargo(cargo));
            Global.getSector().getMemory().set(memoryKey, xp);
        }catch (Exception e){
        }
    }
    public float getXP(CargoAPI cargo){
        return Math.min(Global.getSector().getMemory().getFloat(memoryKey),getCrewInCargo(cargo));
    }
    public float getEffectiveXP(float xp,CargoAPI cargo){
        float crew = getCrewInCargo(cargo);
        if (crew == 0) return 0f;
        return xp/crew;
    }
    public float getXPDefneceMulti(float xp, CargoAPI cargo){
        return 1 + (getEffectiveXP(xp,cargo) * maxDef);
    }
    public float getXPPowerMulti(float xp, CargoAPI cargo){
        return 1 + (getEffectiveXP(xp,cargo) * maxPower);
    }
    @Override
    public float getCrewDefence(CargoAPI cargo) {
        return super.getCrewDefence(cargo) * getXPDefneceMulti(getXP(cargo),cargo);
    }

    @Override
    public float getCrewPower(CargoAPI cargo) {
        return super.getCrewPower(cargo) * getXPPowerMulti(getXP(cargo),cargo);
    }
    public float getCrewUsedRatio(CargoAPI cargo, float crewUsed){
        Object[] ObjectTemp = (Object[])ExtraData;
        float temp = crewUsed*getCrewPower(cargo);
        ratio = (float)Math.floor(temp) / (int)ObjectTemp[1];
        return ratio;
    }
    @Override
    public float getCrewToLose(CargoAPI cargo, float crewUsed, float crewLost) {
        float temp = super.getCrewToLose(cargo, crewUsed, crewLost);
        crewUsedTemp = temp;
        return temp;
    }

    @Override
    public void removeCrew(CargoAPI cargo, float CrewToLost) {
        super.removeCrew(cargo, CrewToLost);

        float CrewUsed = crewUsedTemp;
        //crewLossMultiTemp = getXPDefneceMulti(getXP(cargo),cargo);
        if(CrewUsed == 0){
            return;
        }
        try{
            Object[] ObjectTemp = (Object[])ExtraData;
            PlayerFleetPersonnelTracker thing = PlayerFleetPersonnelTracker.getInstance();
            GroundRaidObjectivesListener.RaidResultData data = (GroundRaidObjectivesListener.RaidResultData) ObjectTemp[0];

            float xpGain = data.xpGained;
            xpGain *= thing.XP_PER_RAID_MULT;
            if (xpGain < 0) xpGain = 0;
            xpGain *= getCrewUsedRatio(cargo,crewUsedTemp);
            addXP(xpGain,cargo);
        }catch (Exception E){
        }
    }
    @Override
    public void displayCrewAvailable(CargoAPI cargo, float numberOfItems, TextPanelAPI text){
        String displayName = getDisplayName(cargo);

        TooltipMakerAPI tt = text.beginTooltip();
        TooltipMakerAPI iwt = tt.beginImageWithText(getCrewIcon(cargo), 24);
        String numberStr = (int) numberOfItems + "";
        LabelAPI label = iwt.addPara(numberStr + " " + displayName, 0, Misc.getHighlightColor(), numberStr);
        int xpTemp = (int)Math.floor(100 * (getXPPowerMulti(getXP(cargo),cargo) - 1));
        if(xpTemp != 0) {
            String XP = xpTemp+"%";
            iwt.addPara("- strength increase from XP: %s", 0, Misc.getHighlightColor(),XP);
        }
        tt.addImageWithText(0);

        text.addTooltip();
    }
    @Override
    public void displayCrewLost(CargoAPI cargo,float numberOfItems, TextPanelAPI text){
        String displayName = getDisplayName(cargo);

        TooltipMakerAPI tt = text.beginTooltip();
        TooltipMakerAPI iwt = tt.beginImageWithText(getCrewIcon(cargo), 24);
        String numberStr = (int) numberOfItems + "";
        LabelAPI label = iwt.addPara(numberStr + " " + displayName, 0, Misc.getHighlightColor(), numberStr);
        int xpTemp = (int)Math.floor(100 * (getXPDefneceMulti(getXP(cargo),cargo)-1));//(crewLossMultiTemp-1));
        if(xpTemp != 0) {
            String XP = xpTemp+"%";
            iwt.addPara("- loss reduction from XP: %s", 0, Misc.getHighlightColor(),XP);
        }
        tt.addImageWithText(0);

        text.addTooltip();
    }
}
