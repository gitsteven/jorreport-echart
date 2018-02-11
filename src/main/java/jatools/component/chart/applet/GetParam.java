package jatools.component.chart.applet;

import jatools.component.chart.chart.ChartInterface;
import jatools.component.chart.chart.Dataset;
import java.awt.Image;
import java.io.InputStream;

public abstract interface GetParam
{
  public abstract Dataset getDataset(ChartInterface paramChartInterface, int paramInt);

  public abstract void getMyDatasets(String paramString);

  public abstract String getParameter(String paramString);

  public abstract Image makeURLImage(String paramString);

  public abstract InputStream openURL(String paramString);

  public abstract void setDataProvider(DataProvider paramDataProvider);

  public abstract DataProvider getDataProvider();
}

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.applet.GetParam
 * JD-Core Version:    0.6.2
 */