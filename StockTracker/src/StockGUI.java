import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;




public class StockGUI extends JFrame {
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

	StrategyButtonListener myListener = new StrategyButtonListener();
	JToggleButton buyLowButton, buyRiseButton, buyRandomButton, buyCustomButton;
	
	private boolean clickedStrategy = false;
	
	JPanel stockListPanel1;
	JPanel strategyPanel2;
	JPanel stockDisplayPanel3;
	JTable stockTable;
	JTable symbolTable; 
	JScrollPane displayScrollPane;
	JScrollPane stockScrollPane;
	JButton stockButton = new JButton("Add Stocks");

	DefaultTableModel stockModel;
	
	StockGUI(Object[][] getStockList, String[] getColumnLabels){		
		addedList = getStockList;
		getColumns = getColumnLabels;
		init(addedList, getColumns);
	}
	
	public void init( final Object[][] addedList, final Object[] getColumns){
		stockListPanel1 = new JPanel();
		strategyPanel2 = new JPanel();
		stockDisplayPanel3 = new JPanel();	
		
		//Strategy Radio Buttons
		buyLowButton = new JRadioButton("Buy when stock price decreases");
		buyRiseButton = new JRadioButton("Buy when stock price increases");
		buyRandomButton = new JRadioButton("Buy Randomly");
		buyCustomButton = new JRadioButton("Buy Custom");
		
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
		stockDisplayPanel3.add(displayScrollPane);
		
		stockListPanel1.setBorder(BorderFactory.createTitledBorder(null, "Stock List Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));		
		strategyPanel2.setBorder(BorderFactory.createTitledBorder(null, "Strategy Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		stockDisplayPanel3.setBorder(BorderFactory.createTitledBorder(null, "Stock Display Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		
		displayScrollPane.setPreferredSize(new Dimension(980,160));
		
		stockButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				boolean check = true;
				
				clickedRows = symbolTable.getSelectedRows();
				
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
					advice = myListener.notifyStrategy(stockIndex);
					for(int i = 0; i < stockIndex.size(); i++){
						stockModel.setValueAt(advice.get(i), i, 11);
					}
						
				}
			}
		});
		
		
		buyLowButton.addActionListener(myListener);
		buyLowButton.addChangeListener(myListener);
		buyLowButton.addItemListener(myListener);
		
		buyRiseButton.addActionListener(myListener);
		buyRiseButton.addChangeListener(myListener);
		buyRiseButton.addItemListener(myListener);
		
		buyRandomButton.addActionListener(myListener);
		buyRandomButton.addChangeListener(myListener);
		buyRandomButton.addItemListener(myListener);
		
		buyCustomButton.addActionListener(myListener);
		buyCustomButton.addChangeListener(myListener);
		buyCustomButton.addItemListener(myListener);
		
		strategyPanel2.add(buyLowButton);
		strategyPanel2.add(buyRiseButton);
		strategyPanel2.add(buyRandomButton);
		strategyPanel2.add(buyCustomButton);
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)		
				.addGap(50)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)				
				.addComponent(stockDisplayPanel3, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)	
				.addGroup(layout.createSequentialGroup()
					.addComponent(stockScrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addComponent(stockButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(200)
						.addComponent(strategyPanel2, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))));
		
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
			.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(stockScrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)	
				.addComponent(stockButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
				.addComponent(strategyPanel2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
			
				.addComponent(stockDisplayPanel3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)));
		
		setResizable(false);
		pack();
		
	}
	
	public void refreshTable(Object[][] stockList){
		for(int i = 0; i < stockIndex.size(); i++){
			for(int j = 0; j < ExcelTest.totalcols; j++){
				stockModel.setValueAt(stockList[stockIndex.get(i)][j], i, j);
			}
		}
		
		if(clickedStrategy){
			advice = myListener.notifyStrategy(stockIndex);
			for(int i = 0; i < stockIndex.size(); i++){
				stockModel.setValueAt(advice.get(i), i, 11);
			}
		}
		
		super.repaint();
		super.revalidate();
			
	}
}
