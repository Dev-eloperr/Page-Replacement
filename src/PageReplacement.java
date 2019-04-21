/*
 * @author : Dev Kathuria (1710110103)
 *
 *
 * //THERE IS SCOPE OF FUTURE DEVELOPMENT AND OPTIMIZATION IN THIS PROGRAM
 *
 * //FEW BUGS COULD BE ENCOUNTERED IN RR ALGORITHM
 *
 * //REST ALL THE ALGORITHMS WORKS FINE IN ALL THE CASES
 *
 * //UI IS SET WITH UI MANAGER
 * */


import com.sun.org.glassfish.external.statistics.annotations.Reset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class PageReplacement extends JFrame{
    private PageReplacement(){
        prp p = new prp();
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
        obj.setSize(825, 800);
        obj.setLocationRelativeTo(null);
    }
}

class prp extends JPanel implements ActionListener {
    private JLabel numF_label = new JLabel("Number of frames : ");
    private JLabel RS_label = new JLabel("Reference String : ");
    private JLabel fifo_label = new JLabel("First In First Out");
    private JLabel lru_label = new JLabel("Least Recently Used");
    private JLabel opr_label = new JLabel("Optimal Page Replacement");

    private String[] frames = new String[]{"1", "2", "3", "4", "5", "6"};
    private JComboBox nf = new JComboBox(frames);

    private Font fo = new Font("Serif", Font.BOLD, 16);
    private JTextField ref_string_tf = new JTextField("Enter Ref String", 30);

    private JButton compute = new JButton("Compute");
    private JButton reset = new JButton("Reset");

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

        JPanel MainPanel = new JPanel(new GridLayout(2,1,5,5));
        JPanel P1 =new JPanel(new GridLayout(4,2,5,5));

        P1.add(numF_label);
        P1.add(nf);

        P1.add(RS_label);
        P1.add(ref_string_tf);

        P1.add(compute);
        P1.add(reset);

        P1.add(new JSeparator());
        P1.add(new JSeparator());
        MainPanel.add(P1);

        JPanel P2 =new JPanel(new GridLayout(6,1,5,5));

        P2.add(fifo_label);
        P2.add(lru_label);
        P2.add(opr_label);

        MainPanel.add(P2);

        add(MainPanel);

    }
    @Override
    public void actionPerformed(ActionEvent e){

    }
}
