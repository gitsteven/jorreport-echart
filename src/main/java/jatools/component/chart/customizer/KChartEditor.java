/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.Chart;
/*     */ import jatools.component.chart.ChartFactory;
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.CustomizerFactory;
/*     */ import jatools.component.chart.chart.CandlestickChart;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.DateAxis;
/*     */ import jatools.component.chart.chart.HiLoBarChart;
/*     */ import jatools.component.chart.chart.HiLoCloseChart;
/*     */ import jatools.component.chart.chart.HorizHiLoBarChart;
/*     */ import jatools.component.chart.component.MoreButton;
/*     */ import jatools.component.chart.component.ReportComboBox;
/*     */ import jatools.component.chart.servlet.Bean;
/*     */ import jatools.designer.Main;
/*     */ import jatools.designer.ReportEditor;
/*     */ import jatools.util.Map;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ public class KChartEditor extends JDialog
/*     */   implements ChangeListener, ActionListener
/*     */ {
/*     */   private Chart javaChart;
/*     */   Tabs tab;
/*     */   ChangeListener pcl;
/*  51 */   ArrayList support = new ArrayList();
/*     */   JButton okButton;
/*     */   public boolean done;
/*     */   private ReportComboBox typeBox;
/*     */ 
/*     */   public KChartEditor()
/*     */   {
/*     */   }
/*     */ 
/*     */   public KChartEditor(Frame f, Chart javaChart)
/*     */   {
/*  75 */     super(f, "图表属性", false);
/*  76 */     this.javaChart = javaChart;
/*     */ 
/*  78 */     getContentPane().setLayout(new BorderLayout(2, 2));
/*     */ 
/*  81 */     JPanel panel = new JPanel();
/*     */ 
/*  86 */     GridBagConstraints gbc = new GridBagConstraints();
/*  87 */     gbc.insets = CommonFinal.INSETS;
/*  88 */     gbc.fill = 2;
/*     */ 
/*  90 */     this.okButton = new MoreButton("确定");
/*  91 */     this.okButton.setActionCommand("ok");
/*  92 */     this.okButton.addActionListener(this);
/*     */ 
/*  94 */     panel.setLayout(new GridBagLayout());
/*     */ 
/*  96 */     JLabel ltype = new JLabel("图表类型");
/*  97 */     ltype.setPreferredSize(CommonFinal.LABEL_SIZE);
/*     */ 
/*  99 */     this.typeBox = new ReportComboBox();
/* 100 */     this.typeBox.setType((String)javaChart.getProperties().get("chartType"));
/* 101 */     this.typeBox.addActionListener(this);
/*     */ 
/* 103 */     gbc.gridwidth = 1;
/* 104 */     gbc.weightx = 0.0D;
/* 105 */     panel.add(ltype, gbc);
/* 106 */     gbc.weightx = 1.0D;
/* 107 */     gbc.gridwidth = 0;
/*     */ 
/* 111 */     panel.add(this.typeBox, gbc);
/*     */ 
/* 113 */     String type1 = (String)javaChart.getProperties().get("chartType");
/*     */ 
/* 115 */     if (type1 == null) {
/* 116 */       type1 = "1";
/*     */     }
/*     */ 
/* 119 */     this.tab = CustomizerFactory.createInstance(javaChart.chart.getClass());
/* 120 */     this.tab.setJavaChart(javaChart, Integer.parseInt(type1));
/*     */ 
/* 122 */     this.tab.addChangeListener(this);
/*     */ 
/* 124 */     JPanel p = new JPanel(new FlowLayout(2));
/* 125 */     p.add(this.okButton);
/*     */ 
/* 127 */     MoreButton cancelButton = new MoreButton("取消");
/* 128 */     cancelButton.setActionCommand("cancel");
/* 129 */     cancelButton.addActionListener(this);
/* 130 */     p.add(cancelButton);
/*     */ 
/* 148 */     getContentPane().add(panel, "North");
/* 149 */     getContentPane().add(this.tab, "Center");
/* 150 */     getContentPane().add(p, "South");
/* 151 */     setSize(295, 570);
/* 152 */     setLocationRelativeTo(f);
/*     */ 
/* 154 */     setLocation(f.getWidth() - getWidth() - 20, (int)getLocation().getY());
/*     */   }
/*     */ 
/*     */   public Chart getJavaChart()
/*     */   {
/* 163 */     return this.javaChart;
/*     */   }
/*     */ 
/*     */   public void fireChange(Object object)
/*     */   {
/* 172 */     if (this.support.size() > 0) {
/* 173 */       for (int i = 0; i < this.support.size(); i++) {
/* 174 */         ChangeListener l = (ActionListener)this.support.get(i);
/* 175 */         l.fireChange(null);
/*     */       }
/*     */     }
/*     */ 
/* 179 */     Map map = new Map();
/* 180 */     map.put("chartType", this.javaChart.getProperties().get("chartType"));
/* 181 */     this.javaChart.chart.applyProperties(map);
/*     */ 
/* 183 */     this.javaChart.setProperties(ChartFactory.asProperties(map));
/*     */ 
/* 185 */     SwingUtilities.invokeLater(new Runnable() {
/*     */       public void run() {
/* 187 */         Main.getInstance().getActiveEditor().repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void addChangeListener(ChangeListener p)
/*     */   {
/* 200 */     this.support.add(p);
/*     */   }
/*     */ 
/*     */   public void removeChangeListener(ChangeListener l)
/*     */   {
/* 209 */     this.support.remove(l);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 218 */     if (e.getActionCommand().equals("ok")) {
/* 219 */       hide();
/* 220 */       this.done = true;
/* 221 */     } else if (e.getActionCommand().equals("cancel")) {
/* 222 */       hide();
/*     */     } else {
/* 224 */       transferChartType();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void transferChartType()
/*     */   {
/* 231 */     Bean tmpbean = ChartFactory.createInstance(Integer.parseInt(this.typeBox.getType()));
/*     */ 
/* 233 */     this.javaChart.clearProperties();
/*     */ 
/* 236 */     this.javaChart.getProperties().put("chartType", this.typeBox.getType());
/* 237 */     tmpbean.setProperties(this.javaChart.getProperties());
/*     */ 
/* 239 */     ChartInterface tmpChart = tmpbean.getChart();
/*     */ 
/* 244 */     boolean canTrans = false;
/*     */ 
/* 246 */     if (this.javaChart.getPlotData() != null) {
/* 247 */       if (((this.javaChart.chart.getXAxis() instanceof DateAxis)) && 
/* 248 */         ((tmpChart.getXAxis() instanceof DateAxis))) {
/* 249 */         if ((tmpChart instanceof HiLoCloseChart)) {
/* 250 */           if (this.javaChart.getPlotData().size() >= 3)
/* 251 */             canTrans = true;
/*     */         }
/* 253 */         else if ((tmpChart instanceof CandlestickChart)) {
/* 254 */           if (this.javaChart.getPlotData().size() >= 4)
/* 255 */             canTrans = true;
/*     */         }
/*     */         else
/* 258 */           canTrans = true;
/*     */       }
/* 260 */       else if ((!(this.javaChart.chart.getXAxis() instanceof DateAxis)) && 
/* 261 */         (!(tmpChart.getXAxis() instanceof DateAxis))) {
/* 262 */         if (((tmpChart instanceof HiLoBarChart)) || ((tmpChart instanceof HorizHiLoBarChart))) {
/* 263 */           if (this.javaChart.getPlotData().size() >= 2)
/* 264 */             canTrans = true;
/*     */         }
/*     */         else
/* 267 */           canTrans = true;
/*     */       }
/* 269 */       else if ((!(this.javaChart.chart.getXAxis() instanceof DateAxis)) && 
/* 270 */         ((tmpChart.getXAxis() instanceof DateAxis))) {
/* 271 */         canTrans = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 276 */     Bean bean = ChartFactory.createInstance(Integer.parseInt(this.typeBox.getType()));
/*     */ 
/* 279 */     bean.setProperties(this.javaChart.getProperties());
/* 280 */     bean.setGraphReader(this.javaChart.getReader());
/* 281 */     bean.setGraphShowData(this.javaChart.getPlotData());
/* 282 */     bean.setGraphLabelField(this.javaChart.getLabelField());
/*     */ 
/* 284 */     this.javaChart.chart = bean.getChart();
/*     */ 
/* 293 */     Tabs tmptab = CustomizerFactory.createInstance(this.javaChart.chart.getClass());
/* 294 */     tmptab.setJavaChart(this.javaChart, Integer.parseInt(this.typeBox.getType()));
/* 295 */     tmptab.addChangeListener(this);
/*     */ 
/* 297 */     getContentPane().remove(this.tab);
/* 298 */     getContentPane().add(tmptab, "Center");
/*     */ 
/* 300 */     this.tab = tmptab;
/* 301 */     getContentPane().validate();
/* 302 */     fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.KChartEditor
 * JD-Core Version:    0.6.2
 */