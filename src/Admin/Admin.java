package Admin;

import static Admin.Login.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
//Immediate Assistance TAb
public class Admin extends JFrame{
    //Defining default AdminId to be 1:
    //int AID = adminid;
    int AID=1;
    JTabbedPane tp;
    JPanel p1;
    JButton ref2,b_accept,b_decline,b_respond;
    JLabel l_userID,l_min,l_inmin;
    JTextField tf_userID;
    JComboBox cb_min;
    String ID;
    int count;
    
    Admin(){

        tp = new JTabbedPane();
        tp.setBounds(0,0,1366, 768);
        p1 = new JPanel();
        p1.setLayout(null);
        System.out.println(AID);
        
        String icont1 = "/resources/immediate 1.png";
        ImageIcon icon = createImageIcon(icont1);

        tp.addTab("Immediate Assistance",icon,p1,"Immediate Assistane");
        add(tp);
        tp.setBackgroundAt(0,Color.WHITE);
        p1.setBackground(Color.WHITE);
        
        new ShowTable();
        
        String icont2 = "/resources/refresh 2.jpg";
        ImageIcon b_icon = createImageIcon(icont2);
        
        ref2 = new JButton(b_icon);
        ref2.setText("REFRESH");
        ref2.setBounds(1100, 100, 120, 40);
        ref2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ShowTable();
            }
            
        });
        p1.add(ref2);
        ref2.setBackground(Color.WHITE);
        
        l_userID = new JLabel("AssistID");
        l_userID.setBounds(1050,200,80,40);
        p1.add(l_userID);
        
        tf_userID = new JTextField();
        tf_userID.setBounds(1130,200,150,40);
        p1.add(tf_userID);
        
        
        l_min = new JLabel("Estimated Time to Reach The Location :");
        l_min.setBounds(1050,400,500,40);
        p1.add(l_min);
        l_min.setVisible(false);
        
        String minutes[] = {"10","20","30","40"};
        cb_min = new JComboBox(minutes);
        cb_min.setBounds(1070,450,80,40);
        p1.add(cb_min);
        cb_min.setVisible(false);
        
        
        l_inmin = new JLabel("In Minutes.");
        l_inmin.setBounds(1170,450,100,40);
        p1.add(l_inmin);
        l_inmin.setVisible(false);
        
        
        b_accept = new JButton("Accept");
        b_accept.setBounds(1060,250,90,40);
        p1.add(b_accept);
        b_accept.setBackground(Color.WHITE);
        b_accept.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
              
               ID = tf_userID.getText();
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bcproject","root","Ram03");
                    Statement st = con.createStatement();
                    String query = "update assist set status = 'Approved' where assistid = '"+ID+"' and status!='Declined'";
                    st.executeUpdate(query);
                    String query1= "select * from assist where assistid = '"+ID+"' and status!='Declined'";
                    ResultSet rs=st.executeQuery(query1);
                    if(rs.next())
                    {
                         l_min.setVisible(true);
                         cb_min.setVisible(true);
                         l_inmin.setVisible(true);
                         b_respond.setVisible(true);
                         new ShowTable();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(rootPane, "Assist Id does not exist");
                    }
                    con.close();
                    new ShowTable();
               }
               catch(Exception ex){
                   JOptionPane.showMessageDialog(rootPane, ex);
               }
           } 
        });
        
        b_decline = new JButton("Decline");
        b_decline.setBounds(1160,250,90,40);
        p1.add(b_decline);
        b_decline.setBackground(Color.WHITE);
        b_decline.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bcproject","root","Ram03");
                    Statement st = con.createStatement();
                    String query = "update assist set status = 'Declined' where assistid = '"+ID+"'";
                    st.executeUpdate(query);
                    con.close();
                    new ShowTable();
               }
               catch(Exception ex){
                   JOptionPane.showMessageDialog(rootPane, ex);
               }
            }
        });
        //Responding Estimated time to user
        b_respond = new JButton("RESPOND USER");
        b_respond.setBounds(1050,500,200,40);
        p1.add(b_respond);
        b_respond.setBackground(Color.WHITE);
        b_respond.setVisible(false);
        b_respond.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    ID = tf_userID.getText();
                    Object minutes =  cb_min.getItemAt(cb_min.getSelectedIndex());
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bcproject","root","Ram03");
                    Statement st = con.createStatement();
                    String query = "update assist set EstimatedTime = '"+minutes+"' where assistid = '"+ID+"'";
                    st.executeUpdate(query);
                    con.close();            
                    new ShowTable();
                    JOptionPane.showMessageDialog(rootPane,"EstimatedTime Updated to "+minutes+" minutes");
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(rootPane, ex);
                }
            }
        });
        
       
        //setSize(1170,1000);
        setLayout(null);
        setLayout(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
//class finding the number of records in a DataBase Table
    public class Count{
        Count(){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bcproject","root","Ram03");
                Statement st = con.createStatement();
                String query = "select count(*) from assist where adminid='"+AID+"'";
                ResultSet rs = st.executeQuery(query);
                rs.next();
                count = rs.getInt(1);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(rootPane,ex);
            }
        }
    }
    
//Class Displaying Table In Pannel
        public class ShowTable {
        String data[][];
        String colname[];
        JTable jt1;
        ShowTable(){
            new Count();
            data = new String[count][12];
            String [] heading = {"Assist ID","UserID","Admin Id","Worker ID","landmark","Pincode","VehicleBrand","VehicleModel","Service","status","EstimatedTime","Reason"};

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bcproject","root","Ram03");
                Statement st = con.createStatement();
                String query = "select * from assist where adminid='"+AID+"'";
                ResultSet rs = st.executeQuery(query);

                int row = 0;
                while(rs.next()) {
                    data[row][0] = rs.getString("assistid");
                    data[row][1] = rs.getString("userid");
                    data[row][2] = rs.getString("adminid");
                    data[row][3] = rs.getString("workerid");
                    data[row][6] = rs.getString("vehiclebrand");
                    data[row][7] = rs.getString("vehiclemodel");
                    data[row][4] = rs.getString("landmark");
                    data[row][5] = rs.getString("pincode");
                    data[row][8] = rs.getString("service");
                    data[row][9] = rs.getString("status");
                    data[row][10] = rs.getString("EstimatedTime");
                    data[row][11] = rs.getString("reason");
                    row++;
                }
                jt1 = new JTable(data,heading);
                jt1.setBounds(10,30,1000,600);
            
                jt1.setEnabled(false);

                JScrollPane jsp = new JScrollPane(jt1);
                jsp.setBounds(10,38,1014,612);
                

                int height = jt1.getRowHeight();
                jt1.setRowHeight(height+35);
//Aligning the text in JTabble to center                
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment( JLabel.CENTER );
                for(int i=0;i<12;i++){
                    jt1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
                
                p1.add(jsp);
                con.close();
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(p1,e.toString());
            }
            jt1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jt1.setLayout(null);

        }
    }
        
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Admin.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        new Admin();
        
    }
}


