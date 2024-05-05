import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class App extends JFrame{
    private JPanel MainPanel;
    private JTextField StartWordTextField;
    private JTextField TargetWordTextField;
    private JLabel StartWordLabel;
    private JLabel TargetWordLabel;
    private JPanel StartInputPanel;
    private JPanel TargetInputPanel;
    private JPanel ResultPanel;
    private JButton SolveButton;
    private JComboBox AlgorithmDrowDown;
    private JLabel AlgorithmLabel;
    private JList ResultList;
    private JLabel ResultLabel;
    private JLabel BlankLabel;
    private JLabel BlankLabel1;
    private JLabel ExecutionTimeLabel;
    private JLabel NodeCheckedLabel;
    private JScrollPane ResultScrollPane;
    private  WordLadder game;
    private String startWord;
    private static String endWord;
    private int algoNum;
    private String algo;

    static class CustomCharRenderer extends JPanel implements ListCellRenderer<String> {
        public CustomCharRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            removeAll();
            if(value.equals("No Possible Path :(") || value.equals("Input word not in vocabulary") || value.equals("Start word and target word length must be the same") || value.equals("Input word must not contain whitespace")){
                JLabel label = new JLabel(value);
                label.setHorizontalAlignment(0);
                label.setForeground(Color.RED);
                add(label);
            }else{
                for (int i=0;i < value.length();i++) {
                    JLabel label = new JLabel(String.valueOf(value.charAt(i)));
                    label.setHorizontalAlignment(0);
                    if(value.charAt(i) == endWord.charAt(i)){
                        label.setForeground(Color.GREEN);
                    }
                    add(label);
                }
            }
            return this;
        }
    }

    public App() {
        this.game = new WordLadder();
        SolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start   : "+StartWordTextField.getText());
                System.out.println("Target  : "+TargetWordTextField.getText());
                System.out.println("Algo    : "+AlgorithmDrowDown.getSelectedItem());
                System.out.println("Algo Idx: "+AlgorithmDrowDown.getSelectedIndex());
                List<String> dataList = new ArrayList<>();
                startWord = StartWordTextField.getText().trim().toUpperCase();
                endWord = TargetWordTextField.getText().trim().toUpperCase();
                algoNum = AlgorithmDrowDown.getSelectedIndex();

                // validate start word
                if(startWord.contains(" ") || endWord.contains(" ")) {
                    // input must be 1 word
                    System.out.println("Contain whitespace");
                    dataList.add("Input word must not contain whitespace");
                }else if(startWord.length() != endWord.length()){
                    // length of start word and target word must be the same
                    System.out.println("Different Length");
                    dataList.add("Start word and target word length must be the same");
                }else if ( !game.isInVocabulary(startWord) && !game.isInVocabulary(endWord)){
                    // input word must be in english dictionary
                    System.out.println("Not in Vocabulary");
                    dataList.add("Input word not in vocabulary");
                }else{
                    if(algoNum == 0){
                        algo = "UCS";
                    }else if(algoNum == 1){
                        algo = "GBFS";
                    }else{
                        algo = "A*";
                    }
                    System.out.println("PROCESSING");
                    double startTime = System.currentTimeMillis();
                    game.initiateGame(startWord,endWord,algo);
                    game.process();

                    double endTime = System.currentTimeMillis();
                    double executionTime = endTime - startTime;
                    ExecutionTimeLabel.setText("Execution Time: " + String.valueOf(executionTime) + " ms");
                    NodeCheckedLabel.setText("Node Checked: " + String.valueOf(game.getNodeChecked()));

                    dataList = game.getResultList();

                    game.printResultPath();
                    System.out.println("Execution Time: " + String.valueOf(executionTime) + " ms");
                    System.out.println("Node Checked: " + String.valueOf(game.getNodeChecked()));
                }
                String[] arrayData = dataList.toArray(new String[0]);
                ResultList.setListData(arrayData);
                ResultList.setCellRenderer(new CustomCharRenderer());

            }
        });
    }

    public static void main(String[] args){
        App swing = new App();
        swing.setContentPane(swing.MainPanel);
        swing.setTitle("Tucil3_13522103");
        swing.setMinimumSize(new Dimension(500,300));
        swing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        swing.pack();
        swing.setVisible(true);
    }
}
