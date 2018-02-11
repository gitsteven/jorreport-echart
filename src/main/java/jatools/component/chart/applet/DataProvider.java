package jatools.component.chart.applet;

import java.util.Iterator;

public abstract interface DataProvider
{
  public abstract Iterator getDatasets();

  public abstract String getUniqueIdentifier();
}

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.applet.DataProvider
 * JD-Core Version:    0.6.2
 */