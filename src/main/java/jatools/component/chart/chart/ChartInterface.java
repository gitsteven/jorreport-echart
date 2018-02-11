package jatools.component.chart.chart;

import jatools.component.chart.Tip;
import jatools.util.Map;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

public abstract interface ChartInterface
{
  public abstract void addDataset(String paramString, double[] paramArrayOfDouble);

  public abstract void addDataset(String paramString, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  public abstract void addDataset(String paramString, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3, double[] paramArrayOfDouble4);

  public abstract void addDataset(String paramString, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, String[] paramArrayOfString);

  public abstract void addDataset(String paramString, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3);

  public abstract void addDataset(String paramString, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3, String[] paramArrayOfString);

  public abstract void addDataset(String paramString, double[] paramArrayOfDouble, String[] paramArrayOfString);

  public abstract void addDataset(Dataset paramDataset);

  public abstract void deleteDataset(String paramString);

  public abstract void deleteDataset(Dataset paramDataset);

  public abstract void drawGraph();

  public abstract void drawGraph(Graphics paramGraphics);

  public abstract Background getBackground();

  public abstract DataRepresentation getDataRepresentation();

  public abstract Dataset[] getDatasets();

  public abstract DisplayList getDisplayList();

  public abstract Globals getGlobals();

  public abstract int getHeight();

  public abstract Image getImage();

  public abstract LegendInterface getLegend();

  public abstract String getName();

  public abstract int getNumDatasets();

  public abstract Plotarea getPlotarea();

  public abstract RotateString getStringRotator();

  public abstract boolean getUseDisplayList();

  public abstract int getWidth();

  public abstract AxisInterface getXAxis();

  public abstract int getXOffset();

  public abstract AxisInterface getYAxis();

  public abstract int getYOffset();

  public abstract boolean isLegendVisible();

  public abstract boolean isThreeD();

  public abstract boolean isXAxisVisible();

  public abstract boolean isYAxisVisible();

  public abstract void paint(Component paramComponent, Graphics paramGraphics);

  public abstract void paint(Graphics paramGraphics);

  public abstract void resize(int paramInt1, int paramInt2);

  public abstract void setDataRepresentation(DataRepresentation paramDataRepresentation);

  public abstract void setDisplayList(DisplayList paramDisplayList);

  public abstract void setHeight(int paramInt);

  public abstract void setImage(Image paramImage);

  public abstract void setLegendVisible(boolean paramBoolean);

  public abstract void setName(String paramString);

  public abstract void setStringRotator(RotateString paramRotateString);

  public abstract void setThreeD(boolean paramBoolean);

  public abstract void setUseDisplayList(boolean paramBoolean);

  public abstract void setWidth(int paramInt);

  public abstract void setXAxis(AxisInterface paramAxisInterface);

  public abstract void setXAxisVisible(boolean paramBoolean);

  public abstract void setXOffset(int paramInt);

  public abstract void setYAxis(AxisInterface paramAxisInterface);

  public abstract void setYAxisVisible(boolean paramBoolean);

  public abstract void setYOffset(int paramInt);

  public abstract void applyProperties(Map paramMap);

  public abstract void paint(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2);

  public abstract void addDataset(String paramString, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, String[] paramArrayOfString, Tip[] paramArrayOfTip);
}

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.ChartInterface
 * JD-Core Version:    0.6.2
 */