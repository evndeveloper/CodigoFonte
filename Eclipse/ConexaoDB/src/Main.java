import java.sql.Connection;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		ContatoDAO contatoDAO = new ContatoDAO();
		Contato novo = new Contato();
		novo.setId("p3");
		novo.setNoA("p3");
		novo.setNoB("p4");
		//novo.setContagem(1);
		contatoDAO.adiciona(novo);
		
		//Contato contato = contatoDAO.getContato(novo);
		//System.out.println(contato.getId() + " | " +contato.getNoA() + " --> " + contato.getNoB() + " = " + contato.getContagem());
	}

}
