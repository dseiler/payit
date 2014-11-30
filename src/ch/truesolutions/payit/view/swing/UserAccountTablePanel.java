/*
                A basic implementation of the JDialog class.
 */

package ch.truesolutions.payit.view.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ch.truesolutions.payit.exceptions.InvalidDataException;
import ch.truesolutions.payit.model.*;
import ch.truesolutions.payit.model.Config;
import ch.truesolutions.payit.model.UserAccount;

public class UserAccountTablePanel extends JDialog implements MouseListener, ConfigChangeListener {

    // fonts
/*
	Font labelFont = Config.labelFont;
	Font normalFont = Config.normalFont;
	Font mediumFont = Config.mediumFont;
	Font bigFont = Config.bigFont;
*/	    
    // panels
    JPanel mainPanel 	= new JPanel(new BorderLayout());
    JPanel commandPanel = new JPanel();
    
    // center objects
    JScrollPane jScrollPane     = new JScrollPane();
    JTable userAccountsTable    = new JTable();
    
    // command objects
    JButton newBtn 		= new JButton();
    JButton editBtn 	= new JButton();
    JButton deleteBtn 	= new JButton();
    JButton cancelBtn 	= new JButton();
    
    MainFrame parentFrame;
    
    public UserAccountTablePanel(Frame parentFrame) {
        super(parentFrame);
        
        this.parentFrame = (MainFrame)parentFrame;
        
        setModal(true);
        getContentPane().setLayout(new BorderLayout());
        setResizable(true);
        setSize(600,400);
        setVisible(false);
        
        commandPanel.setLayout(null);
        commandPanel.setPreferredSize(new Dimension(600,40));
        
        // initialize the comp on the command panel
        //newBtn.setFont(labelFont);
        newBtn.setSize(100,30);
        newBtn.setLocation(5,5);
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleNewBtn();
            }
        });
        //editBtn.setFont(labelFont);
        editBtn.setSize(100,30);
        editBtn.setLocation(110,5);
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleEditBtn();
            }
        });
        //deleteBtn.setFont(labelFont);
        deleteBtn.setSize(100,30);
        deleteBtn.setLocation(215,5);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteBtn();
            }
        });
        //cancelBtn.setFont(labelFont);
        cancelBtn.setSize(100,30);
        cancelBtn.setLocation(490,5);
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleCancelBtn();
            }
        });
        
        commandPanel.add(newBtn);
        commandPanel.add(editBtn);
        commandPanel.add(deleteBtn);
        commandPanel.add(cancelBtn);
        
        // initialize the table
        userAccountsTable.setSelectionMode(0);
        
        userAccountsTable.addMouseListener(this);
        userAccountsTable.setModel(UserAccountTableModel.getInstance());
        userAccountsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // if we have at least one row we select it
        if(userAccountsTable.getRowCount() > 0) {
            userAccountsTable.setRowSelectionInterval(0,0);
        }
        
        userAccountsTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        userAccountsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        userAccountsTable.getColumnModel().getColumn(2).setPreferredWidth(338);
        
        jScrollPane.getViewport().add(userAccountsTable, null);
        
        mainPanel.add(jScrollPane);
        // Adding the panels to the frame
        getContentPane().add(mainPanel,BorderLayout.CENTER);
        getContentPane().add(commandPanel,BorderLayout.SOUTH);
        
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent event){
                handleCancelBtn();
            }
        });
        
        applyLanguageChange();
    }
    
    // handler methods
    private void handleNewBtn() {
        showUserAccountPanel(new UserAccountPanel(this,new UserAccount()));
    }
    private void handleEditBtn() {
        int row = userAccountsTable.getSelectionModel().getMinSelectionIndex();
        if(row == -1) {
            return;
        }
        UserAccount ua = MainModel.getInstance().getUserAccountsModel().getUserAccount(row);
        showUserAccountPanel(new UserAccountPanel(this,ua));
    }
    
    private void handleDeleteBtn() {
        int row = userAccountsTable.getSelectionModel().getMinSelectionIndex();
        if(row == -1) {
            return;
        }

        UserAccount ua = MainModel.getInstance().getUserAccountsModel().getUserAccount(row);

        Object[] options = {Config.getInstance().getText("msg.yes"),
        	Config.getInstance().getText("msg.no")};
        int n = JOptionPane.showOptionDialog(this,
        	Config.getInstance().getText("question_msg.deleteaccount"),
        	Config.getInstance().getText("delete_msg.accounttitle"),
        	JOptionPane.YES_NO_OPTION,
        	JOptionPane.QUESTION_MESSAGE,
        	null,
        	options,
        	options[1]);
        if(n == 0) {
			try {
				MainModel.getInstance().getUserAccountsModel().removeUserAccount(ua);
            } catch (InvalidDataException e) {
                // msg popup
                JOptionPane.showMessageDialog(this,
                e.getMessage(),
                Config.getInstance().getText("msg.payittitle"),
                JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void handleCancelBtn() {
        this.setVisible(false);
    }
    
    private void showUserAccountPanel(UserAccountPanel ua) {
        int xoff = (this.getWidth() - ua.getWidth())/2;
        xoff = (xoff > 0) ? xoff : 0;
        int yoff = (this.getHeight() - ua.getHeight())/2;
        yoff = (yoff > 0) ? yoff : 0;
        ua.setLocation(this.getX()+xoff,this.getY()+yoff);
        ua.setVisible(true);
    }
    
    /*
    public void refreshPaymentsTableDisplay() {
        // it's only an update on the main table
        parentFrame.refreshPaymentsTableDisplay(2);
    }
    */
    /**
     * Method of the MouseListener
     */
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e) {
        if(e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            if(e.getClickCount()==2) {
                int row = userAccountsTable.getSelectionModel().getMinSelectionIndex();
                UserAccount ua = MainModel.getInstance().getUserAccountsModel().getUserAccount(row);
                showUserAccountPanel(new UserAccountPanel(this,ua));
            }
        }
    }
    
    /**
     * Methods of the ConfigChangeListenerInterface
     */
    public void applyLanguageChange() {
        setTitle(Config.getInstance().getText("useraccounttablepanel.title"));
        
        newBtn.setText(Config.getInstance().getText("button.new"));
        editBtn.setText(Config.getInstance().getText("button.edit"));
        deleteBtn.setText(Config.getInstance().getText("button.delete"));
        cancelBtn.setText(Config.getInstance().getText("button.cancel"));
    }
    
    public void setLookAndFeel(String lnfName){}
}