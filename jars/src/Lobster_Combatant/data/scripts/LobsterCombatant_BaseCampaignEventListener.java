package Lobster_Combatant.data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class LobsterCombatant_BaseCampaignEventListener implements EconomyAPI.EconomyUpdateListener {

    @Override
    public void commodityUpdated(String commodityId) {
    }

    @Override
    public void economyUpdated() {
        //making everything a variable for your convenience. just the values here for basic changes.
        String commodity = "LobsterCombatant_CombatLobster";
        String industry = "lionsguard";
        String source = "LobsterCombatant_BaseCampaignEventListener";
        //this for loop runs over every market in the game
        for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()){
            //this if statment prevents crashes if you try to get a industry that doe snot exsist.
            if (market.hasIndustry(industry)){
                //this line of code adds
                market.getIndustry(industry).getSupply(commodity).getQuantity().modifyFlat(source, market.getSize()-1);
            }
        }
    }

    @Override
    public boolean isEconomyListenerExpired() {
        return false;
    }
}
