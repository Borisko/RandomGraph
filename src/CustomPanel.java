import javax.swing.*;

public class CustomPanel extends JPanel {

    JTextField textField;

    public CustomPanel(String labelText){

        JLabel label = new JLabel(labelText);
        textField = new JTextField(20);

        add(label);
        add(textField);
    }

    public double getValue(){
        if(textField.getText().equals("")){
            return 0;
        }
        else {
            return Double.parseDouble(textField.getText());
        }
    }
}
