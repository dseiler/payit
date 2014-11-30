/*
 * Created on 02.11.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.calendar;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.*;

/**
 * @author Daniel Seiler
 */
public class EasyDatePickerSwing extends JDialog implements MouseListener, ItemListener, ActionListener {

	private final static int LEFT_GAP             = 5;
	private final static int RIGHT_GAP            = 5;
	private final static int BOTTOM_GAP           = 5;
	private final static int TOP_GAP              = 20;
        
	private final static byte BG                  = 0;
	private final static byte WEEKEND_BG          = 1;
	private final static byte NEIGHBOR_MONTH_DATE = 2;
	private final static byte HIGHLIGHT           = 3;
	private final static byte UNSELECTABLE        = 4;
	private final static byte MAX_COLORS          = 5;

	private final static int ARROW_STYLE 		 = 0;
	private final static int DROPDOWN_STYLE 		 = 1;

	private int iCellWidth;
	private int iCellHeight;
	private int iCalendarWidth;
	private int iCalendarHeight;
	private byte highlightedCell = -1;
    
	private Choice months = new Choice();
	private Choice years = new Choice();
    
	//JComboBox    months,years;
	//Button       today;
	private JButton btnToday;
	private JButton btnOk;
	private JButton btnClose;
    
	private Color[] calendarColors= new Color[MAX_COLORS];
    
	private JPanel calPanel = new JPanel();
	private JPanel northPanel = new JPanel();
	private JPanel southPanel = new JPanel();
    
	private JLabel monthYearLabel = new JLabel();
    
	private boolean is3D = false;
    
	private JTextField dest = null;
    
	private Font calFont = new Font("Default",Font.PLAIN,10);// Lucida Console,Comic Sans MS,Miriam Fixed
        
	// for the arrow style
	private int selectedYear;
	private int selectedMonth;    
    
	private int displayMode = ARROW_STYLE; // if we have a drop down list or arrows

	private CalendarCell[] calendarCells;
	private EasyDatePicker datePicker;
	
	public static void main(String[] args)
	{
		JFrame mainFrame = new JFrame("Easy Date Picker Swing");
		new EasyDatePickerSwing(mainFrame,null).show();
	}

	public EasyDatePickerSwing(JDialog parentDialog,JTextField textDest) {
		super(parentDialog);
		init(textDest);
	}
    
	public EasyDatePickerSwing(JFrame parentFrame,JTextField textDest) {
		super(parentFrame);
		init(textDest);
	}
	
	private void init(JTextField textDest) {
		datePicker = new EasyDatePicker();

		// initialize this calendar instance
		this.dest = textDest;
        
		// set the preselected date to the date in the date text field
		if(dest != null && dest.getText().trim().length() > 0){
			datePicker.setPreselectedDate(dest.getText());
		}
        
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {closeCalendar();}
		});
		setSize(new Dimension(200,220));
		setResizable(false);
		setModal(true);
        
		northPanel.setLayout(new BorderLayout());
		southPanel.setLayout(new FlowLayout());
		calPanel.setPreferredSize(new Dimension(100,100));
		northPanel.setPreferredSize(new Dimension(100,20));
		southPanel.setPreferredSize(new Dimension(100,30));

		// initialize the colors	
		calendarColors[BG] = new Color(Color.lightGray.getRGB());
		calendarColors[WEEKEND_BG] = new Color(Color.white.darker().getRGB());
		calendarColors[NEIGHBOR_MONTH_DATE] = new Color(Color.gray.getRGB());
		calendarColors[HIGHLIGHT] = new Color(Color.red.getRGB());
		calendarColors[UNSELECTABLE] = new Color(Color.white.getRGB());
		
		selectedYear = datePicker.getCurrentDateField(EasyDatePicker.YEAR);
		selectedMonth = datePicker.getCurrentDateField(EasyDatePicker.MONTH);
        
		calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
		//months = new JComboBox();
				/*
				months.addMouseListener(new MouseAdapter() {
						public void mouseEntered(MouseEvent e) {
										System.out.println("pressed");
						}
				});*/
		//months.setPreferredSize(new Dimension(90,20));
		//years = new JComboBox();
		//years.setPreferredSize(new Dimension(60,20));
		//today = new Button( "!" );
		//today.setPreferredSize(new Dimension(20,20));
		//years.setSelectedItem( String.valueOf( calToday.get( CALYEAR ) ) );
		//months.setSelectedIndex( calToday.get( CALMONTH ) );
        
		// for the arrow style
		JPanel arrowPanelWest = new JPanel(new GridLayout(1,2));
		JButton btnDblArrowWest = new JButton("<<");
		btnDblArrowWest.setFont(calFont);
		btnDblArrowWest.setPreferredSize(new Dimension(20,16));
		btnDblArrowWest.setMargin(new Insets(0,0,0,0));
		btnDblArrowWest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedYear--;
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				repaint();
			}
		});
        
		JButton btnArrowWest = new JButton("<");
		btnArrowWest.setFont(calFont);
		btnArrowWest.setPreferredSize(new Dimension(20,16));
		btnArrowWest.setMargin(new Insets(0,0,0,0));
		btnArrowWest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedMonth--;
				if(selectedMonth == -1){
					selectedMonth = 11;
					selectedYear--;
				}
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				repaint();
			}
		});
		JPanel arrowPanelEast = new JPanel(new GridLayout(1,2));
		JButton btnArrowEast = new JButton(">");
		btnArrowEast.setFont(calFont);
		btnArrowEast.setPreferredSize(new Dimension(20,16));
		btnArrowEast.setMargin(new Insets(0,0,0,0));
		btnArrowEast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedMonth++;
				if(selectedMonth == 12){
					selectedMonth = 0;
					selectedYear++;
				}
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				repaint();
			}
		});
		JButton btnDblArrowEast = new JButton(">>");
		btnDblArrowEast.setFont(calFont);
		btnDblArrowEast.setPreferredSize(new Dimension(20,16));
		btnDblArrowEast.setMargin(new Insets(0,0,0,0));
		btnDblArrowEast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedYear++;
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				repaint();
			}
		});
        
		monthYearLabel.setFont(calFont);
		monthYearLabel.setPreferredSize(new Dimension(50,15));
		monthYearLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
		// add the month names
		String[] monthNames = datePicker.getMonthNames();
		for(int i=0;i<monthNames.length;i++){
			months.addItem(monthNames[i]);
		}
		// add the years
		years.addItem("2003");
		years.addItem("2004");
		years.addItem("2005");
		years.addItem("2006");

		refreshMonthYearDisplay();
        
		arrowPanelWest.add(btnDblArrowWest);
		arrowPanelWest.add(btnArrowWest);
		arrowPanelEast.add(btnArrowEast);
		arrowPanelEast.add(btnDblArrowEast);
        		
		if(displayMode == DROPDOWN_STYLE)
		{
			northPanel.add( months,BorderLayout.CENTER );
			northPanel.add( years,BorderLayout.EAST );
		}
		else if(displayMode == ARROW_STYLE)
		{
			northPanel.add(arrowPanelWest,BorderLayout.WEST);
			northPanel.add(monthYearLabel,BorderLayout.CENTER);
			northPanel.add(arrowPanelEast,BorderLayout.EAST);
		}
        
		btnOk = new JButton("ok");
		btnOk.setPreferredSize(new Dimension(50,20));
		btnOk.setFont(calFont);
        
		btnToday = new JButton("today");
		btnToday.setPreferredSize(new Dimension(60,20));
		btnToday.setFont(calFont);
        
		btnClose = new JButton("close");
		btnClose.setPreferredSize(new Dimension(60,20));
		btnClose.setFont(calFont);
        
		southPanel.add(btnOk);
		southPanel.add(btnToday);
		southPanel.add(btnClose);
                
		calPanel.addMouseListener( this );
		years.addItemListener( this );
		months.addItemListener( this );
		//today.addActionListener( this );
		btnOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnDateFromHighlitedCell();
			}
		});
		btnToday.addActionListener(this);
		btnClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeCalendar();
			}
		});
        
		// Adding the panels to the frame
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(calPanel,BorderLayout.CENTER);
		getContentPane().add(northPanel,BorderLayout.NORTH);
		getContentPane().add(southPanel,BorderLayout.SOUTH);

	}
	
	public EasyDatePicker getModel() {
		return datePicker;
	}
	
	private void refreshMonthYearDisplay()
	{
		monthYearLabel.setText((datePicker.getMonthNames())[selectedMonth]+" "+String.valueOf(selectedYear));
        
		years.select(String.valueOf(selectedYear));
		months.select(selectedMonth);
	}
    
	private void returnDateFromHighlitedCell() {
		if(highlightedCell != -1) {
			Date d = calendarCells[highlightedCell].date;
            
			if(calendarCells[highlightedCell].selectable) {
				if(dest != null) {
					dest.setText(datePicker.formatDate(d));
				} else {
					System.out.println(datePicker.formatDate(d));
				}
				closeCalendar();
			} else {
				System.out.println("Date is not selectable...");
			}
		}
	}

	public void paint(Graphics gr) {
        
		Graphics g = calPanel.getGraphics();
		Dimension d = calPanel.getSize( );
		int i, iOffset;
        
		iCellWidth = ( d.width - LEFT_GAP - RIGHT_GAP ) / 7;
		iCellHeight = ( d.height - TOP_GAP - BOTTOM_GAP ) / 6;
		iCalendarWidth = iCellWidth * 7;
		iCalendarHeight = iCellHeight * 6;
        
		// Draw the surface and 3D effect
		if(is3D){
			g.setColor( Color.white );
		}
		else {
			g.setColor( calendarColors[BG] ); // switching off the 3D Effect DSe
		}
		
		g.drawLine( 0, 0, d.width, 0 );
		g.drawLine( 0, 1, d.width, 1 );
		g.drawLine( 0, 2, d.width, 2 );
		g.drawLine( 0, 0, 0, d.height );
		g.drawLine( 1, 0, 1, d.height );
		g.drawLine( 2, 0, 2, d.height );
        
		if(is3D){
			g.setColor( calendarColors[BG].darker( ) );
		}
		g.drawLine( 2, d.height - 3, d.width, d.height - 3 );
		g.drawLine( 1, d.height - 2, d.width, d.height - 2 );
		g.drawLine( 0, d.height - 1, d.width, d.height - 1 );
		g.drawLine( d.width - 1, 0, d.width - 1, d.height );
		g.drawLine( d.width - 2, 1, d.width - 2, d.height );
		g.drawLine( d.width - 3, 2, d.width - 3, d.height );
		if(is3D){
			g.setColor( calendarColors[BG] );
		}
		g.fillRect( 3, 3, d.width - 6, d.height - 6 );
        
		// Draw the weekend shade
		g.setColor( calendarColors[WEEKEND_BG] );
		// Saturday
		int sat = 6;
		sat -= datePicker.getWeekBeginDay();
		if(sat == 0) sat = 6;
		g.fillRect( LEFT_GAP + iCellWidth * sat, TOP_GAP, iCellWidth, iCalendarHeight );
		// Sunday
		int sun = 0;
		sun -= datePicker.getWeekBeginDay();
		if(sun == -1) sun = 6;
		g.fillRect( LEFT_GAP + iCellWidth * sun, TOP_GAP, iCellWidth, iCalendarHeight );
        
		// Draw the week names now
		Font fontWeeknames = new Font( "Helvetica", Font.PLAIN, 12 );
		g.setFont( fontWeeknames );
		FontMetrics fm = g.getFontMetrics( );
		StringTokenizer dayNamesTokenizer = new StringTokenizer( datePicker.getDayNames(), " " );
		i = 0;
		while( dayNamesTokenizer.hasMoreTokens( ) ) {
			String dayName = dayNamesTokenizer.nextToken( );
			iOffset = fm.stringWidth( dayName ) / 2;
			g.setColor( Color.white );
			g.drawString(  dayName, LEFT_GAP + i * iCellWidth +
			iCellWidth / 2 - iOffset, TOP_GAP - 2 - 1 );
			g.setColor( calendarColors[BG].darker( ) );
			g.drawString(  dayName, LEFT_GAP + i * iCellWidth +
			iCellWidth / 2 + 1 - iOffset, TOP_GAP - 2 );
			i++;
		}
        
		// Draw the grid now
		g.setColor( Color.white );
		for( i=1; i<7; i++ ) {
			g.drawLine( LEFT_GAP, TOP_GAP + i * iCellHeight - 1, LEFT_GAP + iCalendarWidth - 1,
			TOP_GAP + i * iCellHeight - 1 );
		}
		for( i=1; i<8; i++ ) {
			g.drawLine( LEFT_GAP + i * iCellWidth - 1, TOP_GAP, LEFT_GAP + i * iCellWidth - 1,
			TOP_GAP + iCalendarHeight - 1 );
		}
		g.setColor( calendarColors[BG].darker( ) );
		for( i=0; i<6; i++ ) {
			g.drawLine( LEFT_GAP, TOP_GAP + i * iCellHeight, LEFT_GAP + iCalendarWidth - 1,
			TOP_GAP + i * iCellHeight );
		}
		for( i=0; i<7; i++ ) {
			g.drawLine( LEFT_GAP + i * iCellWidth, TOP_GAP, LEFT_GAP + i * iCellWidth,
			TOP_GAP + iCalendarHeight - 1 );
		}
        
		Font fontDates = new Font( "Helvetica", Font.BOLD, (int) ( iCellHeight * 0.7 ) );
		g.setFont( fontDates );
		DrawDates( g );
        
		if( highlightedCell == -1 ) {
			Date preselectedDate = datePicker.getPreselectedDate();
			highlightedCell = (byte)datePicker.getCellNumberForDate(preselectedDate);
		}
		//if( Cell[cbHighlightedCell].bSelectable ) //DSe enable the red caret without being selectable
		highlightCell( highlightedCell, calendarColors[HIGHLIGHT] );
        
		northPanel.repaint();
		southPanel.repaint();
	}
    
	private void DrawDates( Graphics g ) {
		//printDates();
		int i, iHorizOffset, iVertOffset;
		String sBuffer;
        
		FontMetrics fm = g.getFontMetrics( );
		iVertOffset = fm.getHeight( ) / 2 - fm.getMaxDescent( );
		for( i=0; i<42; i++ ) {
			sBuffer = String.valueOf(datePicker.getDateField( calendarCells[i].date, EasyDatePicker.DAY ) );
			iHorizOffset = fm.stringWidth( sBuffer ) / 2;
            
			if( !calendarCells[i].selectable )
				g.setColor( calendarColors[UNSELECTABLE] );
			else if(datePicker.getDateField( calendarCells[i].date, EasyDatePicker.MONTH ) != selectedMonth )
				g.setColor( calendarColors[NEIGHBOR_MONTH_DATE]);
			else
				g.setColor(Color.black);
            
			g.drawString(  sBuffer,
			LEFT_GAP + ( i%7 ) * iCellWidth + iCellWidth / 2 - iHorizOffset,
			TOP_GAP + ( i/7 ) * iCellHeight + iCellHeight / 2 + iVertOffset );
		}
	}
	
	private void highlightCell( int iCellNumber, Color cHighlight ) {
		int	iRow = iCellNumber / 7,
		iColumn = iCellNumber % 7,
		x = iColumn * iCellWidth + 2 + LEFT_GAP,
		y = iRow * iCellHeight + 2 + TOP_GAP;
        
		Graphics g = calPanel.getGraphics( );
        
		g.setColor( cHighlight );
		g.drawLine( x, y, x + iCellWidth - 4, y );
		g.drawLine( x + iCellWidth - 4, y, x + iCellWidth - 4, y + iCellHeight - 4 );
		g.drawLine( x + iCellWidth - 4, y + iCellHeight - 4,  x, y + iCellHeight - 4 );
		g.drawLine( x, y + iCellHeight - 4, x, y );
        
		g.dispose( );
	}
	
	private byte getCellNumber( int x, int y ) {
		int	iColumn = ( ( x - LEFT_GAP ) / iCellWidth ),
		iRow = ( ( y - TOP_GAP ) / iCellHeight );
        
		return (byte) (( iRow * 7 ) + iColumn);
	}
    
	// try to set the default date to the date in the date text field
	private void setDateToDestFieldDate() {
		Date d = datePicker.getPreselectedDate();

		selectedYear = datePicker.getDateField(d, EasyDatePicker.YEAR);
		selectedMonth = datePicker.getDateField(d, EasyDatePicker.MONTH);

		refreshMonthYearDisplay();

		//defaultCalendar = calDefault;
		highlightedCell = -1;
		calendarCells =
			datePicker.getCalendarCellsForMonth(selectedYear, selectedMonth);
	}
	
	private void closeCalendar() {
		try {
			this.setVisible(false);
		} catch (Exception ex) {System.out.println(ex);}
	}

	// overide the show method
	public void show() {
		setDateToDestFieldDate();
		super.show();
	}

	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		int iClickedCell = getCellNumber(x, y);
		Date dBuffer = calendarCells[iClickedCell].date;
		//long lTotalDaysUntildBuffer = GetTotalDaysUntil( dBuffer );

		if (calendarCells[iClickedCell].selectable && e.getClickCount() == 2) {
			if (dest != null) {
				dest.setText(datePicker.formatDate(dBuffer));
			} else {
				System.out.println(datePicker.formatDate(dBuffer));
			}
		}
		if (x > LEFT_GAP
			&& x < LEFT_GAP + iCalendarWidth
			&& y > TOP_GAP
			&& y < TOP_GAP + iCalendarHeight
			/*&&
								Cell[iClickedCell].bSelectable*/
			) {
			if (selectedMonth
				== datePicker.getDateField(dBuffer, EasyDatePicker.MONTH)) {
				// a different date is selected from the displayed month
				highlightCell(
					highlightedCell,
					calendarCells[highlightedCell].weekend
						? calendarColors[WEEKEND_BG]
						: calendarColors[BG]);
				highlightedCell = getCellNumber(x, y);
				highlightCell(highlightedCell, calendarColors[HIGHLIGHT]);
			}
			else { // a date from the neighboring month is selected
				selectedYear = datePicker.getDateField(dBuffer, EasyDatePicker.YEAR);
				selectedMonth = datePicker.getDateField(dBuffer, EasyDatePicker.MONTH);
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				highlightedCell = (byte)datePicker.getCellNumberForDate(dBuffer);
				repaint();
			}
			
		}
		if (calendarCells[iClickedCell].selectable && e.getClickCount() == 2) {
			closeCalendar();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * set the selected date to today
	 */
	public void actionPerformed(ActionEvent arg0) {
		selectedYear = datePicker.getCurrentDateField(EasyDatePicker.YEAR);
		selectedMonth = datePicker.getCurrentDateField(EasyDatePicker.MONTH);
        
		refreshMonthYearDisplay();
        
		//defaultCalendar = calToday;
		highlightedCell = -1;
		calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
		repaint();
	}

	/**
	 * @param year
	 * @param month
	 * @return
	 */
	public CalendarCell[] getCalendarCellsForMonth(int year, int month) {
		return datePicker.getCalendarCellsForMonth(year, month);
	}

	/**
	 * @return
	 */
	public String getDayNames() {
		return datePicker.getDayNames();
	}

	/**
	 * @return
	 */
	public String getNextSelectableDateAsString() {
		return datePicker.getNextSelectableDateAsString();
	}

	/**
	 * @return
	 */
	public int getWeekBeginDay() {
		return datePicker.getWeekBeginDay();
	}

	/**
	 * @return
	 */
	public boolean isWeekendSelectable() {
		return datePicker.isWeekendSelectable();
	}

	/**
	 * @param string
	 */
	public void setDateFormat(String string) {
		datePicker.setDateFormat(string);
	}

	/**
	 * @param dayNames
	 */
	public void setDayNames(String dayNames) {
		datePicker.setDayNames(dayNames);
	}

	/**
	 * @param weekBeginDay
	 */
	public void setWeekBeginDay(int weekBeginDay) {
		datePicker.setWeekBeginDay(weekBeginDay);
	}

	/**
	 * @param b
	 */
	public void setWeekendSelectable(boolean b) {
		datePicker.setWeekendSelectable(b);
	}
	
	public void setLookAndFeel(String lnfName) {
		// update database
		try{
			UIManager.setLookAndFeel(lnfName);
			SwingUtilities.updateComponentTreeUI(this);
			//this.pack();
		} catch (Exception e) { }
	}

	/*
	 * for testing only
	 */
	 /*
	private void printDates(){
		String sBuffer;
		int i;
		for( i=0; i<42; i++ ) {
			sBuffer = String.valueOf(datePicker.getDateField( calendarCells[i].date, EasyDatePicker.DAY ) );
			System.out.print(sBuffer+" ");
			if((i+1) % 7 == 0) System.out.println("");
		}
    	
	}
	*/

}
