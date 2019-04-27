/*
 * @author : Dev Kathuria (1710110103)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

class PageReplacement extends JFrame{
    private PageReplacement(){
        prp p = new prp();
        JScrollPane s = new JScrollPane();
        add(s);
        add(p);
    }
    public static void main(String[] args) {
        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); //to set UI
        }
        catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
        PageReplacement obj = new PageReplacement();

        obj.setLayout(new FlowLayout());
        obj.setTitle("Input");
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //obj.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        obj.setSize(860,760);
        //obj.pack();
        obj.setLocationRelativeTo(null);
    }
}

class prp extends JPanel {
    private JLabel numF_label = new JLabel("Number of frames : ");
    private JLabel RS_label = new JLabel("Reference String : ");
    private JLabel fifo_label = new JLabel("First In First Out");
    private JLabel lru_label = new JLabel("Least Recently Used");
    private JLabel opr_label = new JLabel("Optimal Page Replacement");

    private String[] frames = new String[]{"3", "4", "5", "6","7"};
    private JComboBox nf = new JComboBox(frames);

    private Font fo = new Font("Serif", Font.BOLD, 16);
    private JTextField ref_string_tf = new JTextField("1,2,3", 30);

    private JButton compute = new JButton("Compute");
    private JButton reset = new JButton("Reset");

    private JTable fifo_Table = new JTable(7,25);
    private JTable opr_Table = new JTable(7,25);
    private JTable lru_Table = new JTable(7,25);

    private JLabel label_fifo = new JLabel("FIFO");
    private JLabel label_lru = new JLabel("LRU");
    private JLabel label_opr = new JLabel("OPR");

    //private JLabel pf_fifo = new JLabel("Page Faults: ");
    //private JLabel pf_lru = new JLabel("Page Faults: ");
    //private JLabel pf_opr = new JLabel("Page Faults");

    JScrollPane sc = new JScrollPane();

    private int[][] result;
    private int[] ref_array;
    private int frame_num;
    private int page_faults_fifo = 0;
    private int page_faults_opr = 0;
    private int page_faults_lru = 0;

    public prp() {

        //Designing GUI

        numF_label.setFont(fo);
        RS_label.setFont(fo);
        fifo_label.setFont(fo);
        lru_label.setFont(fo);
        opr_label.setFont(fo);
        compute.setFont(fo);
        reset.setFont(fo);
        ref_string_tf.setFont(fo);

        numF_label.setForeground(Color.DARK_GRAY);
        RS_label.setForeground(Color.DARK_GRAY);
        fifo_label.setForeground(Color.darkGray);
        lru_label.setForeground(Color.darkGray);
        opr_label.setForeground(Color.darkGray);
        compute.setForeground(Color.DARK_GRAY);
        reset.setForeground(Color.DARK_GRAY);
        ref_string_tf.setForeground(Color.lightGray);

        JPanel MainPanel = new JPanel(new BorderLayout(5,5));
        JPanel P1 =new JPanel(new GridLayout(4,2,5,5));
        P1.add(numF_label);
        P1.add(nf);

        P1.add(RS_label);
        P1.add(ref_string_tf);

        P1.add(compute);
        P1.add(reset);
        MainPanel.setSize(1000,1000);
        P1.add(new JSeparator());
        P1.add(new JSeparator());
        MainPanel.add(P1,BorderLayout.NORTH);
        JPanel XP = new JPanel(new GridLayout(3,1,5,5));
        JPanel P2 =new JPanel(new BorderLayout());
        JPanel P3 =new JPanel(new BorderLayout());
        JPanel P4 =new JPanel(new BorderLayout());

        fifo_Table.setPreferredScrollableViewportSize(new Dimension(750,120));
        fifo_Table.setFillsViewportHeight(true);
        fifo_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane1 = new JScrollPane(fifo_Table);
        P2.add(scrollPane1,BorderLayout.CENTER);
        P2.add(label_fifo,BorderLayout.NORTH);

        lru_Table.setPreferredScrollableViewportSize(new Dimension(750,120));
        lru_Table.setFillsViewportHeight(true);
        lru_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane2 = new JScrollPane(lru_Table);
        P3.add(scrollPane2,BorderLayout.CENTER);
        P3.add(label_lru,BorderLayout.NORTH);


        opr_Table.setPreferredScrollableViewportSize(new Dimension(750,120));
        opr_Table.setFillsViewportHeight(true);
        opr_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane3 = new JScrollPane(opr_Table);
        P4.add(scrollPane3,BorderLayout.CENTER);
        P4.add(label_opr,BorderLayout.NORTH);

        XP.add(P2);
        XP.add(P3);
        XP.add(P4);
        XP.setVisible(false);
        MainPanel.add(XP,BorderLayout.CENTER);
        add(MainPanel);

        compute.addActionListener(e -> {
            XP.setVisible(true);
            String[] ref_string = (ref_string_tf.getText()).split("[,]");
            ref_array = new int[ref_string.length];

            try {
                for (int i = 0; i < ref_string.length; i++) {
                    ref_array[i] = Integer.parseInt(ref_string[i]);
                    System.out.println(ref_array[i]);                     //extracting ref array
                }
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Wrong Input");
            }
            frame_num = Integer.parseInt(nf.getSelectedItem().toString());  //extracting frame number

            for(int i=0;(i<ref_string.length);i++){
                fifo_Table.getColumnModel().getColumn(i).setHeaderValue(ref_string[i]);
                lru_Table.getColumnModel().getColumn(i).setHeaderValue(ref_string[i]);
                opr_Table.getColumnModel().getColumn(i).setHeaderValue(ref_string[i]);
            }
            fifo_Table.getTableHeader().resizeAndRepaint();
            lru_Table.getTableHeader().resizeAndRepaint();
            opr_Table.getTableHeader().resizeAndRepaint();

            result = new int[frame_num][ref_array.length];          //result 2d array
            //FIFO...................................................
            for (int i = 0; i < frame_num; i++) {
                System.out.println(" ");
                for (int j = 0; j < ref_array.length; j++) {
                    System.out.print(result[i][j] + " ");
                    fifo_Table.setValueAt("Null",i,j);
                    lru_Table.setValueAt("Null",i,j);
                    opr_Table.setValueAt("Null",i,j);
                }
            }
            fifo();
            lru();
            opr();
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ref_string_tf.setText("");
                setResulttoNull();
                try {
                    Runtime.getRuntime().exec("java PageReplacement.java");
                    System.exit(0);
                }
                catch(Exception ignored){}
            }
        });
    }

    private void setResulttoNull() {
        for (int i = 0; i < frame_num; i++)
            Arrays.fill(result[i], -1);
    }

    private int i=0;
    private int j=0;
    private void fifo() {
        setResulttoNull();
        int found;
        System.out.println(frame_num);
        for(i=0;i<ref_array.length;i++){
            found=0;
            for (j=0;j<frame_num;j++){
                System.out.println(result[j][i]+"  "+j+"  "+i);
                if(result[j][i]==ref_array[i]) {
                    found=1;
                }
            }
            if(found==0) {
                result[page_faults_fifo % frame_num][i] = ref_array[i];
                fifo_Table.setValueAt(ref_array[i] + "", page_faults_fifo % frame_num, i);
                fillrow(fifo_Table, ref_array[i], page_faults_fifo % frame_num, i);
                page_faults_fifo++;
            }
        }
        System.out.println("page_faults_fifo"+page_faults_fifo);
        label_fifo.setText("FIFO                                                              " +
                "                                  " +
                "                                                             " +
                "                                                          Page Faults: "+page_faults_fifo);

    }
    private void lru(){
        setResulttoNull();
        int found;
        for(i=0;i<ref_array.length;i++){
            found=0;
            for (j=0;j<frame_num;j++){
                System.out.println(result[j][i]+"  "+j+"  "+i);
                if(result[j][i]==ref_array[i]) {
                    found=1;
                }
            }
            if(found==0) {
                fillrow(lru_Table, ref_array[i], lru_row(i), i); //table,val,row,col
                page_faults_lru++;
            }
        }
        System.out.println("page_faults_fifo"+page_faults_lru);
        label_lru.setText("LRU                                                             " +
                "                                  " +
                "                                                             " +
                "                                                          Page Faults: "+page_faults_lru);
    }
    private void opr(){
    setResulttoNull();
        int found;
        for(i=0;i<ref_array.length;i++){
            found=0;
            for (j=0;j<frame_num;j++){
                System.out.println(result[j][i]+"  "+j+"  "+i);
                if(result[j][i]==ref_array[i]) {
                    found=1;
                }
            }
            if(found==0) {
                fillrow(opr_Table, ref_array[i], opr_row(i), i); //table,val,row,col
                page_faults_opr++;
            }
        }
        label_opr.setText("OPR                                                              " +
                "                                  " +
                "                                                             " +
                "                                                          Page Faults: "+page_faults_opr);

    }
    private int row_index;
    private int lru_row(int col){
        row_index=0;
        int []lru_array = new int[result.length];

        for (int i = 0 ; i < lru_array.length; i++){
            lru_array[i] = Integer.MIN_VALUE;
        }
        for (int j = 0 ; j < result.length ; j++) { //This function checks whether there is a place empty before
            if (result[j][col] == -1) {
                return j;
            }
        }
        for (int k = 0 ; k < result.length ; k++) {  //set lru arrays
            for (int i = 0; i < col; i++) {
                if (ref_array[i] == result[k][col]){
                    lru_array[k] = i ;
                }
            }
        }
        for (int i = 0 ; i < lru_array.length ; i++){ //find the least
            if (lru_array[row_index] > lru_array[i]){
                row_index = i;
            }
        }
        return row_index;  //returning the least index
    }
    private int opr_row(int i){
        row_index=0;
        for (j=0;j<result.length;j++){ //This function checks weather there is a place empty
            if (result[j][i]==-1) {
                return j;
            }
        }
        int [] opt_array = new int[result.length];
        for (int k=0;k<=opt_array.length-1 ; k++){    //setting the optimal array with future values
            opt_array[k] = ref_array.length;
        }
        for (int k=0;k<result.length;k++) {
            for (int j=i+1;j<ref_array.length;j++) {    //finding the optimal
                if (result[k][i] == ref_array[j]){
                    opt_array[k] = j;
                    break;
                }
            }
        }
        for (int j = 0 ; j < opt_array.length; j++){
            if (opt_array[row_index]<opt_array[j]){ //finding the index to be replaced
                row_index = j;
            }
        }
        return row_index;  //returning the index
    }
    private void fillrow(JTable table, int value, int row, int col){
        for(int s=col;s<ref_array.length;s++){
            table.setValueAt(value+"",row,s);
            result[row][s]=value;
        }
    }
}