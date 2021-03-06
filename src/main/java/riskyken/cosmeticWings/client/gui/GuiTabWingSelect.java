package riskyken.cosmeticWings.client.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import riskyken.cosmeticWings.client.gui.controls.GuiDropDownList;
import riskyken.cosmeticWings.client.gui.controls.GuiDropDownList.DropDownListItem;
import riskyken.cosmeticWings.client.gui.controls.GuiDropDownList.IDropDownListCallback;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import riskyken.cosmeticWings.client.gui.controls.GuiTextBox;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.wings.IWings;
import riskyken.cosmeticWings.common.wings.WingsRegistry;
import riskyken.cosmeticWings.utils.ModLogger;

@SideOnly(Side.CLIENT)
public class GuiTabWingSelect extends GuiTabPage implements IDropDownListCallback {

    public GuiDropDownList dropDownList;
    public GuiTextBox wingInfoBox;
    
    public GuiTabWingSelect(Gui parent, int x, int y) {
        super(parent, x, y);
    }
    
    @Override
    public void initGui() {
        buttonList.clear();
        
        wingInfoBox = new GuiTextBox(1, this.x + 3, this.y + 46, 120, 56);
        buttonList.add(wingInfoBox);
        
        dropDownList = new GuiDropDownList(0, this.x + 3, this.y + 28, 120, "", this);
        
        WingsRegistry wr = WingsRegistry.INSTANCE;
        String unlocalizedName = "wings." + LibModInfo.ID.toLowerCase() + ":none.name";
        String localizedName = I18n.format(unlocalizedName);
        
        dropDownList.addListItem(localizedName, "", true);
        dropDownList.setListSelectedIndex(0);
        ArrayList<IWings> wingTypes = wr.getRegisteredWingTypes();
        for (int i = 0; i < wingTypes.size(); i++) {
            IWings wingType = wingTypes.get(i);
            dropDownList.addListItem(wingType.getLocalizedName(), wingType.getRegistryName(), true);
            if (wingType == ((GuiWings)parent).wingsData.wingType) {
                dropDownList.setListSelectedIndex(i + 1);
                updateWingInfoBox(wingType);
            }
        }
        buttonList.add(dropDownList);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        super.drawScreen(mouseX, mouseY, tickTime);
        String listLabel = GuiHelper.getLocalizedControlName("wings", "label.list");
        this.fontRendererObj.drawString(listLabel, this.x + 2, this.y + 18, 4210752);
    }

    @Override
    public void onDropDownListChanged(GuiDropDownList dropDownList) {
        ((IDropDownListCallback)parent).onDropDownListChanged(dropDownList);
        wingInfoBox.clearDisplayLines();
        DropDownListItem listItem = dropDownList.getListSelectedItem();
        IWings wings = WingsRegistry.INSTANCE.getWingsFormRegistryName(listItem.tag);
        updateWingInfoBox(wings);
    }
    
    private void updateWingInfoBox(IWings wings) {
        wingInfoBox.clearDisplayLines();
        if (wings == null) {
            return;
        }
        String labelAuthor = "inventory.cosmeticwings:wings.label.author";
        String labelGlowing = "inventory.cosmeticwings:wings.label.glowing";
        String labelColourable = "inventory.cosmeticwings:wings.label.colourable";
        
        boolean glowFlag = false;
        boolean colourFlag = false;
        
        for (int i = 0; i < wings.getNumberOfRenderLayers(); i++) {
            if (wings.isGlowing(i)) {
                glowFlag = true;
            }
            if (wings.canRecolour(i)) {
                colourFlag = true;
            }
        }
        
        wingInfoBox.addDisplayLine(translate(labelAuthor, wings.getAuthorName()));
        wingInfoBox.addDisplayLine(translate(labelGlowing, glowFlag));
        wingInfoBox.addDisplayLine(translate(labelColourable, colourFlag));
        wingInfoBox.addDisplayLine(TextFormatting.GRAY.toString() + TextFormatting.ITALIC.toString() + wings.getFlavourText());
    }
    
    public static String translate(String unlocalizedText) {
        String localizedText = I18n.format(unlocalizedText);
        return localizedText.replace("&", "\u00a7");
    }
    
    public static String translate(String unlocalizedText, Object ... args) {
        String localizedText = I18n.format(unlocalizedText, args);
        return localizedText.replace("&", "\u00a7");
    }
}
