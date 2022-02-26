package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

import com.gtnewhorizon.structurelib.alignment.IAlignmentLimits;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Maintenance;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GTPP_Recipe.GTPP_Recipe_Map;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.core.util.minecraft.PlayerUtils;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class GregtechMetaTileEntity_IndustrialWashPlant extends GregtechMeta_MultiBlockBase<GregtechMetaTileEntity_IndustrialWashPlant> {

	private int mMode = 0;
	private int mCasing;
	private IStructureDefinition<GregtechMetaTileEntity_IndustrialWashPlant> STRUCTURE_DEFINITION = null;

	public GregtechMetaTileEntity_IndustrialWashPlant(final int aID, final String aName, final String aNameRegional) {
		super(aID, aName, aNameRegional);
	}

	public GregtechMetaTileEntity_IndustrialWashPlant(final String aName) {
		super(aName);
	}

	@Override
	public IMetaTileEntity newMetaEntity(final IGregTechTileEntity aTileEntity) {
		return new GregtechMetaTileEntity_IndustrialWashPlant(this.mName);
	}

	@Override
	public String getMachineType() {
		return "Ore Washer, Simple Washer, Chemical Bath";
	}

	@Override
	protected GT_Multiblock_Tooltip_Builder createTooltip() {
		GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
		tt.addMachineType(getMachineType())
				.addInfo("Controller Block for the Industrial Wash Plant")
				.addInfo("Can be configured with a screwdriver to also do Simple Washer and process Chemical Bathing")
				.addInfo("400% faster than using single block machines of the same voltage")
				.addInfo("Processes four item per voltage tier")
				.addInfo("Always requires an Input Hatch full of water to refill structure")
				.addPollutionAmount(getPollutionPerSecond(null))
				.addSeparator()
				.beginStructureBlock(5, 3, 7, true)
				.addController("Front Center")
				.addCasingInfo("Wash Plant Casings", 40)
				.addInputBus("Any Casing", 1)
				.addOutputBus("Any Casing", 1)
				.addInputHatch("Any Casing", 1)
				.addEnergyHatch("Any Casing", 1)
				.addMaintenanceHatch("Any Casing", 1)
				.addMufflerHatch("Any Casing", 1)
				.toolTipFinisher(CORE.GT_Tooltip_Builder);
		return tt;
	}

	@Override
	public IStructureDefinition<GregtechMetaTileEntity_IndustrialWashPlant> getStructureDefinition() {
		if (STRUCTURE_DEFINITION == null) {
			STRUCTURE_DEFINITION = StructureDefinition.<GregtechMetaTileEntity_IndustrialWashPlant>builder()
					.addShape(mName, transpose(new String[][]{
							{"CCCCC", "C   C", "C   C", "C   C", "C   C", "C   C", "CCCCC"},
							{"CC~CC", "C   C", "C   C", "C   C", "C   C", "C   C", "CCCCC"},
							{"CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC"},
					}))
					.addElement(
							'C',
							ofChain(
									ofHatchAdder(
											GregtechMetaTileEntity_IndustrialWashPlant::addIndustrialWashPlantList, getCasingTextureIndex(), 1
									),
									onElementPass(
											x -> ++x.mCasing,
											ofBlock(
													getCasingBlock(), getCasingMeta()
											)
									)
							)
					)
					.build();
		}
		return STRUCTURE_DEFINITION;
	}

	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		buildPiece(mName , stackSize, hintsOnly, 2, 1, 0);
	}

	@Override
	public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
		mCasing = 0;
		return checkPiece(mName, 2, 1, 0) && mCasing >= 40 && checkHatch();
	}

	public final boolean addIndustrialWashPlantList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_InputBus){
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Maintenance){
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Energy){
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_OutputBus) {
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Muffler) {
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Input) {
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			}
		}
		return false;
	}

	@Override
	protected IAlignmentLimits getInitialAlignmentLimits() {
		// don't rotate a washer, water will flow out.
		return (d, r, f) -> d.offsetY == 0 && r.isNotRotated() && !f.isVerticallyFliped();
	}

	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(getCasingTextureIndex()), new GT_RenderedTexture(aActive ? TexturesGtBlock.Overlay_Machine_Controller_Default_Active : TexturesGtBlock.Overlay_Machine_Controller_Default)};
		}
		return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(getCasingTextureIndex())};
	}

	@Override
	public boolean hasSlotInGUI() {
		return false;
	}

	@Override
	public String getCustomGUIResourceName() {
		return "IndustrialWashPlant";
	}

	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return mMode == 0 ?  GT_Recipe.GT_Recipe_Map.sOreWasherRecipes : mMode == 1 ? GTPP_Recipe_Map.sSimpleWasherRecipes : GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes;
	}

	@Override
	public boolean checkRecipe(final ItemStack aStack) {
		if (checkForWater()) {
			return checkRecipeGeneric((4 * GT_Utility.getTier(this.getMaxInputVoltage())), 100, 400);
		}
		return false;
	}
	
	@Override
	public int getMaxParallelRecipes() {
		return (4* GT_Utility.getTier(this.getMaxInputVoltage()));
	}

	@Override
	public int getEuDiscountForParallelism() {
		return 100;
	}

	@Override
	public int getMaxEfficiency(final ItemStack aStack) {
		return 10000;
	}

	@Override
	public int getPollutionPerSecond(final ItemStack aStack) {
		if (mMode == 2) return CORE.ConfigSwitches.pollutionPerSecondMultiIndustrialWashPlant_ModeChemBath;
		return CORE.ConfigSwitches.pollutionPerSecondMultiIndustrialWashPlant_ModeWasher;
	}

	@Override
	public int getAmountOfOutputs() {
		return 1;
	}

	@Override
	public boolean explodesOnComponentBreak(final ItemStack aStack) {
		return false;
	}

	public Block getCasingBlock() {
		return ModBlocks.blockCasings2Misc;
	}


	public byte getCasingMeta() {
		return 4;
	}


	public byte getCasingTextureIndex() {
		return (byte) TAE.GTPP_INDEX(11);
	}

	public boolean checkForWater() {

		//Get Facing direction
		IGregTechTileEntity aBaseMetaTileEntity = this.getBaseMetaTileEntity();
		int mDirectionX  = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
		int mCurrentDirectionX;
		int mCurrentDirectionZ;
		int mOffsetX_Lower = 0;
		int mOffsetX_Upper = 0;
		int mOffsetZ_Lower = 0;
		int mOffsetZ_Upper = 0;

		if (mDirectionX == 0){
			mCurrentDirectionX = 2;
			mCurrentDirectionZ = 3;
			mOffsetX_Lower = -2;
			mOffsetX_Upper = 2;
			mOffsetZ_Lower = -3;
			mOffsetZ_Upper = 3;
		}
		else {
			mCurrentDirectionX = 3;
			mCurrentDirectionZ = 2;	
			mOffsetX_Lower = -3;
			mOffsetX_Upper = 3;
			mOffsetZ_Lower = -2;
			mOffsetZ_Upper = 2;	
		}

		//if (aBaseMetaTileEntity.fac)

		final int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX * mCurrentDirectionX;
		final int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ * mCurrentDirectionZ;

		int tAmount = 0;
		for (int i = mOffsetX_Lower + 1; i <= mOffsetX_Upper - 1; ++i) {
			for (int j = mOffsetZ_Lower + 1; j <= mOffsetZ_Upper - 1; ++j) {
				for (int h = 0; h < 2; ++h) {
					Block tBlock = aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j);
					byte tMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j);
					if (tBlock == Blocks.air || tBlock == Blocks.flowing_water || tBlock == Blocks.water) {
						if (this.getStoredFluids() != null) {
							for (FluidStack stored : this.getStoredFluids()) {
								if (stored.isFluidEqual(FluidUtils.getFluidStack("water", 1))) {
									if (stored.amount >= 1000) {
										//Utils.LOG_WARNING("Going to try swap an air block for water from inut bus.");
										stored.amount -= 1000;
										Block fluidUsed = null;
										if (tBlock == Blocks.air || tBlock == Blocks.flowing_water) {
											fluidUsed = Blocks.water;
										}
										if (tBlock == Blocks.water) {
											fluidUsed = BlocksItems.getFluidBlock(InternalName.fluidDistilledWater);
										}
										aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getXCoord() + xDir + i, aBaseMetaTileEntity.getYCoord() + h, aBaseMetaTileEntity.getZCoord() + zDir + j, fluidUsed);


									}
								}
							}
						}
					}
					if (tBlock == Blocks.water) {
						++tAmount;
						//Utils.LOG_WARNING("Found Water");
					} else if (tBlock == BlocksItems.getFluidBlock(InternalName.fluidDistilledWater)) {
						++tAmount;
						++tAmount;
						//Utils.LOG_WARNING("Found Distilled Water");
					}
				}
			}
		}
		if ((tAmount >= 45)){
			Logger.WARNING("Filled structure.");
		}
		else {
			Logger.WARNING("Did not fill structure.");
		}
		return (tAmount >= 45);
	}

	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		aNBT.setInteger("mMode", mMode);
		super.saveNBTData(aNBT);
	}

	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		if (aNBT.hasKey("mChemicalMode")) {
			boolean aTempMode = aNBT.getBoolean("mChemicalMode");
			if (aTempMode) {
				mMode = 2;
			}
			else {
				mMode = 0;
			}
			aNBT.removeTag("mChemicalMode");
		}
		if (aNBT.hasKey("mMode")) {
			mMode = aNBT.getInteger("mMode");			
		}
		super.loadNBTData(aNBT);
	}

	@Override
	public void onModeChangeByScrewdriver(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		mMode++;
		if (mMode > 2) {
			mMode = 0;
		}
		if (mMode == 0){
			PlayerUtils.messagePlayer(aPlayer, "Wash Plant is now running in Ore Washer Mode.");
		}
		else if (mMode == 1){
			PlayerUtils.messagePlayer(aPlayer, "Wash Plant is now running in Simple Washer Mode.");
		}
		else {
			PlayerUtils.messagePlayer(aPlayer, "Wash Plant is now running in Chemical Bath Mode.");
		}		
	}

}