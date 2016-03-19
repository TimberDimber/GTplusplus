package miscutil.gregtech.metatileentity.implementations;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import miscutil.gregtech.gui.CONTAINER_SafeBlock;
import miscutil.gregtech.gui.GUI_SafeBlock;
import miscutil.gregtech.metatileentity.implementations.base.GregtechMetaSafeBlockBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

public class GregtechMetaSafeBlock
        extends GregtechMetaSafeBlockBase {
	
	@Override
	public String[] getDescription() {
		return new String[] {mDescription, "Added by: "	+ EnumChatFormatting.DARK_GREEN+"Alkalus"};
	}
	
    public GregtechMetaSafeBlock(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 28, "Protecting your items from sticky fingers.");
    }

    public GregtechMetaSafeBlock(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount, String aDescription) {
        super(aID, aName, aNameRegional, aTier, aInvSlotCount, aDescription);
    }

    public GregtechMetaSafeBlock(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    @Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GregtechMetaSafeBlock(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
    }

    @Override
	public ITexture getOverlayIcon() {
        return new GT_RenderedTexture(Textures.BlockIcons.AUTOMATION_SUPERBUFFER);
    }

    @Override
	public boolean isValidSlot(int aIndex) {
        return aIndex < this.mInventory.length - 1;
    }

    /*@Override
	protected void moveItems(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
        fillStacksIntoFirstSlots();
        super.moveItems(aBaseMetaTileEntity, aTimer);
        fillStacksIntoFirstSlots();
    }*/

    protected void fillStacksIntoFirstSlots() {
        for (int i = 0; i < this.mInventory.length - 1; i++) {
            for (int j = i + 1; j < this.mInventory.length - 1; j++) {
                if ((this.mInventory[j] != null) && ((this.mInventory[i] == null) || (GT_Utility.areStacksEqual(this.mInventory[i], this.mInventory[j])))) {
                    GT_Utility.moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(), j, i, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
                }
            }
        }
    }

    @Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new CONTAINER_SafeBlock(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_SafeBlock(aPlayerInventory, aBaseMetaTileEntity);
    }
}
