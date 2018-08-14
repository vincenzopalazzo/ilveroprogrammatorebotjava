package top.gigabox.ilveroprogrammatore.persistenza;

import top.gigabox.ilveroprogrammatore.modello.Frase;

import java.util.List;

public interface IDAOFrasi {

    abstract public List<Frase> load(String nomeFile) throws DAOException;
}
