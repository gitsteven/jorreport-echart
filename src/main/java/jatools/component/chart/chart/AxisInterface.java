package jatools.component.chart.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.Format;

public abstract interface AxisInterface
{
  public abstract void addLabels(String[] paramArrayOfString);

  public abstract void draw(Graphics paramGraphics);

  public abstract boolean getAutoScale();

  public abstract double getAxisEnd();

  public abstract double getAxisStart();

  public abstract double getAxisStepSize();

  public abstract boolean getBarScaling();

  public abstract Dataset[] getDatasets();

  public abstract Globals getGlobals();

  public abstract Gc getGridGc();

  public abstract boolean getGridVis();

  public abstract int getLabelAngle();

  public abstract Color getLabelColor();

  public abstract Font getLabelFont();

  public abstract Format getLabelFormat();

  public abstract int getLabelPrecision();

  public abstract boolean getLabelVis();

  public abstract Gc getLineGc();

  public abstract boolean getLineVis();

  public abstract boolean getLogScaling();

  public abstract int getMajTickLength();

  public abstract boolean getMajTickVis();

  public abstract int getMinTickLength();

  public abstract boolean getMinTickVis();

  public abstract int getNumGrids();

  public abstract int getNumLabels();

  public abstract int getNumMajTicks();

  public abstract int getNumMinTicks();

  public abstract Plotarea getPlotarea();

  public abstract int getSide();

  public abstract Gc getTickGc();

  public abstract Color getTitleColor();

  public abstract Font getTitleFont();

  public abstract String getTitleString();

  public abstract boolean getUseDisplayList();

  public abstract boolean isTitleRotated();

  public abstract boolean isXAxis();

  public abstract void scale();

  public abstract void setAutoScale(boolean paramBoolean);

  public abstract void setAxisEnd(double paramDouble);

  public abstract void setAxisStart(double paramDouble);

  public abstract void setAxisStepSize(double paramDouble);

  public abstract void setBarScaling(boolean paramBoolean);

  public abstract void setDatasets(Dataset[] paramArrayOfDataset);

  public abstract void setGlobals(Globals paramGlobals);

  public abstract void setGridGc(Gc paramGc);

  public abstract void setGridVis(boolean paramBoolean);

  public abstract void setIsXAxis(boolean paramBoolean);

  public abstract void setLabelAngle(int paramInt);

  public abstract void setLabelColor(Color paramColor);

  public abstract void setLabelFont(Font paramFont);

  public abstract void setLabelFormat(int paramInt);

  public abstract void setLabelFormat(Format paramFormat);

  public abstract void setLabelPrecision(int paramInt);

  public abstract void setLabelVis(boolean paramBoolean);

  public abstract void setLineGc(Gc paramGc);

  public abstract void setLineVis(boolean paramBoolean);

  public abstract void setLogScaling(boolean paramBoolean);

  public abstract void setMajTickLength(int paramInt);

  public abstract void setMajTickVis(boolean paramBoolean);

  public abstract void setMinTickLength(int paramInt);

  public abstract void setMinTickVis(boolean paramBoolean);

  public abstract void setNumGrids(int paramInt);

  public abstract void setNumLabels(int paramInt);

  public abstract void setNumMajTicks(int paramInt);

  public abstract void setNumMinTicks(int paramInt);

  public abstract void setPlotarea(Plotarea paramPlotarea);

  public abstract void setSide(int paramInt);

  public abstract void setTickGc(Gc paramGc);

  public abstract void setTitleColor(Color paramColor);

  public abstract void setTitleFont(Font paramFont);

  public abstract void setTitleRotated(boolean paramBoolean);

  public abstract void setTitleString(String paramString);

  public abstract void setUseDisplayList(boolean paramBoolean);
}

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.AxisInterface
 * JD-Core Version:    0.6.2
 */