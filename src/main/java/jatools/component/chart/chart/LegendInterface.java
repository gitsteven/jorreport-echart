package jatools.component.chart.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract interface LegendInterface
{
  public abstract void draw(Graphics paramGraphics);

  public abstract Gc getBackgroundGc();

  public abstract boolean getBackgroundVisible();

  public abstract double getIconGap();

  public abstract double getIconHeight();

  public abstract double getIconWidth();

  public abstract Color getLabelColor();

  public abstract Font getLabelFont();

  public abstract boolean getInvertLegend();

  public abstract double getLlX();

  public abstract double getLlY();

  public abstract double getUrX();

  public abstract double getUrY();

  public abstract boolean getUseDisplayList();

  public abstract boolean getVerticalLayout();

  public abstract void resize(int paramInt1, int paramInt2);

  public abstract void setBackgroundGC(Gc paramGc);

  public abstract void setBackgroundVisible(boolean paramBoolean);

  public abstract void setIconGap(double paramDouble);

  public abstract void setIconHeight(double paramDouble);

  public abstract void setIconWidth(double paramDouble);

  public abstract void setInvertLegend(boolean paramBoolean);

  public abstract void setLabelColor(Color paramColor);

  public abstract void setLabelFont(Font paramFont);

  public abstract void setLlX(double paramDouble);

  public abstract void setLlY(double paramDouble);

  public abstract void setUseDisplayList(boolean paramBoolean);

  public abstract void setVerticalLayout(boolean paramBoolean);

  public abstract String toString();
}

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.LegendInterface
 * JD-Core Version:    0.6.2
 */