package gtPlusPlus.xmod.gregtech.registration.gregtech;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.core.util.minecraft.gregtech.PollutionUtils;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ChiselBus;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_AirIntake;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_AirIntake_Extreme;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_ControlCore;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy_RTG;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler_Adv;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Naquadah;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Reservoir;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Solidifier;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_TurbineProvider;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_SuperBus_Input;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_SuperBus_Output;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GT_MetaTileEntity_Hatch_CustomFluidBase;

public class GregtechCustomHatches {

    public static void run() {
        Logger.INFO("Gregtech5u Content | Registering Custom Fluid Hatches.");
        run1();
        if (PollutionUtils.isPollutionEnabled()) {
            run2();
        }
        run3();
        run4();
        run5(); // Chisel buses
        run6(); // Solidifier hatches
    }

    private static void run1() {

        GregtechItemList.Hatch_Input_Cryotheum.set(
                new GT_MetaTileEntity_Hatch_CustomFluidBase(
                        FluidUtils.getFluidStack("cryotheum", 1).getFluid(), // Fluid
                        // to
                        // resitrct
                        // hatch
                        // to
                        128000, // Capacity
                        967, // ID
                        "hatch.cryotheum.input.tier.00", // unlocal name
                        "Cryotheum Cooling Hatch" // Local name
                ).getStackForm(1L));

        GregtechItemList.Hatch_Input_Pyrotheum.set(
                new GT_MetaTileEntity_Hatch_CustomFluidBase(
                        FluidUtils.getFluidStack("pyrotheum", 1).getFluid(), // Fluid
                        // to
                        // resitrct
                        // hatch
                        // to
                        128000, // Capacity
                        968, // ID
                        "hatch.pyrotheum.input.tier.00", // unlocal name
                        "Pyrotheum Heating Vent" // Local name
                ).getStackForm(1L));

        GregtechItemList.Hatch_Input_Naquadah.set(
                new GT_MetaTileEntity_Hatch_Naquadah(
                        969, // ID
                        "hatch.naquadah.input.tier.00", // unlocal name
                        "Naquadah Reactor Input hatch" // Local name
                ).getStackForm(1L));

        GregtechItemList.Hatch_Input_TurbineHousing.set(
                new GT_MetaTileEntity_Hatch_TurbineProvider(
                        31025, // ID
                        "hatch.turbine.input.tier.00", // unlocal name
                        "Turbine Housing", // Local name
                        8).getStackForm(1L));

        // Multiblock Control Core Bus
        GregtechItemList.Hatch_Control_Core.set(
                (new GT_MetaTileEntity_Hatch_ControlCore(30020, "hatch.control.adv", "Control Core Module", 1))
                        .getStackForm(1L));

        // Multiblock Air Intake Hatch
        GregtechItemList.Hatch_Air_Intake.set(
                new GT_MetaTileEntity_Hatch_AirIntake(861, "hatch.air.intake.tier.00", "Air Intake Hatch", 5)
                        .getStackForm(1L));
        GregtechItemList.Hatch_Air_Intake_Extreme.set(
                new GT_MetaTileEntity_Hatch_AirIntake_Extreme(
                        31070,
                        "hatch.air.intake.tier.01",
                        "Extreme Air Intake Hatch",
                        6).getStackForm(1L));

        // Multiblock Reservoir Hatch
        GregtechItemList.Hatch_Reservoir.set(
                new GT_MetaTileEntity_Hatch_Reservoir(31071, "hatch.water.intake.tier.00", "Reservoir Hatch", 6)
                        .getStackForm(1L));

        // Steam Hatch
        GregtechItemList.Hatch_Input_Steam.set(
                new GT_MetaTileEntity_Hatch_CustomFluidBase(
                        FluidUtils.getSteam(1).getFluid(), // Fluid
                        // to
                        // resitrct
                        // hatch
                        // to
                        64000, // Capacity
                        31040, // ID
                        "hatch.steam.input.tier.00", // unlocal name
                        "Steam Hatch" // Local name
                ).getStackForm(1L));
    }

    private static void run2() {
        GregtechItemList.Hatch_Muffler_Adv_LV.set(
                (new GT_MetaTileEntity_Hatch_Muffler_Adv(
                        30001,
                        "hatch.muffler.adv.tier.01",
                        "Advanced Muffler Hatch (LV)",
                        1)).getStackForm(1L));
        GregtechItemList.Hatch_Muffler_Adv_MV.set(
                (new GT_MetaTileEntity_Hatch_Muffler_Adv(
                        30002,
                        "hatch.muffler.adv.tier.02",
                        "Advanced Muffler Hatch (MV)",
                        2)).getStackForm(1L));
        GregtechItemList.Hatch_Muffler_Adv_HV.set(
                (new GT_MetaTileEntity_Hatch_Muffler_Adv(
                        30003,
                        "hatch.muffler.adv.tier.03",
                        "Advanced Muffler Hatch (HV)",
                        3)).getStackForm(1L));
        GregtechItemList.Hatch_Muffler_Adv_EV.set(
                (new GT_MetaTileEntity_Hatch_Muffler_Adv(
                        30004,
                        "hatch.muffler.adv.tier.04",
                        "Advanced Muffler Hatch (EV)",
                        4)).getStackForm(1L));
        GregtechItemList.Hatch_Muffler_Adv_IV.set(
                (new GT_MetaTileEntity_Hatch_Muffler_Adv(
                        30005,
                        "hatch.muffler.adv.tier.05",
                        "Advanced Muffler Hatch (IV)",
                        5)).getStackForm(1L));
        GregtechItemList.Hatch_Muffler_Adv_LuV.set(
                (new GT_MetaTileEntity_Hatch_Muffler_Adv(
                        30006,
                        "hatch.muffler.adv.tier.06",
                        "Advanced Muffler Hatch (LuV)",
                        6)).getStackForm(1L));
        GregtechItemList.Hatch_Muffler_Adv_ZPM.set(
                (new GT_MetaTileEntity_Hatch_Muffler_Adv(
                        30007,
                        "hatch.muffler.adv.tier.07",
                        "Advanced Muffler Hatch (ZPM)",
                        7)).getStackForm(1L));
        GregtechItemList.Hatch_Muffler_Adv_UV.set(
                (new GT_MetaTileEntity_Hatch_Muffler_Adv(
                        30008,
                        "hatch.muffler.adv.tier.08",
                        "Advanced Muffler Hatch (UV)",
                        8)).getStackForm(1L));
        GregtechItemList.Hatch_Muffler_Adv_MAX.set(
                (new GT_MetaTileEntity_Hatch_Muffler_Adv(
                        30009,
                        "hatch.muffler.adv.tier.09",
                        "Advanced Muffler Hatch (MAX)",
                        9)).getStackForm(1L));
    }

    private static void run3() {
        /*
         * Super Input Busses
         */

        int aStartID = 30021;

        GregtechItemList.Hatch_SuperBus_Input_ULV.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.00", "Super Bus (I) (ULV)", 0))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Input_LV.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.01", "Super Bus (I) (LV)", 1))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Input_MV.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.02", "Super Bus (I) (MV)", 2))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Input_HV.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.03", "Super Bus (I) (HV)", 3))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Input_EV.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.04", "Super Bus (I) (EV)", 4))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Input_IV.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.05", "Super Bus (I) (IV)", 5))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Input_LuV.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.06", "Super Bus (I) (LuV)", 6))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Input_ZPM.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.07", "Super Bus (I) (ZPM)", 7))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Input_UV.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.08", "Super Bus (I) (UV)", 8))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Input_MAX.set(
                ((IMetaTileEntity) makeInputBus(aStartID++, "hatch.superbus.input.tier.09", "Super Bus (I) (MAX)", 9))
                        .getStackForm(1L));

        /*
         * Super Output Busses
         */

        GregtechItemList.Hatch_SuperBus_Output_ULV.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.00", "Super Bus (O) (ULV)", 0))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Output_LV.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.01", "Super Bus (O) (LV)", 1))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Output_MV.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.02", "Super Bus (O) (MV)", 2))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Output_HV.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.03", "Super Bus (O) (HV)", 3))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Output_EV.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.04", "Super Bus (O) (EV)", 4))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Output_IV.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.05", "Super Bus (O) (IV)", 5))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Output_LuV.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.06", "Super Bus (O) (LuV)", 6))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Output_ZPM.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.07", "Super Bus (O) (ZPM)", 7))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Output_UV.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.08", "Super Bus (O) (UV)", 8))
                        .getStackForm(1L));
        GregtechItemList.Hatch_SuperBus_Output_MAX.set(
                ((IMetaTileEntity) makeOutputBus(aStartID++, "hatch.superbus.output.tier.09", "Super Bus (O) (MAX)", 9))
                        .getStackForm(1L));
    }

    private static GT_MetaTileEntity_SuperBus_Input makeInputBus(int id, String unlocalizedName, String localizedName,
            int tier) {
        return new GT_MetaTileEntity_SuperBus_Input(id, unlocalizedName, localizedName, tier);
    }

    private static GT_MetaTileEntity_SuperBus_Output makeOutputBus(int id, String unlocalizedName, String localizedName,
            int tier) {
        return new GT_MetaTileEntity_SuperBus_Output(id, unlocalizedName, localizedName, tier);
    }

    private static void run4() {
        int aID = 31060;
        // 41, "hatch.energy.tier.01", "LV Energy Hatch", 1
        GT_MetaTileEntity_Hatch_Energy_RTG aHatch1 = new GT_MetaTileEntity_Hatch_Energy_RTG(
                aID++,
                "hatch.energy.rtg.tier.01",
                "RTG Power Unit [LV]",
                1,
                9);
        GT_MetaTileEntity_Hatch_Energy_RTG aHatch2 = new GT_MetaTileEntity_Hatch_Energy_RTG(
                aID++,
                "hatch.energy.rtg.tier.02",
                "RTG Power Unit [MV]",
                2,
                9);
        GT_MetaTileEntity_Hatch_Energy_RTG aHatch3 = new GT_MetaTileEntity_Hatch_Energy_RTG(
                aID++,
                "hatch.energy.rtg.tier.03",
                "RTG Power Unit [HV]",
                3,
                9);

        GregtechItemList.Hatch_RTG_LV.set(aHatch1.getStackForm(1L));
        GregtechItemList.Hatch_RTG_MV.set(aHatch2.getStackForm(1L));
        GregtechItemList.Hatch_RTG_HV.set(aHatch3.getStackForm(1L));
    }

    private static GT_MetaTileEntity_ChiselBus makeChiselBus(int id, String unlocalizedName, String localizedName,
            int tier) {
        return new GT_MetaTileEntity_ChiselBus(id, unlocalizedName, localizedName, tier);
    }

    private static void run5() {
        int aID = 31777;

        GregtechItemList.GT_MetaTileEntity_ChiselBus_I
                .set((makeChiselBus(aID++, "hatch.chisel.tier.00", "Chisel Bus I", 0)).getStackForm(1L));
        GregtechItemList.GT_MetaTileEntity_ChiselBus_II
                .set((makeChiselBus(aID++, "hatch.chisel.tier.01", "Chisel Bus II", 1)).getStackForm(1L));
        GregtechItemList.GT_MetaTileEntity_ChiselBus_III
                .set((makeChiselBus(aID++, "hatch.chisel.tier.02", "Chisel Bus III", 2)).getStackForm(1L));
        GregtechItemList.GT_MetaTileEntity_ChiselBus_IV
                .set((makeChiselBus(aID++, "hatch.chisel.tier.03", "Chisel Bus IV", 3)).getStackForm(1L));

    }

    private static void run6() {
        int aID = 31781;

        GregtechItemList.GT_MetaTileEntity_Solidifier_I.set(
                new GT_MetaTileEntity_Hatch_Solidifier(aID++, "hatch.solidifier.tier.05", "Solidifier Hatch I", 5)
                        .getStackForm(1L));
        GregtechItemList.GT_MetaTileEntity_Solidifier_II.set(
                new GT_MetaTileEntity_Hatch_Solidifier(aID++, "hatch.solidifier.tier.06", "Solidifier Hatch II", 6)
                        .getStackForm(1L));
        GregtechItemList.GT_MetaTileEntity_Solidifier_III.set(
                new GT_MetaTileEntity_Hatch_Solidifier(aID++, "hatch.solidifier.tier.07", "Solidifier Hatch III", 7)
                        .getStackForm(1L));
        GregtechItemList.GT_MetaTileEntity_Solidifier_IV.set(
                new GT_MetaTileEntity_Hatch_Solidifier(aID, "hatch.solidifier.tier.08", "Solidifier Hatch IV", 8)
                        .getStackForm(1L));
    }
}
