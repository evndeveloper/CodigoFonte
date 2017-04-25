import java.sql.Connection;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		ContatoDAO contatoDAO = new ContatoDAO();
		Contato novo = new Contato();
		novo.setId("p7");
		novo.setNoA("p7");
		novo.setNoB("p94");
		novo.setContagem(1);
		contatoDAO.adiciona(novo);
		
		Contato contato = contatoDAO.getContato("p7", "p94");
		System.out.println(contato.getId() + " | " +contato.getNoA() + " --> " + contato.getNoB() + " = " + contato.getContagem());
	}

}
