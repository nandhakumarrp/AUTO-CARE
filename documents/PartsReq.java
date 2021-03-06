
package admin;

import static admin.Admin.createImageIcon;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class PartsReq extends ViewBooking{
    
    JPanel p3;
    JButton ref6,respondstatus;
    JLabel l_orderid,l_status;
    JTextField tf_orderid;
    JComboBox cb_status;
            
    
    PartsReq(){
        p3 = new JPanel();
    
        String icont1 = "/resources/partsreq 2.png";
        ImageIcon icon = createImageIcon(icont1);
        
        tp.addTab("Parts Required",icon,p3,"Manage OrderS");
        tp.setBackgroundAt(2, Color.WHITE);
        p3.setLayout(null);
        p3.setBackground(Color.WHITE);

        
        new ShowTable();
        
        String icont2 = "/resources/refresh 2.jpg";
        ImageIcon b_icon = createImageIcon(icont2);
        
        ref6 = new JButton(b_icon);
        ref6.setText("REFRESH");
        ref6.setBounds(800, 100, 120, 40);
        ref6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ShowTable();
            }
        });
   
        p3.add(ref6);
        ref6.setBackground(Color.WHITE);
        
        l_orderid = new JLabel("OrderID ");
        l_orderid.setBounds(750,240,80,40);
        p3.add(l_orderid);
        
        tf_orderid = new JTextField();
        tf_orderid.setBounds(820,240,150,40);
        p3.add(tf_orderid);
        
        l_status = new JLabel("Status");
        l_status.setBounds(750,300,70,40);
        p3.add(l_status);
        
        String status[] = {"Packed","Shipped","Transit"};
        cb_status = new JComboBox<String>(status);
        cb_status.setBounds(820,300,150,40);
        p3.add(cb_status);

        String icont3 = "/resources/updatestatus.png";
        ImageIcon b_icon2 = createImageIcon(icont3); 
        
        respondstatus = new JButton(b_icon2);
        respondstatus.setText("UPDATE STATUS");
        respondstatus.setBounds(770,350,190,40);
        p3.add(respondstatus);
        respondstatus.setBackground(Color.WHITE);
        respondstatus.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    ID = tf_orderid.getText();
                    Object status =  cb_status.getItemAt(cb_status.getSelectedIndex());
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin?zeroDateTimeBehavior=convertToNull","Nandha Kumar","Beinghappy@123");
                    Statement st = con.createStatement();
                    String query = "update e_commerce set status = '"+status+"' where orderid = '"+ID+"'";
                    st.executeUpdate(query);
                    con.close();                    
                    new ShowTable();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(rootPane, ex);
                }
            }
        });
        
    }
    
//class finding the number of records in a DataBase Table
    public class Count{
        Count(){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin?zeroDateTimeBehavior=convertToNull","Nandha Kumar","Beinghappy@123");
                Statement st = con.createStatement();
                String query = "select count(*) from e_commerce ";
                ResultSet rs = st.executeQuery(query);
                rs.next();
                count = rs.getInt(1);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(rootPane,ex);
            }
        }
    }
    
    public class ShowTable {
        
        String [][]data;
        String[]colname;
        JTable jt1;
        ShowTable(){
            new Count();
            data = new String[count][8];
            String [] heading = {"OrderID","USERID","ProductID","Date","ProductName","Quantity","Totalprice","Status"};

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin?zeroDateTimeBehavior=convertToNull","Nandha Kumar","Beinghappy@123");
                Statement st = con.createStatement();
                String query = "select * from e_commerce ";
                ResultSet rs = st.executeQuery(query);

                int row = 0;
                while(rs.next()) {
                    data[row][0] = rs.getString("orderid");
                    data[row][1] = rs.getString("userid");
                    data[row][2] = rs.getString("productid");
                    data[row][3] = rs.getString("Date");
                    data[row][4] = rs.getString("productname");
                    data[row][5] = rs.getString("quantity");
                    data[row][6] = rs.getString("totalprice");
                    data[row][7] = rs.getString("status");
                    row++;
                }
                jt1 = new JTable(data,heading);
                jt1.setBounds(10,30,700,500);
                
                jt1.setEnabled(false);
                
                JScrollPane jsp = new JScrollPane(jt1);
                jsp.setBounds(10,38,714,512);

                int height = jt1.getRowHeight();
                jt1.setRowHeight(height+35);
                
//Aligning the text in JTabble to center                
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment( JLabel.CENTER );
                for(int i=0;i<8;i++){
                    jt1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
                
                p3.add(jsp);
                con.close();
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(p3,e.toString());
            }
            jt1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jt1.setLayout(null);
            
   
        }
    }

    
    
    public static void main(String args[]){
        new PartsReq();
    }
    
}
