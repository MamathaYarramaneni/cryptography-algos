import java.util.*;
import java.math.*;
import java.awt.*;
import java.awt.event.*;
class ElgammalFrame extends Frame implements ActionListener{
	BigInteger q,a,Y,K,c1,c2,M,K1,M1;
	int X,k;
    String r="",s1="",s2="",E1="",E2="";
	Label label_a,label_q,label_privatekey_X,label_encrypt,label_c1,label_c2,label_decrypt,label_M,label_K1,label_M1;
	TextField tf_a,tf_q,tf_privatekey_X,tf_generated_K,tf_generated_Y,tf_c1,tf_c2,tf_M,tf_K1,tf_M1;
	Button b_generate_K,b_generate_Y,b_encrypt,b_decrypt;
	ElgammalFrame(){
		setTitle("Elgammal encryption");
		setBackground(Color.LIGHT_GRAY);
        setVisible(true);
		setSize(new Dimension(1000,1500));
		setLayout(new FlowLayout());
        setFont(new Font("Arial",Font.BOLD,20));
        setLayout(new GridLayout(12,2,20,20));    
 
	label_a=new Label("enter value: a");
	tf_a=new TextField(50);
	add(label_a);
	add(tf_a);
	
	label_q=new Label("enter value: q");
	tf_q=new TextField(50);
	add(label_q);
	add(tf_q);

	label_privatekey_X=new Label("enter value: privatekey_X");
	tf_privatekey_X=new TextField(50);
	add(label_privatekey_X);
	add(tf_privatekey_X);

	
	b_generate_Y=new Button("Generate Y:");
	tf_generated_Y=new TextField(50);
	add(b_generate_Y);
	add(tf_generated_Y);

	b_generate_K=new Button("Generate K:");
	tf_generated_K=new TextField(50);
	add(b_generate_K);
	add(tf_generated_K);
	
	label_M=new Label("enter value: Message M :");
	tf_M=new TextField(50);
	add(label_M);
	add(tf_M);

	label_encrypt=new Label("Encrypt:");
	b_encrypt=new Button("Encrypt");
	add(label_encrypt);
	add(b_encrypt);

	label_c1=new Label("generated C1:");
	tf_c1=new TextField(50);
	add(label_c1);
	add(tf_c1);

	label_c2=new Label("generated C2:");
	tf_c2=new TextField(50);
	add(label_c2);
	add(tf_c2);

	label_decrypt=new Label("Decrypt:");
	b_decrypt=new Button("Decrypt");
	add(label_decrypt);
	add(b_decrypt);

	label_K1=new Label("Decrypted: value K:");
	tf_K1=new TextField(50);
	add(label_K1);
	add(tf_K1);

	label_M1=new Label("Decrypted: Message M :");
	tf_M1=new TextField(50);
	add(label_M1);
	add(tf_M1);
	
	b_generate_Y.addActionListener(this);
	b_generate_K.addActionListener(this);
	b_encrypt.addActionListener(this);
	b_decrypt.addActionListener(this);
	}
	public Insets getInsets(){
        return new Insets(50,50,50,50);
    }
	public void actionPerformed(ActionEvent ae){
	
        String str=ae.getActionCommand();
        if(str.equals("Generate Y:")){
			q = new BigInteger(tf_q.getText());
      		a = new BigInteger(tf_a.getText());
      		X=Integer.parseInt(tf_privatekey_X.getText());
			Y=a.pow(X).mod(q);
			tf_generated_Y.setText(Y+"");
		}
		if(str.equals("Generate K:")){
			k=6;
			K=Y.pow(k).mod(q);
			tf_generated_K.setText(K+"");
		}
		if(str.equals("Encrypt")){
			c1=a.pow(k).mod(q);
      		M=new BigInteger(tf_M.getText());
			c2=K.multiply(M).mod(q);
			tf_c1.setText(c1+"");
			tf_c2.setText(c2+"");
		}
		if(str.equals("Decrypt")){
			K1=c1.pow(X).mod(q);
			M1=(c2.multiply(K1.modInverse(q))).mod(q);
			tf_K1.setText(K1+"");
			tf_M1.setText(M1+"");
		}
	}
	
	public static void main(String args[]){
		new ElgammalFrame();
	}
}