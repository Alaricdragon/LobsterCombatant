package Lobster_Combatant.data.scripts.customCrews.listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.*;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.fleet.MutableFleetStatsAPI;
import com.fs.starfarer.api.impl.PlayerFleetPersonnelTracker;
import com.fs.starfarer.api.impl.campaign.CargoPodsEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class LobsterCombatant_CustomCrew_XP_cargoHandler implements ColonyInteractionListener,
        CommodityTooltipModifier,
        CommodityIconProvider,
        CargoScreenListener {
    public static final String commodity = "LobsterCombatant_CombatLobster";
    private final static String memoryKey="$LobsterCombatant_LobsterXP";
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
        float got = transaction.getQuantityBought(commodity);
        float lost = transaction.getQuantitySold(commodity);
        processTransaction(got - lost);
    }

    @Override
    public void reportPlayerMarketTransaction(PlayerMarketTransaction transaction) {
        float got = transaction.getQuantityBought(commodity);
        float lost = transaction.getQuantitySold(commodity);
        processTransaction(got - lost);
    }

    @Override
    public String getRankIconName(CargoStackAPI stack) {
        if(!stack.getCommodityId().equals(commodity) && !(stack.getCargo().getFleetData() != null && stack.getCargo().getFleetData().getFleet().isPlayerFleet())) return null;
        int[] thresholds = {25,50,75,100};
        String[] ranks = {"icon_crew_green","icon_crew_regular","icon_crew_veteran","icon_crew_elite"};
        int xp = getEffectiveXPPercent();
        for (int a = 0; a < thresholds.length; a++){
            if (thresholds[a] >= xp){
                return Global.getSettings().getSpriteName("ui", ranks[a]);
            }
        }
        return Global.getSettings().getSpriteName("ui", ranks[0]);
    }

    @Override
    public void addSectionAfterPrice(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack) {
        if(!stack.getCommodityId().equals(commodity) && !(stack.getCargo().getFleetData() != null && stack.getCargo().getFleetData().getFleet().isPlayerFleet())) return;
        float xp = (float)Global.getSector().getMemory().getFloat(memoryKey);
        info.addPara("total XP is: %s",3,Misc.getHighlightColor(),xp+"");
        info.addPara("current XP is: %s",3,Misc.getHighlightColor(),getEffectiveXPPercent()+"%");
    }

    public void processTransaction(float change){
        //if (change >= 0) return;
        float xp = Global.getSector().getMemory().getFloat(memoryKey);
        float commoditys = Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(commodity);
        //float ratio = -change/commoditys;
        xp = Math.min(xp,commoditys);
        Global.getSector().getMemory().set(memoryKey,xp);// * -ratio);
    }
    public static int getEffectiveXPPercent(){
        float xp = (float)Global.getSector().getMemory().getFloat(memoryKey);
        float commoditys = Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(commodity);
        return (int)Math.min(Math.floor((xp*100)/commoditys),100);
    }










/*
    public void reportPlayerNonMarketTransaction(PlayerMarketTransaction transaction, InteractionDialogAPI dialog) {
        if (pods == null && dialog != null) {
            SectorEntityToken target = dialog.getInteractionTarget();
            if (target != null && target.getCustomPlugin() instanceof CargoPodsEntityPlugin) {
                pods = target;
            }
        }
        processTransaction(transaction, pods);
    }

    public void reportPlayerMarketTransaction(PlayerMarketTransaction transaction) {
        if (transaction.getMarket() == null ||
                transaction.getMarket().getPrimaryEntity() == null ||
                transaction.getSubmarket() == null) return;
        if (!transaction.getSubmarket().getSpecId().equals(Submarkets.SUBMARKET_STORAGE)) {
            doCleanup(true);
            update(false, true, null);
            return;
        }
        processTransaction(transaction, transaction.getMarket().getPrimaryEntity());
    }

    public void processTransaction(PlayerMarketTransaction transaction, SectorEntityToken entity) {
        if (entity == null) return;

        SubmarketAPI sub = transaction.getSubmarket();

//		// when ejecting cargo, there's a fake "storage" submarket, but when interacting with the pods, there's
//		// no submarket - so for pods to display rank correctly, set the submarket when dropping off pods to null
//		if (pods != null) {
//			sub = null;
//		}

        for (CargoStackAPI stack : transaction.getSold().getStacksCopy()) {
            if (!stack.isPersonnelStack()) continue;
            if (stack.isMarineStack()) {
                PlayerFleetPersonnelTracker.PersonnelAtEntity at = getDroppedOffAt(stack.getCommodityId(), entity, sub, true);

                int num = (int) stack.getSize();
                transferPersonnel(marineData, at.data, num, marineData);
            }
        }

        for (CargoStackAPI stack : transaction.getBought().getStacksCopy()) {
            if (!stack.isPersonnelStack()) continue;
            if (stack.isMarineStack()) {
                PlayerFleetPersonnelTracker.PersonnelAtEntity at = getDroppedOffAt(stack.getCommodityId(), entity, sub, true);

                int num = (int) stack.getSize();
                transferPersonnel(at.data, marineData, num, marineData);
            }
        }

        doCleanup(true);
        update();
    }

    public static void transferPersonnel(PlayerFleetPersonnelTracker.PersonnelData from, PlayerFleetPersonnelTracker.PersonnelData to, int num, PlayerFleetPersonnelTracker.PersonnelData keepsXP) {
        if (num > from.num) {
            num = (int) from.num;
        }
        if (num <= 0) return;

        if (KEEP_XP_DURING_TRANSFERS && keepsXP != null) {
            to.add(num);
            from.remove(num, false);

            float totalXP = to.xp + from.xp;
            if (keepsXP == from) {
                from.xp = Math.min(totalXP, from.num);
                to.xp = Math.max(0f, totalXP - from.num);
            } else if (keepsXP == to) {
                to.xp = Math.min(totalXP, to.num);
                from.xp = Math.max(0f, totalXP - to.num);
            }
        } else {
            float xp = from.xp * num / from.num;

            to.add(num);
            to.addXP(xp);

            from.remove(num, true); // also removes XP
        }
    }

    public void update(boolean withIntegrationFromCurrentLocation, boolean keepXP, CargoStackAPI stack) {
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        if (fleet == null) return;
        CargoAPI cargo = fleet.getCargo();


        float marines = cargo.getMarines();
        marineData.numMayHaveChanged(marines, keepXP);

        if (withIntegrationFromCurrentLocation) {
            //getDroppedOffAt(Commodities.MARINES, getInteractionEntity(), currSubmarket, true);
            PlayerFleetPersonnelTracker.PersonnelAtEntity atLocation = getPersonnelAtLocation(Commodities.MARINES, currSubmarket);
            marineData.integrateWithCurrentLocation(atLocation);
        }


        MutableFleetStatsAPI stats = fleet.getStats();

        String id = "marineXP";
        PlayerFleetPersonnelTracker.PersonnelRank rank = marineData.getRank();
        float effectBonus = getMarineEffectBonus(marineData);
        float casualtyReduction = getMarineLossesReductionPercent(marineData);
        if (effectBonus > 0) {
            //stats.getDynamic().getMod(Stats.PLANETARY_OPERATIONS_MOD).modifyMult(id, 1f + effectBonus * 0.01f, rank.name + " marines");
            stats.getDynamic().getMod(Stats.PLANETARY_OPERATIONS_MOD).modifyPercent(id, effectBonus, rank.name + " marines");
        } else {
            //stats.getDynamic().getMod(Stats.PLANETARY_OPERATIONS_MOD).unmodifyMult(id);
            stats.getDynamic().getMod(Stats.PLANETARY_OPERATIONS_MOD).unmodifyPercent(id);
        }
        if (casualtyReduction > 0) {
            stats.getDynamic().getStat(Stats.PLANETARY_OPERATIONS_CASUALTIES_MULT).modifyMult(id, 1f - casualtyReduction * 0.01f, rank.name + " marines");
        } else {
            stats.getDynamic().getStat(Stats.PLANETARY_OPERATIONS_CASUALTIES_MULT).unmodifyMult(id);
        }
    }

    public void addSectionAfterPrice(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack) {
        if (Commodities.MARINES.equals(stack.getCommodityId()) && !expanded) {
            saveData();
            update(true, true, stack);

            PlayerFleetPersonnelTracker.PersonnelData data = marineData;
            boolean nonPlayer = false;
            if (!stack.isInPlayerCargo()) {
                nonPlayer = true;
                PlayerFleetPersonnelTracker.PersonnelAtEntity atLoc = getPersonnelAtLocation(stack.getCommodityId(), getSubmarketFor(stack));
                if (atLoc != null) {
                    data = atLoc.data;
                } else {
                    data = null;
                }
            }
            //if (stack.isInPlayerCargo()) {
            if (data != null) {
                if (data.num <= 0) {
                    restoreData();
                    return;
                }

                float opad = 10f;
                float pad = 3f;
                Color h = Misc.getHighlightColor();

                PlayerFleetPersonnelTracker.PersonnelRank rank = data.getRank();

                LabelAPI heading = info.addSectionHeading(rank.name + " Marines",
                        Misc.getBasePlayerColor(), Misc.getDarkPlayerColor(), Alignment.MID, opad);
                heading.autoSizeToWidth(info.getTextWidthOverride());
                PositionAPI p = heading.getPosition();
                p.setSize(p.getWidth(), p.getHeight() + 3f);


                switch (rank) {
                    case REGULAR:
                        if (nonPlayer) {
                            info.addPara("Regular marines - tough, competent, and disciplined.", opad);
                        } else {
                            info.addPara("These marines are mostly regulars and have seen some combat, " +
                                    "but are not, overall, accustomed to your style of command.", opad);
                        }
                        break;
                    case EXPERIENCED:
                        if (nonPlayer) {
                            info.addPara("Experienced marines with substantial training and a number of " +
                                    "operations under their belts.", opad);
                        } else {
                            info.addPara("You've led these marines on several operations, and " +
                                    "the experience gained by both parties is beginning to show concrete benefits.", opad);
                        }
                        break;
                    case VETERAN:
                        if (nonPlayer) {
                            info.addPara("These marines are veterans of many ground operations. " +
                                    "Well-motivated and highly effective.", opad);
                        } else {
                            info.addPara("These marines are veterans of many ground operations under your leadership; " +
                                    "the command structure is well established and highly effective.", opad);
                        }
                        break;
                    case ELITE:
                        if (nonPlayer) {
                            info.addPara("These marines are an elite force, equipped, led, and motivated well " +
                                    "above the standards of even the professional militaries in the Sector.", opad);
                        } else {
                            info.addPara("These marines are an elite force, equipped, led, and motivated well " +
                                    "above the standards of even the professional militaries in the Sector.", opad);
                        }
                        break;

                }

                float effectBonus = getMarineEffectBonus(data);
                float casualtyReduction = getMarineLossesReductionPercent(data);
                MutableStat fake = new MutableStat(1f);
                fake.modifyPercentAlways("1", effectBonus, "increased effectiveness of ground operations");
                fake.modifyPercentAlways("2", -casualtyReduction, "reduction to marine casualties suffered during ground operations");
                info.addStatModGrid(width, 50f, 10f, opad, fake, true, null);

            }
            restoreData();
        }
    }

    public String getRankIconName(CargoStackAPI stack) {
        if (stack.isPickedUp()) return null;
        saveData();
        update(true, true, stack);
        PlayerFleetPersonnelTracker.PersonnelData data = null;

        if (stack.isMarineStack()) {
            data = marineData;
            if (!stack.isInPlayerCargo()) {
                SubmarketAPI sub = getSubmarketFor(stack);
                PlayerFleetPersonnelTracker.PersonnelAtEntity atLocation = getPersonnelAtLocation(stack.getCommodityId(), sub);
                if (atLocation != null) {
                    data = atLocation.data;
                } else {
                    restoreData();
                    return null;
                }
            }
        }


        if (data == null || data.num <= 0) {
            restoreData();
            return null;
        }

        PlayerFleetPersonnelTracker.PersonnelRank rank = data.getRank();
        restoreData();
        return Global.getSettings().getSpriteName("ui", rank.iconKey);
    }*/
}
