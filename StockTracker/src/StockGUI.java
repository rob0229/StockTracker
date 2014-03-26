import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;




public class StockGUI extends JFrame {
	//menu bar
	private JMenu menuAbout; 
    private JMenuBar menuBar;
    private JMenuItem menuItem;
	// Title for stock list
	private String[] symbolNameHeader = {"Symbol Name"};
	// Keeps track of selected items in list
	private int[] clickedRows;
	// Creates index of where in the excel sheet selected stocks a re
	private ArrayList<Integer> stockIndex = new ArrayList<Integer>();
	// Object of stock list pulled from excel
	Object[][] addedList;
	// Object of column names
	Object[] getColumns;
	// Object of recommendations
	ArrayList<Object> advice = new ArrayList<Object>();
	
	// Strategy pattern
	Strategy strategy = new BuyLow();

	JToggleButton buyLowButton, buyRiseButton, buyRandomButton, buyCustomButton;
	
	private boolean clickedStrategy = false;
	
	JPanel stockListPanel1;
	JPanel strategyPanel;
	JPanel stockDisplayPanel;
	JTable stockTable;
	JTable symbolTable; 
	JScrollPane displayScrollPane;
	JScrollPane stockScrollPane;
	JButton stockButton = new JButton("Add Stocks");

	DefaultTableModel stockModel;
	
	//constructor
	StockGUI(Object[][] getStockList, String[] getColumnLabels){		
		addedList = getStockList;
		getColumns = getColumnLabels;
		init(addedList, getColumns);
	}
	
	public void init( final Object[][] addedList, final Object[] getColumns){
		 menuBar = new JMenuBar();
	     menuAbout = new JMenu("About");
	        
		stockListPanel1 = new JPanel();
		strategyPanel = new JPanel();
		stockDisplayPanel = new JPanel();	
		
		//Strategy Radio Buttons
		buyLowButton = new JRadioButton("Buy when stock price decreases");
		buyRiseButton = new JRadioButton("Buy when stock price increases");
		buyRandomButton = new JRadioButton("Buy Randomly");
		buyCustomButton = new JRadioButton("Buy Custom");
		
		buyLowButton.setEnabled(false);
		buyRiseButton.setEnabled(false);
		buyRandomButton.setEnabled(false);
		buyCustomButton.setEnabled(false);
		
		ButtonGroup stratButtonGroup = new ButtonGroup();
		stratButtonGroup.add(buyLowButton);
		stratButtonGroup.add(buyRiseButton);
		stratButtonGroup.add(buyRandomButton);
		stratButtonGroup.add(buyCustomButton);
		
		buyLowButton.setActionCommand("bl");
		buyLowButton.setSelected(true);
		buyRiseButton.setActionCommand("br");
		buyRandomButton.setActionCommand("brand");
		buyCustomButton.setActionCommand("bc");
		
		//stockTable = new JTable(addedList, getColumns);
		stockTable = new JTable();
		stockModel = (DefaultTableModel) stockTable.getModel();
		for(int i = 0; i < getColumns.length; i++){
			stockModel.addColumn(getColumns[i]);	
		}
		
		symbolTable = new JTable(addedList, symbolNameHeader);
		displayScrollPane = new JScrollPane(stockTable);		
		stockScrollPane = new JScrollPane(symbolTable);
		
		stockListPanel1.add(stockScrollPane);
		stockDisplayPanel.add(displayScrollPane);
		
		stockListPanel1.setBorder(BorderFactory.createTitledBorder(null, "Stock List Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));		
		strategyPanel.setBorder(BorderFactory.createTitledBorder(null, "Strategy Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		stockDisplayPanel.setBorder(BorderFactory.createTitledBorder(null, "Stock Display Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		
		displayScrollPane.setPreferredSize(new Dimension(980,160));
		
		stockButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				boolean check = true;
				
				clickedRows = symbolTable.getSelectedRows();
				
				if(clickedRows.length > 0)
				{
					buyLowButton.setEnabled(true);
					buyRiseButton.setEnabled(true);
					buyRandomButton.setEnabled(true);
					buyCustomButton.setEnabled(true);
				}
				
				for(int i = 0; i < clickedRows.length; i++){
					for (int j = 0; j < stockIndex.size(); j++){
						if(stockIndex.get(j) == clickedRows[i]){
							check = false;
						}
					}
				}
				
				if(check){
					for(int i = 0; i < clickedRows.length; i++){
						stockModel.addRow(addedList[clickedRows[i]]);
						stockIndex.add(clickedRows[i]);
					}
					
					
					clickedStrategy = true;						
					advice = notifyStrategy(stockIndex);
					for(int i = 0; i < stockIndex.size(); i++){
						stockModel.setValueAt(advice.get(i), i, 11);
					}
						
				}
			}
		});
		
		ActionListener myListener = new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		        
		        if (e.getActionCommand() == "bl") {
		           setStrategy(new BuyLow());
		            
		        }else if(e.getActionCommand() == "br") {
		        	setStrategy(new BuyRise());
		        }
		        else if(e.getActionCommand() == "brand") {
		        	setStrategy(new BuyRandom());
		        }
		        else {
		        	System.out.println("custom");
		        	setStrategy(new BuyCustom());
		        } 
		        
		        refreshTable(addedList);
		    }
		};
		
		
		buyLowButton.addActionListener(myListener);	
		buyRiseButton.addActionListener(myListener);	
		buyRandomButton.addActionListener(myListener);
		buyCustomButton.addActionListener(myListener);
		
		strategyPanel.add(buyLowButton);
		strategyPanel.add(buyRiseButton);
		strategyPanel.add(buyRandomButton);
		strategyPanel.add(buyCustomButton);
		
		 	
	        
	        menuAbout.setMnemonic(KeyEvent.VK_A);
	        menuAbout.getAccessibleContext().setAccessibleDescription(
	                "The only menu in this program that has menu items");
	        
	        menuBar.add(menuAbout);
	        
	      //Adds programmer names with a icon to the about menu
	        menuItem = new JMenuItem("Charlie Sun",
                    new ImageIcon("src/sun.jpg"));
	        	menuItem.setMnemonic(KeyEvent.VK_S);
	        	
	        menuAbout.add(menuItem);
	        	menuItem = new JMenuItem("Rob Close",
	                        new ImageIcon("src/close.png"));
	    	        	menuItem.setMnemonic(KeyEvent.VK_C);
	    	        		menuAbout.add(menuItem);
	    	        		
	       //adds menu bar to jframe 
	       setJMenuBar(menuBar);
		
		 GroupLayout stratPanelLayout = new GroupLayout(strategyPanel);
		 strategyPanel.setLayout(stratPanelLayout);
	        stratPanelLayout.setHorizontalGroup(
	            stratPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(stratPanelLayout.createSequentialGroup()
	                .addGroup(stratPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addComponent(buyCustomButton)
	                    .addComponent(buyRandomButton)
	                    .addComponent(buyRiseButton)
	                    .addComponent(buyLowButton))
	                .addContainerGap(102, Short.MAX_VALUE))
	        );
	        stratPanelLayout.setVerticalGroup(
	            stratPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(stratPanelLayout.createSequentialGroup()
	                .addGap(20, 20, 20)
	                .addComponent(buyLowButton)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(buyRiseButton)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(buyRandomButton)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(buyCustomButton)
	                .addContainerGap(30, Short.MAX_VALUE))
	        );

		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		
		layout.setHorizontalGroup(
	            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	            		.addGap(20,200,200)
	                .addComponent(stockScrollPane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(stockButton)
	                .addGap(20,20,20)
	                .addComponent(strategyPanel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
	                )
	            .addComponent(stockDisplayPanel, GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
	        );
		
		layout.setVerticalGroup(
		            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		            		.addContainerGap(23, Short.MAX_VALUE)
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addGroup(layout.createSequentialGroup()
		                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
		                            .addComponent(strategyPanel, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
		                            .addComponent(stockScrollPane, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
		                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))
		                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
		                        .addGap(0,150, 1000)
		                        .addComponent(stockButton)
		                        .addGap(0, 5, 1000)))
		                .addComponent(stockDisplayPanel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
		        );

		
		setResizable(false);
		pack();
		
	}

	public void setStrategy(Strategy s){
		strategy = s;
	}
	
	public ArrayList<Object> notifyStrategy(ArrayList<Integer> stockIndex){
		advice.clear();
		for(int i = 0; i < stockIndex.size(); i++){
			advice.add(strategy.getRecommendation(stockIndex.get(i)));
			
		}
		
		return advice;
	}
	
	public void refreshTable(Object[][] stockList){
		for(int i = 0; i < stockIndex.size(); i++){
			for(int j = 0; j < ExcelData.totalcols; j++){
				stockModel.setValueAt(stockList[stockIndex.get(i)][j], i, j);
				
			}
		}
		
		if(clickedStrategy){
			advice = notifyStrategy(stockIndex);
			for(int i = 0; i < stockIndex.size(); i++){
				stockModel.setValueAt(advice.get(i), i, 11);
				
				
				
			}
		}
		
		super.repaint();
		super.revalidate();
			
	}
	
}	  