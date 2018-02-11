package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Icon;

public abstract interface FillStyleInterface
{
  public static final int FILL_SOLID = 0;
  public static final int FILL_GRADIENT = 1;
  public static final int FILL_TEXTURE = 2;
  public static final int FILL_IMAGE = 3;
  public static final int LINE_SOLID = -1;
  public static final int LINE_DASH = 0;
  public static final int LINE_DOT = 1;
  public static final int LINE_DOT_DASH = 2;
  public static final int MK_NONE = -1;
  public static final int MK_SQUARE = 0;
  public static final int MK_DIAMOND = 1;
  public static final int MK_CIRCLE = 2;
  public static final int MK_TRIANGLE = 3;
  public static final int ICON_WIDTH = 20;
  public static final int ICON_HEIGHT = 10;

  public abstract Component createLabel(Dimension paramDimension);

  public abstract Icon createIcon(Dimension paramDimension);

  public abstract int getType();

  public abstract void setToGc(Gc paramGc);

  public abstract void getFromGc(Gc paramGc);
}

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.FillStyleInterface
 * JD-Core Version:    0.6.2
 */