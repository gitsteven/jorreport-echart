package jatools.component.chart.chart;

public abstract interface BarLineInterface extends BarInterface, LineInterface
{
  public static final int LINE = 1;
  public static final int BAR = 0;

  public abstract void setChartType(int paramInt1, int paramInt2);

  public abstract int getChartType(int paramInt);

  public abstract boolean getStackedBar();

  public abstract void setStackedBar(boolean paramBoolean);
}

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.BarLineInterface
 * JD-Core Version:    0.6.2
 */