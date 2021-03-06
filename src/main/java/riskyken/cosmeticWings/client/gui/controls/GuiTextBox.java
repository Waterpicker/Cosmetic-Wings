package riskyken.cosmeticWings.client.gui.controls;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTextBox extends GuiButtonExt {

    private ArrayList<String> displayLines;
    
    public GuiTextBox(int id, int xPos, int yPos, int width, int height) {
        super(id, xPos, yPos, width, height, "");
        this.displayLines = new ArrayList<String>();
    }
    
    public void clearDisplayLines() {
        displayLines.clear();
    }
    
    public void addDisplayLine(String lineText) {
        displayLines.add(lineText);
    }
    
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!this.visible) {
            return;
        }
        GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.xPosition, this.yPosition, 0, 46, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
        
        int textColour = 16777215;
        int pad = 2;
        for (int i = 0; i < displayLines.size(); i++) {
            mc.fontRendererObj.drawString(displayLines.get(i),
                    this.xPosition + pad, this.yPosition + pad + mc.fontRendererObj.FONT_HEIGHT * i, textColour);
        }
    }
}
